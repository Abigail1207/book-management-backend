package com.example.book_management_backend.service;

import com.example.book_management_backend.dto.BookDTO;
import com.example.book_management_backend.exception.BookNotFoundException;
import com.example.book_management_backend.model.Book;
import com.example.book_management_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void buildAndSave(BookDTO bookDTO) {

        Book newBook = new Book();
        newBook.setName(bookDTO.getName());
        newBook.setCategory(bookDTO.getCategory());

        this.bookRepository.save(newBook);
    }

    public Book getBookById(Long bookId) throws BookNotFoundException {
        Optional<Book> book = this.bookRepository.findById(bookId);

        if(book.isPresent()) {
            return book.get();
        }

        throw new BookNotFoundException("Book with id " + bookId + " is not found");
    }
}