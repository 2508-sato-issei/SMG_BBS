package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.repository.entity.User;
import com.example.SMG_BBS.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
            if (!allowedCombinations.containsKey(branchId) && allowedCombinations.get(branchId).contains(departmentId)) {
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
        return new ModelAndView("redirect:manage");
    }

}
