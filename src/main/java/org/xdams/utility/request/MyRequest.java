package org.xdams.utility.request;

/*
 * Created on 21-apr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author diego
 * 
 *         TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class MyRequest {

	public HttpServletRequest httpServletRequest = null;

	public Map<String, String[]> parameterMap = null;

	public static void main(String[] args) {

	}

	public MyRequest(HttpServletRequest laRequest) {
		this.httpServletRequest = laRequest;
	}

	public MyRequest(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public static String getParameter(String paramName, Map<String, String[]> parameterMap) {
		String paramValue = "";
		if (parameterMap.get(paramName) != null) {
			paramValue = parameterMap.get(paramName)[0];
		}
		return paramValue;
	}

	public static String getParameter(String paramName, String defaultValue, Map<String, String[]> parameterMap) {
		String paramValue = "";
		if (parameterMap.get(paramName) != null && !parameterMap.get(paramName)[0].equals("")) {
			paramValue = parameterMap.get(paramName)[0];
		} else {
			paramValue = defaultValue;
		}
		return paramValue;
	}

	public static String getParameter(String paramName, HttpServletRequest request) {
		String paramValue = "";
		if (request.getParameter(paramName) != null) {
			paramValue = request.getParameter(paramName);
		}
		return paramValue;
	}

	public static Object getAttribute(String paramName, HttpServletRequest request) {
		return request.getAttribute(paramName);
	}

	public static String getParameter(String paramName, String defaultValue, HttpServletRequest request) {
		String paramValue = "";
		if (request.getParameter(paramName) != null) {
			paramValue = request.getParameter(paramName);
		} else {
			paramValue = defaultValue;
		}
		return paramValue;
	}

	public String getParameter(String nomeParametro) {
		String valoreParametro = "";
		if (httpServletRequest != null) {
			if (httpServletRequest.getParameter(nomeParametro) != null) {
				valoreParametro = httpServletRequest.getParameter(nomeParametro);
			}

		} else if (parameterMap != null) {
			if (parameterMap.get(nomeParametro) != null) {
				valoreParametro = parameterMap.get(nomeParametro)[0];
			}
		}
		return valoreParametro;
	}

	public String getParameter(String nomeParametro, String valoreDefault) {
		String valoreParametro = "";
		if (httpServletRequest.getParameter(nomeParametro) != null) {
			valoreParametro = httpServletRequest.getParameter(nomeParametro);
		} else {
			valoreParametro = valoreDefault;
		}
		return valoreParametro;
	}

	public static String[] ordinaRequest(HttpServletRequest laRequest) {

		Enumeration elementi = laRequest.getParameterNames();
		int ilContatore = 0;
		String[] nomiRequest = new String[ilContatore];
		ilContatore = 0;
		while (elementi.hasMoreElements()) {
			String nome = (String) elementi.nextElement();

			nomiRequest[ilContatore] = nome;
			ilContatore++;

		}

		Arrays.sort(nomiRequest);

		return nomiRequest;

	}

	public static String[] ordinaRequest(HttpServletRequest laRequest, String filter) {

		String[] nomiRequest = null;
		try {

			Enumeration elementi = laRequest.getParameterNames();

			int ilContatore = 0;
			while (elementi.hasMoreElements()) {
				if (((String) elementi.nextElement()).indexOf(filter) == 0) {
					ilContatore++;
				}
			}
			elementi = laRequest.getParameterNames();
			nomiRequest = new String[ilContatore];
			ilContatore = 0;
			boolean testOrdina = false;
			boolean testOrdinaCentinaia = false;

			while (elementi.hasMoreElements()) {
				String nome = (String) elementi.nextElement();
				if (nome.indexOf(filter) == 0) {
					String nomeTest = nome.replaceAll("[0-9]", "*");

					if (nomeTest.indexOf("[***]") > 0) {
						testOrdinaCentinaia = true;
					}

					if (nomeTest.indexOf("[**]") > 0) {
						testOrdina = true;
					}

					nomiRequest[ilContatore] = nome;
					// System.out.println(nomiRequest[ilContatore]);
					ilContatore++;
				}
			}

			if (testOrdina && !testOrdinaCentinaia) {
				for (int i = 0; i < nomiRequest.length; i++) {
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[0\\]", "\\[00\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[1\\]", "\\[01\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[2\\]", "\\[02\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[3\\]", "\\[03\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[4\\]", "\\[04\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[5\\]", "\\[05\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[6\\]", "\\[06\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[7\\]", "\\[07\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[8\\]", "\\[08\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[9\\]", "\\[09\\]");
				}
			}

			if (testOrdinaCentinaia) {
				for (int i = 0; i < nomiRequest.length; i++) {
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[0\\]", "\\[000\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[1\\]", "\\[001\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[2\\]", "\\[002\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[3\\]", "\\[003\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[4\\]", "\\[004\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[5\\]", "\\[005\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[6\\]", "\\[006\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[7\\]", "\\[007\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[8\\]", "\\[008\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[9\\]", "\\[009\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[10\\]", "\\[010\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[11\\]", "\\[011\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[12\\]", "\\[012\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[13\\]", "\\[013\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[14\\]", "\\[014\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[15\\]", "\\[015\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[16\\]", "\\[016\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[17\\]", "\\[017\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[18\\]", "\\[018\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[19\\]", "\\[019\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[20\\]", "\\[020\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[21\\]", "\\[021\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[22\\]", "\\[022\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[23\\]", "\\[023\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[24\\]", "\\[024\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[25\\]", "\\[025\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[26\\]", "\\[026\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[27\\]", "\\[027\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[28\\]", "\\[028\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[29\\]", "\\[029\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[30\\]", "\\[030\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[31\\]", "\\[031\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[32\\]", "\\[032\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[33\\]", "\\[033\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[34\\]", "\\[034\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[35\\]", "\\[035\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[36\\]", "\\[036\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[37\\]", "\\[037\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[38\\]", "\\[038\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[39\\]", "\\[039\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[40\\]", "\\[040\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[41\\]", "\\[041\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[42\\]", "\\[042\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[43\\]", "\\[043\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[44\\]", "\\[044\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[45\\]", "\\[045\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[46\\]", "\\[046\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[47\\]", "\\[047\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[48\\]", "\\[048\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[49\\]", "\\[049\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[50\\]", "\\[050\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[51\\]", "\\[051\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[52\\]", "\\[052\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[53\\]", "\\[053\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[54\\]", "\\[054\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[55\\]", "\\[055\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[56\\]", "\\[056\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[57\\]", "\\[057\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[58\\]", "\\[058\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[59\\]", "\\[059\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[60\\]", "\\[060\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[61\\]", "\\[061\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[62\\]", "\\[062\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[63\\]", "\\[063\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[64\\]", "\\[064\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[65\\]", "\\[065\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[66\\]", "\\[066\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[67\\]", "\\[067\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[68\\]", "\\[068\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[69\\]", "\\[069\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[70\\]", "\\[070\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[71\\]", "\\[071\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[72\\]", "\\[072\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[73\\]", "\\[073\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[74\\]", "\\[074\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[75\\]", "\\[075\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[76\\]", "\\[076\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[77\\]", "\\[077\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[78\\]", "\\[078\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[79\\]", "\\[079\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[80\\]", "\\[080\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[81\\]", "\\[081\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[82\\]", "\\[082\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[83\\]", "\\[083\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[84\\]", "\\[084\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[85\\]", "\\[085\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[86\\]", "\\[086\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[87\\]", "\\[087\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[88\\]", "\\[088\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[89\\]", "\\[089\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[90\\]", "\\[090\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[91\\]", "\\[091\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[92\\]", "\\[092\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[93\\]", "\\[093\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[94\\]", "\\[094\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[95\\]", "\\[095\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[96\\]", "\\[096\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[97\\]", "\\[097\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[98\\]", "\\[098\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[99\\]", "\\[099\\]");
				}
			}

			Arrays.sort(nomiRequest);

			if (testOrdina && !testOrdinaCentinaia) {
				for (int i = 0; i < nomiRequest.length; i++) {
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[00\\]", "\\[0\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[01\\]", "\\[1\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[02\\]", "\\[2\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[03\\]", "\\[3\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[04\\]", "\\[4\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[05\\]", "\\[5\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[06\\]", "\\[6\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[07\\]", "\\[7\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[08\\]", "\\[8\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[09\\]", "\\[9\\]");
				}
			}

			if (testOrdinaCentinaia) {
				for (int i = 0; i < nomiRequest.length; i++) {
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[000\\]", "\\[0\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[001\\]", "\\[1\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[002\\]", "\\[2\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[003\\]", "\\[3\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[004\\]", "\\[4\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[005\\]", "\\[5\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[006\\]", "\\[6\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[007\\]", "\\[7\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[008\\]", "\\[8\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[009\\]", "\\[9\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[010\\]", "\\[10\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[011\\]", "\\[11\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[012\\]", "\\[12\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[013\\]", "\\[13\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[014\\]", "\\[14\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[015\\]", "\\[15\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[016\\]", "\\[16\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[017\\]", "\\[17\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[018\\]", "\\[18\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[019\\]", "\\[19\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[020\\]", "\\[20\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[021\\]", "\\[21\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[022\\]", "\\[22\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[023\\]", "\\[23\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[024\\]", "\\[24\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[025\\]", "\\[25\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[026\\]", "\\[26\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[027\\]", "\\[27\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[028\\]", "\\[28\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[029\\]", "\\[29\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[030\\]", "\\[30\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[031\\]", "\\[31\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[032\\]", "\\[32\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[033\\]", "\\[33\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[034\\]", "\\[34\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[035\\]", "\\[35\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[036\\]", "\\[36\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[037\\]", "\\[37\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[038\\]", "\\[38\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[039\\]", "\\[39\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[040\\]", "\\[40\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[041\\]", "\\[41\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[042\\]", "\\[42\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[043\\]", "\\[43\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[044\\]", "\\[44\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[045\\]", "\\[45\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[046\\]", "\\[46\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[047\\]", "\\[47\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[048\\]", "\\[48\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[049\\]", "\\[49\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[050\\]", "\\[50\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[051\\]", "\\[51\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[052\\]", "\\[52\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[053\\]", "\\[53\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[054\\]", "\\[54\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[055\\]", "\\[55\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[056\\]", "\\[56\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[057\\]", "\\[57\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[058\\]", "\\[58\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[059\\]", "\\[59\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[060\\]", "\\[60\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[061\\]", "\\[61\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[062\\]", "\\[62\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[063\\]", "\\[63\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[064\\]", "\\[64\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[065\\]", "\\[65\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[066\\]", "\\[66\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[067\\]", "\\[67\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[068\\]", "\\[68\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[069\\]", "\\[69\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[070\\]", "\\[70\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[071\\]", "\\[71\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[072\\]", "\\[72\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[073\\]", "\\[73\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[074\\]", "\\[74\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[075\\]", "\\[75\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[076\\]", "\\[76\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[077\\]", "\\[77\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[078\\]", "\\[78\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[079\\]", "\\[79\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[080\\]", "\\[80\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[081\\]", "\\[81\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[082\\]", "\\[82\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[083\\]", "\\[83\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[084\\]", "\\[84\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[085\\]", "\\[85\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[086\\]", "\\[86\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[087\\]", "\\[87\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[088\\]", "\\[88\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[089\\]", "\\[89\\]");

					nomiRequest[i] = nomiRequest[i].replaceAll("\\[090\\]", "\\[90\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[091\\]", "\\[91\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[092\\]", "\\[92\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[093\\]", "\\[93\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[094\\]", "\\[94\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[095\\]", "\\[95\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[096\\]", "\\[96\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[097\\]", "\\[97\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[098\\]", "\\[98\\]");
					nomiRequest[i] = nomiRequest[i].replaceAll("\\[099\\]", "\\[99\\]");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return nomiRequest;

	}

	/**
	 * @return Returns the httpServletRequest.
	 */
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	/**
	 * @param httpServletRequest
	 *            The httpServletRequest to set.
	 */
	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

}