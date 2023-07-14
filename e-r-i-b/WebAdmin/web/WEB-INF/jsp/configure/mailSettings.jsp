<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/mailSettings/configure">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="replicateAccessService" value="AdminMailSettingsManagement"/>
        <tiles:put name="submenu" type="string" value="MailSettings"/>
        <tiles:put name="pageName"  value="Параметры почты. Настройки."/>
        <tiles:put name="pageDescription"  value="На данной странице можно ограничить количество символов, вводимое пользователями при создании письма."/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                doOnLoad(function()
                {
                    var element = $("input[name=field(com.rssl.iccs.settings.mail.client_text_length)]");
                    element.setIntegerMoneyRegexp(/^\d+$/);
                    element.setDelimiterChar('');
                    element.setMoneyValue(element.val());
                    element = $("input[name=field(com.rssl.iccs.settings.mail.employee_text_length)]");
                    element.setIntegerMoneyRegexp(/^\d+$/);
                    element.setDelimiterChar('');
                    element.setMoneyValue(element.val());
                });
            </script>
            <table>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.settings.mail.client_text_length"/>
                <tiles:put name="fieldDescription">Количество&nbsp;символов,&nbsp;доступное&nbsp;для&nbsp;ввода&nbsp;клиентам в поле «Сообщение»</tiles:put>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="textSize" value="4"/>
                <tiles:put name="textMaxLength" value="4"/>
                <tiles:put name="requiredField" value="true" />
                <tiles:put name="styleClass" value="moneyField" />
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>
            <tiles:insert definition="propertyField" flush="false">
                <tiles:put name="fieldName" value="com.rssl.iccs.settings.mail.employee_text_length"/>
                <tiles:put name="fieldDescription">Количество символов, доступных для ввода в поле «Текст письма»</tiles:put>
                <tiles:put name="showHint" value="none"/>
                <tiles:put name="textSize" value="4"/>
                <tiles:put name="textMaxLength" value="4"/>
                <tiles:put name="requiredField" value="true" />
                <tiles:put name="styleClass" value="moneyField" />
                <tiles:put name="imagePath" value="${imagePath}"/>
            </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" service="AdminMailSettingsManagement">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" service="AdminMailSettingsManagement">
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>