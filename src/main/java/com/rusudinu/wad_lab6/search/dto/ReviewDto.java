package com.rusudinu.wad_lab6.search.dto;

import java.time.LocalDateTime;

public record ReviewDto(
		Long id,
		String reviewerName,
		Integer rating,
		String comment,
		LocalDateTime createdAt
) {
}
