package com.jmna.order_eai.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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

        ftpClient.setControlEncoding("UTF-8");
        ftpClient.connect(ftpHost, ftpPort);

        int reply = ftpClient.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            log.error("FTP 서버 연결 거부: {}", reply);
            throw new IOException("FTP 서버 연결 거부: " + reply);
        }
        log.info("FTP 연결 성공");

        if (!ftpClient.login(ftpUser, ftpPassword)) {
            log.error("FTP 로그인 실패: {}", ftpClient.getReplyString());
            throw new IOException("FTP 로그인 실패 : " + ftpClient.getReplyString());
        }
        log.info("FTP 로그인 성공");

        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        try (InputStream input = new FileInputStream(file)) {

            ftpClient.changeWorkingDirectory(ftpFilePath);
            boolean result = ftpClient.storeFile(file.getName(), input);
            if (!result) {
                log.error("FTP 파일 전송 실패 : {}", ftpClient.getReplyString());
                throw new IOException("FTP 파일 전송 실패 : " + ftpClient.getReplyString());
            }
            log.info("FTP 전송 성공");
        } catch (IOException e) {
            log.error("FTP 전송 중 I/O 오류 발생. 대상 파일: {}", file.getName(), e);
            throw e;
        }

        ftpClient.logout();
        log.info("FTP 로그아웃 성공");
        ftpClient.disconnect();
        log.info("FTP 연결 해제");
    }

}
