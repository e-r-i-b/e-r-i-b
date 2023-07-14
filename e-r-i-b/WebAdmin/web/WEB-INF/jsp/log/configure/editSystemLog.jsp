<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/logging/systemLog/edit">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="propertiesForm">
    <tiles:put name="tilesDefinition" type="string" value="loggingEdit"/>
    <tiles:put name="submenu" type="string" value="EditSystemLogLevel"/>
	<tiles:put name="pageName" type="string">
		<bean:message key="edit.system.log.title" bundle="loggingBundle"/>
	</tiles:put>

	<tiles:put name="menuButtons" type="string">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.save"/>
            <tiles:put name="commandHelpKey" value="button.save.help"/>
            <tiles:put name="bundle" value="loggingBundle"/>
            <tiles:put name="postbackNavigation" value="true"/>
        </tiles:insert>
	</tiles:put>
    <tiles:put name="additionalStyle" type="string">width750</tiles:put>
    <tiles:put name="formAlign" type="string">center</tiles:put>
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
            <tr>
                <td>
                    <span><bean:message key="Core" bundle="loggingBundle"/></span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Core"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.extended.level.Core"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
            </tr>
            <tr>
                <td>
                    <span><bean:message key="Gate" bundle="loggingBundle"/></span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Gate"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.extended.level.Gate"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
            </tr>
            <tr>
                <td>
                    <span><bean:message key="Scheduler" bundle="loggingBundle"/></span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Scheduler"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.extended.level.Scheduler"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
            </tr>
            <tr>
                <td>
                    <span><bean:message key="Cache" bundle="loggingBundle"/></span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Cache"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.extended.level.Cache"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
            </tr>
            <tr>
                <td>
                    <span><bean:message key="Web" bundle="loggingBundle"/></span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.level.Web"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.systemLog.extended.level.Web"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="selectItems">0@<bean:message bundle="loggingBundle" key="level0"/>|1@<bean:message bundle="loggingBundle" key="level1"/>|2@<bean:message bundle="loggingBundle" key="level2"/>|3@<bean:message bundle="loggingBundle" key="level3"/>|4@<bean:message bundle="loggingBundle" key="level4"/>|5@<bean:message bundle="loggingBundle" key="level5"/>|6@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
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
                    <span>Выбор места логирования журнала системных действий</span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width200"/>
                        <tiles:put name="selectItems">com.rssl.phizic.logging.system.DatabaseSystemLogWriter@БД|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@Консоль</tiles:put>
                    </tiles:insert>
                    <br>
                    <span class="propertyDesc">Укажите место логирования записей журнала системных действий: в файл, в БД или через MQ.</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span>Выбор места резервного логирования журнала системных действий</span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.backup"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width200"/>
                        <tiles:put name="selectItems">com.rssl.phizic.logging.system.DatabaseSystemLogWriter@БД|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@Консоль</tiles:put>
                    </tiles:insert>
                    <br>
                    <span class="propertyDesc">Укажите место логирования записей журнала системных действий: в файл, в БД или через MQ.</span>
                </td>
            </tr>
		</tiles:put>
    </tiles:insert>
    <c:set var="prefix" value="com.rssl.phizic.logging.systemLog."/>
    <%@ include file="editAutoArchivationSettings.jsp" %>
</tiles:put>
</tiles:insert>
</html:form>
