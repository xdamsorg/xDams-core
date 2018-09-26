package org.xdams.page.upload.modeling;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.ui.ModelMap;
import org.xdams.page.upload.bean.UploadBean;
import org.xdams.page.upload.bean.UploadCommandLine;
import org.xdams.xml.builder.XMLBuilder;

public class LoadUploadBean {

	public static void loadUploadBean(UploadBean uploadBean, XMLBuilder theXMLConfUpload, ModelMap modelMap) throws UnsupportedEncodingException, TransformerException {
		try {
			// String prefixUpload = "/root/upload[@name='" + uploadBean.getUploadName() + "' and @type='" + uploadBean.getUploadType() + "']";
			String prefixUpload = "/root/upload[@type='" + uploadBean.getUploadType() + "']";
			int countUploadSection = theXMLConfUpload.contaNodi(prefixUpload);
			if (countUploadSection == 0) {
				uploadBean.getResultError().append("impostare configurazione upload correttamente upload type non presente");
				throw new Exception("impostare configurazione upload correttamente upload type non presente");
			}
			for (int i = 0; i < countUploadSection; i++) {
				uploadBean.setRenameFile(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/renameFile/text()"));
				uploadBean.setCompositionRuleFile(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/compositionRuleFile/text()"));
				uploadBean.setCompositionRuleDir(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/compositionRuleDir/text()"));
				uploadBean.setCompositionReplaceName(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/compositionReplaceName/text()"));
				uploadBean.setAssociatePathDir(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/associatePathDir/text()"));
				uploadBean.setSkipAccountName(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/skipAccountName/text()"));
				int countCommand = theXMLConfUpload.contaNodi(prefixUpload + "[" + (i + 1) + "]" + "/commandList/command");
				for (int j = 0; j < countCommand; j++) {
					UploadCommandLine commandLine = new UploadCommandLine();
					commandLine.setCommandLine(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/commandList/command[" + (j + 1) + "]/commandLine/text()"));
					commandLine.setUploadTempPath(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/commandList/command[" + (j + 1) + "]/uploadTempPath/text()"));
					commandLine.setUploadMode(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/commandList/command[" + (j + 1) + "]/uploadMode/text()"));
					commandLine.setUploadPath(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/commandList/command[" + (j + 1) + "]/uploadPath/text()"));
					if (commandLine.getUploadPath().toLowerCase().contains("webapp")) {
						Map<String, String> valuesMap = new HashMap<String, String>();
						valuesMap.put("webApp", (String) modelMap.get("realPath"));
						StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
						commandLine.setUploadPath(strSubstitutor.replace(commandLine.getUploadPath()));
					}
					if (commandLine.getUploadTempPath().toLowerCase().contains("webapp")) {
						Map<String, String> valuesMap = new HashMap<String, String>();
						valuesMap.put("webApp", (String) modelMap.get("realPath"));
						StrSubstitutor strSubstitutor = new StrSubstitutor(valuesMap);
						commandLine.setUploadTempPath(strSubstitutor.replace(commandLine.getUploadTempPath()));
					}
					commandLine.setUploadNameDir(theXMLConfUpload.valoreNodo(prefixUpload + "[" + (i + 1) + "]" + "/commandList/command[" + (j + 1) + "]/uploadNameDir/text()"));
					uploadBean.getCommandLine().add(commandLine);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
