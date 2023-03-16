package dev.rbsn.springwebflux.model.request;

public record UserRequest (
		String name,
		String amail,
		String passwword
) {}
