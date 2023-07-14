<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/moneybox/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="MoneyboxConfig"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="configureBundle" key="settings.moneybox.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="configureBundle" key="settings.moneybox.description"/></tiles:put>
                <tiles:put name="data">
                    <table>
                        <%--Минимальный размер % перечисления--%>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.moneybox.percent.min"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.moneybox.field.minPercent"/></tiles:put>
                            <tiles:put name="textSize" value="6"/>
                            <tiles:put name="textMaxLength" value="3"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.moneybox.field.minPercent.description"/></tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        </tiles:insert>
                        <%--Максимальный размер % перечисления--%>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.moneybox.percent.max"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.moneybox.field.maxPercent"/></tiles:put>
                            <tiles:put name="textSize" value="6"/>
                            <tiles:put name="textMaxLength" value="3"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.moneybox.field.maxPercent.description"/></tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        </tiles:insert>
                        <%--Значение по умолчанию %, перечисляемого в Копилку от зачислений/расходов--%>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.moneybox.percent.default"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.moneybox.field.defaultPercent"/></tiles:put>
                            <tiles:put name="textSize" value="6"/>
                            <tiles:put name="textMaxLength" value="3"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.moneybox.field.defaultPercent.description"/></tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        </tiles:insert>
                        <%--Виды вкладов, недоступные для копилки--%>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.moneybox.disallowedAccountKinds"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.moneybox.field.disallowedAccountKinds"/></tiles:put>
                            <tiles:put name="textSize" value="50"/>
                            <tiles:put name="textMaxLength" value="45"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.moneybox.field.disallowedAccountKinds.description"/></tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        </tiles:insert>
                        <%--Запрет создания конверсионных копилок--%>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.moneybox.disallowedConversion.enable"/>
                            <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.moneybox.field.disallowedConversion.enable"/></tiles:put>
                            <tiles:put name="fieldType" value="checkbox"/>
                            <tiles:put name="showHint">bottom</tiles:put>
                            <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.moneybox.field.disallowedConversion.enable.description"/></tiles:put>
                            <tiles:put name="tdStyle" value="vertical-align rightAlign"/>
                        </tiles:insert>
                    </table>
                </tiles:put>
            </tiles:insert>

            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>
