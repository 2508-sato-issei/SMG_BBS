package com.example.SMG_BBS.controller.form;

import com.example.SMG_BBS.repository.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class UserMessageForm {
    private Integer id;
    private String title;
    private String text;
    private String category;
    private User user;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
