<%@ page import="java.io.InputStream" %>
<%@ page import="java.net.URL" %>
<%@ page import="javax.net.ssl.*" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
  Created by IntelliJ IDEA.
  User: Krenev
  Date: 09.11.2007
  Time: 12:37:35
  To change this template use File | Settings | File Templates.
--%>
<%
	String responseStr = "";
	String requestStr = request.getParameter("requestText");
	if (requestStr != null)
	{
		try
		{
			responseStr = test(requestStr);
		}
		catch (Exception e)
		{
			responseStr = e.getClass().getName() + ":" + e.getMessage();
			e.printStackTrace();
		}
	}
%>

<html>
<head><title>Test Рапида</title></head>
<body>
<form action="" method="POST">
	<input type="submit"/><br/>
	<textarea name="requestText" type="text" style="width:100%;height:400px"><c:if test="${empty param.requestText}">/test?function=check&PaymExtId=123456x123a&PaymSubjTp=306&Amount=1234500&Params=11%201581315%3B53%20154333%3B16%20148%3B17%2077%3B&TermType=08&TermID=00001234&%20FeeSum=500</c:if><c:if test="${not empty param.requestText}">${param.requestText}</c:if></textarea>
	<br/>
	<textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%>
		<c:out value="${responseStr}"/>
	</textarea>
</form>
</body>
</html>
<%!
	private String test(String requestStr) throws Exception
	{
/*
		System.setProperty("javax.net.ssl.keyStore", "cert.p12");
		System.setProperty("javax.net.ssl.trustStore", "trust.keystore");
		System.setProperty("javax.net.ssl.trustStorePassword", "gthtdjl");
		System.setProperty("javax.net.ssl.keyStorePassword", "1");
		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
*/
		String host = "online.rapida.ru";
		URL url = new URL("https", host, requestStr);

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.connect();

		int responseCode = connection.getResponseCode();

		StringBuffer buffer = new StringBuffer();
		buffer.append("Response Code:").append(responseCode).append("\n");
		InputStream inputStream = null;
		try
		{
			inputStream = connection.getInputStream();
			int c = 0;
			while ((c = inputStream.read()) != -1)
			{
				buffer.append((char) c);
			}
			return buffer.toString();
		}
		finally
		{
			if (inputStream != null)
				inputStream.close();
		}
	}
%>