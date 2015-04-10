package com.quantasnet.qlan2.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
    User getUserBySteamId(long id);
    List<User> findBySteam(boolean steam);
}
