package org.xdams.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.xdams.page.view.bean.XSLBean;

/**
 * @author sandro
 * 
 *         Per modificare il modello associato al commento di questo tipo generato, aprire Finestra&gt;Preferenze&gt;Java&gt;Generazione codice&gt;Codice e commenti
 */
public class XSLLoader {

	public static List<XSLBean> loadXSL(String xslDir) {
		List<XSLBean> result = null;
		File file = new File(xslDir);
//		System.out.println(xslDir);
		if (file.isDirectory()) {
			result = new ArrayList();
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith(".xsl")) {
					XSLBean xSLBean = new XSLBean();
					xSLBean.setFileName(files[i].getName());
					xSLBean.setLabel(StringUtils.replace(files[i].getName().substring(0, files[i].getName().trim().indexOf(".xsl")), "_", " "));
					result.add(xSLBean);
				}
			}
		}
		return result;
	}

}
