package com.example.SMG_BBS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentForm {

    private Integer id;

    private String text;

    private Integer userId;

    private Integer messageId;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
