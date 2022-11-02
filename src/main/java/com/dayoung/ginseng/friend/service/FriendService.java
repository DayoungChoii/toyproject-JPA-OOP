package com.dayoung.ginseng.friend.service;

import com.dayoung.ginseng.friend.domain.FriendRelation;
import com.dayoung.ginseng.friend.domain.FriendStatus;
import com.dayoung.ginseng.friend.repository.FriendRepository;
import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public List<Member> searchNonFriends(String myId, String friendId) {
        return friendRepository.findNonFriendMemberByIds(myId, friendId);
    }

    @Transactional
    public FriendRelation requestFriend(String friendId, String myId) throws Exception {
        Member me = memberRepository.findMemberByID(myId).get();
        Member friend = memberRepository.findMemberByID(friendId).get();

        FriendRelation friendRelation = new FriendRelation(me, friend, FriendStatus.REQUEST.getShortStatus());
        return friendRepository.saveFriendRelation(friendRelation);
    }

    public List<Member> findMyFriendAll(String myId) {
        return friendRepository.findMyFriendRelationsByStatus(myId, FriendStatus.ACCEPT.getShortStatus())
                .stream()
                .map(friendRelation -> friendRelation.getFriend())
                .collect(Collectors.toList());
    }


    public List<Member> findRequestedFriend(String myId){
        return friendRepository.findRequestedFriend(myId, FriendStatus.REQUEST.getShortStatus())
                .stream()
                .map(friendRelation -> friendRelation.getMe())
                .collect(Collectors.toList());
    }

    public List<Member> findRequestSendFriend(String myId) {
        return friendRepository.findMyFriendRelationsByStatus(myId, FriendStatus.REQUEST.getShortStatus())
                .stream()
                .map(friendRelation -> friendRelation.getFriend())
                .collect(Collectors.toList());
    }

    @Transactional
    public FriendRelation acceptFriend(String myId, String friendId) {
        FriendRelation foundRelation = friendRepository.findOneByIds(myId, friendId);
        foundRelation.setStatus(FriendStatus.ACCEPT.getShortStatus());
        return foundRelation;
    }
}