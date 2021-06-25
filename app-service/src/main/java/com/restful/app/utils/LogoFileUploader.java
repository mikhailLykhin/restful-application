package com.restful.app.utils;

import com.restful.app.api.dto.UserDtoMyAccount;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Slf4j
@UtilityClass
public class LogoFileUploader {

    private static final String IMAGE_EXTENSION = ".jpg";
    private static final String LOGOS_FOLDER_PATH = "classpath:images/";

    public void updateOrCreateLogo(MultipartFile file, Principal principal) throws IOException {
        if (file != null && !file.isEmpty()) {
            String filePath = LOGOS_FOLDER_PATH + principal.getName() + IMAGE_EXTENSION;
            File userLogo;
            try {
                userLogo = ResourceUtils.getFile(filePath);
            } catch (FileNotFoundException e) {
                URL fileUrl = ResourceUtils.getURL(LOGOS_FOLDER_PATH);
                userLogo = new File(
                        fileUrl.getPath() + principal.getName() + IMAGE_EXTENSION);
            }
            Path path = Paths.get(userLogo.getPath());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        }
    }

    public void deleteLogo(String email) {
        try {
            URL fileUrl = ResourceUtils.getURL(LOGOS_FOLDER_PATH);
            File logo = new File(fileUrl.getPath() + email + IMAGE_EXTENSION);
            Path path = Paths.get(logo.getPath());
            Files.deleteIfExists(path);
        } catch (FileNotFoundException exception) {
            log.error("File not found: {}", exception.getMessage());
        } catch (IOException exception) {
            log.info("Input output exception: {}", exception.getMessage());
        }
    }

}
