package com.commic.v1.api.admin;

import com.commic.v1.dto.requests.BookRequest;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.services.book.IBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController(value = "BookControllerAdmin")
@RequestMapping("/api/v1/admin/books")
@PreAuthorize("hasAuthority('ADMIN')")
public class BookController {
    @Autowired
    private IBookService bookService;


    @PostMapping
    public ResponseEntity<APIResponse<Void>> addBook(@RequestBody @Valid BookRequest bookRequest) {
        APIResponse<Void> response = bookService.addBook(bookRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
