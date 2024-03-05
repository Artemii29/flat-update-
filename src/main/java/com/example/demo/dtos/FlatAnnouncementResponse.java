package com.example.demo.dtos;

import com.example.demo.entity.FlatStyle;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FlatAnnouncementResponse {
    private String title;
    private String description;
    private Integer price;
    private Float area;
    private Integer rooms;
    private FlatStyle flatStyle;
    private List<FlatPhotoDto> flatPhotos;
}
