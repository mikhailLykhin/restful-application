package com.restful.app.rest.controllers;

import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.services.IRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping()
public class RequestController {

    private final IRequestService requestService;

    public RequestController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/books/request/create/{isbn}")
    public ResponseEntity<HttpStatus> createRequest(@PathVariable("isbn") String isbn, Principal principal) {
        if(this.requestService.isRequestExistForCurrentBookFromUser(isbn, principal.getName())) {
            throw new IncorrectDataException("Request already existed from current user");
        }
        this.requestService.createRequest(isbn, principal.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
