package com.cnems.repositories;

import com.cnems.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
