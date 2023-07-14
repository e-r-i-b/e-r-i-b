<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
    asyncEditUrl - урл для асинхронного редактирования
    winId - идентификатор модального окна
    formId - идентификатор формы
    type - тип объекта учета (Car, House, Flat, Garage)
--%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isEditable" value="${not empty form.id}"/>
<c:set var="needUngroupSubscriptions" value="${form.needUngroupSubscriptions}"/>
<c:set var="message">
    <c:if test="${needUngroupSubscriptions}">
        В "<bean:write name="form" property="field(name)"/>" есть услуги. При удалении данные услуги будут перемещены в "Другие услуги".
    </c:if>
    <bean:message key="remove.message.${type}" bundle="basketBundle"/>
</c:set>

<c:set var="editUrl">
    <c:if test="${not empty asyncEditUrl}">
        ${phiz:calculateActionURL(pageContext, asyncEditUrl)}
    </c:if>
</c:set>

<c:if test="${isEditable}">
    <html:hidden property="id"/>
</c:if>

<%-- Сообщения --%>
<tiles:insert definition="warningBlock" flush="false">
    <tiles:put name="regionSelector" value="editEntityWarnings"/>
    <tiles:put name="isDisplayed" value="${isEditable && needUngroupSubscriptions}"/>
    <tiles:put name="data" value="Для автопоиска неоплаченных счетов с использованием данного значения реквизита, выберите услугу для настройки автопоиска счетов."/>
</tiles:insert>

<%-- Ошибки --%>
<tiles:insert definition="errorBlock" flush="false">
    <tiles:put name="regionSelector" value="editEntityErrors"/>
    <tiles:put name="isDisplayed" value="false"/>
</tiles:insert>


<script type="text/javascript">
    function showEditProductName() {
        $("#productNameView").hide();
        $("#productNameEdit").show();
        $("#fieldInsName")[0].selectionStart = $("#fieldInsName")[0].selectionEnd = $("#fieldInsName").val().length;
    }

    function clearAllMessages()
    {
        removeAllMessages('editEntityWarnings');
        removeAllErrors('editEntityErrors');
        $("#payment .form-row").each(function(){
            payInput.formRowClearError(this);
        });
    }

    function save()
    {
        var params = $('#${formId}').serialize();
        ajaxQuery(
                params + "&operation=button.save",
                "${editUrl}",
                function(data)
                {
                    clearAllMessages();

                    if (data.success)
                    {
                        win.close(${winId});
                        if (data.refresh)
                            location.reload();
                        return;
                    }

                    if(data.errors != null && data.errors.length > 0)
                    {
                        var errors = data.errors;
                        for(var i = 0; i <errors.length; i++)
                            addError(errors[i],'editEntityErrors');
                    }

                    if(data.errorFields != null && data.errorFields.length > 0)
                    {
                        var errorFields = data.errorFields;
                        for(var j = 0; j <errorFields.length; j++)
                            payInput.fieldError(errorFields[j].name, errorFields[j].value);
                    }

                    if(data.messages != null && data.messages.length > 0)
                    {
                        var messages = data.messages;
                        for(var k = 0; k <messages.length; k++)
                            addMessage(messages[k],'editEntityWarnings');
                    }
                },
                "json"
        );
    }

    function removeEntity()
    {
        if (!confirm("${phiz:escapeForJS(message, true)}"))
        {
            return;
        }
        var params = $('#${formId}').serialize();
        ajaxQuery(
                params + "&operation=button.remove",
                "${editUrl}",
                function(data)
                {
                    win.close(${winId});
                    if (data.refresh)
                        location.reload();
                },
                "json"
        );
    }
</script>
<div class="payments-tabs">

    <div class="productTitleDetailInfo">
        <div id="productNameView" name="productNameView" class="productTitleMargin word-wrap">
            <span class="productTitleDetailInfoText">
                <span><c:out value="${form.fields.name}"/></span>
                <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
            </span>
        </div>
        <div id="productNameEdit" name="productNameEdit" class="productTitleMargin productTitleDetailInfoEditBlock">
            <html:text property="field(name)" size="30" maxlength="56" styleId="fieldInsName" styleClass="productTitleDetailInfoEditTextBox"/>
            <div class="errorDiv clear" style="display:none;"></div>
        </div>
    </div>

    <div class="buttonsArea iebuttons btnAreaBasket">
        <c:if test="${not empty asyncEditUrl && not empty formId}">
            <div class="float">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick">save();</tiles:put>
                </tiles:insert>
            </div>
        </c:if>

        <c:if test="${isEditable}">
            <div class="floatRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="viewType" value="delBtnLight"/>
                    <tiles:put name="onclick">removeEntity();</tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </div>
</div>