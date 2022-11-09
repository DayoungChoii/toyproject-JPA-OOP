package com.dayoung.ginseng.file.repository;

import com.dayoung.ginseng.file.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

@Repository
@RequiredArgsConstructor
public class FileRepository implements FileDBDao{

    private final EntityManager em;

    @Override
    public void insertFile(UploadFile uploadFile) {
        em.persist(uploadFile);
    }
}
