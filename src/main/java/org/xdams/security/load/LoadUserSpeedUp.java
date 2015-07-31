package org.xdams.security.load;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xdams.user.bean.Account;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;

/*
 * NOT TESTED
 * 
 * */
public class LoadUserSpeedUp {

	public static String[] extractUser(String username, String account, String xmlUsers) {
		Pattern patternUser = Pattern.compile("((?i)<user\\s*([^>]+)>(.+?)</user>)", Pattern.DOTALL);
		Matcher matcherUser = patternUser.matcher(xmlUsers);
		String[] myUserAndAttribute = new String[2];
		// cerco il mio utente dentro il file utenti
		while (matcherUser.find()) {
			String myUser = matcherUser.group(1);
			String myUserAttribute = matcherUser.group(2).replaceAll("\\s*=\\s*[^\\\"]", "=");
			// System.out.println("LoadUserSpeedUp.extractUser() myUser:" + myUser);
			// System.out.println("LoadUserSpeedUp.extractUser() myUserAttribute:" + myUserAttribute);
			String myUsername = valueAttribute(myUserAttribute, "id");
			String myUsernameAccount = valueAttribute(myUserAttribute, "account");
			// System.out.println("LoadUserSpeedUp.extractUser() myUsername: "+myUsername);
			// System.out.println("LoadUserSpeedUp.extractUser() myUsernameAccount: "+myUsernameAccount);
			// System.out.println("LoadUserSpeedUp.extractUser() username: "+username);
			// System.out.println("LoadUserSpeedUp.extractUser() account: "+account);
			// System.out.println("LoadUserSpeedUp.extractUser() myUsername.equals(username) && myUsernameAccount.equals(account) " + (myUsername.equals(username) && myUsernameAccount.equals(account)));
			if (myUsername.equals(username) && myUsernameAccount.equals(account)) {
				myUserAndAttribute[0] = myUser;
				myUserAndAttribute[1] = myUserAttribute;
				break;
			}
			// if (myUser.contains(username) && myUser.contains(account)) {
			// myUserAndAttribute[0] = myUser;
			// myUserAndAttribute[1] = myUserAttribute;
			// break;
			// }
		}
		return myUserAndAttribute;
	}

