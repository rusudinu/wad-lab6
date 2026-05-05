package com.rusudinu.wad_lab6.second.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "products", createIndex = false)
@Getter
@Setter
@NoArgsConstructor
public class ProductDocument {

	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String title;

	@Field(type = FieldType.Text)
	private String description;

	@Field(type = FieldType.Double)
	private BigDecimal price;

	@Field(type = FieldType.Date)
	private LocalDateTime createdAt;

	@Field(type = FieldType.Object)
	private CategoryInfo category;

	@Field(type = FieldType.Object)
	private ManufacturerInfo manufacturer;

	@Field(type = FieldType.Nested)
	private List<TagInfo> tags;

	@Field(type = FieldType.Nested)
	private List<ReviewInfo> reviews;

	@Getter
	@Setter
	@NoArgsConstructor
	public static class CategoryInfo {
		@Field(type = FieldType.Text)
		private String name;
		@Field(type = FieldType.Text)
		private String description;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ManufacturerInfo {
		@Field(type = FieldType.Text)
		private String name;
		@Field(type = FieldType.Keyword)
		private String country;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class TagInfo {
		@Field(type = FieldType.Text)
		private String name;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ReviewInfo {
		@Field(type = FieldType.Text)
		private String reviewerName;
		@Field(type = FieldType.Integer)
		private Integer rating;
		@Field(type = FieldType.Text)
		private String comment;
	}
}
