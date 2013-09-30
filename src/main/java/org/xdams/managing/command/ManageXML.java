package org.xdams.managing.command;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.springframework.ui.ModelMap;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class ManageXML {

	public String xmlInteraction = "view";

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public ManageXML(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		String physDoc = MyRequest.getParameter("physDoc", parameterMap);
		String makeAction = MyRequest.getParameter("makeAction", parameterMap);
		String selid = MyRequest.getParameter("selid", parameterMap);
		String pos = MyRequest.getParameter("pos", parameterMap);
		String docXML = MyRequest.getParameter("docXML", parameterMap);
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		ManagingBean managingBean = null;
		List<String> confControl = new ArrayList<String>();
		confControl.add("titleManager");
		try {
			managingBean = new ManagingBean();
			// TODO Da rivedere
			managingBean.setXmlInteraction(xmlInteraction);
			managingBean.setSelid(selid);
			UserBean userBean = (UserBean) modelMap.get("userBean");
			confBean = (ConfBean) modelMap.get("confBean");
			WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			if (!physDoc.equals("") && makeAction.equals("")) {
				xwconn = connectionManager.getConnection(workFlowBean.getArchive());
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), Integer.parseInt(physDoc))).getTitle());
				String docXmlToSet = xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc());
				docXmlToSet = XMLCleaner.clearXwXML(docXmlToSet, true);
				if (xmlInteraction.equals("view")) {
					XMLBuilder parsedBuilder = new XMLBuilder(docXmlToSet, "ISO-8859-1");
					docXmlToSet = parsedBuilder.getXML("ISO-8859-1");
					docXmlToSet = docXmlToSet.replaceAll("<", "&#60;");
					docXmlToSet = docXmlToSet.replaceAll(">", "&#62;");
					docXmlToSet = docXmlToSet.replaceAll("\t", "&#160;&#160;&#160;");
					docXmlToSet = docXmlToSet.replaceAll("\n", "<br>");
				} else {
					XMLBuilder parsedBuilder = new XMLBuilder(docXmlToSet, "ISO-8859-1");
					docXmlToSet = parsedBuilder.getXML("ISO-8859-1");
				}
				managingBean.setDocXML(docXmlToSet);
				managingBean.setDocLowerBrother(xwconn.getNumDocNextBrother(managingBean.getPhysDoc()));
				managingBean.setDocUpperBrother(xwconn.getNumDocPreviousBrother(managingBean.getPhysDoc()));
				managingBean.setDocFather(xwconn.getNumDocFather(managingBean.getPhysDoc()));
				managingBean.setDocFirstSon(xwconn.getNumDocFirstSon(managingBean.getPhysDoc()));
				managingBean.setPos(pos);
				if (!selid.equals("")) {
					it.highwaytech.db.QueryResult qr = xwconn.getQRFromSelId(selid);
					managingBean.setNumElementi(qr.elements);
				}
				int posInt = -1;
				try {
					posInt = Integer.parseInt(managingBean.getPos());
				} catch (Exception e) {
					posInt = -1;
				}
				if (posInt > 0) {
					managingBean.setDocPrev(posInt - 1);
				} else {
					managingBean.setDocPrev(-1);
				}
				if (posInt < (managingBean.getNumElementi() - 1)) {
					managingBean.setDocNext(posInt + 1);
				} else {
					managingBean.setDocNext(-1);
				}
				managingBean.setDispatchView("xmlMenu");

			} else if (!physDoc.equals("") && makeAction.equals("true")) { // DO
				xwconn = connectionManager.getConnection(workFlowBean.getArchive());
				managingBean.setPhysDoc(Integer.parseInt(physDoc));
				managingBean.setDispatchView("xmlResult");
				int processati = 0;
				int errori = 0;
				try {
					docXML = docXML.replaceAll("&", "&amp;");
					docXML = docXML.replaceAll("&amp;#", "&#");
					XMLBuilder xmlBuilder = new XMLBuilder(docXML, "ISO-8859-1");
					OutputFormat outputFormat = new OutputFormat();
					outputFormat.setEncoding("ISO-8859-1");
					outputFormat.setIndent(true);
					outputFormat.setIndentSize(0);
					outputFormat.setNewlines(true);
					// outputFormat.setTrimText(true);
					xwconn.executeUpdateByDocNumber(XMLCleaner.clearXwXML(xmlBuilder.getXML(outputFormat), false), managingBean.getPhysDoc());
					processati++;
				} catch (Exception e) {
					managingBean.addErrorMsg(e.getMessage());
					errori++;
				}
				managingBean.setTitle((xwconn.getTitle(xwconn.connection, xwconn.getTheDb(), managingBean.getPhysDoc())).getTitle());
				managingBean.setDocSuccessi(processati);
				managingBean.setDocErrori(errori);
			}

			MultiEditingManager editingManager = new MultiEditingManager(parameterMap, confBean, userBean, workFlowBean);
			editingManager.setTheXML(new XMLBuilder(xwconn.getSingleXMLFromNumDoc(managingBean.getPhysDoc()), "ISO-8859-1"));
			confBean = editingManager.rewriteMultipleConf(confControl);

			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("confBean", confBean);
			modelMap.put("managingBean", managingBean);
			throw new Exception(e.toString());
		} finally {
			connectionManager.closeConnection(xwconn);
		}

		return managingBean;
	}

	public void visit(Node node, int level) {
		// Process node

		// If there are any children, visit each one
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			// Get child node
			Node childNode = list.item(i);
			NamedNodeMap attrs = childNode.getAttributes();
			// Get number of attributes in the element
			if (childNode != null) {
				System.out.println("ManageXML.visit() NodeValue " + childNode.getNodeValue());
				int numAttrs = attrs.getLength();
				// Process each attribute
				for (int x = 0; x < numAttrs; x++) {
					Attr attr = (Attr) attrs.item(x);
					// Get attribute name and value
					String attrValue = attr.getNodeValue();
					System.out.println("ManageXML.visit() attrValue " + attrValue);
				}
			}

			// Visit child node
			visit(childNode, level + 1);
		}
	}

	public String prettyPrint(String html) throws DocumentException, IOException {
		StringWriter sw = new StringWriter();
		org.dom4j.io.OutputFormat format = org.dom4j.io.OutputFormat.createPrettyPrint();
		// These are the default formats from createPrettyPrint, so you needn't set them:
		// format.setNewlines(true);
		// format.setTrimText(true);
		format.setNewLineAfterDeclaration(true);
		format.setLineSeparator("\n");
		format.setPadText(true);
		format.setExpandEmptyElements(true);
		format.setIndent(true);
		format.setIndent("\t");
		format.setXHTML(false); // Default is false, this produces XHTML
		org.dom4j.io.HTMLWriter writer = new org.dom4j.io.HTMLWriter(sw, format);
		org.dom4j.Document document = org.dom4j.DocumentHelper.parseText(html);
		writer.write(document);
		writer.flush();
		return sw.toString();
	}

}
