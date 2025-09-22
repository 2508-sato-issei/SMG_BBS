package com.example.SMG_BBS.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private Integer branchId;

    @Column
    private Integer departmentId;

    @Column
    private byte isStopped;

    @Column(insertable = false, updatable = false)
    private Timestamp createdDate;

    @Column(insertable = false, updatable = false)
    private  Timestamp updatedDate;

}
