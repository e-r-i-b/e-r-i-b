<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ page import="com.rssl.phizicgate.cpfl.messaging.generated.jaxrpc.CommunalServicePT" %>
<%@ page import="com.rssl.phizicgate.cpfl.messaging.generated.jaxrpc.CommunalServiceService" %>
<%@ page import="com.rssl.phizicgate.cpfl.messaging.generated.jaxrpc.CommunalServiceService_Impl" %>
<%@ page import="javax.xml.rpc.Stub" %>


<%
    String url = request.getParameter("url") == null ? "http://localhost:8888/PhizIC-test/services/CommunalServicePort" : request.getParameter("url");
    String responseStr = null;
    String requestStr = request.getParameter("requestText");
    
    if (requestStr != null)
    {

        try
        {
                CommunalServiceService service = new CommunalServiceService_Impl();
                CommunalServicePT stub = service.getCommunalServicePort();
                ((com.sun.xml.rpc.client.StubBase) stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
                responseStr = stub.sendMessage(requestStr);
         }
        catch (Exception e)
        {
            responseStr = e.getClass().getName() + ":" + e.getMessage();
            e.printStackTrace();
        }
    }
    else
    {
        requestStr ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<message>\n" +
                        "</message>";
    }
%>
<html>
<head><title>ESK to CPFL Web-Services TEST</title></head>
<body>
<form action="" method="POST">
    <input type="text" name="url" size="100" value="<%=url%>"/>
    <input type="submit"/><br/>
    <textarea name="requestText" type="text" style="width:100%;height:400px"><%=requestStr%></textarea>
    ******* Ответ из CPFL: *********
    <textarea
            style="width:100%;height:400px"><%pageContext.setAttribute("responseStr", responseStr);%><%=responseStr%>
    </textarea>
</form>
</body>
</html>
