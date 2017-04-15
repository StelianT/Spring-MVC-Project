package com.motivation.servicesImpl;

import com.motivation.config.Errors;
import com.motivation.customExceptions.UsernameIsInUseException;
import com.motivation.entities.BasicUser;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.RegistrationModel;
import com.motivation.repository.BasicUserRepository;
import com.motivation.repository.UserRepository;
import com.motivation.services.BasicUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BasicUserServiceImpl implements BasicUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final BasicUserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BasicUserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                                BasicUserRepository userRepository,
                                ModelMapper modelMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(RegistrationModel registrationModel) throws UsernameIsInUseException {

        if (this.userRepository.findOneByUsername(registrationModel.getUsername()) != null){
            throw new UsernameIsInUseException("A user with this username already exists");
        }

        BasicUser user = this.modelMapper.map(registrationModel, BasicUser.class);
        String encryptedPassword = this.bCryptPasswordEncoder.encode(registrationModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        this.userRepository.save(user);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        User user = this.userRepository.findOneByUsername(username);

        return user.getId();
    }

    @Override
    public String getFullNameByUsername(String username) {
        User user = this.userRepository.findOneByUsername(username);

        return user.getFullName();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(Errors.INVALID_CREDENTIALS);
        }

        return user;
    }
}
