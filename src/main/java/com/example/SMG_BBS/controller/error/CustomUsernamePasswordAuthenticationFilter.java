package com.example.SMG_BBS.controller.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

public class CustomUsernamePasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationServiceException {

        String account = obtainUsername(request);
        String password = obtainPassword(request);

        List<String> errorMessages = new ArrayList<>();

        if (account == null || account.trim().isBlank()) {
           errorMessages.add("アカウントを入力してください");
        }

        if(password == null || password.trim().isBlank()) {
            errorMessages.add("パスワードを入力してください");
        }

        if(!errorMessages.isEmpty()) {
            throw new AuthenticationServiceException(String.join("<br/>", errorMessages));
        }

        account = account.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(account, password);
        setDetails(request, authRequest);

        return this .getAuthenticationManager().authenticate(authRequest);
    }
}
