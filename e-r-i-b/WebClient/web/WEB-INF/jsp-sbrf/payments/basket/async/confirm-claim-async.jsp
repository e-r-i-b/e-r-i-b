<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form styleId="asyncConfirmSubscriptionId" action="/private/async/basket/subscription/claim" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.document)}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="formName" value="${metadata.form.name}"/>
    <c:set var="isCreateAutoPay" value="${formName == 'RurPayJurSB' && form.document.longOffer}"/>

    <c:choose>
        <c:when test="${not empty confirmRequest and confirmRequest.preConfirm}">
            <tiles:insert definition="confirm_sms" flush="false">
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="showActionMessages" value="true"/>
                <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                <tiles:put name="confirmableObject" value="${formName}"/>
                <tiles:put name="title" value="Подтверждение заявки"/>
                <tiles:put name="buttonType" value="singleRow"/>
                <tiles:put name="showCancelButton" value="false"/>
                <tiles:put name="confirmCommandKey" value="button.dispatch"/>
                <tiles:put name="useAjax" value="true"/>
                <tiles:put name="ajaxUrl" value="/private/async/basket/subscription/claim"/>
                <tiles:put name="formId" value="oneTimePasswordWindow"/>
                <c:if test="${isCreateAutoPay}">
                    <tiles:put name="data" value="${form.html}"/>
                </c:if>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            &nbsp; <%-- При наличии одного лишь скрипта, IE не подключает скрипт. Поэтому надо добавить что-нибудь. --%>
            <script type="text/javascript">

                <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
                    <c:set var="errorEscape" value="${phiz:escapeForJS(error, true)}"/>
                    if (window.addError != undefined)
                    {
                        var error = "${errorEscape}";
                        addError(error, null, true);
                    }
                </phiz:messages>

                <phiz:messages id="messages" bundle="commonBundle" field="field" message="message">
                <c:set var="messageEscape" value="${phiz:escapeForJS(messages, true)}"/>
                    if (window.addMessage != undefined)
                    {
                        var message = "${messageEscape}";
                        addMessage(message);
                    }
                </phiz:messages>

                if (window.win != undefined)
                    win.close(confirmOperation.windowId);
            </script>
        </c:otherwise>
    </c:choose>
    <script type="text/javascript">
        removeStateAttr();
    </script>
</html:form>