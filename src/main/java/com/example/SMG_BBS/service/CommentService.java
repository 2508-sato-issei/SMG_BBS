package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.CommentForm;
import com.example.SMG_BBS.repository.CommentRepository;
import com.example.SMG_BBS.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionException;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    //レコード追加
    public void saveComment(CommentForm commentForm){
        Comment comment = setCommentEntity(commentForm);
        commentRepository.save(comment);
    }

    //レコード削除
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
    }

    //Formの情報をEntityに詰め替え
    private Comment setCommentEntity(CommentForm commentForm){
        Comment comment = new Comment();
        comment.setText(commentForm.getText());
        comment.setUserId(commentForm.getUserId());
        comment.setMessageId(commentForm.getMessageId());

        return comment;
    }
}
