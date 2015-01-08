package org.xdams.page.view.modeling;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.user.bean.Archive;
import org.xdams.user.bean.UserBean;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xml.builder.exception.XMLException;

public class QueryPageView {

	@Autowired
	ModelMap modelMap;
	
	private Map<String, List<Map<String, String>>> positionMap = new HashMap<String, List<Map<String, String>>>();

	private Map<String, List<Map<String, String>>> positionAdminMap = new HashMap<String, List<Map<String, String>>>();

	private List<String> outputHourField = new ArrayList<String>();

	private List<String> outputDataField = new ArrayList<String>();

	private List<String> outputSortField = new ArrayList<String>();

	public void generateView(WorkFlowBean workFlowBean, ConfBean confBean, UserBean userBean,ModelMap modelMap) throws UnsupportedEncodingException, TransformerException, XMLException {
		XMLBuilder theXMLconf = confBean.getTheXMLConfQuery();
		//System.out.println("QueryPageView.generateView() "+theXMLconf.getXML("ISO-8859-1"));
		String perPageNew = "10";
		// INIZIO /query/element per i dizionari e ric libera
		String prefix = "/root/query/element";
		Map<String, String> hashOutputField = null;

		List<Map<String, String>> arrLabel = new ArrayList<Map<String, String>>();
		int numField = theXMLconf.contaNodi(prefix);
		for (int i = 0; i < numField; i++) {
			hashOutputField = new HashMap<String, String>();
			String positionDiv = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@position");
			String attrLabel = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@label");
			String attrFirstIdx = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@firstIdx");
			String attrTypology = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@typology");
			String attrExternalPath = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@externalPath");
			String attrValue = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@value");
			String activeIdx = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@activeIdx");
			String filteredKey = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@filteredKey");
			String filteredNumDocs = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@filteredNumDocs");
			String ajaxKey = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@ajaxKey");
			String nameValue = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/text()");
			String genericFind = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@genericFind");
			String selectAddEmptyValue = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@addEmptyValue");
			if (genericFind.equals("true")) {
				continue;
			}
			String outputField = "";
			String nameValueEncoded = URLEncoder.encode(nameValue, "iso-8859-1").replaceAll("%", "").replaceAll("\\*", "");
			if (attrTypology.equals("radio")) {
				String ilValoreCorrente = attrValue;
				XMLBuilder theXMLconfTMP = confBean.getTheXMLValControllati();
				String thisExternalPath = attrExternalPath;
				int nodiOpzioni = theXMLconfTMP.contaNodi(thisExternalPath);
				if (theXMLconfTMP.contaNodi(thisExternalPath + "[@value = '']") == 0 && (!selectAddEmptyValue.equals("") && selectAddEmptyValue.equals("yes"))) {
					outputField = "<input type=\"radio\" name=\"[" + nameValue + "]\" value=\"\" checked=\"true\" />"+workFlowBean.getLocalizedString("nessun_valore","nessun valore");
				}
				for (int a = 0; a < nodiOpzioni; a++) {
					outputField += "<input type=\"radio\" name=\"[" + nameValue + "]\" value=\"" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/@value") + "\" />" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/text()").replaceAll(" ", "&#160;");
				}
			} else if (attrTypology.equals("select")) {
				String ilValoreCorrente = attrValue;
				XMLBuilder theXMLconfTMP = confBean.getTheXMLValControllati();
				String thisExternalPath = attrExternalPath;
				int nodiOpzioni = theXMLconfTMP.contaNodi(thisExternalPath);
				outputField = "<select class=\"text\" name=\"[" + nameValue + "]\" id=\"" + nameValueEncoded + "\">";
				if (theXMLconfTMP.contaNodi(thisExternalPath + "[@value = '']") == 0 && (!selectAddEmptyValue.equals("") && selectAddEmptyValue.equals("yes"))) {
					outputField += "<option value=\"\" selected=\"true\">"+workFlowBean.getLocalizedString("nessun_valore","nessun valore")+"</option>";
				}
				for (int a = 0; a < nodiOpzioni; a++) {
					outputField += "<option  value=\"" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/@value") + "\">" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/text()").replaceAll(" ", "&#160;") + "</option>";
				}
				outputField += "</select>";
			} else if (attrTypology.equals("hidden")) {
				outputField = "<input name=\"[" + nameValue + "]\" type=\"hidden\" class=\"long\" id=\"" + nameValueEncoded + "\" value=\"" + attrValue + "\"/>";
			} else if (attrTypology.equals("wrap")) {
				String prefixSub = prefix + "[" + (i + 1) + "]/element";
				int numFieldSub = theXMLconf.contaNodi(prefixSub);
				outputField = "<div id=\"" + URLEncoder.encode(prefixSub, "iso-8859-1").replaceAll("%", "") + "\"><select onchange=\"return showInput(this.value)\" class=\"text\" style=\"float:left\"><option value=" + URLEncoder.encode(prefixSub, "iso-8859-1").replaceAll("%", "")
						+ " selected=\"true\">"+workFlowBean.getLocalizedString("scegli","scegli")+"...</option>";
				for (int a = 0; a < numFieldSub; a++) {
					outputField += "<option value=\"" + URLEncoder.encode(prefixSub + (a + 1), "iso-8859-1").replaceAll("%", "") + "\">" + theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@label") + "</option>";
				}
				outputField += "</select>";
				for (int a = 0; a < numFieldSub; a++) {
					attrFirstIdx = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@firstIdx");
					attrTypology = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@typology");
					attrExternalPath = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@externalPath");
					attrValue = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@value");
					activeIdx = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@activeIdx");
					filteredKey = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@filteredKey");
					filteredNumDocs = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@filteredNumDocs");
					ajaxKey = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/@ajaxKey");
					nameValue = theXMLconf.valoreNodo(prefixSub + "[" + (a + 1) + "]/text()");
					nameValueEncoded = URLEncoder.encode(nameValue, "iso-8859-1").replaceAll("%", "").replaceAll("\\*", "");
					if ((activeIdx.equals("yes") || ajaxKey.equals("yes"))) {
						String ajaxStr = "";
						if (ajaxKey.equals("yes")) {
							String idJs = nameValueEncoded;
							idJs = idJs.replaceAll("%", "");
							idJs = idJs.replaceAll(",", "");
							idJs = idJs.replaceAll("-", "");
							String attrTypologyAjax = attrTypology.equals("double") ? "doubleQuery" : attrTypology;
							outputField += "<div class=\"queryEleSub\" id=\"" + URLEncoder.encode(prefixSub + (a + 1), "iso-8859-1").replaceAll("%", "") + "\"><input name=\"[" + nameValue + "]\" type=\"text\" class=\"long complete\" id=\"" + nameValueEncoded
									+ "\" servlet=\""+modelMap.get("contextPath")+"/"+workFlowBean.getAlias()+"/ajax.html?actionFlag=vocabolarioJson&key=" + nameValue + "&typology=" + attrTypologyAjax + "&attrFirstIdx=" + attrFirstIdx + "&numKeys=20\" />";
							if (activeIdx.equals("yes")) {
								outputField += "<a title=\"accedi al dizionario del campo\" href=\"#n\" onclick=\"return apriIdx(document.theForm['[" + nameValue + "]'],'" + confBean.getKeyCountIDX() + "','" + workFlowBean.getArchive().getAlias() + "','" + nameValue + "','"
										+ attrTypology.replaceAll("Query", "").replaceAll("Reverse", "") + "','" + filteredKey + "','" + attrFirstIdx + "','" + filteredNumDocs + "');\"><img src=\"" + modelMap.get("frontUrl")
										+ "/img/spacer.gif\" class=\"diz\" border=\"0\" alt=\""+workFlowBean.getLocalizedString("apri_dizionario_di_campo","apri dizionario di campo")+"\" /></a>";
							}
							outputField += "</div>";
						} else {
							outputField += "<div class=\"queryEleSub\" id=\"" + URLEncoder.encode(prefixSub + (a + 1), "iso-8859-1").replaceAll("%", "") + "\"><input name=\"[" + nameValue + "]\" type=\"text\" class=\"long\" id=\"" + nameValueEncoded + "\" " + ajaxStr
									+ "/><a title=\"accedi al dizionario del campo\" href=\"#n\" onclick=\"return apriIdx(document.theForm['[" + nameValue + "]'],'" + confBean.getKeyCountIDX() + "','" + workFlowBean.getArchive().getAlias() + "','" + nameValue + "','"
									+ attrTypology.replaceAll("Query", "").replaceAll("Reverse", "") + "','" + filteredKey + "','" + attrFirstIdx + "','" + filteredNumDocs + "');\"><img src=\"" + modelMap.get("frontUrl")
									+ "/img/spacer.gif\" class=\"diz\" border=\"0\" alt=\""+workFlowBean.getLocalizedString("apri_dizionario_di_campo","apri dizionario di campo")+"\" /></a></div>";
						}
					} else {
						outputField += "<div class=\"queryEleSub\" id=\"" + URLEncoder.encode(prefixSub + (a + 1), "iso-8859-1").replaceAll("%", "") + "\"><input name=\"[" + nameValue + "]\" type=\"text\" class=\"long\" id=\"" + nameValueEncoded + "\" /></div>";
					}
				}
				outputField += "</div>";
			} else {
				if (!activeIdx.equals("") && activeIdx.equals("yes")) {
					String ajaxStr = "";
					if (ajaxKey.equals("yes") || ajaxKey.equals("yes")) {
						String idJs = nameValueEncoded;
						idJs = idJs.replaceAll("%", "");
						idJs = idJs.replaceAll(",", "");
						idJs = idJs.replaceAll("-", "");
						String attrTypologyAjax = attrTypology.equals("double") ? "doubleQuery" : attrTypology;
						outputField += "<input name=\"[" + nameValue + "]\" type=\"text\" class=\"long complete\" id=\"" + nameValueEncoded + "\" servlet=\""+modelMap.get("contextPath")+"/"+workFlowBean.getAlias()+"/ajax.html?actionFlag=vocabolarioJson&key=" + nameValue + "&typology=" + attrTypologyAjax + "&attrFirstIdx=" + attrFirstIdx
								+ "&numKeys=20\" />";
						if (activeIdx.equals("yes")) {
							outputField += "<a title=\"accedi al dizionario del campo\" href=\"#n\" onclick=\"return apriIdx(document.theForm['[" + nameValue + "]'],'" + confBean.getKeyCountIDX() + "','" + workFlowBean.getArchive().getAlias() + "','" + nameValue + "','"
									+ attrTypology.replaceAll("Query", "").replaceAll("Reverse", "") + "','" + filteredKey + "','" + attrFirstIdx + "','" + filteredNumDocs + "');\"><img src=\"" + modelMap.get("frontUrl")
									+ "/img/spacer.gif\" class=\"diz\" border=\"0\" alt=\""+workFlowBean.getLocalizedString("apri_dizionario_di_campo","apri dizionario di campo")+"\" /></a>";
						}
					} else {
						outputField = "<input name=\"[" + nameValue + "]\" type=\"text\" class=\"long\" id=\"" + nameValueEncoded + "\" " + ajaxStr + "/><a title=\"accedi al dizionario del campo\" href=\"#n\" onclick=\"return apriIdx(document.theForm['[" + nameValue + "]'],'"
								+ confBean.getKeyCountIDX() + "','" + workFlowBean.getArchive().getAlias() + "','" + nameValue + "','" + attrTypology.replaceAll("Query", "").replaceAll("Reverse", "") + "','" + filteredKey + "','" + attrFirstIdx + "','" + filteredNumDocs + "');\"><img src=\""
								+ modelMap.get("frontUrl") + "/img/spacer.gif\" class=\"diz\" border=\"0\" alt=\""+workFlowBean.getLocalizedString("apri_dizionario_di_campo","apri dizionario di campo")+"\" /></a>";
					}
				} else {
					outputField = "<input name=\"[" + nameValue + "]\" type=\"text\" class=\"long\" id=\"" + nameValueEncoded + "\" />";
				}
			}

			hashOutputField.put(attrLabel, outputField);
			if (getPositionMap().containsKey(positionDiv)) {
				arrLabel = (ArrayList<Map<String, String>>) getPositionMap().get(positionDiv);
				arrLabel.add(hashOutputField);
				getPositionMap().put(positionDiv, arrLabel);
			} else {
				arrLabel = new ArrayList<Map<String, String>>();
				arrLabel.add(hashOutputField);
				getPositionMap().put(positionDiv, arrLabel);
			}

		}// end for master
			// FINE /query/element per i dizionari e ric libera

		// INIZO DATE /root/query/data/element
		prefix = "/root/query/data/element";
		numField = theXMLconf.contaNodi(prefix);

		for (int z = 0; z < numField; z++) {
			String outputField = "";
			String nameValue = theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/text()");
			String attrLabel = theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/@label");
			String nameValueEncoded = URLEncoder.encode(nameValue, "iso-8859-1").replaceAll("%", "").replaceAll("\\*", "");
			outputField += "<div class=\"ml20mt30\"><label>" + attrLabel + "</label></div>\n";
			outputField += "<div class=\"ml20\"><input type=\"text\" onblur=\"return findDate(document.theForm,'normalize',this,'day')\" class=\"data\" size=\"3\" name=\"giorno_ini\" maxlength=\"2\" />&#160;/&#160;<input type=\"text\" onblur=\"return findDate(document.theForm,'normalize',this,'month')\" class=\"data\"  size=\"3\" name=\"mese_ini\" maxlength=\"2\" />&#160;/&#160;<input type=\"text\" onblur=\"return findDate(document.theForm,'normalize',this,'year')\" class=\"data\" name=\"anno_ini\" maxlength=\"4\" />&#160;-&#160;<input type=\"text\" onblur=\"return findDate(document.theForm,'normalize',this,'day')\" class=\"data\"  size=\"3\"  name=\"giorno_fin\" maxlength=\"2\" />&#160;/&#160;<input type=\"text\" onblur=\"return findDate(document.theForm,'normalize',this,'month')\" class=\"data\"  size=\"3\" name=\"mese_fin\" maxlength=\"2\" />&#160;/&#160;<input type=\"text\" onblur=\"return findDate(document.theForm,'normalize',this,'year')\" class=\"data\" name=\"anno_fin\" maxlength=\"4\" />&#160;&#160;&#160;<input type=\"checkbox\" name=\"dataEsatta\" />"+workFlowBean.getLocalizedString("esatta","esatta")+"</div>\n";
			getOutputDataField().add(outputField);
		}
		prefix = "/root/query/hour/element";
		numField = theXMLconf.contaNodi(prefix);
		for (int z = 0; z < numField; z++) {
			String outputField = "";
			String nameValue = theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/text()");
			String attrLabel = theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/@label");
			String nameValueEncoded = URLEncoder.encode(nameValue, "iso-8859-1").replaceAll("%", "").replaceAll("\\*", "");
			outputField += "<div class=\"ml20mt30\"><label>" + attrLabel + "</label></div>\n";
			outputField += "<div class=\"ml20\"><input type=\"hidden\" name=\"[" + nameValue + "]\" title=\"[" + nameValue
					+ "]\"/><input type=\"text\" class=\"data\" size=\"2\" name=\"ora_ini\" maxlength=\"2\" onblur=\"return oraNormal(document.theForm['ora_ini'],document.theForm['ora_fine'],document.theForm['[" + nameValue
					+ "]'])\"/> <input type=\"text\" class=\"data\" name=\"ora_fine\" maxlength=\"2\" size=\"2\" onblur=\"return oraNormal(document.theForm['ora_ini'],document.theForm['ora_fine'],document.theForm['[" + nameValue + "]'])\"/> </div>\n";
			getOutputHourField().add(outputField);
		}
		// INIZIO ORDINAMENTO
		prefix = "/root/query/sort/element";
		numField = theXMLconf.contaNodi(prefix);
		String strChecked = "";
		for (int z = 0; z < numField; z++) {
			String outputField = "";
			if (z == 0) {
				outputField = "<div class=\"ml20mt30\"><label>"+workFlowBean.getLocalizedString("Criteri_di_ordinamento","Criteri di ordinamento")+":</label></div>";
				outputField += "<div class=\"ml20\"><select class=\"text\" onchange=\"return cambiaCriterio(this.value,document.theForm)\" name=\"ordinaCrescenteDecsrescente\"><option value=\"XML\">"+workFlowBean.getLocalizedString("crescente","crescente")+"</option><option value=\"xml\">"+workFlowBean.getLocalizedString("decrescente","decrescente")+"</option></select>";
			}
			strChecked = "";
			if ((theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/@checked").equals("yes"))) {
				strChecked = "checked=\"checked\"";
			}

			String nameValue = theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/text()");
			String attrLabel = theXMLconf.valoreNodo(prefix + "[" + (z + 1) + "]/@label");
			outputField += "<input type=\"radio\" class=\"ml15\" name=\"sorting\" " + strChecked + " value=\"" + nameValue + "\" />" + attrLabel;
			if (numField - 1 == z) {
				outputField += "</div>";
			}
			getOutputSortField().add(outputField);
		}
		// FINE ORIDINAMENTO

		// INIZIO /query/element per i dizionari AMMINISTRATIVI
		Map<String, String> hashOutputFieldAdm = null;
		List<Map<String, String>> arrLabelAdm = new ArrayList<Map<String, String>>();
		if (userBean.getRole().equals("ROLE_ADMIN")) {
			prefix = "/root/queryAdm/element";
			numField = theXMLconf.contaNodi(prefix);
			for (int i = 0; i < numField; i++) {
				hashOutputFieldAdm = new HashMap<String, String>();
				String positionDiv = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@position");
				String attrLabel = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@label");
				String attrFirstIdx = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@firstIdx");
				String attrTypology = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@typology");
				String attrExternalPath = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@externalPath");
				String attrValue = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@value");
				String activeIdx = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@activeIdx");
				String nameValue = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/text()");
				String filteredKey = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@filteredKey");
				String filteredNumDocs = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@filteredNumDocs");
				String ajaxKey = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@ajaxKey");
				String selectAddEmptyValue = theXMLconf.valoreNodo(prefix + "[" + (i + 1) + "]/@addEmptyValue");
				String outputField = "";
				String nameValueEncoded = URLEncoder.encode(nameValue, "iso-8859-1").replaceAll("%", "").replaceAll("\\*", "");
				if (attrTypology.equals("radio")) {
					String ilValoreCorrente = attrValue;
					XMLBuilder theXMLconfTMP = confBean.getTheXMLValControllati();
					String thisExternalPath = attrExternalPath;
					int nodiOpzioni = theXMLconfTMP.contaNodi(thisExternalPath);
					if (theXMLconfTMP.contaNodi(thisExternalPath + "[@value = '']") == 0 && (!selectAddEmptyValue.equals("") && selectAddEmptyValue.equals("yes"))) {
						outputField = "<input type=\"radio\" name=\"[" + nameValue + "]\" value=\"\" checked=\"true\" >"+workFlowBean.getLocalizedString("nessun_valore","nessun valore");
					}
					for (int a = 0; a < nodiOpzioni; a++) {
						outputField += "<input type=\"radio\" name=\"[" + nameValue + "]\" value=\"" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/@value") + "\">" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/text()").replaceAll(" ", "&#160;");
					}
				} else if (attrTypology.equals("select")) {
					String ilValoreCorrente = attrValue;
					XMLBuilder theXMLconfTMP = confBean.getTheXMLValControllati();
					String thisExternalPath = attrExternalPath;
					int nodiOpzioni = theXMLconfTMP.contaNodi(thisExternalPath);
					outputField = "<select class=\"text\" name=\"[" + nameValue + "]\">";
					if (theXMLconfTMP.contaNodi(thisExternalPath + "[@value = '']") == 0 && (!selectAddEmptyValue.equals("") && selectAddEmptyValue.equals("yes"))) {
						outputField += "<option value=\"\" selected=\"true\">"+workFlowBean.getLocalizedString("nessun_valore","nessun valore")+"</option>";
					}
					for (int a = 0; a < nodiOpzioni; a++) {
						outputField += "<option  value=\"" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/@value") + "\">" + theXMLconfTMP.valoreNodo(thisExternalPath + "[" + (a + 1) + "]/text()").replaceAll(" ", "&#160;") + "</option>";
					}
					outputField += "</select>";
				} else if (attrTypology.equals("hidden")) {
					outputField = "<input name=\"[" + nameValue + "]\" type=\"hidden\" class=\"long\" id=\"" + nameValueEncoded + "\" value=\"" + attrValue + "\"/>";
				} else {
					if (!activeIdx.equals("") && activeIdx.equals("yes")) {
						String ajaxStr = "";
						if (ajaxKey.equals("yes")) {
							String idJs = nameValueEncoded;
							idJs = idJs.replaceAll("%", "");
							idJs = idJs.replaceAll(",", "");
							String attrTypologyAjax = attrTypology.equals("double") ? "doubleQuery" : attrTypology;
							outputField += "<input name=\"[" + nameValue + "]\" type=\"text\" class=\"long complete\" id=\"" + nameValueEncoded + "\"  servlet=\""+modelMap.get("contextPath")+"/"+workFlowBean.getAlias()+"/ajax.html?actionFlag=vocabolarioJson&key=" + nameValue + "&typology=" + attrTypologyAjax + "&attrFirstIdx=" + attrFirstIdx
									+ "&numKeys=20\" /><a title=\"accedi al dizionario del campo\" href=\"#n\" onclick=\"return apriIdx(document.theForm['[" + nameValue + "]'],'" + confBean.getKeyCountIDX() + "','" + workFlowBean.getArchive().getAlias() + "','" + nameValue + "','"
									+ attrTypology.replaceAll("Query", "").replaceAll("Reverse", "") + "','" + filteredKey + "','" + attrFirstIdx + "','" + filteredNumDocs + "');\"><img src=\"" + modelMap.get("frontUrl")
									+ "/img/spacer.gif\" class=\"diz\" border=\"0\" alt=\""+workFlowBean.getLocalizedString("apri_dizionario_di_campo","apri dizionario di campo")+"\" /></a>";
						} else {
							outputField = "<input name=\"[" + nameValue + "]\" type=\"text\" class=\"long\" id=\"" + nameValueEncoded + "\" " + ajaxStr + "/><a title=\"accedi al dizionario del campo\" href=\"#n\" onclick=\"return apriIdx(document.theForm['[" + nameValue + "]'],'"
									+ confBean.getKeyCountIDX() + "','" + workFlowBean.getArchive().getAlias() + "','" + nameValue + "','" + attrTypology.replaceAll("Query", "").replaceAll("Reverse", "") + "','" + filteredKey + "','" + attrFirstIdx + "','" + filteredNumDocs + "');\"><img src=\""
									+ modelMap.get("frontUrl") + "/img/spacer.gif\" class=\"diz\" border=\"0\" alt=\""+workFlowBean.getLocalizedString("apri_dizionario_di_campo","apri dizionario di campo")+"\"/></a>";

						}
					} else {
						outputField = "<input name=\"[" + nameValue + "]\" type=\"text\" class=\"long\" id=\"" + nameValueEncoded + "\">";
					}
				}
				hashOutputFieldAdm.put(attrLabel, outputField);
				if (getPositionAdminMap().containsKey(positionDiv)) {
					arrLabelAdm = (ArrayList<Map<String, String>>) getPositionAdminMap().get(positionDiv);
					arrLabelAdm.add(hashOutputFieldAdm);
					getPositionAdminMap().put(positionDiv, arrLabelAdm);
				} else {
					arrLabelAdm = new ArrayList<Map<String, String>>();
					arrLabelAdm.add(hashOutputFieldAdm);
					getPositionAdminMap().put(positionDiv, arrLabelAdm);
				}
				// System.out.println(hashPositionAdm);
			}// end for master
		} // INIZIO /query/element per i dizionari AMMINISTRATIVI
	}

	/**
	 * @return the positionMap
	 */
	public Map<String, List<Map<String, String>>> getPositionMap() {
		return positionMap;
	}

	/**
	 * @param positionMap
	 *            the positionMap to set
	 */
	public void setPositionMap(Map<String, List<Map<String, String>>> positionMap) {
		this.positionMap = positionMap;
	}

	/**
	 * @return the positionAdminMap
	 */
	public Map<String, List<Map<String, String>>> getPositionAdminMap() {
		return positionAdminMap;
	}

	/**
	 * @param positionAdminMap
	 *            the positionAdminMap to set
	 */
	public void setPositionAdminMap(Map<String, List<Map<String, String>>> positionAdminMap) {
		this.positionAdminMap = positionAdminMap;
	}

	/**
	 * @return the outputHourField
	 */
	public List<String> getOutputHourField() {
		return outputHourField;
	}

	/**
	 * @param outputHourField
	 *            the outputHourField to set
	 */
	public void setOutputHourField(List<String> outputHourField) {
		this.outputHourField = outputHourField;
	}

	/**
	 * @return the outputDataField
	 */
	public List<String> getOutputDataField() {
		return outputDataField;
	}

	/**
	 * @param outputDataField
	 *            the outputDataField to set
	 */
	public void setOutputDataField(List<String> outputDataField) {
		this.outputDataField = outputDataField;
	}

	/**
	 * @return the outputSortField
	 */
	public List<String> getOutputSortField() {
		return outputSortField;
	}

	/**
	 * @param outputSortField
	 *            the outputSortField to set
	 */
	public void setOutputSortField(List<String> outputSortField) {
		this.outputSortField = outputSortField;
	}
}
