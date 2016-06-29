/*
 * Created on 1-giu-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xdams.save;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.CommonUtils;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

/**
 * @author diego
 * 
 *         TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveDocumentCommand {

	// private HttpServletRequest request = null;
	//
	// private ServletContext servletContext = null;
	//
	// public SaveDocumentCommand(HttpServletRequest req, ServletContext servletContext) throws Exception {
	// request = req;
	// this.servletContext = servletContext;
	// }

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public SaveDocumentCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		HttpServletRequest request = null;
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			String thePne = workFlowBean.getArchive().getPne();
			request = workFlowBean.getRequest();
			MyRequest myRequest = new MyRequest(request);
			if (!(myRequest.getParameter("thePne")).equals("")) {
				thePne = myRequest.getParameter("thePne");
			}
			String[] nomiRequest = MyRequest.ordinaRequest(request, "." + thePne + ".");
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			System.out.println("SaveDocumentCommand.execute() thePne: " + thePne);
			XMLBuilder builder = new XMLBuilder(thePne);
			for (int i = 0; i < nomiRequest.length; i++) {
				boolean isCDATA = false;

				String ilNome = nomiRequest[i].replace('.', '/');
				String ilValore = (request.getParameter(nomiRequest[i])).trim();

				//System.out.println("record.put(\"" + ilNome + "\",\"" + ilValore + "\");");

				if (ilNome.endsWith("/@cdata")) {
					ilNome = StringUtils.chomp(ilNome, "/@cdata");
					isCDATA = true;
				}
				if (ilNome.endsWith("/@crypted")) {
					ilNome = StringUtils.chomp(ilNome, "/@crypted");
					if (!ilValore.equals("")) {
						try {
							if (!CommonUtils.isValidMD5(ilValore)) {
								ilValore = new Md5PasswordEncoder().encodePassword(ilValore, null);
							}

						} catch (Exception e) {

						}
					}
				}
				if (!ilValore.equals("")) {
					if (isCDATA) {
						builder.insertNode(ilNome, ilValore, true);
					} else {
						builder.insertNode(ilNome, ilValore);
					}
				}
			}
			String theXML = builder.getXML("ISO-8859-1", false);
			// System.out.println(theXML);
			xwconn.executeUpdateByDocNumber(XMLCleaner.clearXwXML(theXML, false), Integer.parseInt(request.getParameter("physDoc")));
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}
	}
}