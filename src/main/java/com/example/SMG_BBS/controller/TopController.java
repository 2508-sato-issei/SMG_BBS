package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.MessageForm;
import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.repository.entity.Message;
import com.example.SMG_BBS.repository.entity.User;
import com.example.SMG_BBS.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ModelAndView top(HttpSession session){
        ModelAndView mav = new ModelAndView();

//        //loginUserの部署IDが総務人事部ならばボタン表示フラグON
//        boolean isShowButton = false;
//        UserForm user = (UserForm)session.getAttribute("loginUser");
//        if(user.getDepartmentId() == 1){
//            isShowButton = true;
//        }

        List<MessageForm> messages = messageService.findMessage();

        mav.setViewName("/top");
//        mav.addObject("isShowButton", isShowButton);
        mav.addObject("messages", messages);
        return mav;
    }
}
