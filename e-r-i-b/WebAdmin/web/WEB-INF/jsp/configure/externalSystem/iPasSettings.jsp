<!--
    Настройки для iPas.
-->

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<table>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="csa.ipas.service.timeout"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="iPas.csa.ipas.service.timeout"/></tiles:put>
        <tiles:put name="textSize" value="10"/>
        <tiles:put name="fieldHint">Укажите тайм-аут соединения с АС "iPas" в миллисекундах.</tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
</table>

