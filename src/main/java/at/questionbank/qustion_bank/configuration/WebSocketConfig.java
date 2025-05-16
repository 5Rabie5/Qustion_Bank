package at.questionbank.qustion_bank.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // With SockJS fallback
        registry.addEndpoint("/wss").setAllowedOriginPatterns("*").withSockJS();

        // Optional native WebSocket (for clients without SockJS)
        registry.addEndpoint("/wss").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws-test").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // for broadcasting
        registry.setApplicationDestinationPrefixes("/app"); // for client send
    }
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper()); // Optional: customize if needed
        messageConverters.add(converter);
        return false;
    }

}
