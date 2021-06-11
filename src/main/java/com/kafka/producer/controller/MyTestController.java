package com.kafka.producer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class MyTestController {

    @GetMapping("/addCookie")
    public void addCokie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = new Cookie("name", "dsz");
        Cookie cookie1 = new Cookie("name1", "dsz1");
        cookie1.setMaxAge(30);
        cookie.setSecure(true);
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.getWriter().print(cookies);
     }

    @GetMapping("/session")
    public void session(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        System.err.println("SessionId = " + session.getId());
    }

}
