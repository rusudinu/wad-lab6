package com.rusudinu.wad_lab6.second.web;

import com.rusudinu.wad_lab6.second.dto.ProductSearchResponseDto;
import com.rusudinu.wad_lab6.second.service.ProductSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/elasticsearch")
public class ProductSearchController {

	private final ProductSearchService productSearchService;

	public ProductSearchController(ProductSearchService productSearchService) {
		this.productSearchService = productSearchService;
	}

	@GetMapping
	public ProductSearchResponseDto search(@RequestParam(name = "q", defaultValue = "") String query) {
		// TODO: Delegate to productSearchService.search(query) and return the result.
		return null;
	}
}
