package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.MessageForm;
import com.example.SMG_BBS.controller.form.UserMessageForm;
import com.example.SMG_BBS.repository.MessageRepository;
import com.example.SMG_BBS.repository.entity.Message;
import com.example.SMG_BBS.repository.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
     * 投稿取得＋絞り込み
     */
    public List<UserMessageForm> findMessage(String startDate, String endDate, String category) {
        List<Message> results;
        Date date = new Date();
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 開始日の設定
        String startTime;
        if (!StringUtils.isBlank(startDate)) {
            startTime = startDate + " 00:00:00";
        } else {
            startTime = "2022-01-01 00:00:00";
        }
        Timestamp start = Timestamp.valueOf(startTime);
        // 終了日の設定
        String endTime;
        if (!StringUtils.isBlank(endDate)) {
            endTime = endDate + " 23:59:59";
        } else {
            endTime = fmt.format(date);
        }
        Timestamp end = Timestamp.valueOf(endTime);

        if (StringUtils.isBlank(startDate)
                && StringUtils.isBlank(endDate)
                && StringUtils.isBlank(category)) {
            results = messageRepository.findAllByOrderByCreatedDateDesc();
        } else if (!StringUtils.isBlank(category)) {
            results = messageRepository.findMessageByCategory(start, end, category);
        } else {
            //絞り込み(日付のみ)
            results = messageRepository.findAllMessage(start, end);
        }

        return setUserMessageForm(results);
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
    private List<UserMessageForm> setUserMessageForm(List<Message> results) {
        List<UserMessageForm> messages = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserMessageForm message = new UserMessageForm();
            Message result = results.get(i);
            message.setId(result.getId());
            message.setTitle(result.getTitle());
            message.setText(result.getText());
            message.setCategory(result.getCategory());
            message.setUser(result.getUser());
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
        User user = new User();
        user.setId(reqMessage.getUserId());
        Message message = new Message();
        message.setTitle(reqMessage.getTitle());
        message.setText(reqMessage.getText());
        message.setCategory(reqMessage.getCategory());
        message.setUser(user);

        if (reqMessage.getId() != null) {
            message.setId(reqMessage.getId());
            message.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        return message;
    }
}
