package dev.rbsn.springwebflux.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.model.request.UserRequest;
@Component
@Mapper(
		componentModel = "spring"
)
public interface UserMapper {
	
	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request);
}
