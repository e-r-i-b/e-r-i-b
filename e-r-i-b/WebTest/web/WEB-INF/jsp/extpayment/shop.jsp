<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopSystemName" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.DocRegRsType" %>
<%@ page import="com.rssl.phizic.gate.einvoicing.TypeOrder" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.CurrencyType" %>
<%@ page import="com.rssl.phizic.utils.xml.XmlHelper" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<%
    final String webServiceURL = request.getParameter("webServiceUrl");

    ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper(webServiceURL);

    boolean isSuccess = false;
    ShopSystemName provider = ShopSystemName.valueOf(request.getParameter("provider"));
    TypeOrder mode = request.getParameter("partial").equals("1") ? TypeOrder.P :(request.getParameter("partial").equals("2") ? TypeOrder.O : TypeOrder.F);
    String modeStr = request.getParameter("partial");
    String backUrl = request.getParameter("backUrl");
    String mobilePhone = request.getParameter("mobilePhone");
    String eShopIdBySP = request.getParameter("eShopIdBySP");
    String eShopURL = request.getParameter("eShopURL");
    String systemId = request.getParameter("systemId");
    boolean mobileCheckout = "true".equals(request.getParameter("mobileCheckout"));
    boolean isFacilitator = "true".equals(request.getParameter("facilitator"));
    CurrencyType currency = ("RUB".equals(request.getParameter("currency")))?CurrencyType.RUB:("EUR".equals(request.getParameter("currency")))?CurrencyType.EUR:CurrencyType.USD;
    DocRegRsType result = shopRegServiceWrapper.ordersRegistration(mode, provider, backUrl, mobilePhone, mobileCheckout, isFacilitator, eShopIdBySP, eShopURL,currency,systemId);
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
  <head><title>Тестирование платежа в пользу <%=provider%></title></head>
  <body>
      <c:set var="eribUID" value="<%=result.getERIBUID()%>"/>
      <c:set var="isSuccess" value="<%=isSuccess%>"/>
      <c:set var="partial" value="<%=modeStr%>"/>
      <c:if test="${isSuccess}">
          <br/>
          <c:if test="${partial == '2'}">
              <a href="http://localhost:8888/CSAFront/index.do" target="_blank">
                  Войти
              </a>
          </c:if>
          <c:if test="${partial != '2'}">
              <a href="http://localhost:8888/CSAFront/payOrderPaymentLogin.do?ReqId=${eribUID}" target="_blank">
                  Войти
              </a>
          </c:if>
      </c:if>
  </body>
</html>