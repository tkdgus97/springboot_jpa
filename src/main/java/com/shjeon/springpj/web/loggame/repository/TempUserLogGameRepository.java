package com.shjeon.springpj.web.loggame.repository;

import com.shjeon.springpj.entity.TempCharactor;
import com.shjeon.springpj.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserLogGameRepository extends JpaRepository<TempCharactor, Integer> {
    TempCharactor findByTempUser(TempUser tempUser);
}
