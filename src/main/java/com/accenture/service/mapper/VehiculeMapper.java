package com.accenture.service.mapper;


import com.accenture.repository.VehiculeDao;
import com.accenture.repository.entity.vehicule.Vehicule;
import com.accenture.service.dto.vehicule.VehiculeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    VehiculeDto toVehiculeDto (Vehicule vehicule);
}
