package com.bulke.repository;

import com.bulke.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findByUserId(Integer userId);
}
