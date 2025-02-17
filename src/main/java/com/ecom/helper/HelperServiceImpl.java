package com.ecom.helper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class HelperServiceImpl implements HelperService {

    @Autowired
    private JavaMailSender mailSender;


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

    @Override
    public String generateURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(),"");
    }

    @Override
    public Boolean sendMail(String recipientEmail, String url) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("vivek.raj7557@gmail.com", "Ecom");
        messageHelper.setTo(recipientEmail);

        String content = "<p>Hello,</p>"+"<p>You have requested to reset your password.</p>"
                                        +"<p>Click the below link to reset the password:X</p>"
                                        +"<p><a href=\"" + url +"\">Change Password</a></p>";

        messageHelper.setSubject("Reset Password");
        messageHelper.setText(content,true);
        mailSender.send(message);
        
        return true;
    }

}
