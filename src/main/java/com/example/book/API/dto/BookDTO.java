package com.example.book.API.dto;

public record BookDTO(
    String title,
    String author,
    String genre,
    Integer pages,
    Integer publishedYear
) {}
