package com.example.demo.repository;

import com.example.demo.entity.FlatAnnouncementEntity;
import com.example.demo.entity.FlatStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatAnnouncementRepository extends JpaRepository<FlatAnnouncementEntity, Long> {
    List<FlatAnnouncementEntity> findByflatStyleAndRooms(FlatStyle flatStyle, int rooms);

}