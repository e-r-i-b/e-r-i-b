<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<tiles:importAttribute/>

<c:if test="${editable}">
    <script type="text/javascript" src="${globalUrl}/scripts/reminder.js"></script>
    <script type="text/javascript" src="${globalUrl}/scripts/jquery.datePicker.js"></script>
</c:if>
<div class="reminder-row">
    <div class="paymentLabel">
        <span class="paymentTextLabel" <c:if test="${enableReminder != 'true'}">style="display: none"</c:if> >
            <bean:message key="reminder.row.label" bundle="reminderBundle"/>:
        </span>
    </div>
    <div class="paymentValue">
        <c:set var="defaultParameters" value="${phiz:getDefaultReminderParameters()}"/>
        <tiles:insert page="/WEB-INF/jsp-sbrf/reminder/reminderParameters.jsp" flush="false">
            <tiles:put name="id"                value="${id}"/>
            <tiles:put name="enableReminder"    value="${enableReminder}"/>
            <tiles:put name="reminderType"      value="${empty reminderType ? defaultParameters['reminderType'] : reminderType}"/>
            <tiles:put name="dayOfMonth"        value="${empty dayOfMonth ? defaultParameters['dayOfMonth'] : dayOfMonth}"/>
            <tiles:put name="monthOfQuarter"    value="${empty monthOfQuarter ? defaultParameters['monthOfQuarter'] : monthOfQuarter}"/>
            <tiles:put name="onceDate"          value="${empty onceDate ? defaultParameters['onceDate'] : onceDate}"/>
            <tiles:put name="selfSave"          value="${selfSave}"/>
        </tiles:insert>
    </div>
    <div class="clear"></div>
</div>
