package com.example.futdabandaapi.repository;

import com.example.futdabandaapi.dto.UserDto;
import com.example.futdabandaapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    User findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.userRole <> 'ADMIN'")
    Page<User> findAllExceptAdmin(Pageable pageable);

    User findByUserRole(String role);

}
