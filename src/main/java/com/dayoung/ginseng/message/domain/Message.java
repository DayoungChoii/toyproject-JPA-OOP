package com.dayoung.ginseng.message.domain;

import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.message.dto.SendMessageRequest;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Message {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Member receiver;

    private LocalDateTime regDate;

    private String body;

    private String title;

    public Message(Member sender, Member receiver, SendMessageRequest messageRequest) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = messageRequest.getTitle();
        this.body = messageRequest.getBody();
    }
}
