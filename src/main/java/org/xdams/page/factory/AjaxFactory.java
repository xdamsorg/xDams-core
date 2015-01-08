package org.xdams.page.factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.xdams.ajax.bean.AjaxBean;
import org.xdams.ajax.command.AjaxCommandNotInHier;
import org.xdams.ajax.command.AjaxCommandVerifyUserName;
import org.xdams.ajax.command.AjaxCommandVocabolarioJson;
import org.xdams.ajax.command.AjaxCutPasteCopyCommand;
import org.xdams.ajax.command.AjaxDocInfoCommand;
import org.xdams.ajax.command.AjaxSearchRelatedRecordsCommand;
import org.xdams.ajax.command.AjaxSessionCommand;

public class AjaxFactory {

	private HttpServletRequest aReq = null;

	public AjaxBean ajaxBean = null;

	private HttpServletResponse aRes = null;

	private ModelMap modelMap = null;

	public AjaxFactory(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
		this.aReq = req;
		this.aRes = res;
		this.modelMap = modelMap;
	}

	public AjaxBean execute() throws Exception {
		String actionFlag = aReq.getParameter("actionFlag");
		try {
			if (actionFlag.equals("verifyUser")) {
				AjaxCommandVerifyUserName ajaxCommandVerifyUserName = new AjaxCommandVerifyUserName(aReq, aRes, modelMap);
				ajaxBean = ajaxCommandVerifyUserName.execute();
				ajaxBean.setContentType("application/json; charset=iso-8859-1");
 			} else if (actionFlag.equals("valoriControllatiJson")) {
				// AjaxCommandValoriControllatiJson ajaxCommandValoriControllati = new AjaxCommandValoriControllatiJson(aReq, modelMap, aRes);
				// ajaxBean = ajaxCommandValoriControllati.execute();
			} else if (actionFlag.equals("valoriControllati")) {
				// AjaxCommandValoriControllati ajaxCommandValoriControllati = new AjaxCommandValoriControllati(aReq, modelMap, aRes);
				// ajaxBean = ajaxCommandValoriControllati.execute();
				// aRes.setCharacterEncoding("iso-8859-1");
				// aRes.setContentType("text/xml");
			} else if (actionFlag.equals("vocabolarioSelect")) {
				// AjaxCommandVocabolario ajaxCommandVocabolario = new AjaxCommandVocabolario(aReq, modelMap, aRes);
				// ajaxBean = ajaxCommandVocabolario.execute();
			} else if (actionFlag.equals("vocabolarioJson")) {
				AjaxCommandVocabolarioJson ajaxCommandVocabolarioJson = new AjaxCommandVocabolarioJson(aReq, aRes, modelMap);
				ajaxBean = ajaxCommandVocabolarioJson.execute();
				ajaxBean.setContentType("application/json; charset=iso-8859-1");
			} else if (actionFlag.equals("selectedDoc")) {
				AjaxSessionCommand ajaxSessionCommand = new AjaxSessionCommand(aReq, aRes, modelMap);
				ajaxBean = ajaxSessionCommand.execute();
			} else if (actionFlag.equals("cutPaste")) {
				AjaxCutPasteCopyCommand ajaxCutPasteCopyCommand = new AjaxCutPasteCopyCommand(aReq, aRes, modelMap);
				ajaxBean = ajaxCutPasteCopyCommand.execute();
				//ajaxBean.setContentType("text/html; charset=iso-8859-1");
			} else if (actionFlag.equals("cookieManager")) {
				// AjaxCookieCommand ajaxCookieCommand = new AjaxCookieCommand(aReq, modelMap, aRes);
				// ajaxBean = ajaxCookieCommand.execute();
			} else if (actionFlag.equals("eraseQueryBean")) {
				// AjaxEraseQueryBeanCommand ajaxEraseQueryBeanCommand = new AjaxEraseQueryBeanCommand(aReq, modelMap, aRes);
				// ajaxBean = ajaxEraseQueryBeanCommand.execute();
			} else if (actionFlag.equals("searchRelatedRecords")) {
				AjaxSearchRelatedRecordsCommand ajaxSearchRelatedRecordsCommand = new AjaxSearchRelatedRecordsCommand(aReq, aRes, modelMap);
				ajaxBean = ajaxSearchRelatedRecordsCommand.execute();
			} else if (actionFlag.equals("unlockRecord")) {
				// AjaxUnLockRecord ajaxUnLockRecord = new AjaxUnLockRecord(aReq, modelMap, aRes);
				// ajaxBean = ajaxUnLockRecord.execute();
			} else if (actionFlag.equals("infoDoc")) {
				AjaxDocInfoCommand ajaxInfo = new AjaxDocInfoCommand(aReq, aRes, modelMap);
				ajaxBean = ajaxInfo.execute();
				ajaxBean.setContentType("text/html; charset=iso-8859-1");
			} else if (actionFlag.equals("deleteAttachFile")) {
				// AjaxDeleteAttach ajaxDeleteAttach = new AjaxDeleteAttach(aReq, modelMap, aRes);
				// ajaxBean = ajaxDeleteAttach.execute();
			} else if (actionFlag.equals("notInHier")) {
				AjaxCommandNotInHier ajaxCommandNotInHier = new AjaxCommandNotInHier(aReq, aRes, modelMap);
				ajaxBean = ajaxCommandNotInHier.execute();
			}
		} catch (Exception e) {
			throw e;
		}
		return ajaxBean;
	}

}
