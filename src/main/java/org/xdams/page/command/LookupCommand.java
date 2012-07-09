/*
 * Created on 30-mag-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.xdams.page.command;

import it.highwaytech.db.QueryResult;
import it.highwaytech.db.Title;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.form.bean.LookupBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.Titles;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;


/**
 * @author simoneAdm
 */
public class LookupCommand {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public LookupCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public void execute() throws Exception, SQLException {
		XWConnection xwconn = null;
		List<String> titleList = new ArrayList<String>();
		ConnectionManager connectionManager = new ConnectionManager();
 		WorkFlowBean workFlowBean = null;
		Titles titlesPage = new Titles();
		LookupBean lookupBean = null;
		try {

 			workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
			lookupBean = (LookupBean) modelMap.get("lookupBean");
			xwconn = connectionManager.getConnection(workFlowBean.getArchiveLookup());
			QueryResult qr = null;
			String startPage = "";
			String keyCountQuery = MyRequest.getParameter("inputPerPage", "10", parameterMap);
			System.out.println("keyCountQuery keyCountQuery " + keyCountQuery);
			System.out.println("ENTRATO QUI " + (String) modelMap.get("selId"));
			System.out.println("ENTRATO selId " + MyRequest.getParameter("selId", parameterMap));

			if (!(MyRequest.getParameter("selId", parameterMap).trim()).equals("")) {
				qr = xwconn.getQRFromSelId(MyRequest.getParameter("selId", parameterMap));
				System.out.println("Lookup.execute()1111" + qr);
			} else {
				// System.out.println("ENTRATO QUA " + getQrSelId());
				qr = find(xwconn, "singoloTermine", keyCountQuery, lookupBean);
				System.out.println("Lookup.execute()2222" + qr);
			}
			System.out.println("Lookup.execute() " + qr);
			if (!MyRequest.getParameter("flagXML", parameterMap).equals("true")) {
				if (!lookupBean.getInputTitleRule().trim().equals("")) {
					xwconn.setTitleRule(xwconn.connection, workFlowBean.getArchiveLookup().getAlias(), lookupBean.getInputTitleRule());
				}
				System.out.println("Lookup.execute()333333" + qr);
				int totElements = qr.elements;
				// setQrSelId(qr.id);
				// System.out.println("PIPPETTO " + qr.elements + " FINE");
				if (!MyRequest.getParameter("startPage", parameterMap).equals("")) {
					startPage = MyRequest.getParameter("startPage", parameterMap);
				}
				if (startPage.equals("")) {
					titlesPage.setPages(Integer.parseInt(keyCountQuery), totElements);
				} else {
					titlesPage.setPages(Integer.parseInt(keyCountQuery), totElements, Integer.parseInt(startPage));
				}
				int ilPrimo = titlesPage.getFirstElement() - 1;
				for (int x = 0; x < Integer.parseInt(keyCountQuery); x++) {
					if (x + ilPrimo < totElements) {
						Title titolo = xwconn.getTitle(xwconn.connection, workFlowBean.getArchiveLookup().getAlias(), qr, x + ilPrimo);
						System.out.println(titolo.getTitle());
						titleList.add(titolo.getTitle());
					}
				}
				System.out.println("Lookup.execute()44444" + qr);
				modelMap.put("selId", qr.id);
				System.out.println("Lookup.execute()6666666" + qr);
			} else if (MyRequest.getParameter("flagXML", parameterMap).equals("true")) {
				lookupBean.setInputTitleRule("");
				int totElements = qr.elements;
				// setQrSelId(qr.id);
				System.out.println("PIPPETTO " + qr.elements + " FINE");
				if (!MyRequest.getParameter("startPage", parameterMap).equals("")) {
					startPage = MyRequest.getParameter("startPage", parameterMap);
				}

				if (startPage.equals("")) {
					titlesPage.setPages(Integer.parseInt(keyCountQuery), totElements);
				} else {
					titlesPage.setPages(Integer.parseInt(keyCountQuery), totElements, Integer.parseInt(startPage));
				}
				int ilPrimo = titlesPage.getFirstElement() - 1;
				StringBuffer buffer = new StringBuffer();
				for (int x = 0; x < Integer.parseInt(keyCountQuery); x++) {
					if (x + ilPrimo < totElements) {
						int numDoc = xwconn.getNumDocFromQRElement(qr, x + ilPrimo);
						String strXml = xwconn.getSingleXMLFromQr(qr, x + ilPrimo, false);
						try {
							titleList.add(strXml);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				modelMap.put("selId", qr.id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.toString());
		} finally {
			modelMap.put("titleList", titleList);
			modelMap.put("titlesPage", titlesPage);
			try {
				if (!lookupBean.getInputTitleRule().trim().equals("")) {
					xwconn.restoreTitleRule(xwconn.connection, workFlowBean.getArchiveLookup().getAlias());
				}
				connectionManager.closeConnection(xwconn);
			} catch (Exception e1) {
				throw new Exception(e1.toString());
			}
		}
	}

	int contaQuery = 0;

	public QueryResult find(XWConnection xwconn, String tipoQuery, String keyCountQuery, LookupBean lookupBean) throws Exception {
		String flagQuery = "";
		QueryResult qr = null;
		try {
			String finalQuery = "";

			if (!lookupBean.getInputStrQuery().trim().equals("")) {
				if (tipoQuery.equals("singoloTermine")) {
					if (MyRequest.getParameter("flagXML", parameterMap).equals("true")) {
						finalQuery = lookupBean.getInputMultiArchivio() + lookupBean.getInputQuery() + "=" + lookupBean.getInputStrQuery();
					} else {
						finalQuery = lookupBean.getInputQuery() + "=" + lookupBean.getInputStrQuery();
					}
					flagQuery = "asterisco";
				} else if (tipoQuery.equals("asterisco")) {
					if (MyRequest.getParameter("flagXML", parameterMap).equals("true")) {
						finalQuery = lookupBean.getInputMultiArchivio() + "(" + lookupBean.getInputQuery() + "=" + lookupBean.getInputStrQuery() + "*) AND ([UD,/xw/@UdType]=" + lookupBean.getInputUdType() + ")";
					} else {
						finalQuery = "(" + lookupBean.getInputQuery() + "=" + lookupBean.getInputStrQuery() + "*) AND ([UD,/xw/@UdType]=" + lookupBean.getInputUdType() + ")";
					}

					flagQuery = "udType";
				} else if (tipoQuery.equals("udType")) {
					if (MyRequest.getParameter("flagXML", parameterMap).equals("true")) {
						finalQuery = lookupBean.getInputMultiArchivio() + "([UD,/xw/@UdType]=" + lookupBean.getInputUdType() + ")";
					} else {
						finalQuery = "([UD,/xw/@UdType]=" + lookupBean.getInputUdType() + ")";
					}

					flagQuery = "fine";
				}
			} else {
				if (MyRequest.getParameter("flagXML", parameterMap).equals("true")) {
					finalQuery = lookupBean.getInputMultiArchivio() + "([UD,/xw/@UdType]=" + lookupBean.getInputUdType() + ")";
				} else {
					finalQuery = "([UD,/xw/@UdType]=" + lookupBean.getInputUdType() + ")";
				}
				flagQuery = "fine";
			}
			// finalQuery = getInputQuery()+"="+getInputStrQuery();
			if (!lookupBean.getInputExtraQuery().trim().equals("")) {
				finalQuery += " AND " + lookupBean.getInputExtraQuery();
			}
			qr = xwconn.find(xwconn.connection, xwconn.getTheDb(), finalQuery, lookupBean.getInputSort(), it.highwaytech.broker.ServerCommand.find_SORT, -3, 0);
			// System.out.println("qr.elements " + qr.elements + " FINE");
			if (qr.elements == 0 && !tipoQuery.equals("fine")) {
				// System.out.println("ENTRATO IF ");
				// System.out.println("aaaaaaaaaa " + tipoQuery);
				// if (!tipoQuery.equals("fine")) {
				// System.out.println("QUI " + qr.elements + " FINE");
				find(xwconn, flagQuery, keyCountQuery, lookupBean);
				tipoQuery = "fine";
				// }
			}

		} catch (Exception e) {
			contaQuery++;
			if ((e.getMessage()).indexOf("stoplist") != -1 && (contaQuery >= 0 && contaQuery <= 4)) {
				flagQuery = "asterisco";
				find(xwconn, flagQuery, keyCountQuery, lookupBean);
			} else {
				throw e;
			}

		}
		// System.out.println("PRIMA DI RETURN " + qr.elements + " FINE");
		return qr;
	}

}