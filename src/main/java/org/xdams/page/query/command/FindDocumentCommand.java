package org.xdams.page.query.command;

import it.highwaytech.db.QueryResult;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xw.XWConnection;


public class FindDocumentCommand {

	private String laFrase = "";

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public FindDocumentCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public QueryResult execute(WorkFlowBean workFlowBean, XWConnection xwconn) throws Exception, SQLException {
		QueryResult qr = null;
		try {

			String sorting = MyRequest.getParameter("sorting", parameterMap);
			String selid = MyRequest.getParameter("selid", parameterMap);
			String ramoDoc = MyRequest.getParameter("ramoDoc", parameterMap);
			String leChiavi = "";
			java.util.Enumeration elementi = workFlowBean.getRequest().getParameterNames();
			while (elementi.hasMoreElements()) {
				String nome = (String) elementi.nextElement();
				if (nome.equals("[ramoDoc]")) {
					nome = "ramoDoc";
					ramoDoc = MyRequest.getParameter("[ramoDoc]", parameterMap);
				} else {
					if (nome.equals("[selid]")) {
						nome = "selid";
						ramoDoc = MyRequest.getParameter("[selid]", parameterMap);
					} else {
						if (nome.indexOf("[") == 0 && nome.indexOf("]") > 0 && !(MyRequest.getParameter(nome, parameterMap).trim()).equals("")) {
							laFrase += "(" + nome + "=" + MyRequest.getParameter(nome, parameterMap) + ") AND ";
							leChiavi += (MyRequest.getParameter(nome, parameterMap)).trim() + ",";
						}
					}
				}
			}

			if (laFrase.length() > 0)
				laFrase = "(" + laFrase.substring(0, laFrase.lastIndexOf(" AND ")) + ")";
			// laFrase = "[[" + userBean.getTheArch() + "]]" + laFrase;

			if (!(MyRequest.getParameter("qlphrase", parameterMap).trim()).equals("")) {
				if (laFrase.length() > 0 && !laFrase.substring(laFrase.indexOf("]]") + 2).trim().equals("")) {
					laFrase += " AND ";
				}
				if (MyRequest.getParameter("qlphrase", parameterMap).toLowerCase().indexOf("notinhier:") != -1) {
					try {
						int intDoc = Integer.parseInt(StringUtils.substringAfter(MyRequest.getParameter("qlphrase", parameterMap).toLowerCase(), "notinhier:"));
						QueryResult queryResult = xwconn.getQRFromHier(intDoc, true);
						laFrase += " NOT ([?SEL]=" + queryResult.id + ") AND ([UD,/xw/@UdType/]=\"" + workFlowBean.getArchive().getPne() + "\")";
						System.out.println("FindDocumentCommand.execute() laFrase laFrase laFrase " + laFrase);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {

					laFrase += "(" + MyRequest.getParameter("qlphrase", parameterMap).trim() + ")";
				}

			}
			// System.out.println("FindDocumentCommand.execute() raffina " + aReq.getParameter("raffina"));
			if ((!selid.equals("") && MyRequest.getParameter("raffina", parameterMap).equals("esito"))) {
				laFrase += " AND ([?SEL]=" + selid + ")";
			}

			if (qr == null) {
				if (!ramoDoc.equals("") && MyRequest.getParameter("raffina", parameterMap).equals("tree")) {
					if (ramoDoc.indexOf(";") > 0) {
						String[] iDocFather = ramoDoc.split(";");
						String fraseAppend = "";
						for (int i = 0; i < iDocFather.length; i++) {
							int docFather = Integer.parseInt(iDocFather[i]);
							qr = xwconn.getQRFromHier(docFather, true);
							if (qr.elements > 0) {
								if (i > 0)
									fraseAppend += " OR ";
								fraseAppend += "([?SEL]=" + qr.id + ")";
							}
						}
						if (!laFrase.endsWith("]]"))
							laFrase += " AND ";
						laFrase += "(" + fraseAppend + ")";
					} else {
						int docFather = Integer.parseInt(ramoDoc);
						qr = xwconn.getQRFromHier(docFather, true);
						if (laFrase.length() > 2)
							laFrase += " AND ";
						laFrase += "([?SEL]=" + qr.id + ")";
					}
				}
				// System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				// System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				// System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				// System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				// System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				// System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				// qr = xwconn.getQRfromPhrase(laFrase, sorting);// find(xwconn.connection, theArch, laFrase, sorting, it.highwaytech.broker.ServerCommand.find_SORT, -2, 0, null);
				// qr = xwconn.find(xwconn.connection,xwconn,laFrase,sorting,it.highwaytech.broker.ServerCommand.find_SORT,-3,0,null);
				System.out.println("FindDocumentCommand.execute() laFrase " + laFrase);
				System.out.println("FindDocumentCommand.execute() sorting " + sorting);
				qr = xwconn.selectQR(laFrase, sorting, it.highwaytech.broker.ServerCommand.find_SORT, -6);
				System.out.println("FindDocumentCommand.execute() qr " + qr.id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		}
		return qr;
	}

	/**
	 * @return Returns the laFrase.
	 */
	public String getLaFrase() {
		return laFrase;
	}

	/**
	 * @param laFrase
	 *            The laFrase to set.
	 */
	public void setLaFrase(String laFrase) {
		this.laFrase = laFrase;
	}
}
