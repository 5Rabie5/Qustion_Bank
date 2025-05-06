package at.questionbank.qustion_bank.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Set allowed origins to restrict to specific domain in production
        registry.addEndpoint("/ws")
                .setAllowedOrigins("https://quiz.antiochorthodox.at")  // Restrict origins for production (or use "*" for testing)
                .withSockJS();  // Enables XHR fallbacks for browsers that don't support WebSockets
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enables a simple message broker for topics and queues
        registry.enableSimpleBroker("/topic", "/queue");  // Allows clients to subscribe to /topic and /queue destinations

        // Defines the application destination prefix for routing messages
        registry.setApplicationDestinationPrefixes("/app");  // Messages sent to /app will be routed to controller methods
    }

    // Additional WebSocket transport configuration (optional)
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        // You can configure WebSocket transport settings here if needed
        // For example, you can add a custom WebSocket handler decorator
        // registry.addDecoratorFactory(new CustomWebSocketHandlerDecoratorFactory());
    }
}
