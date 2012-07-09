package org.xdams.user.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;

@Component
public class ServiceUser {

	// private XMLBuilder xmlUsers = ConfManager.getConfXML("users.xml");
	//
	// private XMLBuilder xmlArchives = ConfManager.getConfXML("accounts.xml");

	//carico tutti gli archivi associati all'utente
	public void loadArchives(UserBean userBean, Map<String, List<Archive>> map) throws Exception {
		List<Archive> archives = userBean.getArchives();
		for (Archive archive : archives) {
			if (map.containsKey(archive.getGroupName())) {
				map.get(archive.getGroupName()).add(archive);
			} else {
				List<Archive> list = new ArrayList<Archive>();
				list.add(archive);
				map.put(archive.getGroupName(), list);
			}
		}
	}

	public static Archive getArchive(UserBean userBean, String alias) throws Exception {
		Archive archive = userBean.getArchivesMap().get(alias);
		if (archive == null) {
			throw new Exception("utente non abilitato sull'archivio o archivio non presente");
		}
		return archive;
	}

	// public List<Archive> getArchives(String username, String account) throws Exception {
	// List<Archive> archives = new ArrayList<Archive>();
	// try {
	//
	// int countArchiveUser = xmlUsers.contaNodi("/root/user[@id='" + username + "' and @account='" + account + "']/archive");
	// for (int i = 0; i < countArchiveUser; i++) {
	// Archive archive = new Archive();
	// String archAliasUser = xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/archive[" + (i + 1) + "]/@alias");
	// String archAlias = xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAliasUser + "']/@alias");
	// if (archAlias.equals(archAliasUser)) {
	// String archGrp = xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup[child::archive/@alias='" + archAlias + "']/@name");
	// archive.setGroupName(archGrp);
	// archive.setRole(xmlUsers.valoreNodo("/root/user[@id='" + username + "' and @account='" + account + "']/archive[@alias='" + archAlias + "']/@role"));
	// archive.setArchiveDescr(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/text()"));
	// archive.setAlias(archAlias);
	// archive.setHost(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@host"));
	// archive.setIco(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@ico"));
	// archive.setPne(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@pne"));
	// archive.setPort(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@port"));
	// archive.setWebapp(xmlArchives.valoreNodo("/root/account[@id='" + account + "']/archiveGroup/archive[@alias='" + archAlias + "']/@webapp"));
	// archives.add(archive);
	// }
	// }
	// } catch (Exception e) {
	// throw e;
	// }
	// return archives;
	// }

}
