<!--
    ��������� ��� CSA-BACK
-->
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="csa.back.webservice.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.csa.back.webservice.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">������� ����� ���-������� ��� ������ "���"</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.ipas.csa.back.config.timeout"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.ipas.csa.back.config.timeout"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">������� ����-��� ���������� � �� "iPAS" � �������������.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.integration.ipas.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.integration.ipas.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">������� ����� ���-������� ���  ������  "iPAS".</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tr><td colspan="2">
<fieldset>
    <legend>����� ���������</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.confirmation.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.confirmation.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ����� �������� ������������� �������� � ��������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.confirmation.code.length"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.confirmation.code.length"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ����������� ����� ������, ������������� � SMS ��� ������������� ��������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.confirmation.code.allowed-chars"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint">������� ���������� ������� ��� ������ ������������� ��������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.common.session.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.common.session.timeout"/></tiles:put>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="fieldHint">������� ������������ ����� ����� ������ � �����.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tr><td colspan="2">
<fieldset>
    <legend>��������� ����������� �������������</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <!--� �� ��� ���� ��������� true �������� ���������  -->
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.registration.user.deny-multiple"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.registration.user.deny-multiple"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@���������|true@���������"/>
            <tiles:put name="fieldHint">������� ����������� ��������� ����������� �������� ��� ������ ������� �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.registration.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.registration.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ����� �������� ������������� ����������� ������� � �������������. ���� � ������� ����� ������� ������ �� ���������� ������ �� �����������, �� ��� ����� �������� � ����������� � �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>

    </table>
</fieldset>
</td>
</tr>
<tr>
<td colspan="2">
<fieldset>
    <legend>��������������� ����������� � ����</legend>
    <table cellpadding="0" cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.registration.mode"/>
            <tiles:put name="fieldDescription">����� ��������������� ����������� ������� ����</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="OFF@����� ��������|SOFT@������� ������� �����|HARD@������� �������� �����"/>
            <tiles:put name="fieldHint">�������� ����� ��� ����������� � ����� ��� ��������������� ����������� ��������, ��� ���������� � ����. ���� ����� ��������, �� ��������������� ����������� ����������. ����� ������� ������� �����, ����������� ��������, �� ������ ����� �� ��� ����������. ���� ������� �������� �����, �� ������ �� ����� ���������� �� �����������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <!--� �� ��� ���� ��������� true �������� ���������  -->
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.registration.user.deny-multiple"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.iccs.registration.user.deny-multiple"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@���������|true@���������"/>
            <tiles:put name="fieldHint">������� ����������� ��������� ����������� �������� ��� ������ ������� �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.ban.ipas.login"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.iccs.ban.ipas.login"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@���|true@��"/>
            <tiles:put name="fieldHint">������ ����� � �������/������� iPAS.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_NOT_EXIST.mode"/>
            <tiles:put name="fieldDescription">����� ��������� ������������ ���� � ������ ������ ����������� ��� ���������� ������� � ����� CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">������� ����� ��������� ������������ ���� ������������� ������� � ������ ������ ��������������� ����������� ��� ���������� ������� � ����� CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_NOT_EXIST.mode"/>
            <tiles:put name="fieldDescription">����� ��������� ������������ ���� � ������� ������ ����������� ��� ���������� ������� � ����� CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">������� ����� ��������� ������������ ���� ������������� ������� � ������� ������ ��������������� ����������� ��� ���������� ������� � ����� CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_EXIST.mode"/>
            <tiles:put name="fieldDescription">����� ��������� ������������ ���� � ������ ������ ����������� ��� ������� ������� � ����� CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">������� ����� ��������� ������������ ���� ������������� ������� � ������ ������ ��������������� ����������� ��� ��� ������� ������� � ����� CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_EXIST.mode"/>
            <tiles:put name="fieldDescription">����� ��������� ������������ ���� � ������� ������ ����������� ��� ������� ������� � ����� CSA</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">������� ����� ��������� ������������ ���� ������������� ������� � ������� ������ ��������������� ����������� ��� ������� ������� � ����� CSA.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationForm.message"/>
            <tiles:put name="fieldDescription">����� ��������� �� ����� �����������</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">������� ����� ��������� ������� �� ����� �����������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.web.client.SelfRegistrationWindow.title"/>
            <tiles:put name="fieldDescription">��������� ���������</tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="textMaxLength" value="500"/>
            <tiles:put name="fieldHint">������� ��������� ��������� �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.self.registration.new.design"/>
            <tiles:put name="fieldDescription">������ ��������������� ����������� ������� ����:</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="false@������|true@�����"/>
            <tiles:put name="fieldHint">�������� ������ ��� ��������������� ����������� ��������, ��� ���������� � ����</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.multiple.registration.part.visible"/>
            <tiles:put name="fieldDescription">����������� ������ ����� ������������������</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@���������|false@���������"/>
            <tiles:put name="fieldHint">������� ������ �� ������������ ���� � ������� ����� ������������������</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.iccs.self.registration.show.login.self.registration.screen"/>
            <tiles:put name="fieldDescription">����������� �������� ����� �� ������������ ������ � ������:</tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@���������|false@���������"/>
            <tiles:put name="fieldHint">�������, ���������� �� ���������� �������� ����� �� ������������ ������ � ������, ���� ������ ����� ��� iPAS-������� ��� ��������� �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>

