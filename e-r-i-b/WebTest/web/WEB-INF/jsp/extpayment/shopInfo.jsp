<%@ page contentType="text/html;charset=Windows-1251" language="java" %>
<%@ page import="com.rssl.phizic.test.webgate.shop.generated.DocInfoStatRqType" %>
<%@ page import="com.rssl.phizic.test.webgate.shop.generated.DocInfoStatRsType" %>
<%@ page import="com.rssl.phizic.test.webgate.shop.generated.ShopInfoServiceSoapBindingImpl" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopRegServiceWrapper" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.ShopSystemName" %>
<%@ page import="com.rssl.phizic.test.wsgateclient.shop.generated.DocRegRsType" %>
<%@ page import="com.rssl.phizic.utils.RandomGUID" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.rssl.phizic.test.webgate.shop.generated.DocInfoStatRqDocumentsType" %>
<%@ page import="com.rssl.phizic.gate.einvoicing.TypeOrder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
    ShopRegServiceWrapper shopRegServiceWrapper = new ShopRegServiceWrapper("http://localhost:8888/ShopListener/axis-services/DocRegServicePort");

    DocRegRsType result = shopRegServiceWrapper.ordersRegistration(TypeOrder.F, ShopSystemName.EVENTIM, "ya.ru", null, false, false, null, null, null, null);

    if (result.getStatus().getStatusCode() == 0)
    {
        out.println("Регистрация прошла успешно.");
        out.println("Идентификатор документа в системе ЕРИБ: " + result.getERIBUID());

        out.println("Проверка документа во внешней системе");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DocInfoStatRqType checkRq = new DocInfoStatRqType();
        checkRq.setRqTm(simpleDateFormat.format(Calendar.getInstance().getTime()));
        checkRq.setRqUID(new RandomGUID().getStringValue());
        checkRq.setDocuments(new DocInfoStatRqDocumentsType(result.getERIBUID(), null));

        ShopInfoServiceSoapBindingImpl shopInfo = new ShopInfoServiceSoapBindingImpl();
        DocInfoStatRsType checkRs =  shopInfo.docInfoStat(checkRq);

        if (checkRs.getDocuments().getResult().getStatus().getStatusCode() == 0)
        {
            out.println("Заказ актуален!!");
        }
        else
            out.println("Заказ  не актуален =(");
    }
    else
    {
        out.println("Регистрация завершилась с ошибкой");
    }
%>
  <head><title>Test EVENTIME</title></head>
  <body>
  </body>
</html>