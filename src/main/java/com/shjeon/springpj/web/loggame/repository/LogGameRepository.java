package com.shjeon.springpj.web.loggame.repository;

import com.shjeon.springpj.entity.CharacterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LogGameRepository extends JpaRepository<CharacterInfo, Integer> {
    List<CharacterInfo> findByUserId(int id);
}
