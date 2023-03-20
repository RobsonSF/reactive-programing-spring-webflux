package dev.rbsn.springwebflux.mapper;

import dev.rbsn.springwebflux.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

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

	UserResponse toResponse(final User entity);
}
