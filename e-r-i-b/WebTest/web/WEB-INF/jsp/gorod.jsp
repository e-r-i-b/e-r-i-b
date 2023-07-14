<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.net.InetSocketAddress"%>
<%@ page import="java.net.Socket"%>
<%@ page import="java.nio.CharBuffer" %>
<%@ page import="java.net.SocketTimeoutException" %>
<%@ page import="com.rssl.phizic.utils.xml.XmlHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String responseStr = "";

	String requestStr = request.getParameter("requestText");
	if (requestStr != null)
	{
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("10.170.225.42", 8586));
//		socket.setSendBufferSize(4096);
		PrintWriter output = new PrintWriter(socket.getOutputStream());

		output.write(requestStr);
		output.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] c = new char[10000];

		int i = 0;
		socket.setSoTimeout(50000);
		StringBuilder sb = new StringBuilder();
		try
		{
			while ((i = reader.read(c)) != -1)
				sb.append(c, 0, i);
			responseStr = sb.toString();
			responseStr = XmlHelper.convertDomToText(XmlHelper.parse(responseStr));
		}
		catch (SocketTimeoutException e)
		{
			System.out.println("timeout");
			responseStr = "timeout";
		}
		socket.close();
	}
%>

<html>
  <head><title>Test WebService</title></head>
  <body>
    <form action="" method="POST">
	    <input type="submit"/><br/>
	    <textarea name="requestText" type="text" style="width:100%;height:400px">${param.requestText}</textarea>
		<hr/>
	    <textarea style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><c:out value="${responseStr}"/></textarea>
    </form>
  </body>
</html>