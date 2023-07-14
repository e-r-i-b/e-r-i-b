<%@ page contentType="text/html;charset=Windows-1251" language="java" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.GoodsReturnRsResultType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper(request.getParameter("webServiceUrl"));

    GoodsReturnRsResultType result = shopRegServiceWrapper.sendGoodsReturnRq(request.getParameter("eribUUID"), request.getParameter("provider"), request.getParameter("eShopIdBySP"),
                                                                                request.getParameter("systemId"));

    if (result.getStatus().getStatusCode() == 0)
    {
        out.println("Возврат выполнен успешно.");
    }
    else
    {
        out.println("Возврат завершилась ошибкой. Код ошибки:" + result.getStatus().getStatusCode());
    }
%>
  <head><title>Тестирование возврата товара</title></head>
  <body>
  </body>
</html>