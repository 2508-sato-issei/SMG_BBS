package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.MessageForm;
import com.example.SMG_BBS.security.LoginUserDetails;
import com.example.SMG_BBS.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/message/new")
    public ModelAndView newMessage(@AuthenticationPrincipal LoginUserDetails loginUser, Model model) {

        ModelAndView mav = new ModelAndView();

        if (!model.containsAttribute("formModel")) {
            MessageForm messageForm = new MessageForm();
            mav.addObject("formModel", messageForm);
        }

        mav.addObject("loginUser", loginUser);
        mav.setViewName("message/new");
        return mav;
    }

    /*
     * 投稿登録処理
     */
    @PostMapping("/message/add")
    public ModelAndView addMessage(@AuthenticationPrincipal LoginUserDetails loginUser,
                                   @ModelAttribute("formModel") @Valid MessageForm messageForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.formModel", result);
            redirectAttributes.addFlashAttribute("formModel", messageForm);
            return new ModelAndView("redirect:new");
        }

        Integer userId = loginUser.getId();
        messageForm.setUserId(userId);
        messageService.saveMessage(messageForm);
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/message/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id,
                                      @RequestParam Integer userId,
                                      @AuthenticationPrincipal LoginUserDetails loginUser,
                                      @ModelAttribute("formModel") MessageForm messageForm,
                                      RedirectAttributes redirectAttributes) {

        Integer loginUserId = loginUser.getId();

        // 削除権限判定
        if (!Objects.equals(userId, loginUserId)) {
            String errorMessage = "無効なアクセスです";
            redirectAttributes.addFlashAttribute(errorMessage);
            return new ModelAndView("redirect:/");
        }

        messageService.deleteMessage(id);
        return new ModelAndView("redirect:/");

    }

}
