<%@ page import="com.rssl.phizic.web.servlet.HttpServletEditableRequest" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<b>Запрос:</b> <br/>
<small>Cookie:</small>
<br/>
<html:text name="form" property="cookie" readonly="true" size="70"/>
<br/>
<small>Адрес:</small>
<br/>
URL: <html:text name="form" property="url" readonly="true" size="65"/>
Proxy: <html:text name="form" property="proxyUrl" readonly="true"/>
port: <html:text name="form" property="proxyPort" readonly="true"/>
<br/>
address: <html:text name="form" property="address" readonly="true" value="${address}" size="65"/>
<br/>

transactionToken: <html:text name="form" property="transactionToken" readonly="true" size="40"/>
<br/>

<hr/>
<c:if test="${form.submit}">
    <b>Ответ:</b><br/>
    <c:if test="${not empty form.response}">
        <c:choose>
            <c:when test="${form.response.status.code eq 0}">
                <span style="color:green;">Код возврата: OK</span>
            </c:when>
            <c:otherwise>
                <span style="color:red;">Код возврата: ${form.response.status.code}</span>
            </c:otherwise>
        </c:choose>
        <br/>
        <c:if test="${not empty form.response.status.errors}">
            <c:forEach var="error" items="${form.response.status.errors.error}">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <span style="color:red;">
                    <c:if test="${not empty error.elementId}">
                        <c:out value="${error.elementId}"/> :
                    </c:if>
                    <c:out value="${error.text}"/>
                </span>
                <br/>
            </c:forEach>
        </c:if>
        <c:if test="${not empty form.response.status.warnings}">
            <c:forEach var="warning" items="${form.response.status.warnings.warning}">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <span style="color:peru;">
                    <c:if test="${not empty warning.elementId}">
                        <c:out value="${warning.elementId}"/> :
                    </c:if>
                    <c:out value="${warning.text}"/>
                </span>
                <br/>
            </c:forEach>
        </c:if>
    </c:if>
    <hr/>
</c:if>
<br/>
${data}
<hr/>
<c:if test="${address != '/private/payments/confirm.do'}">
    <tiles:insert page="payments/confirm-payment-buttons.jsp" flush="false"/>
</c:if>

<c:if test="${operation != ''}">
    <html:submit property="operation" value="${operation}"/><br/>
</c:if>
<c:if test="${formName != ''}">
    <html:hidden name="form" property="form" value="${formName}"/>
</c:if>
<br/>

<c:if test="${form.submit}">
    <small>Ответный Xml:</small>
    <br/>
    <textarea readonly cols="70" rows="20"><c:out value="${form.result}"/></textarea><br/>
    Status: <input type="text" value="${form.status}" readonly/><br/>
    Content-Type: <input type="text" value="${form.contentType}" readonly size="40"/><br/>
</c:if>
<br/>

<c:url var="url" value="/atm.do">
    <c:param name="url" value="http://localhost:8888/atm"/>
    <c:param name="cookie" value="${form.cookie}"/>
    <c:param name="proxyUrl" value="${form.proxyUrl}"/>
    <c:param name="proxyPort" value="${form.proxyPort}"/>
    <c:param name="params" value="operation=login&pan=0629778350443415&codeATM=qwe123"/>
</c:url>

&nbsp;<a href='${url}'> Главная страница </a>&nbsp;

<c:choose>
    <c:when test="${not empty form.response.initialData.form}">
        <c:set var="fname" value="${form.response.initialData.form}"/>
    </c:when>
    <c:when test="${not empty form.response.document.form}">
        <c:set var="fname" value="${form.response.document.form}"/>
    </c:when>
</c:choose>
<c:if test="${not empty fname}">
    <c:set var="repeatUrl">
        <c:choose>
            <c:when test="${fname eq 'AccountOpeningClaim'}">/atm/accountOpeningClaim.do</c:when>
            <c:when test="${fname eq 'AccountClosingPayment'}">/atm/accountClosingPayment.do</c:when>
            <c:when test="${fname eq 'ChangeCreditLimitClaim'}">/atm/changeCreditLimitClaim.do</c:when>
        </c:choose>
    </c:set>
    <c:if test="${not empty repeatUrl}">
        <c:url var="paymentUrl" value="${repeatUrl}">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
        </c:url>
        <html:link href="${paymentUrl}">Начать сначала</html:link>
    </c:if>
</c:if>
