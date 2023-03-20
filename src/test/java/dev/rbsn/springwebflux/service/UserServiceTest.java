package dev.rbsn.springwebflux.service;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.mapper.UserMapperImpl;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapperImpl mapper;

    @InjectMocks
    private UserService service;

    @Test
    void testSave() {
        UserRequest request = new UserRequest(
                "valid name", "valid@email.mail", "validPassword");
        User entity = User.builder().build();

        when(mapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(repository.save(any(User.class))).thenReturn(Mono.just(entity));

        Mono<User> result = service.save(request);
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getClass()  == User.class)
                .expectComplete()
                .verify();

        verify(repository, times(1)).save(any(User.class));
    }
}