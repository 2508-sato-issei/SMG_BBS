package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.repository.UserRepository;
import com.example.SMG_BBS.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * レコード追加
     */
    public void saveUser(UserForm reqUser) {
        User saveUser = setUserEntity(reqUser);
        userRepository.save(saveUser);
    }

    /*
     * アカウント重複チェック用
     */
        public User selectUserByAccount(String account) {
            return userRepository.findByAccount(account).orElse(null);
        }

    /*
     * リクエストから取得した情報をentityに設定
     */
    private User setUserEntity(UserForm reqUser) {
        User user = new User();
        user.setAccount(reqUser.getAccount());
        user.setPassword(reqUser.getPassword());
        user.setName(reqUser.getName());
        user.setBranchId(reqUser.getBranchId());
        user.setDepartmentId(reqUser.getDepartmentId());

        if (reqUser.getId() != null) {
            user.setId(reqUser.getId());
            user.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        return user;
    }

}
