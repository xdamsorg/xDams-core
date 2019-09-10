package org.xdams.page.form.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xdams.xml.builder.XMLBuilder;

public class CustomPageBean {
	
	private CommonsMultipartFile filedata;
	
	private List<CommonsMultipartFile> multipleFiledata;
	
	String physDoc = "";

	String selid = "";

	String totSelid = "";

	String pos = "";

	String pageName = "";

	List<String> confControl = new ArrayList<String>();
	
	XMLBuilder xmlBuilder = null;

	public String getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(String physDoc) {
		this.physDoc = physDoc;
	}

	public String getSelid() {
		return selid;
	}

	public void setSelid(String selid) {
		this.selid = selid;
	}

	public String getTotSelid() {
		return totSelid;
	}

	public void setTotSelid(String totSelid) {
		this.totSelid = totSelid;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public List<String> getConfControl() {
		return confControl;
	}

	public void setConfControl(List<String> confControl) {
		this.confControl = confControl;
	}

	@Override
	public String toString() {
		return "CustomPageBean [physDoc=" + physDoc + ", selid=" + selid + ", totSelid=" + totSelid + ", pos=" + pos + ", pageName=" + pageName + ", confControl=" + confControl + "]";
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public XMLBuilder getXmlBuilder() {
		return xmlBuilder;
	}

	public void setXmlBuilder(XMLBuilder xmlBuilder) {
		this.xmlBuilder = xmlBuilder;
	}

	public CommonsMultipartFile getFiledata() {
		return filedata;
	}

	public void setFiledata(CommonsMultipartFile filedata) {
		this.filedata = filedata;
	}

	public List<CommonsMultipartFile> getMultipleFiledata() {
		return multipleFiledata;
	}

	public void setMultipleFiledata(List<CommonsMultipartFile> multipleFiledata) {
		this.multipleFiledata = multipleFiledata;
	}
 
}
