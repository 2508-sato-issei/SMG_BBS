package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.repository.UserRepository;
import com.example.SMG_BBS.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /*
     * ユーザー情報一覧を取得
     */
    public List<UserForm> findAll() {
        List<User> results = userRepository.findAll();
        return setUserForm(results);
    }

    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();
        for(int i = 0; i < results.size(); i++){
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsStopped(result.getIsStopped());
            user.setCreatedDate(result.getCreatedDate().toLocalDateTime());
            user.setUpdatedDate(result.getUpdatedDate().toLocalDateTime());
            users.add(user);
        }
        return users;
    }

    /*
     * ユーザー編集画面でのユーザー情報を取得
     */
    public UserForm findUser(Integer id) {
        List<User> results = new ArrayList<>();
        results.add(userRepository.findById(id).orElse(null));
        List<UserForm> users = setUserForm(results);
        return users.get(0);
    }

    /*
     * ユーザー復活・停止フラグの更新
     */
    public void saveUser(UserForm userForm) {
        User user = setUserEntity(userForm);
        userRepository.save(user);
    }

    private User setUserEntity(UserForm reqUser) {
        User user = new User();
        user.setAccount(reqUser.getAccount());
        user.setName(reqUser.getName());
        user.setPassword(reqUser.getPassword());
        user.setBranchId(reqUser.getBranchId());
        user.setDepartmentId(reqUser.getDepartmentId());
        user.setIsStopped((byte)reqUser.getIsStopped());

        // 新規登録と更新を区別する（id == null 新規 / id != null 更新）
        if(reqUser.getId() != null) {
            user.setId(reqUser.getId());
            user.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        return user;


    }
}
