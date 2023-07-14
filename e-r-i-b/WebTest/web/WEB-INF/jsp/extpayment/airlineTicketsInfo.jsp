<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.StatusType" %>

<html>
  <head><title>Отправка информации о выпущенных авиабилетах</title></head>
  <body>
    <h2>Результат</h2>
    <%
        final String webServiceURL = request.getParameter("webServiceUrl");

        ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper(webServiceURL);

        String eribUUID = request.getParameter("eribUUID");
        String ticketsStatus = request.getParameter("ticketsStatusCode");
        String itineraryUrl = request.getParameter("itineraryUrl");
        String systemId = request.getParameter("systemId");

        StatusType responseStatus = shopRegServiceWrapper.sendAirlineTicketsInfo(eribUUID, ticketsStatus, itineraryUrl, systemId);
        if (responseStatus.getStatusCode() == 0)
            out.println("Информация об авиабилетах успешно отправлена в ЕРИБ");
        else
            out.println("Не удалось отправить в ЕРИБ информацию об авиабилетах. " +
                    "Код возврата: " + responseStatus.getStatusCode() + ". " +
                    "Описание ошибки: " + responseStatus.getStatusDesc());
    %>
  </body>
</html>