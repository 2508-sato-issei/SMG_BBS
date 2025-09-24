package com.example.SMG_BBS.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserForm {

    private Integer id;
    private String account;
    private String password;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private int isStopped;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
