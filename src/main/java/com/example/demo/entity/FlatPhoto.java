package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_photo")
public class FlatPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    @Lob
    private byte[] data;

    @ManyToOne
    private FlatAnnouncementEntity flatAnnouncement;
}