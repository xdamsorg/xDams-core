package org.xdams.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

public class AuthenticationSSOProvider extends AbstractUserDetailsAuthenticationProvider {

	private UserDetailsService userDetailsService;


	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,UsernamePasswordAuthenticationToken authentication)throws AuthenticationException {
	}
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService,	"A UserDetailsService must be set");
	}
	@Override
	protected UserDetails retrieveUser(String arg0,UsernamePasswordAuthenticationToken arg1)throws AuthenticationException {
		//AuthenticationToken authenticationToken = (AuthenticationToken) arg1;
		System.out.println("AuthenticationSSOProvider.retrieveUser()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return ((UserDetailsServiceImpl) userDetailsService).loadUserFromSSO(arg0);
	}
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
