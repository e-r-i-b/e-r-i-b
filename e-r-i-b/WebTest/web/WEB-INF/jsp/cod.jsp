<%@ page import="com.rssl.phizic.gate.GateSingleton"%>
<%@ page import="com.rssl.phizic.gate.messaging.WebBankServiceFacade"%>
<%@ page import="org.w3c.dom.Document"%>
<%@ page import="java.io.StringWriter"%>
<%@ page import="com.sun.org.apache.xml.internal.serialize.XMLSerializer"%>
<%@ page import="com.sun.org.apache.xml.internal.serialize.Method"%>
<%@ page import="com.sun.org.apache.xml.internal.serialize.OutputFormat"%>
<%@ page import="com.rssl.phizic.utils.xml.XmlHelper"%>
<%@ page import="com.rssl.phizic.gate.exceptions.GateException"%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String responseStr = "";
	WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
	String requestStr = request.getParameter("requestText");
	if (requestStr != null)
	{

		//noinspection UnhandledExceptionInJSP
		Document requestDoc = XmlHelper.parse(requestStr);

		try
		{
			if ("online".equals(request.getParameter("requestType")))
			{
				//noinspection UnhandledExceptionInJSP
				Document responseDoc = serviceFacade.sendOnlineMessage(requestDoc,null);
				OutputFormat format = new OutputFormat(Method.XML, "windows-1251", true);
				StringWriter writer = new StringWriter();
				XMLSerializer serializer = new XMLSerializer(writer, format);
				serializer.serialize(responseDoc);
				responseStr = writer.toString();
			}
			else if ("offline".equals(request.getParameter("requestType")))
			{
				//noinspection UnhandledExceptionInJSP
				serviceFacade.sendOfflineMessage(requestDoc, "777L");
				responseStr = "OK";
			}
		}
		catch (Exception e)
		{
			responseStr = e.getClass().getName() + ":" + e.getMessage();
			e.printStackTrace();
		}
	}
%>

<html>
  <head><title>Test WebService</title></head>
  <body>
    <form action="" method="POST">
	    <select name="requestType">
			<option ${param.requestType == 'online' ? 'selected' : ''}>online</option>
		    <option ${param.requestType == 'offline' ? 'selected' : ''}>offline</option>
	    </select>
	    <input type="submit"/><br/>
	    <textarea name="requestText" type="text" style="width:100%;height:400px">${param.requestText}</textarea>
		<hr/>
	    <textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><c:out value="${responseStr}"/></textarea>
    </form>
  </body>
</html>