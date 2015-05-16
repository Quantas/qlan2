package com.quantasnet.qlan2.user;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
    User getUserBySteamId(long id);
    List<User> findBySteam(boolean steam);
}
