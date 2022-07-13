package com.shjeon.springpj.web.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터 처리시
    private String content; //섬머노트

    @ColumnDefault("0")
    private int count; //조회수

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

// mappedby 연관관계의 주인이 아님 select 시에 join을 통해 가져오도록만 설정
// mappedby 값은 reply 테이블에 board 컬럼명을 지정
// EAGGER 전략이 필요한 이유는 보드를 가져올 때 댓글이 필요하기 때문 LAZY 방식은 나중에 요청이 있다면 들고오는 방식
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp regDate;

}
