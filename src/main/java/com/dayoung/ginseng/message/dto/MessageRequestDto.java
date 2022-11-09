package com.dayoung.ginseng.message.dto;

import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.message.domain.MessageRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageRequestDto {

    private Long id;
    private String friendId;
    private String friendNickname;
    private LocalDateTime requestDate;
    private String status;

    public MessageRequestDto(MessageRequest messageRequest) {
        id = messageRequest.getId();
        friendId = messageRequest.getFriend().getId();
        friendNickname = messageRequest.getFriend().getNickname();
        requestDate = messageRequest.getRequestDate();
        status = messageRequest.getStatus();
    }
}
