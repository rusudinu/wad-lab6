package com.rusudinu.wad_lab6.search.service;

import com.rusudinu.wad_lab6.search.dto.SearchHitDto;
import com.rusudinu.wad_lab6.search.dto.SearchResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticsearchSearchService {

	public SearchResponseDto search(String query) {
		long started = System.nanoTime();
		List<SearchHitDto> results = List.of();
		long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
		return new SearchResponseDto(query, elapsedMs, results);
	}
}
