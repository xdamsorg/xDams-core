package org.xdams.utility.testing;

import javax.servlet.http.HttpSession;

import org.xdams.page.view.bean.ViewBean;

public class TestingViewBean extends TestingGeneric {

	public TestingViewBean() {

	} 

	public boolean visualizzaPredecente(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getDocUpperBrother() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaSuccessivo(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getDocLowerBrother() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaSuperiore(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getDocFather() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaInferiore(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getDocSon() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaStruttura(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getDocFather() > 0 || ((ViewBean) viewBean).getDocSon() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaAvanti(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getPosNext() > -1 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaIndietro(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((ViewBean) viewBean).getPosPrev() > -1 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaScheda(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = controllaLivello(workFlowBean, userLevel);

		return ritorno;
	}

	public boolean visualizzaXML(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = controllaLivello(workFlowBean, userLevel);
		return ritorno;
	}

	public boolean editingControl(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = controllaLivello(workFlowBean, userLevel);
		return ritorno;
	}

	public boolean visualizzaTornaEsito(Object viewBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		ViewBean bean = (ViewBean) viewBean;
		try {
			HttpSession httpSession = bean.getHttpServletRequest().getSession(false);
			Object QRParser = httpSession.getAttribute("QRParser");
			Object pageToShow = httpSession.getAttribute("pageToShow");
			Object QRPage = httpSession.getAttribute("QRPage");
			if ((QRParser != null && pageToShow != null && QRPage != null) && controllaLivello(workFlowBean, userLevel)) {
				ritorno = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return ritorno;
	}

	public boolean testXPath(Object viewBean, Object workFlowBean, String[] userLevel, String xPath) {
		return super.testXPath(viewBean, workFlowBean, userLevel, xPath);
	}

}
