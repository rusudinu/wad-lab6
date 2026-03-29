package com.rusudinu.wad_lab6.second;

import com.rusudinu.wad_lab6.second.document.ProductDocument;
import com.rusudinu.wad_lab6.second.document.ProductDocument.*;
import com.rusudinu.wad_lab6.second.repository.ProductDocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ElasticsearchDataSeeder {

	@Bean
	CommandLineRunner seedProducts(ProductDocumentRepository repo) {
		return args -> {
			if (repo.count() > 0) return;

			CategoryInfo electronics = category("Electronics", "Electronic devices and gadgets");
			CategoryInfo clothing = category("Clothing", "Apparel and fashion items");

			ManufacturerInfo samsung = manufacturer("Samsung", "South Korea");
			ManufacturerInfo nike = manufacturer("Nike", "United States");
			ManufacturerInfo sony = manufacturer("Sony", "Japan");

			repo.saveAll(List.of(
					product("Galaxy S24", "Flagship smartphone with AI features", new BigDecimal("899.99"),
							electronics, samsung,
							List.of(tag("smartphone"), tag("android")),
							List.of(review("Alice", 5, "Amazing phone with great camera"))),
					product("WH-1000XM5", "Premium noise-cancelling wireless headphones", new BigDecimal("349.99"),
							electronics, sony,
							List.of(tag("headphones"), tag("wireless")),
							List.of(review("Bob", 4, "Excellent sound quality and comfort"))),
					product("Air Max 90", "Classic running shoes with visible Air cushioning", new BigDecimal("129.99"),
							clothing, nike,
							List.of(tag("shoes"), tag("running")),
							List.of(review("Charlie", 5, "Comfortable and stylish sneakers"))),
					product("PlayStation 5 Controller", "DualSense wireless controller with haptic feedback", new BigDecimal("69.99"),
							electronics, sony,
							List.of(tag("gaming"), tag("controller")),
							List.of(review("Diana", 4, "Great haptic feedback for immersive gaming"))),
					product("Dri-FIT Running Shirt", "Lightweight moisture-wicking athletic shirt", new BigDecimal("34.99"),
							clothing, nike,
							List.of(tag("sportswear"), tag("running")),
							List.of(review("Eve", 3, "Good fabric but runs a bit small"))),
					product("Galaxy Buds Pro", "True wireless earbuds with active noise cancellation", new BigDecimal("199.99"),
							electronics, samsung,
							List.of(tag("earbuds"), tag("wireless")),
							List.of(review("Frank", 5, "Perfect fit and incredible sound")))
			));
		};
	}

	private ProductDocument product(String title, String desc, BigDecimal price,
									CategoryInfo cat, ManufacturerInfo mfr,
									List<TagInfo> tags, List<ReviewInfo> reviews) {
		ProductDocument p = new ProductDocument();
		p.setTitle(title);
		p.setDescription(desc);
		p.setPrice(price);
		p.setCreatedAt(LocalDateTime.now());
		p.setCategory(cat);
		p.setManufacturer(mfr);
		p.setTags(tags);
		p.setReviews(reviews);
		return p;
	}

	private CategoryInfo category(String name, String description) {
		CategoryInfo c = new CategoryInfo();
		c.setName(name);
		c.setDescription(description);
		return c;
	}

	private ManufacturerInfo manufacturer(String name, String country) {
		ManufacturerInfo m = new ManufacturerInfo();
		m.setName(name);
		m.setCountry(country);
		return m;
	}

	private TagInfo tag(String name) {
		TagInfo t = new TagInfo();
		t.setName(name);
		return t;
	}

	private ReviewInfo review(String reviewer, int rating, String comment) {
		ReviewInfo r = new ReviewInfo();
		r.setReviewerName(reviewer);
		r.setRating(rating);
		r.setComment(comment);
		return r;
	}
}
