package org.xdams.adv.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.xdams.adv.configuration.Element;
import org.xdams.utility.reflection.ReflectionUtil;
import org.xdams.xml.builder.XMLBuilder;

public class MappingAdv {

	public List<Element> extractMapping(ArrayList<Element> arrayList, String prefix, Map<String, String> filters) throws Exception {
		List<Element> list = new ArrayList<Element>();
		try {
			// System.out.println(arrayList.size());
			if (arrayList != null) {
				for (int i = 0; i < arrayList.size(); i++) {
					if (arrayList.get(i) instanceof Element) {
						Element element = (Element) arrayList.get(i);
						if (element.getPrefix() != null) {
							String newPrefix = element.getPrefix();
							if (newPrefix.startsWith("/*")) {
								newPrefix = prefix + element.getPrefix().replaceAll("/\\*", "");
							}
							list.addAll(extractMapping(element.getElemets(), newPrefix, filters));
						} else {
							if (element.getText() != null && controlla(element, filters)) {
								list.add(extractElement(element, prefix));
							}
							if (element.getElemets() != null) {
								list.addAll(extractMapping(element.getElemets(), null, filters));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public boolean controlla(Element element, Map<String, String> filters) {
		ReflectionUtil reflectionUtil = new ReflectionUtil();
		boolean ritorno = true;
		for (Entry<String, String> entrySet : filters.entrySet()) {
			// System.out.println("MappingAdv.controlla() " + entrySet.getKey());
			// System.out.println("MappingAdv.controlla() " + entrySet.getValue());
			try {
				Object object = reflectionUtil.invokeMethod(element, "get" + StringUtils.capitalize(entrySet.getKey()));
				if (!entrySet.getValue().equals(object)) {
					// System.out.println("MappingAdv.controlla() VALUEEEE " + object);
					ritorno = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// System.out.println(ritorno);
		// System.out.println("######################################");
		return ritorno;
	}

	public Element extractElement(Element element, String prefix) throws Exception {
		String name = "";
		try {
			String fieldValue = "";
			String fieldName = "";
			// if (element.getDeep() == null || element.getXPathMapping() == null) {
			// System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°° ");
			// }
			// System.out.println("MainTest.extractValue() " + element.getDeep());
			// System.out.println("MainTest.extractValue() " + element.getXPathMapping());
			if (prefix != null) {
				String appo = "";
				try {
					// int count = 0;
					if (element.getText().startsWith("/*")) {
						appo = prefix + element.getText().replaceAll("/\\*", "");
						// qui è sbagliato devo togliere gli indici [xx] per creare un campo field
						fieldName = prefix + element.getText().replaceAll("/\\*", "");
						// count = 1;// xmlBuilder.contaNodi(appo);
						prefix = "";
						// prefix_count = "";
					} else {
						appo = element.getText().substring(element.getText().indexOf(prefix) + prefix.length(), element.getText().length());
						fieldName = element.getText();
						// count = 1;// xmlBuilder.contaNodi(appo);
					}
					name = prefix + appo;
					element.setText(name);
					// fieldValue = xmlBuilder.valoreNodo(prefix + prefix_count + appo.replaceAll("/text\\(\\)", "[" + (i + 1) + "]/text\\(\\)"));

					// if (element.getSearch_alias().equals("SGTD")) {
					// System.out.println("IF name " + name);
					// System.out.println("IF fieldName " + fieldName);
					// System.out.println("IF fieldValue " + fieldValue);
					// System.out.println("IF prefix " + prefix);
					// System.out.println("IF appo " + appo);
					// System.out.println("BuildXML.extractValue() " + element.getEmpty_key());
					// System.out.println("############################");
					// }

				} catch (Exception e) {
					System.out.println("Errore in text " + e.getMessage());
				}
			} else {
				name = element.getText();
				element.setText(name);
				// System.out.println("ELSE NAME " + name);
				// System.out.println("ELSE fieldName " + fieldName);
				// System.out.println("ELSE fieldValue " + fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return element;
	}

	public void buildXML(List<Element> arrayList, XMLBuilder builderRecord, String prefix, String prefix_count, GenericInterface<XMLBuilder> genericInterface) throws Exception {
		try {

			// System.out.println("buildXML " + arrayList.size());
			if (arrayList != null) {
				for (int i = 0; i < arrayList.size(); i++) {
					if (arrayList.get(i) instanceof Element) {
						Element element = (Element) arrayList.get(i);
						// System.out.println("bbb " + element.getText());
						if (element.getPrefix() != null) {
							String newPrefix = element.getPrefix();
							if (newPrefix.startsWith("/*")) {
								newPrefix = prefix + element.getPrefix().replaceAll("/\\*", prefix_count);
								// System.out.println("BuildXML.buildPage()" + (prefix + element.getPrefix()).replaceAll("/\\*", ""));
							}
							int count = builderRecord.contaNodi(newPrefix);
							if (count == 0)
								count = 1;
							for (int x = 0; x < count; x++) {
								buildXML(element.getElemets(), builderRecord, newPrefix, "[" + (x + 1) + "]", genericInterface);
							}
						} else {
							if (element.getText() != null) {
								// System.out.println("aaaa " + element.getPrefix());
								// System.out.println("aaaa " + prefix);
								// System.out.println("aaaa " + prefix_count);
								// qui creo il bean per fare Field
								extractValue(element, builderRecord, prefix, prefix_count, genericInterface);
							}
							if (element.getElemets() != null) {
								buildXML(element.getElemets(), builderRecord, null, null, genericInterface);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void extractValue(Element element, XMLBuilder xmlBuilder, String prefix, String prefix_count, GenericInterface genericInterface) throws Exception {
		try {
			String fieldValue = "";
			String fieldName = "";
			String name = "";
			if (prefix != null) {
				String appo = "";
				try {
					int count = 0;
					if (element.getText().startsWith("/*")) {
						appo = prefix + prefix_count + element.getText().replaceAll("/\\*", "");
						// qui è sbagliato devo togliere gli indici [xx] per creare un campo field
						fieldName = prefix + element.getText().replaceAll("/\\*", "");
						count = xmlBuilder.contaNodi(appo);
						prefix = "";
						prefix_count = "";
					} else {
						appo = element.getText().substring(element.getText().indexOf(prefix) + prefix.length(), element.getText().length());
						fieldName = element.getText();
						count = xmlBuilder.contaNodi(appo);
					}
					if (count == 0)
						count++;
					for (int i = 0; i < count; i++) {
						name = prefix + prefix_count + appo;
						fieldValue = xmlBuilder.valoreNodo(prefix + prefix_count + appo.replaceAll("/text\\(\\)", "[" + (i + 1) + "]/text\\(\\)"));
						element.setFieldValue(fieldValue);
						element.setFieldXPath(name);
						genericInterface.invoke(xmlBuilder, element);
					}
				} catch (Exception e) {
					System.out.println("Errore in text jsp" + e.getMessage());
				}
			} else {
				name = element.getText();
				fieldValue = xmlBuilder.valoreNodo(element.getText());
				element.setFieldValue(fieldValue);
				element.setFieldXPath(name);
				genericInterface.invoke(xmlBuilder, element);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
