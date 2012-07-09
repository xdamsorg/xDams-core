package org.xdams.utility.testing;

import org.xdams.page.view.bean.EditingBean;

public class TestingEditingBean extends TestingGeneric {

	public TestingEditingBean() {

	}

	public boolean visualizzaPredecente(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;

		if (((EditingBean) editingBean).getDocUpperBrother() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaSuccessivo(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((EditingBean) editingBean).getDocLowerBrother() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaSuperiore(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((EditingBean) editingBean).getDocFather() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaInferiore(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((EditingBean) editingBean).getDocSon() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaStruttura(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((EditingBean) editingBean).getDocFather() > 0 || ((EditingBean) editingBean).getDocSon() > 0 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaAvanti(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((EditingBean) editingBean).getPosNext() > -1 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean visualizzaIndietro(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = false;
		if (((EditingBean) editingBean).getPosPrev() > -1 && controllaLivello(workFlowBean, userLevel)) {
			ritorno = true;
		}
		return ritorno;
	}

	public boolean editingControl(Object editingBean, Object workFlowBean, String[] userLevel) {
		boolean ritorno = controllaLivello(workFlowBean, userLevel);

		return ritorno;
	}
	
	public boolean testXPath(Object editingBean, Object workFlowBean, String[] userLevel, String xPath) {
		return super.testXPath(editingBean, workFlowBean, userLevel, xPath);
	}
	
}
