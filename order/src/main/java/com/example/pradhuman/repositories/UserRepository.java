package com.example.pradhuman.repositories;

import com.example.pradhuman.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "Select * from new2 u where u.disabled is false", nativeQuery = true)
    List<User> getAllUsers();
}
