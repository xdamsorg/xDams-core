package org.xdams.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.xdams.security.load.LoadUser;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;


public class UserDetailsServiceImpl implements UserDetailsService {

	public static void main(String[] args) {
		UserDetailsServiceImpl detailsServiceImpl = new UserDetailsServiceImpl();
		detailsServiceImpl.loadUserByUsernameCompany("admin", "xdams.org");
	}

	private Assembler assembler;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			/*
			 * Users users = (Users)service.getListFromSQL(Users.class, "SELECT * FROM users where username='"+username+"';").get(0); if (users == null) throw new UsernameNotFoundException("user not found");
			 */
			return assembler.buildUserFromUserEntity(null);
		} catch (IndexOutOfBoundsException e) {
			throw new UsernameNotFoundException("user not found");
		}
	}

	public UserDetails loadUserByUsernameCompany(String username, String account) throws UsernameNotFoundException, DataAccessException {
		try {
			/*
			 * Users users = (Users)service.getListFromSQL(Users.class, "SELECT * FROM users where username='"+username+"' and ref_id_company="+company+";").get(0); if (users == null) throw new UsernameNotFoundException("user not found");
			 */
			try {
				XMLBuilder xmlUsers = ConfManager.getConfXML(account+"-security/users.xml");
				XMLBuilder xmlArchives = ConfManager.getConfXML(account+"-security/accounts.xml");
				XMLBuilder xmlrole = ConfManager.getConfXML(account+"-security/role.xml");
				UserBean userBean = LoadUser.loadUser(xmlUsers, xmlArchives, xmlrole, username, account);
				if (userBean == null)
					throw new UsernameNotFoundException("user not found");
				return assembler.buildUserFromUserEntity(userBean);
			} catch (Exception e) {
				throw new UsernameNotFoundException("user not found");
			}
		} catch (IndexOutOfBoundsException e) {
			throw new UsernameNotFoundException("user not found");
		}
	}

	public UserDetails loadUserFromSSO(String username) throws UsernameNotFoundException, DataAccessException {
		try {
			/*
			 * Users users = (Users)service.getListFromSQL(Users.class, "SELECT * FROM users where username='"+username+"'").get(0); if (users == null) throw new UsernameNotFoundException("user not found");
			 */
			return assembler.buildUserFromUserEntity(null);
		} catch (IndexOutOfBoundsException e) {
			throw new UsernameNotFoundException("user not found");
		}
	}

	public void setAssembler(Assembler assembler) {
		this.assembler = assembler;
	}
}