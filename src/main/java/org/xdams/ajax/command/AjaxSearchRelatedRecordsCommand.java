package org.xdams.ajax.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.managing.bean.ModifyAutherBean;
import org.xdams.managing.command.ModifyAuther;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCopy;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class AjaxSearchRelatedRecordsCommand {

	private HttpServletRequest req = null;

	private HttpServletResponse res = null;

	private ModelMap modelMap = null;

	public AjaxSearchRelatedRecordsCommand(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {
		AjaxBean ajaxBean = new AjaxBean();
		HttpSession httpSession = null;
		String actionType = MyRequest.getParameter("action", req.getParameterMap());
		String physDoc = MyRequest.getParameter("physDoc", req.getParameterMap());
		ManagingBean managingBean = null;
		String valori = "";
		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwconn = null;
		ConfBean confBean = null;
		WorkFlowBean workFlowBean = null;
		XMLCopy xmlCopy = new XMLCopy();
		// questa lista può essere presa da un file di configurazione
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		confControl.add("docEdit");
		try {
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			httpSession = workFlowBean.getRequest().getSession(false);

			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			MultiEditingManager editingManager = new MultiEditingManager(req.getParameterMap(), confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(Integer.parseInt(physDoc)), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);

			ModifyAuther modifyAuther = new ModifyAuther(req.getParameterMap(), modelMap);
			/*
			 * qui forse ho un problema, forse è meglio non estendere ModifyAutherBean a MangingBean, ma semplicemente metterci un addModifyAutherBean e quindi un get e set all'interno di ManaginBean.
			 */

			String contenitoreIni = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<div class=\"riga_sch_bre\">"+workFlowBean.getLocalizedString("archivi_collegati", "archivi collegati")+"</div>\n<div class=\"box_sch_bre\">\n";
			String string0 = "";
			String contenitoreEnd = "</div>";
			ModifyAutherBean modifyAutherBean = (ModifyAutherBean) modifyAuther.execute();
//			System.out.println(modifyAutherBean);
			boolean atLastOne = false;
			for (int x = 0; x < modifyAutherBean.getArrModifyAutherBean().size(); x++) {
				ModifyAutherBean autherBean = (ModifyAutherBean) (modifyAutherBean.getArrModifyAutherBean()).get(x);
				if (autherBean.getNumElementi() > 0) {
					atLastOne = true;
//					System.out.println(autherBean.getQuery());
					// string0 += "<div class=\"campo\"> archivio alias " + autherBean.getArchivioAlias() + "</div>\n";
					string0 += "<strong><a class=\"cerca_b\" href=\""+modelMap.get("contextPath")+"/search/"+autherBean.getArchivioAlias()+"/title.html?qlphrase="+autherBean.getQuery()+"\" target=\""+autherBean.getArchivioAlias()+"\">" + java.net.URLEncoder.encode(autherBean.getArchivioDescr().replaceAll(" ", "\\&nbsp;"), "iso-8859-1") + "</a></strong> "+workFlowBean.getLocalizedString("numero_elementi", "numero elementi")+" " + autherBean.getNumElementi() + "<br />";
					// string0 += "<div class=\"campo\"> descr. archivio " + (autherBean.getArchivioDescr()) + "</div>\n";
					// string0 += "<div class=\"campo\"> descr. archivio escapeHtml " + StringEscapeUtils.escapeHtml(autherBean.getArchivioDescr()) + "</div>\n";
					// string0 += "<div class=\"campo\"> descr. archivio escapeJavaScript " + StringEscapeUtils.escapeJavaScript(autherBean.getArchivioDescr()) + "</div>\n";
					// string0 += "<div class=\"campo\"> descr. archivio escapeXml " + StringEscapeUtils.escapeXml(autherBean.getArchivioDescr()) + "</div>\n";
					// string0 += "<div class=\"campo\"> descr. archivio escapeXml and escapeJavaScript " + StringEscapeUtils.escapeXml(StringEscapeUtils.escapeJavaScript(autherBean.getArchivioDescr())) + "</div>\n";
					// string0 += "<div class=\"campo\"> descr. archivio escapeJavaScript and escapeXml " + StringEscapeUtils.escapeJavaScript(StringEscapeUtils.escapeXml(autherBean.getArchivioDescr())) + "</div>\n";
					// string0 += "<div class=\"campo\"> query " + autherBean.getQuery() + "</div>\n";
					// string0 += "<div class=\"campo\"></div>\n";
				}
				// <div class="m10"><span class="testoMain12"><input type="checkbox" checked="checked" name="theArchiveToProcess_<%=x%>" value="<%=autherBean.getArchivioAlias()%>"/><em class="testoMainBold12"><%=autherBean.getArchivioDescr()%></em><br> trovate
				// <strong><%=autherBean.getNumElementi()%></strong> occorrenze per <%=autherBean.getQuery()%></span><br></div>
			}

			if (atLastOne) {
				String string = contenitoreIni + string0 + contenitoreEnd;
				ajaxBean.setStrXmlOutput(string);
			} else {
				String string = contenitoreIni + "<strong>"+workFlowBean.getLocalizedString("nessun_elemento_collegato", "nessun elemento collegato")+"</strong>" + contenitoreEnd;
				ajaxBean.setStrXmlOutput(string);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ajaxBean.setStrXmlOutput("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<error>" + StringEscapeUtils.escapeXml(workFlowBean.getLocalizedString("Attenzione_impossibile_effettuare_loperazione_il_documento_selezionato_potrebbe_non_essere_piu_in_gerarchia", "Attenzione: impossibile effettuare l'operazione, il documento selezionato potrebbe non essere più in gerarchia")) + "\n" + e.getMessage() + "</error>");
		} finally {
			connectionManager.closeConnection(xwconn);
		}

		return ajaxBean;
	}
}
