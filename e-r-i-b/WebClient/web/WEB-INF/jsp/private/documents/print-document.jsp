<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/print">
<tiles:insert definition="printDoc">

<tiles:put name="data" type="string">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
	<c:set var="metadata" value="${form.metadata}"/>
	<c:set var="defaultPrintForm" value="${metadata.additionalAttributes.defaultPrintForm}"/>
    <c:set var="isShortLoanClaim" value="${metadata.form.name == 'ShortLoanClaim'}"/>
    <c:set var="isLoanOffer" value="${ metadata.form.name == 'LoanOffer'}"/>
    <c:set var="VirtualCardClaim" value="${ metadata.form.name == 'VirtualCardClaim'}"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="state" value="${form.document.state.code}"/>

<c:choose>
	<c:when test="${defaultPrintForm}">
		<tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value="${form.metadata.form.name}"/>
			<tiles:put name="alignTable" value="center"/>
			<tiles:put name="name" value="${form.metadata.form.description}"/>
			<tiles:put name="description" value="${form.metadata.form.detailedDescription}"/>
			<tiles:put name="docDate" value="${form.dateString}"/>
            <c:choose>
                <c:when test="${state=='DELAYED_DISPATCH' || state=='OFFLINE_DELAYED'}">
                    <tiles:put name="stamp" value="delayed"/>
                </c:when>
                <c:when test="${state=='DISPATCHED' || state=='STATEMENT_READY' || state=='UNKNOW' || state=='SENT' || state=='BILLING_CONFIRM_TIMEOUT'
                                || state=='BILLING_GATE_CONFIRM_TIMEOUT' || state=='ABS_RECALL_TIMEOUT' || state=='ABS_GATE_RECALL_TIMEOUT'}">
                    <tiles:put name="stamp" value="received"/>
                </c:when>
                <c:when test="${state == 'ADOPTED'}">
                    <tiles:put name="stamp" value="accepted"/>
                </c:when>
                <c:when test="${state=='EXECUTED' || state=='SUCCESSED' || state=='TICKETS_WAITING'}">
                    <tiles:put name="stamp" value="executed"/>
                </c:when>
                <c:when test="${state=='REFUSED'}">
                    <tiles:put name="stamp" value="refused"/>
                </c:when>
                <c:when test="${state=='WAIT_CONFIRM'}">
                    <tiles:put name="stamp" value="waitconfirm"/>
                </c:when>
            </c:choose>
            <tiles:put name="additionInfo" value="${form.additionPaymentInfo}" type="string"/>
			<tiles:put name="bankBIC" value="${form.bankBIC}"/>
			<tiles:put name="bankName" value="${form.bankName}"/>
			<tiles:put name="data">${form.html}</tiles:put>
		</tiles:insert>
	</c:when>
	<c:otherwise>
		${form.html}
        <c:set var="OSB" value="${phiz:getOSB(form.department)}"/>
		<c:set var="bankBIC" value="${form.bankBIC}"/>
		<c:set var="bankName" value="${form.bankName}"/>
		<c:set var="docDate" value="${form.dateString}"/>
        <c:set var="printStamp" value="${state == 'DISPATCHED' || state == 'STATEMENT_READY' || state == 'EXECUTED' || state == 'REFUSED'}"/>

        <c:if test="${state=='DISPATCHED' || state=='STATEMENT_READY'}">
            <c:set var="imageSrc" value="${imagePath}/stampReceived_noBorder.gif"/>
        </c:if>
        <c:if test="${state=='EXECUTED'}">
            <c:set var="imageSrc" value="${imagePath}/stampExecuted_noBorder.gif"/>
        </c:if>
        <c:if test="${state=='REFUSED'}">
            <c:set var="imageSrc" value="${imagePath}/stampRefused_noBorder.gif"/>
        </c:if>
        <c:if test="${state=='DISPATCHED' && isShortLoanClaim}">
            <c:set var="imageSrc" value="${imagePath}/stampProcessed_noBorder.gif"/>
        </c:if>
        <c:if test="${printStamp}">
            <div class="stampBlockPosition <c:if test="${isLoanOffer || VirtualCardClaim || isShortLoanClaim}">stampBottomPosition</c:if>">
                <c:if test="${isShortLoanClaim}">
                    <div style="text-align:center;">
                        <img src="${imageSrc}" width="145px" height="30px"/>
                    </div>
                </c:if>
                <span class="stampTitle"><c:out value="${OSB.name}" default="${bankName}"/></span><br/>
                <span class="stampText">ÁÈÊ:<c:out value="${OSB.BIC}" default="${bankBIC}"/></span><br/>
                <span class="stampText">Êîðð.Ñ÷åò: <c:out value="${phiz:getCorrByBIC(OSB.BIC)}" default="${phiz:getCorrByBIC(bankBIC)}"/></span><br/>
                <c:if test="${!isShortLoanClaim}">
                    <div style="text-align:center;">
                        <img src="${imageSrc}" border="0"/>
                    </div>
                </c:if>
            </div>
        </c:if>
	</c:otherwise>
	</c:choose>
</tiles:put>
	
</tiles:insert>
</html:form>

