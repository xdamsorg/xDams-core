package org.xdams.page.view.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.xdams.xml.builder.XMLBuilder;

public class ManagingBean implements Serializable{

	private ArrayList listPhysDoc = new ArrayList();

	private String multipleChoise = "";

	private String dispatchView = "";

	private int physDoc = -1;

	private int numElementiHier = -1;

	private int numElementiSons = -1;

	private int numElementi = -1;

	private String xmlInteraction = "";

	private String selid = "";

	private String pos = "";

	private String docXML = "";

	private XMLBuilder XMLBuilder = null;

	private int docNext = -1;

	private int docPrev = -1;

	private int docUpperBrother = 0;

	private int docLowerBrother = 0;

	private int docFather = 0;

	private int docFirstSon = 0;

	private int docSuccessi = -1;

	private int docErrori = -1;

	private ArrayList errorMsg = new ArrayList();

	private String title = "";

	private String exportXML = "";

	private int totDocumenti = -1;

	private int cutPhysDoc = -1;

	private String cutTitle = "";

	private String cutHtmlOutput = "";

	private boolean spreadMod = false;

	private ArrayList arrangeBean = new ArrayList();

	public String getCutTitle() {
		return cutTitle;
	}

	public void setCutTitle(String cutTitle) {
		this.cutTitle = cutTitle;
	}

	public ArrayList getListPhysDoc() {
		return listPhysDoc;
	}

	public void setListPhysDoc(ArrayList listPhysDoc) {
		this.listPhysDoc = listPhysDoc;
	}

	public void addListPhysDoc(String physDoc) {
		listPhysDoc.add(physDoc);
	}

	public void removePhysDoc(String physDoc) {
		for (int i = 0; i < listPhysDoc.size(); i++) {
			String physDocList = (String) listPhysDoc.get(i);
			if (physDoc.equals(physDocList)) {
				listPhysDoc.remove(i);
			}
		}
	}

	public boolean isChecked(String physDoc) throws Exception {
		boolean ritorno = false;

		try {
			for (int i = 0; i < listPhysDoc.size(); i++) {
				String physDocList = (String) listPhysDoc.get(i);
				// System.out.println("ManagingBean.isChecked()"+physDocList+"ManagingBean.isChecked()");
				if (physDoc.equals(physDocList)) {
					ritorno = true;
					break;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return ritorno;

	}

	public String getMultipleChoise() {
		return multipleChoise;
	}

	public void setMultipleChoise(String multipleChoise) {
		this.multipleChoise = multipleChoise;
	}

 	public int getNumElementi() {
		return numElementi;
	}

	public void setNumElementi(int numElementi) {
		this.numElementi = numElementi;
	}

	public int getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(int physDoc) {
		this.physDoc = physDoc;
	}

	public int getDocErrori() {
		return docErrori;
	}

	public void setDocErrori(int docErrori) {
		this.docErrori = docErrori;
	}

	public int getDocSuccessi() {
		return docSuccessi;
	}

	public void setDocSuccessi(int docSuccessi) {
		this.docSuccessi = docSuccessi;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumElementiHier() {
		return numElementiHier;
	}

	public void setNumElementiHier(int numElementiHier) {
		this.numElementiHier = numElementiHier;
	}

	public int getNumElementiSons() {
		return numElementiSons;
	}

	public void setNumElementiSons(int numElementiSons) {
		this.numElementiSons = numElementiSons;
	}

	public int getDocLowerBrother() {
		return docLowerBrother;
	}

	public void setDocLowerBrother(int docLowerBrother) {
		this.docLowerBrother = docLowerBrother;
	}

	public int getDocUpperBrother() {
		return docUpperBrother;
	}

	public void setDocUpperBrother(int docUpperBrother) {
		this.docUpperBrother = docUpperBrother;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getSelid() {
		return selid;
	}

	public void setSelid(String selid) {
		this.selid = selid;
	}

	public ArrayList getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(ArrayList errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void addErrorMsg(String errorMsgStr) {
		errorMsg.add(errorMsgStr);
	}

	public String getXmlInteraction() {
		return xmlInteraction;
	}

	public void setXmlInteraction(String xmlInteraction) {
		this.xmlInteraction = xmlInteraction;
	}

	public int getDocNext() {
		return docNext;
	}

	public void setDocNext(int docNext) {
		this.docNext = docNext;
	}

	public int getDocPrev() {
		return docPrev;
	}

	public void setDocPrev(int docPrev) {
		this.docPrev = docPrev;
	}

	public String getDocXML() {
		return docXML;
	}

	public void setDocXML(String docXML) {
		this.docXML = docXML;
	}

	public int getDocFather() {
		return docFather;
	}

	public void setDocFather(int docFather) {
		this.docFather = docFather;
	}

	public int getDocFirstSon() {
		return docFirstSon;
	}

	public void setDocFirstSon(int docFirstSon) {
		this.docFirstSon = docFirstSon;
	}

	public String getExportXML() {
		return exportXML;
	}

	public void setExportXML(String exportXML) {
		this.exportXML = exportXML;
	}

	public int getTotDocumenti() {
		return totDocumenti;
	}

	public void setTotDocumenti(int totDocumenti) {
		this.totDocumenti = totDocumenti;
	}

	public int getCutPhysDoc() {
		return cutPhysDoc;
	}

	public void setCutPhysDoc(int cutPhysDoc) {
		this.cutPhysDoc = cutPhysDoc;
	}

	public String getCutHtmlOutput(String physDoc) {
		return cutHtmlOutput.replaceAll("@@physDocToPaste@@", physDoc);
	}

	public void setCutHtmlOutput(String cutHtmlOutput) {
		this.cutHtmlOutput = cutHtmlOutput;
	}

	public XMLBuilder getXMLBuilder() {
		return XMLBuilder;
	}

	public void setXMLBuilder(XMLBuilder builder) {
		XMLBuilder = builder;
	}

	public ArrayList getArrangeBean() {
		return arrangeBean;
	}

	public void setArrangeBean(ArrayList arrangeBean) {
		this.arrangeBean = arrangeBean;
	}

	public boolean isSpreadMod() {
		return spreadMod;
	}

	public void setSpreadMod(boolean spreadMod) {
		this.spreadMod = spreadMod;
	}

	@Override
	public String toString() {
		return "ManagingBean [listPhysDoc=" + listPhysDoc + ", multipleChoise=" + multipleChoise + ", dispatchView=" + dispatchView + ", physDoc=" + physDoc + ", numElementiHier=" + numElementiHier + ", numElementiSons=" + numElementiSons + ", numElementi=" + numElementi + ", xmlInteraction="
				+ xmlInteraction + ", selid=" + selid + ", pos=" + pos + ", docXML=" + docXML + ", XMLBuilder=" + XMLBuilder + ", docNext=" + docNext + ", docPrev=" + docPrev + ", docUpperBrother=" + docUpperBrother + ", docLowerBrother=" + docLowerBrother + ", docFather=" + docFather
				+ ", docFirstSon=" + docFirstSon + ", docSuccessi=" + docSuccessi + ", docErrori=" + docErrori + ", errorMsg=" + errorMsg + ", title=" + title + ", exportXML=" + exportXML + ", totDocumenti=" + totDocumenti + ", cutPhysDoc=" + cutPhysDoc + ", cutTitle=" + cutTitle
				+ ", cutHtmlOutput=" + cutHtmlOutput + ", spreadMod=" + spreadMod + ", arrangeBean=" + arrangeBean + "]";
	}

	public String getDispatchView() {
		return dispatchView;
	}

	public void setDispatchView(String dispatchView) {
		this.dispatchView = dispatchView;
	}

}
