<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/async/sms/csa/locale/save">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
    <h2>
    <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
    <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
    <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
        �������������� ������� SMS
    </h2>
    </div>

    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="localeErrors"/>
        <tiles:put name="isDisplayed" value="${false}"/>
        <tiles:put name="data" value=""/>
    </tiles:insert>

    <tiles:insert definition="paymentForm" flush="false">
        <tiles:put name="description">
            �� ������ �������� ����� ��������������� ������ ��і���������
        </tiles:put>
        <tiles:put name="data">
            <html:hidden property="id"/>
            <html:hidden property="localeId"/>

            <fieldset>
                <legend>���</legend>
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="vertical-align: top;" class="LabelAllTitle"><nobr>����� SMS</nobr></td>
                        <td>
                            <label>
                                <html:textarea styleId="csaLocaleText" property="fields(csaLocaleText)" rows="10" cols="100"  style="width:100%" onkeyup="countSymbols(this, 'csaLocaleCount')"/>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>��������: <span id="csaLocaleCount">0</span> �� 499. ��� ������� ����������.</td>
                    </tr>
                </table>
            </fieldset>


        </tiles:put>
        <tiles:put name="buttons">
            <script type="text/javascript">
                <c:set var="editLanguageURL" value="${phiz:calculateActionURL(pageContext, '/private/async/sms/csa/locale/save')}"/>
                function saveLocales()
                {
                    var localeId = $('#languageSelectForEditchooseLocale [name=locale_Id]:first').val();
                    var params = "operation=button.save&id=${form.id}&localeId="+localeId;
                    params += "&fields(csaLocaleText)="+$('#csaLocaleText').val();
                    params += "&fields(id)="+$('[name="id"]').val();
                    params += "&field(csaTemplate)=true";

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
                var eribText = document.getElementById('csaLocaleText');
                countSymbols(eribText, 'csaLocaleCount');
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