package com.dayoung.ginseng.friend.repository;

import com.dayoung.ginseng.friend.domain.FriendVo;
import com.dayoung.ginseng.member.domain.MemberVo;

import java.util.List;

public interface FriendRepository {
    List<MemberVo> findMember(FriendVo friendId);

    void requestFriend(FriendVo friendVo);

    List<FriendVo> findMyFriendsByStatus(FriendVo myMemberId);

    List<FriendVo> findRequestedFriend(FriendVo myMemberId);

    int acceptFriend(FriendVo myId);
}
