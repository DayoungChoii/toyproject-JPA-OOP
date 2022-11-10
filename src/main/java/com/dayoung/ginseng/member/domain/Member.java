package com.dayoung.ginseng.member.domain;

import com.dayoung.ginseng.file.domain.UploadFile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member implements Serializable {

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

    public void setId(String id){
        this.id = id;
    }
}
