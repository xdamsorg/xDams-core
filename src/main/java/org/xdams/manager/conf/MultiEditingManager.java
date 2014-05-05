package org.xdams.manager.conf;

import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xdams.conf.master.ConfBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.request.MyRequest;
import org.xdams.utility.resource.ConfManager;
import org.xdams.workflow.bean.WorkFlowBean;
import org.xdams.xml.builder.XMLBuilder;

public class MultiEditingManager {

	private static final Logger logger = LoggerFactory.getLogger(MultiEditingManager.class);

	private XMLBuilder theXMLConf = null;

	private XMLBuilder theXML = null;

	private String elementToFind = null;

	private String confName = null;

	private String methodValue = "";

	private Map<String, String[]> parameterMap = null;

	private ConfBean confBean = null;

	private UserBean userBean = null;

	private WorkFlowBean workFlowBean = null;

	private VelocityContext velocityContext = new VelocityContext();

	public MultiEditingManager(Map<String, String[]> parameterMap, ConfBean confBean, UserBean userBean, WorkFlowBean workFlowBean) throws Exception {
		this.parameterMap = parameterMap;
		this.confBean = confBean;
		this.userBean = userBean;
		this.workFlowBean = workFlowBean;

		velocityContext.put("parameterMap", this.parameterMap);
		velocityContext.put("confBean", this.confBean);
		velocityContext.put("workFlowBean", this.workFlowBean);
		velocityContext.put("userBean", this.userBean);
		
	}

