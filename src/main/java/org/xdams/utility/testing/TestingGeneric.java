package org.xdams.utility.testing;

import javax.servlet.http.HttpSession;

import org.xdams.page.view.bean.EditingBean;
import org.xdams.page.view.bean.ViewBean;
import org.xdams.user.bean.UserBean;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;

public class TestingGeneric {

	// in questo caso utilizzo 4 parametri
	public boolean testXPath(Object obj, Object workFlowBean, String[] userLevel, String xPath) {
		if (obj instanceof ViewBean) {
			if (evalTestXPath(((ViewBean) obj).getXmlBuilder(), xPath) && controllaLivello(workFlowBean, userLevel)) {
				return true;
			}
		} else if (obj instanceof EditingBean) {
			if (evalTestXPath(((EditingBean) obj).getXmlBuilder(), xPath) && controllaLivello(workFlowBean, userLevel)) {
				return true;
			}
		}
		return false;
	}

	public boolean evalTestXPath(XMLBuilder builder, String xPath) {
		// try {
		// System.out.println("TestingGeneric.evalTestXPath() xPath "+xPath);
		// System.out.println("TestingGeneric.evalTestXPath() builder "+builder.getXML("ISO-8859-1"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		if (builder.contaNodi(xPath) > 0) {
			return true;
		}
		return false;
	}

	public boolean visualizzaTornaEsito(HttpSession httpSession) {
		boolean ritorno = false;
		Object QRParser = httpSession.getAttribute("QRParser");
		Object pageToShow = httpSession.getAttribute("pageToShow");
		Object QRPage = httpSession.getAttribute("QRPage");
		if ((QRParser != null && pageToShow != null && QRPage != null)) {
			ritorno = true;
		}

		return ritorno;
	}

	public boolean controllaLivelloFromBar(Object fix, Object workFlowBean, String[] userLevel) {
		boolean isOk = false;
		String archivioLivello = ((WorkFlowBean) workFlowBean).getArchive().getRole();
		for (int i = 0; i < userLevel.length; i++) {
			String userToView = userLevel[i];
			if (userToView.equals(archivioLivello) || userToView.toLowerCase().equals("all")) {
				isOk = true;
				break;
			}
		}
		return isOk;
	}

	public boolean controllaLivello(Object workFlowBean, String[] userLevel) {
		boolean isOk = false;
		String archivioLivello = ((WorkFlowBean) workFlowBean).getArchive().getRole();
		System.out.println("TestingGeneric.controllaLivello() archivioLivello " + archivioLivello);
		for (int i = 0; i < userLevel.length; i++) {
			String userToView = userLevel[i];
			if (userToView.equals(archivioLivello) || userToView.toLowerCase().equals("all")) {
				isOk = true;
				break;
			}
		}
		return isOk;
	}
}
