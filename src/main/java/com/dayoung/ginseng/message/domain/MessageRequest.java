package com.dayoung.ginseng.message.domain;

import com.dayoung.ginseng.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageRequest {

    @Id @GeneratedValue
    @Column(name = "message_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_id", referencedColumnName = "id")
    private Member me;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private Member friend;

    private String status;

    private LocalDateTime requestDate;

    public MessageRequest(Member me, Member friend, String status) {
        this.me = me;
        this.friend = friend;
        this.status = status;
    }
}
