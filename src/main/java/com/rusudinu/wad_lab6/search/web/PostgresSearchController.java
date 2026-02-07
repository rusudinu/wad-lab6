package com.rusudinu.wad_lab6.search.web;

import com.rusudinu.wad_lab6.search.dto.SearchResponseDto;
import com.rusudinu.wad_lab6.search.service.PostgresSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/postgres")
public class PostgresSearchController {

	private final PostgresSearchService postgresSearchService;

	public PostgresSearchController(PostgresSearchService postgresSearchService) {
		this.postgresSearchService = postgresSearchService;
	}

	@GetMapping
	public SearchResponseDto search(@RequestParam(name = "q", defaultValue = "") String query) {
		return postgresSearchService.search(query);
	}
}
