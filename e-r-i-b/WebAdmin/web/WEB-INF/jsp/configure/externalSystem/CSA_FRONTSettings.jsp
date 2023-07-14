<!--
    ��������� ��� CSA-FRONT
-->
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<table>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.auth.csa.front.config.use.captcha.restrict"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_FRONT.com.rssl.auth.csa.front.config.use.captcha.restrict"/></tiles:put>
        <tiles:put name="fieldType" value="select"/>
        <tiles:put name="selectItems" value="true@��������|false@���������"/>
        <tiles:put name="fieldHint">������� ������ �� ���� �������� ����� ��� ��� �����.</tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.auth.csa.front.config.choose.region.mode"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_FRONT.com.rssl.auth.csa.front.config.choose.region.mode"/></tiles:put>
        <tiles:put name="showHint" value="none"/>
        <tiles:put name="fieldType" value="radio"/>
        <tiles:put name="selectItems" value="false@��������������� �����|true@������������������� �����"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.auth.csa.front.config.access.registration.external"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_FRONT.com.rssl.auth.csa.front.config.access.registration.external"/></tiles:put>
        <tiles:put name="fieldType" value="select"/>
        <tiles:put name="selectItems" value="true@���������|false@���������"/>
        <tiles:put name="fieldHint">������� ������ �� ���� ��������� ����������� ��������������� ����������� � ������� ������.</tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
    <tiles:insert definition="propertyField" flush="false">
        <tiles:put name="fieldName" value="com.rssl.auth.csa.front.config.access.registration.internal"/>
        <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_FRONT.com.rssl.auth.csa.front.config.access.registration.internal"/></tiles:put>
        <tiles:put name="fieldType" value="select"/>
        <tiles:put name="selectItems" value="true@���������|false@���������"/>
        <tiles:put name="fieldHint">������� ������ �� ���� ��������� ����������� ��������������� ����������� � ���������� ������.</tiles:put>
        <tiles:put name="imagePath" value="${imagePath}"/>
        <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
    </tiles:insert>
</table>