package com.example.demo.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "tb_favorites")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private FlatAnnouncementEntity flatAnnouncement;


    // getters and setters
}