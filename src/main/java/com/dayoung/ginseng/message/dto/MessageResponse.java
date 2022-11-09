package com.dayoung.ginseng.message.dto;

import com.dayoung.ginseng.message.domain.Message;
import com.dayoung.ginseng.message.domain.MessageRequest;

public class MessageResponse {
    private String title;

    private String body;

    private String senderId;

    private String senderNickname;

    private String receiverId;

    private String receiverNickname;

    public MessageResponse(Message message) {
        this.title = message.getTitle();
        this.body = message.getBody();
        this.senderId = message.getSender().getId();
        this.senderNickname = message.getSender().getNickname();
        this.receiverId = message.getReceiver().getId();
        this.receiverNickname = message.getReceiver().getNickname();
    }


}
