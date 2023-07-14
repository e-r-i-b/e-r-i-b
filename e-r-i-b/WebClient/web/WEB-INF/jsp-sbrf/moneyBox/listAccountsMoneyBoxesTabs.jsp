<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<tiles:insert definition="paymentTabs" flush="false">
    <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('account')}"/>
    <c:set var="count">
        <c:choose>
            <c:when test="${not empty target}">
                <c:out value="2"/>
            </c:when>
            <c:when test="${!showTemplates}">
                <c:out value="3"/>
            </c:when>
            <c:otherwise>
                <c:out value="4"/>
            </c:otherwise>
        </c:choose>
    </c:set>
    <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
        <c:set var="count" value="${count+1}"/>
    </c:if>
    <tiles:put name="count" value="${count}"/>
    <tiles:put name="tabItems">
        <tiles:insert definition="paymentTabItem" flush="false">
            <tiles:put name="position" value="first"/>
            <tiles:put name="active" value="false"/>
            <tiles:put name="title" value="Последние операции"/>
            <tiles:put name="action" value="/private/accounts/operations?id=${accountLink.id}"/>
        </tiles:insert>
        <tiles:insert definition="paymentTabItem" flush="false">
            <tiles:put name="active" value="false"/>
            <tiles:put name="title" value="Информация по вкладу"/>
            <tiles:put name="action" value="/private/accounts/info.do?id=${accountLink.id}"/>
        </tiles:insert>
        <c:if test="${empty target}">
            <c:if test="${showTemplates}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="false"/>
                    <tiles:put name="title" value="Шаблоны и платежи"/>
                    <tiles:put name="action" value="/private/account/payments.do?id=${accountLink.id}"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Графическая выписка"/>
                <tiles:put name="action" value="/private/accounts/graphic-abstract.do?id=${accountLink.id}"/>
            </tiles:insert>
        </c:if>
        <c:if test="${phiz:impliesService('CreateMoneyBoxPayment')}">
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="true"/>
                <tiles:put name="title">
                    <div style="float:left">
                        <div style="display:inline-block ;vertical-align:middle;"><c:out value="Копилка"/></div>
                        <div class="il-newIconMoneyBoxSmall" style="display:inline-block ;vertical-align:middle;"></div>
                    </div>
                </tiles:put>
                <tiles:put name="action" value="/private/accounts/moneyBoxList.do?id=${accountLink.id}"/>
            </tiles:insert>
        </c:if>
    </tiles:put>
</tiles:insert>