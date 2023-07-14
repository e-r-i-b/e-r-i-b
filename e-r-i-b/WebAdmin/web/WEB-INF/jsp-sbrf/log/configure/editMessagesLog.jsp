<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/logging/messagesLog/edit">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="propertiesForm">
    <tiles:put name="tilesDefinition" type="string" value="tablePropertiesLoggingEdit"/>
    <tiles:put name="submenu" type="string" value="EditMesagesLoggingLevel"/>
	<tiles:put name="pageName" type="string">
		<bean:message key="edit.message.log.title" bundle="loggingBundle"/>
	</tiles:put>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <tiles:put name="additionalStyle" type="string">width1000</tiles:put>
    <tiles:put name="formAlign" type="string">center</tiles:put>
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
				Система
			</td>
			<td>
				Уровень детализации
			</td>
            <td>
				Уровень детализации расширенного логирования
			</td>
		</tiles:put>
		<tiles:put name="data">
            <c:set var="eribSysteName"><bean:message bundle="logBundle" key="config.message.log.system.erib"/></c:set>
            <c:set var="iPasSysteName"><bean:message bundle="logBundle" key="config.message.log.system.iPas"/></c:set>
            <c:set var="esberibSysteName"><bean:message bundle="logBundle" key="config.message.log.system.esberib"/></c:set>
            <c:set var="csaSysteName"><bean:message bundle="logBundle" key="config.message.log.system.csa"/></c:set>
            <c:set var="csa2SysteName"><bean:message bundle="logBundle" key="config.message.log.system.csa2"/></c:set>
            <c:set var="mobileSysteName"><bean:message bundle="logBundle" key="config.message.log.system.mobile"/></c:set>
            <c:set var="atmSysteName"><bean:message bundle="logBundle" key="config.message.log.system.atm"/></c:set>
            <c:set var="pfrSysteName"><bean:message bundle="logBundle" key="config.message.log.system.pfr"/></c:set>
            <c:set var="shopSysteName"><bean:message bundle="logBundle" key="config.message.log.system.shop"/></c:set>
            <c:set var="barsSysteName"><bean:message bundle="logBundle" key="config.message.log.system.bars"/></c:set>
            <c:set var="depoSysteName"><bean:message bundle="logBundle" key="config.message.log.system.depo"/></c:set>
            <c:set var="mdmSysteName"><bean:message bundle="logBundle" key="config.message.log.system.mdm"/></c:set>
            <c:set var="jdbcSysteName"><bean:message bundle="logBundle" key="config.message.log.system.jdbc"/></c:set>
            <c:set var="IPSSysteName"><bean:message bundle="logBundle" key="config.message.log.system.IPS"/></c:set>
            <c:set var="WebAPISysteName"><bean:message bundle="logBundle" key="config.message.log.system.WebAPI"/></c:set>
		<!-- строки списка -->
            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="iPas"/>
                <tiles:put name="firstSystemName" value="${csaSysteName}"/>
                <tiles:put name="secondSystemName" value="${iPasSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="esberib"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${esberibSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="csa"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${csaSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="CSA2"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${csa2SysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="mobile"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${mobileSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="atm"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${atmSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="pfr"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${pfrSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="shop"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${shopSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="bars"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${barsSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="depo"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${depoSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="mdm"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${mdmSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="jdbc"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${jdbcSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="IPS"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${IPSSysteName}"/>
            </tiles:insert>

            <tiles:insert page="editMessageLogRow.jsp" flush="false">
                <tiles:put name="module" value="WebAPI"/>
                <tiles:put name="firstSystemName" value="${eribSysteName}"/>
                <tiles:put name="secondSystemName" value="${WebAPISysteName}"/>
            </tiles:insert>
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
                    <span>Выбор места логирования журнала сообщений</span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width200"/>
                        <tiles:put name="selectItems">com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@БД|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@Консоль</tiles:put>
                    </tiles:insert>
                    <br>
                    <span class="propertyDesc">Укажите место логирования записей журнала сообщений: в файл, в БД или через MQ.</span>
                </td>
            </tr>
            <tr>
                <td>
                    <span>Выбор места резервного логирования журнала сообщений</span>
                </td>
                <td>
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.backup"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width200"/>
                        <tiles:put name="selectItems">com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@БД|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@Консоль</tiles:put>
                    </tiles:insert>

                    <br>
                    <span class="propertyDesc">Укажите место логирования записей журнала сообщений: в файл, в БД или через MQ.</span>
                </td>
            </tr>
		</tiles:put>
    </tiles:insert>

    <c:set var="prefix" value="com.rssl.phizic.logging.messagesLog."/>
    <%@ include file="/WEB-INF/jsp/log/configure/editAutoArchivationSettings.jsp" %>
    <fieldset>
        <legend>
            Настройка исключаемых сообщений
        </legend>
        <table class="pmntGlobal">
            <tr>
               <td>
                   <c:set var="excludedMessagesField">Настройка исключаемых сообщений</c:set>
                   <tiles:insert definition="tableProperties" flush="false">
                       <tiles:put name="bundle" value="commonBundle"/>
                       <tiles:put name="fieldValue" value="com.rssl.phizic.logging.messagesLog.excluded.messages"/>
                       <tiles:put name="showCheckbox" value="${form.replication}"/>
                       <tiles:put name="propertyId" value="excludedMessagesProperties"/>
                       <tiles:put name="tableTitle">
                           <div class="propertiesTable float">Редактирование списка исключаемых сообщений</div>
                           <tiles:insert definition="floatMessageShadow" flush="false">
                               <tiles:put name="id" value="excludedMessagesProp"/>
                               <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                               <tiles:put name="data"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px"></tiles:put>
                               <tiles:put name="showHintImg" value="false"/>
                               <tiles:put name="text">
                                   Укажите исключаемые сообщения.
                               </tiles:put>
                               <tiles:put name="dataClass" value="dataHint"/>
                           </tiles:insert>
                       </tiles:put>
                       <tiles:put name="tableHead">
                           <th class="titleTable Width20">
                               <input type="checkbox" onclick="switchSelection(this,'excludedMessagesProperties_Ids');" ${form.replication ? "disabled='disabled'" : ""}>
                           </th>
                           <th class="titleTable Width1050" >${excludedMessagesField}</th>
                       </tiles:put>
                   </tiles:insert>
               </td>
            </tr>
        </table>
    </fieldset>

    <c:set var="ExcludedMessagesFieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(EditMessagesLoggingSettingsForm.fields,
                               'excludedMessagesProperties_Id_|excludedMessagesProperties_message_')}"/>

    <script type="text/javascript">
        <%--настройки таблицы для исключаемых сообщений--%>
        var fieldsExcludedMessagesProperties = {
            excludedMessagesProperties_Id_:
                    {type: 'identifier', td: 1},
            excludedMessagesProperties_message_:
                    {type: 'input', td: 2,
                        validators: [{template: templateObj.REQUIRED,
                                      message: '<bean:message bundle="commonBundle"
                                                 key="com.rssl.common.forms.validators.RequiredFieldValidator.message"
                                                 arg0="${excludedMessagesField}"/>'},
                                     {template: new templateItem ('____', '.{1,200}$'),
                                      message: '<bean:message bundle="commonBundle"
                                                 key="com.rssl.common.forms.validators.CharRangeFieldValidator.message"
                                                 arg0="${excludedMessagesField}" arg1="1" arg2="200"/>'}]
                    }
        };
        var initFieldsExcludedMessagesProperties = ${ExcludedMessagesFieldsFormatedForJSArray};
        var uniqExcludedMessagesProperties = ["excludedMessagesProperties_message_"];
        var excludedMessagesHelper = new TablePropertiesHelper('excludedMessagesProperties',
                                            fieldsExcludedMessagesProperties, initFieldsExcludedMessagesProperties, '${imagePath}', uniqExcludedMessagesProperties);
        tablePropertiesFactory.registerHelper(excludedMessagesHelper);
    </script>
</tiles:put>
</tiles:insert>
</html:form>
