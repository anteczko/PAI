package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Boolean status=false;//false
    private Timestamp registrationTimestamp=new Timestamp(System.currentTimeMillis());//date.now()

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_to_tasks",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="task_id")
    )
    private Set<task> tasks=new HashSet<>();

}
