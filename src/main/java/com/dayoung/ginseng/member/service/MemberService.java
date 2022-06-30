package com.dayoung.ginseng.member.service;

import com.dayoung.ginseng.member.domain.MemberRegisterForm;
import com.dayoung.ginseng.member.domain.MemberVo;
import com.dayoung.ginseng.member.exception.EncryptAlgorithmFailException;
import com.dayoung.ginseng.member.util.EncryptAlgorithm;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface MemberService {

    void register(MemberRegisterForm form, MultipartFile profileFile) throws EncryptAlgorithmFailException, IOException;

    boolean isExistedMember(String id);

    MemberVo login(String id, String password) throws EncryptAlgorithmFailException;
}
