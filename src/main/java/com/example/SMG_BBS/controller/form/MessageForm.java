package com.example.SMG_BBS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MessageForm {

    private int id;

    private String title;

    private String text;

    private String category;

    private int userId;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
