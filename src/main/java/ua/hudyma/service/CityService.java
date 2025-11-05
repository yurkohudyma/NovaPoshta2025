package ua.hudyma.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.hudyma.domain.City;
import ua.hudyma.repository.CityRepository;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Log4j2
public class CityService {
    private final CityRepository cityRepository;

    public void createBatchCities(String[] cityNames) {
        cityRepository.saveAll(Arrays
                .stream(cityNames)
                .map(cityName -> {
                    var newCity = new City();
                    newCity.setCityName(cityName);
                    return newCity;
                }).toList());
        log.info(":::: City batch size = {} CREATED", cityNames.length);
    }

    public City getCityByID(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(
                () -> new EntityNotFoundException("City " + cityId + " NOT FOUND"));
    }
}
