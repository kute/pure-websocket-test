package com.kute.test.component;

import com.kute.test.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * created by bailong001 on 2019/12/14 10:58
 */
@Slf4j
@Component
public class PureHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    public void start() {
        super.start();
    }

    @Override
    protected void doStart() {
        super.doStart();
        log.debug("开始握手");
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void doStop() {
        super.doStop();
        log.debug("终止握手");
    }

    @Override
    protected void handleInvalidUpgradeHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        super.handleInvalidUpgradeHeader(request, response);
        log.info("处理不合法的升级websocket 的header");
    }

    @Override
    protected void handleInvalidConnectHeader(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        super.handleInvalidConnectHeader(request, response);
        log.info("处理不合法的连接header");
    }

    @Override
    protected void handleWebSocketVersionNotSupported(ServerHttpRequest request, ServerHttpResponse response) {
        super.handleWebSocketVersionNotSupported(request, response);
        log.info("处理不合法的websocket版本");
    }

    @Override
    protected String selectProtocol(List<String> requestedProtocols, WebSocketHandler webSocketHandler) {
        return super.selectProtocol(requestedProtocols, webSocketHandler);
    }

    @Override
    protected List<WebSocketExtension> filterRequestedExtensions(ServerHttpRequest request, List<WebSocketExtension> requestedExtensions, List<WebSocketExtension> supportedExtensions) {
        return super.filterRequestedExtensions(request, requestedExtensions, supportedExtensions);
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return new User().setName(request.getURI().getPath().split("/")[3]);
    }
}
