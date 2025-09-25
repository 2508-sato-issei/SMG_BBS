package com.example.SMG_BBS.filter;

import com.example.SMG_BBS.repository.entity.User;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class LoginFilter implements Filter {

    @Autowired
    HttpSession httpSession;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //ServletRequest→「Http」ServletRequestへ型変換
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        User user = (User) session.getAttribute("loginUser");

        //ログインしていない（loginUserがnull)の場合はログイン画面を表示
        if(httpSession != null && user != null) {
            chain.doFilter(request, response);
        } else {
            httpSession = httpRequest.getSession(true);
            session.setAttribute("errorMessages", "ログインしてください");
            //当初要求されたページではないのでRedirect
            httpResponse.sendRedirect("/login");
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
