<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html:form action="/cards/delivery/edit" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="showCheckbox" value="${form.replication}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="informServiceEdit"/>
        <tiles:put name="submenu" type="string" value="EditEmailDeliverySettings"/>
        <tiles:put name="pageDescription">
            <bean:message bundle="mailDeliverySettingsBundle" key="form.description"/>
        </tiles:put>
        <tiles:put name="pageTitle" type="string">
            <bean:message bundle="mailDeliverySettingsBundle" key="form.title"/>
        </tiles:put>

        <tiles:put name="additionalStyle" value="propertiesForm"/>
        <tiles:put name="data">
            <script type="text/javascript">
                var txt = "";
                var show = false;
                $(document).ready(function() {
                    txt = document.getElementsByName("field(com.rssl.iccs.card.report.delivery.is.available.message.text)")[0].value;
                    show = document.getElementsByName("field(com.rssl.iccs.card.report.delivery.is.available.message.show)")[0].checked;
                })
                function cancelButtonClick()
                {
                    var text = document.getElementsByName("field(com.rssl.iccs.card.report.delivery.is.available.message.text)")[0].value;
                    var check = document.getElementsByName("field(com.rssl.iccs.card.report.delivery.is.available.message.show)")[0].checked;

                    if(text != txt || check != show)
                    {
                        <c:set var="message1">
                            <bean:message bundle="mailDeliverySettingsBundle" key="button.cancel.message"/>
                        </c:set>
                        alert("${message1}");
                    }
                    reload();
                }
            </script>
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="mailDeliverySettingsBundle" key="form.fields.text"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldType" value="text"/>
                        <tiles:put name="fieldName" value="com.rssl.iccs.card.report.delivery.is.available.message.text"/>
                        <tiles:put name="textSize" value="100"/>
                        <tiles:put name="textMaxLength" value="200"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    <bean:message bundle="mailDeliverySettingsBundle" key="form.fields.use"/>
                </tiles:put>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldType" value="checkbox"/>
                        <tiles:put name="fieldName" value="com.rssl.iccs.card.report.delivery.is.available.message.show"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditEmailDeliverySettingsOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.hint"/>
                <tiles:put name="bundle" value="mailDeliverySettingsBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditEmailDeliverySettingsOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.hint"/>
                <tiles:put name="bundle" value="mailDeliverySettingsBundle"/>
                <tiles:put name="onclick" value="cancelButtonClick();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>