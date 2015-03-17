package org.xdams.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	private SaltSource saltSource;

	private UserDetailsService userDetailsService;

	private AuthenticationType authenticationType;

	private boolean includeDetailsObject = true;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		Object salt = null;
		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}
		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
			throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);
		}
		String company = ((AuthenticationToken) authentication).getCompany().toString();
		if (company == null || company.equals(""))
			throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"), includeDetailsObject ? userDetails : null);

	}

	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
	}

	@Override
	protected UserDetails retrieveUser(String arg0, UsernamePasswordAuthenticationToken arg1) throws AuthenticationException {
		AuthenticationToken authenticationToken = (AuthenticationToken) arg1;
		return ((UserDetailsServiceImpl) userDetailsService).loadUserByUsernameCompany(arg0, authenticationToken.getCompany().toString(), authenticationType);
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public void setIncludeDetailsObject(boolean includeDetailsObject) {
		this.includeDetailsObject = includeDetailsObject;
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

}
