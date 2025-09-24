package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.MessageForm;
import com.example.SMG_BBS.repository.MessageRepository;
import com.example.SMG_BBS.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
     * レコード追加
     */
    public void saveMessage(MessageForm reqMessage) {
        Message saveMassage = setMessageEntity(reqMessage);
        messageRepository.save(saveMassage);
    }

    /*
     *  レコード削除
     */
    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }

    /*
     * リクエストから取得した情報をentityに設定
     */
    private Message setMessageEntity(MessageForm reqMessage) {
        Message message = new Message();
        message.setTitle(reqMessage.getTitle());
        message.setText(reqMessage.getText());
        message.setCategory(reqMessage.getCategory());
        message.setUserId(reqMessage.getUserId());

        if (reqMessage.getId() != null) {
            message.setId(reqMessage.getId());
            message.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        return message;
    }

}
