package com.motivation.servicesImpl;

import com.motivation.entities.User;
import com.motivation.repository.AbstractUserRepository;
import com.motivation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements com.motivation.services.UserService {

    @Autowired
    private AbstractUserRepository abstractUserRepository;

    @Override
    public Long getUserIdByUsername(String username) {
        User user = abstractUserRepository.findOneByUsername(username);
        return user.getId();
    }

    @Override
    public String getFullNameByUsername(String username) {
        User user = abstractUserRepository.findOneByUsername(username);
        return user.getFullName();
    }

    @Override
    public String getFullNameByUserId(Long userId) {
        User user = abstractUserRepository.findOne(userId);
        return user.getFullName();
    }

    @Override
    public String getUsernameByUserId(Long userId) {
        User user = abstractUserRepository.findOne(userId);
        return user.getUsername();
    }
}
