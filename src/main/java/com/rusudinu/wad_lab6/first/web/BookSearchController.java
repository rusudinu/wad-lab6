package com.rusudinu.wad_lab6.first.web;

import com.rusudinu.wad_lab6.first.dto.BookSearchResponseDto;
import com.rusudinu.wad_lab6.first.service.BookSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/postgres")
public class BookSearchController {

	private final BookSearchService bookSearchService;

	public BookSearchController(BookSearchService bookSearchService) {
		this.bookSearchService = bookSearchService;
	}

	@GetMapping
	public BookSearchResponseDto search(@RequestParam(name = "q", defaultValue = "") String query) {
		// TODO: Delegate to bookSearchService.search(query) and return the result.
		return null;
	}
}
