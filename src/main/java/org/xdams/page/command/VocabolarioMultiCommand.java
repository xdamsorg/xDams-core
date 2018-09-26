package org.xdams.page.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.im4java.core.CommandException;
import org.springframework.ui.ModelMap;
import org.xdams.conf.master.ConfBean;
import org.xdams.user.access.ServiceUser;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xmlengine.connection.manager.ConnectionManager;
import org.xdams.xw.XWConnection;
import org.xdams.xw.exception.XWException;
import org.xdams.xw.utility.Key;
import org.xdams.xw.utility.XWComparator;

public class VocabolarioMultiCommand {

	private List<Key> vocabolarioList = null;

	private List<Key> originalList = null;

	private Map<String, Key> vocabolarioMap = null;

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public VocabolarioMultiCommand(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
		this.vocabolarioList = new ArrayList<Key>();
		this.originalList = new ArrayList<Key>();
		this.vocabolarioMap = new TreeMap<String, Key>();
	}

	public void execute() throws Exception {
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		int totNum = 0;
		boolean multi = true;
		String vocabolario_phrase = "";
		ConfBean confBean = null;
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		UserBean userBean = (UserBean) modelMap.get("userBean");
		try {
			Map<String, String> hashChekbox = null;
			if (workFlowBean.getRequest().getParameter("vocabolario_tocheck") != null) {
				if (workFlowBean.getRequest().getSession(false).getAttribute("hashChekbox_" + workFlowBean.getAlias()) != null) {
					hashChekbox = (Map) workFlowBean.getRequest().getSession(false).getAttribute("hashChekbox_" + workFlowBean.getAlias());
				} else {
					hashChekbox = new TreeMap<String, String>();
				}
				for (Entry<String, String[]> entry : parameterMap.entrySet()) {
					String nomeck = (String) entry.getKey();
					if (nomeck.trim().startsWith("vocabolario_ch"))
						hashChekbox.put(entry.getValue()[0], entry.getValue()[0]);
					if (nomeck.trim().startsWith("remove_vocabolario_ch") && hashChekbox.get(entry.getValue()[0]) != null)
						hashChekbox.remove(entry.getValue()[0]);
				}
				workFlowBean.getRequest().getSession(false).setAttribute("hashChekbox_" + workFlowBean.getAlias(), hashChekbox);
			} else {
				workFlowBean.getRequest().getSession(false).removeAttribute("hashChekbox_" + workFlowBean.getAlias());
			}

			System.out.println("MyRequest.getParameter(\"vocabolario_start_param\", parameterMap):" + MyRequest.getParameter("vocabolario_start_param", parameterMap));
			System.out.println("workFlowBean.getRequest().getParameter(\"vocabolario_start_param\"):" + workFlowBean.getRequest().getParameter("vocabolario_start_param"));

			vocabolario_phrase = MyRequest.getParameter("vocabolario_phrase", parameterMap);
			// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>vocabolario_phrase = "+vocabolario_phrase);
			workFlowBean.getRequest().setAttribute("vocabolario_alias", MyRequest.getParameter("vocabolario_alias", parameterMap));
			workFlowBean.getRequest().setAttribute("vocabolario_maxresult", MyRequest.getParameter("vocabolario_maxresult", parameterMap));
			workFlowBean.getRequest().setAttribute("vocabolario_up_down", MyRequest.getParameter("vocabolario_up_down", parameterMap));
			workFlowBean.getRequest().setAttribute("vocabolario_start_param", MyRequest.getParameter("vocabolario_start_param", parameterMap));
			workFlowBean.getRequest().setAttribute("vocabolario_tipology", MyRequest.getParameter("vocabolario_tipology", parameterMap));
			workFlowBean.getRequest().setAttribute("urlAfterSave", MyRequest.getParameter("urlAfterSave", parameterMap));
			workFlowBean.getRequest().setAttribute("urlErrorPage", MyRequest.getParameter("urlErrorPage", parameterMap));
			workFlowBean.getRequest().setAttribute("theArch", MyRequest.getParameter("theArch", parameterMap));
			workFlowBean.getRequest().setAttribute("vocabolario_return_id", MyRequest.getParameter("vocabolario_return_id", parameterMap));
			workFlowBean.getRequest().setAttribute("num_docs", MyRequest.getParameter("num_docs", parameterMap));

			String aliasMulti = MyRequest.getParameter("vocabolario_alias", parameterMap);
			System.out.println("aliasMulti " + aliasMulti);
			int totResult = Integer.parseInt(MyRequest.getParameter("vocabolario_maxresult", parameterMap));
			String orientation = "";
			String startParam = "";
			if (workFlowBean.getRequest().getAttribute("vocabolario_start_param") != null)
				startParam = (String) workFlowBean.getRequest().getAttribute("vocabolario_start_param");
			else
				startParam = MyRequest.getParameter("vocabolario_start_param", parameterMap);
			if (workFlowBean.getRequest().getAttribute("vocabolario_up_down") != null)
				orientation = (String) workFlowBean.getRequest().getAttribute("vocabolario_up_down");
			else
				orientation = MyRequest.getParameter("vocabolario_up_down", parameterMap);
			String part = "";
			String theArchs = MyRequest.getParameter("theArch", parameterMap);

			System.out.println("startParam: " + startParam);

			if (theArchs.indexOf(";") == theArchs.lastIndexOf(";")) {
				multi = false;
			}

			StringTokenizer stringTokenizer = new StringTokenizer(theArchs, ";");
			StringTokenizer stringTokenizer2 = new StringTokenizer(aliasMulti, ";");

			for (; stringTokenizer.hasMoreTokens(); xwconn.close()) {
				String theArch = stringTokenizer.nextToken();
				String alias = "";
				if (aliasMulti.indexOf(";") != -1)
					alias = stringTokenizer2.nextToken();
				else
					alias = aliasMulti;
				if (theArch != null)
					xwconn = connectionManager.getConnection(ServiceUser.getArchive(userBean, theArch));
				else
					throw new CommandException("Errore theArch = null");
				Vector result = null;
				// System.out.println("orienattion = "+orientation);
				try {
					String num_docs = MyRequest.getParameter("num_docs", parameterMap);
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>num_docs = "+num_docs);
					if (!MyRequest.getParameter("num_docs", parameterMap).equals("") && !num_docs.trim().equals("")) {
						if (vocabolario_phrase != null && vocabolario_phrase.indexOf("[?sel]") == -1) {
							if (vocabolario_phrase != null && !vocabolario_phrase.trim().equals("")) {
								vocabolario_phrase += " AND (";
							} else {
								vocabolario_phrase = "(";
							}
							StringTokenizer stringTokenizer3 = new StringTokenizer(num_docs, ";");
							while (stringTokenizer3.hasMoreTokens()) {
								try {
									int num_doc = Integer.parseInt(stringTokenizer3.nextToken());
									String selId = xwconn.getSelectionIdFormHier(num_doc);

									vocabolario_phrase += " [?sel]=\"" + selId + "\" or";
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
							if (vocabolario_phrase.endsWith("or")) {
								vocabolario_phrase = vocabolario_phrase.substring(0, vocabolario_phrase.lastIndexOf("or"));
							}
							vocabolario_phrase += ")";
						}
					}
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>vocabolario_phrase = "+vocabolario_phrase);
					if (vocabolario_phrase != null && !vocabolario_phrase.trim().equals("")) {
						System.out.println(vocabolario_phrase);
						// result = xwconn.getFilteredKeys(vocabolario_phrase.replace('\'', '"'), alias, totResult, orientation, startParam,part);
						result = xwconn.getFilteredKeys(vocabolario_phrase.replace('\'', '"'), alias, totResult, orientation, startParam, 1, xwconn.getTotNumDoc(), "", "frequenze|spettrale");
						if (result.size() < totResult && orientation.equalsIgnoreCase("down") && multi) {
							// System.out.println("ne devo prendere di più");
							Key key = (Key) result.elementAt(result.size() - 1);
							// Vector toAdd=xwconn.getFilteredKeys(vocabolario_phrase.replace('\'', '"'), alias, totResult-result.size(), "up", key.key.toString(),part);
							Vector toAdd = xwconn.getFilteredKeys(vocabolario_phrase.replace('\'', '"'), alias, totResult - result.size(), "up", key.key.toString(), 1, xwconn.getTotNumDoc(), "", "frequenze|spettrale");
							for (int i = 1; i < toAdd.size(); i++) {
								result.addElement(toAdd.elementAt(i));
							}
						}
					} else {
						System.out.println("STO IN SINGLEKEY QUANTOVALE startParam:" + startParam);
						result = xwconn.getSingleKeys(alias, totResult, orientation, startParam, part);
						if (result.size() < totResult && orientation.equalsIgnoreCase("down") && multi) {
							// System.out.println("ne devo prendere di più");
							Key key = (Key) result.elementAt(result.size() - 1);
							// System.out.println("parto dalla chiave "+key.key.toString());
							Vector toAdd = xwconn.getSingleKeys(alias, totResult - result.size(), "up", key.key.toString(), part);
							// System.out.println("e ne aggiungo "+toAdd.size());
							for (int i = 1; i < toAdd.size(); i++) {
								result.addElement(toAdd.elementAt(i));
							}
						}
					}
					totNum += xwconn.getTotNumDoc();

				} catch (Exception exception) {
				}
				if (result != null && result.size() != 0) {
					// System.out.println(theArch+" result.size() = "+result.size());
					for (int i = 0; i < result.size(); i++) {
						Key key = (Key) result.elementAt(i);
						originalList.add(key);
						// System.out.println(">>>>>>>>>>>> ="+key.key.toString());
						if (vocabolarioMap.get(key.toString()) != null) {
							Key oldKey = (Key) vocabolarioMap.get(key.toString());
							oldKey.frequence = oldKey.frequence + key.frequence;
							vocabolarioMap.put(key.toString(), oldKey);
						} else {
							vocabolarioMap.put(key.toString(), key);
						}
					}
				}
			}

			// for (Enumeration enumeration = vocabolarioMap.keys(); enumeration.hasMoreElements(); vocabolarioList.addElement(vocabolarioMap.get(enumeration.nextElement())))
			// ;

			for (Entry<String, Key> entry : vocabolarioMap.entrySet()) {
				vocabolarioList.add(vocabolarioMap.get(entry.getKey()));
			}
			List<String> newResult = new ArrayList<String>();
			// System.out.println("vocabolarioList.size() = "+vocabolarioList.size());
			for (int i = 0; i < vocabolarioList.size(); i++) {
				Key key = (Key) vocabolarioList.get(i);
				String keyFreq = key.key.toString() + "~" + Integer.toString(key.frequence);
				newResult.add(keyFreq);
			}
			String vocFirst = "";
			String vocLast = "";
			try {
				vocFirst = ((Key) originalList.get(0)).key.toString();
				vocLast = ((Key) originalList.get(originalList.size() - 1)).key.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object objs[] = newResult.toArray();
			// Arrays.sort(objs);
			// Arrays.sort(objs,Collator.getInstance(Locale.ITALIAN));
			Arrays.sort(objs, new XWComparator());
			// newResult.removeAllElements();
			newResult.clear();
			Map<String, Integer> summKeys = new TreeMap<String, Integer>();
			for (int i = 0; i < objs.length; i++) {
				String keyString = (String) objs[i];
				String sKey = keyString.substring(0, keyString.indexOf("~"));
				String sFrequence = keyString.substring(keyString.indexOf("~") + 1, keyString.length());
				int laFrequenza = Integer.parseInt(sFrequence);
				if (summKeys.get(sKey) != null) {
					summKeys.put(sKey, new Integer(((Integer) summKeys.get(sKey)).intValue() + laFrequenza));
				} else {
					summKeys.put(sKey, new Integer(laFrequenza));
				}
			}
			// String keyH;
			// for (Enumeration enumeration2 = summKeys.keys(); enumeration2.hasMoreElements(); newResult.add(keyH + "~" + ((Integer) summKeys.get(keyH)).toString()))
			// keyH = (String) enumeration2.nextElement();

			for (Entry<String, Integer> entry : summKeys.entrySet()) {
				String keyH = entry.getKey();
				newResult.add(keyH + "~" + ((Integer) summKeys.get(keyH)).toString());
			}
			objs = newResult.toArray();
			// Arrays.sort(objs);
			// Arrays.sort(objs,Collator.getInstance(Locale.ITALIAN));
			Arrays.sort(objs, new XWComparator());
			// newResult.removeAllElements();
			newResult.clear();

			List<Key> newResultKey = new ArrayList<Key>();
			// newResult = new ArrayList<String>();
			// System.out.println("objs.length = "+objs.length);
			if (orientation.equalsIgnoreCase("up") || !multi) {
				// System.out.println("avanti");
				for (int i = 0; i < objs.length; i++) {
					String keyString = (String) objs[i];
					String sKey = keyString.substring(0, keyString.indexOf("~"));
					String sFrequence = keyString.substring(keyString.indexOf("~") + 1, keyString.length());
					int laFrequenza = Integer.parseInt(sFrequence);
					Key key = new Key(sKey, laFrequenza);
					newResultKey.add(key);
					if (i == totResult - 1)
						break;
				}
			} else {

				if (objs.length >= totResult) {
					// System.out.println("indietro toto result"+totResult +" partendo da "+(objs.length-totResult));
					int start = 0;
					int end = objs.length;
					for (int i = 0; i < objs.length; i++) {
						String keyString = (String) objs[i];
						String sKey = keyString.substring(0, keyString.indexOf("~"));
						// System.out.println("sKey = |" + sKey +"| lo comparo con startParam = |"+ startParam+"|");
						if (sKey.trim().equalsIgnoreCase(startParam.trim())) {
							end = i + 1;
							if (i - totResult >= 0) {
								start = i - (totResult - 1);
							} else {
								end = totResult;
							}
							// System.out.println("ok sono uguali sKey e startParam");
							break;
						}
					}
					if (start < 0) {
						start = 0;
						end = start + totResult;
					}
					for (int i = start; i < end; i++) {
						// for(int i = objs.length-totResult; i < objs.length; i++){
						String keyString = (String) objs[i];
						String sKey = keyString.substring(0, keyString.indexOf("~"));
						System.out.println("le chiavi risultanti sono = " + sKey);
						String sFrequence = keyString.substring(keyString.indexOf("~") + 1, keyString.length());
						int laFrequenza = Integer.parseInt(sFrequence);
						Key key = new Key(sKey, laFrequenza);
						newResultKey.add(key);

					}
				}

			}

			String previous = "no";

			if (vocabolario_phrase != null && !vocabolario_phrase.trim().equals("")) {
				if (isFirstKey(theArchs, aliasMulti, ((Key) newResultKey.get(0)).key.toString(), startParam, vocabolario_phrase.trim().replace('\'', '"'), part)) {
					workFlowBean.getRequest().setAttribute("vocabolario_previous", previous);
					workFlowBean.getRequest().setAttribute("vocabolario_previous_string", vocFirst);
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>precedente no");
				} else {
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>precedente si");
				}
			} else {
				// System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
				if (isFirstKey(theArchs, aliasMulti, ((Key) newResultKey.get(0)).key.toString(), startParam, null, part)) {
					// if(isFirstKey(theArchs, aliasMulti, startParam,null,part)){
					workFlowBean.getRequest().setAttribute("vocabolario_previous", previous);
					workFlowBean.getRequest().setAttribute("vocabolario_previous_string", vocFirst);
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>precedente no");
				} else {
					// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>precedente si");
				}
			}
			vocabolarioList = newResultKey;
			String next = "";
			if (vocabolarioList.size() >= totResult)
				next = ((Key) vocabolarioList.get(vocabolarioList.size() - 1)).key.toString();
			workFlowBean.getRequest().setAttribute("vocabolario_next", next);
			workFlowBean.getRequest().setAttribute("vocabolario_next_string", vocLast);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		} catch (CommandException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (xwconn != null && !xwconn.isClosed())
					xwconn.close();
			} catch (XWException e1) {
				throw new CommandException(e1.toString());
			}
		}
		workFlowBean.getRequest().setAttribute("totNum", Integer.toString(totNum));
		workFlowBean.getRequest().setAttribute("vocabolario_phrase", vocabolario_phrase);
		// req.setAttribute("vocabolario_return_id", req.getParameter("vocabolario_return_id"));
		if (vocabolarioList != null) {
			workFlowBean.getRequest().setAttribute("vocabolarioList", vocabolarioList);
		}

	}

	private boolean isFirstKey(String theArchs, String aliasMulti, String param, String startParam, String filter, String part) throws CommandException {
		boolean result;
		result = true;
		XWConnection xwconn = null;
		ConnectionManager connectionManager = new ConnectionManager();
		WorkFlowBean workFlowBean = (WorkFlowBean) modelMap.get("workFlowBean");
		UserBean userBean = (UserBean) modelMap.get("userBean");
		try {
			StringTokenizer stringTokenizer = new StringTokenizer(theArchs, ";");
			StringTokenizer stringTokenizer2 = new StringTokenizer(aliasMulti, ";");
			String[] values = new String[stringTokenizer.countTokens()];
			int countBoolean = 0;
			while (stringTokenizer.hasMoreTokens()) {
				try {
					String theArch = stringTokenizer.nextToken();
					String alias = "";
					if (aliasMulti.indexOf(";") != -1)
						alias = stringTokenizer2.nextToken();
					else
						alias = aliasMulti;
					if (theArch != null)
						xwconn = connectionManager.getConnection(ServiceUser.getArchive(userBean, theArch));

					else
						throw new CommandException("Errore theArch = null");
					Vector first = null;
					if (filter == null)
						first = xwconn.getSingleKeys(alias, 2, "down", "", part);
					else
						// first = xwconn.getFilteredKeys(filter,alias, 2, "down", "",part);
						first = xwconn.getFilteredKeys(filter, alias, 2, "down", "", 1, xwconn.getTotNumDoc(), "", "frequenze|spettrale");
					if (first != null && first.size() > 0) {
						Key key = (Key) first.elementAt(0);
						values[countBoolean] = key.key.toString().trim();
					} else {
						values[countBoolean] = "zzzzzzzzzzzzzzzzzz";
					}
					xwconn.close();
					countBoolean++;
				} catch (SQLException e) {
					try {
						if (xwconn != null && !xwconn.isClosed())
							xwconn.close();
					} catch (XWException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} catch (XWException e) {
					try {
						if (xwconn != null && !xwconn.isClosed())
							xwconn.close();
					} catch (XWException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
			// Arrays.sort(values);
			Arrays.sort(values, new XWComparator());
			// Arrays.sort(values,Collator.getInstance(Locale.ITALIAN));
			// System.out.println(param.trim()+" = "+values[0]+" = "+startParam);
			if (param.trim().equalsIgnoreCase(values[0])) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (xwconn != null && !xwconn.isClosed())
					xwconn.close();
			} catch (XWException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}

}