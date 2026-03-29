package com.rusudinu.wad_lab6.first.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String country;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Book> books = new ArrayList<>();
}
