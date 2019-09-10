package org.xdams.admin.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xdams.admin.bean.CreateArchiveResultBean;
import org.xdams.user.bean.Archive;
import org.xdams.utility.CommonUtils;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

@Component
@Scope("prototype")
public class CreateArchive {

	private String dbDir = "";
	
	private String dbInitCatDir = "";

	private String dbDirWriteConf = "";

	private String nameXWPrefix;

	private Archive archive;

	private int numberOfFill;

	private boolean nameFromXWINI = false;

	private String extrawayDir = "";

	public CreateArchive() throws Exception {

	}

	public static void main(String[] args) {
		try {
			new CreateArchive().generateName("xDamsHist");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreateArchiveResultBean execute(String xDamsType) throws Exception {
		CreateArchiveResultBean createArchiveResultBean = new CreateArchiveResultBean();
		try {
			String confArchive = ConfManager.getConfString("generateArchiveConf" + File.separator + xDamsType + ".conf.xml");
		} catch (Exception e) {
			createArchiveResultBean.setCreated(false);
			createArchiveResultBean.setxDamsType(xDamsType);
			createArchiveResultBean.setMsg("Impossibile creare archivio - conf non disponibile - ");
			return createArchiveResultBean;
		}

		String newArchivID = null;
		if (nameFromXWINI) {
			newArchivID = generateNameFromINI(xDamsType);
		} else {
			newArchivID = generateName(xDamsType);
		}
		System.out.println("newArchivID: " + newArchivID);

		if (newArchivID == null) {
			createArchiveResultBean.setCreated(false);
			createArchiveResultBean.setxDamsType(xDamsType);
			createArchiveResultBean.setMsg("Impossibile creare archivio - error 1001 - ");
			return createArchiveResultBean;
		}

		boolean verifyIfPresent = verifyIfPresent(newArchivID);

		if (verifyIfPresent) {
			createArchiveResultBean.setCreated(false);
			createArchiveResultBean.setxDamsType(xDamsType);
			createArchiveResultBean.setMsg("Archivio esistente");
			createArchiveResultBean.setAlias(newArchivID);
			return createArchiveResultBean;
		}

		// System.out.println("CreateArchive.execute() verifyIfPresent: " + verifyIfPresent);
		String maskID = newArchivID.replaceAll(nameXWPrefix + xDamsType, "");
		// System.out.println("maskID: " + maskID);
		boolean created = createArchive(newArchivID, xDamsType, maskID);
		if (created) {
			// System.out.println("created: " + created + " newArchivID: " + newArchivID);
			createArchiveResultBean.setCreated(true);
			createArchiveResultBean.setxDamsType(xDamsType);
			createArchiveResultBean.setAlias(newArchivID);
		} else {
			createArchiveResultBean.setCreated(false);
			createArchiveResultBean.setxDamsType(xDamsType);
			createArchiveResultBean.setMsg("Impossibile creare archivio - error 1002 - ");
			createArchiveResultBean.setAlias(newArchivID);
		}

		System.out.println("CreateArchive.execute() createArchiveResultBean " + createArchiveResultBean);

		return createArchiveResultBean;
	}

	public boolean verifyIfPresent(String dbName) throws Exception {
		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwConnection = null;
		try {
			xwConnection = connectionManager.getConnection(dbName, archive.getHost(), archive.getPort(), archive.getPne());
		} catch (Exception e) {
			return false;
		} finally {
			connectionManager.closeConnection(xwConnection);
		}
		return true;
	}

	public String generateNameFromINI(String xDamsType) throws Exception {
		// ConnectionManager connectionManager = new ConnectionManager();
		// XWConnection xwConnection = null;
		String newArchivID = nameXWPrefix + xDamsType;
		// System.out.println("archive: " + archive);
		try {

			// xwConnection = connectionManager.getConnection(archive);
			// devo leggere xw.ini
			// String xmlCommand = "<?xml version=\"1.0\"?>\n";
			// xmlCommand += "<cmd c=\"4\" bits=\"7\">\n";
			// xmlCommand += "</cmd>\n";
			// String result = xwConnection.XMLCommand(xwConnection.connection, archive.getAlias(), xmlCommand);
			// Pattern patternUser = Pattern.compile("<dtl dtype=\"info\" dval=\"([^>]+)\"/>", Pattern.DOTALL);
			// Matcher matcherUser = patternUser.matcher(result);
			// System.out.println("result: " + result);
			String result = FileUtils.readFileToString(new File(extrawayDir + "conf/xw.ini"));
			Scanner scanner = new Scanner(result);
			List<Integer> list = new ArrayList<Integer>();
			while (scanner.hasNextLine()) {
				String idArch = scanner.nextLine();
				if (idArch.contains("=")) {
					idArch = StringUtils.substringBefore(idArch, "=");
//					System.out.println("CreateArchive.generateName() idArch: " + idArch);
					if (idArch.startsWith(nameXWPrefix + xDamsType)) {
						try {
							Integer integer = Integer.parseInt(idArch.replaceAll(nameXWPrefix + xDamsType, ""));
							// System.out.println("CreateArchive.generateName() integer: " + integer);
							list.add(integer);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			scanner.close();

			// while (matcherUser.find()) {
			// String idArch = matcherUser.group(1);
			// // System.out.println("nameXWPrefix + xDamsType: " + nameXWPrefix + xDamsType);
			// if (idArch.startsWith(nameXWPrefix + xDamsType)) {
			// // System.out.println("CreateArchive.generateName() idArch: " + idArch);
			// try {
			// Integer integer = Integer.parseInt(idArch.replaceAll(nameXWPrefix + xDamsType, ""));
			// // System.out.println("CreateArchive.generateName() integer: " + integer);
			// list.add(integer);
			// } catch (Exception e) {
			// // TODO: handle exception
			// }
			// }
			// }

			// System.out.println("CreateArchive.generateName() listPrima: " + list);
			Integer[] integers = (Integer[]) list.toArray(new Integer[0]);
			Arrays.sort(integers);
			// System.out.println("CreateArchive.generateName() listDOPO: " + integers);
			if (integers.length == 0) {
				newArchivID += StringUtils.leftPad(String.valueOf(1), numberOfFill, '0');
			} else {
				newArchivID += StringUtils.leftPad(String.valueOf(integers[integers.length - 1] + 1), numberOfFill, '0');
			}
			// System.out.println("ULTIMO: " + list.get(list.size() - 1));
			// System.out.println("newArchivID: " + newArchivID);
		} catch (Exception e) {
			e.printStackTrace();
			newArchivID = null;
		} finally {
			// connectionManager.closeConnection(xwConnection);
		}
		return newArchivID;
	}

	public String generateName(String xDamsType) throws Exception {
		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwConnection = null;
		String newArchivID = nameXWPrefix + xDamsType;
		// System.out.println("archive: " + archive);
		try {

			xwConnection = connectionManager.getConnection(archive);
			// devo leggere xw.ini
			String xmlCommand = "<?xml version=\"1.0\"?>\n";
			xmlCommand += "<cmd c=\"4\" bits=\"7\">\n";
			xmlCommand += "</cmd>\n";
			String result = xwConnection.XMLCommand(xwConnection.connection, archive.getAlias(), xmlCommand);
			Pattern patternUser = Pattern.compile("<dtl dtype=\"info\" dval=\"([^>]+)\"/>", Pattern.DOTALL);
			Matcher matcherUser = patternUser.matcher(result);
			System.out.println("result: " + result);

			List<Integer> list = new ArrayList<Integer>();
			while (matcherUser.find()) {
				String idArch = matcherUser.group(1);
				// System.out.println("nameXWPrefix + xDamsType: " + nameXWPrefix + xDamsType);
				if (idArch.startsWith(nameXWPrefix + xDamsType)) {
					// System.out.println("CreateArchive.generateName() idArch: " + idArch);
					try {
						Integer integer = Integer.parseInt(idArch.replaceAll(nameXWPrefix + xDamsType, ""));
						// System.out.println("CreateArchive.generateName() integer: " + integer);
						list.add(integer);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			// System.out.println("CreateArchive.generateName() listPrima: " + list);
			Integer[] integers = (Integer[]) list.toArray(new Integer[0]);
			Arrays.sort(integers);
			// System.out.println("CreateArchive.generateName() listDOPO: " + integers);
			if (integers.length == 0) {
				newArchivID += StringUtils.leftPad(String.valueOf(1), numberOfFill, '0');
			} else {
				newArchivID += StringUtils.leftPad(String.valueOf(integers[integers.length - 1] + 1), numberOfFill, '0');
			}
			// System.out.println("ULTIMO: " + list.get(list.size() - 1));
			// System.out.println("newArchivID: " + newArchivID);
		} catch (Exception e) {
			e.printStackTrace();
			newArchivID = null;
		} finally {
			connectionManager.closeConnection(xwConnection);
		}
		return newArchivID;
	}

	public boolean createArchive(String dbName, String xDamsType, String mask) {
		boolean esito = false;

		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwConnection = null;
		try {
			String sDb = getDbDir() + File.separator + dbName + File.separator + xDamsType;
			String fileConfWriteTo = getDbDir() + File.separator + dbName + File.separator + xDamsType + ".conf.xml";
			//mi serve per definire se la cartella di inizializzazione archivi e' diversa da quella di creazione, vedi casi di cartelle remote
			if(getDbInitCatDir()!=null && !getDbInitCatDir().equals("")){
				sDb = getDbInitCatDir() + File.separator + dbName + File.separator + xDamsType;
			}
			System.out.println("sDb: " + sDb);
			System.out.println("fileConfWriteTo: " + fileConfWriteTo);
			try {
				String confArchive = ConfManager.getConfString("generateArchiveConf" + File.separator + xDamsType + ".conf.xml");
				// System.out.println("confArchive: " + confArchive.replaceAll("MASKNUM", mask));

				// permessi da aggiornare
				Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
				perms.add(PosixFilePermission.OWNER_WRITE);
				perms.add(PosixFilePermission.OWNER_READ);
				perms.add(PosixFilePermission.OWNER_EXECUTE);
				perms.add(PosixFilePermission.GROUP_WRITE);
				perms.add(PosixFilePermission.GROUP_READ);
				perms.add(PosixFilePermission.GROUP_EXECUTE);
				perms.add(PosixFilePermission.OTHERS_READ);

				System.out.println("CreateArchive.createArchive() PRIMA DI CREARE LA DIRECTORY : " + (getDbDir() + File.separator + dbName));
				boolean dirCreated = new File(getDbDir() + File.separator + dbName + File.separator).mkdirs();
				if (!CommonUtils.isWindows()) {
					Files.setPosixFilePermissions(new File(getDbDir() + File.separator + dbName + File.separator).toPath(), perms);
				}
				System.out.println("CreateArchive.createArchive() DOPO AVER CREATO LA DIRECTORY : " + (getDbDir() + File.separator + dbName));
				System.out.println("CreateArchive.createArchive() dirCreated : " + dirCreated);

				FileUtils.writeStringToFile(new File(fileConfWriteTo), confArchive.replaceAll("MASKNUM", mask), "ISO-8859-1");
				if (!CommonUtils.isWindows()) {
					Files.setPosixFilePermissions(new File(fileConfWriteTo).toPath(), perms);
				}
				xwConnection = connectionManager.getConnection(archive);
				xwConnection.createDb(xwConnection.connection, sDb, dbName, true);
				xwConnection.close();
				xwConnection.invalidateHost();
				xwConnection = connectionManager.getConnection(archive);
				// System.out.println("xDamsType: " + xDamsType);
				esito = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					xwConnection.close();
				} catch (Exception e2) {
					// e2.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return esito;
	}

	public String getDbDir() {
		if (dbDir != null) {
			return FilenameUtils.normalizeNoEndSeparator(dbDir);
		}
		return dbDir;
	}

	public void setDbDir(String dbDir) {
		this.dbDir = dbDir;
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public String getDbDirWriteConf() {
		return dbDirWriteConf;
	}

	public void setDbDirWriteConf(String dbDirWriteConf) {
		this.dbDirWriteConf = dbDirWriteConf;
	}

	public String getNameXWPrefix() {
		return nameXWPrefix;
	}

	public void setNameXWPrefix(String nameXWPrefix) {
		this.nameXWPrefix = nameXWPrefix;
	}

	public int getNumberOfFill() {
		return numberOfFill;
	}

	public void setNumberOfFill(int numberOfFill) {
		this.numberOfFill = numberOfFill;
	}

	public boolean isNameFromXWINI() {
		return nameFromXWINI;
	}

	public void setNameFromXWINI(boolean nameFromXWINI) {
		this.nameFromXWINI = nameFromXWINI;
	}

	public String getExtrawayDir() {
		return extrawayDir;
	}

	public void setExtrawayDir(String extrawayDir) {
		this.extrawayDir = extrawayDir;
	}

	public String getDbInitCatDir() {
		return dbInitCatDir;
	}

	public void setDbInitCatDir(String dbInitCatDir) {
		this.dbInitCatDir = dbInitCatDir;
	}
}
