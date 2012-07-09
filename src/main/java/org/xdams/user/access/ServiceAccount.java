package org.xdams.user.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xdams.user.bean.Archive;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;


public class ServiceAccount {

	private XMLBuilder xmlUsers = ConfManager.getConfXML("users.xml");

	private XMLBuilder xmlArchives = ConfManager.getConfXML("accounts.xml");

	public void getArchives(String username, String account, Map<String, List<Archive>> map) throws Exception {
		List<Archive> archives = getArchives(username, account);
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

	public List<Archive> getArchives(String username, String account) throws Exception {
		List<Archive> archives = new ArrayList<Archive>();
		try {

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
					archives.add(archive);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return archives;
	}

}
