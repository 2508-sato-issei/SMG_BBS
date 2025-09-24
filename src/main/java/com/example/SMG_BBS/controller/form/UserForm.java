package com.example.SMG_BBS.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserForm {

    private Integer id;

    @NotEmpty(message = "アカウントを入力してください")
    @Pattern(regexp = "^$|[a-zA-Z0-9]{6,20}$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    private String account;

    @NotEmpty(message = "パスワードを入力してください")
    @Pattern(regexp = "^$|[\\x21-\\x7E]{6,20}$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください")
    private String password;

    @NotEmpty(message = "氏名を入力してください")
    @Pattern(regexp = "^$|.*[^\\s　].*", message = "氏名を入力してください")
    @Size(min = 0, max = 10, message = "氏名は10文字以下で入力してください")
    private String name;

    @NotNull(message = "支社を選択してください")
    private Integer branchId;

    @NotNull(message = "部署を選択してください")
    private Integer departmentId;

    private byte isStopped;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
