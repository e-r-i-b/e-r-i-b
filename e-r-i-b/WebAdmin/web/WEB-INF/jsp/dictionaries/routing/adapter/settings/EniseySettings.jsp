<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file EniseySettings.jsp -->
<!-- generated code -->
<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table>
<tr>
    <td colspan="2">
        <fieldset>
            <legend>Информация о шлюзе</legend>
            <table cellpadding="0" cellspacing="0" width="100%">
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.gate.connection.host"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Enisey.com.rssl.gate.connection.host"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint">Укажите имя домена или IP-адрес сервера, на котором установлен XML-шлюз для БС "Енисей".</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.gate.connection.port"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Enisey.com.rssl.gate.connection.port"/></tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="fieldHint">Укажите номер порта, через который настроен шлюз для взаимодействия с БС "Енисей".</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>

            </table>
        </fieldset>
    </td>
</tr>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phizic.wsgate.listener.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Enisey.com.rssl.phizic.wsgate.listener.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign Width50Percent"/>
</tiles:insert>
<!-- end generated code -->
</table>