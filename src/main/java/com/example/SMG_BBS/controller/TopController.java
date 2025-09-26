package com.example.SMG_BBS.controller;

import com.example.SMG_BBS.controller.form.CommentForm;
import com.example.SMG_BBS.controller.form.UserCommentForm;
import com.example.SMG_BBS.controller.form.UserMessageForm;
import com.example.SMG_BBS.security.LoginUserDetails;
import com.example.SMG_BBS.service.CommentService;
import com.example.SMG_BBS.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TopController {

    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;

    /*
     * Top画面表示
     */
    @GetMapping
    public ModelAndView top(@AuthenticationPrincipal LoginUserDetails loginUser,
                            HttpSession session,
                            @RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            @RequestParam(required = false) String category,
                            Model model) {
        ModelAndView mav = new ModelAndView();

        //loginUserの部署IDが総務人事部ならばボタン表示フラグON
        boolean isShowButton = false;
        if (loginUser.getDepartmentId() == 1) {
            isShowButton = true;
        }

        // 投稿情報取得
        List<UserMessageForm> messages = messageService.findMessage(startDate, endDate, category);

        // コメント情報取得
        List<UserCommentForm> comments = commentService.findComment();

        //modelにformModelが存在しないとき空のcommentFormをviewに渡す
        if (!model.containsAttribute("formModel")) {
            CommentForm commentForm = new CommentForm();
            mav.addObject("formModel", commentForm);
        }

        // 管理者権限フィルターのエラーメッセージ処理
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            mav.addObject("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        mav.setViewName("top");
        mav.addObject("isShowButton", isShowButton);
        mav.addObject("messages", messages);
        mav.addObject("comments", comments);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("category", category);
        return mav;
    }

}
