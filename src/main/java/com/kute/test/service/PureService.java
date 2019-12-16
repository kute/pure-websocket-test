package com.kute.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * created by bailong001 on 2019/12/15 17:05
 */
@Service
@Slf4j
public class PureService {

    private Map<String, String> userSessionMap = new HashMap<>();
    private Map<String, String> sessionUserMap = new HashMap<>();

    public void mappingUser(String simpSessionId, String user) {
        log.info("mappingUser sessionId={}, user={}", simpSessionId, user);
        userSessionMap.put(user, simpSessionId);
        sessionUserMap.put(simpSessionId, user);
    }

    public String getBySessionId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    public String getByUser(String admin) {
        return userSessionMap.get(admin);
    }
}
