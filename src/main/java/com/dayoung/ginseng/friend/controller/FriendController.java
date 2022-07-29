package com.dayoung.ginseng.friend.controller;

import com.dayoung.ginseng.friend.domain.FriendVo;
import com.dayoung.ginseng.friend.service.FriendService;
import com.dayoung.ginseng.member.domain.MemberVo;
import com.dayoung.ginseng.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final MessageSource ms;

    /**
     * 친구 추가 화면을 로드.
     * @return
     */
    @GetMapping("/request")
    public String addView(){
        return "friends/requestFriend";
    }

    /**
     * 친구 신청.
     * @param friendId
     * @param request
     * @return
     */
    @PostMapping("/request/{friendId}")
    @ResponseBody
    public String requestFriend(@PathVariable String friendId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object myMemberId = session.getAttribute(SessionConst.MEMBER_ID);
        try {
            friendService.requestFriend(friendId, (String) myMemberId);
            return ms.getMessage("msg.friend.requestsuccess", null, null);
        } catch (Exception e) {
            log.info("REQUEST FRIEND FAIL: " + e);
            return ms.getMessage("msg.friend.requestfail", null, null);
        }
    }

    /**
     * 친구 추가 페이지에서 사용자가 입력한 입력값으로 검색.
     * @param friendId
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/finds/{friendId}")
    public String findMember(@PathVariable String friendId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object myMemberId = session.getAttribute(SessionConst.MEMBER_ID);
        List<MemberVo> foundFriends = friendService.findMember((String)myMemberId, friendId);
        model.addAttribute("foundFriends", foundFriends);
        return "friend/foundMemberTable";
    }

    /**
     * 친구 목록 페이지를 로드.
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/my")
    public String myFriendView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object myMemberId = session.getAttribute(SessionConst.MEMBER_ID);
        List<FriendVo> myFriends = friendService.findMyFriendAll((String) myMemberId);
        model.addAttribute("myFriends", myFriends);
        return "friends/myFriend";
    }

    /**
     * 받은 신청 페이지 로드.
     * @return
     */
    @GetMapping("/requested")
    public String requestedView() {
        return "friends/requestedFriend";
    }


    /**
     * 받은 신청 페이지에서 받은 신청 리스트를 로드.
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/requested")
    public String findRequestedFriendList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object myMemberId = session.getAttribute(SessionConst.MEMBER_ID);
        List<FriendVo> requestedFriends = friendService.findRequestedFriend((String) myMemberId);
        model.addAttribute("requestedFriends", requestedFriends);
        return "friends/requestedFriendTable";
    }

    /**
     * 받은 신청 페이지에서 받은 요청 승인.
     * @param friendId
     * @param request
     * @return
     */
    @PostMapping("/requested/{friendId}")
    @ResponseBody
    public String acceptFriend(@PathVariable String friendId, HttpServletRequest request){
        HttpSession session = request.getSession();
        Object myMemberId = session.getAttribute(SessionConst.MEMBER_ID);
        try {
            friendService.acceptFriend((String) myMemberId, friendId);
            return ms.getMessage("msg.friend.acceptsuccess", null, null);
        } catch (Exception e) {
            log.info("REQUEST FRIEND FAIL: " + e);
            return ms.getMessage("msg.friend.acceptfail", null, null);
        }
    }

    /**
     * 보낸 신청 페이지 로드.
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/send")
    public String requestSendView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object myMemberId = session.getAttribute(SessionConst.MEMBER_ID);
        List<FriendVo> requestSendFriends = friendService.findRequestSendFriend((String) myMemberId);
        model.addAttribute("requestSendFriends", requestSendFriends);
        return "friends/requestSendFriend";
    }
}
