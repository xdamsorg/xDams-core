package org.xdams.controller.rest;

import it.highwaytech.broker.XMLCommand;
import it.highwaytech.db.QueryResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.xdams.security.AuthenticationType;
import org.xdams.security.load.LoadUserSpeedUp;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.Account;
import org.xdams.user.bean.Archive;
import org.xdams.utility.XMLCleaner;
import org.xdams.utility.request.MyRequest;
import org.xdams.utility.resource.ConfManager;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.exception.XWException;
import org.xdams.xw.utility.Key;

@Controller
@SessionAttributes({ "userBean" })
@SuppressWarnings("unchecked")
public class xDamsRestController {

	private static final Logger logger = LoggerFactory.getLogger(xDamsRestController.class);

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	ServletContext servletContext;

	@Autowired
	AuthenticationType authenticationType;

	@Autowired
	Boolean multiAccount;

	// @RequestMapping(value = "/springcontent", method = RequestMethod.GET, produces = {
	// "application/xml", "application/json" })
	// @ResponseStatus(HttpStatus.OK)
	// public String getUser() throws UnsupportedEncodingException, TransformerException, XMLException {
	// XMLBuilder builder = ConfManager.getConfXML("media.conf.xml");
	// String validSecretKey = builder.valoreNodo("/root/secretKey/text()");
	// return builder.getXML("ISO-8859-1");
	// }

