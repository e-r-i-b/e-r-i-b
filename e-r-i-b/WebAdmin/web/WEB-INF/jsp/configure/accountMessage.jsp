<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/accountMessage/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="AccountMessageConfig"/>
        <tiles:put name="pageName" type="string">Настройки подсказок</tiles:put>
        <tiles:put name="pageDescription" type="string">Используйте форму для изменения настройки сообщений объясняющих максимальную сумму вклада.</tiles:put>
        <tiles:put name="replicateAccessService" type="string">DepositsManagement</tiles:put>

        <tiles:put name="data" type="string">

        <table>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.account.max.balance.message"/>
                <tiles:put name="fieldDescription" value="Максимальная сумма вклада"/>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="textSize" value="100"/>
                <tiles:put name="textMaxLength" value="250"/>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>

            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.account.max.balance.validator.message"/>
                <tiles:put name="fieldDescription" value="Предупреждение при превышении"/>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="textSize" value="100"/>
                <tiles:put name="textMaxLength" value="250"/>
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>
        </table>

        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="AccountMessageConfigureOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="AccountMessageConfigureOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>