package com.accenture.service.vehicule;

import com.accenture.repository.VehiculeDao;
import com.accenture.service.dto.vehicule.VehiculeDto;
import com.accenture.service.mapper.VehiculeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements VehiculeService{

    private VehiculeDao vehiculeDao;
    private VehiculeMapper vehiculeMapper;

    @Override
    public List<VehiculeDto> trouverTous() {
        return vehiculeDao.findAll().stream().map(vehicule -> vehiculeMapper.toVehiculeDto(vehicule)).toList();
    }
}
