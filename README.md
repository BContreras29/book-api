# Book API

A RESTful Spring Boot API for managing a book catalog. Built as a practice project to solidify four-layer Spring Boot architecture and REST API design patterns.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 In-Memory Database

## How to Run

No database setup needed — uses H2 in-memory database, data resets on restart.

1. Clone the repo
2. Run `mvn spring-boot:run`
3. API available at `http://localhost:8080/books`
4. H2 console (optional) at `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:bookdb`
   - No password needed

## Endpoints

| Method | Endpoint | Description | Success Response |
|--------|----------|-------------|-----------------|
| POST | /books | Add a new book | 201 Created |
| GET | /books | Get all books | 200 OK |
| GET | /books?author=X | Filter by author (case-insensitive) | 200 OK |
| GET | /books?genre=X | Filter by genre (case-insensitive) | 200 OK |
| GET | /books?author=X&genre=Y | Filter by both | 200 OK |
| GET | /books/{id} | Get book by ID | 200 OK |
| PUT | /books/{id} | Full update — all fields replaced | 200 OK |
| PATCH | /books/{id} | Partial update — only provided fields updated | 200 OK |
| DELETE | /books/{id} | Delete a book | 204 No Content |

## Request Body

```json
{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "genre": "Fiction",
  "pages": 180,
  "publishedYear": 1925
}
```

All fields are optional for PATCH requests — only provided fields will be updated.

## Key Technical Decisions

**DTO pattern (Java record)** — `BookDTO` is a Java record that separates the API contract from the JPA entity. The controller receives a DTO, the service maps it to an entity, and the entity is persisted. This prevents accidentally exposing internal fields or allowing clients to set the ID directly.

**Constructor injection** — dependencies are injected via constructor (`private final BookService service`) rather than `@Autowired` field injection. This makes dependencies explicit and the class easier to test.

**Optional handling** — the service layer returns `Optional<Book>` so the controller can cleanly return 404 when a book is not found, without throwing exceptions.

**Case-insensitive filtering** — `findByAuthorIgnoreCase` and `findByGenreIgnoreCase` are Spring Data JPA derived query methods. No custom SQL needed — JPA generates the query from the method name.

**PUT vs PATCH** — PUT replaces all fields (full update), PATCH only updates fields that are provided and non-null (partial update). Both are implemented and properly differentiated.

## Project Structure

```
src/main/java/com/example/book/API/
├── controller/
│   └── BookController.java     — REST endpoints, HTTP status codes
├── service/
│   └── BookService.java        — business logic, Optional handling
├── repository/
│   └── BookRepo.java           — JPA repository, custom query methods
├── entity/
│   └── Book.java               — JPA entity mapped to H2 table
└── dto/
    └── BookDTO.java            — Java record, API request contract
```
