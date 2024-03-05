package com.example.demo.dtos;

import com.example.demo.entity.FlatStyle;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FlatAnnouncementCreateRequest {
    private String title;
    private String description;
    private Integer price;
    private Float area;
    private Integer rooms;
    private FlatStyle flatStyle;
}
