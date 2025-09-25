package com.example.SMG_BBS.repository;

import com.example.SMG_BBS.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c " +
            "FROM Comment c " +
            "INNER JOIN c.user u " +
            "ORDER BY c.createdDate ASC")
    List<Comment> findAllComment();

}
