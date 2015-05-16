package com.quantasnet.qlan2.user;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
