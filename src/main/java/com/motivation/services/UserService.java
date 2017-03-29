package com.motivation.services;

import com.motivation.customExceptions.UsernameIsInUseException;
import com.motivation.models.RegistrationModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(RegistrationModel registrationModel) throws UsernameIsInUseException;


}
