<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<script type="text/javascript">
    function returnToShortClaim()
    {
        win.open('shortClaimReturnConfirmWindow');
        return false;
    }

    function goToShortClaim()
    {
        win.close('shortClaimReturnConfirmWindow');
        window.location = document.webRoot + '/private/payments/payment.do?id=${documentId}';
    }
</script>

<div class="extendedClaimBlock">
    <table>
        <tr>
            <td class="extendedClaimTitle">
                <span class="fastApplicationTitle">Расширенная анкета</span>
            </td>
        </tr>
    </table>
    <div class="clear"></div>
</div>

<div class="paymentHeader">
    <span class="text-gray">Поля, обязательные для заполнения, отмечены </span> <span class="text-red">*</span> <span class="text-gray">.</span>
</div>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="stateCode" value="${form.document.state.code}"/>
<c:if test="${stateCode eq 'INITIAL7'}">
    <tiles:insert page="departmentSelection.jsp" flush="false"/>
</c:if>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="shortClaimReturnConfirmWindow"/>
    <tiles:put name="data">
        <h2>Данные будут утеряны</h2>
        <%--дефолтное сообщение--%>
        <div class="messageContainer">При переходе к быстрой заявке на кредит все данные, которые вы вносили в расширенной анкете, будут утеряны.</div>
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="simpleLink"/>
                <tiles:put name="onclick" value="win.close('shortClaimReturnConfirmWindow'); return false;"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.toShortLoanClaim"/>
                <tiles:put name="commandHelpKey" value="button.toShortLoanClaim"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="onclick" value="goToShortClaim();"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
