package com.ecom.helper;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface HelperService {

    void removeSessionMessage();

    public void saveFileToPath(MultipartFile file,String location) throws IOException;

}
