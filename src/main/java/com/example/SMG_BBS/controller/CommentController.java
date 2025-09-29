package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.CommentForm;
import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.security.LoginUserDetails;
import com.example.SMG_BBS.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    //コメント登録機能
    @PostMapping("/comment/add/{messageId}")
    public ModelAndView addComment(@AuthenticationPrincipal LoginUserDetails loginUser,
                                   @ModelAttribute("formModel") @Validated CommentForm commentForm,
                                   BindingResult result,
                                   @PathVariable Integer messageId,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session){

        if(result.hasErrors()){
            String text = commentForm.getText();
            String errorMessage = "";
            for(ObjectError error : result.getAllErrors()){
                errorMessage += error.getDefaultMessage();
            }

            redirectAttributes.addFlashAttribute("comment", text);
            redirectAttributes.addFlashAttribute("commentErrorMessage", errorMessage);
            redirectAttributes.addFlashAttribute("errorId", messageId);
            return new ModelAndView("redirect:/");
        }

        //セキュリティからuserIdを取得し、commentFormにセット
        commentForm.setUserId(loginUser.getId());

        //messageIdをcommentFormにセット
        commentForm.setMessageId(messageId);

        commentService.saveComment(commentForm);

        return new ModelAndView("redirect:/");
    }

    //コメント削除機能
    @DeleteMapping("/comment/delete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id){
        commentService.deleteComment(id);
        return new ModelAndView("redirect:/");
    }

}
