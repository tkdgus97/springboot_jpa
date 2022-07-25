package com.shjeon.springpj.web.loggame.repository;

import com.shjeon.springpj.web.entity.TempCharactor;
import com.shjeon.springpj.web.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserLogGameRepository extends JpaRepository<TempCharactor, Integer> {
    TempCharactor findByTempUser(TempUser tempUser);
}
