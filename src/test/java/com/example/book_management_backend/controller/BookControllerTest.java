package com.example.book_management_backend.controller;

import com.example.book_management_backend.exception.BookNotFoundException;
import com.example.book_management_backend.model.Book;
import com.example.book_management_backend.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    public void createBookTest() throws Exception {
        mockMvc.perform(post("/book/create").contentType(APPLICATION_JSON).content("{\"book\":\"book name\",\"category\":\"book category\"}"))
            .andExpect(status().isOk());
    }

    @Test
    public void getBookById() throws Exception {
        Book book = new Book();
        book.setName("name");
        book.setCategory("category");

        when(bookService.getBookById(anyLong())).thenReturn(book);

        mockMvc.perform(get("/book/27"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"id\":0,\"name\":\"name\",\"category\":\"category\"}"));
    }

    @Test
    public void getBookByIdThrowException() throws Exception {
        when(bookService.getBookById(anyLong())).thenThrow(new BookNotFoundException("Book with id 27 is not found"));

        mockMvc.perform(get("/book/27"))
                .andExpect(status().isNotFound());
    }
}

