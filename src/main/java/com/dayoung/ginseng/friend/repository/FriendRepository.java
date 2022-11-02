package com.dayoung.ginseng.friend.repository;

import com.dayoung.ginseng.friend.domain.FriendRelation;
import com.dayoung.ginseng.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendRepository {

    private final EntityManager em;

    public List<Member> findNonFriendMemberByIds(String myId, String friendId) { //jpql
        return em.createQuery("select m " +
                                        "from Member m " +
                                        "where m.id not in (select f.me.id from FriendRelation f where f.me.id = :myId) " +
                                        "and m.id like concat('%', :friendId, '%') ", Member.class)
                .setParameter("myId", myId)
                .setParameter("friendId", friendId)
                .getResultList();
    }

    public FriendRelation saveFriendRelation(FriendRelation friendRelation) {
        em.persist(friendRelation);
        return friendRelation;
    }

    public List<FriendRelation> findMyFriendRelationsByStatus(String myId, String status) {
        return em.createQuery("select fr from FriendRelation fr join fetch fr.friend where fr.me.id = :myId and fr.status = :status", FriendRelation.class)
                .setParameter("myId", myId)
                .setParameter("status", status)
                .getResultList();

    }

    public List<FriendRelation> findRequestedFriend(String myId, String status) {
        return em.createQuery("select fr from FriendRelation fr join fetch fr.friend where fr.friend.id = :myId and fr.status = :status", FriendRelation.class)
                .setParameter("myId", myId)
                .setParameter("status", status)
                .getResultList();
    }

    public FriendRelation findOne(Long id){
        return em.find(FriendRelation.class, id);
    }

    public FriendRelation findOneByIds(String myId, String friendId){
        return em.createQuery("select fr from FriendRelation fr where fr.me.id = :myId and fr.friend.id = :friendId", FriendRelation.class)
                    .setParameter("myId", myId)
                    .setParameter("friendId", friendId)
                    .getResultList()
                    .stream()
                    .findAny().get();
    }
}
