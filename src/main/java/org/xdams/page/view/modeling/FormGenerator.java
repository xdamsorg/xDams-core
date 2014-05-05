/*
 * Created on 30-mag-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xdams.page.view.modeling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.view.bean.EditingBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.AdvancedUtility;
import org.xdams.utility.resource.ConfManager;
import org.xdams.utility.xw.query.NewPreparedXwQuery;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xml.sax.SAXException;

public class FormGenerator {

	String elementoPath = "";

	String[] elementoPathGroup = null;

	XMLBuilder theXMLconf = null; // documento XML contente la configurazione

	XMLBuilder theXML = null; // documento XML contente i dati

	String macroArea, sezione, elemento = "";

	int physDoc = -1;

	String imagePath = "";

	String part01, part02, part03, rigaSepara, partAuther01, partAuther02, partAuther03, partAuther04, rigaSeparaAuther = "";

	String part01multi, part02multi, part00multi, part01multiGroup, part02multiGroup, part03multiGroup = "";

	String newXPath = "";

	WorkFlowBean workFlowBean = null;

	String contextPath;

	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
	}

	public FormGenerator(XMLBuilder theXML, XMLBuilder theXMLconf, WorkFlowBean workFlowBean, String contextPath) {
		this.theXML = theXML;
		this.theXMLconf = theXMLconf;
		this.workFlowBean = workFlowBean;
		this.contextPath = contextPath;
	}

	public FormGenerator(XMLBuilder theXMLconf, WorkFlowBean workFlowBean, String contextPath) {
		this.theXMLconf = theXMLconf;
		this.workFlowBean = workFlowBean;
		this.contextPath = contextPath;
	}

	public void setValues(String elemento, String sezione, String macroarea) {
		this.elemento = elemento;
		this.sezione = sezione;
		this.macroArea = macroarea;
	}

	public void setValues(String nomeVariabile, String valoreVariabile) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		this.getClass().getDeclaredField(nomeVariabile).set(this, valoreVariabile);
	}

	public void generateSection(javax.servlet.jsp.JspWriter out, String customType) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		if (customType.equals("daogrp")) {
			String nomeSezione = theXMLconf.valoreNodo(macroArea + sezione + "/@name");
			String idRef = theXMLconf.valoreNodo(macroArea + sezione + "/@externalPath");
			String idSezione = (nomeSezione.replaceAll(" ", "-")).replaceAll("'", "_");
			String prefix = theXMLconf.valoreNodo(macroArea + sezione + "/@prefix");
			String mode = theXMLconf.valoreNodo(macroArea + sezione + "/@mode");
			String copyFlag = theXMLconf.valoreNodo(macroArea + sezione + "/@copyFlag");
			String spreadMod = theXMLconf.valoreNodo(macroArea + sezione + "/@spreadMod");
			String removeAttach = "";
			// String originalFileName = theXMLconf.valoreNodo(macroArea +
			// sezione + "/@originalFileName");
			String originalFileNameXpath = StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo(macroArea + sezione + "/@originalFileNameXpath"));
			String prefixDot = prefix.replace('/', '.');
			String xpathCorrenteSpan = ((((prefix.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
			if (xpathCorrenteSpan.startsWith("."))
				xpathCorrenteSpan = xpathCorrenteSpan.substring(1);
			out.print("<span id=\"sezione_head_" + idSezione + "\">"); // inizio
			// sezione
			out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" ><tr class=\"dcTSS\"><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"dcTSSD\" height=\"12\" width=\"12\"><a class=\"doceditActionLink\" id=\"sezione_link_" + idSezione
					+ "\" onclick=\"openSection('sezione_" + idSezione + "',this)\" href=\"#n\">+</a></td><td class=\"dcTSSD\">" + nomeSezione + "</td>");
			out.println("<td class=\"dcTSSD\" width=\"50\" align=\"right\">");
			/* TODO: gestire più gruppi */
			/* out.println(generateAddControl(xpathCorrenteSpan, false)); */
			// out.println(generateRemoveControl(xpathCorrenteSpan + ".0"));
			out.println("</td>");
			out.print("</tr></table></span>");
			out.print("<span id=\"sezione_" + idSezione + "\" style=\"display:none\">");
			boolean testSezione = false;
			boolean testSezioneOpen = false;
			if (!theXMLconf.valoreNodo(macroArea + sezione + "/@disable").equals("yes")) {
				testSezione = true;
				int nodiElemento = theXML.contaNodi(prefix);
				if (nodiElemento == 0)
					nodiElemento++;
				if (theXMLconf.valoreNodo(macroArea + sezione + "/@opened").equals("yes")) {
					testSezioneOpen = true;
				}
				out.print("<span>");
				out.print("<span id=\"" + xpathCorrenteSpan + ".0\"></span>");
				for (int x = 0; x < nodiElemento; x++) {
					String daoCorrente = prefix + "[" + (x + 1) + "]";
					out.print("<span id=\"" + xpathCorrenteSpan + "." + (x + 1) + "\">");
					out.print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  ><tr><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td  class=\"showdocTitoloElemento\" valign=\"middle\" colspan=\"2\" style=\"padding-left:5px\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  ><tr><td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">denominazione del gruppo ");
					generaLiveText(prefix + "[" + (x + 1) + "]/@id", prefixDot + "[" + (x + 1) + "].@id", "", out);
					// title=\"" + prefix + "[" + (x + 1) +
					// "]/daodesc/p/text()\"
					out.print("</td><td class=\"showdocValue\" style=\"border-bottom: 0px none;\"><input name=\"" + prefixDot + "[" + (x + 1) + "].daodesc.p.text()\"  type=\"text\" class=\"docEditInputExtraLong\"  value=\"" + theXML.valoreNodo(prefix + "[" + (x + 1) + "]/daodesc/p/text()")
							+ "\" />");
					out.println("&#160;&#160;&#160;" + generateRemoveControl(xpathCorrenteSpan + "." + (x + 1), copyFlag, removeAttach, spreadMod) + "</td>");
					out.print("</tr><tr><td class=\"showdocValue\" colspan=\"2\"><span>"); // inizio
					// sezione
					out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" ><tr class=\"dcTSS\"><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"dcTSSSD\" height=\"12\">&#160;&#160;&#160;</td>");
					out.println("<td class=\"dcTSSSD\" width=\"50\" align=\"right\">" + generateDaoGrpAddControl(xpathCorrenteSpan + "[" + x + "].daoloc", 1, prefix) + "</td></tr></table></span><span>");
					out.print("<span id=\"" + xpathCorrenteSpan + "[" + x + "].daoloc.0\"></span>");
					int nodiSottoElemento = 0;
					nodiSottoElemento = theXML.contaNodi(daoCorrente + "/daoloc");
					for (int i = 0; i < nodiSottoElemento + 1; i++) {
						String originalFileNameValue = "";
						out.print("<span id=\"" + xpathCorrenteSpan + "[" + x + "].daoloc." + (i + 1) + "\">");
						out.print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
						out.print("<tr><td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td>");
						out.print("<td  class=\"showdocTitoloElemento\" valign=\"middle\" colspan=\"2\" style=\"padding-left:5px\">");
						out.print("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
						if (!(originalFileNameXpath.trim()).equals("")) {
							out.print("<tr>");
							// title=\"" + prefix + "[" + (x + 1) + "]/daoloc["
							// + (i + 1) + "]/@label\"
							out.print("<td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">nome allegato originale</td>");
							out.print("<td class=\"showdocValue\" style=\"border-bottom: 0px none;\"><input name=\"" + prefixDot + "[" + (x + 1) + "].daoloc[" + (i + 1) + "].@label\" type=\"text\" class=\"docEditInputExtraLong\" value=\""
									+ theXML.valoreNodo(prefix + "[" + (x + 1) + "]/daoloc[" + (i + 1) + "]/@label") + "\" /></td>");
							out.print("</tr><tr>");
							originalFileNameValue = prefixDot + "[" + (x + 1) + "].daoloc[" + (i + 1) + "].@label";
						} else {
							out.print("<tr>");
						}

						out.print("<td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">denominazione elemento</td>");
						// title=\"" + prefix + "[" + (x + 1) + "]/daoloc[" + (i
						// + 1) + "]/@title\"
						out.print("<td class=\"showdocValue\" style=\"border-bottom: 0px none;\"><input name=\"" + prefixDot + "[" + (x + 1) + "].daoloc[" + (i + 1) + "].@title\" type=\"text\" class=\"docEditInputExtraLong\" value=\""
								+ theXML.valoreNodo(prefix + "[" + (x + 1) + "]/daoloc[" + (i + 1) + "]/@title") + "\" /></td>");
						out.print("</tr><tr>");
						out.print("<td class=\"showdocValue\" width=\"30%\" style=\"border-bottom: 0px none;padding-left:5px\">percorso</td>");
						// String idRef = prefixDot+ "["+ (x + 1)+ "].daoloc["+
						// (i + 1)+ "].@href";//title=\"" + prefix + "[" + (x +
						// 1) + "]/daoloc[" + (i + 1) + "]/@href\"
						out.print("<td class=\"showdocValue\" style=\"border-bottom: 0px none;\"><input name=\"" + prefixDot + "[" + (x + 1) + "].daoloc[" + (i + 1) + "].@href\" type=\"text\" class=\"docEditInputExtraLong\"  value=\""
								+ theXML.valoreNodo(prefix + "[" + (x + 1) + "]/daoloc[" + (i + 1) + "]/@href") + "\" /> ");
						/*
						 * if (!(originalFileNameXpath.trim()).equals("")) { out.println( "<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return allega(document.theForm.theDb.value,'" + theXML.valoreNodo(idRef) + "',this,'multi','" + xpathCorrenteSpan + "[" + x + "].daoloc','" + mode + "','" +
						 * StringEscapeUtils.escapeJavaScript(originalFileNameValue ) +"','"+getPhysDoc()+ "')\">allega originalFileName</a>&#160;&#160;"); } else {out.println( "<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return allega(document.theForm.theDb.value,'" +
						 * theXML.valoreNodo(idRef) + "',this,'multi','" + xpathCorrenteSpan + "[" + x + "].daoloc','" + mode + "')\">allega</a>&#160;&#160;"); }
						 */
						out.println("<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return allega('" + theXML.valoreNodo(idRef) + "',this,'" + xpathCorrenteSpan + "','" + mode + "','" + originalFileNameXpath + "','"
								+ StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo(macroArea + sezione + "/@prefix")) + "','" + getPhysDoc() + "')\">allega dao</a>");

						out.print(generateRemoveControl(xpathCorrenteSpan + "[" + x + "].daoloc." + (i + 1), copyFlag, removeAttach, spreadMod));
						// out.println("&#160;&#160;&#160;<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return inserisci(this,'"
						// + xpathCorrenteSpan + "[" + x + "].daoloc." + (i + 1)
						// + "')\">inserisci</a>");
						// out.print("&#160;&#160;<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return visualizzaImg(this,'"
						// + imagePath + "','" + mode +
						// "')\">visualizza</a></td></tr></table></td></tr></table></span>");
						out.print("</td></tr></table></td></tr></table></span>");
					}
					out.print("</span></td></tr></table></td>");
					out.println("<td width=\"50\" align=\"right\">");
					/* TODO: gestire più gruppi */
					/*
					 * out.println(generateRemoveControl(xpathCorrenteSpan + "." + String.valueOf(x) + ""));
					 */
					out.println("</td>");
					out.print("</tr></table></span>");
				}
				out.print("</span>");
			}
			if (!testSezione) {
				out.print("<script type=\"text/javascript\">hideSection('" + idSezione + "')</script>");
			} else if (testSezioneOpen) {
				out.print("<script type=\"text/javascript\">showSection('" + idSezione + "')</script>");
			}
			out.print("</span>");
		}
	}

	public void generateSection(javax.servlet.jsp.JspWriter out) throws Exception {
		String theClass, inputType, theName, theType = "";
		theClass = "docEditInput" + theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@class");
		inputType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@input_type");
		theName = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@name");

		theType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@type");
		if (theType.equals("")) { // elemento SINGOLO
			String xpathCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()");
			String theHTMLextra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@HTMLextra", false);
			String xpathCorrenteDot = xpathCorrente.replace('/', '.');
			String theExtra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extra", false);
			if (inputType.equals("textarea") && theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@cdata").equals("true")) {
				xpathCorrenteDot += ".@cdata";
			}
			String theVocKey = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ajaxVoc", false);
			String theVocType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@vocType", false);
			String theVocFilterQuery = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@filterQuery", false);

			boolean testSezioneOpen = false;
			boolean testSezioneOpenable = false;
			if (!(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@opened")).equals("")) {
				testSezioneOpenable = true;
				if (!(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@opened")).equals("no"))
					testSezioneOpen = true;
			}
			if (!testSezioneOpenable) {
				out.println(part01);
				out.println(theName);
				if (inputType.equals("text")) { // input tipo TEXT
					out.println(part02);
					generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
				} else if (inputType.equals("textarea")) { // input tipo
					// TEXTAREA
					if (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@rich").equals("yes")) {
						out.println(generateRichTextAreaControl(xpathCorrenteDot, "14"));
						out.println(part02);
						generaRichTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					} else if (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@rich").equals("basicInPage")) {
						out.println(part02);
						generaRichTextAreaBasicInPage(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					} else if (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@rich").equals("flash")) {
						out.println(part02);
						generaFlashTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					} else {
						out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
						out.println(part02);
						generaTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					}
				} else if (inputType.equals("select") || inputType.equals("radio")) { // input
					// tipo
					// SELECT
					// o
					// RADIO
					out.println(part02);
					generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, macroArea + sezione + elemento, out, theExtra, theHTMLextra);
				} else if (inputType.equals("liveText")) { // input tipo
					// LIVETEXT + HIDDEN
					out.println(part02);
					generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
				}
			} else {
				String xpathCorrenteSpan = ((((xpathCorrente.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
				out.println(part00multi);
				out.println("<span id=\"sezione_head_" + xpathCorrenteSpan + "\">");
				out.println("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  ><tr class=\"dcTSSSD\">");
				out.println("<td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td>");
				out.println("<td class=\"dcTSSSD\" height=\"12\" width=\"12\"><a class=\"doceditActionLink\" id=\"sezione_link_" + xpathCorrenteSpan + "\" onclick=\"openSection('sezione_" + xpathCorrenteSpan + "',this)\" href=\"#n\">+</a>");
				out.println("</td><td class=\"dcTSSD\">" + theName + "</td>");
				out.println("</tr>");
				out.println("</table></span>");
				out.println("<span id=\"sezione_" + xpathCorrenteSpan + "\"  style=\"display:none\">");
				out.println("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  >");
				out.println(part01multi);
				if (inputType.equals("text")) { // input tipo TEXT
					out.println(part02multi);
					generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
				} else if (inputType.equals("textarea")) { // input tipo
					// TEXTAREA
					/*
					 * out.println(generateTextAreaControl(xpathCorrenteDot, "14")); out.println(part02multi); generaTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					 */

					if (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@rich").equals("yes")) {
						out.println(generateRichTextAreaControl(xpathCorrenteDot, "14"));
						out.println(part02);
						generaRichTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					} else if (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@rich").equals("basicInPage")) {
						out.println(part02);
						generaRichTextAreaBasicInPage(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					} else if (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@rich").equals("flash")) {
						out.println(part02);
						generaFlashTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					} else {
						out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
						out.println(part02);
						generaTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
					}

				} else if (inputType.equals("select") || inputType.equals("radio")) { // input
					// tipo
					// SELECT
					// o
					// RADIO
					out.println(part02multi);
					generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, macroArea + sezione + elemento, out, theExtra, theHTMLextra);
				} else if (inputType.equals("liveText")) { // input tipo
					// LIVETEXT + HIDDEN
					out.println(part02multi);
					generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
				}
				if (testSezioneOpen) {
					out.println("<script type=\"text/javascript\">showSection('" + xpathCorrenteSpan + "')</script>");
					testSezioneOpen = false;
				}
				out.println("</td></tr></table></span>");
			}
			out.println("</td></tr>");
		} else {
			if (theType.equals("multi_group")) {
				String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
				String theArrange = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arrangeXpath", false);
				String copyFlag = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@copyFlag", false);
				String spreadMod = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@spreadMod", false);
				String xpathCorrenteSpan = ((((prefix.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
				String removeAttach = "";
				if (xpathCorrenteSpan.startsWith("."))
					xpathCorrenteSpan = xpathCorrenteSpan.substring(1);
				int contatore = theXML.contaNodi(prefix);
				boolean inLine = false;
				boolean testSezioneOpen = false;
				if (!(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@opened")).equals("no")) {
					testSezioneOpen = true;
				}
				if ((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@inline")).equals("yes"))
					inLine = true;
				out.println(part00multi);
				for (int p = 0; p < contatore + 2; p++) {
					if (p > 0) {
						out.println("<span><span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
						out.print(part01multiGroup);
						out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
						for (int a = 0; a < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento"); a++) {
							String nodoCorrente = macroArea + sezione + elemento + "/elemento[" + (a + 1) + "]";
							String xpathCorrente = theXMLconf.valoreNodo(nodoCorrente + "/text()");
							String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
							String theExtra = theXMLconf.valoreNodo(nodoCorrente + "/@extra", false);

							String theVocKey = theXMLconf.valoreNodo(nodoCorrente + "/@ajaxVoc", false);
							String theVocType = theXMLconf.valoreNodo(nodoCorrente + "/@vocType", false);
							String theVocFilterQuery = theXMLconf.valoreNodo(nodoCorrente + "/@filterQuery", false);

							xpathCorrente = prefix + "[" + (p) + "]" + xpathCorrente.substring(xpathCorrente.indexOf(prefix) + prefix.length());
							String xpathCorrenteDot = xpathCorrente.replace('/', '.');
							inputType = theXMLconf.valoreNodo(nodoCorrente + "/@input_type");
							theClass = "docEditInput" + theXMLconf.valoreNodo(nodoCorrente + "/@class");
							theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
							if (theName.indexOf("[#") > 0 && theName.indexOf("#]") > 0) {
								theName = translateName(theName, p + 1);
							}

							theType = theXMLconf.valoreNodo(nodoCorrente + "/@type");
							// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"00"+theXMLconf.valoreNodo(nodoCorrente
							// +
							// "/@name")+" "+theXMLconf.valoreNodo(nodoCorrente
							// + "/@rich")+" "+xpathCorrente);
							if (!inLine) {
								// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + "01");
								// if (!theType.equals("auther")) {
								out.println(part02multiGroup);
								out.println(theName); /* VERIFICARE */
								// }
								if (inputType.equals("textarea")) {
									// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"01");
									if (theXMLconf.valoreNodo(nodoCorrente + "/@rich").equals("yes")) {
										// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"02");
										out.println(generateRichTextAreaControl(xpathCorrenteDot, "14"));
									} else {
										// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"03");
										out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
									}
								}
								out.println(part03multiGroup);
							} else {
								if (inLine && a == 0) {
									// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"04");

									out.println(part02multiGroup);
									out.println(theName);

									if (inputType.equals("textarea")) {
										// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"05");
										if (theXMLconf.valoreNodo(nodoCorrente + "/@rich").equals("yes")) {
											out.println(generateRichTextAreaControl(xpathCorrenteDot, "14"));
											// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"06");
										} else {
											out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
											// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+"07");
										}
									}
									out.println(part03multiGroup);
								} else {
									if (!theName.equals("")) {
										// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa 08");
										out.println("</td></tr>");
										out.println(part02multiGroup);
										out.println(theName);
										if (inputType.equals("textarea")) {
											if (theXMLconf.valoreNodo(nodoCorrente + "/@rich").equals("yes")) {
												out.println(generateRichTextAreaControl(xpathCorrenteDot, "14"));
											} else {
												out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
											}
										}
										out.println(part03multiGroup);
									}
								}
							}
							if (theType.equals("custom")) { // CAMPI DI TIPO
								// CUSTOM
								generateCustomElementMulti(theXMLconf.valoreNodo(nodoCorrente + "/@id"), nodoCorrente, out, xpathCorrente);
								if (theXMLconf.valoreNodo(nodoCorrente + "/@id").equals("upload")) {
									removeAttach = theXMLconf.valoreNodo(nodoCorrente + "/@removeAttach");
								}
							} else if (theType.equals("auther")) { // CAMPO AUTHER
								String codeCorrente = theXMLconf.valoreNodo(nodoCorrente + "/@code");
								if (codeCorrente.indexOf("@") == -1) {
									codeCorrente = "@" + codeCorrente;
								}
								String archRefCorrente = theXMLconf.valoreNodo(nodoCorrente + "/@arch_ref");

								if (theExtra.equals(""))
									theExtra = "onchange=\"return invalidaElementoAuther(this)\"";

								String prefixAuther = theXMLconf.valoreNodo(nodoCorrente + "/@prefix");
								String showcode = theXMLconf.valoreNodo(nodoCorrente + "/@showcode");
								String campoCodiceDot = (prefix + "[" + (p) + "]").replace('/', '.') + "." + codeCorrente;
								String campoCodice = prefix + "[" + (p) + "]" + "/" + codeCorrente;
								// out.println(part01);

								if ((theXML.valoreNodo(campoCodice, "")).equals("") && !(theXML.valoreNodo(xpathCorrente, "")).equals(""))
									theExtra += " style=\"background-color: #FF886A\" ";
								// out.println(theName + "&#160;");
								if (inputType.equals("text")) { // input tipo TEXT
									// out.println(part02);
									generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
								} else if (inputType.equals("textarea")) { // input
									// tipo
									// TEXTAREA
									out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
									// out.println(part02);
									generaTextArea(xpathCorrente, xpathCorrenteDot, nodoCorrente, theExtra, theHTMLextra, out);
								} else if (inputType.equals("select") || inputType.equals("radio")) { // input
									// tipo
									// SELECT
									// o
									// RADIO
									// out.println(part02);
									generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, nodoCorrente, out, "", theHTMLextra);
								} else if (inputType.equals("liveText")) { // input
									// tipo
									// LIVETEXT
									// + HIDDEN
									// out.println(part02);
									generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
								}

								if (showcode.equals("yes"))
									out.println("<br />&#160;code&#160;" + generateInput("text", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "docEditInputMiddle", " readonly ", theHTMLextra));
								else
									out.println(generateInput("hidden", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "", "", theHTMLextra));
								// GESTISCO ELEMENTI TIPO "ROLE"

								out.println(generateLookupControl(archRefCorrente, theXMLconf.valoreNodo(nodoCorrente + "/@web_app"), theXMLconf.valoreNodo(nodoCorrente + "/@extraQuery"), theXMLconf.valoreNodo(nodoCorrente + "/@sort"), theXMLconf.valoreNodo(nodoCorrente + "/@title_rule"),
										theXMLconf.valoreNodo(nodoCorrente + "/@ud_type"), theXMLconf.valoreNodo(nodoCorrente + "/@query"), xpathCorrenteDot.replaceAll("'", "\\\\'"), "\\\'" + xpathCorrenteDot.replaceAll("'", "\\\\'") + "\\\',\\\'" + campoCodiceDot.replaceAll("'", "\\\\'") + "\\\'",
										theXMLconf.valoreNodo(nodoCorrente + "/@title_format"), theXMLconf.valoreNodo(nodoCorrente + "/@flagXML"), theXMLconf.valoreNodo(nodoCorrente + "/@jspOutPut")));

							} else {
								if (inputType.equals("text")) { // input tipo
									// TEXT
									generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
								} else if (inputType.equals("textarea")) { // input
									// tipo
									// TEXTAREA
									if (theXMLconf.valoreNodo(nodoCorrente + "/@rich").equals("yes")) {
										generaRichTextArea(xpathCorrente, xpathCorrenteDot, nodoCorrente, theExtra, theHTMLextra, out);
									} else {
										generaTextArea(xpathCorrente, xpathCorrenteDot, nodoCorrente, theExtra, theHTMLextra, out);
									}
								} else if (inputType.equals("select") || inputType.equals("radio")) { // input
									// tipo
									// SELECT
									// o
									// RADIO
									generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, nodoCorrente, out, theExtra, theHTMLextra);
								} else if (inputType.equals("liveText")) { // input
									// tipo
									// LIVETEXT
									// +
									// HIDDEN
									generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
								}

							}
							if (!inLine) {
								out.println("</td></tr>");
							}
						}
						if (inLine) {
							out.println("</td></tr>");
						}
						out.println("</table>");
					}
					if (p == 0) {
						out.println("<span>");
						out.println("<span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><span id=\"sezione_head_" + xpathCorrenteSpan + "\">");
						out.println("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  ><tr class=\"dcTSSSD\">");
						out.println("<td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td>");
						out.println("<td class=\"dcTSSSD\" height=\"12\" width=\"12\"><a class=\"doceditActionLink\" id=\"sezione_link_" + xpathCorrenteSpan + "\" onclick=\"openSection('sezione_" + xpathCorrenteSpan + "',this)\" href=\"#n\">+</a>");
						out.println("</td><td class=\"dcTSSD\">" + theName + "</td>");
						out.println("<td class=\"dcTSSSD\" width=\"50\" align=\"right\">" + generateAddControl(xpathCorrenteSpan, theArrange, prefix) + "</td>");
						out.println("</tr>");
						out.println("</table></span></span></span>");
						out.println("<span id=\"sezione_" + xpathCorrenteSpan + "\"  style=\"display:none\">");
					} else {
						out.println("</td>");
						out.println("<td class=\"showdocValue\" width=\"50\" align=\"right\">");
						out.println(generateRemoveControl(xpathCorrenteSpan + "[" + String.valueOf(p) + "]", copyFlag, removeAttach, spreadMod) + "</td>");
						out.println("</tr></table><hr/></span></span>");
					}
					if (p == (contatore + 1)) {
						out.println("</span>");
						if (testSezioneOpen) {
							out.println("<script type=\"text/javascript\">showSection('" + xpathCorrenteSpan + "')</script>");
							testSezioneOpen = false;
						}
					}
				}
				out.println("</td></tr>");
			} else if (theType.equals("multi")) { // CAMPO MULTIPLO SEMPLICE
				out.println(part00multi);
				String xpathCorrenteSpan = (((((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix")).replace('/', '.')).replace('\'', '_')).replaceAll("]", "-")).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
				String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
				String theHTMLextra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@HTMLextra", false);
				String theExtra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extra", false);

				String theVocKey = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ajaxVoc", false);
				String theVocType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@vocType", false);
				String theVocFilterQuery = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@filterQuery", false);
				String copyFlag = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@copyFlag", false);
				String spreadMod = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@spreadMod", false);
				String removeAttach = "";
				String theArrange = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arrangeXpath", false);
				int contatore = theXML.contaNodi(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix"));
				boolean testSezioneOpen = false;
				if (!(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@opened")).equals("no")) {
					testSezioneOpen = true;
				}

				for (int p = 0; p < contatore + 2; p++) {
					String xpathCorrente = prefix + "[" + (p) + "]" + (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).substring((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).indexOf(prefix) + prefix.length());
					String xpathCorrenteDot = xpathCorrente.replace('/', '.');

					if (p == 0) {
					} else {
						out.println("<span>");
						out.println("<span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
						out.println(part01multi);
						if (!testSezioneOpen && theXML.testaNodo(xpathCorrente)) {
							testSezioneOpen = true;
						}
						if (inputType.equals("text")) { // input tipo TEXT
							out.println(part02multi);
							generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
						} else if (inputType.equals("textarea")) { // input
							// tipo
							// TEXTAREA
							out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
							out.println(part02multi);
							generaTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
						} else if (inputType.equals("select") || inputType.equals("radio")) { // input
							// tipo
							// SELECT
							// o
							// RADIO
							out.println(part02multi);
							generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, macroArea + sezione + elemento, out, "", theHTMLextra);
						} else if (inputType.equals("liveText")) { // input
							// tipo
							// LIVETEXT
							// +
							// HIDDEN
							out.println(part02multi);
							generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
						}
						out.println("</td>");
					}
					if (p == 0) {
						out.println("<span>");
						out.println("<span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><span id=\"sezione_head_" + xpathCorrenteSpan + "\">");
						out.println("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  ><tr class=\"dcTSSSD\">");
						out.println("<td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td>");
						out.println("<td class=\"dcTSSSD\" height=\"12\" width=\"12\"><a class=\"doceditActionLink\" id=\"sezione_link_" + xpathCorrenteSpan + "\" onclick=\"openSection('sezione_" + xpathCorrenteSpan + "',this)\" href=\"#n\">+</a>");
						out.println("</td><td class=\"dcTSSD\">" + theName + "</td>");
						out.println("<td class=\"dcTSSSD\" width=\"50\" align=\"right\">" + generateAddControl(xpathCorrenteSpan, theArrange, prefix) + "</td>");
						out.println("</tr>");
						out.println("</table></span></span></span>");
						out.println("<span id=\"sezione_" + xpathCorrenteSpan + "\"  style=\"display:none\">");
					} else {
						out.println("<td class=\"showdocValue\" width=\"50\" align=\"right\">");
						out.println(generateRemoveControl(xpathCorrenteSpan + "[" + String.valueOf(p) + "]", copyFlag, removeAttach, spreadMod) + "</td>");
						out.println("</tr>");
						out.println(" </table></span></span>");
					}
					if (p == (contatore + 1)) {
						out.println("</span>");
						if (testSezioneOpen) {
							out.println("<script type=\"text/javascript\">showSection('" + xpathCorrenteSpan + "')</script>");
							testSezioneOpen = false;
						}
					}
				}
				out.println("</td></tr>");
			} else if (theType.equals("auther")) { // CAMPO AUTHER
				String xpathCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()");
				String xpathCorrenteDot = xpathCorrente.replace('/', '.');
				String codeCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@code");
				if (codeCorrente.indexOf("@") == -1) {
					codeCorrente = "@" + codeCorrente;
				}
				String archRefCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arch_ref");
				String theHTMLextra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@HTMLextra", false);
				String theExtra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extra", false);

				String theVocKey = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ajaxVoc", false);
				String theVocType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@vocType", false);
				String theVocFilterQuery = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@filterQuery", false);

				if (theExtra.equals(""))
					theExtra = "onchange=\"return invalidaElementoAuther(this)\"";
				String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
				String showcode = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@showcode");
				String campoCodiceDot = prefix.replace('/', '.') + "." + codeCorrente;
				String campoCodice = prefix + "/" + codeCorrente;
				out.println(part01);

				if ((theXML.valoreNodo(campoCodice, "")).equals("") && !(theXML.valoreNodo(xpathCorrente, "")).equals(""))
					theExtra += " style=\"background-color: #FF886A\" ";
				out.println(theName + "&#160;");
				if (inputType.equals("text")) { // input tipo TEXT
					out.println(part02);
					generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
				} else if (inputType.equals("textarea")) { // input
					// tipo
					// TEXTAREA
					out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
					out.println(part02);
					generaTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
				} else if (inputType.equals("select") || inputType.equals("radio")) { // input
					// tipo
					// SELECT
					// o
					// RADIO
					out.println(part02);
					generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, macroArea + sezione + elemento, out, "", theHTMLextra);
				} else if (inputType.equals("liveText")) { // input
					// tipo
					// LIVETEXT
					// + HIDDEN
					out.println(part02);
					generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
				}

				if (showcode.equals("yes"))
					out.println("<br />&#160;code&#160;" + generateInput("text", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "docEditInputMiddle", " readonly ", theHTMLextra));
				else
					out.println(generateInput("hidden", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "", "", theHTMLextra));
				// GESTISCO ELEMENTI TIPO "ROLE"
				for (int q = 1; q < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento") + 1; q++) {
					out.println("<br />");
					String nameCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/elemento[" + (q + 1) + "]" + "/@name");
					out.println(nameCorrente + "&#160;");
					generateSubInput(macroArea, sezione, elemento, "/elemento", prefix, 1, q, out);
				}
				out.println("</td><td valign=\"top\" align=\"left\">");

				out.println(generateLookupControl(archRefCorrente, theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@web_app"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extraQuery"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@sort"),
						theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@title_rule"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ud_type"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@query"), xpathCorrenteDot.replaceAll("'", "\\\\'"), "\\\'"
								+ xpathCorrenteDot.replaceAll("'", "\\\\'") + "\\\',\\\'" + campoCodiceDot.replaceAll("'", "\\\\'") + "\\\'", theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@title_format"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@flagXML"),
						theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@jspOutPut")));

				out.println("</td></tr>");
			} else if (theType.equals("multi_auther")) { // CAMPO
				// MULTIPLO
				// SEMPLICE
				out.println(part00multi);
				String xpathCorrenteSpan = (((((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix")).replace('/', '.')).replace('\'', '_')).replaceAll("]", "-")).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
				if (xpathCorrenteSpan.startsWith("."))
					xpathCorrenteSpan = xpathCorrenteSpan.substring(1);
				String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
				String codeCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@code");
				if (codeCorrente.indexOf("@") == -1) {
					codeCorrente = "@" + codeCorrente;
				}

				String theExtra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extra", false);

				String theVocKey = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ajaxVoc", false);
				String theVocType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@vocType", false);
				String theVocFilterQuery = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@filterQuery", false);

				String theArrange = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arrangeXpath", false);
				String theHTMLextra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@HTMLextra", false);
				String copyFlag = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@copyFlag", false);
				String spreadMod = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@spreadMod", false);

				if (theExtra.equals(""))
					theExtra = "onchange=\"return invalidaElementoAuther(this)\"";
				String showcode = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@showcode");
				String archRefCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arch_ref");

				// System.out.println("FormGenerator.generateSection() macroArea + sezione + elemento + \"/@prefix\" "
				// + macroArea + sezione + elemento + "/@prefix");
				// System.out.println("FormGenerator.generateSection() theXMLconf.valoreNodo(macroArea + sezione + elemento + \"/@prefix\")");
				int contatore = theXML.contaNodi(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix"));
				// System.out.println("FormGenerator.generateSection() contatore "
				// + contatore);

				boolean testSezioneOpen = false;
				if (!(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@opened")).equals("no")) {
					testSezioneOpen = true;
				}
				for (int p = 0; p < contatore + 2; p++) {

					String xpathCorrente = prefix + "[" + (p) + "]" + (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).substring((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).indexOf(prefix) + prefix.length());
					String xpathCorrenteDot = xpathCorrente.replace('/', '.');
					String campoCodiceDot = prefix.replace('/', '.') + "[" + (p) + "]" + "." + codeCorrente;
					String campoCodice = prefix + "[" + (p) + "]" + "/" + codeCorrente;
					String theExtraAdd = "";
					String removeAttach = "";
					// System.out.println("FormGenerator.generateSection() xpathCorrente "
					// + xpathCorrente);

					if ((theXML.valoreNodo(campoCodice, "")).equals("") && !(theXML.valoreNodo(xpathCorrente, "")).equals(""))
						theExtraAdd = " style=\"background-color: #FF886A\" ";
					if (p == 0) {
					} else {
						out.println("<span>");
						out.println("<span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
						out.println(part01multi);

						if (!testSezioneOpen && theXML.testaNodo(xpathCorrente)) {
							testSezioneOpen = true;
						}
						// System.out.println("MULTI_AUTHER inputType=" + inputType);
						// System.out.println("MULTI_AUTHER inputType=" + inputType);
						if (inputType.equals("text")) { // input
							// tipo TEXT
							// System.out.println("SONO IN TEXT " + xpathCorrente);
							out.println(part02multi);
							generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra + theExtraAdd, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
						} else if (inputType.equals("textarea")) { // input
							// tipo
							// TEXTAREA
							// System.out.println("SONO IN TEXTAREA " + xpathCorrente);
							out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
							out.println(part02multi);
							generaTextArea(xpathCorrente, xpathCorrenteDot, macroArea + sezione + elemento, theExtra, theHTMLextra, out);
						} else if (inputType.equals("select") || inputType.equals("radio")) { // input
							// tipo
							// SELECT
							// o
							// RADIO
							out.println(part02multi);
							generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, macroArea + sezione + elemento, out, "", theHTMLextra);
						} else if (inputType.equals("liveText")) { // input
							// tipo
							// LIVETEXT
							// +
							// HIDDEN
							out.println(part02multi);
							generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
						}

						if (showcode.equals("yes"))
							out.println("<br />codice&#160;" + generateInput("text", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "docEditInputMiddle", " readonly ", theHTMLextra));
						else
							out.println(generateInput("hidden", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "", "", theHTMLextra));
						// GESTISCO ELEMENTI TIPO "ROLE"

						for (int q = 1; q < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento") + 1; q++) {
							String nameCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/elemento[" + (q) + "]/@name");
							out.println("<br />");
							String nameOriginal = nameCorrente;
							if (nameOriginal.indexOf("[#") > 0 && nameOriginal.indexOf("#]") > 0) {
								nameOriginal = translateName(nameOriginal, q + 1);
							}
							out.println(nameOriginal + "&#160;");
							generateSubInput(macroArea, sezione, elemento, "/elemento", prefix, p, q, out);
						}
						out.println("</td>");
					}
					if (p == 0) {
						out.println("<span>");
						out.println("<span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><span id=\"sezione_head_" + xpathCorrenteSpan + "\">");
						out.println("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  ><tr class=\"dcTSSSD\">");
						out.println("<td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td>");
						out.println("<td class=\"dcTSSSD\" height=\"12\" width=\"12\"><a class=\"doceditActionLink\" id=\"sezione_link_" + xpathCorrenteSpan + "\" onclick=\"openSection('sezione_" + xpathCorrenteSpan + "',this)\" href=\"#n\">+</a>");
						out.println("</td><td class=\"dcTSSD\" colspan=\"2\">" + theName + "</td>");
						out.println("<td class=\"dcTSSSD\" width=\"50\" align=\"right\">" + generateAddControl(xpathCorrenteSpan, theArrange, prefix) + "</td>");
						out.println("</tr>");
						out.println("</table></span></span></span>");
						out.println("<span id=\"sezione_" + xpathCorrenteSpan + "\"  style=\"display:none\">");
					} else {
						out.println("<td class=\"showdocValue\" valign=\"top\" align=\"left\">");
						out.println(generateLookupControl(archRefCorrente, theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@web_app"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extraQuery"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@sort"),
								theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@title_rule"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ud_type"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@query"), xpathCorrenteDot.replaceAll("'", "\\\\'"), "\\\'"
										+ xpathCorrenteDot.replaceAll("'", "\\\\'") + "\\\',\\\'" + campoCodiceDot.replaceAll("'", "\\\\'") + "\\\'", theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@title_format"), theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@flagXML"),
								theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@jspOutPut")));

						out.println("</td>");
						out.println("<td class=\"showdocValue\" width=\"50\" align=\"right\" valign=\"top\">");
						out.println(generateRemoveControl(xpathCorrenteSpan + "[" + String.valueOf(p) + "]", copyFlag, removeAttach, spreadMod) + "</td>");
						out.println("</tr>");
						out.println(" </table></span></span>");
					}
					if (p == (contatore + 1)) {
						out.println("</span>");
						if (testSezioneOpen) {
							out.println("<script type=\"text/javascript\">showSection('" + xpathCorrenteSpan + "')</script>");
							testSezioneOpen = false;
						}
					}
				}
				out.println("</td></tr>");
			} else if (theType.equals("group")) {
				String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
				String xpathCorrenteSpan = ((((prefix.replace('/', '.')).replace('\'', '_')).replace(']', '-')).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
				if (xpathCorrenteSpan.startsWith("."))
					xpathCorrenteSpan = xpathCorrenteSpan.substring(1);
				boolean inLine = false;
				boolean testSezioneOpen = false;
				if (!(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@opened")).equals("no")) {
					testSezioneOpen = true;
				}
				if ((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@inline")).equals("yes"))
					inLine = true;
				out.println(part00multi);
				out.println("<span id=\"sezione_head_" + xpathCorrenteSpan + "\">");
				out.println("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  ><tr class=\"dcTSSSD\">");
				out.println("<td height=\"12\" width=\"4\"><span style=\"font-size:1px\">&#160;</span></td>");
				out.println("<td class=\"dcTSSSD\" height=\"12\" width=\"12\"><a class=\"doceditActionLink\" id=\"sezione_link_" + xpathCorrenteSpan + "\" onclick=\"openSection('sezione_" + xpathCorrenteSpan + "',this)\" href=\"#n\">+</a>");
				out.println("</td><td class=\"dcTSSD\">" + theName + "</td>");
				out.println("<td class=\"dcTSSSD\" width=\"50\" align=\"right\"></td>");
				out.println("</tr>");
				out.println("</table></span>");
				out.println("<span id=\"sezione_" + xpathCorrenteSpan + "\"  style=\"display:none\">");
				out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
				out.print(part01multiGroup);
				out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
				for (int a = 0; a < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento"); a++) {
					String nodoCorrente = macroArea + sezione + elemento + "/elemento[" + (a + 1) + "]";
					String xpathCorrente = theXMLconf.valoreNodo(nodoCorrente + "/text()");
					String xpathCorrenteDot = xpathCorrente.replace('/', '.');
					inputType = theXMLconf.valoreNodo(nodoCorrente + "/@input_type");
					theClass = "docEditInput" + theXMLconf.valoreNodo(nodoCorrente + "/@class");
					theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
					String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
					String theExtra = theXMLconf.valoreNodo(nodoCorrente + "/@extra", false);

					String theVocKey = theXMLconf.valoreNodo(nodoCorrente + "/@ajaxVoc", false);
					String theVocType = theXMLconf.valoreNodo(nodoCorrente + "/@vocType", false);
					String theVocFilterQuery = theXMLconf.valoreNodo(nodoCorrente + "/@filterQuery", false);

					theType = theXMLconf.valoreNodo(nodoCorrente + "/@type");

					if (!inLine) {
						out.println(part02multiGroup);
						if (!theXMLconf.valoreNodo(nodoCorrente + "/@id").equals("data"))
							out.println(theName);
						/* VERIFICARE */
						if (inputType.equals("textarea"))
							out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
						out.println(part03multiGroup);
					} else {
						if (inLine && a == 0) {
							out.println(part02multiGroup);
							if (!theXMLconf.valoreNodo(nodoCorrente + "/@id").equals("data"))
								out.println(theName);
							if (inputType.equals("textarea"))
								out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
							out.println(part03multiGroup);
						} else {
							if (!theName.equals("")) {
								out.println("</td></tr>");
								out.println(part02multiGroup);
								out.println(theName);
								if (inputType.equals("textarea"))
									out.println(generateTextAreaControl(xpathCorrenteDot, "14"));
								out.println(part03multiGroup);
							}
						}
					}
					if (theType.equals("custom")) { // CAMPI DI
						// TIPO
						// CUSTOM
						generateCustomElementMulti(theXMLconf.valoreNodo(nodoCorrente + "/@id"), nodoCorrente, out, xpathCorrente);
					} else {
						if (inputType.equals("text")) { // input
							// tipo
							// TEXT
							generaText(xpathCorrente, xpathCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
						} else if (inputType.equals("textarea")) { // input
							// tipo
							// TEXTAREA
							generaTextArea(xpathCorrente, xpathCorrenteDot, nodoCorrente, theExtra, theHTMLextra, out);
						} else if (inputType.equals("select") || inputType.equals("radio")) { // input
							// tipo
							// SELECT
							// o
							// RADIO
							generaSceltaMultipla(xpathCorrente, xpathCorrenteDot, inputType, nodoCorrente, out, "", theHTMLextra);
						} else if (inputType.equals("liveText")) { // input
							// tipo
							// LIVETEXT
							// +
							// HIDDEN
							generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
						}
					}
					if (!inLine) {
						out.println("</td></tr>");
					}
				}
				if (inLine) {
					out.println("</td></tr>");
				}
				out.println("</table>");
				out.println("</td><td></td>");
				out.println("</tr></table>");
				out.println("</span>");
				if (testSezioneOpen) {
					out.println("<script type=\"text/javascript\">showSection('" + xpathCorrenteSpan + "')</script>");
					testSezioneOpen = false;
				}
				out.println("</td></tr>");
			} else if (theType.equals("custom")) { // CAMPI DI TIPO
				// CUSTOM
				generateCustomElement(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@id"), macroArea + sezione + elemento, out, false);
			}

		}
	}

	private String translateName(String theName, int i) {
		String ilValore = theName;
		String ilValoreIni = ilValore.substring(0, ilValore.indexOf("[#"));
		String ilValoreFin = ilValore.substring(ilValore.indexOf("#]") + 2);
		String bloccoValore = ilValore.substring(ilValore.indexOf("[#") + 1, ilValore.indexOf("#]") + 1);

		String contatoreMultiplo = String.valueOf(i - 1);
		while (contatoreMultiplo.length() < bloccoValore.length()) {
			contatoreMultiplo = "0" + contatoreMultiplo;
		}
		ilValore = ilValoreIni + " (" + contatoreMultiplo + ") " + ilValoreFin;
		return ilValore;
	}

	private void generateSubInput(String currMacroArea, String currSezione, String currElemento, String currSuffix, String prefix, int indiceSup, int q, JspWriter out) throws Exception {

		String percorsoNodo = currMacroArea + currSezione + currElemento + currSuffix + "[" + (q) + "]";

		String xpathCorrente = theXMLconf.valoreNodo(percorsoNodo + "/text()");
		String theHTMLextra = theXMLconf.valoreNodo(percorsoNodo + "/@HTMLextra", false);
		String theExtra = theXMLconf.valoreNodo(percorsoNodo + "/@extra", false);

		String theVocKey = theXMLconf.valoreNodo(percorsoNodo + "/@ajaxVoc", false);
		String theVocType = theXMLconf.valoreNodo(percorsoNodo + "/@vocType", false);
		String theVocFilterQuery = theXMLconf.valoreNodo(percorsoNodo + "/@filterQuery", false);

		String inputType = theXMLconf.valoreNodo(percorsoNodo + "/@input_type");
		String theClass = "docEditInput" + theXMLconf.valoreNodo(percorsoNodo + "/@class");
		String xpathCorrenteCorrente = "";
		try {
			// xpathCorrenteCorrente = prefixCorrente +
			// (theXMLconf.valoreNodo(percorsoNodo +
			// "/text()")).substring((theXMLconf.valoreNodo(percorsoNodo +
			// "/text()")).indexOf(prefixCorrente) + prefixCorrente.length());
			xpathCorrenteCorrente = prefix + "[" + (indiceSup) + "]" + (theXMLconf.valoreNodo(percorsoNodo + "/text()")).substring((theXMLconf.valoreNodo(percorsoNodo + "/text()")).indexOf(prefix) + prefix.length());
		} catch (Exception e) {
			xpathCorrenteCorrente = xpathCorrente;
		}
		String xpathCorrenteCorrenteDot = xpathCorrenteCorrente.replace('/', '.');
		if (inputType.equals("text")) { // input tipo TEXT
			generaText(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, theClass, theExtra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery, out);
		} else if (inputType.equals("textarea")) { // input tipo
			// TEXTAREA
			// input tipo
			// TEXTAREA
			if (theXMLconf.valoreNodo(percorsoNodo + "/@rich").equals("yes")) {
				out.println(generateRichTextAreaControl(xpathCorrenteCorrenteDot, "14"));
				out.println("<br />");
				generaRichTextArea(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, percorsoNodo, theExtra, theHTMLextra, out);
			} else if (theXMLconf.valoreNodo(percorsoNodo + "/@rich").equals("flash")) {
				generaFlashTextArea(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, percorsoNodo, theExtra, theHTMLextra, out);
			} else if (theXMLconf.valoreNodo(percorsoNodo + "/@rich").equals("basicInPage")) {
				generaRichTextAreaBasicInPage(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, percorsoNodo, theExtra, theHTMLextra, out);
			} else {
				out.println(generateTextAreaControl(xpathCorrenteCorrenteDot, "14"));
				out.println("<br />");
				generaTextArea(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, percorsoNodo, theExtra, theHTMLextra, out);
			}

		} else if (inputType.equals("select") || inputType.equals("radio")) { // input
			// tipo
			// SELECT
			// o
			// RADIO

			generaSceltaMultipla(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, inputType, percorsoNodo, out, "", theHTMLextra);
		} else if (inputType.equals("liveText")) { // input tipo
			// LIVETEXT + HIDDEN

			generaLiveText(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, theHTMLextra, out);
		}

	}

	public void generateSectionOnDemand(javax.servlet.jsp.JspWriter out) throws Exception {
		String theType = "";

		theType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@type");
		if (theType.equals("")) { // elemento SINGOLO
			String xpathCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()");
			String theHTMLextra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@HTMLextra", false);
			String xpathCorrenteDot = xpathCorrente.replace('/', '.');

			generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
		} else {
			if (theType.equals("multi_group")) {
				String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
				String theArrange = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arrangeXpath", false);
				int contatore = theXML.contaNodi(prefix);
				for (int p = 0; p < contatore + 2; p++) {

					for (int a = 0; a < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento"); a++) {
						String nodoCorrente = macroArea + sezione + elemento + "/elemento[" + (a + 1) + "]";
						String xpathCorrente = theXMLconf.valoreNodo(nodoCorrente + "/text()");
						String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
						xpathCorrente = prefix + "[" + (p) + "]" + xpathCorrente.substring(xpathCorrente.indexOf(prefix) + prefix.length());
						String xpathCorrenteDot = xpathCorrente.replace('/', '.');

						generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);

					}

				}
			} else {
				if (theType.equals("multi")) { // CAMPO MULTIPLO SEMPLICE

					String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
					int contatore = theXML.contaNodi(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix"));
					String theArrange = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arrangeXpath", false);
					for (int p = 0; p < contatore + 2; p++) {
						String xpathCorrente = prefix + "[" + (p) + "]" + (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).substring((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).indexOf(prefix) + prefix.length());
						String xpathCorrenteDot = xpathCorrente.replace('/', '.');
						generaLiveText(xpathCorrente, xpathCorrenteDot, "", out);
					}

				} else {
					if (theType.equals("auther")) { // CAMPO AUTHER
						String xpathCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()");
						String xpathCorrenteDot = xpathCorrente.replace('/', '.');
						String codeCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@code");
						if (codeCorrente.indexOf("@") == -1) {
							codeCorrente = "@" + codeCorrente;
						}
						String theHTMLextra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@HTMLextra", false);
						String theExtra = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@extra", false);
						String theVocKey = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@ajaxVoc", false);
						String theVocType = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@vocType", false);

						if (theExtra.equals(""))
							theExtra = "onchange=\"return invalidaElementoAuther(this)\"";
						String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");

						String campoCodiceDot = prefix.replace('/', '.') + "." + codeCorrente;
						String campoCodice = prefix + "/" + codeCorrente;

						// tipo
						// LIVETEXT
						// + HIDDEN

						generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);

						out.println(generateInput("hidden", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "", "", theHTMLextra));
						// GESTISCO ELEMENTI TIPO "ROLE"
						for (int q = 1; q < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento") + 1; q++) {
							String prefixCorrente = prefix;

							String xpathCorrenteCorrente = prefixCorrente
									+ (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/elemento" + "[" + (q) + "]" + "/text()")).substring((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/elemento" + "[" + (q) + "]" + "/text()")).indexOf(prefixCorrente) + prefixCorrente.length());
							String xpathCorrenteCorrenteDot = xpathCorrenteCorrente.replace('/', '.');

							generaLiveText(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, theHTMLextra, out);
						}

					} else {
						if (theType.equals("multi_auther")) { // CAMPO
							// MULTIPLO
							// SEMPLICE

							String prefix = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix");
							String codeCorrente = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@code");
							if (codeCorrente.indexOf("@") == -1) {
								codeCorrente = "@" + codeCorrente;
							}
							String theArrange = theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@arrangeXpath", false);
							int contatore = theXML.contaNodi(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@prefix"));

							for (int p = 0; p < contatore + 2; p++) {
								String xpathCorrente = prefix + "[" + (p) + "]" + (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).substring((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/text()")).indexOf(prefix) + prefix.length());
								String xpathCorrenteDot = xpathCorrente.replace('/', '.');
								String campoCodiceDot = prefix.replace('/', '.') + "[" + (p) + "]" + "." + codeCorrente;
								String campoCodice = prefix + "[" + (p) + "]" + "/" + codeCorrente;

								generaLiveText(xpathCorrente, xpathCorrenteDot, "", out);

								out.println(generateInput("hidden", campoCodiceDot, theXML.valoreNodo(campoCodice, ""), "", "", ""));
								// GESTISCO ELEMENTI TIPO "ROLE"
								for (int q = 1; q < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento") + 1; q++) {
									String prefixCorrente = prefix;
									String xpathCorrenteCorrente = prefixCorrente
											+ "["
											+ (p)
											+ "]"
											+ (theXMLconf.valoreNodo(macroArea + sezione + elemento + "/elemento" + "[" + (q) + "]" + "/text()")).substring((theXMLconf.valoreNodo(macroArea + sezione + elemento + "/elemento" + "[" + (q) + "]" + "/text()")).indexOf(prefixCorrente)
													+ prefixCorrente.length());
									String xpathCorrenteCorrenteDot = xpathCorrenteCorrente.replace('/', '.');
									generaLiveText(xpathCorrenteCorrente, xpathCorrenteCorrenteDot, "", out);
								}
							}
							out.println("</td></tr>");
						} else {
							if (theType.equals("group")) {
								for (int a = 0; a < theXMLconf.contaNodi(macroArea + sezione + elemento + "/elemento"); a++) {
									String nodoCorrente = macroArea + sezione + elemento + "/elemento[" + (a + 1) + "]";
									String xpathCorrente = theXMLconf.valoreNodo(nodoCorrente + "/text()");
									String xpathCorrenteDot = xpathCorrente.replace('/', '.');

									theType = theXMLconf.valoreNodo(nodoCorrente + "/@type");
									generaLiveText(xpathCorrente, xpathCorrenteDot, "", out);
								}
							} else {
								if (theType.equals("custom")) { // CAMPI DI TIPO
									// CUSTOM
									generateCustomElement(theXMLconf.valoreNodo(macroArea + sezione + elemento + "/@id"), macroArea + sezione + elemento, out, false);
								}
							}
						}
					}
				}
			}
		}
	}

	private String generateLookupControl(String db_name, String theWebApp, String inputExtraQuery, String inputSort, String inputTitleRule, String inputUdType, String inputQuery, String campoDescr, String campiDaValorizzare, String titleRule, String flagXML, String outputJSP) {
		String theReturn = "<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return performLookup('" + db_name + "','" + inputExtraQuery + "','" + inputSort + "','" + inputTitleRule + "','" + inputUdType + "','" + inputQuery + "','" + /** campoDescr + */
		"','" + /** campiDaValorizzare + */
		"','" + titleRule + "',this,'" + theWebApp + "','" + flagXML + "','" + outputJSP + "')\">LOOKUP</a>";
		return theReturn;
	}

	private void generaLiveText(String xpathCorrente, String xpathCorrenteDot, String theHTMLextra, javax.servlet.jsp.JspWriter out) throws TransformerException, IOException {
		String theValue = (theXML.valoreNodo(xpathCorrente, "")).trim();
		out.println("<em>" + theValue + "</em>");
		out.println(generateInput("hidden", xpathCorrenteDot, theValue, "", "", theHTMLextra));
	}

	private void generaSceltaMultipla(String xpathCorrente, String xpathCorrenteDot, String inputType, String nodoCorrente, javax.servlet.jsp.JspWriter out, String extra, String theHTMLextra) throws Exception {
		String theValue = (theXML.valoreNodo(xpathCorrente)).trim();
		String extraPlus = "";
		String valoreCorrente = theXMLconf.valoreNodo(nodoCorrente + "/@value");
		if (valoreCorrente.indexOf("document:") == 0) {
			String fullPath = valoreCorrente.replaceAll("document:", "");
			// com.regesta.dams.utility.xml.DomManager domManager = com.regesta.dams.utility.xml.DomManager.getInstance(isStatic, realPath);
			// XMLBuilder theXMLconfTMP = new XMLBuilder(domManager.getDocument(fullPath));
			System.out.println("1111 fullPath " + fullPath);
			XMLBuilder theXMLconfTMP = ConfManager.getConfXML(fullPath);// new XMLBuilder(domManager.getDocument(fullPath));
			String externalPath = theXMLconf.valoreNodo(nodoCorrente + "/@externalPath");
			int nodiOpzioni = theXMLconfTMP.contaNodi(externalPath);
			if (inputType.equals("select")) {
				out.println("<select   name=\"" + xpathCorrenteDot + "\" class=\"docEditInput\"  " + extra + ">");
				for (int a = 0; a < nodiOpzioni; a++) {
					extraPlus = "";
					if (theValue.equalsIgnoreCase(theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"))) {
						extraPlus = " selected=\"selected\" ";
					}
					if (theValue.equals("") && (theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@selected")).equals("true")) {
						extraPlus = " selected=\"selected\" ";
					}
					out.println(generateInput("option", theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"), theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/text()"), "", extraPlus, ""));
				}
				out.println("</select>&#160;" + theHTMLextra);
			} else {
				for (int a = 0; a < nodiOpzioni; a++) {
					extraPlus = "";
					if (theValue.equalsIgnoreCase(theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"))) {
						extraPlus = " checked=\"checked\" ";
					}
					out.println(generateInput("radio", xpathCorrenteDot, theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"), theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/text()"), "docEditInputRadio", extra + " " + extraPlus, theHTMLextra));
				}
			}
		} else if (valoreCorrente.indexOf("ajax:") == 0) {
			String fullPath = valoreCorrente.replaceAll("ajax:", "");
			// com.regesta.dams.utility.xml.DomManager domManager = com.regesta.dams.utility.xml.DomManager.getInstance(isStatic, realPath);
			// XMLBuilder theXMLconfTMP = new XMLBuilder(domManager.getDocument(fullPath));
			System.out.println("2222 fullPath " + fullPath);
			XMLBuilder theXMLconfTMP = ConfManager.getConfXML(fullPath);
			// System.out.println("FormGenerator.generateCustomDataDieg()3333333333333333333333 theXMLconfTMP "+theXMLconfTMP.getXML("ISO-8859-1"));
			String externalPath = theXMLconf.valoreNodo(nodoCorrente + "/@externalPath");
			int nodiOpzioni = theXMLconfTMP.contaNodi(externalPath);
			if (inputType.equals("select")) {// title=\"" +
				// xpathCorrenteDot.replace('.',
				// '/') + "\"
				out.println("<span><select  onfocus=\"setCurrInput(this);ajaxGetValues(this,'" + fullPath + "','" + StringEscapeUtils.escapeEcmaScript(externalPath) + "')\"  name=\"" + xpathCorrenteDot + "\" class=\"docEditInput\"  " + extra + ">");
				for (int a = 0; a < nodiOpzioni; a++) {
					extraPlus = "";
					if (theValue.equalsIgnoreCase(theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"))) {
						extraPlus = "selected";
						out.println(generateInput("option", theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"), theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/text()"), "", extraPlus, ""));
					}
				}
				out.println("</select></span>&#160;" + theHTMLextra);
			} else {
				// NON IMPLEMENTABILI RADIOBUTTON AJAX
			}
		}

	}

	private void generaTextArea(String xpathCorrente, String xpathCorrenteDot, String nodoCorrente, String extraExtra, String theHTMLextra, javax.servlet.jsp.JspWriter out) throws TransformerException, IOException {

		String theValue = (theXML.valoreNodo(xpathCorrente)).trim();
		String extra = "rows=\"" + theXMLconf.valoreNodo(nodoCorrente + "/@rows") + "\" cols=\"" + theXMLconf.valoreNodo(nodoCorrente + "/@cols") + "\"";
		extra += " " + extraExtra;

		// onblur=\"this.rows=(this.rows-2)\"";
		out.println(generateInput("textarea", xpathCorrenteDot, theValue, "docEditTextArea", extra, theHTMLextra));
	}

	private void generaRichTextArea(String xpathCorrente, String xpathCorrenteDot, String nodoCorrente, String extraExtra, String theHTMLextra, javax.servlet.jsp.JspWriter out) throws TransformerException, IOException {
		String theValue = (theXML.valoreNodo(xpathCorrente)).trim();
		if(xpathCorrente.equalsIgnoreCase("/eac-cpf/control/sources/source/sourceEntry/text()")){
			System.out.println("AAAAAAAAAAA "+theValue);
		}
		String extra = "rows=\"" + theXMLconf.valoreNodo(nodoCorrente + "/@rows") + "\" cols=\"" + theXMLconf.valoreNodo(nodoCorrente + "/@cols") + "\"";
		extra += " " + extraExtra;
		if (!xpathCorrenteDot.endsWith(".@cdata")) {
			xpathCorrenteDot += ".@cdata";
		}
		out.println(generateInput("textarea", xpathCorrenteDot, theValue, "docEditTextArea", extra, theHTMLextra));
	}

	private void generaRichTextAreaBasicInPage(String xpathCorrente, String xpathCorrenteDot, String nodoCorrente, String extraExtra, String theHTMLextra, javax.servlet.jsp.JspWriter out) throws TransformerException, IOException {
		String theValue = (theXML.valoreNodo(xpathCorrente)).trim();
		String extra = "rows=\"" + theXMLconf.valoreNodo(nodoCorrente + "/@rows") + "\" cols=\"" + theXMLconf.valoreNodo(nodoCorrente + "/@cols") + "\"";
		extra += " " + extraExtra;
		if (!xpathCorrenteDot.endsWith(".@cdata")) {
			xpathCorrenteDot += ".@cdata";
		}
		out.println(generateInput("textarea", xpathCorrenteDot, theValue, "mceEditor", extra, theHTMLextra));
	}

	@Deprecated
	private void generaFlashTextArea(String xpathCorrente, String xpathCorrenteDot, String nodoCorrente, String extraExtra, String theHTMLextra, javax.servlet.jsp.JspWriter out) throws TransformerException, IOException {
		String theValue = (theXML.valoreNodo(xpathCorrente)).trim();
		theValue = theValue.replaceAll("\n", "<br />");
		theValue = theValue.replaceAll("\"&quot;", "\"");
		String extra = "";
		String nomeVarJS = xpathCorrente.replaceAll("/", "").replaceAll("'", "").replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\]", "").replaceAll("\\[", "").replaceAll("=", "").replaceAll("-", "").replaceAll("@", "");
		extra += " " + extraExtra;
		out.println("<script language=\"JavaScript\" type=\"text/javascript\">");
		out.println("AC_FL_RunContent('src', '" + "" + "/flash/SimpleRTE','FlashVars', 'flashid=" + nomeVarJS + "-ID','width', '100%','height', '150','align', 'middle','id', '" + nomeVarJS + "-ID','quality', 'high','bgcolor', '#869ca7','name', '" + nomeVarJS
				+ "-ID','allowScriptAccess','sameDomain','type', 'application/x-shockwave-flash','pluginspage', 'http://www.adobe.com/go/getflashplayer');");
		// out.println("AC_FL_RunContent('src', '" + frontPath + "/flash/SimpleRTE','FlashVars', 'flashid=" + nomeVarJS + "-ID','width', '100%','height', '150','align', 'middle','id', '" + nomeVarJS + "-ID','quality', 'high','bgcolor', '#869ca7','name', '" + nomeVarJS
		// + "-ID','allowScriptAccess','sameDomain','type', 'application/x-shockwave-flash','pluginspage', 'http://www.adobe.com/go/getflashplayer');");
		// out.println(" initText(document.theForm['theValue'],\""+nomeVarJS+"\");");
		out.println("eleArray[eleArray.length]='" + nomeVarJS + ";" + StringEscapeUtils.escapeEcmaScript(xpathCorrenteDot) + "'");
		out.println("</script>");
		out.println("<textarea style=\"display:none\" name=\"" + xpathCorrenteDot + "\">" + theValue + "</textarea>");
	}

	public String rteSafe(String strText) {
		// returns safe code for preloading in the RTE
		String tmpString = strText;
		try {
			// convert all types of single quotes
			tmpString = tmpString.replace((char) 145, (char) 39);
			tmpString = tmpString.replace((char) 146, (char) 39);
			tmpString = tmpString.replaceAll("'", "&#39;");

			// convert all types of double quotes
			tmpString = tmpString.replace((char) 147, (char) 34);
			tmpString = tmpString.replace((char) 148, (char) 34);

			// replace carriage returns & line feeds
			tmpString = tmpString.replace((char) 10, (char) 32);
			tmpString = tmpString.replace((char) 13, (char) 32);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmpString;
	}

	private void generaText(String xpathCorrente, String xpathCorrenteDot, String theClass, String extra, String theHTMLextra, String theVocKey, String theVocType, String theVocFilterQuery, javax.servlet.jsp.JspWriter out) throws TransformerException, IOException {
		String theValue = (theXML.valoreNodo(xpathCorrente, "")).trim();
		if (theVocKey.equals("")) {
			out.println(generateInput("text", xpathCorrenteDot, theValue, theClass, extra, theHTMLextra));
		} else {
			out.println(generateAjaxInput("text", xpathCorrenteDot, theValue, theClass, extra, theHTMLextra, theVocKey, theVocType, theVocFilterQuery));
		}
	}

	private String generateTextAreaControl(String theName, String theFactor) {
		String theReturn = "&#160;&#160;<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return openTextArea(this)\">apri</a>";

		return theReturn;
	}

	private String generateRichTextAreaControl(String theName, String theFactor) {
		String theReturn = "&#160;&#160;<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return openRichTextArea(this)\">apri editor HTML</a>";
		return theReturn;
	}

	private String generateInput(String type, String name, String value, String style, String extraParam, String theHTMLextra) {
		String theInput = "";
		name = changeName(name);
		if (type.equals("text") || type.equals("hidden")) {
			if (extraParam.equals(""))
				extraParam = " ";
			else {
				if ((extraParam.toUpperCase()).indexOf("ONFOCUS=\"") != -1)
					extraParam.replaceAll("onfocus=\"", "onfocus=\"setCurrInput(this);");
				else
					extraParam += " onfocus=\"setCurrInput(this);\" ";
			}// title=\"" + name.replace('.', '/') + "\"
			theInput = "<input  name=\"" + name + "\" type=\"" + type + "\" ";
			if (type.equals("text"))
				theInput += "class=\"" + style + "\" ";
			theInput += extraParam + " value=\"" + value + "\" />";
		}
		if (type.equals("textarea")) {
			if (extraParam.equals(""))
				extraParam = " ";
			else {
				if ((extraParam.toUpperCase()).indexOf("ONFOCUS=\"") != -1)
					extraParam.replaceAll("onfocus=\"", "onfocus=\"setCurrInput(this);");
				else
					extraParam += " onfocus=\"setCurrInput(this);\" ";
			}// title=\"" + name.replace('.', '/') + "\"
			theInput = "<textarea class=\"" + style + "\" " + extraParam + "  name=\"" + name + "\">" + value + "</textarea>";
		}
		if (type.equals("option")) {
			theInput = "<option  value=\"" + name + "\" " + extraParam + ">" + value + "</option>";
			return theInput;
		}
		return theInput + "&#160;" + theHTMLextra;
	}

	private String generateAjaxInput(String type, String name, String value, String style, String extraParam, String theHTMLextra, String theVocKey, String theVocType, String theVocFilterQuery) throws UnsupportedEncodingException {
		String theInput = "";
		String idJs = java.net.URLEncoder.encode(name, "iso-8859-1");
		idJs = idJs.replaceAll("%", "");
		idJs = idJs.replace('.', '_');
		if (extraParam.equals("")) {
			// extraParam = " ";
			extraParam = " onclick=\"makeAutocomplete(this)\" ";
		} else {
			if ((extraParam.toUpperCase()).indexOf("ONFOCUS=\"") != -1) {
				// extraParam.replaceAll("onfocus=\"",
				// "onfocus=\"setCurrInput(this);");
				extraParam.replaceAll("onclick=\"", "onclick=\"makeAutocomplete(this);");
			} else {
				// extraParam += " onfocus=\"setCurrInput(this);\" ";
				extraParam += " onclick=\"makeAutocomplete(this);\" ";
			}
		}
		if (!theVocKey.startsWith("XML,")) {
			theVocKey = "XML," + theVocKey;
		}
		String theClass = "complete";
		String flagAzione = "vocabolario";

		if (theVocType.indexOf("ajax:") == 0) {
			theClass = "completestatic";
			flagAzione = "valoriControllati";
		}

		StringBuffer result = new StringBuffer();
		List<String> listAction = new ArrayList<String>();
		// System.out.println("FormGenerator.generateAjaxInput() theVocFilterQuery " + theVocFilterQuery);
		AdvancedUtility.compileString(theVocFilterQuery, result, listAction);
		// ho la query da passare al newprepared
		// System.out.println(result.toString());
		// ho la lista con all'interno il match azione/valore da esegure
		// System.out.println(listAction);
		List<String> listWritePrepXwQuery = new ArrayList<String>();
		AdvancedUtility.extractValue(listAction, listWritePrepXwQuery, theXML);
		NewPreparedXwQuery newPreparedXwQuery = new NewPreparedXwQuery(result.toString().replaceAll("&quot;", "\""));
		newPreparedXwQuery.setProgressiveValues(listWritePrepXwQuery);
		// System.out.println(newPreparedXwQuery.compile());
		// System.out.println(newPreparedXwQuery.compile());
		// System.out.println(newPreparedXwQuery.compile());
		// System.out.println(newPreparedXwQuery.compile());
		String filterQuery = newPreparedXwQuery.compile();
		if (!filterQuery.equals("")) {
			filterQuery = URLEncoder.encode(filterQuery, "ISO-8859-1");
		}
		// title=\"" + name.replace('.', '/') + "\"
		theInput += "<input name=\"" + name + "\" type=\"" + type + "\"   id=\"" + idJs + "\"  servlet=\"" + contextPath + "/" + workFlowBean.getAlias() + "/ajax.html?actionFlag=" + flagAzione + "Json&amp;key=" + theVocKey + "&amp;typology=" + theVocType
				+ "&amp;attrFirstIdx=&amp;numKeys=20&amp;filterQuery=" + filterQuery + "\" ";
		/*
		 * theInput = "<div class=\"dojoDiv\" dojoType=\"custom.ComboBoxReadStore\" jsId=\"" +idJs+"ID\" url=\"AjaxServlet?flagTipologia=vocabolarioJson&key="+ theVocKey+"&typology="+theVocType+ "&attrFirstIdx=a&numKeys=20\" requestMethod=\"get\" ></div>"; theInput += "<input title=\"" +
		 * name.replace('.', '/') + "\" dojoType=\"dijit.form.ComboBox\" autoComplete=\"false\" store=\"" +idJs+"ID\" searchDelay=\"200\" name=\"" + name + "\" type=\"" + type + "\" class=\"middle\" id=\""+idJs+"\" ";
		 */

		if (type.equals("text")) {
			theInput += "class=\"" + theClass + " " + style + "\" ";
		}
		theInput += extraParam + " value=\"" + value + "\" />";
		return theInput + "&#160;" + theHTMLextra;
	}

	private String generateInput(String type, String name, String value, String text, String style, String extraParam, String theHTMLextra) {
		String theInput = "";

		name = changeName(name);

		if (type.equals("radio")) {
			// title=\"" + name.replace('.', '/') + "\"
			theInput = "<input type=\"radio\" name=\"" + name + "\" value=\"" + value + "\" class=\"" + style + "\" " + extraParam + ">" + text;
			return theInput;
		}
		return theInput + "&#160;" + theHTMLextra;
	}

	private String changeName(String name) {
		String newName = "";
		if (newXPath != null && !newXPath.equals("")) {
			newName = StringUtils.replace(newXPath, "/", ".");
		} else {
			newName = name;
		}
		return newName;
	}

	public String generateAddControl(String spanId, String theArrange, String prefix) {
		String theReturn = "<span id=\"control_" + spanId + "\" style=\"display:none\">";
		// if (!theArrange.equals("") && theArrange.equals("true")) {
		// if (editingBean != null && editingBean.getPhysDoc() > 0) {
		// theReturn += "<a class=\"doceditActionLink\" href=\"#n\" ";
		// theReturn += "onclick=\"return openArrangeXpath('" + StringEscapeUtils.escapeEcmaScript(prefix) + "','" + editingBean.getPhysDoc() + "')\">riordina</a>";
		// } else {
		// theReturn += "<a class=\"doceditActionLink\" href=\"#n\" ";
		// theReturn += ">riordina non disponibile</a>";
		// }
		// }
		theReturn += "<a class=\"doceditActionLink\" href=\"#n\" ";
		theReturn += "onclick=\"return addInstance(this)\">aggiungi</a>";
		theReturn += "</span>";
		return theReturn;
	}

	public String generateRemoveControl(String spanId, String flagCopia, String removeAttach, String spreadMod) {
		String theReturn = "<span id=\"control_" + spanId + "\">";
		theReturn += "<a class=\"doceditActionLink\" href=\"#n\" ";
		// theReturn += "onclick=\"return addInstance('" + spanId +
		// "',this)\">inserisci</a>";
		theReturn += "onclick=\"return addInstance(this, true)\">inserisci</a>";
		if (flagCopia.equals("true")) {
			theReturn += "<a class=\"doceditActionLink\" href=\"#n\" ";
			// theReturn += "onclick=\"return addInstance('" + spanId +
			// "',this)\">inserisci</a>";
			theReturn += "onclick=\"return copyInstance(this, true)\">copia</a>";
		}
		if (removeAttach.equals("true")) {
			theReturn += "&#160;<a class=\"doceditActionLink\" href=\"#n\" ";
			theReturn += "onclick=\"return removeAttach(this)\">rimuovi allegato digitale</a>";
			theReturn += "</span>";
		} else if (!spreadMod.equals("")) {
			theReturn += "&#160;<a class=\"doceditActionLink\" href=\"#n\" ";
			theReturn += "onclick=\"return removeInstanceSpread(this,'" + spreadMod + "')\">rimuovi rel</a>";
			theReturn += "</span>";
		} else {
			theReturn += "&#160;<a class=\"doceditActionLink\" href=\"#n\" ";
			theReturn += "onclick=\"return removeInstance(this)\">rimuovi</a>";
			theReturn += "</span>";
		}

		return theReturn;
	}

	private void generateCustomElement(String type, String nodoCorrente, javax.servlet.jsp.JspWriter out, boolean noHTML) throws Exception {

		// String theType = theXMLconf.valoreNodo(nodoCorrente + "/@type");
		if (type.equals("processinfo")) {
			generateCustomProcessinfo(out, nodoCorrente, noHTML);
		} else {
			if (type.equals("data")) {
				generateCustomData(out, nodoCorrente, noHTML, "");
			} else {
				if (type.equals("lingua")) {
					generateCustomLingua(out, nodoCorrente, noHTML);
				} else {
					if (type.equals("level")) {
						generateCustomLevel(out, nodoCorrente, noHTML);
					} else {
						if (type.equals("modificaAuther")) {
							generateCustomModificaAuther(out, nodoCorrente, noHTML);
						} else {
							if (type.equals("levelFotografico")) {
								generateCustomLevelFotografico(out, nodoCorrente, noHTML);
							} else {
								if (type.equals("upload")) {
									generateCustomUpload(out, nodoCorrente, false, theXMLconf.valoreNodo(nodoCorrente + "/text()"), "single");
								} else {
									if (type.equals("sfoglia") || type.equals("associate")) {
										generateCustomSfoglia(out, nodoCorrente, false, theXMLconf.valoreNodo(nodoCorrente + "/text()"), "single");
									} else {
										if (type.equals("dataSimple")) {
											generateCustomData(out, nodoCorrente, noHTML, "simple");
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void generateCustomElementMulti(String type, String nodoCorrente, javax.servlet.jsp.JspWriter out, String xpathCorrente) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		if (type.equals("upload")) {
			generateCustomUpload(out, nodoCorrente, true, xpathCorrente, "multi");
		} else {
			generateCustomSfoglia(out, nodoCorrente, true, xpathCorrente, "multi");
		}
	}

	private void generateCustomUpload(JspWriter out, String nodoCorrente, boolean noHTML, String xpathCorrente, String theMode) throws TransformerException, IOException {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String originalFileNameXpath = StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo(nodoCorrente + "/@originalFileNameXpath"));
		String xpathCorrenteDot = xpathCorrente.replace('/', '.');
		String idRef = theXMLconf.valoreNodo(nodoCorrente + "/@externalPath");

		String resultIdRef = "";
		if (idRef.indexOf(";") != -1) {
			String[] idRefSplit = idRef.split(";");
			for (int i = 0; i < idRefSplit.length; i++) {
				String appoStr = StringUtils.substringBetween(idRefSplit[i], "$", "$");
				if (appoStr != null) {
					String xPathPart = idRefSplit[i].replaceAll(appoStr, "");
					String xPathExp = xPathPart.replaceAll("\\{\\$\\$\\}", "");
					String subStr = appoStr.replaceAll("substring:", "");
					int indexStart = Integer.parseInt(subStr.substring(0, subStr.indexOf("-")));
					int indexEnd = Integer.parseInt(subStr.substring(subStr.indexOf("-") + 1));
					resultIdRef += StringUtils.substring(theXML.valoreNodo(xPathExp), indexStart, indexEnd);
				} else {
					resultIdRef += theXML.valoreNodo(idRefSplit[i]);
				}
			}
		} else {
			resultIdRef = theXML.valoreNodo(idRef);
		}
		String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
		String theClass = "docEditInput" + theXMLconf.valoreNodo(nodoCorrente + "/@class");
		String mode = theXMLconf.valoreNodo(nodoCorrente + "/@mode");
		String xpathCorrenteSpan = (((((theXMLconf.valoreNodo(nodoCorrente + "/@prefix")).replace('/', '.')).replace('\'', '_')).replaceAll("]", "-")).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
		String previewMode = theXMLconf.valoreNodo(nodoCorrente + "/@previewMode");
		String previewLowPrefix = theXMLconf.valoreNodo(nodoCorrente + "/@previewLowPrefix");
		String previewThumbPrefix = theXMLconf.valoreNodo(nodoCorrente + "/@previewThumbPrefix");
		if (xpathCorrenteSpan.startsWith("."))
			xpathCorrenteSpan = xpathCorrenteSpan.substring(1);

		if (!noHTML) {
			out.println(part01);
			out.println(theName);
			out.println(part02);
		}

		if (previewMode.equals("simpleShowImage") && !theXML.valoreNodo(xpathCorrente).equals("")) {
			// if (userBean != null) {
			previewLowPrefix = previewLowPrefix.replaceAll("getTheArch", workFlowBean.getAlias());
			previewThumbPrefix = previewThumbPrefix.replaceAll("getTheArch", workFlowBean.getAlias());
			// }
			if (!previewLowPrefix.equals("")) {
				out.println("<a class=\"fancy\" href=\"" + previewLowPrefix + theXML.valoreNodo(xpathCorrente) + "\">");
			}
			out.println("<img class=\"imgInDocEdit\" src=\"" + previewThumbPrefix + theXML.valoreNodo(xpathCorrente) + "\"/>");
			if (!previewLowPrefix.equals("")) {
				out.println("</a>");
			}
		}

		out.println(generateInput("text", xpathCorrenteDot, theXML.valoreNodo(xpathCorrente), theClass, "  ", theHTMLextra));

		// if (!(originalFileNameXpath.trim()).equals("")) {
		out.println("<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return allega('" + resultIdRef + "',this,'" + xpathCorrenteSpan + "','" + mode + "','" + originalFileNameXpath + "','" + StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo(nodoCorrente + "/@prefix")) + "','"
				+ getPhysDoc() + "')\">allega</a>");
		// } else {
		// out.println("<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return allega(document.theForm.theDb.value,'"
		// + theXML.valoreNodo(idRef) + "',this,'" + theMode + "','" +
		// xpathCorrenteSpan + "','" + mode + "')\">allega</a>");
		// }
		// commentato simone 28/05/2008
		// out.println("&#160;&#160;<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return visualizzaImg(this,'"
		// + imagePath + "')\">visualizza</a>");
		if (!noHTML) {
			out.println("</td></tr>");
		}
	}

	private void generateCustomSfoglia(JspWriter out, String nodoCorrente, boolean noHTML, String xpathCorrente, String theMode) throws TransformerException, IOException {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String xpathCorrenteDot = xpathCorrente.replace('/', '.');
		String originalFileNameXpath = StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo(nodoCorrente + "/@originalFileNameXpath"));
		String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
		String theClass = "docEditInput" + theXMLconf.valoreNodo(nodoCorrente + "/@class");
		String xpathCorrenteSpan = (((((theXMLconf.valoreNodo(nodoCorrente + "/@prefix")).replace('/', '.')).replace('\'', '_')).replaceAll("]", "-")).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
		if (!noHTML) {
			out.println(part01);
			out.println(theName);
			out.println(part02);
		}
		String idRef = theXMLconf.valoreNodo(nodoCorrente + "/@externalPath");

		String resultIdRef =  theXML.valoreNodo(idRef);
		String mode = theXMLconf.valoreNodo(nodoCorrente + "/@mode");
		out.println(generateInput("text", xpathCorrenteDot, theXML.valoreNodo(xpathCorrente), theClass, " ", theHTMLextra));
		//out.println("<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return associate(document.theForm.theDb.value,this,'" + xpathCorrenteSpan + "','" + originalFileNameXpath + "')\">associa</a>");
		out.println("<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return associate('" + resultIdRef + "',this,'" + xpathCorrenteSpan + "','" + mode + "','" + originalFileNameXpath + "','" + StringEscapeUtils.escapeEcmaScript(theXMLconf.valoreNodo(nodoCorrente + "/@prefix")) + "','"	+ getPhysDoc() + "')\">associa</a>");
//		out.println("&#160;&#160;<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return visualizzaImg(this,'" + imagePath + "')\">visualizza</a>");
		if (!noHTML) {
			out.println("</td></tr>");
		}
	}

	protected void generateCustomLevelFotografico(JspWriter out, String nodoCorrente, boolean noHTML) throws Exception {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
		String prefix = theXMLconf.valoreNodo(nodoCorrente + "/@prefix");
		String prefixDot = prefix.replace('/', '.');
		out.println(part01);
		out.println(theName);
		out.println(part02);
		generaSceltaMultipla(prefix + "/text()", prefixDot + ".text()", "select", nodoCorrente, out, " onchange=\"return valorizzaLevel(this,'.c.@level')\"", theHTMLextra);
		out.println("&#160;&#160;" + generateInput("hidden", ".c.@level", theXML.valoreNodo("/c/@level"), "docEditInputMiddle", " readonly=\"readonly\" ", theHTMLextra));
		out.println("</td></tr>");
	}

	protected void generateCustomModificaAuther(JspWriter out, String nodoCorrente, boolean noHTML) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String prefix = theXMLconf.valoreNodo(nodoCorrente + "/@prefix");
		String xPathValue = theXMLconf.valoreNodo(nodoCorrente + "/text()");
		String textValue = theXML.valoreNodo(xPathValue);
		String prefixDot = (prefix.replace('/', '.')).replaceAll("'", "\\\\'");
		if (!noHTML) {
			out.println(part01);
			out.println(part02);
		}
		out.println("<a class=\"doceditActionLink\" href=\"#n\" onclick=\"return modificaFormaAutorizzata('" + prefixDot + "')\">" + theName + "</a>");
		out.println("</td></tr>");
	}

	protected void generateCustomLevel(JspWriter out, String nodoCorrente, boolean noHTML) throws Exception {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String prefix = theXMLconf.valoreNodo(nodoCorrente + "/@prefix");
		String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);

		String prefixDot = prefix.replace('/', '.');
		if (!noHTML) {
			out.println(part01);
			out.println(theName);
			out.println(part02);
		}
		generaSceltaMultipla(prefix + "/@level", prefixDot + ".@level", "select", nodoCorrente, out, " onchange=\"return testaLevel(this,'" + prefixDot.replaceAll("'", "\\\\'") + "')\"", theHTMLextra);
		if (!(theXML.valoreNodo(prefix + "/@level")).equals("otherlevel"))
			out.println("&#160;&#160;" + generateInput("text", prefixDot + ".@otherlevel", theXML.valoreNodo(prefix + "/@otherlevel"), "docEditInputMiddle", " readonly=\"readonly\" ", theHTMLextra));
		else
			out.println("&#160;&#160;" + generateInput("text", prefixDot + ".@otherlevel", theXML.valoreNodo(prefix + "/@otherlevel"), "docEditInputMiddle", "", theHTMLextra));
		out.println("</td></tr>");
	}

	protected void generateCustomLingua(JspWriter out, String nodoCorrente, boolean noHTML) throws Exception {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String prefix = theXMLconf.valoreNodo(nodoCorrente + "/@prefix");
		String theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra", false);
		String prefixDot = prefix.replace('/', '.');
		if (!noHTML) {
			out.println(part01);
			out.println(theName);
			out.println(part02);
		}
		generaSceltaMultipla(prefix + "/@langcode", prefixDot + ".@langcode", "select", nodoCorrente, out, " onchange=\"return testaLingua(this,'" + prefixDot.replaceAll("'", "\\\\'") + "')\"", theHTMLextra);
		out.println("&#160;&#160;" + generateInput("hidden", prefixDot + ".text()", theXML.valoreNodo(prefix + "/text()"), "docEditInputShort", " readonly=\"readonly\" ", theHTMLextra));
		out.println("</td></tr>");
	}

	public String generateDaoGrpAddControl(String spanId, int level, String prefix) {
		String theReturn = "";
		theReturn += "<a class=\"doceditActionLink\" href=\"#n\" ";
		// theReturn += "onclick=\"return addInstanceNastedDaoGrp('" + spanId +
		// "'," + String.valueOf(level) + ",'" + prefix + "')\">aggiungi</a>";
		theReturn += "onclick=\"return addInstance(this)\">aggiungi daogrp</a>";
		return theReturn;
	}

	protected void generateCustomProcessinfo(JspWriter out, String superNodoCorrente, boolean noHTML) throws TransformerException, IOException {
		String theName = theXMLconf.valoreNodo(superNodoCorrente + "/@name");
		String prefix = theXMLconf.valoreNodo(superNodoCorrente + "/@prefix");
		String theHTMLextra = theXMLconf.valoreNodo(superNodoCorrente + "/@HTMLextra", false);
		String theActionValue = "modifica";
		String xpathCorrenteSpan = ((((prefix.replace('/', '.')).replace('\'', '_')).replaceAll("]", "-")).replace('[', '-')).replace(' ', '-').replace('@', '-').replace('=', '-').replace('(', '-').replace(')', '-');
		if (xpathCorrenteSpan.startsWith("."))
			xpathCorrenteSpan = xpathCorrenteSpan.substring(1);
		boolean inLine = false;
		if ((theXMLconf.valoreNodo(superNodoCorrente + "/@inline")).equals("yes"))
			inLine = true;
		out.println(part00multi);
		for (int p = 0; p < theXML.contaNodi(prefix) + 1; p++) {
			out.println("<span><span id=\"" + xpathCorrenteSpan + "." + (p) + "\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
			out.println(part01multiGroup);
			if (p == 0) {
				out.println(theName + "&#160;");
			} else {
				out.println("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >");
				if (inLine) {
					out.println(part02multiGroup);
				}
				for (int a = 0; a < theXMLconf.contaNodi(superNodoCorrente + "/elemento"); a++) {
					String nodoCorrente = superNodoCorrente + "/elemento[" + (a + 1) + "]";
					String xpathCorrente = theXMLconf.valoreNodo(nodoCorrente + "/text()");
					xpathCorrente = prefix + "[" + (p) + "]" + xpathCorrente.substring(xpathCorrente.indexOf(prefix) + prefix.length());
					String xpathCorrenteDot = xpathCorrente.replace('/', '.');

					theHTMLextra = theXMLconf.valoreNodo(nodoCorrente + "/@HTMLextra");

					theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
					if (!theXMLconf.valoreNodo(nodoCorrente + "/@actionValue").equals("")) {
						theActionValue = theXMLconf.valoreNodo(nodoCorrente + "/@actionValue");
					}

					if (!inLine) {
						out.println(part02multiGroup);
						out.println(theName + "&#160;");
						out.println(part03multiGroup);
					} else {
						if (inLine && a < 1) {
							out.println(theName + "&#160;");
							out.println(part03multiGroup);
						}
					}
					generaLiveText(xpathCorrente, xpathCorrenteDot, theHTMLextra, out);
					if (!inLine) {
						out.println("</td></tr>");
					}
				}
				if (inLine) {
					out.println("</td></tr>");
				}
				out.println("</table>");
			}
			out.println("</td>");
			if (p == 0) {
				out.println("<td class=\"showdocValue\" width=\"50\" align=\"right\"></td>");
			} else {
				out.println("<td class=\"showdocValue\" width=\"50\" align=\"right\"></td>");
			}
			out.println("</tr></table></span></span>");
		}
		String xpathCorrente = theXMLconf.valoreNodo(superNodoCorrente + "/elemento[1]/text()");
		theHTMLextra = theXMLconf.valoreNodo(superNodoCorrente + "/elemento[1]/@HTMLextra");
		String aggiungiAzione = (prefix + "[" + (theXML.contaNodi(prefix) + 1) + "]" + xpathCorrente.substring(xpathCorrente.indexOf(prefix) + prefix.length())).replace('/', '.');
		String javascriptAction = "<script type=\"text/javascript\">setResponsabile('" + theActionValue + "','" + aggiungiAzione + "','";
		out.println(generateInput("hidden", aggiungiAzione, "", "", "", theHTMLextra));
		xpathCorrente = theXMLconf.valoreNodo(superNodoCorrente + "/elemento[2]/text()");
		theHTMLextra = theXMLconf.valoreNodo(superNodoCorrente + "/elemento[2]/@HTMLextra");
		aggiungiAzione = (prefix + "[" + (theXML.contaNodi(prefix) + 1) + "]" + xpathCorrente.substring(xpathCorrente.indexOf(prefix) + prefix.length())).replace('/', '.');
		javascriptAction += aggiungiAzione + "','";
		out.println(generateInput("hidden", aggiungiAzione, "", "", "", theHTMLextra));
		xpathCorrente = theXMLconf.valoreNodo(superNodoCorrente + "/elemento[3]/text()");
		aggiungiAzione = (prefix + "[" + (theXML.contaNodi(prefix) + 1) + "]" + xpathCorrente.substring(xpathCorrente.indexOf(prefix) + prefix.length())).replace('/', '.');
		javascriptAction += aggiungiAzione + "','Attenzione:\\nnon e stato possibile reperire le informazioni relative all\\\'utente: aprire nuovamente la maschera di modifica')</script>";
		out.println(generateInput("hidden", aggiungiAzione, "", "", "", theHTMLextra));
		out.println(javascriptAction);
		/*
		 * out.println( "<script>setResponsabile('modifica','.c.processinfo.list.item[" + (theXML.contaNodi(prefix) + 1) + "].text()','.c.processinfo.list.item[" + (theXML.contaNodi(prefix) + 1) + "].date.text()','.c.processinfo.list.item[" + (theXML.contaNodi(prefix) + 1) +
		 * "].persname.text()','Attenzione:\\nnon e stato possibile reperire le informazioni relative all\\\'utente: aprire nuovamente la maschera di modifica')</script>" );
		 */
		out.println("</td></tr>");
	}

	private void generateCustomData(JspWriter out, String nodoCorrente, boolean noHTML, String theType) throws Exception {
		String theName = theXMLconf.valoreNodo(nodoCorrente + "/@name");
		String theCentury = theXMLconf.valoreNodo(nodoCorrente + "/@showCentury");
		String isRange = theXMLconf.valoreNodo(nodoCorrente + "/@range");
		String prefix = theXMLconf.valoreNodo(nodoCorrente + "/@prefix");
		String format = theXMLconf.valoreNodo(nodoCorrente + "/@format");
		String allowOnly = theXMLconf.valoreNodo(nodoCorrente + "/@allowOnly");
		String escapeSenzaData = theXMLconf.valoreNodo(nodoCorrente + "/@escapeSenzaData");
		String customNormal = theXMLconf.valoreNodo(nodoCorrente + "/@normal");
		if (customNormal.equals("")) {
			customNormal = "@normal";
		} else if (customNormal.startsWith("/")) {
			customNormal = customNormal.substring(customNormal.indexOf("/") + 1);
		}
		// System.out.println("FormGenerator.generateCustomData() customNormal " + customNormal);
		// System.out.println("FormGenerator.generateCustomData() customNormal " + customNormal);
		// System.out.println("FormGenerator.generateCustomData() customNormal " + customNormal);
		// System.out.println("FormGenerator.generateCustomData() customNormal " + customNormal);
		// System.out.println("FormGenerator.generateCustomData() customNormal " + customNormal);

		String prefixDot = prefix.replace('/', '.');
		String prefixDotSin = prefixDot.replaceAll("'", "\\\\'");
		if (noHTML) {
			out.println("</td><td> </td><td> </td></tr></table><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\"><tr>");
		}
		if (theType.equals("simple")) {

			out.println("<tr>");
			out.println("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" style=\"padding-left:5px\">componi data</td>" +

			"<td class=\"doceditTitoloElemento\"><input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"" +

			"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "',null,null,null,'" + prefixDotSin + ".text()','" + format + "');//valorizzaData();" +

			"\" type=\"text\" id=\"GIORNO_INI" + prefixDot + "\" name=\"GIORNO_INI" + prefixDot + "\" size=\"2\" maxlength=\"2\" value=\"\" />");

			out.println("/ <input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"" +

			"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "',null,null,null,'" + prefixDotSin + ".text()','" + format + "');//valorizzaData();" +

			"\" type=\"text\" id=\"MESE_INI" + prefixDot + "\" name=\"MESE_INI" + prefixDot + "\" size=\"2\" maxlength=\"2\" value=\"\" />");

			out.println("/ <input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"" +

			"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "',null,null,null,'" + prefixDotSin + ".text()','" + format + "');//valorizzaData();" +

			"\" type=\"text\" name=\"ANNO_INI" + prefixDot + "\" id=\"ANNO_INI" + prefixDot + "\" size=\"4\" maxlength=\"4\" value=\"\" /></td><td width=\"20\"><span style=\"font-size:1px\">&#160;</span></td>");

			out.println("</tr>");

			out.println("<tr>");
			out.print("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" style=\"padding-left:5px\">" + theName + "&#160;" + "</td><td><input type=\"text\" ");
			if (!allowOnly.equals("")) {
				out.println(" onkeypress=\"allowOnly(" + allowOnly + ");\" ");
			}
			out.println(" name=\"" + prefixDot + ".text()\" class=\"doceditInputLong\" value=\"" + theXML.valoreNodo(prefix + "/text()") + "\"></td><td><a class=\"doceditActionLink\" href=\"#n\" onclick=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI"
					+ prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "',null,null,null,'" + prefixDotSin + ".text()','" + format + "');\">applica</a></td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" width=\"30%\" style=\"padding-left:5px\">");
			out.println("forma normalizzata" + "&#160;");
			out.println("</td><td align=\"left\"><input readonly  type=\"text\" name=\"" + prefixDot + "." + customNormal + "\" class=\"doceditInputMiddle\" value=\"" + theXML.valoreNodo(prefix + "/" + customNormal + "") + "\"></td>");
			if (escapeSenzaData.equals("") || !escapeSenzaData.equalsIgnoreCase("true")) {
				out.println("<td align=\"right\"><a class=\"doceditActionLink\" href=\"#n\" onclick=\"return senzaData('" + prefixDotSin + "')\">senza&#160;data</a></td>");
			} else if (escapeSenzaData.equalsIgnoreCase("true")) {
				out.println("<td align=\"right\"></td>");
			}

			out.println("</tr>");

		} else {

			out.println("<tr>");
			out.println("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" style=\"padding-left:5px\">da</td><td class=\"doceditTitoloElemento\"><input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"return dataNormal('theForm','"
					+ prefixDotSin
					+ "."
					+ customNormal
					+ "','GIORNO_INI"
					+ prefixDotSin
					+ "','MESE_INI"
					+ prefixDotSin
					+ "','ANNO_INI"
					+ prefixDotSin
					+ "','GIORNO_FIN"
					+ prefixDotSin
					+ "','MESE_FIN"
					+ prefixDotSin
					+ "','ANNO_FIN"
					+ prefixDotSin
					+ "','"
					+ prefixDotSin
					+ ".text()','"
					+ prefixDotSin
					+ ".date[1].text()','" + format + "');//valorizzaData();\" type=\"text\" id=\"GIORNO_INI" + prefixDot + "\" name=\"GIORNO_INI" + prefixDot + "\" size=\"2\" maxlength=\"2\" value=\"\">");
			out.println("/ <input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "','GIORNO_FIN"
					+ prefixDotSin + "','MESE_FIN" + prefixDotSin + "','ANNO_FIN" + prefixDotSin + "','" + prefixDotSin + ".text()','" + prefixDotSin + ".date[1].text()','" + format + "');//valorizzaData();\" type=\"text\" id=\"MESE_INI" + prefixDot + "\" name=\"MESE_INI" + prefixDot
					+ "\" size=\"2\" maxlength=\"2\" value=\"\">");
			out.println("/ <input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "','GIORNO_FIN"
					+ prefixDotSin + "','MESE_FIN" + prefixDotSin + "','ANNO_FIN" + prefixDotSin + "','" + prefixDotSin + ".text()','" + prefixDotSin + ".date[1].text()','" + format + "');//valorizzaData();\" type=\"text\" name=\"ANNO_INI" + prefixDot + "\" id=\"ANNO_INI" + prefixDot
					+ "\" size=\"4\" maxlength=\"4\" value=\"\" /></td><td width=\"20\"><span style=\"font-size:1px\">&#160;</span></td>");
			out.println("</tr>");

			if (!isRange.equals("false")) {
				out.println("<tr>");
				out.println("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" style=\"padding-left:5px\">a</td><td class=\"doceditTitoloElemento\"><input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"return dataNormal('theForm','"
						+ prefixDotSin
						+ "."
						+ customNormal
						+ "','GIORNO_INI"
						+ prefixDotSin
						+ "','MESE_INI"
						+ prefixDotSin
						+ "','ANNO_INI"
						+ prefixDotSin
						+ "','GIORNO_FIN"
						+ prefixDotSin
						+ "','MESE_FIN"
						+ prefixDotSin
						+ "','ANNO_FIN"
						+ prefixDotSin
						+ "','"
						+ prefixDotSin
						+ ".text()','"
						+ prefixDotSin + ".date[1].text()','" + format + "');//valorizzaData();\" type=\"text\" id=\"GIORNO_FIN" + prefixDot + "\"  name=\"GIORNO_FIN" + prefixDot + "\" size=\"2\" maxlength=\"2\" value=\"\" />");
				out.println("/ <input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "','GIORNO_FIN"
						+ prefixDotSin + "','MESE_FIN" + prefixDotSin + "','ANNO_FIN" + prefixDotSin + "','" + prefixDotSin + ".text()','" + prefixDotSin + ".date[1].text()','" + format + "');//valorizzaData();\" type=\"text\" id=\"MESE_FIN" + prefixDot + "\" name=\"MESE_FIN" + prefixDot
						+ "\" size=\"2\" maxlength=\"2\" value=\"\">");
				out.println("/ <input class=\"doceditInputData\" onkeypress=\"allowOnlyNumbers()\" onchange=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "','GIORNO_FIN"
						+ prefixDotSin + "','MESE_FIN" + prefixDotSin + "','ANNO_FIN" + prefixDotSin + "','" + prefixDotSin + ".text()','" + prefixDotSin + ".date[1].text()','" + format + "');//valorizzaData();\" type=\"text\" name=\"ANNO_FIN" + prefixDot + "\" id=\"ANNO_FIN" + prefixDot
						+ "\" size=\"4\" maxlength=\"4\" value=\"\" /></td><td width=\"20\"><span style=\"font-size:1px\">&#160;</span>");
				if (!noHTML)
					out.println("</td></tr>");
			}

			if (!theCentury.equals("false")) {
				out.println("<tr>");
				out.println("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" style=\"padding-left:5px\">secolo</td>");
				out.println("<td><select onchange=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI" + prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "','GIORNO_FIN" + prefixDotSin + "','MESE_FIN" + prefixDotSin + "','ANNO_FIN"
						+ prefixDotSin + "','" + prefixDotSin + ".text()','" + prefixDotSin + ".date[1]','" + format + "');//valorizzaData();\"  name=\"" + prefixDot + ".date[1]\" class=\"doceditInput\">");
				String valoreCorrente = theXMLconf.valoreNodo(nodoCorrente + "/@value");
				String fullPath = valoreCorrente;
				if (valoreCorrente.indexOf("document:") == 0) {
					fullPath = valoreCorrente.replaceAll("document:", "");
				} else if (valoreCorrente.indexOf("ajax:") == 0) {
					fullPath = valoreCorrente.replaceAll("ajax:", "");
				}
				if (fullPath.equals("")) {
					fullPath = "valoriControllati.xml";
				}
				// com.regesta.dams.utility.xml.DomManager domManager = com.regesta.dams.utility.xml.DomManager.getInstance(isStatic, realPath);
				// XMLBuilder theXMLconfTMP = new XMLBuilder(domManager.getDocument(fullPath));
				System.out.println("333 fullPath " + fullPath);
				XMLBuilder theXMLconfTMP = ConfManager.getConfXML(fullPath);// new XMLBuilder(domManager.getDocument(fullPath));
				String externalPath = "/root/elemento[@name='secolo']/opzione";
				int nodiOpzioni = theXMLconfTMP.contaNodi(externalPath);
				String theValue = theXML.valoreNodo(prefix + "/date/text()");
				for (int a = 0; a < nodiOpzioni; a++) {
					String extra = "";
					if (theValue.equals(theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"))) {
						extra = "selected";
					}
					out.println(generateInput("option", theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/@value"), theXMLconfTMP.valoreNodo(externalPath + "[" + (a + 1) + "]/text()"), "", extra, ""));
				}
				out.println("</select></td><td width=\"20\"><span style=\"font-size:1px\">&#160;</span></td>");
				out.println("</tr>");
			}
			out.println("<tr>");
			out.print("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" style=\"padding-left:5px\">forma visualizzata</td><td><input type=\"text\" ");

			if (!allowOnly.equals("")) {
				out.println(" onkeypress=\"allowOnly(" + allowOnly + ");\" ");
			}

			out.println(" name=\"" + prefixDot + ".text()\" class=\"doceditInputLong\" value=\"" + theXML.valoreNodo(prefix + "/text()") + "\"></td><td><a class=\"doceditActionLink\" href=\"#n\" onclick=\"return dataNormal('theForm','" + prefixDotSin + "." + customNormal + "','GIORNO_INI"
					+ prefixDotSin + "','MESE_INI" + prefixDotSin + "','ANNO_INI" + prefixDotSin + "','GIORNO_FIN" + prefixDotSin + "','MESE_FIN" + prefixDotSin + "','ANNO_FIN" + prefixDotSin + "','" + prefixDotSin + ".text()','" + prefixDotSin + ".date[1]','" + format + "');\">applica</a></td>");
			out.println("</tr>");

			out.println("<tr>");
			out.println("<td width=\"4\" bgcolor=\"#B4B7BA\"><span style=\"font-size:1px\">&#160;</span></td><td class=\"doceditTitoloElemento\" width=\"30%\" style=\"padding-left:5px\">");
			out.println(theName + "&#160;");
			out.println("</td>");
			out.println("<td align=\"left\"><input readonly  type=\"text\" name=\"" + prefixDot + "." + customNormal + "\" class=\"doceditInputMiddle\" value=\"" + theXML.valoreNodo(prefix + "/" + customNormal + "") + "\"></td>");
			if (escapeSenzaData.equals("") || !escapeSenzaData.equalsIgnoreCase("true")) {
				out.println("<td><a class=\"doceditActionLink\" href=\"#n\" onclick=\"return senzaData('" + prefixDotSin + "')\">senza&#160;data</a></td>");
			} else if (escapeSenzaData.equalsIgnoreCase("true")) {
				out.println("<td></td>");
			}

			out.println("<td align=\"right\"></td>");
			out.println("</tr>");

		}
	}

	public int getPhysDoc() {
		return physDoc;
	}

	public void setPhysDoc(int physDoc) {
		this.physDoc = physDoc;
	}

}
