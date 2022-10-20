package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InfoService {

    Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private Integer serverPort;

    public ResponseEntity<Integer> getPort() {
        logger.info("Was invoked method for edit faculty");
        return ResponseEntity.ok(serverPort);
    }
}
