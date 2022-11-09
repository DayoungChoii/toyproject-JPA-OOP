package com.dayoung.ginseng.message.repository;

import com.dayoung.ginseng.message.domain.Message;
import com.dayoung.ginseng.message.domain.MessageRequest;
import com.dayoung.ginseng.message.domain.MessageRequestStatus;
import com.dayoung.ginseng.message.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    final private EntityManager em;

    public int findReceivedCount(String myId) {
        return em.createQuery(
                "select count(m) from Message m where m.receiver.id = :myId", Message.class)
                .setParameter("myId", myId)
                .getFirstResult();
    }

    public int findSentCount(String myId) {
        return em.createQuery(
                "select count(m) from Message m where m.sender.id = :myId", Message.class)
                .setParameter("myId", myId)
                .getFirstResult();
    }

    public MessageRequest saveRequestMessage(MessageRequest messageRequest) {
        em.persist(messageRequest);
        return messageRequest;
    }

    public Message saveMessage(Message message) {
        em.persist(message);
        return message;
    }

    public List<Message> findAllReceivedList(String myId, int offset, int limit) {
        return em.createQuery(
                "select ms " +
                        "from Message ms " +
                        "join fetch ms.sender " +
                        "join fetch ms.receiver " +
                        "where ms.receiver.id = :myId", Message.class)
                .setParameter("myId", myId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Message> findAllSentList(String myId, int offset, int limit) {
        return em.createQuery(
                "select ms " +
                        "from Message ms " +
                        "join fetch ms.sender " +
                        "join fetch ms.receiver " +
                        "where ms.sender.id = :myId", Message.class)
                .setParameter("myId", myId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<MessageRequest> findAllRequestedMessage(String myId, int offset, int limit) {
        return em.createQuery(
                "select mr " +
                        "from MessageRequest mr " +
                        "join fetch mr.me " +
                        "join fetch mr.friend " +
                        "where mr.friend.id = :myId", MessageRequest.class)
                .setParameter("myId", myId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Message finOne(String messageId) {
        return em.find(Message.class, messageId);
    }
}
