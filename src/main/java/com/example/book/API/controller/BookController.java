package com.example.book.API.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.API.dto.BookDTO;
import com.example.book.API.entity.Book;
import com.example.book.API.service.BookService;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }
    
     // POST /books: create a new book, returns 201 Created with Location header
    @PostMapping
    public ResponseEntity<Book> postBook(@RequestBody BookDTO dto) {
        Optional<Book> opBook = service.postBook(dto);

        if(opBook.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Book book = opBook.get();

        return ResponseEntity.created(URI.create("/books/" + book.getId())).body(book);
    }

    // GET /books/{id}: get single book by ID, returns 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> opBook = service.getByID(id);

        if(opBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(opBook.get());
    }

     // GET /books: get all books, optionally filter by ?author= and/or ?genre=
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String genre
    ) {
        List<Book> books = service.getBooks(author, genre);

        return ResponseEntity.ok(books);
    }

    // PUT /books/{id}: full update, all fields replaced, returns 404 if not found
    @PutMapping("/{id}")
    public ResponseEntity<Book> putBook(@PathVariable Long id, @RequestBody BookDTO dto) {
        Optional<Book> updated = service.updateBook(id, dto);
 
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
 
        return ResponseEntity.ok(updated.get());
    }
 
    // PATCH /books/{id}: partial update, only provided fields updated, returns 404 if not found
    @PatchMapping("/{id}")
    public ResponseEntity<Book> patchBook(@PathVariable Long id, @RequestBody BookDTO dto) {
        Optional<Book> updated = service.patchBook(id, dto);
 
        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
 
        return ResponseEntity.ok(updated.get());
    }
 
    // DELETE /books/{id}: delete book, returns 204 No Content, 404 if not found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = service.deleteBook(id);
 
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
 
        return ResponseEntity.noContent().build();
    }

}
