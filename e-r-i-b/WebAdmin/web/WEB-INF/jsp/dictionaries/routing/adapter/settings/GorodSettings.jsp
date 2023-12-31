<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file GorodSettings.jsp -->
<!-- generated code -->
<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phizic.wsgate.listener.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Gorod.com.rssl.phizic.wsgate.listener.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">������� ����� � ��������� ���-������� �� ������� ����.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tr>
    <td colspan="2">
        <fieldset>
            <legend>���������� � �����</legend>
            <table cellpadding="0" cellspacing="0" width="100%">
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="gorod.host"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Gorod.gorod.host"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint">������� ��� ������ ��� IP-����� �������, �� ������� ���������� XML-���� ��� ����� ������.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="gorod.port"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Gorod.gorod.port"/></tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="fieldHint">������� ����� �����, ����� ������� �������� ���� ��� �������������� � ����� ������.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="gorod.ikfl.pan"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Gorod.gorod.ikfl.pan"/></tiles:put>
                    <tiles:put name="textSize" value="16"/>
                    <tiles:put name="fieldHint">������� ��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="gorod.ikfl.pin"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Gorod.gorod.ikfl.pin"/></tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="fieldHint">������� ��� �����, ��� ������� �������� ���� ��� �������������� � ����� ������.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
            </table>
        </fieldset>
    </td>
</tr>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="ccom.rssl.gate.gorod.is.debt.negative"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="Gorod.com.rssl.gate.gorod.is.debt.negative"/></tiles:put>
    <tiles:put name="fieldType" value="select"/>
    <tiles:put name="selectItems" value="true@�������������|false@���������"/>
    <tiles:put name="fieldHint">������� ���������� ������������� ����� �������������.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<!-- end generated code -->
</table>