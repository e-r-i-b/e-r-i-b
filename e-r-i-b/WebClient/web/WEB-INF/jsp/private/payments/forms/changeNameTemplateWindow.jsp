<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%-- Окно подтверждения удаления шаблона. --%>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="chageNameTemplateWindow"/>
    <tiles:put name="data">
        <div  onkeypress="onEnterKey(event);">
            <h1>Переименовать шаблон</h1>
            <div class="clear"></div>
            <br>
            <h4>
                <div>Для того чтобы переименовать шаблон, введите его новое название и нажмите на кнопку «Сохранить».</div>
                <div class="text-gray">Название шаблона должно быть не более 50 символов.</div>
            </h4>
            <div class="clear"></div>
            <br>

            <tiles:insert definition="fieldWithHint" flush="false">
                <tiles:put name="fieldName" value="Название:"/>
                <tiles:put name="externalId" value="templateName"/>
                <tiles:put name="fieldType" value="text"/>
                <tiles:put name="data">
                    <span class="bold" id="oldNameTemplate"><bean:write name="form" property="title"/></span>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="fieldWithHint" flush="false">
                <tiles:put name="fieldName" value="Новое название:"/>
                <tiles:put name="externalId" value="templateName"/>
                <tiles:put name="fieldType" value="text"/>
                <tiles:put name="data">
                   <input type="text" id="newNameTemplate" size="25" name="field(templateName)">
                </tiles:put>
            </tiles:insert>

            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="paymentsBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="closeChageNameWindow();"/>
                </tiles:insert>
                <tiles:insert definition="createTemplateConfirmButton" flush="false">
                    <tiles:put name="isDefault"         value="true"/>
                    <tiles:put name="commandTextKey"    value="button.changeName.template"/>
                    <tiles:put name="templateNameId"    value="#newNameTemplate"/>
                    <tiles:put name="bundle"            value="paymentsBundle"/>
                    <tiles:put name="onSaveFunction"    value="saveNewTemplateName()"/>
                </tiles:insert>
                <div class="clear"></div>
            </div>
        </div>
    </tiles:put>
</tiles:insert>
<script type="text/javascript">
    function closeChageNameWindow()
    {
        <%-- очищаем ошибку если была --%>
        payInput.fieldClearError("templateName");
        <%-- закрываем окно --%>
        win.close('chageNameTemplateWindow');
    }

    function openChageNameWindow()
    {
        win.open('chageNameTemplateWindow');
    }

    function handleResult(data)
    {
        if(data == null)
            return;
        
        if(data.errors != null &&  data.errors.length > 0)
        {
            payInput.fieldError("templateName", data.errors[0]);
        }
        else
        {
            var strWithoutHTML = escapeHTML(data.newTemplateName);
            <%-- меняем название на форме --%>
            $("#nameTemplate").html(strWithoutHTML);
            <%-- меняем название в зеленом заголовке --%>
            $("#payment div[class='greenTopTitle']").html(strWithoutHTML);
             <%-- меняем текущее название в окне --%>
            var oldName = $("#oldNameTemplate").html();
            $("#oldNameTemplate").html(strWithoutHTML);
            $(".Title span").html(strWithoutHTML);

            $.each($("#templatesLinkList span"), function(){
                if ($(this).html() == oldName)
                {
                    $(this).html(strWithoutHTML);
                }
            });
            closeChageNameWindow();
        }
    }

    function saveNewTemplateName()
    {
        ajaxQuery(
            "operation=changeName&field(newTemplateName)=" + decodeURItoWin( $("#newNameTemplate").val()) + "&id=" + ${form.id},
            '${phiz:calculateActionURL(pageContext, "/private/async/payments/template")}',
            handleResult,
            "json"
        );
    }
</script>