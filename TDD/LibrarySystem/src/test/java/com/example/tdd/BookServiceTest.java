package com.example.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BookServiceTest {

    private BookService bookService;
    private IBookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository = new FakeRepositoryForTest();
        bookService = new BookService(bookRepository);
    }

    // Test cases for getBooksByAuthor

    @Test
    public void testGetBooksByAuthor_Found() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck"),
                new Book("2", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByAuthor("Kent Beck");
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals("Test Driven Development", books.get(0).getTitle());
    }

    @Test
    public void testGetBooksByAuthor_NoBooksFound() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByAuthor("Unknown Author");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthor_MultipleBooks() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck"),
                new Book("2", "Extreme Programming Explained", "Kent Beck")
        );
        List<Book> books = bookService.getBooksByAuthor("Kent Beck");
        Assertions.assertEquals(2, books.size());
    }

    @Test
    public void testGetBooksByAuthor_CaseInsensitive() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck")
        );
        List<Book> books = bookService.getBooksByAuthor("kent beck");
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals("Test Driven Development", books.get(0).getTitle());
    }

    @Test
    public void testGetBooksByAuthor_NullAuthor() {
        List<Book> books = bookService.getBooksByAuthor(null);
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthor_EmptyAuthor() {
        List<Book> books = bookService.getBooksByAuthor("");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthor_BlankAuthor() {
        List<Book> books = bookService.getBooksByAuthor("   ");
        Assertions.assertEquals(0, books.size());
    }

    // Test cases for getBooksByTitle

    @Test
    public void testGetBooksByTitle_Found() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck"),
                new Book("2", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByTitle("Clean Code");
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals("Robert C. Martin", books.get(0).getAuthor());
    }

    @Test
    public void testGetBooksByTitle_NoBooksFound() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByTitle("Unknown Title");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByTitle_MultipleBooks() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Clean Code", "Robert C. Martin"),
                new Book("2", "Clean Code", "Some Other Author")
        );
        List<Book> books = bookService.getBooksByTitle("Clean Code");
        Assertions.assertEquals(2, books.size());
    }

    @Test
    public void testGetBooksByTitle_CaseInsensitive() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByTitle("clean code");
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals("Robert C. Martin", books.get(0).getAuthor());
    }

    @Test
    public void testGetBooksByTitle_NullTitle() {
        List<Book> books = bookService.getBooksByTitle(null);
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByTitle_EmptyTitle() {
        List<Book> books = bookService.getBooksByTitle("");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByTitle_BlankTitle() {
        List<Book> books = bookService.getBooksByTitle("   ");
        Assertions.assertEquals(0, books.size());
    }

    // Test cases for getBooksByAuthorAndTitle

    @Test
    public void testGetBooksByAuthorAndTitle_Found() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck"),
                new Book("2", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByAuthorAndTitle("Kent Beck", "Test Driven Development");
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals("Test Driven Development", books.get(0).getTitle());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_NoBooksFound() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Clean Code", "Robert C. Martin")
        );
        List<Book> books = bookService.getBooksByAuthorAndTitle("Unknown Author", "Unknown Title");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_MultipleBooks() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck"),
                new Book("2", "Test Driven Development", "Kent Beck")
        );
        List<Book> books = bookService.getBooksByAuthorAndTitle("Kent Beck", "Test Driven Development");
        Assertions.assertEquals(2, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_CaseInsensitive() {
        FakeRepositoryForTest.booksList = Arrays.asList(
                new Book("1", "Test Driven Development", "Kent Beck")
        );
        List<Book> books = bookService.getBooksByAuthorAndTitle("kent beck", "test driven development");
        Assertions.assertEquals(1, books.size());
        Assertions.assertEquals("Test Driven Development", books.get(0).getTitle());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_NullAuthor() {
        List<Book> books = bookService.getBooksByAuthorAndTitle(null, "Test Driven Development");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_EmptyAuthor() {
        List<Book> books = bookService.getBooksByAuthorAndTitle("", "Test Driven Development");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_BlankAuthor() {
        List<Book> books = bookService.getBooksByAuthorAndTitle("   ", "Test Driven Development");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_NullTitle() {
        List<Book> books = bookService.getBooksByAuthorAndTitle("Kent Beck", null);
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_EmptyTitle() {
        List<Book> books = bookService.getBooksByAuthorAndTitle("Kent Beck", "");
        Assertions.assertEquals(0, books.size());
    }

    @Test
    public void testGetBooksByAuthorAndTitle_BlankTitle() {
        List<Book> books = bookService.getBooksByAuthorAndTitle("Kent Beck", "   ");
        Assertions.assertEquals(0, books.size());
    }
}
