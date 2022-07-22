package com.shjeon.springpj.web.loggame.repository;

import com.shjeon.springpj.web.entity.CharacterInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LogGameRepository extends JpaRepository<CharacterInfo, Integer> {
}
