package com.example.demo.service;

import com.example.demo.entity.FlatAnnouncementEntity;
import com.example.demo.entity.FlatPhoto;
import com.example.demo.entity.FlatStyle;
import com.example.demo.entity.User;
import com.example.demo.mapper.FlatAnnouncementMapper;
import com.example.demo.model.FlatAnnouncement;
import com.example.demo.repository.FlatAnnouncementRepository;
import com.example.demo.repository.FlatPhotoRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnnouncementService {
    private final FlatAnnouncementRepository flatAnnouncementRepository;
    private final UserRepository userRepository;
    private final FlatPhotoRepository flatPhotoRepository;
    private final FlatAnnouncementMapper flatAnnouncementMapper;

    @Transactional
    public FlatAnnouncement findAnnouncementById(Long id) {
        FlatAnnouncementEntity announcementEntity = flatAnnouncementRepository.findById(id).orElseThrow();
        FlatAnnouncement flatAnnouncement = flatAnnouncementMapper.toAnnouncement(announcementEntity);
        setFlatAnnouncementPhotos(flatAnnouncement);
        return flatAnnouncement;
    }

    @Transactional
    public List<FlatAnnouncement> getAnnouncementAll() {
        return flatAnnouncementMapper.toAnnouncement(flatAnnouncementRepository.findAll()).stream()
                .map(this::setFlatAnnouncementPhotos)
                .toList();
    }

    public void deleteAnnouncement(Long announcementId) {
        flatPhotoRepository.deleteAllByFlatAnnouncementId(announcementId);
        flatAnnouncementRepository.deleteById(announcementId);
    }

    public FlatAnnouncement updateAnnouncement(Long announcementId, FlatAnnouncement updates) {

        FlatAnnouncement flatAnnouncement = findAnnouncementById(announcementId);

        if (updates.getTitle() != null) {
            flatAnnouncement.setTitle(updates.getTitle());
        }
        if (updates.getDescription() != null) {
            flatAnnouncement.setDescription(updates.getDescription());
        }
        if (updates.getPrice() != null) {
            flatAnnouncement.setPrice(updates.getPrice());
        }
        if (updates.getArea() != null) {
            flatAnnouncement.setArea(updates.getArea());
        }
        if (updates.getRooms() != null) {
            flatAnnouncement.setRooms(updates.getRooms());
        }
        // TODO: доделать обновление

        FlatAnnouncementEntity flatAnnouncementEntity = flatAnnouncementMapper.toEntity(flatAnnouncement);
        flatAnnouncementRepository.save(flatAnnouncementEntity);
        return flatAnnouncementMapper.toAnnouncement(flatAnnouncementEntity);
    }

    @Transactional
    public FlatAnnouncement createAnnouncement(FlatAnnouncement flatAnnouncement,
                                               MultipartFile[] files,
                                               UserDetails principal) {
        String username = principal.getUsername();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        flatAnnouncement.setUser(user);

        //установить ссылку на пользователя из бд
        //пользоваетлю в список добавляем новое объявление добавить проверки
        // Сохранить объявление
        FlatAnnouncementEntity flatAnnouncementEntity = flatAnnouncementMapper.toEntity(flatAnnouncement);
        flatAnnouncementRepository.save(flatAnnouncementEntity);

        // Сохранить фото для объявления
        if (files != null) {
            for (MultipartFile file : files) {
                addFlatPhoto(file, flatAnnouncementEntity);
            }
        }
        return findAnnouncementById(flatAnnouncementEntity.getId());
    }

    public List<FlatAnnouncementEntity> filterAnnouncementsByStyleAndRooms(FlatStyle flatStyle, int rooms) {
        return flatAnnouncementRepository.findByflatStyleAndRooms(flatStyle, rooms);
    }

    public void addFlatPhoto(MultipartFile file, Long announcementId) {
        // Создать экземпляр FlatPhoto на основе MultipartFile
        // проверять на расширение и размер
        FlatAnnouncementEntity announcement = flatAnnouncementRepository.findById(announcementId)
                .orElseThrow(() -> new EntityNotFoundException("Объявление %s не найдено".formatted(announcementId)));

        addFlatPhoto(file, announcement);
    }

    private void addFlatPhoto(MultipartFile file, FlatAnnouncementEntity flatAnnouncementEntity) {
        try {
            FlatPhoto flatPhoto = new FlatPhoto();
            flatPhoto.setFileName(file.getOriginalFilename());
            flatPhoto.setFlatAnnouncement(flatAnnouncementEntity);
            flatPhoto.setData(file.getBytes());
            flatPhotoRepository.save(flatPhoto);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось сохранить фото " + e.getMessage());
        }
    }

    private FlatAnnouncement setFlatAnnouncementPhotos(FlatAnnouncement flatAnnouncement) {
        flatAnnouncement.setFlatPhotos(getAnnouncementPhotos(flatAnnouncement.getId()));
        return flatAnnouncement;
    }

    private List<FlatPhoto> getAnnouncementPhotos(Long announcementId) {
        return flatPhotoRepository.findAllByFlatAnnouncementId(announcementId);
    }

}
