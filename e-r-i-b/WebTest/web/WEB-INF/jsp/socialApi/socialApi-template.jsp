<%@ page import="com.rssl.phizic.web.servlet.HttpServletEditableRequest" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<b>������:</b><br/>
<small>Cookie:</small><br/>
<html:text name="form" property="cookie" readonly="true" size="70"/><br/>
<small>�����:</small><br/>

URL:   <html:text name="form" property="url" readonly="true" size="65"/>
Proxy: <html:text name="form" property="proxyUrl" readonly="true"/>
port:  <html:text name="form" property="proxyPort" readonly="true"/>
<br/>
address: <html:text name="form" property="address" readonly="true" value="${address}" size="65"/>
<br/>

transactionToken: <html:text name="form" property="transactionToken" readonly="true" size="40"/>
<br/>

<hr/>
<c:if test="${form.submit}">
    <b>�����:</b><br/>
    <c:if test="${not empty form.response}">
        <c:choose>
            <c:when test="${form.response.status.code eq 0}">
                <span style="color:green;">��� ��������: OK</span>
            </c:when>
            <c:otherwise>
                <span style="color:red;">��� ��������: ${form.response.status.code}</span>
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
    <small>�������� Xml:</small>
    <br/>
    <textarea readonly cols="70" rows="20"><c:out value="${form.result}"/></textarea><br/>
    Status: <input type="text" value="${form.status}" readonly/><br/>
    Content-Type: <input type="text" value="${form.contentType}" readonly size="40"/><br/>
</c:if>
<br/>

<c:url var="url" value="/socialApi.do">
    <c:param name="url"       value="http://localhost:8888/socialApi"/>
    <c:param name="cookie"    value="${form.cookie}"/>
    <c:param name="proxyUrl"  value="${form.proxyUrl}"/>
    <c:param name="proxyPort" value="${form.proxyPort}"/>

    <%--
       - appType - ��� ����������
       - devID   - ������� ������������� �������
       --%>
    <c:param name="params" value="appType=vkontakte&operation=button.login&mGUID=4e61ee1b04558f39b5ed0e409169af19&extClientID=5465478654"/>
</c:url>

&nbsp;<a href='${url}'> ������� �������� </a>&nbsp;

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
            <c:when test="${fname eq 'RurPayJurSB'}">/socialApi/servicePayment.do</c:when>
            <c:when test="${fname eq 'JurPayment'}">/socialApi/jurPayment.do</c:when>
            <c:when test="${fname eq 'LoanPayment'}">/socialApi/loanPayment.do</c:when>
            <c:when test="${fname eq 'LoanPaymentLongOffer'}">/socialApi/loanPaymentLongOffer.do</c:when>
            <c:when test="${fname eq 'InternalPayment'}">/socialApi/internalPayment.do</c:when>
            <c:when test="${fname eq 'InternalPaymentLongOffer'}">/socialApi/internalPaymentLongOffer.do</c:when>
            <c:when test="${fname eq 'BlockingCardClaim'}">/socialApi/blockingCardClaim.do</c:when>
            <c:when test="${fname eq 'RurPayment'}">/socialApi/rurPayment.do</c:when>
            <c:when test="${fname eq 'RurPaymentLongOffer'}">/socialApi/rurPaymentLongOffer.do</c:when>
            <c:when test="${fname eq 'IMAPayment'}">/socialApi/imaPayment.do</c:when>
            <c:when test="${fname eq 'IMAOpeningClaim'}">/socialApi/imaOpeningClaim.do</c:when>
            <c:when test="${fname eq 'AccountOpeningClaim'}">/socialApi/accountOpeningClaim.do</c:when>
            <c:when test="${fname eq 'AccountClosingPayment'}">/socialApi/accountClosingPayment.do</c:when>
            <c:when test="${fname eq 'CreateAutoPaymentPayment' or fname eq 'CreateAutoSubscriptionPayment'}">/socialApi/createAutoPayment.do</c:when>
            <c:when test="${fname eq 'EditAutoPaymentPayment'}">/socialApi/editAutoPaymentPayment.do</c:when>
            <c:when test="${fname eq 'RefuseAutoPaymentPayment'}">/socialApi/refuseAutoPayment.do</c:when>
            <c:when test="${fname eq 'EditAutoSubscriptionPayment'}">/socialApi/editAutoSubscriptionPayment.do</c:when>
            <c:when test="${fname eq 'DelayAutoSubscriptionPayment'}">/socialApi/delayAutoSubscriptionPayment.do</c:when>
            <c:when test="${fname eq 'RecoveryAutoSubscriptionPayment'}">/socialApi/recoveryAutoSubscriptionPayment.do</c:when>
            <c:when test="${fname eq 'CloseAutoSubscriptionPayment'}">/socialApi/closeAutoSubscriptionPayment.do</c:when>
            <c:when test="${fname eq 'LoanCardProduct' or fname eq 'LoanCardOffer'}">/socialApi/cardOpeningClaim.do</c:when>
            <c:when test="${fname eq 'LoanProduct'}">/socialApi/loanProductOpeningClaim.do</c:when>
            <c:when test="${fname eq 'LoanOffer'}">/socialApi/loanOfferOpeningClaim.do</c:when>
            <c:when test="${fname eq 'RefuseLongOffer'}">/socialApi/refuseLongOffer.do</c:when>
            <c:when test="${fname eq 'ExternalProviderPayment'}">/socialApi/externalProviderPayment.do</c:when>
            <c:when test="${fname eq 'AirlineReservationPayment'}">/socialApi/airlineReservationPayment.do</c:when>
        </c:choose>
    </c:set>
    <c:if test="${not empty repeatUrl}">
        <c:url var="paymentUrl" value="${repeatUrl}">
            <c:param name="url" value="${form.url}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
        </c:url>
        <html:link href="${paymentUrl}">������ �������</html:link>
    </c:if>
</c:if>
