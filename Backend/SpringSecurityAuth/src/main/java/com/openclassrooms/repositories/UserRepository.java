package com.openclassrooms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.models.MyUser;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByEmail(String email);
}
