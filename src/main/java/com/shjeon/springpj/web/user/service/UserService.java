package com.shjeon.springpj.web.user.service;

import com.shjeon.springpj.web.entity.User;
import com.shjeon.springpj.web.user.repository.UserRepository;
import com.shjeon.springpj.web.user.vo.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("userid === "+userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("사용자 없음");
                });
        return new Account(user);
    }
}
