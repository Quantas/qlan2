package com.quantasnet.qlan2.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by andrewlandsverk on 4/9/15.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
