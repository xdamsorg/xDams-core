package org.xdams.security.load;

import org.xdams.user.bean.Account;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.xml.builder.XMLBuilder;

public class LoadUser {

	public static UserBean loadUser(XMLBuilder xmlUsers, XMLBuilder xmlArchives, XMLBuilder xmlrole, String username, String account) {

		UserBean userBean = new UserBean();
		try {
			userBean.setName(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@name"));
			userBean.setLastName(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@lastName"));
			userBean.setId(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@id"));
			userBean.setEmail(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@email"));
			userBean.setLanguage(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@language"));
			userBean.setAccountRef(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@account"));
			userBean.setFatherAccountRef(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@fatherAccount"));
			userBean.setPwd(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@pwd"));
			userBean.setRole(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/@role"));
			int countArchiveUser = xmlUsers.contaNodi("/root/user[@id='" + username + "' and @account='" + account + "']/archive");
			for (int i = 0; i < countArchiveUser; i++) {
				Archive archive = new Archive();
				String archAliasUser = xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/archive[" + (i + 1) + "]/@alias");
				String archAlias = xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAliasUser + "']/@alias");
				if (archAlias.equals(archAliasUser)) {
					String archGrp = xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup[child::archive/@alias='" + archAlias + "']/@name");
					archive.setGroupName(archGrp);
					archive.setRole(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/archive[@alias='" + archAlias + "']/@role"));
					archive.setArchiveDescr(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/text()"));
					archive.setAlias(archAlias);
					archive.setHost(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@host"));
					archive.setIco(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@ico"));
					archive.setPne(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@pne"));
					archive.setPort(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@port"));
					archive.setWebapp(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@webapp"));
					archive.setType(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@type"));
					userBean.putArchives(archAlias, archive);
					userBean.addArchives(archive);
				} 
			}
			Account accountBean = new Account();
			accountBean.setDescrAccount(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/@descrAccount"));
			accountBean.setId(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/@id"));
			accountBean.setFatherAccount(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/@fatherAccount"));
			userBean.setAccount(accountBean);
			
			
			if (userBean.getAccount().equals("") || userBean.getId().equals("")) {
				return null;
			}

			System.out.println(userBean);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}
}
