<%@ page import="com.rssl.phizic.web.servlet.HttpServletEditableRequest" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<script type="text/javascript">
    var dojoConfig = {
        async: true,
        parseOnLoad: true,
        baseUrl: "${initParam.resourcesRealPath}/scripts/",     // корень со всеми js
        packages: [
            { name: "dojo", location: "dojo" }                 // здесь лежит dojo
        ]
    };
</script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/dojo/dojo.js"></script>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="response" value="${form.response}"/>
<input type="hidden" name="version" value="${form.version}"/>

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

mobileSdkData: <html:text name="form" property="mobileSdkData" readonly="false" size="70"/>
<br/>

<hr/>
<c:if test="${form.submit}">
    <b>Ответ:</b><br/>
    <c:if test="${not empty response}">
        <c:choose>
            <c:when test="${response.status.code eq 0}">
                <span style="color:green;">Код возврата: OK</span>
            </c:when>
            <c:otherwise>
                <span style="color:red;">Код возврата: ${response.status.code}</span>
            </c:otherwise>
        </c:choose>
        <br/>
        <c:if test="${not empty response.status.errors}">
            <c:forEach var="error" items="${response.status.errors.error}">
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
        <c:if test="${not empty response.status.warnings}">
            <c:forEach var="warning" items="${response.status.warnings.warning}">
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
        <c:choose>
            <c:when test="${form.version > 4.00 and not empty response.initialData.form}">
                <table>
                    <tr><td>form:</td><td><input type="text" value="${response.initialData.form}" disabled="disabled" size="30"/></td></tr>
                </table>
            </c:when>
            <c:when test="${not empty response.document}">
                <table>
                    <tr><td>id:</td><td><input type="text" value="${response.document.id}" disabled="disabled" size="30"/></td></tr>
                    <tr><td>form:</td><td><input type="text" value="${response.document.form}" disabled="disabled" size="30"/></td></tr>
                    <tr><td>document status:</td><td><input type="text" value="${response.document.status}" disabled="disabled" size="30"/></td></tr>
                </table>
            </c:when>
        </c:choose>
    </c:if>
    <hr/>
</c:if>
<br/>
<div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
    ${data}
</div>
<hr/>
<%--Если в ответе пришли ошибки, то ненужно никаких кнопок подтверждения--%>
<c:if test="${address != '/private/payments/confirm.do'}">
    <tiles:insert page="payments/confirm-payment-buttons.jsp" flush="false"/>
</c:if>

<c:if test="${operation != ''}">
    <html:submit property="operation" value="${operation}"/>
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

