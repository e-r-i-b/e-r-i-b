<!--
    Настройки для CSA
-->
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<table>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="csa.auth.service.timeout"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA.csa.auth.service.timeout"/></tiles:put>
        <tiles:put name="textSize" value="10"/>
        <tiles:put name="fieldHint">Укажите тайм-аут соединения с АС "CSA" в миллисекундах.</tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="csa.webservice.url"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA.csa.webservice.url"/></tiles:put>
        <tiles:put name="textSize" value="60"/>
        <tiles:put name="fieldHint">Укажите адрес web-сервиса АС "CSA".</tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
</table>
