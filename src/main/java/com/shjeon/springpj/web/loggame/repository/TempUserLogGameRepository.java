package com.shjeon.springpj.web.loggame.repository;

import com.shjeon.springpj.web.entity.TempCharactor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserLogGameRepository extends JpaRepository<TempCharactor, Integer> {
}
