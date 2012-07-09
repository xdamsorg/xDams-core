package org.xdams.utility;

import org.xdams.user.bean.UserBean;
import org.xdams.xml.builder.XMLBuilder;

public class XMLCopy {

	public static String xmlCopy(XMLBuilder builderConf, XMLBuilder builderOriginal) throws Exception {
		int contaElementi = builderConf.contaNodi("/root/param/elemento[@id='clona']/opzione");
		if (contaElementi == 0) {
			throw new Exception("COPIA NON IMPOSTATO");
		}
		try {
			for (int i = 0; i < contaElementi; i++) {
				String elementoAction = builderConf.valoreNodo("/root/param/elemento[@id='clona']/opzione[" + (i + 1) + "]/@action");
				String elementoValue = builderConf.valoreNodo("/root/param/elemento[@id='clona']/opzione[" + (i + 1) + "]/@value");
				String elementoXPath = builderConf.valoreNodo("/root/param/elemento[@id='clona']/opzione[" + (i + 1) + "]/text()");
				if (elementoXPath.equals("")) {
					throw new Exception("elementoXPath NON IMPOSTATO ");
				}
				if (elementoAction.equals("")) {
					throw new Exception("elementoAction NON IMPOSTATO ");
				}
				if (elementoAction.equals("append")) {
					builderOriginal.insertValueAt(elementoXPath, builderOriginal.valoreNodo(elementoXPath) + elementoValue);
				} else if (elementoAction.equals("replace")) {
					builderOriginal.insertValueAt(elementoXPath, elementoValue);
				} else if (elementoAction.equals("delete")) {
					builderOriginal.deleteNode(elementoXPath);
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return builderOriginal.getXML("ISO-8859-1");
	}

	public static String xmlCopy(XMLBuilder builderConf, XMLBuilder builderOriginal, UserBean userBean) throws Exception {
		int contaElementi = builderConf.contaNodi("/root/param/elemento[@id='clona']/opzione");
		if (contaElementi == 0) {
			throw new Exception("COPIA NON IMPOSTATO");
		}
		try {
			for (int i = 0; i < contaElementi; i++) {
				String elementoAction = builderConf.valoreNodo("/root/param/elemento[@id='clona']/opzione[" + (i + 1) + "]/@action");
				String elementoValue = builderConf.valoreNodo("/root/param/elemento[@id='clona']/opzione[" + (i + 1) + "]/@value");
				String elementoXPath = builderConf.valoreNodo("/root/param/elemento[@id='clona']/opzione[" + (i + 1) + "]/text()");
				if (elementoXPath.equals("")) {
					throw new Exception("elementoXPath NON IMPOSTATO ");
				}
				if (elementoAction.equals("")) {
					throw new Exception("elementoAction NON IMPOSTATO ");
				}

				// value="systemdate" o SYSTEMDATE per mettere la data di copia
				// value="username" o USERNAME per mettere il nome di chi fa la copia
				if ((elementoValue.toLowerCase()).equals("systemdate")) {
					elementoValue = DateUtil.getDataSystem(null);
				}
				if ((elementoValue.toLowerCase()).equals("username")) {
					elementoValue = userBean.getName() + " " + userBean.getLastName();
				}

				if (elementoAction.equals("append")) {
					builderOriginal.insertValueAt(elementoXPath, builderOriginal.valoreNodo(elementoXPath) + " " + elementoValue);
				} else if (elementoAction.equals("replace")) {
					builderOriginal.insertValueAt(elementoXPath, elementoValue);
				} else if (elementoAction.equals("delete")) {
					builderOriginal.deleteNode(elementoXPath);
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return builderOriginal.getXML("ISO-8859-1");
	}

}
