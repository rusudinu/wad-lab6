package com.rusudinu.wad_lab6.second.dto;

import java.util.List;

public record ProductSearchResponseDto(String query, long executionTimeMs, List<ProductSearchHitDto> results) {
}
