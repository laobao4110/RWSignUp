package com.example.rwsignup.service;
import com.example.rwsignup.dto.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>{
    @Query("SELECT p.id,p.username,p.email,p.password FROM User p WHERE p.email = :email ")
    List<User>  findByEmail(@Param("email") String email, Pageable pageable);
}
