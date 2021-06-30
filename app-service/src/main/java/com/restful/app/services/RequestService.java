package com.restful.app.services;

import com.restful.app.api.dao.IBookDao;
import com.restful.app.api.dao.IRequestDao;
import com.restful.app.api.dao.IUserDao;
import com.restful.app.api.dto.RequestDto;
import com.restful.app.api.enums.RequestStatusName;
import com.restful.app.api.services.CommonMapper;
import com.restful.app.api.services.IRequestService;

import com.restful.app.api.utils.IEmailSender;
import com.restful.app.entity.Book;
import com.restful.app.entity.Request;
import com.restful.app.entity.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService implements IRequestService {

    private final IRequestDao requestDao;
    private final IBookDao bookDao;
    private final IUserDao userDao;
    private final IEmailSender emailSender;
    private final CommonMapper commonMapper;

    public RequestService(IRequestDao requestDao, IBookDao bookDao, IUserDao userDao, IEmailSender emailSender, CommonMapper commonMapper) {
        this.requestDao = requestDao;
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.emailSender = emailSender;
        this.commonMapper = commonMapper;
    }

    @Override
    @Transactional("transactionManager")
    public void createRequest(String isbn, String email) {
        Request request = new Request();
        Book book = this.bookDao.findBookByIsbn(isbn);
        User user = this.userDao.findUserByEmail(email);
        request.setBook(book);
        request.setUser(user);
        request.setDateOfCreation(LocalDateTime.now());
        request.setStatus(RequestStatusName.CREATED.getNameDB());
        book.getRequests().add(request);
        user.getRequests().add(request);
        this.requestDao.create(request);
    }

    @Override
    @Transactional("transactionManager")
    public void deleteRequest(long id) {
        this.requestDao.delete(this.requestDao.get(id));
    }

    @Override
    @Transactional("transactionManager")
    public List<RequestDto> getAllCreatedRequestsFromUserByEmail(String email) {
        return commonMapper.mapAll(this.requestDao.findAllCreatedRequestsFromUserByEmail(email), RequestDto.class);
    }

    @Override
    @Transactional("transactionManager")
    public List<RequestDto> getAllConfirmedRequestsFromUserByEmail(String email) {
        return commonMapper.mapAll(this.requestDao.findAllConfirmedRequestsFromUserByEmail(email), RequestDto.class);
    }

    @Override
    @Transactional("transactionManager")
    public List<RequestDto> getAllRequests(String status) {
        return commonMapper.mapAll(this.requestDao.findAllRequests(status), RequestDto.class);
    }

    @Override
    @Transactional("transactionManager")
    public List<RequestDto> getAllRequestsBySearch(String status, String search) {
        return commonMapper.mapAll(this.requestDao.findAllRequestsBySearch(status, search), RequestDto.class);
    }

    @Override
    @Transactional("transactionManager")
    public boolean isRequestExistForCurrentBookFromUser(String isbn, String email) {
        return this.requestDao.isRequestExistForCurrentBookFromUser(isbn, email);
    }

    @Override
    @Transactional("transactionManager")
    public void confirmRequests(String email) {
        for (Request request : this.requestDao.findAllCreatedRequestsFromUserByEmail(email)) {
            request.setStatus(RequestStatusName.CONFIRMED.getNameDB());
            this.requestDao.update(request);
        }
    }

    @Override
    @Transactional("transactionManager")
    public void processRequest(long id) {
        Request request = this.requestDao.get(id);
        request.setStatus(RequestStatusName.PROCESSED.getNameDB());
        request.setDateOfExtradition(LocalDateTime.now());
        this.requestDao.update(request);
    }

    @Override
    @Transactional("transactionManager")
    public void returnRequest(long id) {
        Request request = this.requestDao.get(id);
        request.setStatus(RequestStatusName.RETURNED.getNameDB());
        request.setDateOfReturn(LocalDateTime.now());
        this.requestDao.update(request);
    }

    @Scheduled(cron = "0 13 * * * 1-5")
    public void sendMessageToBookBack() {
        String status = RequestStatusName.PROCESSED.getNameDB();
        List<Request> requestList = this.requestDao.findAllRequests(status);
        for (Request request : requestList) {
            if(request.getDateOfExtradition().plusMinutes(1).isBefore(LocalDateTime.now())){
                emailSender.sendMessageToBookBack(request.getUser(),"GET BOOK BACK!");
            }
        }
    }
}
