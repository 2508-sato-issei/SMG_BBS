package com.example.SMG_BBS.security;

import com.example.SMG_BBS.repository.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class LoginUserDetails implements UserDetails {

    private final Integer id;
    private final String account;
    private final String password;
    private final Integer branchId;
    private final Integer departmentId;
    private final Collection<? extends GrantedAuthority> authorities;
    private final User user;

    public LoginUserDetails(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.password = user.getPassword();
        this.branchId = user.getBranchId();
        this.departmentId = user.getDepartmentId();
        this.user = user;

        if (user.getDepartmentId() == 1) {
            // 部署IDが1(総務人事部)なら、管理者権限を付与
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), // 管理者権限
                    new SimpleGrantedAuthority("ROLE_USER") // 一般ユーザー
            );
        } else {
            this.authorities = List.of(new SimpleGrantedAuthority("ROLE_USER")); // 一般ユーザー
        }
    }

    // ロールのコレクションを返す
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // ログイン名を返す
    @Override
    public String getUsername() {
        return account;
    }

    // パスワードを返す
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        if (user.getIsStopped() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
