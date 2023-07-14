<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/sms/erib/locale/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            Редактирование шаблона SMS
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            На данной странице можно отредактировать тексты СМС–сообщения
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>

            <%-- ЕРИБ --%>

            <fieldset>
                <legend>ЕРИБ</legend>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        Текст SMS
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="eribLocaledText" property="fields(eribLocaledText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'eribLocaledCount')"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        Символов: <span id="eribLocaledCount">0</span> из 499. Без участия переменных.
                    </tiles:put>
                </tiles:insert>
            </fieldset>

            <c:if test="${not(form.fields['key'] == 'default')}">
                <%-- МОБИЛЬНОЕ ПРИЛОЖЕНИЕ --%>
                <fieldset>
                    <legend>Мобильное приложение</legend>
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:textarea styleId="customMApiLocaledText" property="fields(customMApiLocaledText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'MapiLocaledCount')"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            Символов: <span id="MapiLocaledCount">0</span> из 499. Без участия переменных.
                        </tiles:put>
                    </tiles:insert>
                </fieldset>
                <%-- УСТРОЙСТВА САМООБСЛУЖИВАНИЯ --%>

                <fieldset>
                    <legend>Устройство самообслуживания</legend>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:textarea styleId="customAtmLocaledText" property="fields(customAtmLocaledText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'AtmLocaledCount')"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            Символов: <span id="AtmLocaledCount">0</span> из 499. Без участия переменных.
                        </tiles:put>
                    </tiles:insert>
                </fieldset>

                <%-- СОЦИАЛЬНОЕ ПРИЛОЖЕНИЕ --%>

                <fieldset>
                    <legend>Социальное приложение</legend>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:textarea styleId="customSocialLocaledText" property="fields(customSocialLocaledText)" rows="10" style="width:100%" onkeyup="countSymbols(this, 'SocialLocaledCount')"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            Символов: <span id="SocialLocaledCount">0</span> из 499. Без участия переменных.
                        </tiles:put>
                    </tiles:insert>
                </fieldset>
            </c:if>
        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/sms/erib/locale/save')}"/>
                function saveLocales()
                {
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params += "&fields(eribLocaledText)="+$('#eribLocaledText').val();
                    params += "&fields(mapiTemplateType)="+$('[name="fields(mapiTemplateType)"]:checked').val();
                    params += "&fields(customMApiLocaledText)="+$('#customMApiLocaledText').val();
                    params += "&fields(amtTemplateType)="+$('[name="fields(amtTemplateType)"]:checked').val();
                    params += "&fields(customAtmLocaledText)="+$('#customAtmLocaledText').val();
                    params += "&fields(socialTemplateType)="+$('[name="fields(socialTemplateType)"]:checked').val();
                    params += "&fields(customSocialLocaledText)="+$('#customSocialLocaledText').val();
                    params += "&fields(id)="+$('[name="fields(id)"]').val();

                    ajaxQuery(params, "${editLanguageURL}",
                            function(data){
                                if(data == undefined || data == null)
                                    return;

                                if(data.errors.length > 1)
                                {
                                    $("#localeErrors .messageContainer").html(data.errors[0]);
                                    $("#localeErrors").show();
                                    return;
                                }
                                $("#localeErrors .messageContainer").html("");
                                $("#localeErrors").hide();
                                win.close('editLanguageWindow');
                            }
                            , "json", true);
                }

            </script>
            <script type="text/javascript">
                var customMApiLocaledText   = document.getElementById('customMApiLocaledText');
                var customAtmLocaledText    = document.getElementById('customAtmLocaledText');
                var customSocialLocaledText = document.getElementById('customSocialLocaledText');
                var eribLocaledText         = document.getElementById('eribLocaledText');

                countSymbols(eribLocaledText,       'eribLocaledCount');

                <c:if test="${not(form.fields['key'] == 'default')}">
                    countSymbols(customMApiLocaledText, 'MapiLocaledCount');
                    countSymbols(customAtmLocaledText,  'AtmLocaledCount');
                    countSymbols(customSocialLocaledText,  'SocialLocaledCount');

                    if ($('[name="fields(mapiTemplateType)"]:checked').val()!='true')
                        customMApiLocaledText.setAttribute('disabled', 'disabled');
                    else
                        customMApiLocaledText.removeAttribute('disabled');

                    if ($('[name="fields(amtTemplateType)"]:checked').val()!='true')
                        customAtmLocaledText.setAttribute('disabled', 'disabled');
                    else
                        customAtmLocaledText.removeAttribute('disabled');

                    if ($('[name="fields(socialTemplateType)"]:checked').val()!='true')
                        customSocialLocaledText.setAttribute('disabled', 'disabled');
                    else
                        customSocialLocaledText.removeAttribute('disabled');
                </c:if>
            </script>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditSmsSettingsResourcesOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>