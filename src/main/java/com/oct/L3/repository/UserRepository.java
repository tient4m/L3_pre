package com.oct.L3.repository;

import com.oct.L3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Tìm kiếm người dùng bằng tên người dùng
    Optional<User> findByUserName(String userName);
}
