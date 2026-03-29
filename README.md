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

### Expected Results

**Query: `orwell`** — matches Author.name → 2 results

| Title        | Author        | Genre   | Year |
|--------------|---------------|---------|------|
| 1984         | George Orwell | Fiction | 1949 |
| Animal Farm  | George Orwell | Fiction | 1945 |

**Query: `mystery`** — matches Genre.name → 2 results

| Title                          | Author          | Genre   | Year |
|--------------------------------|-----------------|---------|------|
| Murder on the Orient Express   | Agatha Christie | Mystery | 1934 |
| The ABC Murders                | Agatha Christie | Mystery | 1936 |

**Query: `robot`** — matches Book.title and Book.description → 1 result

| Title    | Author       | Genre           | Year |
|----------|--------------|-----------------|------|
| I, Robot | Isaac Asimov | Science Fiction | 1950 |

**Query: `farm animals`** — matches Book.description (contains "farm animals" as substring) → 1 result

| Title       | Author        | Genre   | Year |
|-------------|---------------|---------|------|
| Animal Farm | George Orwell | Fiction | 1945 |

**Query: `galactic empire`** — matches Book.description (contains "galactic empire") → 1 result

| Title      | Author       | Genre           | Year |
|------------|--------------|-----------------|------|
| Foundation | Isaac Asimov | Science Fiction | 1951 |

> **Note:** Postgres uses `LIKE '%query%'` which treats the entire input as a single substring.
> `"farm animals"` matches because that exact phrase appears in the description.
> A query like `"orwell mystery"` would return **0 results** — no single field contains that exact substring.

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

### Expected Results

**Query: `wireless`** — matches description and tag → 3 results

| Title           | Category    | Manufacturer | Price    | Why it matched                  |
|-----------------|-------------|--------------|----------|---------------------------------|
| WH-1000XM5     | Electronics | Sony         | $349.99  | description + tag `wireless`    |
| Galaxy Buds Pro | Electronics | Samsung      | $199.99  | description + tag `wireless`    |
| PS5 Controller  | Electronics | Sony         | $69.99   | description contains "wireless" |

**Query: `Samsung`** — matches manufacturer.name → 2 results

| Title           | Category    | Manufacturer | Price   |
|-----------------|-------------|--------------|---------|
| Galaxy S24      | Electronics | Samsung      | $899.99 |
| Galaxy Buds Pro | Electronics | Samsung      | $199.99 |

**Query: `running`** — matches tag → 2 results

| Title                 | Category | Manufacturer | Price   |
|-----------------------|----------|--------------|---------|
| Air Max 90            | Clothing | Nike         | $129.99 |
| Dri-FIT Running Shirt | Clothing | Nike         | $34.99  |

**Query: `wireless noise cancelling`** — Elasticsearch tokenizes into 3 terms; each term matches independently and scores are combined → 3 results (ordered by relevance)

| Title           | Category    | Manufacturer | Price    | Why it matched                                                  |
|-----------------|-------------|--------------|----------|-----------------------------------------------------------------|
| WH-1000XM5     | Electronics | Sony         | $349.99  | matches all 3 terms (description: "noise-cancelling wireless")  |
| Galaxy Buds Pro | Electronics | Samsung      | $199.99  | matches "wireless" + "noise cancellation" in description        |
| PS5 Controller  | Electronics | Sony         | $69.99   | matches "wireless" in description                               |

**Query: `great sound`** — matches across review comments → 2 results

| Title           | Category    | Manufacturer | Price    | Why it matched                                          |
|-----------------|-------------|--------------|----------|---------------------------------------------------------|
| WH-1000XM5     | Electronics | Sony         | $349.99  | review: "Excellent **sound** quality"                   |
| Galaxy Buds Pro | Electronics | Samsung      | $199.99  | review: "incredible **sound**"                          |

> **Note:** Unlike Postgres, Elasticsearch tokenizes multi-word queries into individual terms.
> `"wireless noise cancelling"` becomes three separate terms that each contribute to the relevance score.
> A document matching more terms ranks higher. This is why Elasticsearch is better suited for natural-language search.

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
