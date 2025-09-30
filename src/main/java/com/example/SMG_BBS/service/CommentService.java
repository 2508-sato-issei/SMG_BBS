package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.CommentForm;
import com.example.SMG_BBS.controller.form.UserCommentForm;
import com.example.SMG_BBS.repository.CommentRepository;
import com.example.SMG_BBS.repository.entity.Comment;
import com.example.SMG_BBS.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    // レコード取得
    public List<UserCommentForm> findComment() {
        List<Comment> results;
        results = commentRepository.findAllComment();
        return setUserCommentForm(results);
    }

    //レコード追加
    public void saveComment(CommentForm commentForm) {
        Comment comment = setCommentEntity(commentForm);
        commentRepository.save(comment);
    }

    //レコード削除
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    // DBから取得したデータをFormに設定
    private List<UserCommentForm> setUserCommentForm(List<Comment> results) {
        List<UserCommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserCommentForm comment = new UserCommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setText(result.getText());
            comment.setUser(result.getUser());
            comment.setMessageId(result.getMessageId());
            comment.setCreatedDate(result.getCreatedDate());
            comment.setUpdatedDate(result.getUpdatedDate());
            comments.add(comment);
        }
        return comments;
    }

    //Formの情報をEntityに詰め替え
    private Comment setCommentEntity(CommentForm reqComment) {
        User user = new User();
        user.setId(reqComment.getUserId());
        Comment comment = new Comment();
        comment.setText(reqComment.getText());
        comment.setUser(user);
        comment.setMessageId(reqComment.getMessageId());

        return comment;
    }
}
