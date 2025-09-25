package com.example.SMG_BBS.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentForm {

    private Integer id;

    @NotNull(message = "メッセージを入力してください")
    @Pattern(regexp = "(?s)^(?![\\s　]*$).+", message = "本文を入力してください") // 半角スペース・全角スペース・改行 のみ入力の場合バリデーションエラー
    @Size(max = 500, message = "500文字以内で入力してください")
    private String text;

    private Integer userId;

    private Integer messageId;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
