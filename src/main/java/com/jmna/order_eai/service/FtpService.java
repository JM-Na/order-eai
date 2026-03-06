package com.jmna.order_eai.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FtpService {

    @Value("${inspien.ftp.host}")
    private String ftpHost;
    @Value("${inspien.ftp.port}")
    private int ftpPort;
    @Value("${inspien.ftp.user}")
    private String ftpUser;
    @Value("${inspien.ftp.password}")
    private String ftpPassword;
    @Value("${inspien.ftp.file_path}")
    private String ftpFilePath;

    public void transferFile(File file) throws IOException {
        FTPClient ftpClient = new FTPClient();

        ftpClient.connect(ftpHost, ftpPort);
        log.info("FTP 연결 성공");
        ftpClient.login(ftpUser, ftpPassword);
        log.info("FTP 로그인 성공");
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        try (InputStream input = new FileInputStream(file)) {
            ftpClient.storeFile(file.getName(), input);
            log.info("FTP 전송 성공");
        }

        ftpClient.logout();
        log.info("FTP 로그아웃 성공");
        ftpClient.disconnect();
        log.info("FTP 연결 해제");
    }

}
