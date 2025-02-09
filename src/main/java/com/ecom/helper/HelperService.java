package com.ecom.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

public interface HelperService {

    void removeSessionMessage();

    public void saveFileToPath(MultipartFile file,String location) throws IOException;

    public boolean validateDiscountValue(int value);

    public default Boolean sendMail(String email, String url) throws UnsupportedEncodingException, MessagingException{
        return true;
    }

    String generateURL(HttpServletRequest request);

}
