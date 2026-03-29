package com.rusudinu.wad_lab6.first.repository;

import com.rusudinu.wad_lab6.first.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

	// TODO: Write a JPQL query that searches across Book title, description,
	//       Author name, and Genre name using case-insensitive LIKE matching.
	//       Use JOIN FETCH for author and genre to avoid N+1 queries.
	@Query("""
			SELECT b FROM Book b
			JOIN FETCH b.author
			JOIN FETCH b.genre
			""")
	List<Book> searchBooks(@Param("q") String query);
}
