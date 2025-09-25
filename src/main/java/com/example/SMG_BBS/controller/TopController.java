package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.controller.form.UserMessageForm;
import com.example.SMG_BBS.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TopController {

    @Autowired
    MessageService messageService;

    /*
     * Top画面表示
     */
    @GetMapping
    public ModelAndView top(HttpSession session,
                            @RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            @RequestParam(required = false) String category){
        ModelAndView mav = new ModelAndView();

        UserForm loginUser = new UserForm();
        loginUser.setId(1);
        loginUser.setAccount("master1");
        loginUser.setPassword("root");
        loginUser.setName("総務人事部");
        loginUser.setBranchId(1);
        loginUser.setDepartmentId(1);
        loginUser.setIsStopped(0);
        session.setAttribute("loginUser", loginUser);


//        //loginUserの部署IDが総務人事部ならばボタン表示フラグON
//        boolean isShowButton = false;
//        UserForm user = (UserForm)session.getAttribute("loginUser");
//        if(user.getDepartmentId() == 1){
//            isShowButton = true;
//        }

        List<UserMessageForm> messages = messageService.findMessage(startDate, endDate, category);

        mav.setViewName("/top");
//        mav.addObject("isShowButton", isShowButton);
        mav.addObject("messages", messages);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("category", category);
        return mav;
    }

    // ログアウト機能
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {

        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}
