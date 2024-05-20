package com.datn.maguirestore.security.services;

import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserByEmailOrUsername(String subject) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(subject, subject)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email or username: " + subject));

        return UserDetailsImpl.build(user);
    }

}

