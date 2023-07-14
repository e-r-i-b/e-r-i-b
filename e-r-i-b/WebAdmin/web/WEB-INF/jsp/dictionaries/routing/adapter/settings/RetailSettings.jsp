<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file RetailV6Settings.jsp -->
<!-- generated code -->
<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<table>
<script type="text/javascript">
    function setDepartmentInfo(resource)
    {
        setElement("field(com.rssl.gate.office.code.region.number)", resource['region']);
    }
</script>

<tr>
    <td colspan="2">
        <fieldset>
            <legend>Настройки соединения</legend>
            <table cellpadding="0" cellspacing="0" width="100%">
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.gate.rs-retail-v64.remote.host"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.host"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.gate.rs-retail-v64.remote.port"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.port"/></tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.gate.rs-retail-v64.version"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.version"/></tiles:put>
                    <tiles:put name="textSize" value="10"/>
                    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
            </table>
        </fieldset>
    </td>
</tr>

<tiles:insert definition="propertyField" flush="false">
    <tiles:put name="fieldName" value="com.rssl.phizic.wsgate.listener.url"/>
    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.com.rssl.phizic.wsgate.listener.url"/></tiles:put>
    <tiles:put name="textSize" value="60"/>
    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
    <tiles:put name="imagePath" value="${imagePath}"/>
    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
</tiles:insert>
<tr>
    <td class="vertical-align rightAlign">
        <bean:message bundle="adapterBundle" key="RetailV6.com.rssl.gate.office.code.region.number"/>
    </td>
    <td>
        <nobr>
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.gate.office.code.region.number"/>
                <tiles:put name="textSize" value="4"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="readonly" value="true"/>
            </tiles:insert>
            <c:if test="${!form.replication}">
                <input class="buttWhite smButt" id="officeSelectButton" type="button" value="..." onclick="openAllowedTBDictionary(setDepartmentInfo, true)"/>
            </c:if>
        </nobr>
        <br>
        <span class="propertyDesc">Введите номер ТБ, который автоматизирует данный узел.</span>
    </td>
</tr>

<tr>
    <td colspan="2">
        <fieldset>
            <legend>Информация о банке</legend>
            <table cellpadding="0" cellspacing="0" width="100%">
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.our.bank.name"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.com.rssl.iccs.our.bank.name"/></tiles:put>
                    <tiles:put name="textSize" value="60"/>
                    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.our.bank.bic"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.com.rssl.iccs.our.bank.bic"/></tiles:put>
                    <tiles:put name="textSize" value="9"/>
                    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.our.bank.coracc"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="adapterBundle" key="RetailV6.com.rssl.iccs.our.bank.coracc"/></tiles:put>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="fieldHint">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                </tiles:insert>
            </table>
        </fieldset>
    </td>
</tr>
<!-- end generated code -->
</table>