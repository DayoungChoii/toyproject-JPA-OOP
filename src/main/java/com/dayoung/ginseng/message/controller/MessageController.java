package com.dayoung.ginseng.message.controller;

import com.dayoung.ginseng.message.domain.MessageVo;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    // 받은 메시지 수, 보낸 메시지 수 get
    @GetMapping
    @RequestMapping("/messagesCount")
    public Map getMessagesCount(HttpServletRequest request){

    }

    // 메시지요청 get
    @GetMapping
    @RequestMapping("/request")
    public HttpEntity needMessage(HttpServletRequest request) {

    }

    // 메시지 작성 전송 post
    @PostMapping
    public HttpEntity sendMessage(@RequestBody MessageVo messageVo){

    }

    // 받은 메시지 목록 get
    @GetMapping
    @RequestMapping("received-list")
    public ArrayList<MessageVo> receivedList(HttpServletRequest request) {
        return "";
    }

    // 보낸 메시지 목록 get
    @GetMapping
    @RequestMapping("sent-list")
    public ArrayList<MessageVo> sentList(HttpServletRequest request) {
        return "";
    }

    // 요청받은 메시지 목록 get
    @GetMapping
    @RequestMapping("requested-list")
    public ArrayList<MessageVo> requestedList(HttpServletRequest request) {
        return "";
    }
}
