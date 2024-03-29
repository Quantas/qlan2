package com.quantasnet.qlan2.admin.user;

import com.quantasnet.qlan2.organization.OrganizationService;
import com.quantasnet.qlan2.security.HasAdminRole;
import com.quantasnet.qlan2.user.Role;
import com.quantasnet.qlan2.user.User;
import com.quantasnet.qlan2.user.UserFactory;
import com.quantasnet.qlan2.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andrewlandsverk on 4/16/15.
 */
@HasAdminRole
@Transactional
@Service
public class UserAdminService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserFactory userFactory;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final long id) {
    	final User user = userRepository.findOne(id);
    	user.getOrganizations().forEach(org -> organizationService.removeOrgMember(org.getOrg().getId(), user));
        userRepository.delete(user);
    }

    public void deactivateUser(final long id) {
        final User user = userRepository.findOne(id);
        user.setActive(false);
        userRepository.save(user);
    }

    public void activateUser(final long id) {
        final User user = userRepository.findOne(id);
        user.setActive(true);
        userRepository.save(user);
    }

    public void makeAdmin(long id) {
        final User user = userRepository.findOne(id);
        userFactory.addAdminRole(user);
        userRepository.save(user);
    }

    public void revokeAdmin(long id) {
        final User user = userRepository.findOne(id);
        Role toRemove = null;
        for (final Role role : user.getRoles()) {
            if (role.getRoleName().equals(Role.ADMIN)) {
                toRemove = role;
                break;
            }
        }

        user.getRoles().remove(toRemove);
        userRepository.save(user);
    }
}
