package com.restful.app.api.services;

import com.restful.app.api.dto.RequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRequestService {

    void createRequest(String isbn, String email);

    boolean isRequestExistForCurrentBookFromUser(String isbn, String email);

    List<RequestDto> getAllCreatedRequestsFromUserByEmail(String email);

    List<RequestDto> getAllConfirmedRequestsFromUserByEmail(String email);

    List<RequestDto> getAllRequests(String status);

    List<RequestDto> getAllRequestsBySearch(String status, String search);

    void deleteRequest(long id);

    void confirmRequests(String email);

    void processRequest(long id);

    void returnRequest(long id);
}
