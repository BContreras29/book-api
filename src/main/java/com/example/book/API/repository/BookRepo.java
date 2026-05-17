package com.example.book.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book.API.entity.Book;
import java.util.List;


@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    public List<Book> findByAuthorIgnoreCase(String author);
    public List<Book> findByGenreIgnoreCase(String genre);
    public List<Book> findByAuthorIgnoreCaseAndGenreIgnoreCase(String author, String genre);
}
