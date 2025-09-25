package com.example.SMG_BBS.controller.form;

import com.example.SMG_BBS.repository.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class UserCommentForm {

    private Integer id;
    private String text;
    private User user;
    private Integer messageId;
    private Timestamp createdDate;
    private Timestamp updatedDate;

}
