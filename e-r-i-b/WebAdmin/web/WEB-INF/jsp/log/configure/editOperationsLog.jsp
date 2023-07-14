<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/logging/operationsLog/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="loggingEdit"/>
        <tiles:put name="submenu" type="string" value="EditOperationsLoggingLevel"/>
        <tiles:put name="pageName" type="string">
            <bean:message key="edit.operation.log.title" bundle="loggingBundle"/>
        </tiles:put>

        <tiles:put name="menuButtons" type="string">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="postbackNavigation" value="true"/>
                <tiles:put name="bundle" value="loggingBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

<tiles:put name="data" type="string">
    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="logLovels"/>
		<tiles:put name="text" value="Настройка уровня ведения логов"/>
		<tiles:put name="head">
	        <td>
				Параметр
			</td>
			<td>
				Стандартное логирование
			</td>
            <td>
				Расширенное логирование
			</td>
		</tiles:put>
		<tiles:put name="data">
		<!-- строки списка -->
		<tr>
			<td>
				<span>Степень детализации</span>
			</td>
			<td>
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.logging.operations.level"/>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems">SHORT@<bean:message bundle="loggingBundle" key="leShort"/>|MEDIUM@<bean:message bundle="loggingBundle" key="leMedium"/>|DETAILED@<bean:message bundle="loggingBundle" key="leDetailed"/></tiles:put>
                </tiles:insert>
			</td>
            <td>
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.logging.operations.extended.level"/>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems">SHORT@<bean:message bundle="loggingBundle" key="leShort"/>|MEDIUM@<bean:message bundle="loggingBundle" key="leMedium"/>|DETAILED@<bean:message bundle="loggingBundle" key="leDetailed"/></tiles:put>
                </tiles:insert>
			</td>
		</tr>
        <tr>
			<td>
				<span>Уровень детализации</span>
			</td>
			<td>
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.logging.operations.mode"/>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems">OFF@<bean:message bundle="loggingBundle" key="mode0"/>|ACTIVE@<bean:message bundle="loggingBundle" key="mode1"/>|FULL@<bean:message bundle="loggingBundle" key="mode2"/></tiles:put>
                </tiles:insert>
			</td>
            <td>
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.phizic.logging.operations.extended.mode"/>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems">OFF@<bean:message bundle="loggingBundle" key="mode0"/>|ACTIVE@<bean:message bundle="loggingBundle" key="mode1"/>|FULL@<bean:message bundle="loggingBundle" key="mode2"/></tiles:put>
                </tiles:insert>
			</td>
		</tr>
		</tiles:put>

    </tiles:insert>

    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="text" value="Настройка места логирования"/>
		<tiles:put name="head">
	        <td>Параметр</td>
			<td>Значение</td>
		</tiles:put>
		<tiles:put name="data">
            <!-- строки списка -->
            <tr>
                <td>
                    <span>Выбор места логирования журнала действий пользователей</span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.OperationLogWriter"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width200"/>
                        <tiles:put name="selectItems">com.rssl.phizic.logging.operations.DatabaseOperationLogWriter@БД|com.rssl.phizic.logging.operations.JMSOperationLogWriter@MQ</tiles:put>
                    </tiles:insert>
                    <br>
                    <span class="propertyDesc">Укажите место логирования записей журнала действий пользователей: в БД или через MQ.</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span>Выбор места резервного логирования журнала действий пользователей</span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.OperationLogWriter.backup"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width200"/>
                        <tiles:put name="selectItems">com.rssl.phizic.logging.operations.DatabaseOperationLogWriter@БД|com.rssl.phizic.logging.operations.JMSOperationLogWriter@MQ</tiles:put>
                    </tiles:insert>
                    <br>
                    <span class="propertyDesc">Укажите место логирования записей журнала действий пользователей: в БД или через MQ.</span>
                </td>
            </tr>
		</tiles:put>
    </tiles:insert>
    <c:set var="prefix" value="com.rssl.phizic.logging.operations."/>
    <%@ include file="editAutoArchivationSettings.jsp" %>
</tiles:put>
</tiles:insert>
</html:form>
