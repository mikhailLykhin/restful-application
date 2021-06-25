package com.restful.app.rest.controllers;

import com.restful.app.api.dto.RequestDto;
import com.restful.app.api.services.IRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRequestController {

    private final IRequestService requestService;

    public UserRequestController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public List<RequestDto> getRequests(Principal principal) {
        return this.requestService.getAllCreatedRequestsFromUserByEmail(principal.getName());
    }

    @DeleteMapping("/requests/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRequest(@PathVariable("id") long id) {
     this.requestService.deleteRequest(id);
     return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/requests/confirm")
    public ResponseEntity<HttpStatus> confirmRequests(Principal principal) {
        this.requestService.confirmRequests(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/requests/confirmed")
    public List<RequestDto> getConfirmdedRequests(Principal principal) {
        return this.requestService.getAllConfirmedRequestsFromUserByEmail(principal.getName());
    }
}
