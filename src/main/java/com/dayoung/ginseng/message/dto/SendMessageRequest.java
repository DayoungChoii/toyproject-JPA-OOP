package com.dayoung.ginseng.message.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class SendMessageRequest {

    private String title;

    private String body;

    private String myId;

    private String friendId;
}
