package com.dayoung.ginseng.member.controller;

import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.domain.MemberRegisterForm;
import com.dayoung.ginseng.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    public void 로그인_실패_유효성검증_오류있을_떄() throws Exception{
        //given
        MultiValueMap<String, String> memberLoginInfo = new LinkedMultiValueMap<>();
        memberLoginInfo.add("id", "");
        memberLoginInfo.add("password", "");

        String redirectURL = "";

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/members/login")
                .params(memberLoginInfo)
                .param("redirectURL", redirectURL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("members/login"));
    }

    @Test
    public void 로그인_실패_아이디_패스워드_불일치() throws Exception{
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();

        MultiValueMap<String, String> memberLoginInfo = new LinkedMultiValueMap<>();
        memberLoginInfo.add("id", "choi1234");
        memberLoginInfo.add("password", "1234");

        String redirectURL = "";

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/members/login")
                .params(memberLoginInfo)
                .param("redirectURL", redirectURL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(view().name("members/login"));

    }

    @Test
    public void 로그인_성공() throws Exception{
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();

        MultiValueMap<String, String> memberLoginInfo = new LinkedMultiValueMap<>();
        memberLoginInfo.add("id", "choi1234");
        memberLoginInfo.add("password", "1234");

        String redirectURL = "/";

        Member member = new Member("M123456789", "choi1234", "maxx", "1234");
        doReturn(Optional.of(member)).when(memberService).login(anyString(), anyString());

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/members/login")
                .params(memberLoginInfo)
                .param("redirectURL", redirectURL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:"+redirectURL));
    }

    @Test
    public void 회원가입_유효성_검증_실패() throws Exception{
        //given
        MultiValueMap<String, String> memberRegisterInfo = new LinkedMultiValueMap<>();
        memberRegisterInfo.add("id", "");
        memberRegisterInfo.add("password", "");
        memberRegisterInfo.add("passwordCheck", "");
        memberRegisterInfo.add("nickname", "");

        MockMultipartFile profileFile = new MockMultipartFile("file"
                , "test.png"
                , "img/png"
                , new FileInputStream("/Users/choidayoung/dev/file/before/test.png"));

        doReturn(false).when(memberService).isExistedMember(anyString());

        //when
        ResultActions result = mockMvc.perform(multipart("/members/register")
                .file("profileFile", profileFile.getBytes())
                .params(memberRegisterInfo)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("members/register"));

    }

    @Test
    public void 회원가입_존재하는_회원_실패() throws Exception{
        //given
        MultiValueMap<String, String> memberRegisterInfo = new LinkedMultiValueMap<>();
        memberRegisterInfo.add("id", "choi1234@gmail.com");
        memberRegisterInfo.add("password", "1234");
        memberRegisterInfo.add("passwordCheck", "1234");
        memberRegisterInfo.add("nickname", "maxx");

        MockMultipartFile profileFile = new MockMultipartFile("file"
                , "test.png"
                , "img/png"
                , new FileInputStream("/Users/choidayoung/dev/file/before/test.png"));

        doReturn(true).when(memberService).isExistedMember(anyString());

        //when
        ResultActions result = mockMvc.perform(multipart("/members/register")
                .file("profileFile", profileFile.getBytes())
                .params(memberRegisterInfo)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("members/register"));

    }

    @Test
    public void 회원가입_비밀번호_확인_틀림_실패() throws Exception{
        //given
        MultiValueMap<String, String> memberRegisterInfo = new LinkedMultiValueMap<>();
        memberRegisterInfo.add("id", "choi1234@gmail.com");
        memberRegisterInfo.add("password", "1234");
        memberRegisterInfo.add("passwordCheck", "1234456789");
        memberRegisterInfo.add("nickname", "maxx");

        MockMultipartFile profileFile = new MockMultipartFile("file"
                , "test.png"
                , "img/png"
                , new FileInputStream("/Users/choidayoung/dev/file/before/test.png"));

        doReturn(false).when(memberService).isExistedMember(anyString());

        //when
        ResultActions result = mockMvc.perform(multipart("/members/register")
                .file("profileFile", profileFile.getBytes())
                .params(memberRegisterInfo)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("members/register"));

    }

    @Test
    public void 회원가입_성공() throws Exception{
        //given
        MultiValueMap<String, String> memberRegisterInfo = new LinkedMultiValueMap<>();
        memberRegisterInfo.add("id", "choi1234@gmail.com");
        memberRegisterInfo.add("password", "1234");
        memberRegisterInfo.add("passwordCheck", "1234");
        memberRegisterInfo.add("nickname", "maxx");

        MockMultipartFile profileFile = new MockMultipartFile("file"
                , "test.png"
                , "img/png"
                , new FileInputStream("/Users/choidayoung/dev/file/before/test.png"));

        doReturn(false).when(memberService).isExistedMember(anyString());
        doReturn(new Member("M123456789","choi1234@gmail.com", "maxx","1234"))
                .when(memberService).register(any(MemberRegisterForm.class), any(MultipartFile.class));

        //when
        ResultActions result = mockMvc.perform(multipart("/members/register")
                .file("profileFile", profileFile.getBytes())
                .params(memberRegisterInfo)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print());

        //then
        result.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/members/successedRegister"));

    }

}