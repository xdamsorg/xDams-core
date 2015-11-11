package org.xdams.page.factory;

import java.util.Map;

import org.springframework.ui.ModelMap;
import org.xdams.managing.command.AdvFindReplace;
import org.xdams.managing.command.EraseCommand;
import org.xdams.managing.command.ManageXML;
import org.xdams.managing.command.ModifyAuther;
import org.xdams.managing.command.MultiCopy;
import org.xdams.managing.command.MultiModCommand;
import org.xdams.managing.command.MultiModRenumberCommand;
import org.xdams.managing.command.MultiMove;
import org.xdams.managing.command.SortingCommand;
import org.xdams.managing.command.XML2PDF;
import org.xdams.page.view.bean.ManagingBean;
import org.xdams.utility.request.MyRequest;

public class ManagingFactory {

	// private HttpServletRequest aReq = null;
	//
	public ManagingBean managingBean = null;

	//
	// private ServletContext servletContext = null;
	//
	// private HttpServletResponse aRes = null;

	private Map<String, String[]> parameterMap = null;

	private ModelMap modelMap = null;

	public ManagingFactory(Map<String, String[]> parameterMap, ModelMap modelMap) throws Exception {
		this.parameterMap = parameterMap;
		this.modelMap = modelMap;
	}

	public ManagingBean execute() throws Exception {
		// String actionFlag = "";
		/*
		 * Enumeration enumeration = aReq.getParameterNames(); while (enumeration.hasMoreElements()) { String element = (String) enumeration.nextElement(); System.out.println("element "+aReq.getParameter(element)); }
		 */
		// MultiPartCommand multiPartCommand = new MultiPartCommand(aReq);
		// multiPartCommand.multiPartDispatch();
//		System.out.println("--------------------------------------------------------------");
//		System.out.println("--------------------------------------------------------------");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()" + MyRequest.getParameter("actionFlag", parameterMap));
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("ManagingFactory.execute()");
//		System.out.println("##############################################################");
//		System.out.println("##############################################################");

		String actionFlag = MyRequest.getParameter("actionFlag", parameterMap);
		try {
			if (actionFlag.equals("sorting")) {
				SortingCommand sortingCommand = new SortingCommand(parameterMap, modelMap);
				managingBean = sortingCommand.execute();
			} else if (actionFlag.equals("multiMod")) {
				MultiModCommand multiModCommand = new MultiModCommand(parameterMap, modelMap);
				managingBean = multiModCommand.execute();
			} else if (actionFlag.equals("multiModRenumber")) {
				MultiModRenumberCommand multiModRenumberCommand = new MultiModRenumberCommand(parameterMap, modelMap);
				managingBean = multiModRenumberCommand.execute();
			} else if (actionFlag.equals("erase")) {
				EraseCommand eraseCommand = new EraseCommand(parameterMap, modelMap);
				managingBean = eraseCommand.execute();
			} else if (actionFlag.equals("editXml")) {
				ManageXML manageCommand = new ManageXML(parameterMap, modelMap);
				manageCommand.xmlInteraction = "edt";
				managingBean = manageCommand.execute();
			} else if (actionFlag.equals("viewXml")) {
				ManageXML manageCommand = new ManageXML(parameterMap, modelMap);
				manageCommand.xmlInteraction = "view";
				managingBean = manageCommand.execute();
			} else if (actionFlag.equals("xml2pdf")) {
				XML2PDF xml2pdf = new XML2PDF(parameterMap, modelMap);
				managingBean = xml2pdf.execute();
			} else if (actionFlag.equals("multiMove")) {
				MultiMove multiMove = new MultiMove(parameterMap, modelMap);
				managingBean = multiMove.execute();
			} else if (actionFlag.equals("multiCopy")) {
				 MultiCopy multiCopy = new MultiCopy(parameterMap, modelMap);
				managingBean = multiCopy.execute();
			} else if (actionFlag.equals("modifyAuther")) {
				ModifyAuther modifyAuther = new ModifyAuther(parameterMap, modelMap);
				managingBean = modifyAuther.execute();
			} else if (actionFlag.equals("uploadFile")) {
				// UploadManaging uploadManaging = new UploadManaging(aReq, servletContext);
				// managingBean = uploadManaging.execute();
			} else if (actionFlag.equals("arrangeXPath")) {
				// ArrangeXPathCommand arrangeXPathCommand = new ArrangeXPathCommand(aReq, servletContext);
				// managingBean = arrangeXPathCommand.execute();
			} else if (actionFlag.equals("adminXw")) {
				// AdminXwFactory adminXwFactory = new AdminXwFactory(aReq, aRes, servletContext, multiPartCommand);
				// managingBean = adminXwFactory.execute();
			} else if (actionFlag.equals("findReplace")) {
				 AdvFindReplace advFindReplace = new AdvFindReplace(parameterMap, modelMap);
				 managingBean = advFindReplace.execute();
			} else if (actionFlag.equals("advMultiMod")) {
				// AdvMultiModCommand advMultiModCommand = new AdvMultiModCommand(aReq, servletContext);
				// managingBean = advMultiModCommand.execute();
			} else if (actionFlag.equals("spreadMod")) {
				// SpreadModCommand spreadModCommand = new SpreadModCommand(aReq, servletContext);
				// managingBean = spreadModCommand.execute();
			}
		} catch (Exception e) {
			throw e;
		}

		return managingBean;
	}
}
