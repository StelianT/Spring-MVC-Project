package com.motivation.repository;

import com.motivation.entities.SocialUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialUserRepository extends UserRepository<SocialUser> {
}
