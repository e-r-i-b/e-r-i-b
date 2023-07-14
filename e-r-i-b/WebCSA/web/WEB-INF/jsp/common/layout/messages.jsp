<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:useAttribute name="bundle"/>
<tiles:useAttribute name="usePopup" ignore="true"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${empty bundle || bundle==''}">
    <c:set var="bundle" value="commonBundle"/>
</c:if>

<c:set var="isPopup" value="${usePopup eq 'true'}"/>

<c:set var="errors" value=""/>
<c:set var="message" value=""/>

<script type="text/javascript">
    <%-- если во всплывающем окне, то скриптовая составляющая не нужна --%>
    <c:if test="${not isPopup}">
        var errorHash = {};
        var messageHash = {};


        function addFieldErrorsMessages(beforeText, errors, divId, addFunction, beforeTextCondition)
        {
            var error = '';
            var fieldError = '';
            var isNeedBeforeText = false;
            errors = changeErrors(errors);

            for (var field in errors)
            {
                if (payInput.fieldError(field, errors[field]))
                {
                    fieldError += '<br/>\n' + payInput.getFieldLabel(field);
                    if (beforeTextCondition == null)
                    {
                        isNeedBeforeText = true;
                    }
                    else
                    {
                        isNeedBeforeText = beforeTextCondition(errors[field]);
                    }
                }
                else
                {
                    if (error.length > 0)
                        error += '<br/>\n';
                    error += errors[field];
                }
            }

            if (fieldError != '' && isNeedBeforeText) addFunction(beforeText + fieldError, divId);

            // если поля не были найдены просто выводим текст ошибки
            if (error != '') addFunction(error, divId);
        }

        //в некоторых формах требуется показывать ошибку в поле с другим названием(не с тем что пришло)
        function changeErrors(errors)
        {
            return errors;
        }

        function getFieldError(errors, divId, deleteMsgIfExist)
        {
            // текст предшествующий перечислению полей в которых произошла ошибка
            var TEXT_BEFORE_FIELD_ERROR = "Некоторые поля формы были заполнены некорректно. Пожалуйста, внесите изменения в поля, выделенные красным цветом:";

            addFieldErrorsMessages(TEXT_BEFORE_FIELD_ERROR, errors, divId, function(error, div)
            {
                addError(error, div, deleteMsgIfExist);
            });
        }

        function getFieldMessage(messages, divId)
        {
            // текст, предшествующий перечислению полей которые были изменены
            var TEXT_BEFORE_FIELD_ERROR = "Внимание, некоторые поля формы были пересчитаны. Пожалуйста, обратите "
                    + "внимание на выделенные красным цветом поля и отредактируйте платеж, если Вас не устраивают "
                    + "новые значения.";

            var beforeTextCondition = function(message)
            {
                return !(message == '' || message == null);
            };

            addFieldErrorsMessages(TEXT_BEFORE_FIELD_ERROR, messages, divId, function(error, div)
            {
                addMessage(error, div);
            }, beforeTextCondition);
        }
    </c:if>

    <csa:messages  id="error" bundle="${bundle}" field="field" message="error">
        <c:choose>
            <c:when test="${not isPopup and field != null}">
                <c:set var="cOutError">
                    <c:out value="${error}"/>
                </c:set>
                errorHash ['<bean:write name="field" filter="false"/>'] = "${csa:replaceNewLine(cOutError,'<br>')}";
            </c:when>
            <c:otherwise>
                <c:set var="errors">${errors}<div class = "itemDiv"><bean:write name="error" filter="false"/> </div></c:set>
            </c:otherwise>
        </c:choose>
    </csa:messages>

    <csa:messages id="messages" bundle="${bundle}" field="field" message="message">
        <c:choose>
            <c:when test="${not isPopup and field != null}">
                <c:set var="cOutMessages">
                    <c:out value="${messages}"/>
                </c:set>
                messageHash ['<bean:write name="field" filter="false"/>'] = "${csa:replaceNewLine(cOutMessages,'<br>')}";
            </c:when>
            <c:otherwise>
                <c:set var="message">${message}<div class = "itemDiv"><bean:write name="messages" filter="false" ignore="true"/> </div></c:set>
            </c:otherwise>
        </c:choose>
    </csa:messages>

    <c:choose>
        <c:when test="${not isPopup}">
            // Необходимо чтобы страница была полностью загружена иначе JS не будет находить поля
            doOnLoad(function ()
            {
                getFieldError(errorHash);
                getFieldMessage(messageHash);
            });
        </c:when>
        <c:otherwise>
            <c:set var="fullMessage" value="${errors}${message}"/>
            <c:set var="fullMessageLength" value="${fn:length(fullMessage)}"/>
            <c:set var="escapedMessage" value="${csa:escapeForJS(fullMessage, true)}"/>
            <c:if test="${fullMessageLength gt 0}">
                $(document).ready(function(){
                    displayErrors('${escapedMessage}');
                });
            </c:if>
        </c:otherwise>
    </c:choose>

</script>
<%-- если во всплывающем окне, то блоки сообщений рисовать не нужно --%>
<c:if test="${not isPopup}">
    <%-- Сообщения --%>
    <c:set var="messagesLength" value="${fn:length(message)}"/>
    <tiles:insert definition="warningBlock" flush="false">
        <tiles:put name="regionSelector" value="warnings"/>
        <tiles:put name="isDisplayed" value="${messagesLength gt 0 ? true : false}"/>
        <tiles:put name="data">
            <bean:write name="message" filter="false"/>
        </tiles:put>
    </tiles:insert>

    <%-- Ошибки --%>
    <c:set var="errorsLength" value="${fn:length(errors)}"/>
    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errors"/>
        <tiles:put name="isDisplayed" value="${errorsLength gt 0 ? true : false}"/>
        <tiles:put name="data">
            <bean:write name="errors" filter="false"/>
        </tiles:put>
    </tiles:insert>
</c:if>
