package ru.jskills.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * Created by safin.v on 11.11.2016.
 */
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        // ignore non-STOMP messages like heartbeat messages
        if(sha.getCommand() == null) {
            return;
        }

        String sessionId = sha.getSessionId();

        switch(sha.getCommand()) {
            case CONNECT:
                log.debug("STOMP Connect [sessionId: " + sessionId + "]");
                System.out.println("STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case CONNECTED:
                log.debug("STOMP Connected [sessionId: " + sessionId + "]");
                System.out.println("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                log.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
                System.out.println("STOMP Disconnect [sessionId: " + sessionId + "]");
                break;
            default:
                break;

        }
    }
}
