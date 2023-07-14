<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/async/dictionaries/paymentService/resources/edit">
    &nbsp;
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="localeImageId" value="${form.locale.imageId}"/>
    <div class="confirmWindowTitle">
        <h2>
            <c:set var="localeImageData" value="${phiz:getImageById(localeImageId)}"/>
            <c:set var="localeImage" value="${phiz:getAddressImage(localeImageData, pageContext)}"/>
            <img class="languageIcon" src="${localeImage}" border="0"/>&nbsp;
            <bean:message bundle="paymentServiceBundle" key="editform.name"/>
        </h2>

        <tiles:insert definition="errorBlock" flush="false">
            <tiles:put name="regionSelector" value="localeErrors"/>
            <tiles:put name="isDisplayed" value="${false}"/>
            <tiles:put name="data" value=""/>
        </tiles:insert>

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="editPaymentServiceResources"/>
            <tiles:put name="description" type="string"><bean:message bundle="paymentServiceBundle" key="editform.title"/></tiles:put>
            <tiles:put name="data">
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="paymentServiceBundle" key="label.name"/>
                    </tiles:put>
                    <tiles:put name="isNecessary" value="true"/>
                    <tiles:put name="data">
                        <html:text property="field(localedName)" size="50"/>
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message key="label.description" bundle="paymentServiceBundle"/>
                    </tiles:put>
                    <tiles:put name="data">
                        <html:textarea property="field(localedDescription)" cols="40" rows="5" styleClass="contactInput"/>
                    </tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="buttons">
                <script type="text/javascript">

                    function getData()
                    {
                        var data = "&stringId=${form.stringId}";
                        data += "&localeId=${form.locale.id}";
                        data += "&fields(localedName)=" + getField("localedName").value;
                        data += "&fields(localedDescription)=" + getField("localedDescription").value;
                        return data;
                    }

                    function saveData()
                    {
                        var params = "operation=button.save";
                        params += getData();
                        var editURL = getEditLocaledResourcesURL();
                        ajaxQuery(params, editURL,
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
                    <tiles:put name="bundle"         value="localeBundle"/>
                    <tiles:put name="onclick" value="win.close('editLanguageWindow')"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="localeBundle"/>
                    <tiles:put name="onclick" value="saveData();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </div>
</html:form>