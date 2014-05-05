package org.xdams.adv.configuration;

import java.util.ArrayList;

public class Param {

	private String deep = null;

	private String xPathMapping = null;

	private ArrayList elemento = null;

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
