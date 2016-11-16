package ru.jskills.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import ru.jskills.utils.HttpSessionIdHandshakeInterceptor;
import ru.jskills.utils.PresenceChannelInterceptor;

/**
 * Created by safin.v on 17.10.2016.
 */
@Configuration
@EnableWebSocketMessageBroker
public class AppWebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(presenceChannelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(8);
        registration.setInterceptors(presenceChannelInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws").withSockJS();

    }

//    @Bean
//    public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
//        return new HttpSessionIdHandshakeInterceptor();
//    }

    //

    @Bean
    public PresenceChannelInterceptor presenceChannelInterceptor() {
        return new PresenceChannelInterceptor();
    }

}