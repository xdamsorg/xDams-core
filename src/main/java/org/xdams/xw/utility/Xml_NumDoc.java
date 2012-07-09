package org.xdams.xw.utility;

public class Xml_NumDoc {

	String xml = "";

	int docNumber = -1;

	public Xml_NumDoc(String xml, int docNumber) {
		this.xml = xml;
		this.docNumber = docNumber;
	}

	public int getDocNumber() {
		return docNumber;
	}

	public String getXml() {
		return xml;
	}

	public void setDocNumber(int i) {
		docNumber = i;
	}

	public void setXml(String string) {
		xml = string;
	}

}
