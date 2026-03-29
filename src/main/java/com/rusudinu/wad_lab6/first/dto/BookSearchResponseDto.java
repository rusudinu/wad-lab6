package com.rusudinu.wad_lab6.first.dto;

import java.util.List;

public record BookSearchResponseDto(String query, long executionTimeMs, List<BookSearchHitDto> results) {
}
