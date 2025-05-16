package at.questionbank.qustion_bank;

import at.questionbank.qustion_bank.communication.dto.JoinRequest;
import at.questionbank.qustion_bank.logic.GameSessionManager;
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
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"server.ssl.enabled=false"}
)
public class PlayerSocketEndpointTest {

    @MockBean
    private GameSessionManager gameSessionManager;

    @MockBean
    private PlayerRepository playerRepository;

    @Value("${local.server.port}")
    private int port;

    private WebSocketStompClient stompClient;
    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private final String WS_URL_TEMPLATE = "ws://localhost:%d/wss";

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        Player testPlayer = new Player();
        testPlayer.setName("TestPlayer");
        testPlayer.setGameSessionId("ABC123");

        Mockito.doReturn(Optional.of(testPlayer))
                .when(playerRepository)
                .findByGameSessionIdAndName("ABC123", "TestPlayer");
    }

    @Test
    public void shouldBroadcastPlayerJoinMessageViaWebSocket() throws Exception {
        String gameCode = "ABC123";
        String topicDestination = "/topic/players/" + gameCode;
        CompletableFuture<JoinRequest> future = new CompletableFuture<>();

        StompSession session = stompClient
                .connectAsync(String.format(WS_URL_TEMPLATE, port), headers, new StompSessionHandlerAdapter() {})
                .join(); // blocks until connected

        session.subscribe(topicDestination, new StompFrameHandler() {
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
        request.setGameCode(gameCode);
        request.setPlayerName("TestPlayer");
        request.setLanguage("en");

        session.send("/app/join", request);

        // Wait manually up to 3 seconds
        JoinRequest received = null;
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < 3000 && !future.isDone()) {
            Thread.sleep(100);
        }

        if (future.isDone()) {
            received = future.getNow(null);
        }

        assertThat(received).isNotNull();
        assertThat(received.getPlayerName()).isEqualTo("TestPlayer");
        assertThat(received.getGameCode()).isEqualTo(gameCode);
    }
}
