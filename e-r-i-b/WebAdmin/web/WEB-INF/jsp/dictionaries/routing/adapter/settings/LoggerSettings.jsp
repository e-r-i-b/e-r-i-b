<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean" %>
<!-- file LoggerSettings.jsp -->
<!-- generated code -->
<c:set var="helpString" value="/help.do?id=/dictionaries/routing/adapter/settings/edit${EditAdapterSettingsForm.nodeType}"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext, helpString)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="logLevels"/>
    <tiles:put name="text" value="Уровень детализации журналов"/>
    <tiles:put name="head">
        <td width="40%">Параметр</td>
        <td width="30%">Стандартное логирование</td>
        <td width="30%">Расширенное логирование</td>
    </tiles:put>
    <tiles:put name="data">
        <tiles:insert page="logLevelSettingRow.jsp" flush="false">
            <tiles:put name="module" value="Core"/>
        </tiles:insert>

        <tiles:insert page="logLevelSettingRow.jsp" flush="false">
            <tiles:put name="module" value="Gate"/>
        </tiles:insert>

        <tiles:insert page="logLevelSettingRow.jsp" flush="false">
            <tiles:put name="module" value="Scheduler"/>
        </tiles:insert>

        <tiles:insert page="logLevelSettingRow.jsp" flush="false">
            <tiles:put name="module" value="Cache"/>
        </tiles:insert>
        <%-- журнал сообщений --%>
        <tr>
            <td>
                <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.messagesLog.level" arg0="${EditAdapterSettingsForm.nodeTypeObject.description}"/>
            </td>
            <td nowrap="true">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.level.${EditAdapterSettingsForm.nodeTypeObject.system}"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width150"/>
                        <tiles:put name="selectItems">on@<bean:message bundle="loggingBundle" key="level0"/>|off@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="messagesLogInfo"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><div class="imgHintBlock"></div></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">
                        <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.messagesLog.level.hint" arg0="${EditAdapterSettingsForm.nodeTypeObject.description}"/>
                    </tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
            <td nowrap="true">
                <div class="float">
                    <tiles:insert definition="propertyInput" flush="false">
                        <tiles:put name="fieldName" value="com.rssl.phizic.logging.messagesLog.extended.level.${EditAdapterSettingsForm.nodeTypeObject.system}"/>
                        <tiles:put name="fieldType" value="select"/>
                        <tiles:put name="inputClass" value="Width150"/>
                        <tiles:put name="selectItems">on@<bean:message bundle="loggingBundle" key="level0"/>|off@<bean:message bundle="loggingBundle" key="level6"/></tiles:put>
                    </tiles:insert>
                </div>
                <tiles:insert definition="floatMessageShadow" flush="false">
                    <tiles:put name="id" value="messagesExtendedLogInfo"/>
                    <tiles:put name="hintClass" value="indent-top normal-white-space"/>
                    <tiles:put name="data"><div class="imgHintBlock"></div></tiles:put>
                    <tiles:put name="showHintImg" value="false"/>
                    <tiles:put name="text">
                        <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.messagesLog.extended.level.hint" arg0="${EditAdapterSettingsForm.nodeTypeObject.description}"/>
                    </tiles:put>
                    <tiles:put name="dataClass" value="dataHint"/>
                    <tiles:put name="floatClassSyffix" value="Right"/>
                </tiles:insert>&nbsp;&nbsp;
            </td>
        </tr>
    </tiles:put>
</tiles:insert>

<fieldset>
    <legend>Выбор места хранения журналов</legend>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.writers.SystemLogWriter"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter"/>
                <tiles:put name="inputClass" value="Width200"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="selectItems">com.rssl.phizic.logging.system.DatabaseSystemLogWriter@БД|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@Консоль</tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="description">
            Укажите место логирования записей журнала системных действий: в файл, в БД или через MQ.
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.writers.SystemLogWriter.backup"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.SystemLogWriter.backup"/>
                <tiles:put name="inputClass" value="Width200"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="selectItems">com.rssl.phizic.logging.system.DatabaseSystemLogWriter@БД|com.rssl.phizic.logging.system.JMSSystemLogWriter@MQ|com.rssl.phizic.logging.system.ConsoleSystemLogWriter@Консоль</tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="description">
            Укажите место резервного логирования записей журнала системных действий: в файл, в БД или через MQ.
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.writers.MessageLogWriter"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter"/>
                <tiles:put name="inputClass" value="Width200"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="selectItems">com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@БД|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@Консоль</tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="description">
            Укажите место логирования записей журнала сообщений: в файл, в БД или через MQ.
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            <bean:message bundle="adapterBundle" key="Logger.com.rssl.phizic.logging.writers.MessageLogWriter.backup"/>
        </tiles:put>
        <tiles:put name="data">
            <tiles:insert definition="propertyInput" flush="false">
                <tiles:put name="fieldName" value="com.rssl.phizic.logging.writers.MessageLogWriter.backup"/>
                <tiles:put name="inputClass" value="Width200"/>
                <tiles:put name="fieldType" value="select"/>
                <tiles:put name="selectItems">com.rssl.phizic.logging.messaging.DatabaseMessageLogWriter@БД|com.rssl.phizic.logging.messaging.JMSMessageLogWriter@MQ|com.rssl.phizic.logging.messaging.ConsoleMessageLogWriter@Консоль</tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="description">
            Укажите место резервного логирования записей журнала сообщений: в файл, в БД или через MQ.
        </tiles:put>
    </tiles:insert>
</fieldset>

<fieldset>
    <tiles:insert definition="simpleFormRow" flush="false">
        <tiles:put name="title">
            Настройка исключаемых сообщений
        </tiles:put>
        <tiles:put name="data">
            <c:set var="excludedMessagesField">Настройка исключаемых сообщений</c:set>
            <tiles:insert definition="tableProperties" flush="false">
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="fieldValue" value="com.rssl.phizic.logging.messagesLog.excluded.messages"/>
                <tiles:put name="showCheckbox" value="${form.replication}"/>
                <tiles:put name="propertyId" value="excludedMessagesProperties"/>
                <tiles:put name="tableTitle">
                    <div class="propertiesTable float">Редактирование исключаемых сообщений</div>
                    <tiles:insert definition="floatMessageShadow" flush="false">
                        <tiles:put name="id" value="ExcludedMessagesProp"/>
                        <tiles:put name="hintClass" value="rightAlignHint indent-top"/>
                        <tiles:put name="data">
                            <div class="imgHintBlock"></div>
                        </tiles:put>
                        <tiles:put name="showHintImg" value="false"/>
                        <tiles:put name="text">
                            Укажите исключаемые сообщения.
                        </tiles:put>
                        <tiles:put name="dataClass" value="dataHint"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="tableHead">
                    <th class="titleTable" width="20px">
                        <input type="checkbox" onclick="switchSelection(this,'excludedMessagesProperties_Ids');" ${form.replication ? "disabled='disabled'" : ""}>
                    </th>
                    <th class="titleTable" width="100%">${excludedMessagesField}</th>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</fieldset>

<c:set var="ExcludedMessagesFieldsFormatedForJSArray" value="${phiz:formatTableSettingToArray(EditAdapterSettingsForm.fields,
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
<!-- end generated code -->