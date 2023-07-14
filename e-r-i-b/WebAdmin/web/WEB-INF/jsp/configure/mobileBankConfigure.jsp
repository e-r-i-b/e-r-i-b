<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/mobileBank/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="MobileBankSettings"/>
        <tiles:put name="pageName" type="string">
            Мобильный банк. Настройки
        </tiles:put>
        <tiles:put name="pageDescription" value="Используйте форму для изменения настроек мобильного банка"/>
        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="mb.registration.repeat.interval"/>
                    <tiles:put name="fieldDescription">Перерыв запуска процедуры подключения услуги</tiles:put>
                    <tiles:put name="inputDesc" value="мин"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="9"/>
                    <tiles:put name="textMaxLength" value="9"/>
                </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>