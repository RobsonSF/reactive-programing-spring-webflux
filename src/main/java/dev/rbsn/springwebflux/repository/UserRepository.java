package dev.rbsn.springwebflux.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import dev.rbsn.springwebflux.entity.User;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplete;
	
	public Mono<User> save(final User user){
		return mongoTemplete.save(user);
	}
}
