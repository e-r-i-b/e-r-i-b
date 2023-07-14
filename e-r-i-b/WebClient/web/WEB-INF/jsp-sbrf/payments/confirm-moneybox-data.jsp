<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ties" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:form styleId="confirmMoneyBoxFormId" action="/private/async/moneybox/claim/confirm" onsubmit="return setEmptyAction();" show="true">
    <c:set var="form" value="${ConfirmPaymentByFormForm}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
    <c:set var="ajaxActionUrl" value="/private/async/moneybox/claim/confirm"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>

    <div id="paymentForm">
        ${form.html}

    <div class="buttonsArea iebuttons">
        <div class="backToService backToServiceBottom" style="float: left;">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.edit"/>
                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                <tiles:put name="bundle" value="paymentsBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="imageUrl" value="${globalUrl}/commonSkin/images"/>
                <tiles:put name="imageHover" value="backIconHover.png"/>
                <tiles:put name="image" value="backIcon.png"/>
                <tiles:put name="imagePosition"  value="left"/>
                <tiles:put name="onclick" value="getEdit(${form.id});"/>
            </tiles:insert>
        </div>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.cancel"/>
            <tiles:put name="commandHelpKey" value="button.cancel"/>
            <tiles:put name="bundle" value="claimsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="onclick">win.close('moneyBoxWinDiv');</tiles:put>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.dispatch"/>
            <tiles:put name="commandHelpKey" value="button.dispatch.help"/>
            <tiles:put name="bundle" value="securityBundle"/>
            <tiles:put name="onclick">confirm();</tiles:put>
        </tiles:insert>
    </div>
    <div class="clear"></div>

    <script type="text/javascript">
        <phiz:messages  id="error" bundle="${bundle}" field="field" title="title" message="error">
            addError('${phiz:escapeForJS(phiz:processBBCodeAndEscapeHtml(error, false), false)}', 'errorsMoneyBox');
        </phiz:messages>

        <phiz:messages id="messages" bundle="${bundle}" field="field" message="message">
            addMessage('${phiz:escapeForJS(phiz:processBBCodeAndEscapeHtml(messages, false), false)}','warningsMoneyBox');
        </phiz:messages>
    </script>
</html:form>