package dev.rbsn.springwebflux.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.rbsn.springwebflux.controller.UserController;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.model.response.UserResponse;
import dev.rbsn.springwebflux.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController{
	
	@Autowired
	private UserService service;

	@Override
	public ResponseEntity<Mono<Void>> save(final UserRequest request) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(service.save(request).then());
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Flux<UserResponse>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Mono<Void>> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
