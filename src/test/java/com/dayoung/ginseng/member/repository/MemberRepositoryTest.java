package com.dayoung.ginseng.member.repository;

import com.dayoung.ginseng.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberDao memberRepository;


    @Test
    public void 멤버등록() throws Exception{
        //given
        Member member = new Member("M123456789", "choi1234", "maxx","1234");

        //when
        memberRepository.save(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findMemberByID("choi1234").get());

    }

    @Test
    public void 회원_아이디로_찾기() throws Exception{
        //given
        Member member = new Member("M123456789", "choi1234", "maxx","1234");
        memberRepository.save(member);

        //when
        Member foundMember = memberRepository.findMemberByID("choi1234").get();

        //then
        assertThat(member).isEqualTo(foundMember);

    }

    @Test
    public void 회원_아이디와_비밀번호로_찾기() throws Exception{
        //given
        Member member = new Member("M123456789", "choi1234", "maxx","1234");
        memberRepository.save(member);

        //when
        Member foundMember = memberRepository.findMemberByPasswordAndId(member).get();

        //then
        assertThat(member).isEqualTo(foundMember);

    }

}