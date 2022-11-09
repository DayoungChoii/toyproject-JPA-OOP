package com.dayoung.ginseng.file.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UploadFile {


    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String targetId;

    private String localFileName;

    private String filePath;

    private String realFileName;

    public UploadFile(String realFileName, String localFileName, String filePath) {
        this.realFileName = realFileName;
        this.localFileName = localFileName;
        this.filePath = filePath;
    }

    public void setTargetId(String targetId){
        this.targetId = targetId;
    }
}
