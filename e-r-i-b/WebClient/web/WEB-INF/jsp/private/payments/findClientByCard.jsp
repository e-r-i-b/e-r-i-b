<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/payments/findClientByCard">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="userInfo" value="${form.userInfo}"/>
    <c:if test="${not empty userInfo}">
        <c:set var="client" value="${form.userInfo.first}"/>
        {
            "fio": "${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}",
            "card" : "${phiz:getCutCardNumber(userInfo.second.number)}",
            "cardCurrency" : "${userInfo.second.currency.code}"
        }
    </c:if>
</html:form>