package org.xdams.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationToken extends UsernamePasswordAuthenticationToken {
    private Object company ;
    private static final long serialVersionUID = 5521301753350687127L;
	public AuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	public AuthenticationToken(Object principal, Object credentials,Object company) {
		super(principal, credentials);
		this.company=company;
	}
	public Object getCompany() {
		return company;
	}
	public void setCompany(Object company) {
		this.company = company;
	}
}
