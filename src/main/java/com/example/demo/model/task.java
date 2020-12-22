package com.example.demo.model;

import com.example.demo.model.enums.Status;
import com.example.demo.model.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int taskId;
    private String title;
    private String descrition;
    private Timestamp dateAdded=new Timestamp(System.currentTimeMillis());
    private Type type;
    private Status status;

    @ManyToOne(
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private user user;
}
