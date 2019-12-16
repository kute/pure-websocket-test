package com.kute.test.config;

import com.kute.test.component.PureInboundChannelInterceptor;
import com.kute.test.component.PureHandshakeHandler;
import com.kute.test.component.PureHandshakeInterceptor;
import com.kute.test.component.PureOutboundChannelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import javax.annotation.Resource;
import java.util.List;

/**
 * created by bailong001 on 2019/12/14 10:50
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private PureHandshakeHandler pureHandshakeHandler;
    @Autowired
    private PureHandshakeInterceptor pureHandshakeInterceptor;

    @Resource
    private ThreadPoolTaskExecutor pureInboundThreadPoolTaskExecutor;
    @Resource
    private ThreadPoolTaskExecutor pureOutboundThreadPoolTaskExecutor;

    @Autowired
    private PureInboundChannelInterceptor pureInboundChannelInterceptor;
    @Autowired
    private PureOutboundChannelInterceptor pureOutboundChannelInterceptor;

    /**
     * 注册 STOMP 协议的端点URL
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                .addInterceptors(pureHandshakeInterceptor)
                .setAllowedOrigins("*")
                .setHandshakeHandler(pureHandshakeHandler)
                // 启用 socketjs client回调
                .withSockJS();
    }

    /**
     * 配置 STOM 消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // enableSimpleBroker基于内存的消息维护，配置目的通道前缀，客户端订阅topic前缀
        registry.enableSimpleBroker("/topic");

        registry.enableSimpleBroker("/queue");

        // 配置全局的@MessageMapping注解的前缀
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(100)
                // ms
                .setSendTimeLimit(30000);
    }

    /**
     * 配置用于 接收来自 websocket client的消息的 通道，默认只有一个大小的线程池
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor(pureInboundThreadPoolTaskExecutor);

        registration.interceptors(pureInboundChannelInterceptor);
    }

    /**
     * 配置用于发送消息到 websocket client的线程池
     *
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor(pureOutboundThreadPoolTaskExecutor);

        registration.interceptors(pureOutboundChannelInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 参数解析
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // 返回值解析
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }


}
