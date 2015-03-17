package org.xdams.managing.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.xdams.adv.configuration.ConfigurationXMLReader;
import org.xdams.adv.configuration.Element;
import org.xdams.adv.utility.MappingAdv;
import org.xdams.adv.utility.GenericInterface;
import org.xdams.xml.builder.XMLBuilder;

public class BuildElementToFindBean {

	public Map<String, List<ElementToFindBean>> buildElementToFindBean(XMLBuilder theXMLconf, String theArch) {
		Map<String, List<ElementToFindBean>> mapDbToFind = new LinkedHashMap<String, List<ElementToFindBean>>();
		List<ElementToFindBean> arrElementToFind = new ArrayList<ElementToFindBean>();
		try {
			String fixXpath = "/root/connectedArchives/masterArchive[@name='" + theArch + "']";
			int controlMaster = theXMLconf.contaNodi(fixXpath);
			// System.out.println("controlMaster "+controlMaster+"<br>");
			if (controlMaster > 1) {
				throw new Exception("masterArchive[@name='" + theArch + "'] multiplo");
			}
			int countWorkArchive = theXMLconf.contaNodi(fixXpath + "/workArchive");
			for (int x = 0; x < countWorkArchive; x++) {
				String dbToFindName = theXMLconf.valoreNodo(fixXpath + "/workArchive[" + (x + 1) + "]/@name");
				String[] dbToFindSplited = dbToFindName.split(",");

				for (String dbToFind : dbToFindSplited) {

					if (mapDbToFind.containsKey(dbToFind)) {
						throw new Exception(fixXpath + "/workArchive[" + (x + 1) + "]/@name \"" + dbToFind + "\" multiplo");
					}
//					System.out.println("dbToFind " + dbToFind + "<br>");
					// is multi elementToFind
					int countElementToFind = theXMLconf.contaNodi(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind");
					int countElementAdv = theXMLconf.contaNodi(fixXpath + "/workArchive[" + (x + 1) + "]/root/element");

//					System.out.println("BuildElementToFindBean.buildElementToFindBean() countElementToFind " + countElementToFind);
//					System.out.println("BuildElementToFindBean.buildElementToFindBean() countElementAdv " + countElementAdv);

					// System.out.println("countElementToFind "+countElementToFind+"<br>");
					if (countElementToFind == 0 && countElementAdv == 0) {
						throw new Exception(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind" + " non puo essere uguale a 0 (zero)");
					}
					for (int z = 0; z < countElementToFind; z++) {
						ElementToFindBean elementToFindBean = new ElementToFindBean();
						String strQuery = theXMLconf.valoreNodo(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@query");
						if (strQuery.equals("")) {
							throw new Exception(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@query" + " obbligatorio");
						}
						elementToFindBean.setStrQuery(strQuery);

//						System.out.println("strQuery " + strQuery + "<br>");
						String strPrefix = theXMLconf.valoreNodo(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@prefix");
						if (strPrefix.equals("")) {
							throw new Exception(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@prefix" + " obbligatorio");
						}
						elementToFindBean.setStrPrefix(strPrefix);
//						System.out.println("strPrefix " + strPrefix + "<br>");
						String strCode = theXMLconf.valoreNodo(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@code");
						if (strCode.equals("")) {
							throw new Exception(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@code" + " obbligatorio");
						}
						elementToFindBean.setStrCode(strCode);
//						System.out.println("strCode " + strCode + "<br>");
						String xPathToChange = theXMLconf.valoreNodo(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/text()");
						if (xPathToChange.equals("")) {
							throw new Exception(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/text()" + " obbligatorio");
						}
						elementToFindBean.setXPathToChange(xPathToChange);
//						System.out.println("xPathToChange " + xPathToChange + "<br>");
						String isTextNode = theXMLconf.valoreNodo(fixXpath + "/workArchive[" + (x + 1) + "]/elementToFind[" + (z + 1) + "]/@isTextNode");
						elementToFindBean.setIsTextNode(isTextNode);
//						System.out.println("isTextNode " + isTextNode + "<br>");

						elementToFindBean.setAlias(dbToFind);
						
						arrElementToFind.add(elementToFindBean);
					}
					for (int z = 0; z < countElementAdv; z++) {
						Node node = theXMLconf.getSingleNode(fixXpath + "/workArchive[" + (x + 1) + "]/root");
//						System.out.println(theXMLconf.getXMLFromNode(node, "ISO-8859-1"));
						final ConfigurationXMLReader configurationXMLReader = new ConfigurationXMLReader(theXMLconf.getXMLFromNode(node, "ISO-8859-1"));
//						System.out.println(configurationXMLReader.getObjects());
						MappingAdv mappingAdv = new MappingAdv();
						List<Element> list = mappingAdv.extractMapping(configurationXMLReader.getObjects(), null, new HashMap<String, String>());
						ArrayList<Element> arrayList = new ArrayList<Element>(list);
						for (Element element : arrayList) {
							final ElementToFindBean elementToFindBean = new ElementToFindBean();
							List<Element> arrayListA = new ArrayList<Element>();
							arrayListA.add(element);
							mappingAdv.buildXML(arrayListA, theXMLconf, null, null, new GenericInterface<XMLBuilder>() {
								public void invoke(XMLBuilder builder, Element element) {
									try {
//										System.out.println(element.getFieldXPath());
//										System.out.println(element.getFieldValue());
//										System.out.println(element.getQuery());
//										System.out.println(element.getPrefix());
										elementToFindBean.setStrQuery(element.getQuery());
										elementToFindBean.setAdvEditing(true);
										elementToFindBean.setConfigurationXMLReader(configurationXMLReader);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
							arrElementToFind.add(elementToFindBean);
						}
					}
					mapDbToFind.put(dbToFind, arrElementToFind);
					arrElementToFind = new ArrayList<ElementToFindBean>();
//					System.out.println("####################################################################");
				}
			}
		} catch (Exception e) {
			mapDbToFind.put("", new ArrayList<ElementToFindBean>());
		}

		return mapDbToFind;

	}
}
