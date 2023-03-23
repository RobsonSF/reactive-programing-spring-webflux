package dev.rbsn.springwebflux.controller;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.mapper.UserMapper;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.model.response.UserResponse;
import dev.rbsn.springwebflux.service.UserService;
import dev.rbsn.springwebflux.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    private static final String VALID_ID = "validId";
    private static final String VALID_NAME = "validName";
    private static final String VALID_EMAIL = "valid@email.com";
    private static final String VALID_PASS = "validPass";
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @Test
    void should_be_able_to_create_a_new_user_when_all_parameters_are_valid() {
        final var request = new UserRequest(VALID_NAME, VALID_EMAIL, VALID_PASS);

        when(service.save(any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        verify(service, times(1)).save(any(UserRequest.class));
    }
    @Test
    void should_not_be_able_to_create_a_new_user_when_name_is_invalid() {
        final var invalidName = "invalidName   ";
        final var request = new UserRequest(invalidName, VALID_EMAIL, VALID_PASS);

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at the end");
    }

    @Test
    void should_not_be_able_to_create_a_new_user_when_email_is_invalid() {
        final var invalidEmail = "invalidEmail";
        final var request = new UserRequest(VALID_NAME, invalidEmail, VALID_PASS);

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("email")
                .jsonPath("$.errors[0].message").isEqualTo("invalid e-mail");
    }

    @Test
    void should_not_be_able_to_create_a_new_user_when_password_with_blank_spaces() {
        final var invalidPassword = " password ";
        final var request = new UserRequest(VALID_NAME, VALID_EMAIL, invalidPassword);

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("password")
                .jsonPath("$.errors[0].message").isEqualTo("field cannot have blank spaces at the beginning or at the end");

    }

    @Test
    void should_not_be_able_to_create_a_new_user_when_password_with_wrong_parameters() {
        final var invalidPassword = "invalidPassword";
        final var request = new UserRequest(VALID_NAME, VALID_EMAIL, invalidPassword);

        webTestClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation error")
                .jsonPath("$.message").isEqualTo("Error on validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("password")
                .jsonPath("$.errors[0].message").isEqualTo("must be between 6 and 10 characters");

    }

    @Test
    void should_be_able_to_find_a_user_when_id_is_valid() {
        final var UserResponse = new UserResponse(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_PASS);

        when(service.findById(anyString())).thenReturn(Mono.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(UserResponse);

        webTestClient.get().uri("/users/"+VALID_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(VALID_ID)
                .jsonPath("$.name").isEqualTo(VALID_NAME)
                .jsonPath("$.email").isEqualTo(VALID_EMAIL)
                .jsonPath("$.password").isEqualTo(VALID_PASS);

        verify(service, times(1)).findById(anyString());
        verify(mapper, times(1)).toResponse(any(User.class));
    }

    @Test
    void should_not_be_able_to_find_a_user_when_id_is_invalid() {
        final var invalidId = "invalidId";

        when(service.findById(anyString())).thenThrow(new ObjectNotFoundException(
                String.format("Object not found id: %s Type: %s", invalidId, User.class.getSimpleName()))
        );

        webTestClient.get().uri("/users/"+invalidId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users/"+invalidId)
                .jsonPath("$.status").isEqualTo(NOT_FOUND.value())
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.message").isEqualTo(
                        String.format("Object not found id: %s Type: %s", invalidId, User.class.getSimpleName()));

        verify(service, times(1)).findById(anyString());

    }

    @Test
    void should_be_able_to_find_all_user() {
        final var UserResponse = new UserResponse(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_PASS);

        when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(UserResponse);

        webTestClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(VALID_ID)
                .jsonPath("$.[0].name").isEqualTo(VALID_NAME)
                .jsonPath("$.[0].email").isEqualTo(VALID_EMAIL)
                .jsonPath("$.[0].password").isEqualTo(VALID_PASS);

        verify(service, times(1)).findAll();
        verify(mapper, times(1)).toResponse(any(User.class));
    }

    @Test
    void should_be_able_to_update_a_user_when_id_is_valid() {
        final var request = new UserRequest(VALID_NAME, VALID_EMAIL, VALID_PASS);
        final var UserResponse = new UserResponse(VALID_ID, VALID_NAME, VALID_EMAIL, VALID_PASS);

        when(service.update(anyString(), any(UserRequest.class))).thenReturn(Mono.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(UserResponse);

        webTestClient.patch().uri("/users/"+VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(VALID_ID)
                .jsonPath("$.name").isEqualTo(VALID_NAME)
                .jsonPath("$.email").isEqualTo(VALID_EMAIL)
                .jsonPath("$.password").isEqualTo(VALID_PASS);

        verify(service, times(1)).update(anyString(), any(UserRequest.class));
        verify(mapper).toResponse(any(User.class));
    }

    @Test
    void should_not_be_able_to_update_a_user_when_id_is_invalid() {
        final var invalidId = "invalidId";
        final var request = new UserRequest(VALID_NAME, VALID_EMAIL, VALID_PASS);

        when(service.update(anyString(), any(UserRequest.class))).thenThrow(new ObjectNotFoundException(
                String.format("Object not found id: %s Type: %s", invalidId, User.class.getSimpleName()))
        );

        webTestClient.patch().uri("/users/"+invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users/"+invalidId)
                .jsonPath("$.status").isEqualTo(NOT_FOUND.value())
                .jsonPath("$.error").isEqualTo("Not Found")
                .jsonPath("$.message").isEqualTo(
                        String.format("Object not found id: %s Type: %s", invalidId, User.class.getSimpleName())
                );

        verify(service, times(1)).update(anyString(), any(UserRequest.class));
    }

    @Test
    void should_be_able_to_delete_a_user_when_id_is_valid() {
        when(service.delete(anyString())).thenReturn(Mono.empty());

        webTestClient.delete().uri("/users/"+VALID_ID)
                .exchange()
                .expectStatus().isNoContent();

        verify(service, times(1)).delete(anyString());
    }
}