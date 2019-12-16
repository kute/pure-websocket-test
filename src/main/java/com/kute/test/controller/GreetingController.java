package com.kute.test.controller;

import com.kute.test.message.Greeting;
import com.kute.test.message.HelloMessage;
import com.kute.test.service.PureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * created by bailong001 on 2019/12/14 11:38
 * <p>
 * 1. spring webscoket能识别带”/user”的订阅路径并做出处理，如 "/user/topic/greetings"会被spring websocket利用UserDestinationMessageHandler进行转化成”/topic/greetings-usererbgz2rq”,”usererbgz2rq”中，user是关键字，erbgz2rq是sessionid，这样子就把用户和订阅路径唯一的匹配起来了
 */
@Controller
@Slf4j
public class GreetingController {

    /**
     * org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration#brokerMessagingTemplate()
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    private PureService pureService;

    /**
     * {@link SendTo} 该注解：方法的返回值会被messageconverter转化并推送到消息代理器中，由消息代理器广播到订阅路径去
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("收到消息：{}", message);
        Thread.sleep(1000);
        return new Greeting().setContent("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    /**
     * 用户聊天
     * {@link SendToUser} 会被UserDestinationMessageHandler转化成”/user/role1/topic/greetings”,role1是用户的登录帐号，这样子就把消息唯一的推送到请求者的订阅路径中去
     *
     * @param message
     * @return
     * @throws Exception
     */
    @MessageMapping("/chat")
    public void chat(Principal principal, HelloMessage message) throws Exception {
        log.info("收到聊天消息 user={}, message={}", principal, message);
        if (null != principal && null != principal.getName()) {
            String user = pureService.getBySessionId(principal.getName());
            String sessionId;
            if ("guest".equalsIgnoreCase(user)) {
                sessionId = pureService.getByUser("admin");
            } else {
                sessionId = pureService.getByUser("guest");
            }
            String content = user + " send message [" + message.getName() + "] to " + pureService.getBySessionId(sessionId);
            messagingTemplate.convertAndSendToUser(sessionId,
                    "/queue/chat", new Greeting().setContent(content));
        }
    }


}