	@Deprecated
	public static List<Archive> extractArchiveUserList(String username, String account, String myUser) {
		List<Archive> archives = new ArrayList<Archive>();
		try {
			Pattern patternArchive = Pattern.compile("((?i)<archive\\s*([^>]+)>(.+?)</archive>)", Pattern.DOTALL);
			Matcher matcherArchive = patternArchive.matcher(myUser);
			while (matcherArchive.find()) {
				Archive archive = new Archive();
				String archiveStr = matcherArchive.group(1);
				String archiveAttribute = matcherArchive.group(2).replaceAll("\\s*=\\s*[^\\\"]", "=");
				archive.setAlias(valueAttribute(archiveAttribute, "alias"));
				archive.setRole(valueAttribute(archiveAttribute, "role"));
				// System.out.println("archive: " + archive);
				archives.add(archive);
				// System.out.println("#################################################");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return archives;
	}

	public static Map<String, Archive> extractArchiveUserListSimple(String username, String account, String myUser) {
		// List<String> archives = new ArrayList<String>();
		Map<String, Archive> archives = new LinkedHashMap<String, Archive>();
		try {
			Pattern patternArchive = Pattern.compile("((?i)<archive\\s*([^>]+)>(.+?)</archive>)", Pattern.DOTALL);
			Matcher matcherArchive = patternArchive.matcher(myUser);
			while (matcherArchive.find()) {
				Archive archive = new Archive();
				String archiveStr = matcherArchive.group(1);
				String archiveAttribute = matcherArchive.group(2).replaceAll("\\s*=\\s*[^\\\"]", "=");
				archive.setAlias(valueAttribute(archiveAttribute, "alias"));
				archive.setRole(valueAttribute(archiveAttribute, "role"));
				// System.out.println("archive: " + archive);
				archives.put(valueAttribute(archiveAttribute, "alias"), archive);
				// System.out.println("#################################################");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return archives;
	}

	public static Map<String, Archive> extractArchiveList(String account, String xmlArchives, Account accountBean) {
		Map<String, Archive> archivesMap = new LinkedHashMap<String, Archive>();
		try {
			// mi trovo gli account
			Pattern patternAccount = Pattern.compile("((?i)<account\\s*([^>]+)>(.+?)</account>)", Pattern.DOTALL);
			Matcher matcherAccount = patternAccount.matcher(xmlArchives);
			// mi trovo gli archiveGroup
			Pattern patternArchiveGroup = Pattern.compile("((?i)<archiveGroup\\s*([^>]+)>(.+?)</archiveGroup>)", Pattern.DOTALL);
			// mi trovo gli archive
			Pattern patternArchive = Pattern.compile("((?i)<archive ([^>]+)>(.+?)</archive>)", Pattern.DOTALL);
			while (matcherAccount.find()) {
				String accountStr = matcherAccount.group(1);
				String accountAttribute = matcherAccount.group(2).replaceAll("\\s*=\\s*[^\\\"]", "=");
				if (valueAttribute(accountAttribute, "id").equals(account)) {
					accountBean.setId(valueAttribute(accountAttribute, "id"));
					accountBean.setDescrAccount(valueAttribute(accountAttribute, "descrAccount"));
					accountBean.setFatherAccount(valueAttribute(accountAttribute, "fatherAccount"));
					// System.out.println("accountStr: " + accountStr);
					// System.out.println("accountAttribute: " + accountAttribute);
					Matcher matcherArchiveGroup = patternArchiveGroup.matcher(accountStr);
					while (matcherArchiveGroup.find()) {
						String archiveGroupStr = matcherArchiveGroup.group(1);
						String archiveGroupAttributeStr = matcherArchiveGroup.group(2).replaceAll("\\s*=\\s*[^\\\"]", "=");
						String groupName = valueAttribute(archiveGroupAttributeStr, "name");

						// System.out.println("matcherArchiveGroup.group(1): " + matcherArchiveGroup.group(1));
						// System.out.println("archiveGroupStr: " + archiveGroupStr);
						Matcher matcherArchive = patternArchive.matcher(archiveGroupStr);
						while (matcherArchive.find()) {
							Archive archive = new Archive();

							String archiveStr = matcherArchive.group(1);
							String archiveAttributeStr = matcherArchive.group(2).replaceAll("\\s*=\\s*[^\\\"]", "=");
							String archiveTextStr = matcherArchive.group(3);
							// System.out.println("archiveTextStr: " + archiveTextStr);
							archive.setGroupName(groupName);
							archive.setAlias(valueAttribute(archiveAttributeStr, "alias"));
							archive.setHost(valueAttribute(archiveAttributeStr, "host"));
							archive.setIco(valueAttribute(archiveAttributeStr, "ico"));
							archive.setArchiveDescr(archiveTextStr);
							archive.setPne(valueAttribute(archiveAttributeStr, "pne"));
							archive.setPort(valueAttribute(archiveAttributeStr, "port"));
							archive.setWebapp(valueAttribute(archiveAttributeStr, "webapp"));
							archive.setType(valueAttribute(archiveAttributeStr, "type"));
							archivesMap.put(archive.getAlias(), archive);
							// System.out.println("archive: " + archive);

						}
						// System.out.println("#################################################");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return archivesMap;
	}

	public static String valueAttribute(String valuesAttributes, String attributeName) {
		String returnValue = "";
		try {
			Pattern patternAttrValue = Pattern.compile(attributeName + "=\"(.+?)\"");
			Matcher matcherAttrValue = patternAttrValue.matcher(valuesAttributes);
			while (matcherAttrValue.find()) {
				returnValue = matcherAttrValue.group(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// System.out.println("attributeName: " + attributeName + " returnValue: " + returnValue + "!!!!!!!!!!!!!!!!!");
		return returnValue;
	}

	public static UserBean loadUserByString(String xmlUsers, String xmlArchives, String xmlrole, String username, String account) {

		UserBean userBean = new UserBean();
		try {

			xmlUsers = xmlUsers.replaceAll("(?s)<!--.*?-->", "");
			xmlArchives = xmlArchives.replaceAll("(?s)<!--.*?-->", "");
			// System.out.println("LoadUserSpeedUp.loadUserByString() username:"+username);
			// System.out.println("LoadUserSpeedUp.loadUserByString() account:"+account);
			// System.out.println("LoadUserSpeedUp.loadUserByString() xmlUsers:"+xmlUsers);
			// System.out.println("###########################################################################");
			String myUser = extractUser(username, account, xmlUsers)[0];
			// System.out.println("LoadUserSpeedUp.loadUserByString() myUser:"+myUser);
			String myUserAttribute = extractUser(username, account, xmlUsers)[1];
			// System.out.println("LoadUserSpeedUp.loadUserByString() myUserAttribute:"+myUserAttribute);
			Account accountBean = new Account();
			Map<String, Archive> archiveAllMap = extractArchiveList(account, xmlArchives, accountBean);
			Map<String, Archive> archiveUserMap = extractArchiveUserListSimple(username, account, myUser);
			// System.out.println(myUser);
			// System.out.println(myUserAttribute);
			userBean.setName(valueAttribute(myUserAttribute, "name"));
			userBean.setLastName(valueAttribute(myUserAttribute, "lastName"));
			userBean.setId(valueAttribute(myUserAttribute, "id"));
			userBean.setEmail(valueAttribute(myUserAttribute, "email"));
			userBean.setLanguage(valueAttribute(myUserAttribute, "language"));
			userBean.setAccountRef(valueAttribute(myUserAttribute, "account"));
			userBean.setFatherAccountRef(valueAttribute(myUserAttribute, "fatherAccount"));
			userBean.setPwd(valueAttribute(myUserAttribute, "pwd"));
			userBean.setRole(valueAttribute(myUserAttribute, "role"));

			for (Entry<String, Archive> entry : archiveAllMap.entrySet()) {
				String archAlias = entry.getKey();
				Archive archive = entry.getValue();
				if (archiveUserMap.containsKey(archAlias)) {
					Archive userArch = archiveUserMap.get(archAlias);
					Archive archiveNew = archive;
					archiveNew.setRole(userArch.getRole());
					userBean.putArchives(archAlias, archiveNew);
					userBean.addArchives(archiveNew);
				}
			}
			// for (Archive archive : archiveUserList) {
			// String archAlias = archive.getAlias();
			// if (archiveAllMap.containsKey(archAlias)) {
			// Archive archiveNew = archiveAllMap.get(archAlias);
			// archiveNew.setRole(archive.getRole());
			// userBean.putArchives(archAlias, archiveNew);
			// userBean.addArchives(archiveNew);
			// }
			// }
			userBean.setAccount(accountBean);
			if (userBean.getAccount().equals("") || userBean.getId().equals("")) {
				return null;
			}

			// System.out.println(userBean);
			return userBean;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}
}
