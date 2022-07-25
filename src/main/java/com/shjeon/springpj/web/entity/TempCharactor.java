package com.shjeon.springpj.web.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TempCharactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String name;

    @Column
    private String job;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int hp;

    @Column(nullable = false)
    private int mp;

    @Column(nullable = false)
    private int attack;

    @Column(nullable = false)
    private int defend;

    @Column(nullable = false)
    private int maxHp;

    @Column(nullable = false)
    private int maxMp;

    @Column(nullable = false)
    private double attackSpeed;

    @Column(nullable = false)
    private int money;

    @OneToOne
    @JoinColumn(name = "tempId")
    private TempUser tempUser;

    @CreationTimestamp
    private Timestamp regDate;

}
