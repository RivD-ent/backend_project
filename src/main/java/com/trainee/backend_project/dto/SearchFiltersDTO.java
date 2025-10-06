package com.trainee.backend_project.dto;

import java.math.BigDecimal;
import java.util.List;

public class SearchFiltersDTO {
    public BigDecimal minPrice;
    public BigDecimal maxPrice;
    public String city;
    public Integer minBedrooms;
    public Integer maxBedrooms;
    public Integer minBathrooms;
    public Integer maxBathrooms;
    public String operationType;
    public String propertyType;
    public List<Integer> amenityIds;
}
