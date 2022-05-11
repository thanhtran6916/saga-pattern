package com.example.loansservice.mapper;

import com.example.loansservice.dto.LoansProfileDTO;
import com.example.loansservice.entity.LoansProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoansProfileMapper {

    LoansProfileMapper INSTANCE = Mappers.getMapper(LoansProfileMapper.class);

    LoansProfile toLoansProfile(LoansProfileDTO loansProfileDTO);

    LoansProfileDTO toLoansProfileDTO(LoansProfile loansProfile);
}
