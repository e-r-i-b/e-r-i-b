<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file XBankSettings.jsp -->
<!-- generated code -->
<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<script type="text/javascript">
    function setDepartmentInfo(resource)
    {
        setElement("field(com.rssl.gate.office.code.region.number)", resource['region']);
    }
</script>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="adapterBundle" key="XBank.com.rssl.phizic.wsgate.listener.url"/>
        </tiles:put>
        <tiles:put name="description">
            <span class="propertyDesc">Укажите адрес обратного веб-сервиса на стороне ИКФЛ.</span>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.wsgate.listener.url"/>
                <tiles:put name="textSize" value="60"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="adapterBundle" key="XBank.com.rssl.gate.office.code.region.number"/>
        </tiles:put>
        <tiles:put name="description">
            <span class="propertyDesc">Укажите код тербанка, автоматизируемого через данный узел.</span>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.gate.office.code.region.number"/>
                <tiles:put name="textSize" value="4"/>
                <tiles:put name="textMaxLength" value="2"/>
                <tiles:put name="readonly" value="true"/>
            </tiles:insert>
            <c:if test="${!form.replication}">
                <input class="buttWhite smButt" id="officeSelectButton" type="button" value="..." onclick="openAllowedTBDictionary(setDepartmentInfo, true)"/>
            </c:if>
        </tiles:put>
    </tiles:insert>

<!-- end generated code -->