package com.ecom.helper;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class HelperServiceImpl implements HelperService{

    @Override
    public void removeSessionMessage() {
        HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("errorMsg");
        session.removeAttribute("successrMsg");
        
    }

}
