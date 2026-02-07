package com.rusudinu.wad_lab6.search.dto;

import java.util.List;

public record SearchResponseDto(String query, long executionTimeMs, List<SearchHitDto> results) {
}
