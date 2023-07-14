<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/dictionaries/bank/locale/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            <bean:message bundle="regionsBundle" key="edit.title"/>
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            <bean:message bundle="regionsBundle" key="edit.description"/>
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="stringId"/>
            <html:hidden property="localeId"/>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="dictionariesBundle" key="label.name"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text styleId="name_lang" property="fields(name)" size="60" maxlength="256"/>
                    </tiles:put>
                </tiles:insert>

            </fieldset>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="dictionariesBundle" key="label.place"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text styleId="place_lang" property="fields(place)" size="50" maxlength="50"/>
                    </tiles:put>
                </tiles:insert>

            </fieldset>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="dictionariesBundle" key="label.short.name"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text styleId="shortName_lang" property="fields(shortName)" size="60" maxlength="256"/>
                    </tiles:put>
                </tiles:insert>

            </fieldset>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="dictionariesBundle" key="label.address"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text styleId="address_lang" property="fields(address)" size="60" maxlength="256"/>
                    </tiles:put>
                </tiles:insert>

            </fieldset>
        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/dictionaries/bank/locale/save')}"/>
                function saveLocales()
                {
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&stringId=${form.stringId}&localeId="+localeId;
                    params +="&fields(name)="+$('#name_lang').val();
                    params +="&fields(place)="+$('#place_lang').val();
                    params +="&fields(shortName)="+$('#shortName_lang').val();
                    params +="&fields(address)="+$('#address_lang').val();
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
//                                $("#localeErrors .messageContainer").html("");
                                $("#localeErrors").hide();
                                win.close('editLanguageWindow');
                            }
                            , "json", true);
                }

            </script>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="dictionariesBundle"/>
                <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditBankResourcesOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="dictionariesBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>