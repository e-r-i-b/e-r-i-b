<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file CPFLSettings.jsp -->
<!-- generated code -->
<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
 <table>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.gate.connection.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="CPFL.com.rssl.gate.connection.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес веб-сервиса для взаимодействия с АС "ЦПФЛ".</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phizic.wsgate.listener.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="CPFL.com.rssl.phizic.wsgate.listener.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.gate.connection.timeout"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="CPFL.com.rssl.gate.connection.timeout"/> </tiles:put>
    <tiles:put name="textSize" value="10"/>
    <tiles:put name="fieldHint">Укажите тайм-аут соединения с БС "ЦПФЛ" в миллисекундах.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<!-- end generated code -->
 </table>