package com.dayoung.ginseng.friend.repository;

import com.dayoung.ginseng.friend.domain.FriendRelation;
import com.dayoung.ginseng.friend.domain.FriendStatus;
import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FriendRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Test
    public void 나와_친구가아닌_친구_목록_찾기() throws Exception{
        //given
        createMembers();
        Member me = memberRepository.findMemberByID("choi1").get();
        Member friend = memberRepository.findMemberByID("choi2").get();

        FriendRelation friendRelation = new FriendRelation(me, friend, FriendStatus.REQUEST.getShortStatus());
        friendRepository.saveFriendRelation(friendRelation);

        //when
        List<Member> memberList = friendRepository.findNonFriendMemberByIds("choi1", "choi3");

        //then
        assertThat(memberList.size()).isEqualTo(1);
        assertThat(memberList.get(0).getId()).isEqualTo("choi3");

    }

    @Test
    public void 친구_관계_저장_성공() throws Exception{
        //given
        createMembers();

        //new Member(), setId()로 새로운 객체를 만들려고 Member가 아직 영속되지 않아 오류가 생겼음
        //Member들을 영속시킨 후 friend_relation save 진행
        Member me = memberRepository.findMemberByID("choi1").get();
        Member friend = memberRepository.findMemberByID("choi2").get();

        FriendRelation friendRelation = new FriendRelation(me, friend, FriendStatus.REQUEST.getShortStatus());

        //when
        FriendRelation resultFriendRelation = friendRepository.saveFriendRelation(friendRelation);

        //then
        assertThat(resultFriendRelation).isEqualTo(friendRepository.findOne(resultFriendRelation.getId()));
    }


    @Test
    public void 상태에_따른_친구목록_불러오기() throws Exception{
        //given
        createMembers();

        Member me = memberRepository.findMemberByID("choi1").get();
        Member friend1 = memberRepository.findMemberByID("choi2").get();
        FriendRelation friendRelation1 = new FriendRelation(me, friend1, FriendStatus.REQUEST.getShortStatus());

        Member friend2 = memberRepository.findMemberByID("choi3").get();
        FriendRelation friendRelation2 = new FriendRelation(me, friend2, FriendStatus.ACCEPT.getShortStatus());

        friendRepository.saveFriendRelation(friendRelation1);
        friendRepository.saveFriendRelation(friendRelation2);

        //when
        List<FriendRelation> foundMember = friendRepository.findMyFriendRelationsByStatus("choi1", FriendStatus.REQUEST.getShortStatus());

        //then
        assertThat(foundMember.size()).isEqualTo(1);
        assertThat(foundMember.get(0).getFriend().getId()).isEqualTo("choi2");
    }

    @Test
    public void 나에게_친구추가한_멤버리스() throws Exception{
        //given
        createMembers();

        Member friend = memberRepository.findMemberByID("choi1").get();
        Member me = memberRepository.findMemberByID("choi2").get();
        FriendRelation friendRelation1 = new FriendRelation(me, friend, FriendStatus.REQUEST.getShortStatus());

        friendRepository.saveFriendRelation(friendRelation1);

        //when
        List<FriendRelation> foundMember = friendRepository.findRequestedFriend("choi1", FriendStatus.REQUEST.getShortStatus());

        //then
        assertThat(foundMember.size()).isEqualTo(1);
        assertThat(foundMember.get(0).getMe().getId()).isEqualTo("choi2");
    }

    private void createMembers() {
        Member member1 = new Member("M123456789", "choi1", "maxx1","1234");
        Member member2 = new Member("M1234567892", "choi2", "maxx2","1234");
        Member member3 = new Member("M1234567893", "choi3", "maxx3","1234");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

}
