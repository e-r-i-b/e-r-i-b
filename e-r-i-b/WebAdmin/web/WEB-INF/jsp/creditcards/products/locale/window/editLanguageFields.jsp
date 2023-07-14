<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/async/creditcards/products/language/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="creditcardsBundle"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            <bean:message bundle="cardsBundle" key="title.card.product.edit"/>
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            <bean:message bundle="cardsBundle" key="title.card.product.edit.description"/>
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>

            <html:text property="field(name)" size="50" maxlength="255" styleId="nameId"/>
            <div class="form-row">
                <div class="paymentLabel">
                    <bean:message key="label.product.additional.terms" bundle="${bundle}"/>
                </div>
                <div class="paymentValue">
                    <html:textarea property="field(additionalTerms)" cols="38" rows="4" styleId="additionalTermsID"/>
                </div>
                <div class="clear"></div>
            </div>

        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/async/creditcards/products/language/save')}"/>
                var saved = true;
                function saveLocales()
                {
                    if(!saved)
                        return;

                    saved = false;
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&fields(name)="+$('#nameId').val();
                    params +="&fields(additionalTerms)="+$('#additionalTermsID').val();

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
            <tiles:insert definition="clientButton" flush="false" operation="EditCardProductResourceOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>