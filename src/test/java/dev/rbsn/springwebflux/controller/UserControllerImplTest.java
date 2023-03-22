package dev.rbsn.springwebflux.controller;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.mapper.UserMapperImpl;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.PrepareTestInstance;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mongodb.reactivestreams.client.MongoClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

   @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService service;

    @Test
    void should_be_able_to_create_a_new_user_when_all_parameters_are_valid() {
        final var request = new UserRequest("validName", "valid@email.com", "validPass");

        when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated();

        verify(service, times(1)).save(any(UserRequest.class));
    }
    @Test
    void should_not_be_able_to_create_a_new_user_when_name_is_invalid() {
     final var request = new UserRequest("invalidName ", "valid@email.com", "validPass");

     webTestClient.post().uri("/users")
             .contentType(MediaType.APPLICATION_JSON)
             .body(BodyInserters.fromValue(request))
             .exchange()
             .expectStatus()
             .isBadRequest()
             .expectBody()
             .jsonPath("$.path").isEqualTo("/users")
             .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
             .jsonPath("$.error").isEqualTo("Validation error")
             .jsonPath("$.message").isEqualTo("Error on validation attributes")
             .jsonPath("$.errors[0].fildName").isEqualTo("name")
             .jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at the end");
    }

    @Test
    void should_not_be_able_to_create_a_new_user_when_email_is_invalid() {
     final var request = new UserRequest("validName", "invalidEmail.com", "validPass");

     webTestClient.post().uri("/users")
             .contentType(MediaType.APPLICATION_JSON)
             .body(BodyInserters.fromValue(request))
             .exchange()
             .expectStatus()
             .isBadRequest()
             .expectBody()
             .jsonPath("$.path").isEqualTo("/users")
             .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
             .jsonPath("$.error").isEqualTo("Validation error")
             .jsonPath("$.message").isEqualTo("Error on validation attributes")
             .jsonPath("$.errors[0].fildName").isEqualTo("email")
             .jsonPath("$.errors[0].message").isEqualTo("invalid e-mail");
    }

    @Test
    void should_not_be_able_to_create_a_new_user_when_password_is_invalid() {
        final var request = new UserRequest("validName", "valid@email.com", "invalidPassword ");

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fildName").isEqualTo("password")
                .jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at the end")
                .jsonPath("$.errors[1].fildName").isEqualTo("password")
                .jsonPath("$.errors[1].message").isEqualTo("must be between 6 and 10 characteres");
    }
}