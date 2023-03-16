package dev.rbsn.springwebflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.mapper.UserMapper;
import dev.rbsn.springwebflux.model.request.UserRequest;
import dev.rbsn.springwebflux.repository.UserRepository;
import reactor.core.publisher.Mono;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	public Mono<User> save(final UserRequest request){
		return repository.save(mapper.toEntity(request));
	}

}
