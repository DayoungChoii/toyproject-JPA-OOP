package com.dayoung.ginseng.friend.controller;

import com.dayoung.ginseng.friend.domain.FriendRelation;
import com.dayoung.ginseng.friend.domain.FriendStatus;
import com.dayoung.ginseng.friend.service.FriendService;
import com.dayoung.ginseng.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FriendControllerTest {

    @InjectMocks
    private FriendController friendController;

    @Mock
    private FriendService friendService;

    @Mock
    private MessageSource ms;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(friendController).build();
    }

    @Test
    public void 친구_신청_성공() throws Exception{
        //given
        doReturn(new FriendRelation()).when(friendService).requestFriend(anyString(), anyString());
        doReturn("친구 신청 성공!").when(ms).getMessage("msg.friend.requestsuccess", null, null);
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");


        //when
        ResultActions result = mockMvc.perform(post("/friends/request/park1")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("친구 신청 성공!"));
    }

    @Test
    public void 친구_신청_실패() throws Exception{
        //given
        doThrow(new Exception()).when(friendService).requestFriend(anyString(), anyString());
        doReturn("친구 신청 실패!").when(ms).getMessage("msg.friend.requestfail", null, null);
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");


        //when
        ResultActions result = mockMvc.perform(post("/friends/request/park1")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("친구 신청 실패!"));
    }

    @Test
    public void 친구아닌_사용자_목록() throws Exception{
        //given
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");
        Member friend1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member friend2 = new Member("M123456782", "park2", "luv", "1234");

        List<Member> friendList = new ArrayList();
        friendList.add(friend1);
        friendList.add(friend2);

        doReturn(friendList).when(friendService).searchNonFriends("choi1", "park");

        //when
        ResultActions result = mockMvc.perform(post("/friends/finds/park")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("foundFriends", friendList))
                .andExpect(view().name("friends/foundMemberTable"));
    }

    @Test
    public void 내_친구_목록() throws Exception{
        //given
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");
        Member friend1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member friend2 = new Member("M123456782", "park2", "luv", "1234");

        List<Member> friendList = new ArrayList();
        friendList.add(friend1);
        friendList.add(friend2);

        doReturn(friendList).when(friendService).findMyFriendAll("choi1");

        //when
        ResultActions result = mockMvc.perform(get("/friends/my")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("myFriends", friendList))
                .andExpect(view().name("friends/myFriend"));
    }

    @Test
    public void 내가_받은_친구목록() throws Exception{
        //given
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");
        Member friend1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member friend2 = new Member("M123456782", "park2", "luv", "1234");

        List<Member> friendList = new ArrayList();
        friendList.add(friend1);
        friendList.add(friend2);

        doReturn(friendList).when(friendService).findRequestedFriend("choi1");

        //when
        ResultActions result = mockMvc.perform(post("/friends/requested")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("requestedFriends", friendList))
                .andExpect(view().name("friends/requestedFriendTable"));
    }

    @Test
    public void 친구_요청_승낙_성공() throws Exception{
        //given
        doReturn(new FriendRelation()).when(friendService).acceptFriend(anyString(), anyString());
        doReturn("친구 승낙 성공!").when(ms).getMessage("msg.friend.acceptsuccess", null, null);
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");


        //when
        ResultActions result = mockMvc.perform(post("/friends/requested/park1")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("친구 승낙 성공!"));
    }

    @Test
    public void 내가_요청한_친구목록() throws Exception{
        //given
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("id", "choi1");
        Member friend1 = new Member("M123456781", "park1", "ggoyo", "1234");
        Member friend2 = new Member("M123456782", "park2", "luv", "1234");

        List<Member> friendList = new ArrayList();
        friendList.add(friend1);
        friendList.add(friend2);

        doReturn(friendList).when(friendService).findRequestSendFriend("choi1");

        //when
        ResultActions result = mockMvc.perform(get("/friends/send")
                .session(mockHttpSession))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attribute("requestSendFriends", friendList))
                .andExpect(view().name("friends/requestSendFriend"));
    }

}