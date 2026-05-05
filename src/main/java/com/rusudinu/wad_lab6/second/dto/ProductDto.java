package com.rusudinu.wad_lab6.second.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDto(
		String id,
		String title,
		String description,
		BigDecimal price,
		LocalDate createdAt,
		CategoryDto category,
		ManufacturerDto manufacturer,
		List<TagDto> tags,
		List<ReviewDto> reviews
) {
}
