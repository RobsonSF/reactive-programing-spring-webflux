package dev.rbsn.springwebflux.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.rbsn.springwebflux.controller.UserController;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.model.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController{

	@Override
	public ResponseEntity<Mono<Void>> save(UserRequest request) {
		// TODO Auto-generated method stub
		return null;
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
