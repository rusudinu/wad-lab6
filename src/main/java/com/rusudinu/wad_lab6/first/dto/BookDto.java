package com.rusudinu.wad_lab6.first.dto;

import java.time.LocalDateTime;

public record BookDto(
		Long id,
		String title,
		String description,
		Integer publicationYear,
		LocalDateTime createdAt,
		AuthorDto author,
		GenreDto genre
) {
}
