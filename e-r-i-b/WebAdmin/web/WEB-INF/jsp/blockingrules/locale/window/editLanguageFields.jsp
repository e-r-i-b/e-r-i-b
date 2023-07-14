<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/blockingrules/language/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            <bean:message bundle="providerBundle" key="edit.name"/>
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            <bean:message bundle="providerBundle" key="edit.description"/>
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.blocking.reason" bundle="blockingRulesBundle"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <html:textarea styleId="eribMessage" property="field(eribMessage)" cols="55" rows="3"/>
                </tiles:put>
            </tiles:insert>


            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.blocking.reason" bundle="blockingRulesBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:textarea styleId="mapiMessage" property="field(mapiMessage)" cols="55" rows="3"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.blocking.reason" bundle="blockingRulesBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:textarea styleId="atmMessage" property="field(atmMessage)" cols="55" rows="3"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message key="label.blocking.reason" bundle="blockingRulesBundle"/>
                </tiles:put>
                <tiles:put name="data">
                    <html:textarea styleId="ermbMessage" property="field(ermbMessage)" cols="55" rows="3"/>
                </tiles:put>
            </tiles:insert>

        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/blockingrules/language/save')}"/>
                var saved = true;
                function saveLocales()
                {
                    if(!saved)
                        return;

                    saved = false;
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&fields(ermbMessage)="+$('#ermbMessage').val();
                    params +="&fields(atmMessage)="+$('#atmMessage').val();
                    params +="&fields(mapiMessage)="+$('#mapiMessage').val();
                    params +="&fields(eribMessage)="+$('#eribMessage').val();

                    ajaxQuery(params, "${editLanguageURL}",
                            function(data){
                                if(data == undefined || data == null)
                                    return;

                                if(data.errors.length > 1)
                                {
                                    $("#localeErrors .messageContainer").html(data.errors[0]);
                                    $("#localeErrors").show();
                                    saved = true;
                                    return;
                                }
                                $("#localeErrors .messageContainer").html("");
                                $("#localeErrors").hide();
                                win.close('editLanguageWindow');
                                saved = true;
                            }
                            , "json", true);
                }

            </script>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditBlockingRulesResourcesOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>