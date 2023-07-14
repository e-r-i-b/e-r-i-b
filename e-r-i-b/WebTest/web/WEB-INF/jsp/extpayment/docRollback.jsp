<%@ page contentType="text/html;charset=Windows-1251" language="java" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.DocRollbackRsResultType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper(request.getParameter("webServiceUrl"));

    DocRollbackRsResultType result = shopRegServiceWrapper.sendDocRollbackRq(request.getParameter("eribUUID"), request.getParameter("provider"), request.getParameter("eShopIdBySP"));

    if (result.getStatus().getStatusCode() == 0)
    {
        out.println("Отмена оплаты выполнена успешно.");
    }
    else
    {
        out.println("Возврат завершилась ошибкой. Код ошибки:" + result.getStatus().getStatusCode());
    }
%>
  <head><title>Тестирование отмены оплаты товара</title></head>
  <body>
  </body>
</html>