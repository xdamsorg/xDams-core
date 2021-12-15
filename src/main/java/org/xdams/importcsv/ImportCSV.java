package org.xdams.importcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xdams.mail.sender.MailSender;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

@Service
@EnableAsync
public class ImportCSV {

	@Autowired
	private MailSender mailSender;

	@Async
	public void execute(CommonsMultipartFile commonsMultipartFile, UserBean userBean, Archive archiveBean, Map<String, String> mapextraCVSXML, String numDocFather, String mailTo) throws Exception {
		try {
			XWConnection xwConnection = null;
			ConnectionManager connectionManager = new ConnectionManager();
			List<String> listXMLError = new ArrayList<>();
			List<String> listXML = new ArrayList<>();
			// String titleFather = "";
			try {

				xwConnection = connectionManager.getConnection(archiveBean.getAlias(), archiveBean.getHost(), archiveBean.getPort(), archiveBean.getPne());

				// try {
				// XMLBuilder builderFather = new XMLBuilder(xwConnection.getSingleXMLFromNumDoc(Integer.parseInt(numDocFather)), "ISO-8859-1");
				// titleFather = builderFather.valoreNodo("/c/did/unittitle/text()");
				// } catch (Exception e) {
				// // TODO: handle exception
				// }

				String pathToTrasfert = FilenameUtils.normalizeNoEndSeparator(System.getProperty("java.io.tmpdir"));
				String fileNameAtt = new String(commonsMultipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
				File fileToWrite = new File( pathToTrasfert + File.separator + UUID.randomUUID().toString() + fileNameAtt);

				System.out.println("ImportCSV.execute() AAAAAA excelFile: " + fileToWrite);
				System.out.println("ImportCSV.execute() AAAAAA canExecute: " + fileToWrite.canExecute());
				System.out.println("ImportCSV.execute() AAAAAA canWrite: " + fileToWrite.canWrite());
				System.out.println("ImportCSV.execute() AAAAAA canRead: " + fileToWrite.canRead());
				commonsMultipartFile.transferTo(fileToWrite);
				// System.out.println("ImportCSV.execute() BBBBBB fileToWrite: " + fileToWrite);
				Map<String, List<String>> mapCSV = readCsvFile(fileToWrite.getAbsolutePath());
				listXML = createXML(mapCSV, archiveBean, mapextraCVSXML, listXMLError);
				for (String thisXML : listXML) {
					try {
						int thisPhysDoc = xwConnection.insert(thisXML);
						if (!numDocFather.equals("-1")) {
							xwConnection.docRelInsert(thisPhysDoc, Integer.parseInt(numDocFather), it.highwaytech.broker.ServerCommand.navigarel_FIGLIOPADRE);
						}
					} catch (Exception e) {
						listXMLError.add("Errore inserimento: " + e.getMessage());
					}
				}
				sendMail(archiveBean, listXML, listXMLError, mailTo);
			} catch (Exception e) {
				sendMail(archiveBean, listXML, listXMLError, mailTo);

				throw e;
			} finally {
				connectionManager.closeConnection(xwConnection);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void sendMail(Archive archiveBean, List<String> listXML, List<String> listXMLError, String mailTo) {
		try {
			String resultTemplateMail = "";
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mailHelper = new MimeMessageHelper(mimeMessage, false, "iso-8859-1");
			mailHelper.setSubject("import csv terminato");

			// resultTemplateMail = "Archivio: " + archiveBean.getGroupName() + " / " + archiveBean.getArchiveDescr() + "<br/>";
			resultTemplateMail = "Archivio: " + archiveBean.getGroupName() + " / " + archiveBean.getArchiveDescr() + "<br/>";

			resultTemplateMail += "Numero totale documenti inserti: " + listXML.size() + "<br/>";
			resultTemplateMail += "Numero totale errori : " + listXMLError.size() + "<br/><br/>";
			for (String error : listXMLError) {
				resultTemplateMail += error + "<br/>";
			}
			mailHelper.setText(resultTemplateMail, true);
			mailHelper.setFrom(mailSender.getMailFrom());
			if (mailSender.getAdminTo() != null && !mailSender.getAdminTo().equals("")) {
				mailHelper.setBcc(mailSender.getAdminTo());
			}
			mailHelper.setTo(mailTo);
			mailSender.send(mailHelper.getMimeMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> createXML(Map<String, List<String>> mapCSV, Archive archiveBean, Map<String, String> mapextraCVSXML, List<String> listXMLError) {
		List<String> listXML = new ArrayList<String>();
		try {
			List<String> mapping = mapCSV.get("mapping");
			for (Entry<String, List<String>> entry : mapCSV.entrySet()) {
				if (!entry.getKey().equals("mapping")) {
					// System.out.println(entry.getKey());
					int count = 0;
					XMLBuilder xmlBuilder = new XMLBuilder(archiveBean.getPne());
					boolean writeXML = false;
					for (String value : entry.getValue()) {
						// if (mapping.size() - 1 > count) {
						if (mapping.size() > count) {
							String xPathToInsert = mapping.get(count);
							if (xPathToInsert.contains("[xx]")) {
								if (!value.trim().equals("")) {
									String prefixPath = StringUtils.substringBeforeLast(xPathToInsert, "[xx]");
									String suffixPath = StringUtils.substringAfterLast(xPathToInsert, "[xx]");
									String[] splitv = value.split(";");
									int countPrefix = 1;
									for (String stringVal : splitv) {
										// System.out.println(prefixPath);
										// System.out.println(suffixPath);
										try {
											xmlBuilder.insertValueAt(prefixPath + "[" + countPrefix + "]" + suffixPath, stringVal, false);
										} catch (Exception e) {
											listXMLError.add(prefixPath + "[" + countPrefix + "]" + suffixPath + " -- " + e.getMessage());
										}
										countPrefix++;

									}
									writeXML = true;
								}
							} else if (xPathToInsert.endsWith("/@cdata")) {
								if (!value.trim().equals("")) {
									try {
										xmlBuilder.insertValueAt(StringUtils.removeEndIgnoreCase(xPathToInsert, "/@cdata"), value, true);
									} catch (Exception e) {
										listXMLError.add(xPathToInsert + " -cdata- " + e.getMessage());
									}
									writeXML = true;
								}
							} else {
								if (!value.trim().equals("")) {
									try {
										xmlBuilder.insertValueAt(xPathToInsert, value, false);
									} catch (Exception e) {
										listXMLError.add(xPathToInsert + " -- " + e.getMessage());
									}
									writeXML = true;
								}
							}
							// System.out.println("\t" + value + " --- " + mapping.get(count));
						}
						count++;
					}
					if (writeXML) {
						// String rootEl = xmlBuilder.getRootElement();
						// if (rootEl.equals("c")) {
						// int countPrInfo = xmlBuilder.contaNodi("/" + rootEl + "/processinfo/list/item");
						// xmlBuilder.insertValueAt("/" + rootEl + "/processinfo/list/item[" + (countPrInfo + 1) + "]/text()", "inserimento csv");
						// xmlBuilder.insertValueAt("/" + rootEl + "/processinfo/list/item[" + (countPrInfo + 1) + "]/date/text()", dataCompilatoreCMPN);
						// xmlBuilder.insertValueAt("/" + rootEl + "/processinfo/list/item[" + (countPrInfo + 1) + "]/persname/text()", nomeCompilatoreCMPN);
						// } else {
						//
						// }
						try {
							for (Entry<String, String> entryCVSXML : mapextraCVSXML.entrySet()) {
								xmlBuilder.insertValueAt(entryCVSXML.getKey(), entryCVSXML.getValue());
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						

//						System.out.println(xmlBuilder.getXML("ISO-8859-1"));
						listXML.add(xmlBuilder.getXML("ISO-8859-1", false));
					}
				}
			}
		} catch (Exception e) {

		}
		return listXML;
	}

	public Map<String, List<String>> readCsvFile(String fileName) {
		BufferedReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n").withDelimiter(';').withTrim();
		Map<String, List<String>> mappping = new LinkedHashMap<String, List<String>>();
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-1")); // new FileReader(fileName);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> csvRecords = csvFileParser.getRecords();
			for (int i = 0; i < csvRecords.size(); i++) {
				CSVRecord record = (CSVRecord) csvRecords.get(i);
				// System.out.println("+++" + record + "----");
				for (String string : record) {
					if (i == 0) {
						//
						if (mappping.containsKey("mapping")) {
							mappping.get("mapping").add(string);
						} else {
							List<String> list = new ArrayList<String>();
							list.add(string);
							mappping.put("mapping", list);
						}
					} else if (i != 0 && i != 1) {
						if (mappping.containsKey(i + "")) {
							mappping.get(i + "").add(string);
						} else {
							List<String> list = new ArrayList<String>();
							list.add(string);
							mappping.put(i + "", list);
						}
					}
				}
			}

			// for (Entry<String, List<String>> entryS : mappping.entrySet()) {
			// System.out.println(entryS.getKey());
			// System.out.println(entryS.getValue());
			// }
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}
		return mappping;
	}

}
