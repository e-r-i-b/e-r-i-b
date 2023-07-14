<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<tiles:insert definition="mobileDateTimeType" flush="false">
    <tiles:put name="name" value="date"/>
    <tiles:put name="calendar" beanName="transaction" beanProperty="date"/>
</tiles:insert>

<c:set var="sum" value=""/>
<c:set var="sign" value=""/>
<%--Если creditSum 0, а accountCreditSum не ноль, то выводим accountCreditSum.
    Аналогично для debitSum и  accountDebitSum.--%>
<c:choose>
    <c:when test="${(empty transaction.creditSum || transaction.creditSum.decimal == 0) && !empty transaction.accountCreditSum && transaction.accountCreditSum.decimal!=0}">
        <c:set var="sum" value="${transaction.accountCreditSum}"/>
        <c:set var="sign" value="+"/>
    </c:when>
    <c:when test="${!empty transaction.creditSum && transaction.creditSum.decimal != 0}">
        <c:set var="sum" value="${transaction.creditSum}"/>
        <c:set var="sign" value="+"/>
    </c:when>
    <c:when test="${(empty transaction.debitSum || transaction.debitSum.decimal == 0) && !empty transaction.accountDebitSum && transaction.accountDebitSum.decimal!=0}">
        <c:set var="sum" value="${transaction.accountDebitSum}"/>
        <c:set var="sign" value="-"/>
    </c:when>
    <c:when test="${!empty transaction.debitSum && transaction.debitSum.decimal != 0}">
        <c:set var="sum" value="${transaction.debitSum}"/>
        <c:set var="sign" value="-"/>
    </c:when>
</c:choose>

<tiles:insert definition="mobileMoneyType" flush="false">
    <tiles:put name="name" value="sum"/>
    <tiles:put name="money" beanName="sum"/>
    <tiles:put name="sign" value="${sign}"/>
</tiles:insert>

<c:set var="description" value=""/>
<c:if test="${not empty transaction.description}">
    <c:set var="description" value="${transaction.description}"/>
</c:if>
<c:if test="${not empty transaction.shopInfo}">
    <c:if test="${description != ''}">
        <c:set var="description" value="${description} "/>
    </c:if>
    <c:set var="description" value="${description}${transaction.shopInfo}"/>
</c:if>
<description><![CDATA[${description}]]></description>
