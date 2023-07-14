<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ page import="com.rssl.phizic.test.csa.generated.AuthServiceLocator" %>
<%@ page import="com.rssl.phizic.test.csa.generated.AuthServicePortType" %>
<%@ page import="javax.xml.rpc.Stub" %>


<%
    String url = request.getParameter("url") == null ? "http://localhost:8888/CSABackApp/ws/AuthServicePort" : request.getParameter("url");
    String responseStr = null;
    String requestStr = request.getParameter("requestText");

    if (requestStr != null)
    {

        try
        {
            AuthServiceLocator service = new AuthServiceLocator();
            service.setAuthServicePortEndpointAddress(url);
            AuthServicePortType stub = service.getAuthServicePort();
            responseStr = stub.process(requestStr);
        }
        catch (Exception e)
        {
            responseStr = e.getClass().getName() + ":" + e.getMessage();
            e.printStackTrace();
        }
    }
    else
    {
        requestStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<message>\n" +
                "  <UID>234234</UID>\n" +
                "  <date>2012-08-27T11:43:37</date>\n" +
                "  <source>2134124</source>\n" +
                "  <version>2006-08-09+04:00</version>\n" +
                "  <IP>127.0.0.1</IP>\n" +
                "  <test>\n" +
                "  </test>\n" +
                "</message>";
    }
%>
<html>
<head><title>Тест взаимодействия с ЦСА Back</title></head>
<body>
<form action="" method="POST">
    <input type="text" name="url" size="100" value="<%=url%>"/>
    <input type="submit"/><br/>
    <textarea name="requestText" type="text" style="width:100%;height:400px"><%=requestStr%></textarea>
    ******* Ответ ЦСА Back: *********
    <textarea
            style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><%=responseStr%>
    </textarea>
</form>
</body>
</html>
