package com.restful.app.rest.controllers;

import com.restful.app.api.dto.RequestDto;
import com.restful.app.api.services.IRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRequestController {

    private final IRequestService requestService;

    public AdminRequestController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public List<RequestDto> getAdminPage(@RequestParam(value = "status", required = false) String status) {
        return this.requestService.getAllRequests(status);
    }

    @PatchMapping("/requests/processed/{id}")
    public ResponseEntity<HttpStatus> processRequest(@PathVariable("id") long id) {
        this.requestService.processRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/requests/returned/{id}")
    public ResponseEntity<HttpStatus> returnedRequest(@PathVariable("id") long id) {
        this.requestService.returnRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/requests/s")
    public List<RequestDto> getRequestBySearch(@RequestParam(value = "status", required = false) String status,
                                            @RequestParam(value = "search", required = false) String search) {
        return this.requestService.getAllRequestsBySearch(status, search);
    }

}