package com.rusudinu.wad_lab6.search.service;

import com.rusudinu.wad_lab6.search.dto.SearchHitDto;
import com.rusudinu.wad_lab6.search.dto.SearchResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PostgresSearchService {

	public SearchResponseDto search(String query) {
		long started = System.nanoTime();
		// TODO: Implement the JPA query/repository call for the required fields.
		// TODO: Map entities to ProductDto and wrap them in SearchHitDto (score can be null for Postgres).
		List<SearchHitDto> results = List.of();
		long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
		return new SearchResponseDto(query, elapsedMs, results);
	}
}
