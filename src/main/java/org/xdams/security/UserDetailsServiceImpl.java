package org.xdams.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.xdams.security.load.LoadUser;
import org.xdams.security.load.LoadUserSpeedUp;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class UserDetailsServiceImpl implements UserDetailsService {

	public static void main(String[] args) {
		UserDetailsServiceImpl detailsServiceImpl = new UserDetailsServiceImpl();
		detailsServiceImpl.loadUserByUsernameCompany("admin", "xdams.org", null);
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

	public UserDetails loadUserByUsernameCompany(String username, String account, AuthenticationType authenticationType) throws UsernameNotFoundException, DataAccessException {
		try {
			/*
			 * Users users = (Users)service.getListFromSQL(Users.class, "SELECT * FROM users where username='"+username+"' and ref_id_company="+company+";").get(0); if (users == null) throw new UsernameNotFoundException("user not found");
			 */
			UserBean userBean = null;
			try {
//				System.out.println("UserDetailsServiceImpl.loadUserByUsernameCompany() username:"+username);
//				System.out.println("UserDetailsServiceImpl.loadUserByUsernameCompany() account:"+account);
				if (authenticationType.getLoadUserType() == null || authenticationType.getLoadUserType().equals("xDams-basic")) {
					if (authenticationType.isLoadUserSpeedUp()) {
						String xmlUsers = ConfManager.getConfString(account + "-security/users.xml");
						String xmlArchives = ConfManager.getConfString(account + "-security/accounts.xml");
						String xmlrole = ConfManager.getConfString(account + "-security/role.xml");
						userBean = LoadUserSpeedUp.loadUserByString(xmlUsers, xmlArchives, xmlrole, username, account);
					} else {
						XMLBuilder xmlUsers = ConfManager.getConfXML(account + "-security/users.xml");
						XMLBuilder xmlArchives = ConfManager.getConfXML(account + "-security/accounts.xml");
						XMLBuilder xmlrole = ConfManager.getConfXML(account + "-security/role.xml");
						userBean = LoadUser.loadUser(xmlUsers, xmlArchives, xmlrole, username, account);
					}
				} else if ((authenticationType.getLoadUserType() != null) && (authenticationType.getLoadUserType().equals("xDams-xway"))) {
					XWConnection xwconn = null;
					ConnectionManager connectionManager = new ConnectionManager();
					try {
						xwconn = connectionManager.getConnection(authenticationType.getArchiveXWAY());
						String queryUser = "([XML,/user/@id]=\""+username+"\") AND ([XML,/user/@account]=\""+account+"\") OR ([XML,/account/@id]=\"" + account + "\")";
						String[] recordsXML = xwconn.select(queryUser);
						String xmlUsers = "";
						String xmlArchives = "";
						for (int i = 0; i < recordsXML.length; i++) {
							if (recordsXML[i].indexOf("<user") != -1) {
								xmlUsers = recordsXML[i];
							} else if (recordsXML[i].indexOf("<account") != -1) {
								xmlArchives = recordsXML[i];
							}
						}
						String xmlrole = ConfManager.getConfString(account + "-security/role.xml");
						userBean = LoadUserSpeedUp.loadUserByString(xmlUsers, xmlArchives, xmlrole, username, account);
					} catch (Exception e) {
						e.printStackTrace();
						throw new UsernameNotFoundException("user not found");
					} finally {
						connectionManager.closeConnection(xwconn);
					}
					
				} else {
					throw new UsernameNotFoundException("user not found");
				}

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