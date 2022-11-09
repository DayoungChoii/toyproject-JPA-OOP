package com.dayoung.ginseng.message.dto;

import com.dayoung.ginseng.message.domain.Message;
import lombok.Getter;

@Getter
public class SendMessageResponse {

    private String title;

    private String senderId;

    private String senderNickname;

    private String receiverId;

    private String receiverNickname;

    public SendMessageResponse(Message message) {
        this.title = message.getTitle();
        this.senderId = message.getSender().getId();
        this.senderNickname = message.getSender().getNickname();
        this.receiverId = message.getReceiver().getId();
        this.receiverNickname = message.getReceiver().getNickname();
    }
}
