package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.security.LoginUserDetails;
import com.example.SMG_BBS.validation.EditValidation;
import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.repository.entity.User;
import com.example.SMG_BBS.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /*
     * 新規ユーザ登録画面表示
     */
    @GetMapping("/user/new")
    public ModelAndView newUser(Model model) {

        ModelAndView mav = new ModelAndView();

        if (!model.containsAttribute("formModel")) {
            UserForm userForm = new UserForm();
            mav.addObject("formModel", userForm);
        }

        mav.setViewName("user/new");
        return mav;
    }

    /*
     * ユーザ登録処理
     */
    @PostMapping("/user/add")
    public ModelAndView addUser(String confirmationPassword,
                                @ModelAttribute("formModel") @Valid UserForm userForm,
                                BindingResult result) {

        // アカウント重複チェック
        User DuplicationUser = userService.selectUserByAccount(userForm.getAccount());
        if (DuplicationUser != null) {
            FieldError fieldError = new FieldError(result.getObjectName(),
                    "account", "アカウントが重複しています");
            result.addError(fieldError);
        }

        // パスワードとパスワード（確認用）の一致チェック
        if (userForm.getPassword() != null) {
            String password = userForm.getPassword();
            if (!password.matches(confirmationPassword)) {
                FieldError fieldError = new FieldError(result.getObjectName(),
                        "password", "パスワードと確認用パスワードが一致しません");
                result.addError(fieldError);
            }
        }

        // 支社と部署の組み合わせチェック
        if (userForm.getBranchId() != null && userForm.getDepartmentId() != null) {
            Map<String, Set<String>> allowedCombinations = Map.of(
                    "1", Set.of("1", "2"),
                    "2", Set.of("3", "4"),
                    "3", Set.of("3", "4"),
                    "4", Set.of("3", "4")
            );
            String branchId = userForm.getBranchId().toString();
            String departmentId = userForm.getDepartmentId().toString();
            if (!(allowedCombinations.containsKey(branchId) && allowedCombinations.get(branchId).contains(departmentId))) {
                FieldError fieldError = new FieldError(result.getObjectName(),
                        "branchId", "支社と部署の組み合わせが不正です");
                result.addError(fieldError);
            }
        }

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("formModel", userForm);
            mav.setViewName("user/new");
            return mav;
        }

        userService.saveUser(userForm);
        return new ModelAndView("redirect:/user/management");
    }

    /*
     * ユーザー管理画面表示
     */
    @GetMapping("/user/management")
    public ModelAndView userManage(@AuthenticationPrincipal LoginUserDetails loginUser) {

        ModelAndView mav = new ModelAndView();
        // ユーザー情報の全件取得
        List<UserForm> users = userService.findAll();

        // ユーザー管理画面表示
        mav.setViewName("user/management");
        mav.addObject("users", users);
        mav.addObject("loginUser", loginUser);
        return mav;

    }

    /*
     * ユーザー編集画面表示
     */
    @GetMapping("/user/edit/{id}")
    public ModelAndView userEdit(@AuthenticationPrincipal LoginUserDetails loginUser,
                                 @PathVariable String id,
                                 RedirectAttributes redirectAttributes) {
        List<String> errorMessages = new ArrayList<>();

        // ログインユーザーの部署チェック（総務人事部(=1)以外ならエラー）
        // 権限のないユーザーまたは未ログインユーザーがURLに直打ちしてアクセスしたときに排除する
        if (loginUser.getDepartmentId() != 1) {
            errorMessages.add("無効なアクセスです");
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/user/management");
        }

        // 取得したユーザーIDをチェック
        if (id == null || id.trim().isEmpty() || !id.matches("^[0-9]+$")) {
            errorMessages.add("不正なパラメータが入力されました");
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/user/management");
        }

        // 編集対象のレコードを取得
        UserForm user = userService.selectUserById(Integer.valueOf(id));

        // URLに存在しないIDを入力した場合のチェック
        if(user == null){
            errorMessages.add("不正なパラメータが入力されました");
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/user/management");
        }

        // 編集画面表示
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/edit");
        mav.addObject("formModel", user);
        mav.addObject("loginUser", loginUser);
        return mav;

    }

    /*
     * URLのIDを削除した場合のチェック
     */
    @GetMapping("/user/edit/")
    public ModelAndView deleteUserId(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessages","不正なパラメータが入力されました");
        return new ModelAndView("redirect:/user/management");
    }

    /*
     * ユーザー復活・停止機能
     */
    @PutMapping("/change/{id}")
    public ModelAndView isStoppedChange(@PathVariable Integer id,
                                        @RequestParam Integer isStopped) {

        UserForm user = userService.selectUserById(id);
        user.setIsStopped(isStopped);
        userService.saveUser(user);

        return new ModelAndView("redirect:/user/management");
    }

    /*
     * ユーザー編集処理
     */
    @PutMapping("/user/update/{id}")
    public ModelAndView updateUser(@AuthenticationPrincipal LoginUserDetails loginUser,
                                   @PathVariable Integer id,
                                   String confirmationPassword,
                                   @ModelAttribute("formModel") @Validated(value = {EditValidation.class}) UserForm userForm,
                                   BindingResult result) {

        // アカウントから既存レコードを取得
        User duplicationUser = userService.selectUserByAccount(userForm.getAccount());

        // アカウント重複チェック(同一アカウントが存在し、かつユーザーIDが一致しない場合 ＝ 重複)
        if(!(duplicationUser == null && duplicationUser.getId().equals(id))) {
            FieldError fieldError = new FieldError(result.getObjectName(),
                    "account", "アカウントが重複しています");
            result.addError(fieldError);
        }

        // パスワードとパスワード（確認用）の一致チェック
        if (userForm.getPassword() != null) {
            String password = userForm.getPassword();
            if (!password.matches(confirmationPassword)) {
                FieldError fieldError = new FieldError(result.getObjectName(),
                        "password", "パスワードと確認用パスワードが一致しません");
                result.addError(fieldError);
            }
        }

        // 支社と部署の組み合わせチェック
        if (userForm.getBranchId() != null && userForm.getDepartmentId() != null) {
            Map<String, Set<String>> allowedCombinations = Map.of(
                    "1", Set.of("1", "2"),
                    "2", Set.of("3", "4"),
                    "3", Set.of("3", "4"),
                    "4", Set.of("3", "4")
            );
            String branchId = userForm.getBranchId().toString();
            String departmentId = userForm.getDepartmentId().toString();
            if (!(allowedCombinations.containsKey(branchId) && allowedCombinations.get(branchId).contains(departmentId))) {
                FieldError fieldError = new FieldError(result.getObjectName(),
                        "branchId", "支社と部署の組み合わせが不正です");
                result.addError(fieldError);
            }
        }

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("loginUser", loginUser);
            mav.addObject("formModel", userForm);
            mav.setViewName("/user/edit");
            return mav;
        }

        userService.saveUser(userForm);

        return new ModelAndView("redirect:/user/management");
    }
}
