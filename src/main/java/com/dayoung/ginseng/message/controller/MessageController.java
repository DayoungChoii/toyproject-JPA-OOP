package com.dayoung.ginseng.message.controller;

import com.dayoung.ginseng.message.CustomRespond;
import com.dayoung.ginseng.message.dto.*;
import com.dayoung.ginseng.message.service.MessageService;
import com.dayoung.ginseng.session.SessionConst;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    final private MessageService messageService;


    /**
     * 받은 메시지 수, 보낸 메시지 수
     * @param request
     * @return
     */
    @GetMapping
    @RequestMapping("/messagesCount")
    public Map getMessagesCount(HttpServletRequest request){
        String myId = (String)request.getSession().getAttribute(SessionConst.ID);
        int receivedCount = messageService.getReceivedCount(myId);
        int sentCount = messageService.getSentCount(myId);

        HashMap<String, Integer> result = new HashMap();
        result.put("receivedCount", receivedCount);
        result.put("sentCount", sentCount);
        return result;
    }

    /**
     * 메시지요청 전송
     * @param friendId
     * @param request
     * @return
     */
    @GetMapping
    @RequestMapping("/request/{friendId}")
    public Result needMessage(@PathVariable String friendId, HttpServletRequest request) {
        Result result = new Result();
        try {
            String myId = (String)request.getSession().getAttribute(SessionConst.ID);
            MessageRequestDto message = messageService.requestMessage(myId, friendId);
            result.setData(message);
            result.setResultCode(CustomRespond.SUCCESS.getShortStatus());
            return result;
        } catch (Exception e){
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return result;
        }
    }

    /**
     * 메시지 전송
     * @param messageRequest
     * @return
     */
    @PostMapping
    public Result sendMessage(@RequestBody SendMessageRequest messageRequest){
        Result result = new Result();
        try {
            SendMessageResponse messageResponse = messageService.sendMessage(messageRequest);
            result.setData(messageResponse);
            result.setResultCode(CustomRespond.SUCCESS.getShortStatus());
            return result;
        } catch (Exception e){
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return result;
        }

    }

    /**
     * 받은 메시지 목록 get
     * @param offset
     * @param limit
     * @param request
     * @return
     */
    @GetMapping
    @RequestMapping("received-list")
    public Result receivedList(@RequestParam(defaultValue = "1") int offset,
                               @RequestParam(defaultValue = "100") int limit, HttpServletRequest request) {
        String myId = (String)request.getSession().getAttribute(SessionConst.ID);

        Result result = new Result();
        try{
            List<MessageDto>messageDtos = messageService.findAllReceivedList(myId, offset, limit);
            result.setData(messageDtos);
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return result;
        } catch (Exception e){
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return  result;
        }
    }

    /**
     * 보낸 메시지 목록
     * @param offset
     * @param limit
     * @param request
     * @return
     */
    @GetMapping
    @RequestMapping("sent-list")
    public Result sentList(@RequestParam(defaultValue = "1") int offset,
                           @RequestParam(defaultValue = "100") int limit, HttpServletRequest request) {
        String myId = (String)request.getSession().getAttribute(SessionConst.ID);

        Result result = new Result();
        try{
            List<MessageDto>messageDtos = messageService.findAllSentList(myId, offset, limit);
            result.setData(messageDtos);
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return result;
        } catch (Exception e){
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return  result;
        }
    }

    /**
     * 요청받은 메시지 목록
     * @param offset
     * @param limit
     * @param request
     * @return
     */
    @GetMapping
    @RequestMapping("requested-list")
    public Result requestedList(@RequestParam(defaultValue = "1") int offset,
                                @RequestParam(defaultValue = "100") int limit, HttpServletRequest request) {
        String myId = (String)request.getSession().getAttribute(SessionConst.ID);

        Result result = new Result();
        try{
            List<MessageRequestDto> messageRequestDtos = messageService.findAllRequestedMessage(myId, offset, limit);
            result.setData(messageRequestDtos);
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return result;
        } catch (Exception e){
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return  result;
        }

    }

    /**
     * 메시지 단 건 조회
     * @param messageId
     * @return
     */
    @GetMapping
    @RequestMapping("{messageId}")
    public Result findMessage(@PathVariable String messageId){
        Result result = new Result();
        try{
            MessageResponse messageResponse = messageService.findOne(messageId);
            result.setData(messageResponse);
            result.setResultCode(CustomRespond.SUCCESS.getShortStatus());
            return result;
        } catch (Exception e){
            result.setResultCode(CustomRespond.FAIL.getShortStatus());
            return result;
        }
    }
}
