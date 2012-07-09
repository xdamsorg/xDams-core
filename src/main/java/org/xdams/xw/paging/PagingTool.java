package org.xdams.xw.paging;

import it.highwaytech.db.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.xdams.page.view.bean.TitleBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xw.XWConnection;


public class PagingTool {

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public PagingTool(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public List<TitleBean> pagingTitleBean(QueryResult queryResult, XWConnection xwConn) {
		List<TitleBean> titleBeans = new ArrayList<TitleBean>();
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		HttpSession httpSession = workFlowBean.getRequest().getSession(false);
		try {
			// INIZIO PAGINAZIONE
			int pageToShow = 1;
			QRParser qRparser = null;
			QRPage currentPage = null;
			int perpage = 10;
			if (!MyRequest.getParameter("perpage", parameterMap).equals("")) {
				perpage = Integer.parseInt(MyRequest.getParameter("perpage", parameterMap));
			} else if (MyRequest.getAttribute("perpage", workFlowBean.getRequest()) != null) {
				perpage = Integer.parseInt((String) MyRequest.getAttribute("perpage", workFlowBean.getRequest()));
			}
			if (MyRequest.getParameter("fromId", parameterMap).equals("") || !MyRequest.getParameter("perpageChanged", parameterMap).equals("")) {
				int from = 0;
				try {
					from = Integer.parseInt(MyRequest.getParameter("toElement", parameterMap));
				} catch (NumberFormatException exc) {
					from = 0;
				}
				int to = queryResult.elements;
				try {
					to = Integer.parseInt(MyRequest.getParameter("fromElement", parameterMap));
				} catch (NumberFormatException exc) {
					to = queryResult.elements;
				}
				qRparser = new QRParser(queryResult, perpage, from, to);
				qRparser.setIdQR(queryResult.id);
				qRparser.setQrElements(queryResult.elements);
				// qRparser.setQueryResult(queryResult);
				if (queryResult.elements > 0) {
					qRparser.setPhysDoc(String.valueOf(xwConn.getNumDocFromQRElement(queryResult, 0)));
				} else {
					qRparser.setPhysDoc("");
				}
				// System.out.println("QUI CI ENTRO qRparser.getIdQR()!!!!!!!!" + qRparser.getIdQR());
			} else {
				qRparser = (QRParser) httpSession.getAttribute("QRParser");
				// mi serve per capire se ho selezionato un documento mentre pagino
				qRparser.setPhysDoc((String) httpSession.getAttribute("physDoc"));
			}

			if (!MyRequest.getParameter("pageToShow", parameterMap).equals("")) {
				pageToShow = Integer.parseInt(MyRequest.getParameter("pageToShow", parameterMap));
			}
			if (!MyRequest.getParameter("primo", parameterMap).equals("")) {
				currentPage = qRparser.getFirsPage();
				pageToShow = currentPage.getNumPage();
			} else if (!MyRequest.getParameter("ultimo", parameterMap).equals("")) {
				currentPage = qRparser.getLastPage();
				pageToShow = currentPage.getNumPage();
			} else if (!MyRequest.getParameter("before10", parameterMap).equals("")) {
				currentPage = qRparser.getBefore10Page(pageToShow);
				pageToShow = currentPage.getNumPage();
			} else if (!MyRequest.getParameter("before", parameterMap).equals("")) {
				currentPage = qRparser.getBeforePage(pageToShow);
				pageToShow = currentPage.getNumPage();
			} else if (!MyRequest.getParameter("next10", parameterMap).equals("")) {
				currentPage = qRparser.getNext10Page(pageToShow);
				pageToShow = currentPage.getNumPage();
			} else if (!MyRequest.getParameter("next", parameterMap).equals("")) {
				currentPage = qRparser.getNextPage(pageToShow);
				pageToShow = currentPage.getNumPage();
			} else {
				currentPage = qRparser.getPage(pageToShow);
			}
			titleBeans = currentPage.loadTitleBean(queryResult, xwConn);
			qRparser.setPerpage(perpage);
			httpSession.setAttribute("pageToShow", new Integer(pageToShow));
			httpSession.setAttribute("QRParser", qRparser);
			httpSession.setAttribute("QRPage", currentPage);
			// FINE PAGINAZIONE
		} catch (Exception e) {
			e.printStackTrace();
		}
		return titleBeans;
	}

}
