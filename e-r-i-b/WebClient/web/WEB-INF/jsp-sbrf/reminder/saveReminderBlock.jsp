<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<tiles:importAttribute/>

<div class="form-row  active-help">
    <div class="paymentLabel">
        <span class="paymentTextLabel">
            Название напоминания:
        </span>
    </div>
    <div class="paymentValue">
        <input type="text" size="30" maxlength="50" id="reminderName" value="${defaultName}">
    </div>
    <div class="clear"></div>
    <c:set var="defaultParameters" value="${phiz:getDefaultReminderParameters()}"/>
    <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderFormRow.jsp" flush="false">
        <tiles:put name="id"                value="${id}"/>
        <tiles:put name="enableReminder"    value="true"/>
        <tiles:put name="reminderType"      value="${defaultParameters['reminderType']}"/>
        <tiles:put name="dayOfMonth"        value="${defaultParameters['dayOfMonth']}"/>
        <tiles:put name="monthOfQuarter"    value="${defaultParameters['monthOfQuarter']}"/>
        <tiles:put name="onceDate"          value="${defaultParameters['onceDate']}"/>
        <tiles:put name="editable"          value="true"/>
        <tiles:put name="selfSave"          value="false"/>
    </tiles:insert>
    <div class="clear"></div>

    <div style="display: inline-block;">
        <div class="paymentLabel"></div>
        <div class="paymentValue reminder-save-buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="button.quicly.save.template"/>
                <tiles:put name="commandHelpKey"    value="button.quicly.save.template"/>
                <tiles:put name="bundle"            value="paymentsBundle"/>
                <tiles:put name="onclick"           value="saveReminder($('#reminderName').val());"/>
            </tiles:insert>
            &nbsp;
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="button.cancel"/>
                <tiles:put name="commandHelpKey"    value="button.cancel"/>
                <tiles:put name="bundle"            value="paymentsBundle"/>
                <tiles:put name="viewType"          value="simpleLink"/>
                <tiles:put name="onclick"           value="cancelReminderRow();"/>
            </tiles:insert>
            <div class="clear"></div>
            <div class="reminder-save-help">
                <bean:message key="message.reminder.save.block.description" bundle="reminderBundle"
                              arg0="${phiz:calculateActionURL(pageContext, '/private/favourite/list/PaymentsAndTemplates.do')}"/>
            </div>
        </div>
    </div>
</div>