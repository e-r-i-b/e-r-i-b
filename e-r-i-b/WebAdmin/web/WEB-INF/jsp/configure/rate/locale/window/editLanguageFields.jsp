<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<html:form action="/private/async/currencyRate/configure/locale/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            <bean:message key="settings.currency.rates" bundle="configureBundle"/>
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description"><bean:message key="settings.currency.rates.description" bundle="configureBundle"/></tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>
            <c:set var="locale" value="${form.localeId}"/>

            <fieldset>
                <div class="privilegedRate">
                    <div class="label">
                        <bean:message key="settings.currency.rates.message" bundle="configureBundle"/>:
                    </div>
                    <div class="messageGroup">
                        <div class="messageText">
                            <html:textarea styleId="privilegedRateMessageId" property="field(privilegedRateMessage)" rows="5" cols="70"/>
                        </div>
                        <div class="buttonsPanel">
                            <tiles:insert definition="bbMessageButtons" flush="false">
                                <tiles:put name="textAreaId" value="privilegedRateMessageId"/>
                            </tiles:insert>
                        </div>
                    </div>
                </div>
            </fieldset>
        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/currencyRate/configure/locale/save')}"/>
                function saveLocales(elem)
                {
                    var selectId = $(elem).parents('[id^=editLocaleWindow]').attr('data-select-id');
                    var localeId = $('#languageSelectForEdit'+selectId+' [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&field(privilegedRateMessage)="+$('#privilegedRateMessageId').val();
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
                                win.close('editLanguageWindow'+selectId);
                            }
                            , "json", true);
                }

                function closeWindow(elem)
                {
                    var selectId = $(elem).parents('[id^=editLocaleWindow]').attr('data-select-id');
                    win.close('editLanguageWindow'+selectId);
                }


            </script>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="closeWindow(this);"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="saveLocales(this);"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>