package com.example.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT r.role FROM role r " +
            "JOIN user_role ur ON ur.role_id = r.id " +
            "JOIN users u ON u.id = ur.user_id " +
            "WHERE u.email = :email", nativeQuery = true)

    List<String> findRoleNamesByEmail(@Param("email") String email);

}
