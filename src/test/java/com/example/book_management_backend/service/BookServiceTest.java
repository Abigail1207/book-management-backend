package com.example.book_management_backend.service;


import com.example.book_management_backend.dto.BookDTO;
import com.example.book_management_backend.exception.BookNotFoundException;
import com.example.book_management_backend.model.Book;
import com.example.book_management_backend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testBuildAndSave () {

        BookDTO bookDTO = new BookDTO();

        bookService.buildAndSave(bookDTO);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGetBookById() throws BookNotFoundException, BookNotFoundException {
        Book book = new Book(27L, "name", "category");
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(27L)).thenReturn(optionalBook);

        Book bookResult = bookService.getBookById(27L);
        assertThat(bookResult).isNotNull();
        assertThat(bookResult.getName()).isEqualTo("name");
        assertThat(bookResult.getCategory()).isEqualTo("category");
    }

    @Test
    public void testGetBookByIdThrowBookNotFoundException() throws BookNotFoundException, BookNotFoundException {
        Optional<Book> book = Optional.empty();
        when(bookRepository.findById(27L)).thenReturn(book);

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(27L));
    }
}