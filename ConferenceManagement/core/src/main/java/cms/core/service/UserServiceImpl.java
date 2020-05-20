package cms.core.service;

import cms.core.domain.CMSUser;
import cms.core.domain.Conference;
import cms.core.domain.Permission;
import cms.core.repo.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cms.core.repo.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Optional<CMSUser> getUserByUsername(String username) {
        return userRepository.findAll().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<CMSUser> getUserByEmailAddress(String emailAddress) {
        return userRepository.findAll().stream().filter(user -> user.getEmailAddress().equals(emailAddress)).findFirst();
    }

    @Override
    public Optional<CMSUser> getUserById(Long userId) {
        return userRepository.findAll().stream().filter(user -> user.getId().equals(userId)).findFirst();
    }

    @Override
    public List<Conference> getConferencesForPCMember(String username) {
        return permissionRepository.findAll().stream()
                .filter(permission -> permission.getCmsUser().getUsername().equals(username))
                .filter(Permission::isPCMember)
                .map(Permission::getConference)
                .collect(Collectors.toList());
    }

    @Override
    public List<CMSUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public CMSUser save(CMSUser CMSUser) {
        return userRepository.save(CMSUser);
    }

    @Override
    @Transactional
    public CMSUser update(CMSUser newCMSUser) {
        CMSUser cmsUser = userRepository.findById(newCMSUser.getId()).orElse(newCMSUser);

        cmsUser.setAffiliation(cmsUser.getAffiliation());
        cmsUser.setEmailAddress(newCMSUser.getEmailAddress());
        cmsUser.setChair(newCMSUser.isChair());
        cmsUser.setCoChair(newCMSUser.isCoChair());
        cmsUser.setSCMember(newCMSUser.isSCMember());
        cmsUser.setName(newCMSUser.getName());
        cmsUser.setUsername(newCMSUser.getUsername());
        cmsUser.setPassword(newCMSUser.getPassword());
        cmsUser.setPersonalWebsite(newCMSUser.getPersonalWebsite());

        return cmsUser;
    }

    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<CMSUser> getChair() {
        return userRepository.findAll().stream().filter(CMSUser::isChair).findFirst();
    }

    @Override
    public List<CMSUser> getCoChairs() {
        return userRepository.findAll().stream().filter(CMSUser::isCoChair).collect(Collectors.toList());
    }

    @Override
    public List<CMSUser> getSCMembers() {
        return userRepository.findAll().stream().filter(CMSUser::isSCMember).collect(Collectors.toList());
    }
}
