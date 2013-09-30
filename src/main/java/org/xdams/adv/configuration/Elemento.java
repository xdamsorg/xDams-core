package org.xdams.adv.configuration;

import java.util.ArrayList;

public class Elemento {
	private String id = "";
	
	private String text = "";

	private ArrayList elemento = null;

	private String deep = null;

	private String xPathMapping = null;

	public String getXPathMapping() {
		return xPathMapping;
	}

	public void setXPathMapping(String pathMapping) {
		xPathMapping = pathMapping;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private ArrayList opzione = null;

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

	public ArrayList getElemento() {
		return elemento;
	}

	public void setElemento(ArrayList elemento) {
		this.elemento = elemento;
	}

	public void addElemento(Object object) {
		if (elemento == null)
			elemento = new ArrayList();
		elemento.add(object);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
