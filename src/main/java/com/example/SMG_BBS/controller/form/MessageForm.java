package com.example.SMG_BBS.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MessageForm {

    private Integer id;

    @NotNull(message = "件名を入力してください")
    @Pattern(regexp = "(?s)^(?![\\s　]*$).+", message = "件名を入力してください") // 半角スペース・全角スペース・改行 のみ入力の場合バリデーションエラー
    @Size(min = 0, max = 30, message = "件名は30文字以内で入力してください")
    private String title;

    @NotNull(message = "本文を入力してください")
    @Pattern(regexp = "(?s)^(?![\\s　]*$).+", message = "本文を入力してください") // 半角スペース・全角スペース・改行 のみ入力の場合バリデーションエラー
    @Size(min = 0, max = 1000, message = "本文は1000文字以内で入力してください")
    private String text;

    @NotNull(message = "カテゴリを入力してください")
    @Pattern(regexp = "(?s)^(?![\\s　]*$).+", message = "カテゴリを入力してください") // 半角スペース・全角スペース・改行 のみ入力の場合バリデーションエラー
    @Size(min = 0, max = 10, message = "カテゴリは10文字以内で入力してください")
    private String category;

    private Integer userId;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
