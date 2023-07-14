<%@ page import="com.rssl.phizic.gate.exceptions.GateException" %>
<%@ page import="com.rssl.phizic.test.wsgate.generated.gateinfoservice.GateInfoService_Impl" %>
<%@ page import="com.rssl.phizic.test.wsgate.generated.gateinfoservice.GateInfoService_PortType" %>
<%@ page import="com.rssl.phizicgate.wsgate.services.clients.ClientServiceHelper" %>
<%@ page import="com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_PortType_Stub" %>
<%@ page import="com.rssl.phizicgate.wsgate.services.clients.generated.ClientService_Service_Impl" %>
<%@ page import="java.rmi.RemoteException" %>
<%@ page import="javax.xml.rpc.Stub" %>
<%--
  User: Krenev
  Date: 27.04.2009
  Time: 16:14:07
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    ClientService_Service_Impl service = new ClientService_Service_Impl();
    ClientService_PortType_Stub stub = (ClientService_PortType_Stub) service.getClientServicePort();
    stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8888/PhizGate/services/ClientService_Server_Impl?WSDL");
    String str = "Ничего не получили +";
    try
    {
        ClientServiceHelper helper = new ClientServiceHelper();
        str = helper.getGateClient(stub.getClientById("7")).getFullName();
    }
    catch (RemoteException ex)
    {
        throw new GateException(ex);
    }

    GateInfoService_Impl locator = new GateInfoService_Impl();
    GateInfoService_PortType portType = locator.getGateInfoService_PortTypePort();
    ((com.sun.xml.rpc.client.StubBase) portType)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/PhizGate/services/GateInfoService");
%>
<head><title>Simple jsp page</title></head>
<body>
!!!!!!!!! <%=str%> !!!!!!!!!!!
<br>
<%=portType.getUID()%>
</body>
</html>