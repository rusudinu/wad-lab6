package com.rusudinu.wad_lab6.second.service;

import com.rusudinu.wad_lab6.second.document.ProductDocument;
import com.rusudinu.wad_lab6.second.dto.*;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductSearchService {

	private final ElasticsearchOperations elasticsearchOperations;

	public ProductSearchService(ElasticsearchOperations elasticsearchOperations) {
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public ProductSearchResponseDto search(String query) {
		long started = System.nanoTime();

		// TODO: Build a NativeQuery that searches across:
		//       - title (boosted x3), description (boosted x2), category.name, manufacturer.name  (use multi_match)
		//       - tags.name            (use a nested query on path "tags")
		//       - reviews.reviewerName and reviews.comment  (use a nested query on path "reviews")
		//       Combine all clauses with bool → should.
		NativeQuery searchQuery = NativeQuery.builder()
				.withQuery(q -> q.matchAll(m -> m))
				.build();

		SearchHits<ProductDocument> hits = elasticsearchOperations.search(searchQuery, ProductDocument.class);

		// TODO: Map each SearchHit<ProductDocument> to a ProductSearchHitDto.
		//       Use hit.getContent() for the document and hit.getScore() for relevance.
		//       Convert the document to a ProductDto using the toDto() helper.
		List<ProductSearchHitDto> results = List.of();

		long elapsedMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - started);
		return new ProductSearchResponseDto(query, elapsedMs, results);
	}

	private ProductDto toDto(ProductDocument doc) {
		// TODO: Map the ProductDocument to a ProductDto.
		//       Convert nested objects (category, manufacturer, tags, reviews) to their DTOs.
		//       Handle nulls gracefully — use empty lists for missing tags/reviews.
		return null;
	}
}
