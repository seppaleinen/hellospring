package se.david.labs;

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
import se.david.labs.repository.entity.NewEntity;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DisplayName("Testing out WebTestClient")
class NestedIntegrationTest {
    @Autowired
    private WebTestClient webClient;

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
            webClient.post().uri("/reactive")
                    .exchange()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBodyList(NewEntity.class).hasSize(200000);
        }
        @Test
        @DisplayName("Who U love?")
        void whoYouLove() {
            webClient.get().uri("/reactive/123")
                    .exchange()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBody(NewEntity.class).isEqualTo(new NewEntity(123L));

        }
        @Test
        @DisplayName("Who yo friends love?")
        void WhoYourFriendsLove() {
            webClient.get().uri("/reactive")
                    .exchange()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .expectBodyList(NewEntity.class).hasSize(2000);
        }
        @Test
        @DisplayName("Baby U smart")
        void babyYouSmart() {}
        @Test
        @DisplayName("U Loyal")
        void youLoyal() {}
    }


}
