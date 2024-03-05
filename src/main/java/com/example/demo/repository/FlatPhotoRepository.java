package com.example.demo.repository;

import com.example.demo.entity.FlatPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatPhotoRepository extends JpaRepository<FlatPhoto, Long> {
    List<FlatPhoto> findAllByFlatAnnouncementId(Long announcementId);

    void deleteAllByFlatAnnouncementId(Long announcementId);
}
