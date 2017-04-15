package com.motivation.servicesImpl;

import com.motivation.repository.AbstractUserRepository;
import com.motivation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements com.motivation.services.UserService {

    @Autowired
    private AbstractUserRepository abstractUserRepository;


    @Override
    public Long getUserIdByUsername(String username) {
        return null;
    }

    @Override
    public String getFullNameByUsername(String username) {
        return null;
    }
}
