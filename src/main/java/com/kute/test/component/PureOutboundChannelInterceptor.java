package com.kute.test.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * created by bailong001 on 2019/12/14 17:57
 */
@Component
@Slf4j
public class PureOutboundChannelInterceptor implements ChannelInterceptor {

    /**
     * 在消息发送到通道前调用，可修改消息，返回null则后续不会被调用
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("channel 之前调用：{}", message);
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("postSend message={}, sent={}", message, sent);
    }

    /**
     * 消息发送完成后调用，忽略任何异常
     * 该方法只有在  preSend 成功调用后才会被执行
     *
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.info("afterSendCompletion， message={}, sent={}", message, sent);
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("postReceive message={}", message);
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("afterReceiveCompletion message={}", message);
    }
}
