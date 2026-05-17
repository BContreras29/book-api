package com.example.book.API.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.book.API.dto.BookDTO;
import com.example.book.API.entity.Book;
import com.example.book.API.repository.BookRepo;

@Service
public class BookService {

    private final BookRepo repo;

    public BookService(BookRepo repo) {
        this.repo = repo;
    }

    public Optional<Book> postBook(BookDTO dto) {
        Book newBook = new Book(
            dto.title(),
            dto.author(),
            dto.genre(),
            dto.pages(),
            dto.publishedYear()
        );
        
        repo.save(newBook);
        return Optional.ofNullable(newBook);
    }

    public Optional<Book> getByID(Long id) {
        return repo.findById(id);
    }

    public List<Book> getBooks(String author, String genre) {
        if(author != null && genre != null) {
            return repo.findByAuthorIgnoreCaseAndGenreIgnoreCase(author, genre);
        }
        if(author != null) {
            return repo.findByAuthorIgnoreCase(author);
        }
        if(genre != null) {
            return repo.findByGenreIgnoreCase(genre);
        }

        return repo.findAll();
    }

    public Optional<Book> updateBook(Long id, BookDTO dto) {
        Optional<Book> bookToUpdate = repo.findById(id);
        if(bookToUpdate.isEmpty()) {
            return Optional.empty();
        }

        Book book = bookToUpdate.get();
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setGenre(dto.genre());
        book.setPages(dto.pages());
        book.setPublishedYear(dto.publishedYear());

        repo.save(book);
        return Optional.ofNullable(book);
    }

    public Optional<Book> patchBook(Long id, BookDTO dto) {
        Optional<Book> bookToUpdate = repo.findById(id);
        if(bookToUpdate.isEmpty()) {
            return Optional.empty();
        }   

        Book book = bookToUpdate.get();

        if(dto.title() != null) {
            book.setTitle(dto.title());
        }
        if(dto.author() != null) {
            book.setAuthor(dto.author());
        }
        if(dto.genre() != null) {
            book.setGenre(dto.genre());
        }
        if(dto.pages() != null) {
            book.setPages(dto.pages());
        }
        if(dto.publishedYear() != null) {
            book.setPublishedYear(dto.publishedYear());
        }

        repo.save(book);
        return Optional.ofNullable(book);
    }

    public boolean deleteBook(Long id) {
        Optional<Book> bookToDelete = repo.findById(id);
        if(bookToDelete.isEmpty()) {
            return false;
        }

        repo.delete(bookToDelete.get());
        return true;
    }
}
