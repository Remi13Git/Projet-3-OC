package com.openclassrooms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
