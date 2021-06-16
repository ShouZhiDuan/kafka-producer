package com.kafka.producer.controller;

import com.alibaba.fastjson.JSON;
import com.kafka.producer.DTO.DataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Auther: ShouZhi@Duan
 * @Description:
 */
@Slf4j
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
    public void session(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        System.err.println("SessionId = " + session.getId());
    }

//    @PostMapping(value = "/body",produces = "")
//    public Object testMsg(@RequestBody DataDTO dto, HttpServletRequest request){
//        log.info(JSON.toJSONString(dto));
//        StringBuilder sb = new StringBuilder();
//        InputStream inputStream = null;
//        BufferedReader reader = null;
//        try {
//            inputStream = request.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        log.info("BODY1：" + sb.toString().trim());
//        return dto;
//    }

    @GetMapping("/get")
    public Object testMsg(String name, HttpServletRequest request) {
        log.info(name);
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("BODY2：" + sb.toString().trim());
        return sb.toString().trim();
    }

}
