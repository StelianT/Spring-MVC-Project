package com.motivation.repository;

import com.motivation.entities.BasicUser;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicUserRepository extends UserRepository<BasicUser> {
}
