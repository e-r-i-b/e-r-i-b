<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<tiles:importAttribute/>
<%--
    Кнопка создания шаблона с проверкой возможности использования шаблона с таким названием в смс-канале.
    Производится проверка названия шаблона (допустимые символы в шаблоне, совпадение с смс-алиасами поставщиков и смс-командами).
    В случае совпадения запрашивается подтверждание клиента.
    Необходимо задание либо commandKey, либо onSaveFunction

        winId           - идентификатор окна подтверждения.
        commandTextKey  - ключ кнопки
		commandKey      - ключ операции
        onSaveFunction  - функция, выполняемая по сохранию
        templateNameId  - id, из которого берется значение названия шаблона
	    isDefault       - true/false кнопка по умолчанию (ее действие выполняется по нажатию Enter)
        buttonViewType  - внешний вид кнопки
	    bundle          - bundle в котором ищутся текст для commandTextKey и commandHelpKey
	    btnId           - id основной кнопки
--%>
<c:if test="${empty commandTextKey and not empty commandKey}">
    <c:set var="commandTextKey" value="${commandKey}"/>
</c:if>
<c:set var="checkTemplateUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/payments/template')}"/>

<tiles:insert definition="clientButton" flush="false">
    <tiles:put name="commandTextKey"    value="${commandTextKey}"/>
    <tiles:put name="commandHelpKey"    value="${commandTextKey}"/>
    <tiles:put name="bundle"            value="${bundle}"/>
    <tiles:put name="viewType"          value="${buttonViewType}"/>
    <tiles:put name="onclick"           value="checkSmsTemplate_${winId}()"/>
    <tiles:put name="isDefault"         value="${isDefault}"/>
    <tiles:put name="btnId"             value="${btnId}"/>
</tiles:insert>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="${winId}"/>
    <tiles:put name="styleClass" value="confirmTemplateNameWin"/>
    <tiles:put name="data">
        <div class="confirmWindowTitle">
            <h2>
                <bean:message key="button.saveTemplate.confirm.title" bundle="paymentsBundle"/>
            </h2>
        </div>

        <div id="confirmMessage_${winId}" class="confirmWindowMessage">
        </div>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('${winId}');"/>
            </tiles:insert>
            <c:choose>
                <c:when test="${not empty commandKey}">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="${commandKey}"/>
                        <tiles:put name="commandTextKey" value="${commandTextKey}"/>
                        <tiles:put name="commandHelpKey" value="${commandTextKey}"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                        <tiles:put name="validationFunction">win.close('${winId}'); return true;</tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"    value="${commandTextKey}"/>
                        <tiles:put name="commandHelpKey"    value="${commandTextKey}"/>
                        <tiles:put name="bundle"            value="${bundle}"/>
                        <tiles:put name="onclick"           value="${onSaveFunction}; win.close('${winId}');"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>

<script type="text/javascript">
    function checkSmsTemplate_${winId}()
    {
        var templateName = decodeURItoWin($('${templateNameId}').val());
        ajaxQuery(
                "operation=checkName&field(templateName)=" + templateName,
                "${checkTemplateUrl}",
                handleCheck_${winId},
                "json"
        );
    }

    function handleCheck_${winId}(data)
    {
        if (data != null && data.messages != null && data.messages.length > 0)
        {
            showConfirmWindow_${winId}(escapeHTML(data.messages[0]));
        }
        else
        {
            save_${winId}();
        }
    }

    function save_${winId}()
    {
        <c:choose>
            <c:when test="${not empty commandKey}">
                createCommandButton('${commandKey}').click('', false);
            </c:when>
            <c:otherwise>
                ${onSaveFunction};
            </c:otherwise>
        </c:choose>
    }

    function showConfirmWindow_${winId}(info)
    {
        info = "<table class=\"confirmWindowTable\"><tr><td>"+info+"</td></tr></table>";
        $('#confirmMessage_${winId}').html(info);
        win.open('${winId}');
    }
</script>
