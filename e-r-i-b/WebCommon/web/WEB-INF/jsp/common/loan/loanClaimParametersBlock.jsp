<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="monthlyPaymentUrlSuffix" value="loanClaim.monthlyPayment.url"/>
<c:set var="urlForMonthlyPayment" value="${phiz:getBankInfoUrlBySuffix(monthlyPaymentUrlSuffix)}"/>
<%--@elvariable id="document" type="com.rssl.phizic.business.documents.payments.ExtendedLoanClaim"--%>

<script type="text/javascript">
<c:if test="${not isOfficeLoan}">
    function returnToLoanChoice()
    {
        window.location = document.webRoot + '/private/payments/payment.do?id=${document.id}';
    }
</c:if>
    function openMonthlyPaymentWindow(event)
    {
       <c:if test="${not empty urlForMonthlyPayment}">
           var winparams = "resizable=1,menubar=0,toolbar=0,scrollbars=1";
           var pwin = openWindow(event, "${urlForMonthlyPayment}", "businessman", winparams);
           pwin.focus();
       </c:if>
    }
</script>

<table class="paymentHeader">
    <tr>
        <td>
            ${description}
        </td>
    </tr>
</table>
<div class="clear"></div>

<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="floatClass" value=""/>
<c:if test="${person != null}">
    <c:set var="floatClass" value="float"/>
</c:if>
<div class="creditOffers mainOffersInfo">
    <div class="creditOffersTop">
        <c:if test="${not isOfficeLoan}">
            <c:set var="stateCode" value="${document.state.code}"/>
            <c:if test="${stateCode == 'SAVED' || stateCode == 'INITIAL' || stateCode == 'INITIAL2' || stateCode == 'INITIAL3' || stateCode == 'INITIAL4' || stateCode == 'INITIAL5' || stateCode == 'INITIAL6' || stateCode == 'INITIAL7' || stateCode == 'INITIAL8'}">
                <div class="offersBtn">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.changeConditions"/>
                        <tiles:put name="commandTextKey" value="button.changeConditions"/>
                        <tiles:put name="commandHelpKey" value="button.changeConditions"/>
                        <tiles:put name="bundle" value="paymentsBundle"/>
                        <tiles:put name="viewType" value="blueGrayLink"/>
                    </tiles:insert>
                </div>
            </c:if>
        </c:if>
        <div id="openDetailClick" class="creditOffersType pointer"><span><c:out value="${document.productName}"/></span></div>
    </div>
    <c:if test="${not isOfficeLoan}">
        <tiles:insert page="loansOfferDescription.jsp" flush="false">
            <tiles:put name="conditionId" value="${document.conditionId}"/>
            <tiles:put name="floatClass" value="${floatClass}"/>
        </tiles:insert>
    </c:if>

    <table cellpadding="0" cellspacing="0" class="mainCreditCondition">
        <tr>
            <th class="offerSum align-right" >Сумма кредита, ${phiz:getCurrencyName(document.loanAmount.currency)}</th>
            <th class="offerPeriodColTtl align-left">На срок</th>
            <th class="offerRateTtl align-left">Ставка</th>
            <th></th>
        </tr>
        <tr>
            <td class="offerSum align-right">
                <span class="amount">${phiz:formatDecimalToAmount(document.loanAmount.decimal, 2)}</span>
            </td>
            <td class="offerPeriodCol">
                <span class="amount"><c:out value="${document.loanPeriod}"/></span>&nbsp;мес.
            </td>
            <td class="offerRate">
                <span class="amount">
                    <c:choose>
                        <c:when test="${not isOfficeLoan}">
                            <c:out value="${phiz:formatLoanRate(document.loanRate)}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${phiz:formatPercentRate(document.loanRate)}"/>
                        </c:otherwise>
                    </c:choose>
                </span>
            </td>
            <td class="mounthPaySum align-right">
               <a class="tblOutsideLink" href="" onclick="openMonthlyPaymentWindow(event);">
                   <span>
                       <bean:message key="loan.link.monthlyPayment" bundle="paymentsBundle"/>
                   </span>
                   <span class="tblOutsideLinkIcon"></span>
               </a>
            </td>
        </tr>
    </table>

</div>
<div class="clear"></div>