<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopSystemName" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.DocRegRsType" %>
<%@ page import="com.rssl.phizic.gate.einvoicing.TypeOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper(request.getParameter("url"));

    boolean isSuccess = false;

    DocRegRsType result = request.getParameter("partial").equals("1") ?
            shopRegServiceWrapper.ordersRegistration(TypeOrder.P, ShopSystemName.OZON, null, null, false, false, null, null, null, null) :
            shopRegServiceWrapper.ordersRegistration(TypeOrder.F, ShopSystemName.OZON, null, null, false, false, null, null, null, null);

    if (result.getStatus().getStatusCode() == 0)
    {
        isSuccess = true;
        out.println("Регистрация прошла успешно.");
        out.println("Идентификатор документа в системе ЕРИБ: " + result.getERIBUID());
    }
    else
    {
        out.println("Регистрация завершилась с ошибкой");
    }
%>
  <head><title>Test OZON</title></head>
  <body>
      <c:set var="eribUID" value="<%=result.getERIBUID()%>"/>
      <c:set var="isSuccess" value="<%=isSuccess%>"/>
      <c:if test="${isSuccess}">
          <br/>
          <a href="http://localhost:8888/CSAFront/payOrderPaymentLogin.do?ReqId=${eribUID}" target="_blank">
              Войти
          </a>
      </c:if>
  </body>
</html>