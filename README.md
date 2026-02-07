# Wad Lab 6 - Search with Postgres and Elasticsearch

## Laboratory request

Based on the following domain model:
![Domain model](img/domain.png)

Use:
1. Spring Data JPA for PostgreSQL access
2. Spring Data Elasticsearch
3. Fill in the missing code to make the application work.
4. Display:
- Search results
- Execution time
- Relevance score (where applicable)

# Helper tutorial

## Start the services (Postgres and Elasticsearch)
From the repo root:
```
docker compose up -d
```

## Stop the services
```
docker compose down
```

## Remove data (optional)
```
docker compose down -v
```

## Connection info
Postgres:
- Host: `localhost`
- Port: `5432`
- User: `app`
- Password: `app_password`
- Database: `app_db`

Elasticsearch:
- Host: `localhost`
- Port: `9200`

## Customize
Edit `docker-compose.yml` to change ports or credentials.
