<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:useAttribute name="bundle"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${empty bundle || bundle==''}">
    <c:set var="bundle" value="commonBundle"/>
</c:if>

<c:set var="errors" value=""/>
<c:set var="needAdditionalErrors" value="false"/>
<c:set var="message" value=""/>
<c:set var="needTitledMessages" value="false"/>
<c:set var="inactiveESMessages" value=""/>
<c:set var="informMessages" value="${phiz:getInformMessages()}"/>
<c:set var="additionalMessages" value=""/>

<script type="text/javascript">

    var errorHash = {};
    var additionalErrorHash = {};
    <phiz:messages  id="error" bundle="${bundle}" field="field" title="title" message="error">
        <c:choose>
            <c:when test="${field != null}">
                <c:set var="errorValue">
                    <c:out value="${phiz:escapeForJS(error, false)}"/>
                </c:set>
                errorHash ['<bean:write name="field" filter="false"/>'] = "${phiz:replaceNewLine(errorValue,'<br>')}";
            </c:when>
            <c:when test="${title != null}">
                additionalErrorHash ['${title}'] = "${phiz:replaceNewLine(error,'<br>')}";
                <c:set var="needAdditionalErrors" value="true"/>
            </c:when>
            <c:otherwise>
                <c:set var="errors">${errors}<div class = "itemDiv">${phiz:processBBCodeAndEscapeHtml(error, false)} </div></c:set>
            </c:otherwise>
        </c:choose>
    </phiz:messages>

    var messageHash = {};
    var titledMessageHash = {};
    <phiz:messages id="messages" bundle="${bundle}" field="field" message="message" title="title">
        <c:choose>
            <c:when test="${field != null}">
                <c:set var="messagesValue">
                    <c:out value="${phiz:escapeForJS(messages, false)}"/>
                </c:set>
                messageHash ['<bean:write name="field" filter="false"/>'] = "${phiz:replaceNewLine(messagesValue, '<br>')}";
            </c:when>
            <c:when test="${title != null}">
                titledMessageHash['${title}'] = "${phiz:processBBCodeAndEscapeHtml(messages, false)}"
                <c:set var="needTitledMessages" value="true"/>
            </c:when>
            <c:otherwise>
                <c:set var="message">${message}<div class = "itemDiv">${phiz:processBBCodeAndEscapeHtml(messages, false)}  </div></c:set>
            </c:otherwise>
        </c:choose>
    </phiz:messages>

    <jsp:useBean id="inactiveExternalSystemMessages" scope="request" class="java.util.ArrayList" />
    <phiz:messages id="inactiveES" bundle="${bundle}" field="field" message="inactiveExternalSystem">
        <c:set var="prepareMessage" value="${phiz:processBBCode(inactiveES)}" scope="request"/>
        <c:if test="${!phiz:contains(inactiveExternalSystemMessages, prepareMessage)}">
            <% inactiveExternalSystemMessages.add(request.getAttribute("prepareMessage")); %>
            <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${prepareMessage} </div></c:set>
        </c:if>
    </phiz:messages>

    <c:forEach var="informMessage" items="${informMessages}">
        <c:if test="${informMessage[1] == 'POPUP_WINDOW'}">
            <c:set var="popupInformMessages">${popupInformMessages}<div class = "itemDiv word-wrap">${phiz:processBBCode(informMessage[0])}</div><br/></c:set>
        </c:if>
    </c:forEach>

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

    function getAdditionalError(additionalErrors)
    {
        for (var title in additionalErrors)
        {
            hideOrShow(ensureElement('titledErrors'), false);
            var msg = additionalErrors[title];

            var infoMessageDiv = findChildByClassName(ensureElement('titledErrors'), "infoMessage");
            infoMessageDiv.innerHTML = infoMessageDiv.innerHTML +
                "<div class='title'><span>" + title + "</span></div>"+
                "<div class='messageContainer'><div class='itemDiv'>" + msg + "</div></div>";
        }
    }
    <%--Добавление сообщений с заголовком и картинкой--%>
    function getTitledMessage(titledMessages)
    {
        for (var title in titledMessages)
        {
            hideOrShow(ensureElement('titledMessages'), false);
            var msg = titledMessages[title];

            var infoMessageDiv = findChildByClassName(ensureElement('titledMessages'), "infoMessage");
            infoMessageDiv.innerHTML = infoMessageDiv.innerHTML +
                "<div class='notice '>"+
                    "<div class='noticeTitle'><span>" + title + "</span></div>"+
                    "<div class='messageContainer'>"+
                        "<div class='itemDiv'>" + msg + "</div>"+
                    "</div>"
                "</div>"
            ;
        }
    }

    // Необходимо чтобы страница была полностью загружена иначе JS не будет находить поля
    $(window).load(function ()
    {
        getFieldError(errorHash);
        getFieldMessage(messageHash);
        getAdditionalError(additionalErrorHash);
        getTitledMessage(titledMessageHash);
        <c:if test="${fn:length(popupInformMessages) gt 0}">
            win.open('popupInfromMessagesId');
            //для ие
            win.positioning($("#popupInfromMessagesIdWin")[0]);
        </c:if>
    });

