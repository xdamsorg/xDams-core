package org.xdams.adv.configuration;

import java.util.ArrayList;

public class Opzione {
	private String action = "";

	private String value = "";

	private String text = "";

	private String deep = null;

	private String xPathMapping = null;

	private ArrayList opzione = null;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList getOpzione() {
		return opzione;
	}

	public void setOpzione(ArrayList opzione) {
		this.opzione = opzione;
	}

	public void addOpzione(Object object) {
		if (opzione == null)
			opzione = new ArrayList();
		opzione.add(object);
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public String getXPathMapping() {
		return xPathMapping;
	}

	public void setXPathMapping(String pathMapping) {
		xPathMapping = pathMapping;
	}

}
