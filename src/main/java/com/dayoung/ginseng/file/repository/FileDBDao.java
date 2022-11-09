package com.dayoung.ginseng.file.repository;

import com.dayoung.ginseng.file.domain.UploadFile;

public interface FileDBDao {
    public void insertFile(UploadFile uploadFile);
}
