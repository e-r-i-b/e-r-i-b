<%--
  User: usachev
  Date: 11.12.14
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/fund/fundRequestInformation">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="response" value="${form.response}"/>
    <tiles:insert definition="empty">
        <tiles:put name="data">
            <h3 class="marginTop20 marginLeft80"><b>Информация о входящем запросе на сбор средств</b></h3>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title" value="ID:"/>
                <tiles:put name="data" value="${response.externalId}"/>
                <tiles:put name="needMargin" value="true"/>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title" value="ФИО инициатора:"/>
                <tiles:put name="data" value="${response.initiatorSurName} ${response.initiatorFirstName} ${response.initiatorPatrName}"/>
                <tiles:put name="needMargin" value="true"/>
            </tiles:insert>
            <c:if test="${not empty response.requiredSum}">
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title" value="Общая необходимая сумма:"/>
                    <tiles:put name="data" value="${response.requiredSum} руб."/>
                    <tiles:put name="needMargin" value="true"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title" value="Текущая собранная сумма:"/>
                <tiles:put name="data" value="${form.accumulatedSum} руб."/>
                <tiles:put name="needMargin" value="true"/>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title" value="Статус:"/>
                <tiles:put name="data" value="${response.viewRequestState.description}"/>
                <tiles:put name="needMargin" value="true"/>
            </tiles:insert>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title" value="Дата создания:"/>
                <tiles:put name="data" value="${phiz:сalendarToString(response.createdDate)}"/>
                <tiles:put name="needMargin" value="true"/>
            </tiles:insert>
            <c:if test="${not empty response.viewClosedDate}">
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title" value="Дата закрытия:"/>
                    <tiles:put name="data" value="${phiz:сalendarToString(response.viewClosedDate)}"/>
                    <tiles:put name="needMargin" value="true"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>

</html:form>