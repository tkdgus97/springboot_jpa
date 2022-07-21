package com.shjeon.springpj.web.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@Entity
public class CharacterInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String unitName;

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
    private double attackSpeed;

    @Column(nullable = false)
    private int money;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp regDate;

    public CharacterInfo() {

    }
}
