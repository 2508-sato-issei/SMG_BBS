package com.example.SMG_BBS.service;

import com.example.SMG_BBS.controller.form.UserForm;
import com.example.SMG_BBS.repository.UserRepository;
import com.example.SMG_BBS.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /*
     * レコード追加・更新
     */
    public void saveUser(UserForm reqUser) {

        boolean isNewUser = reqUser.getId() == null;
        boolean changeIsStopped = false;

        User dbUser = null;
        if (!isNewUser) {
            dbUser = userRepository.findById(reqUser.getId()).orElse(null);
        }
        // ユーザー停止状態変更有無判定
        if (dbUser != null && dbUser.getIsStopped() != reqUser.getIsStopped()) {
            changeIsStopped = true;
        }

        // 新規ユーザー または ユーザー更新で新規パスワード入力ありの場合、パスワードを暗号化
        if (isNewUser || !reqUser.getPassword().isBlank() && !changeIsStopped) {
            String rawPassword = reqUser.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            reqUser.setPassword(encodedPassword);
        } else {
            User user = userRepository.findById(reqUser.getId()).orElse(null);
            reqUser.setPassword(user.getPassword());
        }

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
        user.setIsStopped((byte) reqUser.getIsStopped());

        if (reqUser.getId() != null) {
            user.setId(reqUser.getId());
            user.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        }
        return user;
    }

    /*
     * ユーザー情報一覧を取得
     */
    public List<UserForm> findAll() {
        List<User> results = userRepository.findAllByOrderByIdAsc();
        return setUserForm(results);
    }

    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
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
    public UserForm selectUserById(Integer id) {

        User userResult = userRepository.findById(id).orElse(null);

        // レコードの存在チェック（存在しないIDをURLに直接入力した場合のバリデーション）
        if(userResult == null){
            return null;
        } else {
            List<User> results = new ArrayList<>();
            results.add(userResult);
            List<UserForm> users = setUserForm(results);
            return users.get(0);
        }
    }
}