<c:if test="${not empty form.version}">
    <c:choose>
        <c:when test="${fn:startsWith(form.version, '5.')}">
            <c:set var="root" value="mobile5"/>
        </c:when>
        <c:when test="${fn:startsWith(form.version, '6.')}">
            <c:set var="root" value="mobile6"/>
        </c:when>
        <c:when test="${fn:startsWith(form.version, '7.')}">
            <c:set var="root" value="mobile7"/>
        </c:when>
        <c:when test="${fn:startsWith(form.version, '8.')}">
            <c:set var="root" value="mobile8"/>
        </c:when>
        <c:when test="${fn:startsWith(form.version, '9.')}">
            <c:set var="root" value="mobile9"/>
        </c:when>
    </c:choose>
    
    <c:if test="${not empty root}">
        <c:url var="mainUrl" value="/${root}.do">
            <c:param name="url" value="http://localhost:8888/${root}"/>
            <c:param name="cookie" value="${form.cookie}"/>
            <c:param name="proxyUrl" value="${form.proxyUrl}"/>
            <c:param name="proxyPort" value="${form.proxyPort}"/>
            <c:choose>
                <c:when test="${form.version >= 6.00}">
                    <c:param name="params" value="version=${form.version}&appType=iPhone&appVersion=82&deviceName=SiemensA35&operation=button.login&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3&devID=F5F276B4BF71039948B9590B21E3B2FE"/>
                </c:when>
                <c:otherwise>
                    <c:param name="params" value="version=${form.version}&appType=iPhone&appVersion=82&deviceName=SiemensA35&operation=button.login&isLightScheme=false&mGUID=27B9590B2F6B4BF7B2FEF500399481E3"/>
                </c:otherwise>
            </c:choose>
            <c:param name="version" value="${form.version}"/>
        </c:url>

        &nbsp;<a href='${mainUrl}'> Главная страница </a>&nbsp;
    </c:if>

    <c:choose>
        <c:when test="${form.version > 4.00 and not empty response.initialData.form}">
            <c:set var="fname" value="${response.initialData.form}"/>
        </c:when>
        <c:when test="${not empty response.document.form}">
            <c:set var="fname" value="${response.document.form}"/>
        </c:when>
    </c:choose>
    <c:if test="${not empty fname}">
        <c:set var="repeatUrl">
            <c:choose>
                <c:when test="${fname eq 'RurPayJurSB'}">/mobileApi/servicePayment.do</c:when>
                <c:when test="${fname eq 'JurPayment'}">/mobileApi/jurPayment.do</c:when>
                <c:when test="${fname eq 'LoanPayment'}">/mobileApi/loanPayment.do</c:when>
                <c:when test="${fname eq 'LoanPaymentLongOffer'}">/mobileApi/loanPaymentLongOffer.do</c:when>
                <c:when test="${fname eq 'InternalPayment'}">/mobileApi/internalPayment.do</c:when>
                <c:when test="${fname eq 'InternalPaymentLongOffer'}">/mobileApi/internalPaymentLongOffer.do</c:when>
                <c:when test="${fname eq 'BlockingCardClaim'}">/mobileApi/blockingCardClaim.do</c:when>
                <c:when test="${fname eq 'RurPayment'}">/mobileApi/rurPayment.do</c:when>
                <c:when test="${fname eq 'RurPaymentLongOffer'}">/mobileApi/rurPaymentLongOffer.do</c:when>
                <c:when test="${fname eq 'IMAPayment'}">/mobileApi/imaPayment.do</c:when>
                <c:when test="${fname eq 'IMAOpeningClaim'}">/mobileApi/imaOpeningClaim.do</c:when>
                <c:when test="${fname eq 'AccountOpeningClaim'}">/mobileApi/accountOpeningClaim.do</c:when>
                <c:when test="${fname eq 'AccountClosingPayment'}">/mobileApi/accountClosingPayment.do</c:when>
                <c:when test="${fname eq 'CreateAutoPaymentPayment' or fname eq 'CreateAutoSubscriptionPayment'}">/mobileApi/createAutoPayment.do</c:when>
                <c:when test="${fname eq 'EditAutoPaymentPayment'}">/mobileApi/editAutoPaymentPayment.do</c:when>
                <c:when test="${fname eq 'RefuseAutoPaymentPayment'}">/mobileApi/refuseAutoPayment.do</c:when>
                <c:when test="${fname eq 'EditAutoSubscriptionPayment'}">/mobileApi/editAutoSubscriptionPayment.do</c:when>
                <c:when test="${fname eq 'DelayAutoSubscriptionPayment'}">/mobileApi/delayAutoSubscriptionPayment.do</c:when>
                <c:when test="${fname eq 'RecoveryAutoSubscriptionPayment'}">/mobileApi/recoveryAutoSubscriptionPayment.do</c:when>
                <c:when test="${fname eq 'CloseAutoSubscriptionPayment'}">/mobileApi/closeAutoSubscriptionPayment.do</c:when>
                <c:when test="${fname eq 'LoanCardProduct' or fname eq 'LoanCardOffer'}">/mobileApi/cardOpeningClaim.do</c:when>
                <c:when test="${fname eq 'LoanProduct'}">/mobileApi/loanProductOpeningClaim.do</c:when>
                <c:when test="${fname eq 'LoanOffer'}">/mobileApi/loanOfferOpeningClaim.do</c:when>
                <c:when test="${fname eq 'RefuseLongOffer'}">/mobileApi/refuseLongOffer.do</c:when>
                <c:when test="${fname eq 'ExternalProviderPayment'}">/mobileApi/externalProviderPayment.do</c:when>
                <c:when test="${fname eq 'AirlineReservationPayment'}">/mobileApi/airlineReservationPayment.do</c:when>
                <c:when test="${fname eq 'ChangeCreditLimitClaim'}">/mobileApi/changeCreditLimitClaim.do</c:when>
            </c:choose>
        </c:set>
        <c:if test="${not empty repeatUrl}">
            <c:url var="paymentUrl" value="${repeatUrl}">
                <c:param name="url" value="${form.url}"/>
                <c:param name="cookie" value="${form.cookie}"/>
                <c:param name="proxyUrl" value="${form.proxyUrl}"/>
                <c:param name="proxyPort" value="${form.proxyPort}"/>
                <c:param name="version" value="${form.version}"/>
            </c:url>
            <html:link href="${paymentUrl}">Начать сначала</html:link>
        </c:if>
    </c:if>
</c:if>