package dev.rbsn.springwebflux.mapper;

import dev.rbsn.springwebflux.model.response.UserResponse;
import org.mapstruct.*;

import dev.rbsn.springwebflux.entity.User;
import dev.rbsn.springwebflux.model.request.UserRequest;

@Mapper(
		componentModel = "spring",
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS		
		)
public interface UserMapper {
	
	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request);

	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request,@MappingTarget User entity);

	UserResponse toResponse(final User entity);
}
