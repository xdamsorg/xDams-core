package org.xdams.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private SaltSource saltSource;

	private UserDetailsService userDetailsService;

	private AuthenticationType authenticationType;

	private boolean includeDetailsObject = true;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		String presentedPassword = authentication.getCredentials().toString();
//		System.out.println("AuthenticationProvider.retrieveUser()1111111111111111 sha256Hex: " + DigestUtils.sha256Hex(presentedPassword));
//		System.out.println("AuthenticationProvider.retrieveUser()1111111111111111    md5Hex: " + DigestUtils.md5Hex(presentedPassword));
		;
		//		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
		//		throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Invalid Password"));
		//	}

		if (!DigestUtils.md5Hex(presentedPassword).equals(userDetails.getPassword())) {
			throw new BadCredentialsException(messages.getMessage("CustomAuthenticationProvider.badCredentials", "Bad credentials"));
		}

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
