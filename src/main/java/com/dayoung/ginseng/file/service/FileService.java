package com.dayoung.ginseng.file.service;

import com.dayoung.ginseng.file.domain.UploadFile;
import com.dayoung.ginseng.member.domain.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String getFullPath(String fileName);

    public UploadFile storeFile(MultipartFile multipartFile, Member member) throws IOException;

    public String createLocalFileName(String realFileName);

}
