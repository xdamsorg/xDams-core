package org.xdams.ajax.command;

import it.highwaytech.db.QueryResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;

public class AjaxCommandVocabolarioJson {

	private HttpServletRequest req = null;

	private HttpServletResponse res = null;

	private ModelMap modelMap = null;

	public AjaxCommandVocabolarioJson(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws FileNotFoundException, IOException {
		this.req = req;
		this.res = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		ConfBean confBean = null;
		AjaxBean ajaxBean = new AjaxBean();
		String laChiave = MyRequest.getParameter("key", req.getParameterMap());
		String ilValore = MyRequest.getParameter("value", req.getParameterMap());
		String filterQuery = MyRequest.getParameter("filterQuery", req.getParameterMap());
		WorkFlowBean workFlowBean = null;
		// System.out.println("AjaxCommandVocabolarioJson.execute() !!!" + ilValore+"!!!");
		// System.out.println("AjaxCommandVocabolarioJson.execute() " + ilValore);
		// System.out.println("AjaxCommandVocabolarioJson.execute() " + ilValore);
		// System.out.println("AjaxCommandVocabolarioJson.execute() " + ilValore);
		if (ilValore.trim().startsWith("\" ") && ilValore.trim().length() > 2) {
			ilValore = ilValore.substring(2, ilValore.length() - 1);
		}

		if (ilValore.trim().equals("") || ilValore.equals("*")) {
			ilValore = MyRequest.getParameter("attrFirstIdx", req.getParameterMap());
		}

		int numKeys = 10;
		try {
			numKeys = Integer.parseInt(MyRequest.getParameter("numKeys", req.getParameterMap()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		boolean isQuery = false;

		String laTipologia = MyRequest.getParameter("typology", req.getParameterMap());
		String common = "";
		laChiave = laChiave.replaceAll("\\[", "").replaceAll("\\]", "");
		java.util.Vector result = null;
		byte ilByte = it.highwaytech.broker.ServerCommand.btree_FREQUENZE;
		byte ilByteVerso = it.highwaytech.broker.ServerCommand.btree_NEXTKEY;
		String verso = "up";

		boolean reverseMode = false;
		String valori = "";

		if (laTipologia.startsWith("double")) {
			common = " ";
		}
		if (laTipologia.startsWith("doubleQuery")) {
			isQuery = true;
		}

		if (laTipologia.indexOf("Reverse") > -1) {
			ilByteVerso = it.highwaytech.broker.ServerCommand.btree_PREVKEY;
			reverseMode = true;
			verso = "down";
		}

		try {
			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			xwconn = connectionManager.getConnection(workFlowBean.getArchive());
			if (!filterQuery.equals("")) {
				filterQuery = URLDecoder.decode(filterQuery, "ISO-8859-1");
				QueryResult qr = xwconn.getQRfromPhrase(filterQuery);
				xwconn.setCurrentSet(qr);
				ilByte |= it.highwaytech.broker.ServerCommand.btree_SPETTRALE;
				// result = xwconn.getSingleKeys(laChiave, qr.elements, verso, common + ilValore, 1, qr.elements, "", "frequenze|spettrale");
				result = xwconn.getIdxKeys(xwconn.connection, workFlowBean.getAlias(), "TABELLA", laChiave, common + ilValore, common, numKeys, ilByteVerso, ilByte, 1, 99999999, null);
			} else {
				result = xwconn.getIdxKeys(xwconn.connection, workFlowBean.getAlias(), "TABELLA", laChiave, common + ilValore, common, numKeys, ilByteVerso, ilByte, 1, 99999999, null);

			}
			if (result != null && result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {

					it.highwaytech.db.DbKey key = null;
					if (!reverseMode) {
						key = (it.highwaytech.db.DbKey) result.elementAt(i);
					} else { // CHIAVI INVERSE

						key = (it.highwaytech.db.DbKey) result.elementAt((result.size() - 1) - i);
					}
					// numDocs += key.getKeyFrequency();
					// System.err.println(key.getKeyFrequency()+" "+key.toString()+"<br>");
					// if(key.toString().indexOf(ilValore)==0)
					if (isQuery)
						valori += "\n{\"value\":\"\\\"" + key.toString() + "\\\"\", \"label\":\"" + key.toString().trim() + "\", \"frequency\":" + key.getKeyFrequency() + "},";
					else
						valori += "\n{\"value\":\"" + (key.toString()).trim() + "\", \"label\":\"" + (key.toString()).trim() + "\", \"frequency\":" + key.getKeyFrequency() + "},";
				}
				if (valori.endsWith(",")) {
					valori = valori.substring(0, valori.length() - 1);
				}

			} else {
				valori = "{\"value\":\"\", \"label\":\""+workFlowBean.getLocalizedString("nessun_elemento", "nessun elemento")+"\", \"frequency\":0}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			valori = "{\"value\":\"\", \"label\":\""+workFlowBean.getLocalizedString("nessun_elemento", "nessun elemento")+"\", \"frequency\":0}";
		} finally {
			ajaxBean.setStrXmlOutput("{\"items\": [" + valori + "]}");
			connectionManager.closeConnection(xwconn);
		}
//	    System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz "+valori);

		return ajaxBean;
	}

}
