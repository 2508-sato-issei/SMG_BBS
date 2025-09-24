package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.MessageForm;
import com.example.SMG_BBS.repository.MessageRepository;
import com.example.SMG_BBS.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
