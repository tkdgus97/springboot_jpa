package com.shjeon.springpj.web.user.repository;

import com.shjeon.springpj.web.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempUserRepository extends JpaRepository<TempUser, String> {
}
