package com.example.futdabandaapi.repositories;

import com.example.futdabandaapi.dtos.UserDto;
import com.example.futdabandaapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    @Query("select new com.example.futdabandaapi.dtos.UserDto(u.id, u.fullName, u.email, u.userRole, u.createdAt) from User u")
    List<UserDto> findAllUsers();

    @Query("select new com.example.futdabandaapi.entities.User(u.id, u.fullName, u.email, u.password, u.userRole, u.createdAt) from User u where u.email = :email")
    User findByUserEmail(String email);

    User findByUserRole(String role);

    List<User> findAllByUserRoleAndIdNot(String userRole, Long id);
}
