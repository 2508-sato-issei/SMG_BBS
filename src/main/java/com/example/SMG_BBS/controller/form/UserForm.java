package com.example.SMG_BBS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserForm {

    private Integer id;

    private String account;

    private String password;

    private String name;

    private Integer branchId;

    private Integer departmentId;

    private byte isStopped;

    private Timestamp createdDate;

    private  Timestamp updatedDate;
}
