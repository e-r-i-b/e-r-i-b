<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/news/locale/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
                <bean:message key="news.edit.label" bundle="newsBundle"/>
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            <bean:message key="news.edit.description" bundle="newsBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="news.title" bundle="newsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text styleId="title_lang" property="fields(title)" size="60" maxlength="100"/>
                    </tiles:put>
                </tiles:insert>
            </fieldset>

            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <tiles:insert definition="bbCodeButtons" flush="false">
                            <tiles:put name="textAreaId" value="shortText_lang"/>
                            <tiles:put name="showBirthday" value="false"/>
                            <tiles:put name="showPhone" value="false"/>
                            <tiles:put name="showFio" value="false"/>
                            <tiles:put name="showBold" value="true"/>
                            <tiles:put name="showItalics" value="true"/>
                            <tiles:put name="showUnderline" value="true"/>
                            <tiles:put name="showColor" value="true"/>
                            <tiles:put name="showHyperlink" value="true"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="news.short.text" bundle="newsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="shortText_lang" property="fields(shortText)" cols="55" rows="4"/>
                    </tiles:put>
                </tiles:insert>
            </fieldset>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="news.text" bundle="newsBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="text_lang" property="fields(text)" cols="55" rows="4"/>
                    </tiles:put>
                </tiles:insert>
            </fieldset>
        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/news/locale/save')}"/>
                function saveLocales()
                {
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&fields(title)="+$('#title_lang').val();
                    params +="&fields(text)="+$('#text_lang').val();
                    params +="&fields(shortText)="+$('#shortText_lang').val();


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
                <tiles:put name="bundle" value="newsBundle"/>
                <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditLanguageOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="newsBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>