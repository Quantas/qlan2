package com.quantasnet.qlan2.security;

import com.quantasnet.qlan2.user.User;
import com.quantasnet.qlan2.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
public class QlanUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return load(username);
    }

    private User load(final String userId) throws UsernameNotFoundException, DataAccessException {
        final User user = userRepository.getUserByUserName(userId);

        if (null == user) {
            throw new UsernameNotFoundException("The user does not exist");
        }

        return user;
    }
}
