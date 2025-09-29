package com.example.SMG_BBS.repository;

import com.example.SMG_BBS.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByOrderByIdAsc();

    // アカウント名で検索
    Optional<User> findByAccount(String account);

}
