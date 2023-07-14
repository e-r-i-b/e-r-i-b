<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/provider/fields/language/save">
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
                        <bean:message key="label.name" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(name)" name="form" size="40" maxlength="160" styleClass="contactInput" styleId="name_lang"/>
                    </tiles:put>
                </tiles:insert>

                <div id="legalNameRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.legal.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(legalName)" name="form" size="40" maxlength="250" styleClass="contactInput" styleId="legalName_lang"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="aliasRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.alias.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(alias)" name="form" size="40" maxlength="250" styleClass="contactInput" styleId="alias_lang"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.bankName" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(bankName)" name="form" size="40" styleClass="contactInput"  styleId="bankName_lang"/>
                    </tiles:put>
                </tiles:insert>
            </fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.comment" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:textarea property="field(description)" name="form" cols="40" rows="5" styleClass="contactInput" styleId="description_lang"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.provider.help" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea property="field(tip)" name="form" cols="40" rows="5" styleClass="contactInput" styleId="tip_lang"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.commission.message" bundle="providerBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea property="field(commissionMessage)" styleId="commissionMessage_lang" name="form" cols="40" rows="5" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>

                <div id="nameOnBillRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.name.on.bill" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(nameOnBill)" name="form" size="40" styleClass="contactInput" maxlength="250" styleId="nameOnBill_lang"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div id="serviceNameRow" class="showBilling showExternal">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.service.name" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="field(nameService)" name="form" size="40" maxlength="150" styleClass="contactInput" styleId="nameService_lang"/>
                        </tiles:put>
                    </tiles:insert>
                </div>

        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/provider/fields/language/save')}"/>
                var saved = true;
                function saveLocales()
                {
                    if(!saved)
                        return;

                    saved = false;
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&fields(name)="+$('#name_lang').val();
                    params +="&fields(legalName)="+$('#legalName_lang').val();
                    params +="&fields(alias)="+$('#alias_lang').val();
                    params +="&fields(bankName)="+$('#bankName_lang').val();
                    params +="&fields(description)="+$('#description_lang').val();
                    params +="&fields(tip)="+$('#tip_lang').val();
                    params +="&fields(commissionMessage)="+$('#commissionMessage_lang').val();
                    params +="&fields(nameOnBill)="+$('#nameOnBill_lang').val();
                    params +="&fields(nameService)="+$('#nameService_lang').val();

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
            <tiles:insert definition="clientButton" flush="false" operation="EditServiceProvidersLanguageOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>