package com.example.SMG_BBS.repository;

import com.example.SMG_BBS.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByOrderByCreatedDateDesc();

    @Query("SELECT m " +
            "FROM Message m INNER JOIN m.user u " +
            "WHERE m.createdDate BETWEEN :start AND :end " +
            "ORDER BY m.createdDate DESC")
    List<Message> findAllMessage(@Param("start") Timestamp start, @Param("end") Timestamp end);

    @Query("SELECT m " +
            "FROM Message m INNER JOIN m.user u " +
            "WHERE m.createdDate BETWEEN :start AND :end " +
            "AND m.category LIKE CONCAT('%', :category, '%') " +
            "ORDER BY m.createdDate DESC")
    List<Message> findMessageByCategory(@Param("start") Timestamp start, @Param("end") Timestamp end, @Param("category") String category);
}
