package com.trainee.backend_project.dto;

import java.util.List;

public class PropertyPageDTO {
    public List<PropertyListDTO> content;
    public int currentPage;
    public int totalPages;
    public long totalElements;
    public int pageSize;
}
