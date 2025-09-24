package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.MessageForm;
import com.example.SMG_BBS.repository.MessageRepository;
import com.example.SMG_BBS.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
     * 投稿取得＋絞り込み
     */
    public List<MessageForm> findMessage(){
        //絞り込み未実装（全件取得状態）
        List<Message> results = messageRepository.findAll();
        return setMessageForm(results);
    }

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
     * DBから取得したデータをFormに設定
     */
    private List<MessageForm> setMessageForm(List<Message> results) {
        List<MessageForm> messages = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            MessageForm message = new MessageForm();
            Message result = results.get(i);
            message.setId(result.getId());
            message.setTitle(result.getTitle());
            message.setText(result.getText());
            message.setCategory(result.getCategory());
            message.setUserId(result.getUserId());
            message.setCreatedDate(result.getCreatedDate());
            message.setUpdatedDate(result.getUpdatedDate());
            messages.add(message);
        }
        return messages;
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
