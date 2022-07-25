package com.shjeon.springpj.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@DynamicInsert
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 30)
    private String userId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "user")
    List<CharacterInfo> characterInfoList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoleType role; //enum 타입

    @CreationTimestamp
    private Timestamp regDate;

    public void addCharacterInfo(CharacterInfo character){
        List<CharacterInfo> unitList = this.getCharacterInfoList();
        character.setUser(this);
        unitList.add(character);
    }
    public void removeCharacterInfo(CharacterInfo character) {
        character.setUser(null);
        this.characterInfoList.remove(character);
    }
}
