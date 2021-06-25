package com.restful.app.services;

import com.restful.app.api.dao.IUserDao;
import com.restful.app.api.dto.UserDto;
import com.restful.app.api.dto.UserDtoMyAccount;
import com.restful.app.api.dto.UserDtoPasswordChange;
import com.restful.app.api.enums.RoleName;
import com.restful.app.api.enums.UserStatus;
import com.restful.app.api.exceptions.IncorrectDataException;
import com.restful.app.api.mappers.UserDetailMapper;
import com.restful.app.api.mappers.UserMapper;
import com.restful.app.api.services.IUserService;

import com.restful.app.api.utils.IEmailSender;
import com.restful.app.entity.Role;
import com.restful.app.entity.User;
import com.restful.app.entity.UserDetail;

import com.restful.app.utils.LogoFileUploader;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {

    private final IUserDao userDao;
    private final UserMapper userMapper;
    private final UserDetailMapper userDetailMapper;
    private final IEmailSender emailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(IUserDao userDao, UserMapper userMapper, UserDetailMapper userDetailMapper, IEmailSender emailSender, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.userDetailMapper = userDetailMapper;
        this.emailSender = emailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public UserDto getUserById(long id) {
        User user = this.userDao.get(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public boolean isUserExist(String email) {
        return this.userDao.isUserExist(email);
    }

    @Override
    @Transactional
    public boolean isUserExist(int id) {
        return this.userDao.isUserExist(id);
    }

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = this.userDao.findUserByEmail(email);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public List<UserDto> getUsersBySearchRequest(String search) {
        return userMapper.mapListDto(this.userDao.findUsersBySearchRequest(search));
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User entity = this.userDao.get(id);
        this.userDao.delete(entity);
        LogoFileUploader.deleteLogo(entity.getEmail());
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        return userMapper.mapListDto(this.userDao.getAll());

    }

    @Override
    @Transactional
    public void createUser(UserDto user) {
        User entity = userMapper.toEntity(user);
        UserDetail userDetail = userDetailMapper.toEntity(user.getUserDetails());
        entity.setDateOfCreation(LocalDateTime.now());
        entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entity.setUserDetails(userDetail);
        roleAddOrChange(entity, user.getRoleName());
        startStatusChange(entity, user.getRoleName());
        userDetail.setUser(entity);
        this.userDao.create(entity);
        this.emailSender.sendMessageWithActivationInstruction(entity, "Welcome to global library!");

    }

    @Override
    @Transactional
    public void roleChangeUser(long id, UserDto user) {
        User entity = this.userDao.get(id);
        roleAddOrChange(entity, user.getRoleName());
        this.userDao.update(entity);
    }

    private void roleAddOrChange(User user, String roleName) {
        for (RoleName value : RoleName.values()) {
            if (value.name().equals(roleName)) {
                if (!user.getRoles().isEmpty()) {
                    user.getRoles().clear();
                }
                user.getRoles().add(Role.builder()
                        .id(value.getId())
                        .name(value.toString())
                        .build());
            }
        }
    }

    private void startStatusChange(User user, String roleName) {
        if (roleName.equals(RoleName.ROLE_USER.name())) {
            user.setStatus(UserStatus.Enabled.getId());
        } else {
            user.setStatus(UserStatus.Disabled.getId());
        }
    }

    @Override
    @Transactional
    public void statusChangeUser(long id, int status) {
        User entity = this.userDao.get(id);
        entity.setStatus(status);
        this.userDao.update(entity);
    }

    @Override
    public void uploadLogo(Principal principal, MultipartFile multipartFile) {
        try {
            LogoFileUploader.updateOrCreateLogo(multipartFile, principal);
        } catch (IOException e) {
            log.error("Failed to upload image. Error message: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateUser(Principal principal, UserDtoMyAccount user) {
        User entity = this.userDao.findUserByEmail(principal.getName());
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.getUserDetails().setAddress(user.getUserDetails().getAddress());
        entity.getUserDetails().setPassportNumber(user.getUserDetails().getPassportNumber());
        entity.getUserDetails().setEducationalInstitution(user.getUserDetails().getEducationalInstitution());
        entity.getUserDetails().setEducationalInstitutionAddress(user.getUserDetails().getEducationalInstitutionAddress());
        this.userDao.update(entity);
    }

    @Override
    @Transactional
    public boolean isPasswordMatches(Principal principal, UserDtoPasswordChange userDtoPasswordChange) {
        User entity = this.userDao.findUserByEmail(principal.getName());
        return bCryptPasswordEncoder.matches(userDtoPasswordChange.getPasswordConfirm(), entity.getPassword());
    }

    @Override
    @Transactional
    public void updateUserPassword(Principal principal, UserDtoPasswordChange userDtoPasswordChange) {
        User entity = this.userDao.findUserByEmail(principal.getName());
        entity.setPassword(bCryptPasswordEncoder.encode(userDtoPasswordChange.getPassword()));
        entity.setPasswordConfirm(bCryptPasswordEncoder.encode(userDtoPasswordChange.getPassword()));
    }

    @Override
    @Transactional
    public void changeForgotPassword(String email) {
        User entity = this.userDao.findUserByEmail(email);
        String generatedNewPassword = RandomStringUtils.randomAlphanumeric(10);
        entity.setPassword(bCryptPasswordEncoder.encode(generatedNewPassword));
        entity.setPasswordConfirm(bCryptPasswordEncoder.encode(generatedNewPassword));
        this.emailSender.sendMessageWithNewPassword(entity, "Your new password", generatedNewPassword);

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userDao.findUserByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoles().stream().findFirst().get().getName()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
    }

    @Override
    @Transactional
    public User authentication(String email, String password) {
        User user = this.userDao.findUserByEmail(email);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return user;
            }else {
                throw new IncorrectDataException("incorrect password");
            }
        }
        throw new UsernameNotFoundException("User with email : " + email + " not found");
    }
}
