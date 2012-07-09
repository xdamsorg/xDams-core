package org.xdams.ajax.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.workflow.bean.WorkFlowBean;

public class AjaxSessionCommand {

	private HttpServletRequest aReq = null;

	private HttpServletResponse aRes = null;

	private ModelMap modelMap = null;

	public AjaxSessionCommand(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.aReq = req;
		this.aRes = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {
		AjaxBean ajaxBean = new AjaxBean();
		HttpSession httpSession = null;
		ManagingBean managingBean = null;
		String valori = "";
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);
			if (httpSession.getAttribute(workFlowBean.getManagingBeanName()) == null) {
				managingBean = new ManagingBean();
				if (aReq.getParameter("physDoc") != null) {
					managingBean.addListPhysDoc(aReq.getParameter("physDoc"));
				}
				httpSession.setAttribute(workFlowBean.getManagingBeanName(), managingBean);
			} else {
				managingBean = (ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName());
				if (aReq.getParameter("action") != null && aReq.getParameter("action").equals("addElement")) {
					if (!(managingBean.getListPhysDoc()).contains(aReq.getParameter("physDoc"))) {
						managingBean.addListPhysDoc(aReq.getParameter("physDoc"));
					}
				} else if (aReq.getParameter("action") != null && aReq.getParameter("action").equals("removeElement")) {
					if ((managingBean.getListPhysDoc()).contains(aReq.getParameter("physDoc"))) {
						managingBean.removePhysDoc(aReq.getParameter("physDoc"));
					}
				}
				System.out.println("AjaxSessionCommand.execute()" + aReq.getParameter("action"));
				System.out.println("AjaxSessionCommand.execute()" + managingBean.getMultipleChoise() + " managingBean.getMultipleChoise()");
				if (aReq.getParameter("action").equals("switch")) {
					System.out.println("AjaxSessionCommand.execute()1" + aReq.getParameter("action"));
					System.out.println("AjaxSessionCommand.execute()2" + managingBean.getMultipleChoise() + " managingBean.getMultipleChoise()");
					if ((managingBean.getMultipleChoise()).equals("")) {
						managingBean.setMultipleChoise("none");
					} else {
						managingBean.setMultipleChoise("");
					}
					if (aReq.getParameter("svuota") != null && aReq.getParameter("svuota").equals("ok")) {
						managingBean.setListPhysDoc(new ArrayList());
					}
				}
				httpSession.setAttribute(workFlowBean.getManagingBeanName(), managingBean);
			}
			Enumeration enumeration = httpSession.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String element = (String) enumeration.nextElement();
				valori += "<sessionDoc><text>" + element + "</text><value>" + httpSession.getAttribute(element) + "</value></sessionDoc>\n";
			}
			for (int i = 0; i < managingBean.getListPhysDoc().size(); i++) {
				valori += "<sessionDoc><text>numeroDocumento</text><value>" + managingBean.getListPhysDoc().get(i) + "</value></sessionDoc>\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<sessionList>\n<sessionDoc><text>Errore Caricamento</text><value></value></sessionDoc>\n</sessionList>");
		}
		ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + "<sessionList>\n" + valori + "</sessionList>");
		// ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<?xml-stylesheet type=\"text/xsl\" href=\"http://localhost:8080//xdams-front/REGEXE/xslt/prova.xslt\"?>\n" + "<sessionList>\n" + valori + "</sessionList>");
		return ajaxBean;
	}
}
