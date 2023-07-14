<%@ page import="com.rssl.phizicgate.sbcms.messaging.CMSMessagingService" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String responseStr = "";
	String requestStr = request.getParameter("requestText");
	if (requestStr != null)
	{
		CMSMessagingService service = CMSMessagingService.getInstance();
		responseStr = service.binding.pqQuery(requestStr);

		//записываем сообщения в файл
		String filePath="d:\\test";
		OutputStream os = new FileOutputStream(filePath + "\\cms.txt", true);


		os.write("request:\n".getBytes("Cp1251"));
		os.write( requestStr.getBytes("Cp1251"));

		os.write("\nresponse:\n".getBytes("Cp1251"));
		os.write( responseStr.getBytes("Cp1251"));
		os.write( "\n".getBytes("Cp1251"));
		os.close();
	}
%>

<html>
  <head><title>Test WebService CMS</title></head>
  <body>
    <form action="" method="POST">
	    <input type="submit"/><br/>
	    <textarea name="requestText" type="text" style="width:100%;height:400px">${param.requestText}</textarea>
		<hr/>
	    <textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><c:out value="${responseStr}"/></textarea>
    </form>
  </body>
</html>