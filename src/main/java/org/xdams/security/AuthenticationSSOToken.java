package org.xdams.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationSSOToken extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 5521301753350687127L;
	public AuthenticationSSOToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
}
