package com.dayoung.ginseng.member.repository;

import com.dayoung.ginseng.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class MemberRepository implements MemberDao {

    private final EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Optional<Member> findMemberByID(String id) {
        return em.createQuery("select m from Member m where m.id = :id")
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findAny();

    }

    @Override
    public Optional<Member> findMemberByPasswordAndId(Member member) {
        return em.createQuery("select m from Member m where m.id = :id and m.password = :password")
                .setParameter("id", member.getId())
                .setParameter("password", member.getPassword())
                .getResultList()
                .stream()
                .findAny();
    }



}
