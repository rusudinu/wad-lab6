package com.rusudinu.wad_lab6.first.service;

import com.rusudinu.wad_lab6.first.dto.*;
import com.rusudinu.wad_lab6.first.entity.Book;
import com.rusudinu.wad_lab6.first.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookSearchService {

	private final BookRepository bookRepository;

	public BookSearchService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public BookSearchResponseDto search(String query) {
		long started = System.nanoTime();
		// TODO: Call bookRepository.searchBooks(query), map each Book entity to a
		//       BookSearchHitDto using the toHit() method, and collect into a list.
		List<BookSearchHitDto> results = List.of();
		long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
		return new BookSearchResponseDto(query, elapsedMs, results);
	}

	private BookSearchHitDto toHit(Book book) {
		// TODO: Map the Book entity to a BookDto (including its AuthorDto and GenreDto),
		//       then wrap it in a BookSearchHitDto.
		return null;
	}
}
