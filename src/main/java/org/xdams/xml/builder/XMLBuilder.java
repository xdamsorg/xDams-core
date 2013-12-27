package org.xdams.xml.builder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xpath.XPathAPI;
import org.dom4j.io.DOMReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.DOMOutputter;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xdams.xml.builder.exception.XMLException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLBuilder {
	private Document JDomDocument = null;

	private org.w3c.dom.Document DomDocument = null;

	private DOMOutputter domOutputter = new DOMOutputter();

	private String htmlTagClass = "";

	private String theTerm = "";

	private String spanFine = "";

	private String spanIni = "";

	public XMLBuilder(File file) throws XMLException {
		// strDocXml=strDocXml.replaceAll("&", "&amp;");
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setExpandEntityReferences(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.parse(new InputSource(file.getAbsolutePath()));
			JDomDocument = new DOMBuilder().build(DomDocument);
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public XMLBuilder(Node node) throws XMLException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setExpandEntityReferences(false);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			org.w3c.dom.Document newDomDocument = documentBuilder.newDocument();
			Node importedNode = newDomDocument.importNode(node, true);
			newDomDocument.appendChild(importedNode);
			DomDocument = newDomDocument;
			JDomDocument = new DOMBuilder().build(DomDocument);
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public XMLBuilder(String rootNode) throws XMLException {
		/* AUTHOR SANDRO */
		try {
			JDomDocument = new Document();
			Element root = new Element(rootNode);
			JDomDocument.setRootElement(root);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.newDocument();
			DomDocument.appendChild(DomDocument.createElement(rootNode));
			// DomDocument = new DOMOutputter().output(JDomDocument);
		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}

	}

	public XMLBuilder(org.w3c.dom.Document docResult) throws ParserConfigurationException, SAXException, IOException {
		this.DomDocument = docResult;
	}

	public XMLBuilder(ByteArrayInputStream docXml) throws XMLException {
		/* AUTHOR SANDRO */

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.parse(docXml);
			JDomDocument = new DOMBuilder().build(DomDocument);
		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}
	}

	public XMLBuilder(InputStream docXml) throws XMLException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.parse(docXml);
			JDomDocument = new DOMBuilder().build(DomDocument);
		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}
	}

	public XMLBuilder(String strDocXml, boolean finto) throws XMLException {

		try {
			// strDocXml=strDocXml.replaceAll("&", "&amp;");

			// ByteArrayInputStream docXml = new ByteArrayInputStream(strDocXml.getBytes());
			// DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// dbf.setValidating(false);
			// DocumentBuilder builder;
			// builder = dbf.newDocumentBuilder();
			// DomDocument = builder.parse(docXml);
			strDocXml = parseAttribute(strDocXml);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setExpandEntityReferences(false);
			dbf.setValidating(false);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.parse(new InputSource(new StringReader(strDocXml)));
			JDomDocument = new DOMBuilder().build(DomDocument);

		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}

	}

	public XMLBuilder(String strDocXml, String strEncoding) throws XMLException {
		// strDocXml=strDocXml.replaceAll("&", "&amp;");
		strDocXml = parseAttribute(strDocXml);
		try {
			boolean isoPass = false;
			String strIso = "";
			if (strDocXml.indexOf("?>\n") != -1) {
				strIso = strDocXml.substring(0, strDocXml.indexOf("?>\n") + 3);
				strDocXml = strDocXml.substring(strDocXml.indexOf("?>\n") + 3);
				isoPass = true;
			}
			if (!isoPass && strEncoding != null && !(strEncoding.trim()).equals("")) {
				strDocXml = "<?xml version=\"1.0\" encoding=\"" + strEncoding + "\"?>\n" + strDocXml;
			}
			if (isoPass) {
				strDocXml = strIso + strDocXml;
			}

			// ByteArrayInputStream docXml = new ByteArrayInputStream(strDocXml.getBytes(strEncoding));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setExpandEntityReferences(false);
			dbf.setValidating(false);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.parse(new InputSource(new StringReader(strDocXml)));
			JDomDocument = new DOMBuilder().build(DomDocument);
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}

	}

	public XMLBuilder(String strDocXml, String strEncoding, String htmlTagClass) throws ParserConfigurationException, SAXException, IOException, XMLException {
		// strDocXml=strDocXml.replaceAll("&", "&amp;");
		strDocXml = parseAttribute(strDocXml);

		// stem.out.println("EVIDDDDDDDDDDDDDDDDDDDDDDDDDD " + strDocXml);
		strDocXml = strDocXml.replaceAll("<\\?xw-meta Dbms=\"ExtraWay\".*\\?><.xw-crc .*\\?>", "");
		strDocXml = strDocXml.replaceAll("<\\?xw-meta DbmsVer=\"ExtraWay\".*\\?><.xw-crc .*\\?>", "");
		strDocXml = strDocXml.replaceAll("<\\?xw-nest .*\\?>", "");
		strDocXml = strDocXml.replaceAll("<\\?xw-sr\\?>", "IniZioTagHtMlspan class=LeViRGoleTte" + htmlTagClass + "LeViRGoleTteFinETagHtMl");
		strDocXml = strDocXml.replaceAll("<\\?xw-er\\?>", "IniZioTagHtMl/spanFinETagHtMl");
		strDocXml = strDocXml.replaceAll("<\\?xw-ar @", "<h name=\"");
		strDocXml = strDocXml.replaceAll("\\?>", "\"/>");

		setHtmlTagClass(htmlTagClass);

		try {
			boolean isoPass = false;
			String strIso = "";
			if (strDocXml.indexOf("?>\n") != -1) {
				strIso = strDocXml.substring(0, strDocXml.indexOf("?>\n") + 3);
				strDocXml = strDocXml.substring(strDocXml.indexOf("?>\n") + 3);
				isoPass = true;
			}
			if (!isoPass && strEncoding != null && !(strEncoding.trim()).equals("")) {
				strDocXml = "<?xml version=\"1.0\" encoding=\"" + strEncoding + "\"?>\n" + strDocXml;
			}
			if (isoPass) {
				strDocXml = strIso + strDocXml;
			}
			// System.out.println("XMLBuilder.XMLBuilder()" + strDocXml);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder builder;
			builder = dbf.newDocumentBuilder();
			DomDocument = builder.parse(new InputSource(new StringReader(strDocXml)));
			JDomDocument = new DOMBuilder().build(DomDocument);
		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}
		// <?xw-sr?><?xw-er?> FinETagHtMl
	}

	public static void main(String[] args) throws UnsupportedEncodingException, TransformerException {

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Node getSingleNode(String xPath) throws XMLException {
		/* AUTHOR SIMONE */
		Node returnNode = null;
		try {
			Node singleNode = XPathAPI.selectSingleNode(DomDocument, xPath);
			if (singleNode != null) {
				returnNode = singleNode;
			}
		} catch (TransformerException e) {

			e.printStackTrace();
		}
		return returnNode;
	}

	public NodeList getListNode(String xPath) throws XMLException {
		/* AUTHOR SIMONE */
		NodeList returnNode = null;
		try {
			NodeList listNode = XPathAPI.selectNodeList(DomDocument, xPath);
			if (listNode != null) {
				returnNode = listNode;
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return returnNode;
	}

	public void insertNodesAt(NodeList nodeList, String xpathFather) throws XMLException {
		/* AUTHOR SANDRO */
		try {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node element = (Node) nodeList.item(i);
				Node dup = DomDocument.importNode(element, true);
				Node nodeFather = XPathAPI.selectSingleNode(DomDocument, xpathFather);
				nodeFather.appendChild(dup);
			}
		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}

	}

	public void insertNode(String xPath, String nodeValue) throws XMLException {
		insertNode(xPath, nodeValue, false);
	}

	public void insertNode(String xPath, String nodeValue, boolean isCDATA) throws XMLException {
		/* AUTHOR SANDRO */
		xPath = testXPath(xPath);
		// System.out.println(nodeValue);
		// System.out.println(new StringReader(nodeValue).toString());
		// System.out.println(new StringWriter().append(nodeValue.subSequence(0, nodeValue.length())).toString());
		if (xPath != null && !xPath.trim().equals("")) {
			StringTokenizer stringTokenizer = new StringTokenizer(xPath, "/");
			String currentXPath = "";
			boolean withIdx = false;
			int count = 0;
			int tot = stringTokenizer.countTokens();
			while (stringTokenizer.hasMoreTokens()) {
				count++;
				String nodeName = stringTokenizer.nextToken();
				String realName = nodeName.replace('~', '/');
				nodeName = nodeName.replace('~', '/');
				Node appoAttribute = null;
				ArrayList appoAttributesList = null;
				try {
					if (realName.indexOf("[") != -1) {
						try {
							try {
								Integer.parseInt(realName.substring(realName.indexOf("[") + 1, realName.indexOf("]")));
							} catch (StringIndexOutOfBoundsException e1) {
								String endName = stringTokenizer.nextToken();
								realName += "/" + endName;
								nodeName += "/" + endName;
								tot = tot - 1;
								Integer.parseInt(realName.substring(realName.indexOf("[") + 1, realName.indexOf("]")));
							}
							withIdx = true;
						} catch (NumberFormatException exc) {
							withIdx = false;
							String appo = realName.substring(realName.indexOf("[") + 1, realName.indexOf("]"));
							if (appo.indexOf("@") != -1) {
								if (appo.indexOf("' and @") != -1) {
									// PROBLEMA AND
									appoAttributesList = new ArrayList();
									String appoName2 = appo;
									while (appoName2.indexOf("' and @") != -1) {
										appoName2 = appoName2.substring(0, appoName2.toLowerCase().indexOf("' and @")) + "'~@" + appoName2.substring(appoName2.toLowerCase().indexOf("' and @") + "' and @".length(), appoName2.length());
									}
									StringTokenizer stringTokenizer2 = new StringTokenizer(appoName2, "~");
									while (stringTokenizer2.hasMoreTokens()) {
										String currentName = stringTokenizer2.nextToken();
										String appoName = currentName.substring(currentName.indexOf("@") + 1, currentName.indexOf("="));
										String appoValue = currentName.substring(currentName.indexOf("'") + 1, currentName.lastIndexOf("'"));
										appoAttribute = DomDocument.createAttribute(appoName);
										appoAttribute.setNodeValue(appoValue);
										appoAttributesList.add(appoAttribute);
									}
								} else {
									String appoName = appo.substring(appo.indexOf("@") + 1, appo.indexOf("="));
									String appoValue = appo.substring(appo.indexOf("'") + 1, appo.lastIndexOf("'"));
									appoAttribute = DomDocument.createAttribute(appoName);
									appoAttribute.setNodeValue(appoValue);

								}
							} else {
								if (appo.indexOf("child::") != -1) {
									String appoName = appo.substring(appo.indexOf("child::") + "child::".length(), appo.indexOf("=")).trim();
									String appoValue = appo.substring(appo.indexOf("'") + 1, appo.lastIndexOf("'"));
									// System.out.println(realName);
									String newNode = currentXPath.trim() + "/" + realName.substring(0, realName.indexOf("[")).trim();
									NodeList list = XPathAPI.selectNodeList(DomDocument, newNode);
									if (list != null) {
										if (XPathAPI.selectSingleNode(DomDocument, currentXPath + "/" + nodeName) == null) {
											insertNode(newNode + "[" + list.getLength() + 1 + "]" + "/" + appoName, appoValue, isCDATA);
										} else {
											insertNode(newNode + "/" + appoName, appoValue, isCDATA);
										}
									} else {
										insertNode(newNode, "", isCDATA);
									}
								}
							}
						}
						realName = realName.substring(0, realName.indexOf("["));
					}

					Node testNode = XPathAPI.selectSingleNode(DomDocument, currentXPath + "/" + nodeName);
					if (testNode == null && !realName.equals(JDomDocument.getRootElement().getName())) {
						if (realName.indexOf("@") == -1) {
							Node node = null;
							if (!realName.trim().equalsIgnoreCase("text()")) {
								node = DomDocument.createElement(realName);
								if (count == tot) {
									if (isCDATA) {
										// System.out.println("XMLBuilder.insertNode() STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode() STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode() STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode() STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode() STO CREANDO isCDATA");
										CDATASection section = DomDocument.createCDATASection(nodeValue);
										node.appendChild(section);
									} else {
										// System.out.println("XMLBuilder.insertNode()1111111111111 STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode()1111111111111 STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode()1111111111111 STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode()1111111111111 STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode()1111111111111 STO CREANDO isCDATA");
										// System.out.println("XMLBuilder.insertNode()1111111111111 STO CREANDO isCDATA");
										Node textNode = DomDocument.createTextNode(nodeValue);
										node.appendChild(textNode);
									}
								}
							} else {
								if (isCDATA) {
									// System.out.println("XMLBuilder.insertNode()22222222222222222 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()22222222222222222 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()22222222222222222 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()22222222222222222 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()22222222222222222 STO CREANDO isCDATA");
									node = DomDocument.createCDATASection(nodeValue);
									// node = section ;
								} else {
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");
									// System.out.println("XMLBuilder.insertNode()33333333333333333 STO CREANDO isCDATA");

									node = DomDocument.createTextNode(nodeValue);
								}

							}
							if (appoAttributesList != null) {
								for (int xx = 0; xx < appoAttributesList.size(); xx++)
									((org.w3c.dom.Element) node).setAttributeNode((Attr) appoAttributesList.get(xx));
							} else if (appoAttribute != null) {
								((org.w3c.dom.Element) node).setAttributeNode((Attr) appoAttribute);
							}
							try {
								XPathAPI.selectSingleNode(DomDocument, currentXPath).appendChild(node);

								// System.out.println("numero figli = "+XPathAPI.selectSingleNode(DomDocument, currentXPath).getChildNodes().getLength());
								/*
								 * try { System.out.println(valoreNodo(currentXPath+"/text()") + " XMLBuilder.insertNode() " + node.getNodeValue()); } catch (Exception e) { e.printStackTrace(); }
								 */

							} catch (NullPointerException e1) {
								try {
									int wrongNum = Integer.parseInt(currentXPath.substring(currentXPath.lastIndexOf("[") + 1, currentXPath.lastIndexOf("]")));
									wrongNum = wrongNum - 1;
									while (wrongNum != 0) {
										currentXPath = currentXPath.substring(0, currentXPath.lastIndexOf("[") + 1) + Integer.toString(wrongNum) + currentXPath.substring(currentXPath.lastIndexOf("]"), currentXPath.length());
										try {
											XPathAPI.selectSingleNode(DomDocument, currentXPath).appendChild(node);
											break;
										} catch (NullPointerException exc) {
										} catch (DOMException exct) {
											break;
										}
										wrongNum = wrongNum - 1;
									}
								} catch (StringIndexOutOfBoundsException stex) {
								} catch (NumberFormatException stex) {
								}
							}
						} else {
							Node attribute = DomDocument.createAttribute(realName.substring(realName.indexOf("@") + 1));
							if (count == tot) {
								attribute.setNodeValue(nodeValue);
							}
							// System.out.println(currentXPath);
							try {
								((org.w3c.dom.Element) XPathAPI.selectSingleNode(DomDocument, currentXPath)).setAttributeNode((Attr) attribute);
							} catch (NullPointerException exc) {
								try {
									int wrongNum = Integer.parseInt(currentXPath.substring(currentXPath.lastIndexOf("[") + 1, currentXPath.lastIndexOf("]")));
									wrongNum = wrongNum - 1;
									while (wrongNum != 0) {
										currentXPath = currentXPath.substring(0, currentXPath.lastIndexOf("[") + 1) + Integer.toString(wrongNum) + currentXPath.substring(currentXPath.lastIndexOf("]"), currentXPath.length());
										try {
											((org.w3c.dom.Element) XPathAPI.selectSingleNode(DomDocument, currentXPath)).setAttributeNode((Attr) attribute);
											break;
										} catch (NullPointerException exct) {
										} catch (DOMException exct) {
											break;
										}
										wrongNum = wrongNum - 1;
									}
								} catch (StringIndexOutOfBoundsException stex) {
								} catch (NumberFormatException stex) {
								}
							}
						}
					}
					currentXPath += "/" + nodeName;
				} catch (DOMException e) {
					throw new XMLException(e.getMessage());
				} catch (TransformerException e) {
					throw new XMLException(e.getMessage());
				}
			}
		} else {
			throw new XMLException("Invalid xPath value [" + xPath + "]");
		}
	}

	public void insertNodeAt(String xPath) throws XMLException, TransformerException {
		int xpathPosition = Integer.parseInt(xPath.substring(xPath.lastIndexOf("[") + 1, xPath.lastIndexOf("]")));
		String node2Add = xPath.substring(0, xPath.lastIndexOf("/"));
		String nodefather = node2Add.substring(0, xPath.lastIndexOf("/") - 1);
		nodefather = nodefather.substring(0, nodefather.lastIndexOf("/"));

	}

	public void insertValueAt(String xPath, String value) throws XMLException {
		insertValueAt(xPath, value, false);
	}

	public void insertValueAt(String xPath, String value, boolean isCDATA) throws XMLException {
		try {
			if (xPath.trim().endsWith("text()")) {
				NodeList textList = XPathAPI.selectNodeList(DomDocument, xPath);
				for (int i = 0; i < textList.getLength(); i++) {
					textList.item(i).setNodeValue("");
				}
				XPathAPI.selectSingleNode(DomDocument, xPath).setNodeValue(value);
			} else {
				XPathAPI.selectSingleNode(DomDocument, xPath).setNodeValue(value);
			}
		} catch (NullPointerException e) {
			if (isCDATA) {
				insertNode(xPath, value, true);
			} else {
				insertNode(xPath, value);
			}

		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}
	}

	public org.w3c.dom.Document deleteNode(org.w3c.dom.Document aDoc, String xPath) throws XMLException {
		/* AUTHOR SIMONE */
		org.w3c.dom.Document doc = aDoc;
		if (xPath != null && !xPath.trim().equals("")) {
			try {
				NodeList list = XPathAPI.selectNodeList(doc, xPath);
				if (list != null && list.getLength() != 0) {
					for (int i = 0; i < list.getLength(); i++) {
						Node node = (Node) list.item(i);
						node.getParentNode().removeChild(node);
					}
				}
				doc.normalize();
			} catch (Exception e) {
				e.printStackTrace();
				throw new XMLException(e.getMessage());
			}
		}
		return doc;
	}

	public void deleteNodePreRelease(String xPath) throws XMLException {
		/* AUTHOR SIMONE */
		if (xPath != null && !xPath.trim().equals("")) {
			try {
				NodeList list = XPathAPI.selectNodeList(DomDocument, xPath);
				if (list != null && list.getLength() != 0) {
					for (int i = 0; i < list.getLength(); i++) {
						Node node = (Node) list.item(i);
						Node parent = node.getParentNode();
						Node deleted = parent.removeChild(node);
						// System.out.println(deleted.getNextSibling());
						NodeList listAfterRemove = parent.getChildNodes();
						for (int j = 0; j < listAfterRemove.getLength(); j++) {
							Node nodeANode = (Node) listAfterRemove.item(j);
							// System.out.println(nodeANode+" b "+ nodeANode.getNodeType() + " a "+nodeANode.TEXT_NODE);
							// System.out.println(nodeANode.getNodeType() + " a "+nodeANode.DOCUMENT_NODE);
							if (nodeANode.getNodeType() == nodeANode.TEXT_NODE) {
								String txtNode = (String) nodeANode.getNodeValue().trim();
								if ("".equals(txtNode)) {
									parent.removeChild(nodeANode);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new XMLException(e.getMessage());
			}
		}
	}

	public void deleteNode(String xPath) throws XMLException {
		/* AUTHOR SIMONE */
		if (xPath != null && !xPath.trim().equals("")) {
			try {
				NodeList list = XPathAPI.selectNodeList(DomDocument, xPath);
				if (list != null && list.getLength() != 0) {
					for (int i = 0; i < list.getLength(); i++) {
						Node node = (Node) list.item(i);
						Node parent = node.getParentNode();
						parent.removeChild(node);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new XMLException(e.getMessage());
			}
		}
	}

	public void appendValueAt(String xPathToClone) throws XMLException {
		try {
			// System.out.println(xPathToClone);
			// String xPathToAppendTheClone = xPathToClone.substring(0,xPathToClone.lastIndexOf("/"));
			// System.out.println(xPathToAppendTheClone);
			// Node newChild = getSingleNode(xPathToCreate);
			Node refFather = getSingleNode(xPathToClone).getParentNode();
			Node node = XPathAPI.selectSingleNode(DomDocument, xPathToClone).cloneNode(false);
			// Node clone = art.getLastChild().cloneNode(true);
			refFather.insertBefore(node, getSingleNode(xPathToClone));
			clearNode(xPathToClone);
			// System.out.println("XMLBuilder.appendValueAt()"+node);
			// System.out.println("XMLBuilder.appendValueAt()"+refChild);
			// DomDocument.insertBefore(newChild, refChild);

		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}
	}

	public void clearNode(String xPath) throws XMLException, TransformerException {

		if (XPathAPI.selectSingleNode(DomDocument, xPath) != null) {
			/*
			 * if (XPathAPI.selectSingleNode(DomDocument, xPath).hasChildNodes()) { for (int i = 0; i < XPathAPI.selectSingleNode(DomDocument, xPath).getChildNodes().getLength(); i++) { XPathAPI.selectSingleNode(DomDocument, xPath).removeChild(XPathAPI.selectSingleNode(DomDocument,
			 * xPath).getChildNodes().item(i)); } }
			 */
			if (XPathAPI.selectSingleNode(DomDocument, xPath).hasAttributes()) {
				for (int i = 0; i < XPathAPI.selectSingleNode(DomDocument, xPath).getAttributes().getLength(); i++) {
					XPathAPI.selectSingleNode(DomDocument, xPath).getAttributes().item(i).setNodeValue("");
				}
			}
		}

	}

	public String getXML(String encoding, boolean indent, boolean omitDeclaration) throws XMLException {
		/* AUTHOR SIMONE */
		String result = null;
		try {
			/*
			 * Transformer transformer = TransformerFactory.newInstance().newTransformer(); transformer.setOutputProperty(OutputKeys.INDENT, "yes"); transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			 * transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // initialize StreamResult with File object to save to file StreamResult result = new StreamResult(new StringWriter()); DOMSource source = new DOMSource(DomDocument); StreamResult streamResult = new
			 * StreamResult(new StringWriter()); transformer.transform(source, streamResult); result = streamResult.getWriter().toString();
			 */
			OutputFormat outputFormat = new OutputFormat();
			outputFormat.setSuppressDeclaration(omitDeclaration);
			if (indent) {
				outputFormat.setNewlines(true);
				outputFormat.setIndent(true);
				outputFormat.setIndentSize(4);
			}
			outputFormat.setEncoding(encoding);

			StringWriter stringWriter = new StringWriter();

			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
			xmlWriter.setMaximumAllowedCharacter(255);
			try {
				DOMReader xmlReader = new DOMReader();

				xmlWriter.write(xmlReader.read(DomDocument));
				xmlWriter.flush();

			} catch (IOException e) {
				throw new XMLException(e.getMessage());
			}
			String string = stringWriter.toString();
			string = string.replaceAll("&amp;#", "&#");
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public String getXML(String encoding, boolean indent) throws XMLException {
		/* AUTHOR SIMONE */
		String result = null;
		try {
			/*
			 * Transformer transformer = TransformerFactory.newInstance().newTransformer(); transformer.setOutputProperty(OutputKeys.INDENT, "yes"); transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			 * transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // initialize StreamResult with File object to save to file StreamResult result = new StreamResult(new StringWriter()); DOMSource source = new DOMSource(DomDocument); StreamResult streamResult = new
			 * StreamResult(new StringWriter()); transformer.transform(source, streamResult); result = streamResult.getWriter().toString();
			 */
			OutputFormat outputFormat = new OutputFormat();
			if (indent) {
				outputFormat.setNewlines(true);
				outputFormat.setIndent(true);
				outputFormat.setIndentSize(4);
			}
			outputFormat.setEncoding(encoding);
			StringWriter stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
			xmlWriter.setMaximumAllowedCharacter(255);
			try {
				DOMReader xmlReader = new DOMReader();
				xmlWriter.write(xmlReader.read(DomDocument));
				xmlWriter.flush();

			} catch (IOException e) {
				throw new XMLException(e.getMessage());
			}
			String string = stringWriter.toString();
			string = string.replaceAll("&amp;#", "&#");
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public String getXML(OutputFormat outputFormat) throws XMLException {
		/* AUTHOR SIMONE */
		try {
			StringWriter stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
			xmlWriter.setMaximumAllowedCharacter(255);
			try {
				// System.out.println("QOQOQOQOQOQO xmlWriter.isEscapeText() QOOQOQOQOOQ " + xmlWriter.isEscapeText());
				// xmlWriter.setEscapeText(false);
				DOMReader xmlReader = new DOMReader();
				// System.out.println("QOQOQOQOQOQO xmlWriter.isEscapeText() QOOQOQOQOOQ " + DomDocument);
				xmlWriter.write(xmlReader.read(DomDocument));
				xmlWriter.flush();

			} catch (IOException e) {
				throw new XMLException(e.getMessage());
			}
			String string = stringWriter.toString();
			string = string.replaceAll("&amp;#", "&#");
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public String getXMLFlat(String encoding) throws XMLException {
		/* AUTHOR SIMONE */
		String result = null;
		try {
			/*
			 * Transformer transformer = TransformerFactory.newInstance().newTransformer(); transformer.setOutputProperty(OutputKeys.INDENT, "yes"); transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			 * transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // initialize StreamResult with File object to save to file StreamResult result = new StreamResult(new StringWriter()); DOMSource source = new DOMSource(DomDocument); StreamResult streamResult = new
			 * StreamResult(new StringWriter()); transformer.transform(source, streamResult); result = streamResult.getWriter().toString();
			 */
			OutputFormat outputFormat = new OutputFormat();
			// outputFormat.setSuppressDeclaration(true);
			outputFormat.setTrimText(true);
			outputFormat.setEncoding(encoding);
			StringWriter stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
			xmlWriter.setMaximumAllowedCharacter(255);
			try {
				// System.out.println("QOQOQOQOQOQO xmlWriter.isEscapeText() QOOQOQOQOOQ " + xmlWriter.isEscapeText());
				// xmlWriter.setEscapeText(false);
				DOMReader xmlReader = new DOMReader();
				// System.out.println("QOQOQOQOQOQO xmlWriter.isEscapeText() QOOQOQOQOOQ " + DomDocument);
				xmlWriter.write(xmlReader.read(DomDocument));
				xmlWriter.flush();

			} catch (IOException e) {
				throw new XMLException(e.getMessage());
			}
			String string = stringWriter.toString();
			string = string.replaceAll("&amp;#", "&#");
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	/**
	 * @deprecated getXMLFromNode(Node node, String encoding)
	 */

	public String getXMLFromNode(Node node) throws XMLException {
		/* AUTHOR SIMONE */
		String result = null;
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			// initialize StreamResult with File object to save to file StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(node);
			StreamResult streamResult = new StreamResult(new StringWriter());
			transformer.transform(source, streamResult);
			result = streamResult.getWriter().toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public String getXMLFromNode(Node node, String encoding) throws XMLException {
		try {
			OutputFormat outputFormat = new OutputFormat();
			// outputFormat.setSuppressDeclaration(true);
			outputFormat.setNewlines(true);
			outputFormat.setIndent(true);
			outputFormat.setIndentSize(4);
			outputFormat.setEncoding(encoding);
			StringWriter stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
			xmlWriter.setMaximumAllowedCharacter(255);
			try {
				String xml = getXMLFromNode(node).replaceAll("\\?>", "\\?>\n");
				DOMReader xmlReader = new DOMReader();
				org.w3c.dom.Document doc = new XMLBuilder(xml, "ISO-8859-1").getDomDocument();
				xmlWriter.write(xmlReader.read(doc));
				xmlWriter.flush();

			} catch (IOException e) {
				e.printStackTrace();
				throw new XMLException(e.getMessage());
			}
			String string = stringWriter.toString();
			string = string.replaceAll("&amp;#", "&#");
			return string;

		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public String getXML(String encoding) throws XMLException {
		/* AUTHOR SIMONE */
		String result = null;
		try {
			/*
			 * Transformer transformer = TransformerFactory.newInstance().newTransformer(); transformer.setOutputProperty(OutputKeys.INDENT, "yes"); transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			 * transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // initialize StreamResult with File object to save to file StreamResult result = new StreamResult(new StringWriter()); DOMSource source = new DOMSource(DomDocument); StreamResult streamResult = new
			 * StreamResult(new StringWriter()); transformer.transform(source, streamResult); result = streamResult.getWriter().toString();
			 */
			OutputFormat outputFormat = new OutputFormat();
			// outputFormat.setSuppressDeclaration(true);
			outputFormat.setNewlines(true);
			outputFormat.setIndent(true);
			outputFormat.setIndentSize(4);
			outputFormat.setEncoding(encoding);
			StringWriter stringWriter = new StringWriter();
			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
			xmlWriter.setMaximumAllowedCharacter(255);
			try {
				// System.out.println("QOQOQOQOQOQO xmlWriter.isEscapeText() QOOQOQOQOOQ " + xmlWriter.isEscapeText());
				// xmlWriter.setEscapeText(false);
				DOMReader xmlReader = new DOMReader();
				// System.out.println("QOQOQOQOQOQO xmlWriter.isEscapeText() QOOQOQOQOOQ " + DomDocument);
				xmlWriter.write(xmlReader.read(DomDocument));
				xmlWriter.flush();

			} catch (IOException e) {
				throw new XMLException(e.getMessage());
			}
			String string = stringWriter.toString();
			string = string.replaceAll("&amp;#", "&#");
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			throw new XMLException(e.getMessage());
		}
	}

	public String getRootElement() {
		String ritornoRoot = "";
		try {
			ritornoRoot = (getDomDocument().getDocumentElement()).getNodeName();
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ritornoRoot;
	}

	public Document getJDomDocument() throws XMLException {
		JDomDocument = new DOMBuilder().build(DomDocument);
		if (JDomDocument != null)
			return JDomDocument;
		else
			throw new XMLException("Invalid operation! Document = null");
	}

	public org.w3c.dom.Document getDomDocument() throws XMLException {
		try {
			return DomDocument;
		} catch (Exception e) {
			throw new XMLException(e.getMessage());
		}
	}

	private String testXPath(String xpath) {
		/* AUTHOR SANDRO */
		String result = "";
		if (xpath.indexOf("[") != -1) {
			int start = xpath.indexOf("[") + 1;
			int end = xpath.indexOf("]");
			String toTest = xpath.substring(xpath.indexOf("[") + 1, xpath.indexOf("]"));
			try {
				Integer.parseInt(toTest);
				result = xpath;
			} catch (NumberFormatException e) {
				if (toTest.indexOf("@") == -1) {
					toTest = toTest.replace('/', '~');
					result = xpath.substring(0, start) + toTest + xpath.substring(end, xpath.length());
				} else {
					result = xpath;
				}
			}
		} else {
			result = xpath;
		}
		return result;
	}

	public boolean testaNodo(String xPath) throws TransformerException {
		Node ilNodo = XPathAPI.selectSingleNode(DomDocument, xPath);
		boolean risultato = false;
		if (ilNodo != null) {
			if (ilNodo.getNodeValue() != null && ilNodo.getNodeValue() != "") {
				risultato = true;
			} else {
				risultato = false;
			}
		} else {
			risultato = false;
		}
		return risultato;
	}

	public int contaNodi(String xPath) {
		int iNodi = 0;
		/*
		 * NodeList elencoNodi = XPathAPI.selectNodeList(DomDocument, xPath); if (elencoNodi != null) { iNodi = elencoNodi.getLength(); }
		 */
		try {
			XPath xpath = new DOMXPath(xPath);
			List results = (List) xpath.selectNodes(DomDocument);
			if (results != null) {
				iNodi = results.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iNodi;
	}

	public void setHtmlTagClass(String htmlTagClass) {
		this.htmlTagClass = htmlTagClass;
	}

	private String getValore(String xPath) throws TransformerException, UnsupportedEncodingException {
		String ilValore = "";
		try {
			XPath xpath = new DOMXPath(xPath);
			if (xPath.endsWith("/text()")) {
				List elencoNodi = (List) xpath.selectNodes(DomDocument);
				for (int i = 0; i < elencoNodi.size(); i++) {
					try {
						ilValore += ((Node) elencoNodi.get(i)).getNodeValue() + "\n";
					} catch (NullPointerException exc) {
					}
				}
			} else {
				try {
					Object ilNodo = xpath.selectSingleNode(DomDocument);
					xpath = new DOMXPath(xPath.substring(0, xPath.lastIndexOf("@")) + "halala/@name");
					Object highLightNode = xpath.selectSingleNode(DomDocument);
					if (ilNodo != null) {
						ilValore = ((Node) ilNodo).getNodeValue();
					} else {
						ilValore = "";
					}
					if (highLightNode != null) {
						setSpanIni("IniZioTagHtMlspan class=LeViRGoleTte" + getHtmlTagClass() + "LeViRGoleTteFinETagHtMl");
						setSpanFine("IniZioTagHtMl/spanFinETagHtMl");
					} else {
						setSpanIni("");
						setSpanFine("");
					}
					if (ilNodo != null && getTheTerm() != null) {
						// TODO EVID
						// ilValore = IdxUtils.evidenziaChiave(((Node) ilNodo).getNodeValue(), getTheTerm());
					} else {
						ilValore = "";
					}
				} catch (Exception e) {
					System.err.println("XMLBuilder.getValore().1 error: " + e.getMessage());
				}
			}
			if (ilValore.equals(null)) {
				ilValore = "";
			}
		} catch (Exception e) {
			System.err.println("XMLBuilder.getValore().2 error: " + e.getMessage());
		}

		OutputFormat outputFormat = new OutputFormat();
		outputFormat.setEncoding("ISO-8859-1");
		StringWriter stringWriter = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);
		try {
			xmlWriter.setMaximumAllowedCharacter(255);
			xmlWriter.write(ilValore);
			xmlWriter.flush();
			xmlWriter.close();
		} catch (Exception e) {
			System.err.println("XMLBuilder.getValore().xmlWriter.1 error: " + e.getMessage());
		} finally {
			try {
				xmlWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("XMLBuilder.getValore().xmlWriter.2 error: " + e.getMessage());
			}
			try {
				xmlWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("XMLBuilder.getValore().xmlWriter.3 error: " + e.getMessage());
			}
		}
		String returno = stringWriter.toString().replaceAll("&lt;", "<");
		returno = returno.replaceAll("&gt;", ">");
		return returno;
		// return ilValore;
	}

	public String valoreNodoNoEscape(String xPath) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ((ilValore.replaceAll("&amp;", "\\&")).replaceAll("<", "&#60;")).replaceAll(">", "&#62;");
		return ilValore.trim();
	}

	public String valoreNodo(String xPath) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ((ilValore.replaceAll("\"", "&#34;")).replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		return ilValore.trim();
	}

	public String valoreNodo(String xPath, boolean doConvert) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		if (doConvert) {
			ilValore = ((ilValore.replaceAll("\"", "&#34;")).replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		}
		// ilValore = noHighlight(ilValore);
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		return ilValore.trim();
	}

	public String valoreNodo(String xPath, String returnChar) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ((ilValore.replaceAll("\"", "&#34;")).replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		// ilValore = noHighlight(ilValore);
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		return (ilValore.replaceAll("\n", returnChar)).trim();
	}

	public String valoreNodoNoHL(String xPath) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ((ilValore.replaceAll("\"", "&#34;")).replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		// ilValore = noHighlight(ilValore);
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		if (ilValore.indexOf("IniZioTagHtMlspan") > -1) {
			ilValore = ilValore.replaceAll("IniZioTagHtMlspan class=LeViRGoleTteevidLeViRGoleTteFinETagHtMl", "");
			ilValore = ilValore.replaceAll("IniZioTagHtMl/spanFinETagHtMl", "");
		}
		return ilValore.trim();
	}

	private String noHighlight(String ilValore) {
		if (ilValore.indexOf("IniZioTagHtMlspan") > -1) {
			ilValore = ilValore.replaceAll("IniZioTagHtMlspan class=LeViRGoleTte" + getHtmlTagClass() + "LeViRGoleTteFinETagHtMl", "");
			ilValore = ilValore.replaceAll("IniZioTagHtMl/spanFinETagHtMl", "");
		}
		return ilValore;
	}

	public String valoreNodoHTML(String xPath, String returnChar) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ilValore.trim();
		ilValore = ilValore.replaceAll("\"", "&quot;");
		ilValore = ilValore.replaceAll("<em>", "IniZioTagHtMlemFinETagHtMl");
		ilValore = ilValore.replaceAll("</em>", "IniZioTagHtMl/emFinETagHtMl");
		ilValore = ilValore.replaceAll("<EM>", "IniZioTagHtMlemFinETagHtMl");
		ilValore = ilValore.replaceAll("</EM>", "IniZioTagHtMl/emFinETagHtMl");
		ilValore = ilValore.replaceAll("<P>", "IniZioTagHtMlpFinETagHtMl");
		ilValore = ilValore.replaceAll("</P>", "IniZioTagHtMl/pFinETagHtMl");
		ilValore = ilValore.replaceAll("<p>", "IniZioTagHtMlpFinETagHtMl");
		ilValore = ilValore.replaceAll("</p>", "IniZioTagHtMl/pFinETagHtMl");
		ilValore = ilValore.replaceAll("<STRONG>", "IniZioTagHtMlstrongFinETagHtMl");
		ilValore = ilValore.replaceAll("</STRONG>", "IniZioTagHtMl/strongFinETagHtMl");
		ilValore = ilValore.replaceAll("<strong>", "IniZioTagHtMlstrongFinETagHtMl");
		ilValore = ilValore.replaceAll("</strong>", "IniZioTagHtMl/strongFinETagHtMl");
		ilValore = ilValore.replaceAll("<br>", "IniZioTagHtMlbrFinETagHtMl");
		ilValore = ilValore.replaceAll("<BR>", "IniZioTagHtMlbrFinETagHtMl");
		ilValore = (ilValore.replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		ilValore = (ilValore.replaceAll("IniZioTagHtMl", "<")).replaceAll("FinETagHtMl", ">");
		ilValore = (ilValore.replaceAll("LeViRGoleTte", "\\\""));
		ilValore = (ilValore.replaceAll("&amp;", "&"));

		return (ilValore.replaceAll("\n", returnChar)).trim();
	}

	public String valoreNodoHTML(String xPath, String returnChar, String spacesChar) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ilValore.trim();
		ilValore = ilValore.replaceAll("\"", "&quot;");
		ilValore = ilValore.replaceAll("<em>", "IniZioTagHtMlemFinETagHtMl");
		ilValore = ilValore.replaceAll("</em>", "IniZioTagHtMl/emFinETagHtMl");
		ilValore = ilValore.replaceAll("<EM>", "IniZioTagHtMlemFinETagHtMl");
		ilValore = ilValore.replaceAll("</EM>", "IniZioTagHtMl/emFinETagHtMl");
		ilValore = ilValore.replaceAll("<P>", "IniZioTagHtMlpFinETagHtMl");
		ilValore = ilValore.replaceAll("</P>", "IniZioTagHtMl/pFinETagHtMl");
		ilValore = ilValore.replaceAll("<p>", "IniZioTagHtMlpFinETagHtMl");
		ilValore = ilValore.replaceAll("</p>", "IniZioTagHtMl/pFinETagHtMl");
		ilValore = ilValore.replaceAll("<STRONG>", "IniZioTagHtMlstrongFinETagHtMl");
		ilValore = ilValore.replaceAll("</STRONG>", "IniZioTagHtMl/strongFinETagHtMl");
		ilValore = ilValore.replaceAll("<strong>", "IniZioTagHtMlstrongFinETagHtMl");
		ilValore = ilValore.replaceAll("</strong>", "IniZioTagHtMl/strongFinETagHtMl");
		ilValore = ilValore.replaceAll("<br>", "IniZioTagHtMlbrFinETagHtMl");
		ilValore = ilValore.replaceAll("<BR>", "IniZioTagHtMlbrFinETagHtMl");
		ilValore = (ilValore.replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		ilValore = (ilValore.replaceAll("IniZioTagHtMl", "<")).replaceAll("FinETagHtMl", ">");
		ilValore = (ilValore.replaceAll("LeViRGoleTte", "\\\""));
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		ilValore = ilValore.replaceAll("  ", spacesChar);
		return (ilValore.replaceAll("\n", returnChar)).trim();
	}

	public String valoreNodoHTML(String xPath) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ilValore.trim();
		ilValore = ilValore.replaceAll("\"", "&quot;");
		ilValore = ilValore.replaceAll("<em>", "IniZioTagHtMlemFinETagHtMl");
		ilValore = ilValore.replaceAll("</em>", "IniZioTagHtMl/emFinETagHtMl");
		ilValore = ilValore.replaceAll("<EM>", "IniZioTagHtMlemFinETagHtMl");
		ilValore = ilValore.replaceAll("</EM>", "IniZioTagHtMl/emFinETagHtMl");
		ilValore = ilValore.replaceAll("<P>", "IniZioTagHtMlpFinETagHtMl");
		ilValore = ilValore.replaceAll("</P>", "IniZioTagHtMl/pFinETagHtMl");
		ilValore = ilValore.replaceAll("<p>", "IniZioTagHtMlpFinETagHtMl");
		ilValore = ilValore.replaceAll("</p>", "IniZioTagHtMl/pFinETagHtMl");
		ilValore = ilValore.replaceAll("<STRONG>", "IniZioTagHtMlstrongFinETagHtMl");
		ilValore = ilValore.replaceAll("</STRONG>", "IniZioTagHtMl/strongFinETagHtMl");
		ilValore = ilValore.replaceAll("<strong>", "IniZioTagHtMlstrongFinETagHtMl");
		ilValore = ilValore.replaceAll("</strong>", "IniZioTagHtMl/strongFinETagHtMl");
		ilValore = ilValore.replaceAll("<br>", "IniZioTagHtMlbrFinETagHtMl");
		ilValore = ilValore.replaceAll("<BR>", "IniZioTagHtMlbrFinETagHtMl");
		ilValore = (ilValore.replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		ilValore = (ilValore.replaceAll("LeViRGoleTte", "\\\""));
		ilValore = (ilValore.replaceAll("IniZioTagHtMl", "<")).replaceAll("FinETagHtMl", ">");
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		return ilValore.trim();
	}

	public String valoreNodo(String xPath, String returnChar, String toHighlight) throws TransformerException, UnsupportedEncodingException {
		String ilValore = getValore(xPath);
		ilValore = ((ilValore.replaceAll("\"", "&#34;")).replaceAll("&#60;", "<")).replaceAll("&#62;", ">");
		String[] strToHighlight = toHighlight.split(",");
		for (int i = 0; i < strToHighlight.length; i++) {
			ilValore = ilValore.replaceAll(strToHighlight[i], "<span class=\"chiaveEvidenziata\">" + strToHighlight[i] + "</span>");
		}
		// ilValore = ((ilValore.replaceAll("&#34;","\"")).replaceAll("&#60;","<")).replaceAll("&#62;",">");
		ilValore = ilValore.replaceAll(getSpanIni(), "");
		ilValore = ilValore.replaceAll(getSpanFine(), "");
		ilValore = (ilValore.replaceAll("&amp;", "&"));
		return (ilValore.replaceAll("\n", returnChar)).trim();
	}

	public String toString() {
		try {
			return getXML("ISO-8859-1");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private String parseAttribute(String xml) {
		String[] strings = xml.split("[\\<]");
		String result = "";
		for (int i = 0; i < strings.length; i++) {
			if (!strings[i].trim().equals("")) {
				String node = "<" + strings[i];
				String toTest = node.substring(node.indexOf("<"), node.indexOf(">") + 1);
				if (toTest.indexOf("\"") != -1) {// se ha attributi
					char[] chars = toTest.toCharArray();
					Vector vector = new Vector();
					for (int j = 0; j < chars.length; j++) {
						char c = chars[j];
						if (c == '<' || c == '>' || c == '=' || c == '"') {
							vector.addElement(new CharUtil(new Character(c).toString(), j));
						}
					}
					int lastPosition = -1;
					boolean start = false;
					for (int j = 0; j < vector.size(); j++) {
						CharUtil charUtil = (CharUtil) vector.elementAt(j);
						if (charUtil.c.equals("\"")) {
							if (start == false) {
								start = true;
							} else {
								lastPosition = j;
								charUtil.c = "&quot;";
								vector.set(j, charUtil);
							}
						} else if (charUtil.c.equals("=") || charUtil.c.equals(">")) {
							if (lastPosition != -1) {
								((CharUtil) vector.elementAt(lastPosition)).c = "\"";
								start = false;
							}
						}
					}
					String parsed = "";
					for (int k = 0; k < chars.length; k++) {
						boolean addChar = true;
						String addString = "";
						for (int j = 0; j < vector.size(); j++) {
							CharUtil charUtil = (CharUtil) vector.elementAt(j);
							if (k == charUtil.position) {
								addString = charUtil.c;
								addChar = false;
								break;
							} else {
								addChar = true;
							}
						}
						if (addChar)
							parsed += chars[k];
						else
							parsed += addString;
					}
					result += parsed;
				} else {
					result += toTest;
				}
				try {
					result += node.substring(node.indexOf(">") + 1, node.length());
				} catch (StringIndexOutOfBoundsException e) {
				}
			}
		}
		return result;
	}

	private class CharUtil {
		protected String c;

		protected int position = -1;

		public CharUtil(String c, int position) {
			this.c = c;
			this.position = position;
		}
	}

	public String getTheTerm() {
		return theTerm;
	}

	public void setTheTerm(String theTerm) {
		this.theTerm = theTerm;
	}

	public String getHtmlTagClass() {
		return htmlTagClass;
	}

	public String getSpanFine() {
		return spanFine;
	}

	public void setSpanFine(String spanFine) {
		this.spanFine = spanFine;
	}

	public String getSpanIni() {
		return spanIni;
	}

	public void setSpanIni(String spanIni) {
		this.spanIni = spanIni;
	}

}
