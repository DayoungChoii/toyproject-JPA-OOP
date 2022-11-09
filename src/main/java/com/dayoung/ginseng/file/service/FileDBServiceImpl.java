package com.dayoung.ginseng.file.service;

import com.dayoung.ginseng.file.domain.UploadFile;
import com.dayoung.ginseng.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileDBServiceImpl implements FileDBService{

    private final FileRepository fileRepository;

    @Override
    public void saveFile(UploadFile uploadFile) {
        fileRepository.insertFile(uploadFile);
    }
}
