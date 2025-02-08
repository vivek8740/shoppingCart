package com.ecom.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class HelperServiceImpl implements HelperService {


    @Override
    public void removeSessionMessage() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes()))
                .getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("errorMsg");
        session.removeAttribute("successMsg");

    }

    @Override
    public void saveFileToPath(MultipartFile file, String location) throws IOException {

        File saveFile = new ClassPathResource("static/img").getFile();
        Path path = Paths.get(
                saveFile.getAbsolutePath() + File.separator + location + File.separator + file.getOriginalFilename());
        System.out.println(path);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    }

    @Override
    public boolean validateDiscountValue(int value) {
        return value >= 0 && value <= 100;
    }

}
