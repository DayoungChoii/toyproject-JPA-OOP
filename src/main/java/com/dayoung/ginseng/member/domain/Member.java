package com.dayoung.ginseng.member.domain;

import com.dayoung.ginseng.file.domain.UploadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    private String memberId;

    private String id;

    private String password;

    private String nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private UploadFile uploadFile;

    public Member(String id, String password) {
        this.id = id;
        this.password = password;
    }


    public Member(String memberId, String id, String nickname, String password) {
        this.memberId = memberId;
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
