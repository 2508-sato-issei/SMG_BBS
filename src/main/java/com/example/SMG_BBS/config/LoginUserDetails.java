package com.example.SMG_BBS.config;

import com.example.SMG_BBS.repository.entity.User;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@EqualsAndHashCode
public class LoginUserDetails implements UserDetails {

    private final String account;
    private final String password;
    private final Collection <? extends GrantedAuthority> authorities;

    public LoginUserDetails(User user) {
        this.account = user.getAccount();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getDepartmentId().toString().split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();
    }

    // ロールのコレクションを返す
    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
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
    public boolean isAccountNonExpired() {
        //  ユーザーが期限切れでなければtrueを返す
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //  ユーザーがロックされていなければtrueを返す
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //  パスワードが期限切れでなければtrueを返す
        return true;
    }

    @Override
    public boolean isEnabled() {
        //  ユーザーが有効ならtrueを返す
        return true;
    }
}
