package com.example.demo.model;

import com.example.demo.entity.FlatPhoto;
import com.example.demo.entity.FlatStyle;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
public class FlatAnnouncement {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private Float area;
    private Integer rooms;
    private FlatStyle flatStyle;
    private User user;
    private List<FlatPhoto> flatPhotos;
}
