<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/advertising/block/language/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
                Создание и редактирование баннера
        </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            <bean:message key="label.description" bundle="advertisingBlockBundle"/>
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.banner.title" bundle="advertisingBlockBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:text styleId="title_lang" property="fields(title)" size="60" maxlength="100"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <tiles:insert definition="bbCodeButtons" flush="false">
                            <tiles:put name="textAreaId" value="title_lang"/>
                            <tiles:put name="showBirthday" value="true"/>
                            <tiles:put name="showPhone" value="true"/>
                            <tiles:put name="showFio" value="true"/>
                            <tiles:put name="showBold" value="true"/>
                            <tiles:put name="showItalics" value="true"/>
                            <tiles:put name="showUnderline" value="true"/>
                            <tiles:put name="showColor" value="true"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </fieldset>

            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.text" bundle="advertisingBlockBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea styleId="text_lang" property="fields(text)" cols="55" rows="4"/>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <tiles:insert definition="bbCodeButtons" flush="false">
                            <tiles:put name="textAreaId" value="text_lang"/>
                            <tiles:put name="showBirthday" value="true"/>
                            <tiles:put name="showPhone" value="true"/>
                            <tiles:put name="showFio" value="true"/>
                            <tiles:put name="showBold" value="true"/>
                            <tiles:put name="showItalics" value="true"/>
                            <tiles:put name="showUnderline" value="true"/>
                            <tiles:put name="showColor" value="true"/>
                            <tiles:put name="showHyperlink" value="true"/>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </fieldset>

            <table id="blockButtons" width="100%">
                <c:forEach var="i" begin="0" end="2" step="1">
                    <tr id="${i}">
                        <td style="padding:0;">
                            <fieldset>
                                <tiles:insert definition="simpleFormRow" flush="false">
                                    <tiles:put name="title">
                                        <span id="buttonTitle_${i}"><bean:message key="message.button.order.title" bundle="advertisingBlockBundle"/> ${i+1}</span>
                                    </tiles:put>
                                    <tiles:put name="data">
                                        <c:set var="messageButtonTitle" value="<bean:message key='message.button.title' bundle='advertisingBlockBundle'/>"/>
                                        <html:text property="fields(buttonTitle${i})"
                                                   styleClass="customPlaceholder"
                                                   size="20"
                                                   maxlength="50"
                                                   title="${messageButtonTitle}"
                                                   styleId="buttonTitleName_${i}"/>
                                        <img src="${skinUrl}/images/bbCodeButtons/iconSm_Colored.gif" class="colorBtnImg" onmousedown="hideOrShow('colorButtonTitle_${i}');"/>
                                        <div id="colorButtonTitle_${i}" style="display:none;">
                                            <div class="colorDiv" style="background-color: yellow;" onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'yellow');"></div>
                                            <div class="colorDiv" style="background-color: olive;"  onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'olive');"></div>
                                            <div class="colorDiv" style="background-color: fuchsia;" onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'fuchsia');"></div>
                                            <div class="colorDiv" style="background-color: red;"    onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'red');"></div>
                                            <div class="colorDiv" style="background-color: purple;" onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'purple');"></div>
                                            <div class="colorDiv" style="background-color: maroon;" onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'maroon');"></div>
                                            <div class="colorDiv" style="background-color: navy;"   onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'navy');"></div>
                                            <div class="colorDiv" style="background-color: blue;"   onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'blue');"></div>
                                            <div class="colorDiv" style="background-color: aqua;"   onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'aqua');"></div>
                                            <div class="colorDiv" style="background-color: lime;"   onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'lime');"></div>
                                            <div class="colorDiv" style="background-color: green;"  onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'green');"></div>
                                            <div class="colorDiv" style="background-color: teal;"   onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'teal');"></div>
                                            <div class="colorDiv" style="background-color: silver;" onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'silver');"></div>
                                            <div class="colorDiv" style="background-color: gray;"   onmousedown="changeSelText('c', ['buttonTitleName_${i}'], 'gray');"></div>
                                            <div class="clear"></div>
                                        </div>
                                    </tiles:put>
                                </tiles:insert>
                            </fieldset>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/advertising/block/language/save')}"/>
                function saveLocales()
                {
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params +="&fields(title)="+$('#title_lang').val();
                    params +="&fields(text)="+$('#text_lang').val();
                    <c:forEach var="i" begin="0" end="2" step="1">
                    params +="&fields(buttonTitle${i})="+$('#buttonTitleName_${i}').val();
                    </c:forEach>
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
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditLanguageOperation">
                <tiles:put name="commandTextKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="advertisingBlockBundle"/>
                <tiles:put name="onclick" value="saveLocales();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>