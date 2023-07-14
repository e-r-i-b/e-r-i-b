<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="form" value="${EditPaymentForm}"/>
<c:set var="url">
    <c:choose>
        <c:when test="${form.type == 'create'}">
            <c:out value="/private/async/moneybox/claim/create"/>
        </c:when>
        <c:when test="${form.type == 'edit'}">
            <c:out value="/private/async/moneybox/edit"/>
        </c:when>
        <c:when test="${form.type == 'editDraftClaim'}">
            <c:out value="/private/async/moneybox/claim/edit"/>
        </c:when>
    </c:choose>
</c:set>

<html:form styleId="editMoneyBoxFormId" action="${url}" onsubmit="return setEmptyAction();" show="true">

    <c:set var="mode" value="${phiz:getUserVisitingMode()}"/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

    <c:set var="paymentFormName" value="${form.metadata.name}" scope="request"/>
    <c:set var="metadataPath" value="${form.metadataPath}"/>
    <c:set var="metadata" value="${form.metadata}"/>
    <c:set var="actionUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/moneybox/claim/create.do')}"/>
    <c:choose>
        <c:when test="${form.type == 'create'}">
            <c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/moneybox/claim/confirm.do')}"/>
        </c:when>
        <c:when test="${form.type == 'edit'}">
            <c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/moneybox/edit.do')}"/>
        </c:when>
        <c:when test="${form.type == 'editDraftClaim'}">
            <c:set var="confirmUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/moneybox/claim/edit.do')}"/>
        </c:when>
    </c:choose>


    <%-- Сообщения --%>
    <tiles:insert definition="warningBlock" flush="false">
        <tiles:put name="regionSelector" value="warningsMoneyBox"/>
        <tiles:put name="isDisplayed" value="false"/>
    </tiles:insert>

    <%-- Ошибки --%>
    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errorsMoneyBox"/>
        <tiles:put name="isDisplayed" value="false"/>
    </tiles:insert>

    <div id="moneyBoxPayment">
        <tiles:insert page="/WEB-INF/jsp-sbrf/payments/create-money-box-data.jsp"/>
    </div>

    <script type="text/javascript">

        <c:forEach var="entry" items="${phiz:getCurrencySignMap()}">
        currencySignMap.map['${entry.key}'] = '${entry.value}';
        </c:forEach>

        function save()
        {
            var params = serializeDiv('editMoneyBoxFormId') + '&' + collectRSADataFromFields();
            executeOperation('save', params, '${actionUrl}', editCallBack);
        }

        function confirm()
        {
            var params = serializeDiv('moneyBoxPayment') + '&' + collectRSADataFromFields();
            executeOperation('dispatch', params, '${confirmUrl}', confirmCallBack);
        }

        function saveDraftClaim()
        {
            var params = serializeDiv('moneyBoxPayment') + '&' + collectRSADataFromFields();
            executeOperation('save', params, '${confirmUrl}', confirmCallBack);
        }

        function clearAllMessages()
        {
            removeAllMessages('warningsMoneyBox');
            removeAllErrors('errorsMoneyBox');
            $("#moneyBoxPayment .form-row").each(function ()
            {
                payInput.formRowClearError(this);
            });
        }

        function collectRSADataFromFields()
        {
            var devicePrintName = 'deviceprint';
            var htmlInjectionName = 'htmlinjection';
            var manVsMachineDetectionName = 'manvsmachinedetection';
            var domElementsName = 'domElements';
            var jsEventsName = 'jsEvents';
            var deviceTokenCookieName = 'deviceTokenCookie';

            var devicePrint = encodeURIComponent(getElementValue(devicePrintName));
            var htmlInjection = encodeURIComponent(getElementValue(htmlInjectionName));
            var manVsMachineDetection = encodeURIComponent(getElementValue(manVsMachineDetectionName));
            var domElements = encodeURIComponent(getElementValue(domElementsName));
            var jsEvents = encodeURIComponent(getElementValue(jsEventsName));
            var deviceTokenCookie = encodeURIComponent(getElementValue(deviceTokenCookieName));

            return devicePrintName + '=' + devicePrint
                    + '&' + htmlInjectionName + '=' + htmlInjection
                    + '&' + manVsMachineDetectionName + '=' + manVsMachineDetection
                    + '&' + domElementsName + '=' + domElements
                    + '&' + jsEventsName + '=' + jsEvents
                    + '&' + deviceTokenCookieName + '=' + deviceTokenCookie;
        }

        function executeOperation(operationName, params, actionURL, callbackFunction)
        {
            ajaxQuery(
                    params + "&operation=button." + operationName,
                    actionURL,
                    function (data)
                    {
                        callbackFunction(data);
                    },
                    "json"
            );
        }

        function editCallBack(data)
        {
            clearAllMessages();
            if (data.success)
            {
                getConfirm(data.id);
            }
            processErrors(data);
        }

        function getConfirm(id)
        {
            ajaxQuery(
                    "id=" + id,
                    "${confirmUrl}",
                    function (data)
                    {
                        $('#moneyBoxPayment').html("");
                        $('#moneyBoxPayment').html(data);
                        var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                        if (actualToken != undefined)
                            $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);
                    },
                    "html"
            );
        }

        function getEdit(id)
        {
            var el = ensureElement("moneyBoxWinDiv");
            if (el != null)
            {
                el.isApplyMask = false;
            }
            ajaxQuery(
                    "id=" + id + "&operation=button.edit",
                    "${actionUrl}",
                    function (data)
                    {
                        $('#moneyBoxPayment').html("");
                        $('#moneyBoxPayment').html(data);
                        var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                        if (actualToken != undefined)
                            $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);
                    },
                    "html"
            );
        }

        function confirmCallBack(data)
        {
            removeAllMessages();
            removeAllErrors();
            clearAllMessages();
            if (data.success)
            {
                if (data.messages.length > 0)
                {
                    win.close('moneyBoxWinDiv');
                    addMessage(data.messages[0]);
                }
                else
                {
                    win.close('moneyBoxWinDiv');
                }
                return;
            }
            processErrors(data);
        }

        function processErrors(data)
        {
            if (data.errors != null && data.errors.length > 0)
            {
                var errors = data.errors;
                for (var i = 0; i < errors.length; i++)
                    addError(errors[i], 'errorsMoneyBox', true);
            }

            if (data.errorFields != null && data.errorFields.length > 0)
            {
                var errorFields = data.errorFields;
                for (var j = 0; j < errorFields.length; j++)
                    payInput.fieldError(errorFields[j].name, errorFields[j].value);
            }

            if (data.messages != null && data.messages.length > 0)
            {
                var messages = data.messages;
                for (var k = 0; k < messages.length; k++)
                    addMessage(messages[k], 'warningsMoneyBox', true);
            }
        }

        /**
        *   Вывод сообщений об ошибках, которые возникли при инициализациии копилки
         */
        function showErrors(){
            <phiz:messages id="errorMessage" field="stub" message="message">
                addMessage('${errorMessage}' ,'warningsMoneyBox', true);
            </phiz:messages>
        }
    </script>
</html:form>
