package com.example.demo.mapper;

import com.example.demo.dtos.FlatAnnouncementCreateRequest;
import com.example.demo.dtos.FlatAnnouncementResponse;
import com.example.demo.entity.FlatAnnouncementEntity;
import com.example.demo.model.FlatAnnouncement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FlatPhotoMapper.class})
public interface FlatAnnouncementMapper {
    FlatAnnouncement toAnnouncement(FlatAnnouncementCreateRequest dto);

    FlatAnnouncement toAnnouncement(FlatAnnouncementEntity entity);

    List<FlatAnnouncement> toAnnouncement(List<FlatAnnouncementEntity> entity);

    FlatAnnouncementEntity toEntity(FlatAnnouncement flatAnnouncement);

    FlatAnnouncementResponse toResponse(FlatAnnouncement announcement);

    List<FlatAnnouncementResponse> toResponse(List<FlatAnnouncement> announcement);
}
