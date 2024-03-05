package com.example.demo.controllers;

import com.example.demo.dtos.FlatAnnouncementCreateRequest;
import com.example.demo.dtos.FlatAnnouncementResponse;
import com.example.demo.entity.Favorites;
import com.example.demo.mapper.FlatAnnouncementMapper;
import com.example.demo.model.FlatAnnouncement;
import com.example.demo.repository.FavoritesRepository;
import com.example.demo.service.AnnouncementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/announcements")
//@CrossOrigin(origins = "http://localhost:5173/createAnnouncement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final FavoritesRepository favoritesRepository;
    private final FlatAnnouncementMapper flatAnnouncementMapper;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<FlatAnnouncementResponse> getAllAnnouncements() {
        return flatAnnouncementMapper.toResponse(announcementService.getAnnouncementAll());
    }

    @GetMapping("/{announcementId}")
    public FlatAnnouncementResponse getAnnouncement(@PathVariable("announcementId") Long announcementId) {
        return flatAnnouncementMapper.toResponse(announcementService.findAnnouncementById(announcementId));
    }

    @GetMapping("/favorites/{userId}")
    public List<Favorites> getUserFavorites(@PathVariable Long userId) {
        return favoritesRepository.findAllByUserId(userId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FlatAnnouncementResponse createAnnouncement(
            @ModelAttribute("announcementData") String announcementData,
            @ModelAttribute("announcementFiles") MultipartFile[] announcementFiles,
            @AuthenticationPrincipal UserDetails principal) {

        FlatAnnouncementCreateRequest createRequest;
        try {
            createRequest = objectMapper.readValue(announcementData, FlatAnnouncementCreateRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        if (createRequest == null) {
            throw new RuntimeException("Ошибка чтения запроса");
        }
        FlatAnnouncement flatAnnouncement = flatAnnouncementMapper.toAnnouncement(createRequest);
        return flatAnnouncementMapper.toResponse(
                announcementService.createAnnouncement(flatAnnouncement, announcementFiles, principal));
    }

    @PostMapping("/{announcementId}/photos")
    public void addPhoto(@RequestParam("file") MultipartFile file,
                         @PathVariable("announcementId") Long announcementId) {
        announcementService.addFlatPhoto(file, announcementId);
    }
}