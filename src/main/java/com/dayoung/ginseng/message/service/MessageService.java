package com.dayoung.ginseng.message.service;

import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.repository.MemberRepository;
import com.dayoung.ginseng.message.domain.Message;
import com.dayoung.ginseng.message.domain.MessageRequest;
import com.dayoung.ginseng.message.domain.MessageRequestStatus;
import com.dayoung.ginseng.message.dto.*;
import com.dayoung.ginseng.message.repository.MessageRepository;
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
public class MessageService {

    final private MessageRepository messageRepository;
    final private MemberRepository memberRepository;

    public int getReceivedCount(String myId) {
        return messageRepository.findReceivedCount(myId);

    }

    public int getSentCount(String myId) {
        return messageRepository.findSentCount(myId);
    }

    @Transactional
    public MessageRequestDto requestMessage(String myId, String friendId) {
        Member me = memberRepository.findMemberByID(myId).get();
        Member friend = memberRepository.findMemberByID(friendId).get();
        MessageRequest messageRequest = new MessageRequest(me, friend, MessageRequestStatus.REQUEST.getShortStatus());
        MessageRequest result = messageRepository.saveRequestMessage(messageRequest);
        return new MessageRequestDto(result);
    }

    @Transactional
    public SendMessageResponse sendMessage(SendMessageRequest messageRequest) {
        Member me = memberRepository.findMemberByID(messageRequest.getMyId()).get();
        Member friend = memberRepository.findMemberByID(messageRequest.getFriendId()).get();
        Message message = new Message(me, friend, messageRequest);
        Message result  = messageRepository.saveMessage(message);
        return new SendMessageResponse(result);
    }

    public List<MessageDto> findAllReceivedList(String myId, int offset, int limit) {
         return messageRepository.findAllReceivedList(myId, offset, limit)
                 .stream()
                 .map(ms -> new MessageDto(ms))
                 .collect(Collectors.toList());
    }

    public List<MessageDto> findAllSentList(String myId, int offset, int limit) {
        return messageRepository.findAllSentList(myId, offset, limit)
                .stream()
                .map(ms -> new MessageDto(ms))
                .collect(Collectors.toList());
    }

    public List<MessageRequestDto> findAllRequestedMessage(String myId, int offset, int limit) {
        return messageRepository.findAllRequestedMessage(myId, offset, limit)
                .stream()
                .map(mr -> new MessageRequestDto(mr))
                .collect(Collectors.toList());
    }

    public MessageResponse findOne(String messageId) {
        Message result = messageRepository.finOne(messageId);
        return new MessageResponse(result);
    }
}
