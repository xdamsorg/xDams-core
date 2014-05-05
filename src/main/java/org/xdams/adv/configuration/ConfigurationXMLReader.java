package org.xdams.adv.configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xdams.xml.builder.XMLBuilder;
import org.xdams.xml.builder.exception.XMLException;
import org.xml.sax.SAXException;

public class ConfigurationXMLReader {

	private Document document = null;

	private ArrayList objects = null;

	public HashMap hashMap = null;

	public HashMap hashMapTree = null;

	public HashMap<String, Element> hashMapObj = null;

	public ConfigurationXMLReader(File xml) throws ParserConfigurationException, SAXException, IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		document = builder.parse(xml);
		read(document.getFirstChild(), null, null, null);
	}

	public ConfigurationXMLReader(XMLBuilder xml) throws ParserConfigurationException, SAXException, IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, XMLException {
		read(xml.getDomDocument().getFirstChild(), null, null, null);
	}

	public ConfigurationXMLReader(String xml) throws ParserConfigurationException, SAXException, IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		document = builder.parse(input);
		read(document.getFirstChild(), null, null, null);
	}

	public ConfigurationXMLReader(InputStream input) throws ParserConfigurationException, SAXException, IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		document = builder.parse(input);
		read(document.getFirstChild(), null, null, null);
	}

	public ConfigurationXMLReader(byte[] xml) throws ParserConfigurationException, SAXException, IOException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		ByteArrayInputStream input = new ByteArrayInputStream(xml);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		document = builder.parse(input);
		read(document.getFirstChild(), null, null, null);
	}

	private void read(Node node, Class cParam, Object objectParam, String deepFather) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		NodeList nodeList = node.getChildNodes();
		int gap = 0;
		if (nodeList != null && nodeList.getLength() > 0) {
			if (hashMap == null) {
				hashMap = new HashMap();
				hashMapTree = new HashMap<String, String>();
				hashMapObj = new HashMap<String, Element>();
			}
			for (int i = 0; i < nodeList.getLength(); i++) {
				Class constructorParamDef[] = {};
				Object constructorParam[] = {};
				Class c = null;
				Object object = null;
				if (nodeList.item(i).getNodeType() != Node.TEXT_NODE && nodeList.item(i).getNodeType() != Node.COMMENT_NODE) {
					c = Class.forName(this.getClass().getPackage().toString().replaceAll("/", ".").replaceAll("package ", "") + "." + nodeList.item(i).getNodeName().replaceFirst(nodeList.item(i).getNodeName().substring(0, 1), nodeList.item(i).getNodeName().substring(0, 1).toUpperCase()));
					Constructor theConstructor = c.getConstructor(constructorParamDef);
					object = theConstructor.newInstance(constructorParam);
					NamedNodeMap namedNodeMap = nodeList.item(i).getAttributes();
					// System.out.println(namedNodeMap);
					if (namedNodeMap != null && namedNodeMap.getLength() > 0) {
						for (int j = 0; j < namedNodeMap.getLength(); j++) {
							Class[] classes = { String.class };
							String[] values = { namedNodeMap.item(j).getNodeValue().trim() };
							Method method = c.getDeclaredMethod("set" + namedNodeMap.item(j).getNodeName().replaceFirst(namedNodeMap.item(j).getNodeName().substring(0, 1), namedNodeMap.item(j).getNodeName().substring(0, 1).toUpperCase()), classes);
							method.invoke(object, values);
						}
					}
					String deep = Integer.toString(i - gap);
					if (deepFather != null) {
						deep = deepFather + "_" + deep;

					}
					fixedValue(c, object, deep);
					// System.out.println(deep + " " + c.getSimpleName().toLowerCase());
					hashMapTree.put(deep, c.getSimpleName().toLowerCase());
					try {
						hashMapObj.put(deep, (Element) object);
					} catch (Exception e) {
						System.err.println("NON ELEMENT ESCLUSO DALLA MAPPA");
					}
					read(nodeList.item(i), c, object, deep);
					if (cParam != null) {
						Class[] classes = { Object.class };
						Object[] values = { object };
						Method method = cParam.getDeclaredMethod("add" + nodeList.item(i).getNodeName().replaceFirst(nodeList.item(i).getNodeName().substring(0, 1), nodeList.item(i).getNodeName().substring(0, 1).toUpperCase()), classes);
						method.invoke(objectParam, values);
					} else {
						if (objects == null)
							objects = new ArrayList();
						objects.add(object);
					}
				} else if (nodeList.item(i).getNodeType() != Node.COMMENT_NODE) {
					if (!nodeList.item(i).getNodeValue().trim().equals("")) {
						Class[] classes = { String.class };
						String[] values = { nodeList.item(i).getNodeValue().trim() };
						Method method = cParam.getDeclaredMethod("set" + nodeList.item(i).getNodeName().replaceFirst(nodeList.item(i).getNodeName().substring(1, 2), nodeList.item(i).getNodeName().substring(1, 2).toUpperCase()).replaceAll("#", ""), classes);
						method.invoke(objectParam, values);
						hashMap.put(nodeList.item(i).getNodeValue().trim(), deepFather);
					} else {
						gap++;
					}
				}
			}
		}
	}

	public ArrayList getObjects() {
		return objects;
	}

	public void fixedValue(Class c, Object object, String deep) {
		try {
			Class[] classesa = { String.class };
			String[] valuesa = { deep };
			Method methoda = c.getDeclaredMethod("setDeep", classesa);
			methoda.invoke(object, valuesa);
			getXPathMapping(c, object, deep);
		} catch (Exception e) {
			System.err.println("ConfigurationXMLReader.fixedValue() " + e.getMessage());
		}

	}

	/*
	 * @Simone mi precarico la posizione dell'element. Ci sono problemi se si cambia tipo di tag esempio: <element name="allegati" multiMod="true" type="multi_group" prefix="/c/daogrp/daoloc"> <element input_type="text" class="Long" name="nome allegato originale">/c/daogrp/daoloc/@label</element>
	 * <element input_type="text" class="ExtraLong" name="denominazione elemento">/c/daogrp/daoloc/@title</element> <element input_type="text" type="custom" class="ExtraLong" name="percorso" externalPath="/c/@id">/c/daogrp/daoloc/@href</element> </element> <param> <elemento id="clona"> <opzione
	 * action="append" value="(clonato)">/crono_eventi/authority_control/titolo_evento/text()</opzione> <opzione action="replace" value=".">/crono_eventi/@id</opzione> </elemento> </param>
	 */
	public void getXPathMapping(Class c, Object object, String deep) throws NullPointerException {
		try {

			String simpleName = c.getSimpleName().toLowerCase();
			// if (deep.contains("_")) {
			// System.out.println(simpleName + " " + deep);
			String[] splitDeep = deep.split("_");
			// System.out.println(splitDeep.length);
			String rootElement = "/root/";
			for (String string : splitDeep) {
				try {
					// System.out.println("QUI " + simpleName + " " + deep);
					// System.out.println("QUI " + deep);
					// System.out.println("QUI " + string);
					// System.out.println("QUI " + hashMapTree.get(string));
					String elementName = "";
					if (string.equals("0")) {
						elementName = simpleName;
					} else {
						elementName = (String) hashMapTree.get(string);
						if (elementName == null) {
							elementName = simpleName;
						}
					}

					int indexValue = Integer.parseInt(string) + 1;

					rootElement += elementName + "[" + indexValue + "]/";

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// System.out.println("rootElement " + rootElement);
			// System.out.println("########################################### ");
			// } else {
			// System.out.println(simpleName + " " + deep);
			// }
			if (rootElement.endsWith("/")) {
				rootElement = rootElement.substring(0, rootElement.length() - 1);
			}
			Class[] classesa = { String.class };
			String[] valuesa = { rootElement };
			Method methoda = c.getDeclaredMethod("setXPathMapping", classesa);
			methoda.invoke(object, valuesa);

		} catch (Exception e) {
			System.err.println("ConfigurationXMLReader.fixedValue() " + e.getMessage());
		}
	}

	public Element getElement(String xpath) throws NullPointerException {
		Element result = null;
		try {
			String deep = (String) hashMap.get(xpath);
			String[] levels = deep.split("_");
			// System.out.println(deep);
			for (int i = 0; i < levels.length; i++) {
				result = getObject(result, Integer.parseInt(levels[i]));
			}
		} catch (Exception e) {
			throw new NullPointerException("Nessuna Corrispondenza per l'elemento \"" + xpath + "\" cercato!");
		}
		return result;
	}

	// torna il padre degli elementi
	public Element getElementFromDeep(String deep) throws NullPointerException {
		Element element = null;
		try {

			String[] levels = deep.split("_");
			String hierFather = "";
			System.out.println("ConfigurationXMLReader.getElementFromDeep() deep " + deep);
			int indexLimit = 1;
			for (int i = 0; i < indexLimit; i++) {
				String deepFather = levels[i];
				if (i == 0) {
					hierFather = deepFather;
				} else {
					hierFather += "_" + deepFather;
				}
				System.out.println("ConfigurationXMLReader.getElementFromDeep() hierFather " + hierFather);
				element = (Element) hashMapObj.get(hierFather);
				System.out.println("ConfigurationXMLReader.getElementFromDeep() element.getName() " + element.getName());
				System.out.println("ConfigurationXMLReader.getElementFromDeep() element.getPrefix() " + element.getPrefix());
				System.out.println("ConfigurationXMLReader.getElementFromDeep() element.getText() " + element.getText());
				System.out.println("ConfigurationXMLReader.getElementFromDeep() input_type " + element.getInput_type());
				if (element.getText() == null) {
					if (element.getPrefix() == null) {
						System.out.println("NON c'è il testo e prefix rilancio");
						indexLimit++;
					}
				}
			}

			// System.out.println("####################################################################");
			/*
			 * for (int i = 0; i < levels.length; i++) { result = getObject(result, Integer.parseInt(levels[i])); }
			 */
		} catch (Exception e) {
			throw new NullPointerException("Nessuna corrispondenza per deep " + deep);
		}
		return element;
	}

	public ArrayList<Element> getElementsFromDeep(String deep) throws NullPointerException {
		ArrayList<Element> arrayList = new ArrayList<Element>();
		try {

			String[] levels = deep.split("_");
			String hierFather = "";
			// System.out.println("ConfigurationXMLReader.getElementFromDeep() deep " + deep);
			for (int i = 0; i < levels.length; i++) {
				String deepFather = levels[i];
				if (i == 0) {
					hierFather = deepFather;
				} else {
					hierFather += "_" + deepFather;
				}
				// System.out.println("ConfigurationXMLReader.getElementFromDeep() " + hierFather);
				Element element = (Element) hashMapObj.get(hierFather);
				arrayList.add(element);
				// System.out.println("ConfigurationXMLReader.getElementFromDeep() " + element.getName());
				// System.out.println("ConfigurationXMLReader.getElementFromDeep() " + element.getPrefix());
				// System.out.println("ConfigurationXMLReader.getElementFromDeep() " + element.getText());
			}

			System.out.println("####################################################################");
			/*
			 * for (int i = 0; i < levels.length; i++) { result = getObject(result, Integer.parseInt(levels[i])); }
			 */
		} catch (Exception e) {
			throw new NullPointerException("Nessuna corrispondenza per deep " + deep);
		}
		return arrayList;
	}

	private Element getObject(Element element, int index) {
		if (element == null)
			return (Element) objects.get(index);
		else
			return ((Element) element.getElemets().get(index));
	}
}
