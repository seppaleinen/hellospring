package se.david.labs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.david.labs.dto.SuperDto;
import se.david.labs.repository.NewEntityRepository;
import se.david.labs.repository.entity.NewEntity;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DisplayName("Testing out WebTestClient")
public class NestedIntegrationTest {
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private NewEntityRepository repository;

    @BeforeEach
    public  void beforeEach() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("Trying out testfactory")
    class Tests {
        @TestFactory
        Stream<DynamicTest> generateMultipleTests() {
            return IntStream.range(0, 10).boxed().map(i ->
                    DynamicTest.dynamicTest("Generated test #" + i, () ->
                            webClient.get().uri("/")
                                .exchange()
                                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                                .expectBody(SuperDto.class).isEqualTo(new SuperDto("Jag älskar Kaisa!"))
                    )
            );
        }

        @TestFactory
        Stream<DynamicTest> unallowedMethods() {
            return Stream.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE).map(method ->
                    DynamicTest.dynamicTest("Test unallowed method " + method, () ->
                            webClient.method(method).uri("/")
                                    .exchange()
                                    .expectStatus()
                                    .is4xxClientError()
                    )
            );
        }
    }

    @Nested
    @DisplayName("DJ Khaled")
    class DjKhaled {
        @Test
        @DisplayName("Don't ever play yoself")
        void dontEverPlayYourself() {
            webClient.post().uri("/reactive/20")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBodyList(NewEntity.class)
                        .hasSize(20)
                        .contains(new NewEntity(0L), new NewEntity(19L))
                        .doesNotContain(new NewEntity(20L));
        }
        @Test
        @DisplayName("Who U love?")
        void whoYouLove() {
            webClient.post().uri("/reactive/2")
                    .exchange()
                    .expectStatus().isOk();

            webClient.get().uri("/reactive/1")
                    .exchange()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody(NewEntity.class).isEqualTo(new NewEntity(1L));

        }
        @Test
        @DisplayName("Who yo friends love?")
        void WhoYourFriendsLove() {
            webClient.post().uri("/reactive/20")
                    .exchange()
                    .expectStatus().isOk();

            webClient.get().uri("/reactive")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBodyList(NewEntity.class)
                        .hasSize(20)
                        .contains(new NewEntity(0L), new NewEntity(19L))
                        .doesNotContain(new NewEntity(20L));
        }
        @Test
        @DisplayName("Baby U smart")
        void babyYouSmart() {
            webClient.get().uri("/health")
                    .exchange()
                    .expectStatus().isOk();
        }
        @Test
        @DisplayName("U Loyal")
        void youLoyal() {
            webClient.get().uri("/health")
                    .exchange()
                    .expectStatus().isOk();
        }
    }


}
