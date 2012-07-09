package org.xdams.utility;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.xdams.page.view.bean.ManagingBean;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xw.XWConnection;


public class ExtractDocument {

	public static ArrayList extractDocument(HttpSession httpSession, XWConnection xwconn, ManagingBean managingBean, WorkFlowBean workFlowBean, String applyTo, String selid) {
		ArrayList elementiNum = new ArrayList();
		try {
			if (applyTo.equals("selected") || applyTo.equals("prevSibling") || applyTo.equals("nextSibling") || applyTo.equals("thisDocument")) {
				if (applyTo.equals("nextSibling")) {
					int theBrother = managingBean.getPhysDoc();
					while (theBrother > 0) {
						theBrother = xwconn.getNumDocNextBrother(theBrother);
						if (theBrother > 0) {
							elementiNum.add(new Integer(theBrother));
						}
					}
				} else if (applyTo.equals("prevSibling")) {
					int theBrother = managingBean.getPhysDoc();
					while (theBrother > 0) {
						theBrother = xwconn.getNumDocPreviousBrother(theBrother);
						if (theBrother > 0) {
							elementiNum.add(new Integer(theBrother));
						}
					}
				} else if (applyTo.equals("thisDocument")) {
					elementiNum.add(managingBean.getPhysDoc());
				} else {// DA SELEZIONE MULTIPLA
					elementiNum = ((ManagingBean) httpSession.getAttribute(workFlowBean.getManagingBeanName())).getListPhysDoc();
				}

			} else {
				// System.out.println("MultiModCommand.execute()3333333333333333333");

				it.highwaytech.db.QueryResult qr = null;
				if (applyTo.equals("selid")) {
					qr = xwconn.getQRFromSelId(selid);
				} else if (applyTo.equals("sons")) {
					qr = xwconn.getSonsFromNumDoc(managingBean.getPhysDoc());
				} else if (applyTo.equals("hier")) {
					qr = xwconn.getQRFromHier(managingBean.getPhysDoc(), false);
				}
				// System.out.println("MultiModCommand.execute()44444444444444444444444444+qr" + qr);

				for (int z = 0; z < qr.elements; z++) {
					int theNumber = xwconn.getNumDocFromQRElement(qr, z);
					elementiNum.add(new Integer(theNumber));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return elementiNum;
	}

}
