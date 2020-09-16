package com.kinglead.demo.dao;

import com.kinglead.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNameAndPassword(String name, String password);

}
