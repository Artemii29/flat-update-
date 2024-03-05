package com.example.demo.mapper;

import com.example.demo.dtos.FlatPhotoDto;
import com.example.demo.entity.FlatPhoto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlatPhotoMapper {
    FlatPhotoDto toDto(FlatPhoto flatPhoto);
}
