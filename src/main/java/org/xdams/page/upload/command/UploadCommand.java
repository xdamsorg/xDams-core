package org.xdams.page.upload.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.upload.bean.UploadBean;
import org.xdams.page.upload.bean.UploadCommandLine;
import org.xdams.page.upload.modeling.LoadUploadBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.CommonUtils;
import org.xdams.utility.CompositionRule;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class UploadCommand {
	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public UploadCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		UploadBean uploadBean = (UploadBean) modelMap.get("uploadBean");
		List<String> confControl = new ArrayList<String>();
		confControl.add("upload");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);
			LoadUploadBean.loadUploadBean(uploadBean, confBean.getTheXMLConfUpload(), modelMap);
			System.out.println(uploadBean);
			// da prendere dentro workflowBean
			String archiveName = workFlowBean.getAlias();
			String domainName = userBean.getAccountRef();
			for (int i = 0; i < uploadBean.getCommandLine().size(); i++) {
				UploadCommandLine uploadCommandLine = uploadBean.getCommandLine().get(i);
				try {
					File fileUploadTempPath = new File(uploadCommandLine.getUploadTempPath());
					if (!(fileUploadTempPath.exists())) {
						fileUploadTempPath.mkdirs();
					}
					String fileNameNew = CommonUtils.stripPunctuation(FilenameUtils.getBaseName(uploadBean.getName()), '-') + UUID.randomUUID().getMostSignificantBits();
					File uploadFile = new File(uploadCommandLine.getUploadTempPath() + System.getProperty("file.separator") + CommonUtils.stripPunctuation(FilenameUtils.getBaseName(uploadBean.getName()), '-') + "." + FilenameUtils.getExtension(uploadBean.getName()));

					uploadFile.setReadable(true);
					uploadBean.setFileExist(uploadFile.exists());
//					if (!uploadFile.exists() || uploadBean.isOverWrite()) {
						uploadBean.getFiledata().transferTo(uploadFile);
//					}					

				} catch (Exception e) {

					uploadBean.getResultError().append(workFlowBean.getLocalizedString("errore_nel_copia_nella_cartella_temporanea", "errore nel copia nella cartella temporanea") + ": " + e.getMessage());
					throw e;
				}

				String numFile = "0001";
				StringBuilder uploadPath = null;
				try {
					// controlli su filesystem se esiste la radice getUploadPath e la sua composizione
					uploadPath = new StringBuilder(uploadCommandLine.getUploadPath().trim());
					if (!uploadPath.toString().endsWith(System.getProperty("file.separator"))) {
						uploadPath.append(System.getProperty("file.separator"));
					}

					uploadPath.append(domainName);
					uploadPath.append(System.getProperty("file.separator"));
					uploadPath.append(archiveName);
					uploadPath.append(System.getProperty("file.separator"));
					uploadPath.append(uploadCommandLine.getUploadNameDir());
					uploadPath.append(System.getProperty("file.separator"));
					// tolgo la punteggiatura
					uploadBean.setIdRecord(CompositionRule.stripPunctuation(uploadBean.getIdRecord(), '\u0000'));
					if (uploadBean.getRenameFile().equalsIgnoreCase("true")) {
						uploadPath.append(CompositionRule.compose(uploadBean.getCompositionRuleDir(), uploadBean.getIdRecord(), System.getProperty("file.separator")));
					}
					File fileUploadPath = new File(uploadPath.toString());
					System.out.println(uploadPath);
					if (!(fileUploadPath.exists())) {
						fileUploadPath.mkdirs();
					} else {
						if (fileUploadPath.listFiles().length == 0) {
							numFile = StringUtils.leftPad("1", 4, "0");
						} else {
							numFile = StringUtils.leftPad("" + (fileUploadPath.listFiles().length + 1), 4, "0");
						}
					}
					if (uploadBean.getRenameFile().equalsIgnoreCase("true") && !uploadBean.getCompositionReplaceName().trim().equals("")) {
						uploadBean.setIdRecord(uploadBean.getIdRecord().replaceAll(uploadBean.getCompositionReplaceName(), ""));
					}
					if (uploadBean.getRenameFile().equalsIgnoreCase("true")) {
						uploadPath.append(CompositionRule.compose(uploadBean.getCompositionRuleFile(), uploadBean.getIdRecord(), "."));
						uploadPath.append(numFile);
						uploadPath.append(".");
						uploadPath.append(org.apache.commons.io.FilenameUtils.getExtension(uploadBean.getName()));
					} else {
						uploadPath.append(uploadBean.getName());
					}

				} catch (Exception e1) {
					throw e1;
				}
				System.out.println("UploadCommand.execute() uploadPath.toString() " + uploadPath.toString());

				// verifico se devo lanciare un comando oppure no
				if (!uploadCommandLine.getCommandLine().trim().equals("") && uploadBean.getUploadType().equalsIgnoreCase("resize")) {
					Map<String, String> valuesMap = new HashMap<String, String>();

					if (CommonUtils.isWindows()) {
						valuesMap.put("imgIn", "\"" + uploadCommandLine.getUploadTempPath() + System.getProperty("file.separator") + CommonUtils.stripPunctuation(FilenameUtils.getBaseName(uploadBean.getName()), '-') + "." + FilenameUtils.getExtension(uploadBean.getName()) + "\"");
						valuesMap.put("imgOut", "\"" + uploadPath.toString() + "\"");
					} else {
						System.out.println("quiiiiiiiii");
						valuesMap.put("imgIn", (uploadCommandLine.getUploadTempPath() + System.getProperty("file.separator") + CommonUtils.stripPunctuation(FilenameUtils.getBaseName(uploadBean.getName()), '-') + "." + FilenameUtils.getExtension(uploadBean.getName())));
						System.out.println("spazi linux" + (uploadCommandLine.getUploadTempPath() + System.getProperty("file.separator") + CommonUtils.stripPunctuation(FilenameUtils.getBaseName(uploadBean.getName()), '-') + "." + FilenameUtils.getExtension(uploadBean.getName())));
						valuesMap.put("imgOut", (uploadPath.toString()));
					}
					StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
					String cmd = strSubstitutor.replace(uploadCommandLine.getCommandLine());
					System.out.println(cmd);
					try {
						Runtime runtime = Runtime.getRuntime();
						Process process = runtime.exec(cmd);
						process.waitFor();
						if (process.exitValue() != 0) {
							InputStream lsOut = process.getErrorStream();
							InputStreamReader r = new InputStreamReader(lsOut);
							BufferedReader in = new BufferedReader(r);
							String line;
							int maxLine = 0;
							while ((line = in.readLine()) != null && maxLine < 10) {
								uploadBean.getResultError().append(line);
								maxLine++;
							}
							in.close();
							r.close();
							lsOut.close();
							System.out.println(uploadBean.getResultError());
						} else {
							String resultName = StringUtils.remove(uploadPath.toString(), uploadCommandLine.getUploadPath());
							resultName = StringUtils.remove(resultName, archiveName);
							// resultName = StringUtils.remove(resultName, domainName);
							resultName = StringUtils.replaceOnce(resultName, domainName, "");
							resultName = StringUtils.remove(resultName, uploadCommandLine.getUploadNameDir());
							resultName = resultName.replaceAll("\\\\", "/");
							resultName = resultName.replaceAll("[/]*(.*)", "/$1");
							if (resultName.endsWith("/")) {
								try {
									resultName = resultName.substring(0, resultName.length() - 1);
								} catch (Exception e) {
									uploadBean.getResultError().append(e.getMessage());
								}
							}
							uploadBean.setResult(new StringBuilder(resultName));
							System.out.println("uploadPath resize: " + resultName);
						}
					} catch (Exception e) {
						uploadBean.getResultError().append(workFlowBean.getLocalizedString("errore_nel_processo_di_conversione_file", "errore nel processo di conversione file") + ": " + e.getMessage());
					}
				} else {
					try {
						FileUtils.copyFile(new File(uploadCommandLine.getUploadTempPath() + System.getProperty("file.separator") + CommonUtils.stripPunctuation(FilenameUtils.getBaseName(uploadBean.getName()), '-') + "." + FilenameUtils.getExtension(uploadBean.getName())),
								new File(uploadPath.toString()));
						System.out.println("uploadPath.toString() " + uploadPath.toString());
						String resultName = StringUtils.remove(uploadPath.toString(), uploadCommandLine.getUploadPath());
						resultName = StringUtils.remove(resultName, archiveName);
						// resultName = StringUtils.remove(resultName, domainName);
						resultName = StringUtils.replaceOnce(resultName, domainName, "");
						resultName = StringUtils.remove(resultName, uploadCommandLine.getUploadNameDir());
						resultName = resultName.replaceAll("\\\\", "/");
						resultName = resultName.replaceAll("[/]*(.*)", "/$1");
						if (resultName.endsWith("/")) {
							try {
								resultName = resultName.substring(0, resultName.length() - 1);
							} catch (Exception e) {
								uploadBean.getResultError().append(e.getMessage());
							}
						}
						uploadBean.setResult(new StringBuilder(resultName));
						System.out.println("uploadPath simple: " + resultName);
					} catch (Exception e) {
						uploadBean.getResultError().append(workFlowBean.getLocalizedString("errore_nella_copia_del_file_nella_cartella_di_destinazione", "errore nella copia del file nella cartella di destinazione") + ": " + e.getMessage());
						throw e;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
	}

}