<tr><td colspan="2">
<fieldset>
    <legend>��������� �������������� �������������</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.authentication.blocking.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.authentication.blocking.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ������ ������� � ��������, � ������� �������� ������ ������� � ������� ����� ������������ ����� ���������� ������� ����� ������ ��� ������ ��� ����� � �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.authentication.failed.limit"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.authentication.failed.limit"/></tiles:put>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="fieldHint">������� ���������� ��������� ������� ����� ������� � �������, ����� ������� ������ ������� � ������� ����� �������� ������������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.authentication.ipas.password.store.allowed"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@��������|false@���������"/>
            <tiles:put name="showHint" value="none"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.integration.ipas.allowed"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.integration.ipas.allowed"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="true@���������|false@�� ���������"/>
            <tiles:put name="showHint" value="none"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.password-restoration.timeout"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.password-restoration.timeout"/></tiles:put>
    <tiles:put name="textSize" value="10"/>
    <tiles:put name="fieldHint">������� ����� �������� ������������� �������������� ������ ������� � �������������. ���� �� ��������� ����� ������ �� ���������� ������, �� ��� ����� �������� � �������������� ������.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tr><td colspan="2">
<fieldset>
    <legend>��������� ����������� ���������� ����������</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.timeout"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.timeout"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ����� �������� ������������� �������� ����������� ���������� ���������� � �������������. ���� �� ��������� ����� ������ �� ���������� ������, �� ��� ����� �������� � ����������� ���������� ����������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.max.connectors"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.max.connectors"/></tiles:put>
            <tiles:put name="textSize" value="5"/>
            <tiles:put name="fieldHint">������� ������������ ���������� ������������������ ��������� ��������� ��� ������ �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.request.limit"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.request.limit"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ����������� ���������� ���������� ���������������� �������� �� ����������� ������� � �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.back.config.mobile.registration.request.limit.check.interval"/></tiles:put>
            <tiles:put name="textSize" value="10"/>
            <tiles:put name="fieldHint">������� ����� ������ (� ��������), �� ������� ����� ����������� ����� ���������������� �������� ������� �� �����������. ��� ����� ����� ����������� ������� � �������� �������.</tiles:put>
            <tiles:put name="imagePath" value="${imagePath}"/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="mb.system.id"/>
    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.mb.system.id"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="showHint" value="none"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<%--��� ���������� ��������� ��� CSABack �� ������ ������������.--%>
<c:if test="${!form.replication}">
<tr><td colspan="2">
<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="logLevels"/>
    <tiles:put name="text" value="��������� ������ ����������� �������� ��������� ��������"/>
    <tiles:put name="head">
        <td width="60%">
            ��������
        </td>
        <td width="40%">
            ��������
        </td>
    </tiles:put>
    <tiles:put name="data">
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Core"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Core"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@������������|1@�������|2@����������|3@��������������|4@������|5@��������"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo1"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">�������� ��� ������ "����" ������� ����������� ���������� ��������� ��������.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Gate"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Gate"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@������������|1@�������|2@����������|3@��������������|4@������|5@��������"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo2"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">�������� ��� ������ "����" ������� ����������� ���������� ��������� ��������. </tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Scheduler"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Scheduler"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@������������|1@�������|2@����������|3@��������������|4@������|5@��������"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo3"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">�������� ��� ������ "���������� ����������" ������� ����������� ���������� ��������� ��������.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Cache"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Cache"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@������������|1@�������|2@����������|3@��������������|4@������|5@��������"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo4"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">�������� ��� ������ "������� �����������" ������� ����������� ���������� ��������� ��������.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td nowrap="true">
                <bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.systemLog.level.Web"/>
            </td>
            <td nowrap="true" style="width:200px;">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Web"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems" value="0@������������|1@�������|2@����������|3@��������������|4@������|5@��������"/>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="logInfo5"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">�������� ��� ���-������� ������� ����������� ���������� ��������� ��������.</tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
    </tiles:put>
