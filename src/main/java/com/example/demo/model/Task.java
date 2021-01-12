package com.example.demo.model;

import com.example.demo.model.enums.Status;
import com.example.demo.model.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int taskId;
    private String title;
    private String description;
    private Timestamp dateAdded=new Timestamp(System.currentTimeMillis());
    private Type type;
    private Status status;

    @ManyToOne(
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private User user;

    public Task(String title, String description, Type type, Status status, User user) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.status = status;
        this.user = user;
    }

}
