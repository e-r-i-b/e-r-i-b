<%@ page contentType="text/html;charset=Windows-1251" language="java" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.DocRollbackRsResultType" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.ClientCheckRsType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper(request.getParameter("webServiceUrl"));

    ClientCheckRsType result = shopRegServiceWrapper.sendClientCheckRq(request.getParameter("sPName"), request.getParameter("eShopIdBySP"), request.getParameter("recipientName"), request.getParameter("URL"), request.getParameter("INN"), request.getParameter("phone"));

    if (result.getStatus().getStatusCode() == 1)
    {
        out.println("������ ������.");
    }
    else if (result.getStatus().getStatusCode() == 0)
    {
        out.println("������ �� ������.");
    }
    else
    {
        out.println("�������� ����������� � �������.");
    }
%>
  <head><title>������������ �������� ������ ��������</title></head>
  <body>
  </body>
</html>