package dev.rbsn.springwebflux.service;

import dev.rbsn.springwebflux.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.mapper.UserMapperImpl;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserMapperImpl mapper;

	public Mono<User> save(final UserRequest request) {
		return repository.save(mapper.toEntity(request));
	}

	public Mono<User> findById(String id) {
		return entityNotFound(repository.findById(id), id);
	}

	public Flux<User> findAll() {
		return repository.findAll();
	}

	public Mono<User> update(String id, UserRequest request) {
		return findById(id)
				.map(entity -> mapper.toEntity(request, entity))
				.flatMap(repository::save);
	}

	public Mono<User> delete(String id) {
		return entityNotFound(repository.findAndRemove(id), id);
	}

	private <T> Mono<T> entityNotFound(Mono<T> mono, String id) {
		return mono.switchIfEmpty(Mono.error(
				new ObjectNotFoundException(
						String.format("Object not found id: %s Type: %s", id, User.class.getSimpleName())
				)
		));
	}
}
