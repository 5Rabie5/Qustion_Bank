package at.questionbank.qustion_bank;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.logic.PlayerManager;
import at.questionbank.qustion_bank.persistence.domain.Player;
import at.questionbank.qustion_bank.persistence.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "server.ssl.enabled=false" }
)
public class SockJsEndpointTest {

    @Value("${local.server.port}")
    private int port;

    @MockBean
    private PlayerRepository playerRepository;

    private WebSocketStompClient stompClient;
    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

    @BeforeEach
    void setup() {
        WebSocketClient webSocketClient = new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))
        );
        stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        Player player = new Player();
        player.setId("player-id");
        player.setName("TestPlayer");
        player.setGameSessionId("ABC123");

        Mockito.when(playerRepository.findByGameSessionId("ABC123"))
                .thenReturn(List.of(player));
    }

    @Test
    void testSockJsConnectionAndJoinMessage() throws Exception {
        String url = "http://localhost:%d/ws-test".formatted(port);
        String topic = "/topic/players/ABC123";
        CompletableFuture<JoinRequest> future = new CompletableFuture<>();

        StompSession session = stompClient
                .connectAsync(url, headers, new StompSessionHandlerAdapter() {})
                .join();  // do NOT use .get with TimeUnit

        session.subscribe(topic, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return JoinRequest.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                future.complete((JoinRequest) payload);
            }
        });

        JoinRequest request = new JoinRequest();
        request.setGameCode("ABC123");
        request.setPlayerName("TestPlayer");
        request.setLanguage("en");

        session.send("/app/join", request);

        // Manual wait loop (max 3 seconds)
        JoinRequest received = null;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 3000 && !future.isDone()) {
            Thread.sleep(50);
        }
        if (future.isDone()) {
            received = future.getNow(null);
        }

        assertThat(received).isNotNull();
        assertThat(received.getPlayerName()).isEqualTo("TestPlayer");
        assertThat(received.getGameCode()).isEqualTo("ABC123");
    }
}
