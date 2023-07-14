<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.xml.rpc.Stub" %>
<%@ page import="com.rssl.phizicgate.iqwave.messaging.jaxrpc.generated.IQWave4EskProtType" %>
<%@ page import="com.rssl.phizicgate.iqwave.messaging.jaxrpc.generated.IQWave4EskService" %>
<%@ page import="com.rssl.phizicgate.iqwave.messaging.jaxrpc.generated.IQWave4EskService_Impl" %>


<html>
<%
    IQWave4EskService service = new IQWave4EskService_Impl();
    IQWave4EskProtType stub = service.getIQWave4Esk();
    ((com.sun.xml.rpc.client.StubBase) stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY,
            "http://localhost:8888/PhizIC-test/services/IQWave4EskService");

%>
  <head><title>SBOL to IQWAVE Web-Services TEST</title></head>
  <body>
      ******* Сообщение из IQWave: *********
      <br>
      <%=stub.getXMLmessage("The message from ESK for IQWave!!!")%>
  </body>
</html>