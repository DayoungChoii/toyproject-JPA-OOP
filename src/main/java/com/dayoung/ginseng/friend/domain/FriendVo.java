package com.dayoung.ginseng.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendVo {

    private String myMemberId;

    private String friendId;

    private String status;
}
