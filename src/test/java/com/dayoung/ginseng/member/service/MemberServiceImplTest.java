package com.dayoung.ginseng.member.service;

import com.dayoung.ginseng.file.domain.UploadFile;
import com.dayoung.ginseng.file.util.FileUtil;
import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.domain.MemberRegisterForm;
import com.dayoung.ginseng.member.repository.MemberRepository;
import com.dayoung.ginseng.member.util.EncryptAlgorithm;
import com.dayoung.ginseng.member.util.SHA256;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //mock 객체 사
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;

    private static MockedStatic<FileUtil> fileUtil;

    @Spy
    private SHA256 encryptAlgorithm;

    @BeforeAll
    public static void beforeAll(){
        fileUtil = mockStatic(FileUtil.class);
    }

    @AfterAll
    public static void afterAll(){
        fileUtil.close();
    }


    @Test
    public void 회원가입_성공_프로필사진_없음() throws Exception{
        //given
        String id = "choi1234";
        String password = "1234";
        String nickname = "maxx";


        MemberRegisterForm memberRegisterForm = new MemberRegisterForm();
        memberRegisterForm.setId(id);
        memberRegisterForm.setNickname(nickname);
        memberRegisterForm.setPassword(password);

        doReturn(new Member("M123456789", id, nickname, encryptAlgorithm.encrypt("1234"))).when(memberRepository).save(any(Member.class));
        when(FileUtil.doesFileExist(any(MultipartFile.class))).thenReturn(false);

        //when
        Member registeredMember = memberService.register(memberRegisterForm, any(MultipartFile.class));

        //then
        assertThat(memberRegisterForm.getId()).isEqualTo(registeredMember.getId());
        assertThat(memberRegisterForm.getNickname()).isEqualTo(registeredMember.getNickname());
        assertThat(encryptAlgorithm.encrypt(memberRegisterForm.getPassword())).isEqualTo(registeredMember.getPassword());

    }
    
    @Test
    public void 회원가입_성공_프로필사진_있음() throws Exception{
        //given
        String id = "choi1234";
        String password = "1234";
        String nickname = "maxx";


        MemberRegisterForm memberRegisterForm = new MemberRegisterForm();
        memberRegisterForm.setId(id);
        memberRegisterForm.setNickname(nickname);
        memberRegisterForm.setPassword(password);


        //MultipartFile mock
        MultipartFile multipartFile = new MockMultipartFile("file"
                                                            , "test.png"
                                                            , "img/png"
                                                            , new FileInputStream("/Users/choidayoung/dev/file/before/test.png"));
        UploadFile uploadFile = new UploadFile(123456789L, id, "eedx-3343-48c83-3jdj.png", "/Users/choidayoung/dev/file/profile/dev", "test.png");
//        doReturn(uploadFile).when(fileService).storeFile(any(MultipartFile.class), any(Member.class));
        Member member = new Member("M123456789", id, nickname, encryptAlgorithm.encrypt(password));
        member.setUploadFile(uploadFile);
        doReturn(member).when(memberRepository).save(any(Member.class));

        //when
        Member registeredMember = memberService.register(memberRegisterForm, multipartFile);

        //then
        assertThat(memberRegisterForm.getId()).isEqualTo(registeredMember.getId());
        assertThat(memberRegisterForm.getNickname()).isEqualTo(registeredMember.getNickname());
        assertThat(encryptAlgorithm.encrypt(memberRegisterForm.getPassword())).isEqualTo(registeredMember.getPassword());
        assertThat(registeredMember.getUploadFile().getId()).isNotNull();
        assertThat(registeredMember.getUploadFile().getLocalFileName()).isNotEmpty();
    
    }

    @Test
    public void 로그인_성공() throws Exception{
        //given
        String id = "choi1234";
        String password = "1234";
        String encryptPassword = encryptAlgorithm.encrypt(password);

        Member member = new Member("M123456789", encryptPassword, "maxx", id);
        doReturn(Optional.of(member)).when(memberRepository).findMemberByPasswordAndId(id, encryptPassword);

        //when
        Optional<Member> loginMember = memberService.login(id, password);

        //then
        assertThat(loginMember.get().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(loginMember.get().getNickname()).isEqualTo(member.getNickname());
    }

}