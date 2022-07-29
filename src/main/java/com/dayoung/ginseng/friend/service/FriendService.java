package com.dayoung.ginseng.friend.service;

import com.dayoung.ginseng.friend.domain.FriendStatus;
import com.dayoung.ginseng.friend.domain.FriendVo;
import com.dayoung.ginseng.friend.repository.FriendRepository;
import com.dayoung.ginseng.member.domain.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public List<MemberVo> findMember(String mymemberId, String friendId) {
        FriendVo friendVo = new FriendVo();
        friendVo.setMyMemberId(mymemberId);
        friendVo.setFriendId(friendId);

        return friendRepository.findMember(friendVo);
    }

    @Transactional
    public void requestFriend(String friendId, String myMemberId) throws Exception {
        FriendVo friendVo = new FriendVo(myMemberId, friendId, FriendStatus.REQUEST.getShortStatus());
        friendRepository.requestFriend(friendVo);
    }

    public List<FriendVo> findMyFriendAll(String myMemberId) {
        FriendVo friendVo = new FriendVo();
        friendVo.setMyMemberId(myMemberId);
        friendVo.setStatus(FriendStatus.ACCEPT.getShortStatus());
        return friendRepository.findMyFriendsByStatus(friendVo);
    }


    public List<FriendVo> findRequestedFriend(String myMemberId){
        FriendVo friendVo = new FriendVo();
        friendVo.setMyMemberId(myMemberId);
        friendVo.setStatus(FriendStatus.REQUEST.getShortStatus());
        return friendRepository.findRequestedFriend(friendVo);
    }

    public List<FriendVo> findRequestSendFriend(String myMemberId) {
        FriendVo friendVo = new FriendVo();
        friendVo.setMyMemberId(myMemberId);
        friendVo.setStatus(FriendStatus.REQUEST.getShortStatus());
        return friendRepository.findMyFriendsByStatus(friendVo);
    }

    @Transactional
    public int acceptFriend(String myMemberId, String friendId) {
        FriendVo friendVo = new FriendVo();
        friendVo.setMyMemberId(myMemberId);
        friendVo.setFriendId(friendId);
        friendVo.setStatus(FriendStatus.ACCEPT.getShortStatus());
        return friendRepository.acceptFriend(friendVo);

    }
}
