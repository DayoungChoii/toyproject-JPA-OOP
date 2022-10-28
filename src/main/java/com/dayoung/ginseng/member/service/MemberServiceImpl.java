package com.dayoung.ginseng.member.service;

import com.dayoung.ginseng.file.domain.UploadFile;
import com.dayoung.ginseng.file.service.FileDBService;
import com.dayoung.ginseng.file.service.FileService;
import com.dayoung.ginseng.file.util.FileUtil;
import com.dayoung.ginseng.member.domain.MemberRegisterForm;
import com.dayoung.ginseng.member.domain.Member;
import com.dayoung.ginseng.member.exception.EncryptAlgorithmFailException;
import com.dayoung.ginseng.member.repository.MemberRepository;
import com.dayoung.ginseng.member.util.EncryptAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final FileDBService fileDBService;
    private final EncryptAlgorithm encryptAlgorithm;

    @Override
    public Optional<Member> login(String id, String password) throws EncryptAlgorithmFailException {
        return memberRepository.findMemberByPasswordAndId(id, encryptAlgorithm.encrypt(password));
    }

    @Override
    @Transactional
    public Member register(MemberRegisterForm memberRegisterForm, MultipartFile profileFile) throws IOException {

        Member member = createMember(memberRegisterForm);

        if(FileUtil.doesFileExist(profileFile)){
            UploadFile uploadFile = fileService.storeFile(profileFile, member);
            member.setUploadFile(uploadFile);
        }

        return memberRepository.save(member);
    }

    @Override
    public boolean isExistedMember(String id) {
        Optional<Member> member = memberRepository.findMemberByID(id);
        return member.isEmpty() ? false : true;
    }

    private Member createMember(MemberRegisterForm memberRegisterForm) throws EncryptAlgorithmFailException {
        return new Member("M" + UUID.randomUUID().toString()
                , memberRegisterForm.getId()
                , memberRegisterForm.getNickname()
                , encryptAlgorithm.encrypt(memberRegisterForm.getPassword()));
    }
}
