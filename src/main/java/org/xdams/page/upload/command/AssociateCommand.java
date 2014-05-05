package org.xdams.page.upload.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.upload.bean.UploadBean;
import org.xdams.page.upload.bean.UploadCommandLine;
import org.xdams.page.upload.modeling.LoadUploadBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.CompositionRule;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class AssociateCommand {
	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public AssociateCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String pathToView = MyRequest.getParameter("pathToView", parameterMap);
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
 			if (!pathToView.equals("")) {
				File file = new File(pathToView);
				File[] files = file.listFiles();
				modelMap.put("files", files);
			}else{
				File file = new File(uploadBean.getAssociatePathDir());
				File[] files = file.listFiles();
				modelMap.put("files", files);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
	}
}