	@RequestMapping(value = "/rest/{accountID}/{archive}/{secretKey}", method = RequestMethod.GET, produces = "text/xml")
	public @ResponseBody
	String restCall(@PathVariable String archive, @PathVariable String secretKey, @PathVariable String accountID, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {

		XMLBuilder builder = ConfManager.getConfXML("rest-conf/rest-conf.xml");
		String validSecretKey = builder.valoreNodo("/root/secretKey[@alias='" + archive + "']/text()");
		String alias = builder.valoreNodo("/root/secretKey[@alias='" + archive + "']/@alias");
		String xsltType = MyRequest.getParameter("xsltType", "", request);
		StringBuilder outputBuilder = new StringBuilder();

		if (!secretKey.equals(validSecretKey)) {
			return createErrorResponse("Access denied, wrong api key.");
		}
		if (alias.indexOf(archive) == -1) {
			return createErrorResponse("Access denied, can't access this archive.");
		}

		String xsltPath = "";
		if (!xsltType.trim().equals("")) {
			xsltPath = builder.valoreNodo("/root/xslt[@type='" + xsltType + "' and @alias='" + archive + "']/text()");
		}
		String xslt = "";
		if (!xsltPath.trim().equals("")) {
			xslt = "<bst>" + ConfManager.getConfString(xsltPath) + "</bst>";
		}

		Account accountBean = new Account();
		// System.out.println("xDamsRestController.restCall() " + accountID);
		String xmlArchives = ConfManager.getConfString(accountID + "-security/accounts.xml");
		// System.out.println("xDamsRestController.restCall() " + xmlArchives);
		Map<String, Archive> archiveAllMap = LoadUserSpeedUp.extractArchiveList(accountID, xmlArchives, accountBean);

		ConnectionManager connectionManager = new ConnectionManager();
		XWConnection xwconn = null;

		String id = request.getParameter("id");
		String physDoc = request.getParameter("physDoc");
		String xwQuery = request.getParameter("xwQuery");
		String valueQuery = request.getParameter("valueQuery");
		String query = request.getParameter("query");
		String mode = request.getParameter("mode");
		
		int perpage = 10;
		int pageToShow = 1;
		try {
			pageToShow = Integer.parseInt(request.getParameter("pageToShow"));
		} catch (Exception e) {
			pageToShow = 1;
		}
		try {
			perpage = Integer.parseInt(request.getParameter("perpage"));
		} catch (Exception e) {
			perpage = 10;
		}

		try {
			xwconn = connectionManager.getConnection(archiveAllMap.get(archive));
			String command = "";
			QueryResult qr = null;
			List<String> result = null;
			if (mode != null && mode.equals("vocabulary")) {
				String searchAlias = request.getParameter("searchAlias");
				String orientation = request.getParameter("orientation");
				String startParam = request.getParameter("startParam");
				int totResult = Integer.parseInt(request.getParameter("totResult"));
				Vector<Key> keys = xwconn.getSingleKeys(searchAlias, totResult, orientation, startParam);
				outputBuilder.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
				outputBuilder.append("<response>\n");
				for (int i = 0; i < keys.size(); i++) {
					Key key = (Key) keys.elementAt(i);
					outputBuilder.append("<key freq=\"" + key.frequence + "\">" + key.key.toString().trim() + "</key>\n");
				}
				outputBuilder.append("</response>");
			} else {
				if (id != null && id.trim() != null) {
					qr = xwconn.getQRfromPhrase("[XML,/c/@id]=" + id);
					command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + qr.id + "\">" + xslt + "</cmd>";
					result = new ArrayList<String>();
					result.add(qr.id);
					result.add("1");
					result.add(pageToShow + "");
					result.add(pageToShow + "");
					// } else if (xwQuery != null && xwQuery.trim() != null) {
					// String queryFind = "";
					// if (valueQuery != null && valueQuery.trim() != null) {
					// queryFind = "[" + xwQuery + "]=" + valueQuery;
					// } else {
					// queryFind = "[" + xwQuery + "]=*";
					// }
					// result = findAll(xwconn, archiveAllMap.get(archive), pageToShow, perpage, queryFind);
					// command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + result.get(0) + "\">" + xslt + "</cmd>";
				} else if (xwQuery != null && !xwQuery.trim().equals("")) {
					String queryFind = xwQuery;
					result = findAll(xwconn, archiveAllMap.get(archive), pageToShow, perpage, queryFind);
					command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + result.get(0) + "\">" + xslt + "</cmd>";
				} else if (physDoc != null) {
					qr = xwconn.getQRFromHier(Integer.parseInt(physDoc), true);
					command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + qr.id + "\">" + xslt + "</cmd>";
				} else {
					result = findAll(xwconn, archiveAllMap.get(archive), pageToShow, perpage, null);
					command = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<cmd c=\"8\" bits=\"" + (XMLCommand.Export_Full + XMLCommand.Export_Memory) + "\" sel=\"" + result.get(0) + "\">" + xslt + "</cmd>";
				}
				String trasform = xwconn.XMLCommand(xwconn.connection, xwconn.getTheDb(), command);
				trasform = XMLCleaner.clearXwFullXML(trasform, true);

				if (result != null) {
					trasform = trasform.replaceAll("(?i)<\\?xml version=\"1.0\" encoding=\"ISO-8859-1\"\\?>", "");
					outputBuilder.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
					outputBuilder.append("<response found=\"" + result.get(1) + "\" page=\"" + result.get(2) + "\" totPages=\"" + result.get(3) + "\">\n");
					outputBuilder.append(trasform);
					outputBuilder.append("</response>");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return createErrorResponse(e.getMessage());
		} finally {
			try {
				xwconn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return outputBuilder.toString();
	}

	private String createErrorResponse(String message) {
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		outputBuilder.append("<response found=\"0\" page=\"0\" totPages=\"0\">\n");
		outputBuilder.append("<error>");
		outputBuilder.append(message);
		outputBuilder.append("</error>");
		outputBuilder.append("</response>");
		return outputBuilder.toString();
	}

	private static List<String> findAll(XWConnection xwConnection, Archive archive, int pageToShow, int perpage, String query) throws SQLException, XWException {
		if (query == null) {
			query = "([UD,/xw/@UdType]=\"" + archive.getPne() + "\")";
		}

		QueryResult qr = new QueryResult();
		QueryResult qrTemp = null;
		qrTemp = xwConnection.getQRfromPhrase(query);
		int found = qrTemp.elements;

		// controllo sulle variabili
		if (pageToShow < 1) {
			pageToShow = 1;
		}
		if (perpage < 1) {
			perpage = 1;
		}
		int totPages = found / perpage;
		if (found % perpage > 0) {
			totPages++;
		}

		// se la pagina richiesta supera il numero di pagine
		if (pageToShow > totPages) {
			pageToShow = totPages;
		}

		int start = (pageToShow - 1) * perpage;
		Vector<Integer> numDocs = new Vector<Integer>();
		for (int i = start; i < start + perpage; i++) {
			int numDoc = xwConnection.getNumDocFromQRElement(qrTemp, i);
			if (numDoc != 0) {
				numDocs.add(numDoc);
			}
		}
		xwConnection.addToQueryResult(xwConnection.connection, xwConnection.getTheDb(), qr, numDocs);
		// System.out.println("found: " + found + "; start: " + start + "; totPages: " + totPages);
		List<String> result = new ArrayList<String>();
		result.add(qr.id);
		result.add(found + "");
		result.add(pageToShow + "");
		result.add(totPages + "");
		return result;
	}

}
