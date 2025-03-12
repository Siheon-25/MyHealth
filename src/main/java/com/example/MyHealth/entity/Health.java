package com.example.MyHealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Getter
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String date;
    @Column
    private String content;

    public void patch(Health health) {
        if (health.date != null)
            this.date = health.date;
        if(health.date != null)
            this.content = health.content;
    }

}