</tiles:insert>
</td>
</tr>
</c:if>
<tr><td colspan="2">
<fieldset>
    <legend>��������� ������� ��������� ��������</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.system.DatabaseSystemLogWriter@��|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@�������"/>
            <tiles:put name="fieldHint">������� ����� ����������� ������� ������� ��������� ��������: � ����, � �� ��� ����� MQ.</tiles:put>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.dbInstanceName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="������� ������������� ���������� ���������� ��� ����������� ����������� ������� ��������� ��������."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="������� ������������� ������� ��� ������������ ����������� ������� ��������� ��������."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.jMSQueueFactoryName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="������� ������������� ������� ��� ������������ ����������� ������� ��������� ��������."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.backup"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.SystemLogWriter.backup"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.system.DatabaseSystemLogWriter@��|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@�������"/>
            <tiles:put name="fieldHint" value="������� ����� ���������� ����������� ������� ������� ��������� ��������: � ����, � �� ��� ����� MQ."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tr><td colspan="2">
<fieldset>
    <legend>��������� ������� ���������</legend>
    <table cellpadding="0"cellspacing="0" width="100%">
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@��|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@�������"/>
            <tiles:put name="fieldHint" value="������� ����� ����������� ������� ������� ��������: � ����, � �� ��� ����� MQ."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.dbInstanceName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="������� ������������� ���������� ���������� ��� ����������� ����������� ������� ���������."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="������� ������������� ������� ��� ������������ ����������� ������� ���������."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.jMSQueueFactoryName"/></tiles:put>
            <tiles:put name="textSize" value="60"/>
            <tiles:put name="fieldHint" value="������� ������������� ������� ��� ������������ ����������� ������� ���������."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.backup"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.writers.MessageLogWriter.backup"/></tiles:put>
            <tiles:put name="fieldType" value="select"/>
            <tiles:put name="selectItems" value="com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@��|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@�������"/>
            <tiles:put name="fieldHint" value="������� ����� ���������� ����������� ������� ������� ��������� ��������: � ����, � �� ��� ����� MQ."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.level.jdbc"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.messagesLog.level.jdbc"/></tiles:put>
            <tiles:put name="fieldType" value="radio"/>
            <tiles:put name="selectItems" value="on@��������|off@���������"/>
            <tiles:put name="fieldHint" value="�������� �������, ����� �� ���������� ��������� � �������������� ����� ���-Back � JDBC."/>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
        <tiles:insert definition="propertyField" flush="false">
            <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.level.iPas"/>
            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.phizic.logging.messagesLog.level.iPas"/></tiles:put>
            <tiles:put name="fieldType" value="radio"/>
            <tiles:put name="selectItems" value="on@��������|off@���������"/>
            <tiles:put name="fieldHint">�������� �������, ����� �� ���������� ��������� � �������������� ����� ���-Back � �� "iPAS".</tiles:put>
            <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
            <tiles:put name="imagePath" value="${imagePath}"/>
        </tiles:insert>
    </table>
</fieldset>
</td>
</tr>
<tr>
    <td colspan="2">
        <fieldset>
            <legend>��������� ��������� �����</legend>
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.entry.mode"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.entry.mode"/></tiles:put>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems" value="false@��������|true@��������"/>
                    <tiles:put name="fieldHint" value="�������� ����� ����������� ��������� ����� � ����"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.phones.limit"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.phones.limit"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ���������� ������� �� 0 �� 999"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.phones.limit.cooldown"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.phones.limit.cooldown"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ����� � �������"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.sms.count"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.sms.count"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ���������� ���������� ������� �����"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.sms.count.global"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.sms.count.global"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ���������� ������� �������� ������"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.sms.cooldown"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.sms.cooldown"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ����� ���������� ��� ���������� ���������� ������������ �������"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.captcha.delay"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.captcha.delay"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ������������ ����� �������� ����� ��������� ����������� � ������ ip-������ �� ��������� ����� CAPTCHA"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>


                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.captcha.delay.minimal"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.captcha.delay.minimal"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ����� �������� ����� ��������� ����������� � ������ ip-������ �� ���������� ����� CAPTCHA"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.auth.csa.front.config.constant.captcha.control.enabled"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.auth.csa.front.config.constant.captcha.control.enabled"/></tiles:put>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems" value="false@���|true@��"/>
                    <tiles:put name="fieldHint" value="�������� ����� ����������� ��������� ����� CAPTCHA"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.guest.entry.claims.show.period"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="CSA_BACK.com.rssl.guest.entry.claims.show.period"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint" value="������� ������ � ������� �������� �������� �������� � �������� �����"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>
            </table>
        </fieldset>
    </td>
</tr>
<tr><td colspan="2">
</td>
</tr>
</table>