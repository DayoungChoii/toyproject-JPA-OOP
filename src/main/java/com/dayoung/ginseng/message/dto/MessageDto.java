package com.dayoung.ginseng.message.dto;

import com.dayoung.ginseng.message.domain.Message;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class MessageDto {

    private String title;
    private String body;
    private String senderId;
    private String senderNickname;
    private String receiverId;
    private String receiverNickname;
    private LocalDateTime regDate;

    public MessageDto(Message message) {
        this.title = message.getTitle();
        this.body = message.getBody();
        this.senderId = message.getSender().getId();
        this.senderNickname = message.getSender().getNickname();
        this.receiverId = message.getReceiver().getId();
        this.receiverNickname = message.getReceiver().getNickname();
        this.regDate = message.getRegDate();
    }
}