	public ConfBean execute() throws Exception {
	 
		String ilPath = "/" + getElementToFind();
		// MyRequest myRequest = new MyRequest(aReq);
		// System.out.println("MultiEditingManager.execute() conta xpath " + "/root/multiEditing[child::" + getIlPath() + "]");
		// System.out.println("MultiEditingManager.execute() conta " + getTheXMLConf().contaNodi("/root/multiEditing[child::" + getIlPath() + "]"));
		if (getTheXMLConf().contaNodi("/root/multiEditing[child::" + getElementToFind() + "]") > 0) {
			String ilMethod = getTheXMLConf().valoreNodo("/root/multiEditing[child::" + getElementToFind() + "]/@method");
			// System.out.println("MultiEditingManager.execute() ilMethod " + ilMethod);
			// System.out.println("---- INFO ---- MultiEditingManager method: " + ilMethod);
			String ilMethodValue = ilMethod.substring(ilMethod.indexOf(":") + 1);
			String pathTest = "";
			ilMethod = ilMethod.substring(0, ilMethod.indexOf(":"));
			if (ilMethod.equals("parameter")) {
				pathTest = MyRequest.getParameter(ilMethodValue, parameterMap);
			} else if (ilMethod.equals("xPath")) {
				if (getTheXML() == null) {
					throw new Exception("MultiEditingManager : xPath method");
				}
				pathTest = getTheXML().valoreNodo(ilMethodValue);
			} else if (ilMethod.equals("userBean")) {
				Class c = userBean.getClass();
				Method m = c.getMethod(ilMethodValue, null);
				Object methodValue = m.invoke(userBean, null);
				// System.out.println("methodValuemethodValuemethodValuemethodValuemethodValue " + methodValue);
				pathTest = (String) methodValue;
			} else if (ilMethod.equals("archive")) {
				Class c = workFlowBean.getArchive().getClass();
				// System.out.println("ilMethodilMethodilMethodilMethod " + ilMethod);
				// System.out.println("ilMethodValueilMethodValueilMethodValue " + ilMethodValue);
				Method m = c.getMethod(ilMethodValue, null);
				Object methodValue = m.invoke(workFlowBean.getArchive(), null);
				// System.out.println("methodValuemethodValuemethodValuemethodValuemethodValue " + methodValue);
				pathTest = (String) methodValue;
			}

			ilPath += "[@value='" + pathTest + "']";
		}
		// System.out.println("---- INFO ---- MultiEditingManager loading " + ilPath);
		// System.out.println("MultiEditingManager.execute() ilPath " + ilPath);
		/* GESTIONE MULTIFILE */
		String fullPath = "";
		XMLBuilder theXMLConf = null;
		String nomeFile = null;
		String xslFile = null;
		System.out.println("MultiEditingManager.execute() aaaaa " + getTheXMLConf().valoreNodo("/root/multiEditing" + ilPath + "/file/@name"));
		if (!(getTheXMLConf().valoreNodo("/root/multiEditing" + ilPath + "/file/@name")).equals("")) {
			try {
				nomeFile = getTheXMLConf().valoreNodo("/root/multiEditing" + ilPath + "/file/@name");
				System.out.println("MultiEditingManager.execute() nomeFile " + nomeFile);
				fullPath = "/" + nomeFile;
				System.out.println("MultiEditingManager.execute() fullPath " + fullPath);
				try {
					if (nomeFile.toLowerCase().endsWith("xsl") || nomeFile.toLowerCase().endsWith("xslt")) {
						xslFile = ConfManager.getConfString(fullPath);
					} else {
						//theXMLConf = evaluateBuilder(ConfManager.getConfXML(fullPath));
						theXMLConf = evaluateBuilder(ConfManager.getConfString(fullPath));
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
					theXMLConf = new XMLBuilder("root");
					// theXMLConf = new XMLBuilder(domManager.getDocument(fullPath));
				}
				// System.out.println("MultiEditingManager.execute() ilPath " + ilPath);
				// System.out.println("MultiEditingManager.execute() getIlPath " + getIlPath());
				// System.out.println("MultiEditingManager.execute() theXMLConf " + theXMLConf.getXML("ISO-8859-1"));
				if (xslFile == null && theXMLConf.contaNodi("/root/multiEditing[child::" + getElementToFind() + "]") > 0) {
					// System.out.println("MultiEditingManager.execute() theXMLConf1111111111111 " + theXMLConf.getXML("ISO-8859-1"));
					setTheXMLConf(theXMLConf);
					// ilPath = "/" + getIlPath();
					return execute();
				}
				System.out.println("MultiEditingManager.execute() getElementToFind " + getElementToFind());
				// System.out.println("MultiEditingManager.execute() theXMLConf2222222222222222 " + theXMLConf.getXML("ISO-8859-1"));
				if (getConfName().equals("query")) {
					confBean.setTheXMLConfQuery(theXMLConf);
				} else if (getConfName().equals("query-multiarchive")) {
					confBean.setTheXMLConfQueryMultiArchive(theXMLConf);
				} else if (getConfName().equals("docEdit")) {
					confBean.setTheXMLConfEditing(theXMLConf);
				} else if (getConfName().equals("valoriControllati")) {
					confBean.setTheXMLValControllati(theXMLConf);
				} else if (getConfName().equals("titleManager")) {
					confBean.setTheXMLConfTitle(theXMLConf);
				} else if (getConfName().equals("presentation")) {
					if (xslFile != null) {
						confBean.setPresentationXsl(xslFile);
					} else {
						confBean.setTheXMLConfPresentation(theXMLConf);
					}
				} else if (getConfName().equals("upload")) {
					confBean.setTheXMLConfUpload(theXMLConf);
				} else if (getConfName().equals("media")) {
					confBean.setMediaPath(fullPath);
					confBean.setTheXMLConfMedia(theXMLConf);
				} else if (getConfName().equals("managing")) {
					confBean.setTheXMLConfManaging(theXMLConf);
				} else if (getConfName().equals("bar-vis")) {
					confBean.setTheXMLConfBarVis(theXMLConf);
				} else if (getConfName().equals("bar-query")) {
					confBean.setTheXMLConfBarQuery(theXMLConf);
				} else if (getConfName().equals("bar-preinsert")) {
					confBean.setTheXMLConfBarPreInsert(theXMLConf);
				} else if (getConfName().equals("bar-nav")) {
					confBean.setTheXMLConfBarNav(theXMLConf);
				} else if (getConfName().equals("bar-managing")) {
					confBean.setTheXMLConfBarManaging(theXMLConf);
				} else if (getConfName().equals("bar-edt")) {
					confBean.setTheXMLConfBarEdt(theXMLConf);
				} else if (getConfName().equals("bar-docedit")) {
					confBean.setTheXMLConfBarDocEdit(theXMLConf);
				}
				ilPath = "/" + getElementToFind();
			} catch (Exception ex) {
				throw new Exception("configuration error in can't load (01): " + nomeFile);
			}
		}

		return confBean;
	}

	public ConfBean rewriteMultipleConf(List<String> arrayList) throws Exception {
		// System.out.println("MultiEditingManager.rewriteMultipleConf()");
		// System.out.println("MultiEditingManager.rewriteMultipleConf() " + arrayList);
		// System.out.println("MultiEditingManager.rewriteMultipleConf() " + confBean.getTheXMLValControllati());
		// System.out.println("MultiEditingManager.rewriteMultipleConf()");
		for (int i = 0; i < arrayList.size(); i++) {
			String confName = (String) arrayList.get(i);
			String elementFind = confName;
			if (confName.equals("query")) {
				setTheXMLConf(confBean.getTheXMLConfQuery());
			} else if (confName.equals("query-multiarchive")) {
				setTheXMLConf(confBean.getTheXMLConfQueryMultiArchive());
				elementFind = "query";
			} else if (confName.equals("docEdit")) {
				setTheXMLConf(confBean.getTheXMLConfEditing());
			} else if (confName.equals("valoriControllati")) {
				setTheXMLConf(confBean.getTheXMLValControllati());
			} else if (confName.equals("titleManager")) {
				setTheXMLConf(confBean.getTheXMLConfTitle());
			} else if (confName.equals("presentation")) {
				setTheXMLConf(confBean.getTheXMLConfPresentation());
			} else if (confName.equals("upload")) {
				setTheXMLConf(confBean.getTheXMLConfUpload());
			} else if (confName.equals("media")) {
				setTheXMLConf(confBean.getTheXMLConfMedia());
			} else if (confName.equals("managing")) {
				setTheXMLConf(confBean.getTheXMLConfManaging());
			} else if (confName.equals("bar-vis")) {
				setTheXMLConf(confBean.getTheXMLConfBarVis());
				elementFind = "managing";
			} else if (confName.equals("bar-query")) {
				setTheXMLConf(confBean.getTheXMLConfBarQuery());
				elementFind = "managing";
			} else if (confName.equals("bar-preinsert")) {
				setTheXMLConf(confBean.getTheXMLConfBarPreInsert());
				elementFind = "managing";
			} else if (confName.equals("bar-nav")) {
				setTheXMLConf(confBean.getTheXMLConfBarNav());
				elementFind = "managing";
			} else if (confName.equals("bar-managing")) {
				setTheXMLConf(confBean.getTheXMLConfBarManaging());
				elementFind = "managing";
			} else if (confName.equals("bar-edt")) {
				setTheXMLConf(confBean.getTheXMLConfBarEdt());
				elementFind = "managing";
			} else if (confName.equals("bar-docedit")) {
				setTheXMLConf(confBean.getTheXMLConfBarDocEdit());
				elementFind = "managing";
			}
			try {
				// System.out.println("---- INFO ---- rewriteMultipleConf");
				// System.out.println("confBean.getTheXMLConfEditing() " + confBean.getTheXMLConfEditing().getXML("ISO-8859-1"));
				setConfName(confName);
				setElementToFind(elementFind);
				execute();
			} catch (Exception e) {
				throw new Exception(" configuration error in  MultipleConf (" + e.getMessage() + ") for " + elementFind);
			}
		}
		return confBean;
	}

	public XMLBuilder evaluateBuilder(XMLBuilder myXMLConf) throws Exception {
		try {
			//DEAD CODE
			 StringWriter w = new StringWriter();
//			 boolean isEvaluate = new VelocityEngine().evaluate(velocityContext, w, "mystring", myXMLConf.getXML("ISO-8859-1", false, true));
//			 return new XMLBuilder(w.toString(), "ISO-8859-1");
			 return myXMLConf;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" configuration error in  MultipleConf evaluateBuilder(" + e.getMessage() + ")");
		}
	}
	
