package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /*
     * ユーザー管理画面表示
     */
    @GetMapping("/user")
    public ModelAndView userManage(HttpSession session, RedirectAttributes redirectAttributes){

        // セッションからログインユーザ情報を取得
        UserForm user = (UserForm) session.getAttribute("loginUser");

        // ログインユーザーの部署チェック（総務人事部(=1)以外ならエラー）
        if(user.getDepartmentId() != 1){
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("無効なアクセスです");
            redirectAttributes.addFlashAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/");
        }

        ModelAndView mav = new ModelAndView();
        // ユーザー情報の全件取得
        List<UserForm> users = userService.findAll();

        // ユーザー管理画面表示
        mav.setViewName("/manage");
        mav.addObject("users", users);
        return mav;

    }

    /*
     * ユーザー編集画面表示
     */
    @GetMapping("/user/edit/{id}")
    public ModelAndView userEdit(@PathVariable String id,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes){
        List<String> errorMessages = new ArrayList<>();

        // セッションからログインユーザ情報を取得
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");

        // ログインユーザーの部署チェック（総務人事部(=1)以外ならエラー）
        // 権限のないユーザーまたは未ログインユーザーがURLに直打ちしてアクセスしたときに排除する
        if(loginUser.getDepartmentId() != 1){
            errorMessages.add("無効なアクセスです");
            redirectAttributes.addFlashAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/");
        }

        // 取得したユーザーIDをチェック
        if(id == null || id.trim().isEmpty() || !id.matches("^[0-9]+$")) {
            errorMessages.add("不正なパラメータが入力されました");
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/");
        }

        // 編集対象のレコードを取得
        UserForm user = userService.findUser(Integer.valueOf(id));

        // 編集画面表示
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/user/edit");
        mav.addObject("formModel", user);
        return mav;

    }

    /*
     * ユーザー編集処理
     */
    @PutMapping("/user/update/{id}")
    public ModelAndView updateUser(@PathVariable Integer id,
                                   @ModelAttribute("formModel") UserForm userForm,
                                   RedirectAttributes redirectAttributes) {

    }
}
