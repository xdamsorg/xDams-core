package org.xdams.jstl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.xdams.conf.master.ConfBean;
import org.xdams.manager.conf.MultiEditingManager;
import org.xdams.page.view.bean.EditingBean;
import org.xdams.page.view.bean.ViewBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.ExpressionEvaluationUtils;
import org.xdams.utility.reflection.ReflectionUtil;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;

public class MenuGestioneJstl extends TagSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String confFile = "";

	private String theMultiEditingPath = "managing";

	public int doStartTag() {
		try {

			ConfBean confBean = (ConfBean) pageContext.findAttribute("confBean");

			UserBean userBean = (UserBean) pageContext.findAttribute("userBean");

			ViewBean viewBean = (ViewBean) pageContext.findAttribute("viewBean");

			EditingBean editingBean = (EditingBean) pageContext.findAttribute("editingBean");

			WorkFlowBean workFlowBean = (WorkFlowBean) pageContext.findAttribute("workFlowBean");

			JspWriter out = pageContext.getOut();

			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			if (request.getParameter("infoFlag") != null && request.getParameter("infoFlag").equals("true")) {
				return EVAL_BODY_INCLUDE;
			}

			MultiEditingManager editingManager = new MultiEditingManager(request.getParameterMap(), confBean, userBean, workFlowBean);
			if (editingBean != null) {
				editingManager.setTheXML(editingBean.getXmlBuilder());
			} else if (viewBean != null) {
				editingManager.setTheXML(viewBean.getXmlBuilder());
			}

			XMLBuilder builderManaging = null;
			if (getConfFile().equals("bar-vis")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarVis();
			} else if (getConfFile().equals("bar-query")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarQuery();
			} else if (getConfFile().equals("bar-preinsert")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarPreInsert();
			} else if (getConfFile().equals("bar-nav")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarNav();
			} else if (getConfFile().equals("bar-managing")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarManaging();
			} else if (getConfFile().equals("bar-edt")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarEdt();
			} else if (getConfFile().equals("bar-docedit")) {
				List<String> confControl = new ArrayList<String>();
				confControl.add(getConfFile());
				confBean = editingManager.rewriteMultipleConf(confControl);
				builderManaging = confBean.getTheXMLConfBarDocEdit();
			} else {
				builderManaging = new XMLBuilder("root");
			}

			// System.out.println(ExpressionEvaluationUtils.evaluateString(builderManaging.getXML("ISO-8859-1"), builderManaging.getXML("ISO-8859-1"), pageContext));

			// XMLBuilder builderManaging = editingManager.genericMultipleConf(getConfFile(), getTheMultiEditingPath());
			// List<String> confControl = new ArrayList<String>();
			// confControl.add("titleManager");
			// confControl.add("query");
			// editingManager.rewriteMultipleConf(confControl);

			// System.out.println(getConfFile());
			// System.out.println(getTheMultiEditingPath());
			int contaNodi = builderManaging.contaNodi("/root/managing/user/element");
			for (int i = 0; i < contaNodi; i++) {
				String functionTxt = "";
				String anchorTxt = "";
				String targetTxt = "";
				String txtAhref = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/@value");
				int contaEventJavaScript = builderManaging.contaNodi("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript");
				int contaAnchor = builderManaging.contaNodi("/root/managing/user/element[" + (i + 1) + "]/anchor");
				String isVisible = "true";

				String testMethod = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/@testMethod");
				String testClass = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/@testClass");
				String userValue = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/@userValue");
				String xPathTest = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/@xPathTest");
				// questo lo devo usare quando ho tempo di farlo
				String[] xPathTestSplit = xPathTest.split(";");
				String[] userValueSplit = userValue.split(";");
				// out.println("userValueSplit "+userValueSplit);
				// out.println("userValueSplit "+userValueSplit.length);
				// out.println("userValue "+userValue);
				if (!testMethod.equals("") && !testClass.equals("")) {
					try {
						Class constructorParamDef[] = {};
						Object constructorParam[] = {};
						Class c = Class.forName(testClass);
						Constructor theConstructor = c.getConstructor(constructorParamDef);
						Object myobj = theConstructor.newInstance(constructorParam);
						Method[] m = c.getDeclaredMethods();
						// ClassUtils.getPublicMethod(arg0, arg1, arg2);
						Object objValue = null;
						Object objBean = null;
						if (testClass.toUpperCase().indexOf("VIEWBEAN") != -1) {
							objBean = viewBean;
						} else if (testClass.toUpperCase().indexOf("EDITINGBEAN") != -1) {
							objBean = editingBean;
						} else if (testClass.toUpperCase().indexOf("TESTINGGENERIC") != -1) {
							objBean = null;
						}
						for (int j = 0; j < m.length; j++) {
							// System.out.println("mmmm " + m[j]);
							// System.out.println("mmmm " + m[j].getName());
							if (m[j].getName().equals(testMethod)) {
								if (m[j].getParameterTypes().length > 3) {
									objValue = m[j].invoke(myobj, new Object[] { objBean, workFlowBean, userValueSplit, xPathTest });
									break;
								} else {
									objValue = m[j].invoke(myobj, new Object[] { objBean, workFlowBean, userValueSplit });
									break;
								}
							}
						}
						if (objValue != null) {
							// out.println("objValue " + objValue);
							isVisible = objValue.toString();
							// out.println("isVisible " + isVisible);
						}
					} catch (Exception e) {
						out.println("ERRORE 1" + e.getMessage());
						isVisible = "false";
					}

				}

				if (isVisible.equals("true")) {
					ReflectionUtil reflectionUtil = new ReflectionUtil();
					if (contaAnchor == 1) {

						for (int j = 0; j < contaAnchor; j++) {
							String anchor = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/@value");
							int contaParamAnchor = builderManaging.contaNodi("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/paramAnchor");
							String interrogativo = "?";
							if (anchor.indexOf("#") != -1 || contaParamAnchor == 0) {
								interrogativo = "";
							}
							// if (ExpressionEvaluationUtils.isExpressionLanguage(anchor)) {
							// anchor = ExpressionEvaluationUtils.evaluateString(anchor, anchor, pageContext);
							// }

							anchor = (String) ExpressionEvaluationUtils.evaluate(anchor, String.class, pageContext);

							anchorTxt += "href=\"" + anchor + interrogativo;
							for (int k = 0; k < contaParamAnchor; k++) {
								String paramNameAnchor = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/paramAnchor[" + (k + 1) + "]/@name");
								String javaBean = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/paramAnchor[" + (k + 1) + "]/@javaBean");
								String paramValue = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/paramAnchor[" + (k + 1) + "]/@paramValue");
								String methodBean = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/paramAnchor[" + (k + 1) + "]/text()");
								int paramValueNum = paramValue.split(",").length;
								Object[] methodParam = null;
								Object methodValue = null;
								if (!paramValue.equals("") && paramValueNum > 0) {
									// System.out.println("MenuGestioneJstl.doStartTag() MAGGIORE DI ZEROOOOOOOOOO paramValueNum " + paramValueNum);
									// System.out.println("MenuGestioneJstl.doStartTag() MAGGIORE DI ZEROOOOOOOOOO paramValue " + paramValue);
									methodParam = new Object[paramValueNum];
									for (int index = 0; index < paramValueNum; index++) {
										// System.out.println("MenuGestioneJstl.doStartTag() paramValue.split(\",\")[index] " + paramValue.split(",")[index]);
										methodParam[index] = paramValue.split(",")[index];// getObjectFromString("java.lang.String", paramValue.split(",")[index]);
										// System.out.println("MenuGestioneJstl.doStartTag() methodParam " + methodParam);
									}
								}
								// methodParam = null;

								if (!javaBean.equals("")) {
									Class c = null;
									if (javaBean.toUpperCase().equals("VIEWBEAN")) {
										methodValue = reflectionUtil.invokeMethod(viewBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("CONFBEAN")) {
										methodValue = reflectionUtil.invokeMethod(confBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("USERBEAN")) {
										methodValue = reflectionUtil.invokeMethod(userBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("EDITINGBEAN")) {
										methodValue = reflectionUtil.invokeMethod(editingBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("WORKFLOWBEAN")) {
										methodValue = reflectionUtil.invokeMethod(workFlowBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("REQUEST")) {
										methodValue = request.getParameter(methodBean);
									}
								} else {
									methodValue = methodBean;
								}
								String endCom = "&amp;";
								if (k == contaParamAnchor - 1) {
									endCom = "";
								}
								anchorTxt += paramNameAnchor + "=" + methodValue + endCom;
//								System.out.println("MenuGestioneJstl.doStartTag()anchorTxt anchorTxt " + anchorTxt);
								if (anchorTxt.endsWith("?")) {
//									System.out.println("MenuGestioneJstl.doStartTag()AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
									anchorTxt = anchorTxt.substring(0, anchorTxt.length() - 1);

								}

							}
							anchorTxt += "\"";
							String target = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/anchor[" + (j + 1) + "]/@target");
							if (!target.equals("")) {
								targetTxt = "target=\"" + ExpressionEvaluationUtils.evaluate(target, String.class, pageContext) + "\"";
							}

						}
					} else if (contaAnchor == 0 && contaEventJavaScript > 0) {
						anchorTxt = "href=\"javascript:void(0)\"";
					} else {
						throw new Error("Qualcosa e andato storto. Anchor maggiore di uno");
					}

					// out.println("contaEventJavaScript " + contaEventJavaScript);
					for (int j = 0; j < contaEventJavaScript; j++) {
						String eventJavaScript = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/@value");
						// out.println("eventJavaScripty " + eventJavaScript);
						eventJavaScript += "=\"";
						int contaFunctionName = builderManaging.contaNodi("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName");
						for (int k = 0; k < contaFunctionName; k++) {
							String functionName = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName[" + (k + 1) + "]/@value");
							// out.println("functionName " + functionName);
							eventJavaScript += functionName + "(";
							int contaParamFunction = builderManaging.contaNodi("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName[" + (k + 1) + "]/paramFunction");
							for (int index = 0; index < contaParamFunction; index++) {
								String javaBean = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName[" + (k + 1) + "]/paramFunction[" + (index + 1) + "]/@javaBean");
								String methodBean = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName[" + (k + 1) + "]/paramFunction[" + (index + 1) + "]/text()");
								String paramValue = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName[" + (k + 1) + "]/paramFunction[" + (index + 1) + "]/@paramValue");
								String escapeSingleApex = builderManaging.valoreNodo("/root/managing/user/element[" + (i + 1) + "]/eventJavaScript[" + (j + 1) + "]/functionName[" + (k + 1) + "]/paramFunction[" + (index + 1) + "]/@escapeSingleApex");
								// System.out.println("MenuGestioneJstl.doStartTag() MAGGIORE DI ZEROOOOOOOOOO paramValue " + paramValue);
								int paramValueNum = paramValue.split(",").length;
								Object[] methodParam = null;
								Object methodValue = null;
								if (!paramValue.equals("") && paramValueNum > 0) {
									// System.out.println("MenuGestioneJstl.doStartTag() MAGGIORE DI ZEROOOOOOOOOO paramValueNum " + paramValueNum);

									methodParam = new Object[paramValueNum];
									for (int indexHref = 0; indexHref < paramValueNum; indexHref++) {
										// System.out.println("MenuGestioneJstl.doStartTag() paramValue.split(\",\")[indexHref] " + paramValue.split(",")[indexHref]);
										methodParam[indexHref] = paramValue.split(",")[indexHref];// getObjectFromString("java.lang.String", paramValue.split(",")[indexHref]);
										// System.out.println("MenuGestioneJstl.doStartTag() methodParam " + methodParam);
									}
								}
								Class c = null;

								if (!javaBean.equals("")) {
									if (javaBean.toUpperCase().equals("VIEWBEAN")) {
										methodValue = reflectionUtil.invokeMethod(viewBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("CONFBEAN")) {
										methodValue = reflectionUtil.invokeMethod(confBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("USERBEAN")) {
										methodValue = reflectionUtil.invokeMethod(userBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("EDITINGBEAN")) {
										methodValue = reflectionUtil.invokeMethod(editingBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("WORKFLOWBEAN")) {
										methodValue = reflectionUtil.invokeMethod(workFlowBean, methodBean, methodParam);
									} else if (javaBean.toUpperCase().equals("REQUEST")) {
										methodValue = request.getParameter(methodBean);
									}
									if (escapeSingleApex.equals("true")) {
										methodValue = "" + methodValue + "";
									} else {
										methodValue = "'" + methodValue + "'";
									}

								} else {
									methodValue = methodBean;
									methodValue = (String) ExpressionEvaluationUtils.evaluate((String)methodValue, String.class, pageContext);
								}
								String virgola = ",";
								if (index == contaParamFunction - 1) {
									virgola = "";
								}
								eventJavaScript += "" + methodValue + "" + virgola;
							}

							String puntoVirgola = ";";
							if (k == contaFunctionName - 1) {
								puntoVirgola = "";
							}
							eventJavaScript += ")" + puntoVirgola;
						}
						eventJavaScript += "\"";
						functionTxt += eventJavaScript + " ";
						// out.println(eventJavaScript);
					}
					String tagA = "<li><a " + anchorTxt + " " + targetTxt + " " + functionTxt + ">" + txtAhref + "</a></li>";
					out.println(tagA);
				} else {
					String tagA = "<li><a class=\"hiddenButton\"><del>" + txtAhref + "</del></a></li>";
					out.println(tagA);
				}
			}
			// out.println(" </ul>");
			// out.println("</div>");
		} catch (Exception ex) {
			throw new Error("Qualcosa e andato storto. start" + ex.getMessage());
		}
		// Dovrò ritornare SKIP_BODY perché comunque il body e vuoto
		// come e stato specificato nel file TLD.
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		try {
			JspWriter out = pageContext.getOut();
			// out.println("</div>");
		} catch (Exception ex) {
			throw new Error("Qualcosa e andato storto. end" + ex.getMessage());
		}
		return EVAL_PAGE;
	}

	private Object getObjectFromString(String className, String value) throws Exception {
		Class c = Class.forName(className);

		Constructor theConstructor = c.getConstructor(new Class[] { java.lang.String.class });

		Object obj = theConstructor.newInstance(new Object[] { value });

		return obj;
	}

	public String getConfFile() {
		return confFile;
	}

	public void setConfFile(String confFile) {
		this.confFile = confFile;
	}

	public String getTheMultiEditingPath() {
		return theMultiEditingPath;
	}

	public void setTheMultiEditingPath(String theMultiEditingPath) {
		this.theMultiEditingPath = theMultiEditingPath;
	}

}