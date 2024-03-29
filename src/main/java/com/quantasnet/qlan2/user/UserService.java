package com.quantasnet.qlan2.user;

import com.quantasnet.qlan2.steam.SteamProfile;
import com.quantasnet.qlan2.user.model.ChangePasswordForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFactory userFactory;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllSteamUsers() {
        return userRepository.findBySteam(Boolean.TRUE);
    }

    public User getUserByUsername(final String username) {
        return userRepository.getUserByUserName(username);
    }

    public User getUserByEmail(final String email) {
        return userRepository.getUserByEmail(email);
    }

    public User getUserBySteamId(long id) {
        return userRepository.getUserBySteamId(id);
    }

    public User getUserById(long id) {
        return userRepository.findOne(id);
    }

    public User saveUser(final User user) {
        if (null == userRepository.getUserByUserName(user.getUserName())) {
            final User returnUser = userFactory.make(user);
            return userRepository.save(returnUser);
        }
        return null;
    }

    public User saveOpenIdUser(final SteamProfile profile) {
        final User newUser = userFactory.makeSteamUser(profile);
        return userRepository.save(newUser);
    }

    public User save(final String userName, final String firstName,
                     final String lastName, final String email, final String password,
                     final Set<Role> roles) {
        final User user = userFactory.make(userName, firstName, lastName, email, password, roles);
        return userRepository.save(user);
    }

    public User update(final User user) {
        return userRepository.save(user);
    }

    public User profileUpdate(final User authUser, final User profileUser) {
        final User dbUser = userRepository.findOne(authUser.getId());
        dbUser.setFirstName(profileUser.getFirstName());
        dbUser.setLastName(profileUser.getLastName());
        dbUser.setEmail(profileUser.getEmail());
        if (!dbUser.isSteam()) {
            dbUser.setImageUrl(userFactory.generateGravatarUrl(dbUser.getEmail()));
        }
        final User newUser = userRepository.save(dbUser);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(newUser, newUser.getPassword(), newUser.getAuthorities()));
        return newUser;
    }

    public boolean changePassword(final User user, final ChangePasswordForm changePasswordForm) {
        final User dbUser = userRepository.getUserByUserName(user.getUserName());
        if (passwordEncoder.matches(changePasswordForm.getCurrentPassword(), dbUser.getPassword())) {
            if (changePasswordForm.getNewPassword().equals(changePasswordForm.getNewPasswordAgain())) {
                final User saveUser = userFactory.changePassword(user, changePasswordForm.getNewPassword());
                userRepository.save(saveUser);
                return true;
            }
        }

        return false;
    }
}