</script>

<c:set var="temporaryMessage" value="${phiz:getClientMessage()}"/>
<tiles:insert definition="warningBlock" flush="false">
    <tiles:put name="regionSelector" value="warnings"/>
    <tiles:put name="isDisplayed" value="${not empty temporaryMessage}"/>
    <tiles:put name="data">
        <c:out value="${temporaryMessage}"/>
    </tiles:put>
</tiles:insert>

<%-- Информационное сообщение --%>
<c:forEach var="informMessage" items="${informMessages}" varStatus="status">
    <c:if test="${informMessage[1] == 'STATIC_MESSAGE'}">
        <c:set var="color" value="Orange"/>
        <c:if test="${informMessage[2] == '1'}">
            <c:set var="color" value="Red"/>
        </c:if>
        <c:if test="${informMessage[2] == '3'}">
            <c:set var="color" value="Green"/>
        </c:if>
        <tiles:insert definition="informMessageBlock" flush="false">
            <tiles:put name="regionSelector" value="informMessage${status.index}"/>
            <tiles:put name="color" value="infMes${color}"/>
            <tiles:put name="data">
                <div class = "itemDiv importance${informMessage[2]} word-wrap whole-words">${phiz:processBBCode(informMessage[0])}</div>
            </tiles:put>
        </tiles:insert>
    </c:if>
</c:forEach>


<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="popupInfromMessagesId"/>
    <tiles:put name="styleClass" value="pop-upInformMessage"/>
    <tiles:put name="data">
       <span class="title"><b>Обратите внимание</b></span></br></br>
       <div class="messageContainer">
           ${popupInformMessages}
       </div>
       <div class="buttonsArea">
           <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close.window"/>
                <tiles:put name="commandHelpKey" value="button.close.window"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('popupInfromMessagesId');"/>
           </tiles:insert>
           <div class="clear"></div>
       </div>
    </tiles:put>
</tiles:insert>


<%-- Сообщения --%>
<c:if test="${documentNotice != null}">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="${documentNotice.type.color}"/>
        <tiles:put name="data">
            <div class="notice ${documentNotice.type.className}">
                <c:if test="${documentNotice.title != '' and documentNotice.title != null}">
                    <div class="noticeTitle">${documentNotice.title}</div>
                </c:if>
                ${documentNotice.text}
            </div>
        </tiles:put>
    </tiles:insert>
</c:if>

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

<!-- Дополнительные ошибки -->
<div id="titledErrors" style="${needAdditionalErrors ? 'display: block' : 'display: none'}" class="errMessagesBlock">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
            <div class="infoMessage"></div>
        </tiles:put>
    </tiles:insert>
</div>

<%-- Недоступность ВС --%>
<c:set var="inactiveESMessagesLength" value="${fn:length(inactiveESMessages)}"/>
<tiles:insert definition="inactiveESMessagesBlock" flush="false">
    <tiles:put name="regionSelector" value="inactiveMessages"/>
    <tiles:put name="isDisplayed" value="${inactiveESMessagesLength gt 0 ? true : false}"/>
    <tiles:put name="data">
        <bean:write name='inactiveESMessages' filter='false'/>
    </tiles:put>
</tiles:insert>

<%-- Дополнитльное сообщение --%>
<c:set var="additionalMessagesLength" value="${fn:length(additionalMessages)}"/>
<tiles:insert definition="warningBlock" flush="false">
    <tiles:put name="regionSelector" value="additionalMessages"/>
    <tiles:put name="color" value="infMesOrange"/>
    <tiles:put name="isDisplayed" value="${additionalMessagesLength gt 0 ? true : false}"/>
    <tiles:put name="data">
        <bean:write name='additionalMessages' filter='false'/>
    </tiles:put>
</tiles:insert>