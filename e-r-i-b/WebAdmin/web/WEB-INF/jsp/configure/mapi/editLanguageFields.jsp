<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<html:form action="/private/async/mobileApi/configure">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            Общие настройки мобильного API
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description" value="Используйте форму для изменения общих настроек mAPI."/>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>
            <c:set var="locale" value="${form.localeId}"/>

            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="mobile.api.invalid.version.text" bundle="configureBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="invalidVersion_lang" property="field(mobile.api.invalid.version.text.${locale})" cols="55" rows="4"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="mobile.api.jailbreak.enabled.text" bundle="configureBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="jailbreakEnabled_lang" property="field(mobile.api.jailbreak.enabled.text.${locale})" cols="55" rows="4"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="mobile.api.jailbreak.disabled.text" bundle="configureBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="jailbreakDisabled_lang" property="field(mobile.api.jailbreak.disabled.text.${locale})" cols="55" rows="4"/>
                    </tiles:put>
                </tiles:insert>

            </fieldset>
        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/mobileApi/configure')}"/>
                function saveLocales()
                {
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&field(mobile.api.invalid.version.text.${locale})="+$('#invalidVersion_lang').val();
                    params +="&field(mobile.api.jailbreak.enabled.text.${locale})="+$('#jailbreakEnabled_lang').val();
                    params +="&field(mobile.api.jailbreak.disabled.text.${locale})="+$('#jailbreakDisabled_lang').val();
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
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>