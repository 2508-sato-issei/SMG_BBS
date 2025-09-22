package com.example.SMG_BBS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentForm {

    private int id;

    private String text;

    private int userId;

    private int messageId;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
