package com.example.tdd;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksByAuthor(String author) {
        try {
            if (author == null || author.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid author input
            }
            return bookRepository.findAllBooks().stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getBooksByAuthor: " + e.getMessage());
            return List.of(); // Return empty list in case of any error
        }
    }

    public List<Book> getBooksByTitle(String title) {
        try {
            if (title == null || title.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid title input
            }
            return bookRepository.findAllBooks().stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getBooksByTitle: " + e.getMessage());
            return List.of(); // Return empty list in case of any error
        }
    }

    public List<Book> getBooksByAuthorAndTitle(String author, String title) {
        try {
            if (author == null || author.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid author input
            }
            if (title == null || title.trim().isEmpty()) {
                return List.of(); // Return empty list for invalid title input
            }
            return bookRepository.findAllBooks().stream()
                    .filter(book -> book.getAuthor().equalsIgnoreCase(author) && book.getTitle().equalsIgnoreCase(title))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in getBooksByAuthorAndTitle: " + e.getMessage());
            return List.of(); // Return empty list in case of any error
        }
    }
}
