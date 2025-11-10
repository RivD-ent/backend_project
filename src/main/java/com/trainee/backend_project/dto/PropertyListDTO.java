package com.trainee.backend_project.dto;

import java.math.BigDecimal;
import java.util.List;

public class PropertyListDTO {
    public Long id;
    public String title;
    public BigDecimal price;
    public String city;
    public String district;
    public String firstImageUrl;
    public List<String> amenities;
}
