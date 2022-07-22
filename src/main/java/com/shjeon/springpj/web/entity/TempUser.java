package com.shjeon.springpj.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@DynamicInsert
@Entity
public class TempUser {

    @Id
    private String tempId;

    @CreationTimestamp
    private Timestamp regDate;

    @OneToOne(mappedBy = "tempUser")
    private TempCharactor tempCharactor;
}
