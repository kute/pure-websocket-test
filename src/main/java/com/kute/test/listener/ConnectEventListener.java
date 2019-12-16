package com.kute.test.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * created by bailong001 on 2019/12/14 19:16
 */
@Component
@Slf4j
public class ConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Override
    public void onApplicationEvent(SessionConnectEvent sessionConnectEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
        log.info("监听会话建立连接 for {}", accessor.getUser());
    }

}
