<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<tiles:importAttribute/>

<table id="createReminderBlock${id}" style="display: none" class="createTemplateBlock">
    <tr class="exceptionCell">
        <td class="dateCell">
            <div id="create-reminder-label-${id}" class="create-template-label">
                <bean:message key="reminder.name.label" bundle="reminderBundle"/>:
            </div>
        </td>
    </tr>
    <tr>
        <td class="operationCell">
            <div id="create-reminder-${id}" class="create-template">
                <div class="create-template-body">
                    <input type="text" size="31" id="reminderName${id}">
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <c:set var="defaultParameters" value="${phiz:getDefaultReminderParameters()}"/>
            <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderParameters.jsp" flush="false">
                <tiles:put name="id"                value="${id}"/>
                <tiles:put name="enableReminder"    value="true"/>
                <tiles:put name="reminderType"      value="${defaultParameters['reminderType']}"/>
                <tiles:put name="dayOfMonth"        value="${defaultParameters['dayOfMonth']}"/>
                <tiles:put name="monthOfQuarter"    value="${defaultParameters['monthOfQuarter']}"/>
                <tiles:put name="onceDate"          value="${defaultParameters['onceDate']}"/>
                <tiles:put name="editable"          value="true"/>
                <tiles:put name="selfSave"          value="false"/>
            </tiles:insert>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div class="buttonsArea buttonsAreaTemplate">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle"         value="commonBundle"/>
                    <tiles:put name="onclick"        value="closeReminderForm(${id});"/>
                    <tiles:put name="viewType"       value="buttonGrey"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"    value="button.quicly.save.template"/>
                    <tiles:put name="commandHelpKey"    value="button.quicly.save.template"/>
                    <tiles:put name="bundle"            value="paymentsBundle"/>
                    <tiles:put name="onclick"           value="saveReminder(${id}, $('#reminderName${id}').val());"/>
                </tiles:insert>
            </div>
        </td>
    </tr>
</table>