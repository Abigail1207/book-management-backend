package com.example.book_management_backend.controller;

import com.example.book_management_backend.dto.BookDTO;
import com.example.book_management_backend.exception.BookNotFoundException;
import com.example.book_management_backend.model.Book;
import com.example.book_management_backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public void createBook(@RequestBody BookDTO bookDTO) {
        this.bookService.buildAndSave(bookDTO);
    }

    @GetMapping("{bookId}")
    public Book getBook(@PathVariable Long bookId) throws BookNotFoundException {
        return this.bookService.getBookById(bookId);
    }
}
