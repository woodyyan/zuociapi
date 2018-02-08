package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByAppKey(String appKey);
}
