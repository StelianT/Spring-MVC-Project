package com.motivation.repository;

import com.motivation.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractUserRepository extends UserRepository<User> {
}
