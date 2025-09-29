package com.example.SMG_BBS.controller.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        List<String> errorMessages = new ArrayList<>();
        String account = request.getParameter("username");
        String password = request.getParameter("password");
        boolean hasInputError = false;

        if (account == null || account.isBlank()) {
            errorMessages.add("アカウントを入力してください");
            hasInputError = true;
        }
        if (password == null || password.isBlank()) {
            errorMessages.add("パスワードを入力してください");
            hasInputError = true;
        }

        if (!hasInputError) {
            if (exception instanceof UsernameNotFoundException) {
                errorMessages.add("ログインに失敗しました");
            } else if (exception instanceof BadCredentialsException) {
                errorMessages.add("ログインに失敗しました");
            } else if (exception instanceof DisabledException) {
                errorMessages.add("ログインに失敗しました");
            }
        }

        request.getSession().setAttribute("account", account);
        request.getSession().setAttribute("errorMessages", errorMessages);
        response.sendRedirect(request.getContextPath() + "/login");
    }

}
