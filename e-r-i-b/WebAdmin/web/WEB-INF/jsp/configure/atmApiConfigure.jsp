<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/atmApi/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="AtmApiSettings"/>
        <tiles:put name="pageName" type="string">Настройки atmApi</tiles:put>
        <tiles:put name="pageDescription" value="Используйте данную форму для настройки atmApi"/>
        <tiles:put name="data" type="string">
            <table>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="atm.api.show.services"/>
                <tiles:put name="fieldDescription">Отображение групп услуг в каталоге поставщиков</tiles:put>
                <tiles:put name="requiredField" value="true"/>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="fieldType" value="radio"/>
                <tiles:put name="selectItems" value="true@Отображать|false@Не отображать"/>
            </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="resetForm(event)"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

