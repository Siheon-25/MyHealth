package com.example.MyHealth.dto;

import com.example.MyHealth.entity.Health;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class HealthForm {
    private Long id;
    private String date;
    private String content;

    public Health toEntity() {
        return new Health(id,date,content);
    }


}
