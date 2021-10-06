package com.niu.security.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * user service
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 15:15]
 * @createTime [2021/08/01 15:15]
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username)
                .password(passwordEncoder.encode("123456"))
                .authorities("ROLE_ADMIN")
                .build();
    }
}
