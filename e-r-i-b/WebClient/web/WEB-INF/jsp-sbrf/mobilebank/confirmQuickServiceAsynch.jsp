<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--todo CHG059738 удалить--%>
<c:set var="thisActionUrl" value="/private/async/mobilebank/quickService"/>
<html:form action="${thisActionUrl}">
    <c:set var="form"                    value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmableRegistration" value="${form.confirmableRegistration}"/>
    <c:set var="mainCard"                value="${confirmableRegistration.mainCard}"/>
    <c:set var="confirmStrategy"         value="${form.confirmStrategy}"/>

    <c:if test="${not empty confirmableRegistration}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(confirmableRegistration)}" scope="request"/>
    </c:if>
    <c:choose>
        <c:when test="${not empty confirmRequest and confirmRequest.preConfirm}">
            <c:set var="strategyType" value="${confirmRequest.strategyType}"/>
            <c:set var="confirmTemplate" value="confirm_${strategyType}"/>
            <tiles:insert definition="${confirmTemplate}" flush="false">
                <tiles:put name="title"             value="Подтверждение операции"/>
                <tiles:put name="confirmStrategy"   beanName="confirmStrategy"/>
                <tiles:put name="confirmableObject" value="confirmableRegistration"/>
                <tiles:put name="confirmRequest"    beanName="confirmRequest"/>
                <tiles:put name="data">
                    <input type="hidden" name="cardCode" value="${mainCard.cardlink.id}"/>
                </tiles:put>
                <c:if test="${strategyType == 'sms'}">
                    <tiles:put name="hint">
                        Для завершения операции введите SMS-пароль, полученный на Ваш мобильный телефон, и
                        нажмите на кнопку &laquo;Подтвердить&raquo;.
                    </tiles:put>
                </c:if>
                <tiles:put name="useAjax" value="true"/>
                <tiles:put name="ajaxUrl" value="${thisActionUrl}"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            &nbsp;
            <script type="text/javascript">

                <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
                    var error = '<bean:write name="error" filter="false"/>';
                    if (window.addError != undefined)
                        addError('<bean:write name="error" filter="false"/>', null, true);
                </phiz:messages>

                <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
                    var message = '<bean:write name="error" filter="false"/>';
                    if (window.addMessage != undefined)
                        addMessage('<bean:write name="messages" filter="false" ignore="true"/>');
                </phiz:messages>

                if (window.win != undefined)
                    win.close(confirmOperation.windowId);
            </script>
        </c:otherwise>
    </c:choose>
</html:form>