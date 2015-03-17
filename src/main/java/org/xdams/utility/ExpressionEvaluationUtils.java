package org.xdams.utility;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class ExpressionEvaluationUtils {
	
	public static final String EXPRESSION_PREFIX = "${";

	public static final String EXPRESSION_SUFFIX = "}";

	public static boolean isExpressionLanguage(String value) {
		return (value != null && value.contains(EXPRESSION_PREFIX));
	} 

	public static Object evaluate(String exp, Class<?> resultClass, PageContext pageContext) throws JspException {
		if (pageContext == null) {
			return exp;
		}
		ELContext elContext = pageContext.getELContext();
		JspFactory jf = JspFactory.getDefaultFactory();
		JspApplicationContext jac = jf.getJspApplicationContext(pageContext.getServletContext());
		ExpressionFactory ef = jac.getExpressionFactory();
		ValueExpression val = null;
		try {
			val = ef.createValueExpression(elContext, exp, resultClass);
		} catch (Exception e) {
			return exp;
		}
		return val.getValue(elContext);
	}
}
