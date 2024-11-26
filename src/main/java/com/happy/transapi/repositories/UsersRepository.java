package com.happy.transapi.repositories;

import com.happy.transapi.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("SELECT t FROM Users t where t.email=:email")
    Users findUserByEmail(@Param("email") String email);

    @Query("SELECT t FROM Users t where t.email=:email and t.password=:password")
    Users findLoginUser(@Param("email") String email, @Param("password") String password);
}

