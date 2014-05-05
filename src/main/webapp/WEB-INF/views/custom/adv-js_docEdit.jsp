ciao
<%@page import="org.xdams.xw.paging.QRParser"%>
<%@page import="org.xdams.page.view.bean.TitleBean"%>
<%@page import="org.xdams.utility.request.MyRequest"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.xdams.page.query.bean.QueryBean"%>
<%@page import="java.util.List"%>
<%@page import="org.xdams.utility.TitleManager"%>
<%@page import="org.xdams.xml.builder.XMLBuilder"%>
<%@page import="org.xdams.page.view.bean.ManagingBean"%>
<%@page import="org.xdams.workflow.bean.WorkFlowBean"%>
<%@page import="org.xdams.user.bean.UserBean"%>
<%@page import="org.xdams.conf.master.ConfBean"%>
<%
ConfBean confBean = (ConfBean)request.getAttribute("confBean");
UserBean userBean = (UserBean)request.getAttribute("userBean");
WorkFlowBean workFlowBean = (WorkFlowBean)request.getAttribute("workFlowBean");
ManagingBean managingBean = (ManagingBean)session.getAttribute(workFlowBean.getManagingBeanName());
XMLBuilder theXMLconfTitle = confBean.getTheXMLConfTitle();
//out.println("ciaooo"+theXMLconfTitle.getXML("ISO-8859-1"));
%>