# WAD Lab 6 — Search with Postgres and Elasticsearch

## Domain Model

![Domain model](img/domain.png)

This lab has **two stages**, each using a different search technology and a different set of entities.

---

## Stage 1 — Postgres (package `first`)

**Entities:** `Book`, `Author`, `Genre`

Using Spring Data JPA and PostgreSQL, implement the missing code:

1. **`BookRepository`** — complete the JPQL `@Query` in `searchBooks()`.
   Add a `WHERE` clause that matches the query (case-insensitive, partial match) across:
   - `Book.title`
   - `Book.description`
   - `Author.name`
   - `Genre.name`

2. **`BookSearchService`**
   - In `search()`: call the repository, map each `Book` to a `BookSearchHitDto`, and collect the results.
   - In `toHit()`: convert a `Book` entity to `BookDto` (including `AuthorDto` and `GenreDto`).

3. **`BookSearchController`** — delegate to `BookSearchService` and return the response.

---

## Stage 2 — Elasticsearch (package `second`)

**Entities:** `ProductDocument` (with nested `CategoryInfo`, `ManufacturerInfo`, `TagInfo`, `ReviewInfo`)

Using Spring Data Elasticsearch, implement the missing code:

1. **`ProductSearchService`**
   - Build a `NativeQuery` with a `bool` → `should` combining:
     - A `multi_match` on `title` (boost ×3), `description` (boost ×2), `category.name`, `manufacturer.name`
     - A `nested` query on path `tags` matching `tags.name`
     - A `nested` query on path `reviews` matching `reviews.reviewerName` and `reviews.comment`
   - Map each `SearchHit<ProductDocument>` to a `ProductSearchHitDto` (use `hit.getScore()` for relevance).
   - In `toDto()`: convert the document to `ProductDto`, handling nulls gracefully.

2. **`ProductSearchController`** — delegate to `ProductSearchService` and return the response.

---

## Running the Lab

### Start the services (Postgres and Elasticsearch)
```
docker compose up -d
```

### Run the application
```
./gradlew bootRun
```

Then open [http://localhost:8080](http://localhost:8080) and search. The left panel shows Postgres/Book results, the right panel shows Elasticsearch/Product results.

### Stop the services
```
docker compose down
```

### Remove data (optional)
```
docker compose down -v
```

## Connection Info

| Service       | Host        | Port   | Credentials              |
|---------------|-------------|--------|--------------------------|
| PostgreSQL    | `localhost` | `5432` | `app` / `app_password`   |
| Elasticsearch | `localhost` | `9200` | —                        |

Database name: `app_db`. Edit `docker-compose.yml` to change ports or credentials.
