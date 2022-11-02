package com.dayoung.ginseng.friend.service;

import com.dayoung.ginseng.friend.domain.FriendRelation;
import com.dayoung.ginseng.friend.domain.FriendStatus;
import com.dayoung.ginseng.friend.repository.FriendRepository;
import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @InjectMocks
    private FriendService friendService;

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void 친구가_아닌_사용자_검색() throws Exception{
        //given
        List<Member> memberList = new ArrayList();
        memberList.add(new Member("M123456789", "choi1", "maxx", "1234"));
        doReturn(memberList).when(friendRepository).findNonFriendMemberByIds(anyString(), anyString());

        //when
        List<Member> resultMemberList = friendService.searchNonFriends("park1", "choi1");

        //then
        assertThat(resultMemberList.size()).isEqualTo(1);
        assertThat(resultMemberList.get(0).getId()).isEqualTo("choi1");
    }

    @Test
    public void 친구_요청_성공() throws Exception {
        //given
        Member me = new Member("M123456789", "choi1", "maxx", "1234");
        Member friend = new Member("M123456781", "park1", "ggoyo", "1234");
        FriendRelation friendRelation = new FriendRelation(123456789L, me, friend, FriendStatus.REQUEST.getShortStatus());

        doReturn(Optional.of(me)).when(memberRepository).findMemberByID("choi1");
        doReturn(Optional.of(friend)).when(memberRepository).findMemberByID("park1");
        doReturn(friendRelation).when(friendRepository).saveFriendRelation(any(FriendRelation.class));

        //when
        FriendRelation resultFriendRelation = friendService.requestFriend("park1", "choi1");

        //then
        assertThat(resultFriendRelation.getId()).isNotNull();
    }

    @Test
    public void 내_친구목록_찾기() throws Exception{
        //given
        Member me = new Member("M123456789", "choi1", "maxx", "1234");
        Member friend1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member friend2 = new Member("M123456782", "lee1", "luv", "1234");
        FriendRelation friendRelation1 = new FriendRelation(123456789L, me, friend1, FriendStatus.ACCEPT.getShortStatus());
        FriendRelation friendRelation2 = new FriendRelation(123456781L, me, friend2, FriendStatus.ACCEPT.getShortStatus());

        List<FriendRelation> friendRelationList = new ArrayList<>();
        friendRelationList.add(friendRelation1);
        friendRelationList.add(friendRelation2);

        doReturn(friendRelationList)
                .when(friendRepository)
                .findMyFriendRelationsByStatus("choi1", FriendStatus.ACCEPT.getShortStatus());

        //when
        List<Member> resultList = friendService.findMyFriendAll("choi1");

        //then
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.get(0)).isEqualTo(friend1);
        assertThat(resultList.get(1)).isEqualTo(friend2);

    }

    @Test
    public void 나에게_친구요청_보낸_친구목록() throws Exception{
        //given
        Member friend = new Member("M123456789", "choi1", "maxx", "1234");
        Member me1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member me2 = new Member("M123456782", "lee1", "luv", "1234");
        FriendRelation friendRelation1 = new FriendRelation(123456789L, me1, friend, FriendStatus.REQUEST.getShortStatus());
        FriendRelation friendRelation2 = new FriendRelation(123456781L, me2, friend, FriendStatus.REQUEST.getShortStatus());

        List<FriendRelation> friendRelationList = new ArrayList<>();
        friendRelationList.add(friendRelation1);
        friendRelationList.add(friendRelation2);

        doReturn(friendRelationList)
                .when(friendRepository)
                .findRequestedFriend("choi1", FriendStatus.REQUEST.getShortStatus());

        //when
        List<Member> resultList = friendService.findRequestedFriend("choi1");

        //then
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.get(0)).isEqualTo(me1);
        assertThat(resultList.get(1)).isEqualTo(me2);
    }

    @Test
    public void 내가낸_보_친구요낸_친구목록() throws Exception{
        //given
        Member me = new Member("M123456789", "choi1", "maxx", "1234");
        Member friend1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member friend2 = new Member("M123456782", "lee1", "luv", "1234");
        FriendRelation friendRelation1 = new FriendRelation(123456789L, me, friend1, FriendStatus.REQUEST.getShortStatus());
        FriendRelation friendRelation2 = new FriendRelation(123456781L, me, friend2, FriendStatus.REQUEST.getShortStatus());

        List<FriendRelation> friendRelationList = new ArrayList<>();
        friendRelationList.add(friendRelation1);
        friendRelationList.add(friendRelation2);

        doReturn(friendRelationList)
                .when(friendRepository)
                .findMyFriendRelationsByStatus("choi1", FriendStatus.REQUEST.getShortStatus());

        //when
        List<Member> resultList = friendService.findRequestSendFriend("choi1");

        //then
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.get(0)).isEqualTo(friend1);
        assertThat(resultList.get(1)).isEqualTo(friend2);
    }
}