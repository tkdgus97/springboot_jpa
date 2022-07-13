package com.shjeon.springpj.web.home.repository;

import com.shjeon.springpj.web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
