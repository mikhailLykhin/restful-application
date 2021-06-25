package com.restful.app.api.services;

import com.restful.app.api.dto.UserDto;
import com.restful.app.api.dto.UserDtoMyAccount;
import com.restful.app.api.dto.UserDtoPasswordChange;
import com.restful.app.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
public interface IUserService {

     UserDto getUserById(long id);

     boolean isUserExist(String email);

     boolean isUserExist(int id);

     UserDto getUserByEmail(String email);

     List<UserDto> getUsersBySearchRequest(String search);

     void deleteUser(long id);

     List<UserDto> getAllUsers();

     void createUser(UserDto user);

     void roleChangeUser(long id, UserDto user);

     void statusChangeUser(long id, int status);

     void updateUser(Principal principal, UserDtoMyAccount user);

     void uploadLogo(Principal principal, MultipartFile multipartFile);

     boolean isPasswordMatches(Principal principal, UserDtoPasswordChange userDtoPasswordChange);

     void updateUserPassword(Principal principal, UserDtoPasswordChange userDtoPasswordChange);

     void changeForgotPassword (String email);

     User authentication(String email, String password);


}
