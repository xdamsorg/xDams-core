package org.xdams.security.load;

import org.xdams.security.AuthenticationType;
import org.xdams.security.UserDetails;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class LoadUserManager {
	public static UserBean executeLoad(UserDetails userDetails, AuthenticationType authenticationType) throws Exception {
		if (authenticationType.getLoadUserType() == null || authenticationType.getLoadUserType().equals("xDams-basic")) {
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
		} else if ((authenticationType.getLoadUserType().equals("xDams-xway"))) {
			XWConnection xwconn = null;
			ConnectionManager connectionManager = new ConnectionManager();
			try {
				xwconn = connectionManager.getConnection(authenticationType.getArchiveXWAY());
				String queryUser = "([XML,/user/@id]=\"" + userDetails.getId() + "\") AND ([XML,/user/@account]=\"" + userDetails.getAccount() + "\") OR ([XML,/account/@id]=\"" + userDetails.getAccount() + "\")";
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
//				String xmlrole = ConfManager.getConfString(userDetails.getAccount() + "-security/role.xml");
				return LoadUserSpeedUp.loadUserByString(xmlUsers, xmlArchives, "", userDetails.getId(), userDetails.getAccount());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
 			} finally {
				connectionManager.closeConnection(xwconn);
			}
		} else {
			return null;
		}
	}
}
