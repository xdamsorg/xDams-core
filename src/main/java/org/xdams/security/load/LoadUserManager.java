package org.xdams.security.load;

import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;

public class LoadUserManager {
	public static UserBean executeLoad(UserDetails userDetails, AuthenticationType authenticationType) {
		if (authenticationType.isLoadUserSpeedUp()) {
			String xmlUsers = ConfManager.getConfString(userDetails.getAccount() + "-security/users.xml");
			String xmlArchives = ConfManager.getConfString(userDetails.getAccount() + "-security/accounts.xml");
			String xmlrole = ConfManager.getConfString(userDetails.getAccount() + "-security/role.xml");
			return LoadUserSpeedUp.loadUserByString(xmlUsers, xmlArchives, xmlrole, userDetails.getId(), userDetails.getAccount());
		} else {
			XMLBuilder xmlUsers = ConfManager.getConfXML(userDetails.getAccount() + "-security/users.xml");
			XMLBuilder xmlArchives = ConfManager.getConfXML(userDetails.getAccount() + "-security/accounts.xml");
			XMLBuilder xmlrole = ConfManager.getConfXML(userDetails.getAccount() + "-security/role.xml");
			return LoadUser.loadUser(xmlUsers, xmlArchives, xmlrole, userDetails.getId(), userDetails.getAccount());
		}
	}
}
