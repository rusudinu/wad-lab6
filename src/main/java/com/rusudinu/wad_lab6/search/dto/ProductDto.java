package com.rusudinu.wad_lab6.search.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDto(
		Long id,
		String title,
		String description,
		BigDecimal price,
		LocalDateTime createdAt,
		CategoryDto category,
		ManufacturerDto manufacturer,
		List<TagDto> tags,
		List<ReviewDto> reviews
) {
}
