package com.pfa.project.Repository;

import com.pfa.project.Entities.Category;
import com.pfa.project.Entities.Quiz;
import com.pfa.project.Entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends  JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByNameContaining(String name);

    @Query(value = "SELECT email FROM user WHERE email = :email ", nativeQuery = true)
    List<String> checkUserEmail(@Param("email") String email);

    @Query(value = "SELECT password FROM user WHERE email = :email", nativeQuery = true)
    String checkUserPasswordByEmail(@Param("email") String email);

    @Query(value = " SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User GetUserDetailsByEmail(@Param("email") String email);


}
