package com.dayoung.ginseng.friend.domain;

import com.dayoung.ginseng.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRelation implements Serializable {

    @Id @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_id", referencedColumnName = "id")
    private Member me;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="friend_Id", referencedColumnName = "id")
    private Member friend;

    private String status;

    public FriendRelation(Member me, Member friend, String status) {
        this.me = me;
        this.friend = friend;
        this.status = status;
    }
}
