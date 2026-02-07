package com.rusudinu.wad_lab6.search.web;

import com.rusudinu.wad_lab6.search.dto.SearchResponseDto;
import com.rusudinu.wad_lab6.search.service.ElasticsearchSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/elasticsearch")
public class ElasticsearchSearchController {

	private final ElasticsearchSearchService elasticsearchSearchService;

	public ElasticsearchSearchController(ElasticsearchSearchService elasticsearchSearchService) {
		this.elasticsearchSearchService = elasticsearchSearchService;
	}

	@GetMapping
	public SearchResponseDto search(@RequestParam(name = "q", defaultValue = "") String query) {
		return elasticsearchSearchService.search(query);
	}
}
