package com.dayoung.ginseng.member.repository;

import com.dayoung.ginseng.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDao {

    void save(Member member);

    Optional<Member> findMemberByID(String id);

    Optional<Member> findMemberByPasswordAndId(Member member);
}

