package org.xdams.jstl;

import it.highwaytech.db.HierPath;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xdams.conf.master.ConfBean;
import org.xdams.page.view.bean.ViewBean;
import org.xdams.user.bean.UserBean;
import org.xdams.utility.TitleManager;
import org.xdams.workflow.bean.WorkFlowBean;

public class GerarchiaJstl extends TagSupport {

	private static final long serialVersionUID = 7739100395468852973L;

	private HierPath hierPath = null;

	private String hierMode = "";

	private String targetServlet = "";

	// private ConfBean confBean = null;

	// private UserBean userBean = null;

	// private ViewBean viewBean = null;

	public int doStartTag() {
		try {

			ConfBean confBean = (ConfBean) pageContext.findAttribute("confBean");

			UserBean userBean = (UserBean) pageContext.findAttribute("userBean");

			ViewBean viewBean = (ViewBean) pageContext.findAttribute("viewBean");

			WorkFlowBean workFlowBean = (WorkFlowBean) pageContext.findAttribute("workFlowBean");

			JspWriter out = pageContext.getOut();
			if (targetServlet.equals("")) {
				targetServlet = "_top";
			}
			TitleManager titleManager = new TitleManager(confBean.getTheXMLConfTitle());
			int numeroLivelli = hierPath.depth();
			String divHierPath = "";
			if (getHierMode().equals("")) {
				for (int i = 0; i < numeroLivelli - 1; i++) {
					int spaziatore = 18 * (i - 1);
					int theFatherTemp = hierPath.docNumber(i + 1);
					divHierPath += "<div class=\"hierPath\">";
					divHierPath += "<a href=\"" + pageContext.findAttribute("contextPath") + "/hier/" + workFlowBean.getAlias() + "/hierBrowser.html?docToggle=" + theFatherTemp + "&amp;docStart=" + theFatherTemp + "\" target=\"" + targetServlet + "\">";
					String strTitoloManager = titleManager.defaultParsedTitle(hierPath.getTitle(i + 1), "hierTitle");
					divHierPath += strTitoloManager;
					divHierPath += "</a>";
				}
				for (int i = 0; i < numeroLivelli - 1; i++) {
					divHierPath += "</div>";
				}
				out.println(divHierPath);
			} else if (getHierMode().equals("tooltip")) {
				for (int i = 0; i < numeroLivelli - 1; i++) {
					divHierPath += "<div class=\"hierPath\">";
					String strTitoloManager = titleManager.defaultParsedTitle(hierPath.getTitle(i + 1), "hierTitle");
					divHierPath += strTitoloManager;
				}
				for (int i = 0; i < numeroLivelli - 1; i++) {
					divHierPath += "</div>";
				}
				out.print(StringEscapeUtils.escapeHtml4(divHierPath));
			}
		} catch (Exception ex) {
			throw new Error("Errore nella GerarchiaJstl.1");
		}

		return SKIP_BODY;
	}

	public int doEndTag() {
		try {
			JspWriter out = pageContext.getOut();
		} catch (Exception ex) {
			throw new Error("Errore nella GerarchiaJstl.0");
		}
		return EVAL_PAGE;
	}

	public HierPath getHierPath() {
		return hierPath;
	}

	public void setHierPath(HierPath hierPath) {
		this.hierPath = hierPath;
	}

	/**
	 * @return Returns the hierMode.
	 */
	public String getHierMode() {
		return hierMode;
	}

	/**
	 * @param hierMode
	 *            The hierMode to set.
	 */
	public void setHierMode(String hierMode) {
		this.hierMode = hierMode;
	}

	public String getTargetServlet() {
		return targetServlet;
	}

	public void setTargetServlet(String targetServlet) {
		this.targetServlet = targetServlet;
	}

}
