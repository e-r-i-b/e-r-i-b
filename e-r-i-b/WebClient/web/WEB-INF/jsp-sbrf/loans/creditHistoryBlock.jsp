<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:insert definition="paymentsPaymentCardsDiv"
              operation="CreditReportOperation"
              service="CreditReportService"
              flush="false">
    <tiles:put name="serviceId" value="CreditReportPayment"/>
    <tiles:put name="image" value="credit_64.png"/>
    <tiles:put name="action" value="/private/credit/report"/>
    <tiles:put name="description" value="Полная информация по Вашим кредитам и кредитным картам"/>
    <tiles:put name="listPayTitle">
        Кредитная история <img src='${imagePath}/newGroup.png' class='newGroup'/>
    </tiles:put>
</tiles:insert>