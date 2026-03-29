package com.rusudinu.wad_lab6.first;

import com.rusudinu.wad_lab6.first.entity.Author;
import com.rusudinu.wad_lab6.first.entity.Book;
import com.rusudinu.wad_lab6.first.entity.Genre;
import com.rusudinu.wad_lab6.first.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Configuration
public class DataSeeder {

	@Bean
	@Transactional
	CommandLineRunner seedBooks(BookRepository bookRepository, EntityManager em) {
		return args -> {
			if (bookRepository.count() > 0) return;

			Genre fiction = createGenre(em, "Fiction", "Literary and general fiction");
			Genre sciFi = createGenre(em, "Science Fiction", "Futuristic and speculative stories");
			Genre mystery = createGenre(em, "Mystery", "Crime and detective stories");

			Author orwell = createAuthor(em, "George Orwell", "United Kingdom");
			Author asimov = createAuthor(em, "Isaac Asimov", "United States");
			Author christie = createAuthor(em, "Agatha Christie", "United Kingdom");

			createBook(em, "1984", "A dystopian novel about totalitarian surveillance", 1949, orwell, fiction);
			createBook(em, "Animal Farm", "An allegorical novella about a group of farm animals", 1945, orwell, fiction);
			createBook(em, "Foundation", "A science fiction saga about the fall of a galactic empire", 1951, asimov, sciFi);
			createBook(em, "I, Robot", "A collection of robot stories exploring artificial intelligence", 1950, asimov, sciFi);
			createBook(em, "Murder on the Orient Express", "A detective mystery set on a famous train", 1934, christie, mystery);
			createBook(em, "The ABC Murders", "A serial killer mystery solved by Hercule Poirot", 1936, christie, mystery);
		};
	}

	private Genre createGenre(EntityManager em, String name, String description) {
		Genre g = new Genre();
		g.setName(name);
		g.setDescription(description);
		em.persist(g);
		return g;
	}

	private Author createAuthor(EntityManager em, String name, String country) {
		Author a = new Author();
		a.setName(name);
		a.setCountry(country);
		em.persist(a);
		return a;
	}

	private void createBook(EntityManager em, String title, String description, int year, Author author, Genre genre) {
		Book b = new Book();
		b.setTitle(title);
		b.setDescription(description);
		b.setPublicationYear(year);
		b.setAuthor(author);
		b.setGenre(genre);
		em.persist(b);
	}
}
