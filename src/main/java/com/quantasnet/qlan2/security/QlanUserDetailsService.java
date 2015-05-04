package com.quantasnet.qlan2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.quantasnet.qlan2.user.User;
import com.quantasnet.qlan2.user.UserRepository;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
public class QlanUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.getUserByUserName(username);

        if (null == user) {
            throw new UsernameNotFoundException("The user does not exist");
        }

        return user;
    }
}