	public XMLBuilder evaluateBuilder(String xmlDoc) throws Exception {
		try {
			 StringWriter w = new StringWriter();
	 		 boolean isEvaluate = new VelocityEngine().evaluate(velocityContext, w, "mystring", xmlDoc);
			 return new XMLBuilder(w.toString(), false);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" configuration error in  MultipleConf evaluateBuilder(" + e.getMessage() + ")");
		}
	}

	public XMLBuilder getTheXMLConf() {
		return theXMLConf;
	}

	public void setTheXMLConf(XMLBuilder theXMLConf) throws Exception {
		this.theXMLConf = evaluateBuilder(theXMLConf);
	}

	public String getElementToFind() {
		return elementToFind;
	}

	public void setElementToFind(String elementToFind) {
		this.elementToFind = elementToFind;
	}

	// public String getIlPath() {
	// return ilPath;
	// }
	//
	// public void setIlPath(String ilPath) {
	// this.ilPath = ilPath;
	// }

	public String getMethodValue() {
		return methodValue;
	}

	public void setMethodValue(String methodValue) {
		this.methodValue = methodValue;
	}

	public XMLBuilder getTheXML() {
		return theXML;
	}

	public void setTheXML(XMLBuilder theXML) {
		velocityContext.put("theXML",theXML);
		this.theXML = theXML;
	}

	public String getConfName() {
		return confName;
	}

	public void setConfName(String confName) {
		this.confName = confName;
	}

	// @Deprecated
	// public XMLBuilder genericMultipleConf(String confName, String pathName) throws Exception {
	// XMLBuilder builder = null;
	// XMLBuilder builderInitial = null;
	// String newConfName = "";
	// // MyRequest myRequest = new MyRequest(aReq);
	// try {
	// // System.out.println("confName " + confName);
	//
	// if (!confName.startsWith("/")) {
	// newConfName = "/" + confName;
	// } else {
	// newConfName = confName;
	// }
	//
	// System.out.println("newConfName newConfName " + newConfName);
	// // carico lo XMLBuilder generico
	// builderInitial = ConfManager.getConfXML(newConfName);
	// // setto il setIlPath e il setTheXMLConf
	// setIlPath(pathName);
	// // System.out.println("pathName pathName " + pathName);
	// setTheXMLConf(builderInitial);
	// // controllo se per quel nome ho il multiEditing
	// String ilPath = "/" + getIlPath();
	// // se ho il multiEditing carico di nuovo il documento .
	// if (getTheXMLConf().contaNodi("/root/multiEditing[child::" + getIlPath() + "]") > 0) {
	// String ilMethod = getTheXMLConf().valoreNodo("/root/multiEditing[child::" + getIlPath() + "]/@method");
	// String ilMethodValue = ilMethod.substring(ilMethod.indexOf(":") + 1);
	// String pathTest = "";
	// ilMethod = ilMethod.substring(0, ilMethod.indexOf(":"));
	// if (ilMethod.equals("parameter")) {
	// // pathTest = myRequest.getParameter(ilMethodValue);
	// } else if (ilMethod.equals("xPath")) {
	// if (getTheXML() == null) {
	// throw new Exception("configuration error in  MultiEditingManager (02): xPath method");
	// }
	// pathTest = getTheXML().valoreNodo(ilMethodValue);
	// } else if (ilMethod.equals("userBean")) {
	// Class c = userBean.getClass();
	// // System.out.println("ilMethodilMethodilMethodilMethod " + ilMethod);
	// // System.out.println("ilMethodValueilMethodValueilMethodValue " + ilMethodValue);
	// Method m = c.getMethod(ilMethodValue, null);
	// Object methodValue = m.invoke(userBean, null);
	// // System.out.println("methodValuemethodValuemethodValuemethodValuemethodValue " + methodValue);
	// pathTest = (String) methodValue;
	// }
	// ilPath += "[@value='" + pathTest + "']";
	// }
	// // System.out.println("MultiEditingManager.execute() ilPath " + ilPath);
	// /* GESTIONE MULTIFILE */
	// String fullPath = "";
	// String nomeFile = null;
	// // System.out.println("MultiEditingManager.execute() aaaaa " + "/root/multiEditing" + ilPath + "/file/@name");
	// if (!(getTheXMLConf().valoreNodo("/root/multiEditing" + ilPath + "/file/@name")).equals("")) {
	// try {
	// nomeFile = getTheXMLConf().valoreNodo("/root/multiEditing" + ilPath + "/file/@name");
	// // System.out.println("MultiEditingManager.execute() nomeFile " + nomeFile);
	// fullPath = "/" + nomeFile;
	// // System.out.println("MultiEditingManager.execute() fullPath " + fullPath);
	// builder = ConfManager.getConfXML(fullPath);
	// // System.out.println("MultiEditingManager.execute() ilPath " + ilPath);
	// // System.out.println("MultiEditingManager.execute() getIlPath " + getIlPath());
	// // System.out.println("MultiEditingManager.execute() theXMLConf " + theXMLConf.getXML("ISO-8859-1"));
	// if (builder.contaNodi("/root/multiEditing[child::" + getIlPath() + "]") > 0) {
	// // System.out.println("MultiEditingManager.execute() theXMLConf1111111111111 " + builder.getXML("ISO-8859-1"));
	// setTheXMLConf(builder);
	// // ilPath = "/" + getIlPath();
	// return genericMultipleConf(fullPath, pathName);
	//
	// }
	// ilPath = "/" + getIlPath();
	// // ilPath = "/docEdit";
	// } catch (Exception ex) {
	// throw new Exception("configuration error in  MultipleConf can't load (02): " + nomeFile);
	// }
	// } else {
	// // se NON HO il multiEditing lasci quello che ho caricato per primo.
	// builder = builderInitial;
	// }
	//
	// } catch (Exception e) {
	// if (newConfName.indexOf("query") == -1) {
	// throw new Exception("configuration error in  MultipleConf can't load (03): " + confName + " " + e.getMessage());
	// } else {
	// builder = new XMLBuilder("root");
	// }
	// }
	// return builder;
	// }

}
