package com.motivation.servicesImpl;

import com.motivation.entities.SocialUser;
import com.motivation.repository.SocialUserRepository;
import com.motivation.services.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

@Service
public class SocialUserServiceImpl implements SocialUserService {

    private final SocialUserRepository socialUserRepository;

    @Autowired
    public SocialUserServiceImpl(SocialUserRepository socialUserRepository) {
        this.socialUserRepository = socialUserRepository;
    }

    @Override
    public void registerOrLogin(User facebookUser) {
        String email = facebookUser.getEmail();
        String fullName = facebookUser.getName();
        SocialUser socialUser = this.socialUserRepository.findOneByUsername(email);
        if (socialUser == null) {
            socialUser = registerUser(email, fullName);
        }

        loginUser(socialUser);
    }

    private SocialUser registerUser(String email, String name) {
        SocialUser user = new SocialUser();
        user.setUsername(email);
        user.setFullName(name);
        user.setProvider("FACEBOOK");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        this.socialUserRepository.save(user);
        return user;
    }

    private void loginUser(SocialUser socialUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                socialUser, null, socialUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
