package org.xdams.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.xdams.user.bean.UserBean;

public class Assembler {

	public User buildUserFromUserEntity(UserBean user) {
		String username = user.getId();
		String password = user.getPwd();
		boolean enabled = user.isActive();
		boolean accountNonExpired = user.isActive();
		boolean credentialsNonExpired = user.isActive();
		boolean accountNonLocked = user.isActive();
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		/*
		 * for (UserRoles role : user.getUserRoleses()) { authorities.add(new GrantedAuthorityImpl(role.getRoles().getRoleName())); }
		 */
		// authorities.add(new GrantedAuthorityImpl("ROLE_GOD"));
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		UserDetails userS = new UserDetails(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		userS.setName(user.getName());
		userS.setLastname(user.getLastName());
		userS.setLanguage(user.getLanguage());
		userS.setAccount(user.getAccount().getId());
		userS.setId(username);
		userS.setRole(user.getRole());

		System.out.println("Assembler.buildUserFromUserEntity() " + userS);
		return userS;
	}
	// {
	// String username = user.getUsername();
	// String password = user.getPassword();
	// boolean enabled = user.isActive();
	// boolean accountNonExpired = user.isActive();
	// boolean credentialsNonExpired = user.isActive();
	// boolean accountNonLocked = user.isActive();
	// Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	// /*for (UserRoles role : user.getUserRoleses()) {
	// authorities.add(new GrantedAuthorityImpl(role.getRoles().getRoleName()));
	// }*/
	// authorities.add(new GrantedAuthorityImpl(user.getUserRoles().getRoles().getRoleName()));
	// UserDetails userS = new UserDetails(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	// userS.setId(user.getIdUser());
	// userS.setName(user.getUsersProfile().getName());
	// userS.setLastname(user.getUsersProfile().getLastname());
	// userS.setLanguage(user.getUsersProfile().getLanguage());
	// userS.setCompany(user.getCompanies().getCompanyName());
	// userS.setImageLogo(user.getCompanies().getImageName());
	// userS.setDepartmentAcronym((user.getDepartments() != null ? user.getDepartments().getAcronym() : ""));
	// System.out.println("Assembler.buildUserFromUserEntity() " + userS);
	// return userS;
	// }
}
