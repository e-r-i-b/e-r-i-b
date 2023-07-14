<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<tiles:importAttribute/>
<div class="form-reminder-block" onclick="return {id : '${id}', self : ${not empty selfSave && selfSave}}" id="reminder${id}">
    <div class="reminder-icon reminder-elements">
        <c:if test="${not empty selfSave && selfSave}">
            <img src="${globalUrl}/commonSkin/images/bell-animation.gif" style="display: none">
        </c:if>
    </div>
    <div class="reminder-add-button reminder-elements" <c:if test="${enableReminder == 'true'}"> style="display: none" </c:if>>
        <bean:message key="reminder.enable.button" bundle="reminderBundle"/>
    </div>
    <div class="form-reminder-parameters reminder-elements " <c:if test="${enableReminder != 'true'}">style="display: none"</c:if> >
        <input type="hidden" name="field(reminderType)"     value="${reminderType}"/>
        <input type="hidden" name="field(enableReminder)"   value="${enableReminder}"/>
        <input type="hidden" name="field(dayOfMonth)"       value="${dayOfMonth}"/>
        <input type="hidden" name="field(monthOfQuarter)"   value="${monthOfQuarter}"/>
        <input type="hidden" name="field(onceDate)"         value="${onceDate}"/>
        <div class="linkListOfOperation reminder-elements">
            <span class="visible-reminder-type">
                <c:choose>
                    <c:when test="${reminderType == 'ONCE'}">ќднократно,</c:when>
                    <c:when test="${reminderType == 'ONCE_IN_MONTH'}">≈жемес€чно,</c:when>
                    <c:when test="${reminderType == 'ONCE_IN_QUARTER'}">≈жеквартально,</c:when>
                </c:choose>
            </span>
            <div id="reminderEventTypeMenu">
                <tiles:insert definition="listOfOperation" flush="false">
                    <tiles:put name="productOperation" value="true"/>
                    <tiles:put name="isLock" value="false"/>
                    <tiles:put name="isShowOperationButton" value="false"/>
                    <tiles:putList name="items">
                        <tiles:add>
                            <a href="#" class="item-reminder-type" onclick="return 'ONCE'">ќднократно</a>
                        </tiles:add>
                        <tiles:add>
                            <a href="#" class="item-reminder-type" onclick="return 'ONCE_IN_MONTH'">≈жемес€чно</a>
                        </tiles:add>
                        <tiles:add>
                            <a href="#" class="item-reminder-type" onclick="return 'ONCE_IN_QUARTER'">≈жеквартально</a>
                        </tiles:add>
                    </tiles:putList>
                </tiles:insert>
            </div>
        </div>
        <div class="visible-reminder-values reminder-elements">
            <span>
                <c:choose>
                    <c:when test="${reminderType == 'ONCE'}">
                        <c:if test="${onceDate != null && onceDate != ''}">
                            <c:set var="parseOnceDate" value="${phiz:parseCalendar(onceDate)}"/>
                            ${phiz:formatDateToMonthInWords(parseOnceDate)}
                        </c:if>
                    </c:when>
                    <c:when test="${reminderType == 'ONCE_IN_MONTH'}">${dayOfMonth}-го числа</c:when>
                    <c:when test="${reminderType == 'ONCE_IN_QUARTER'}">${dayOfMonth}-го числа, ${monthOfQuarter}-го мес€ца</c:when>
                </c:choose>
            </span>
            <input type="hidden" name="field(chooseDateInvoice)"/>
        </div>
        <div class="near-remind-date">
            <c:choose>
                <c:when test="${reminderType == 'ONCE_IN_MONTH'}">
                    <c:set var="nearDate" value="${phiz:getNearDateByMonth(dayOfMonth)}"/>
                    Ѕлижайший ${phiz:formatDateToMonthInWords(nearDate)}
                </c:when>
                <c:when test="${reminderType == 'ONCE_IN_QUARTER'}">
                    <c:set var="nearDate" value="${phiz:getNearDateByQuarter(monthOfQuarter, dayOfMonth)}"/>
                    Ѕлижайший ${phiz:formatDateToMonthInWords(nearDate)}
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="reminder-remove-button reminder-elements" <c:if test="${enableReminder != 'true'}">style="display: none"</c:if> >
        Ќе напоминать
    </div>
</div>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirmDisableReminder${id}"/>
    <tiles:put name="styleClass" value="confirmTemplateNameWin"/>
    <tiles:put name="data">
        <div class="confirmWindowTitle">
            <h2><bean:message key="reminder.message.remove" bundle="reminderBundle"/></h2>
        </div>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('confirmDisableReminder${id}');"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="reminder.disable.confirm.button"/>
                <tiles:put name="commandHelpKey"    value="reminder.disable.confirm.button"/>
                <tiles:put name="bundle"            value="reminderBundle"/>
                <tiles:put name="viewType"          value="buttonFillRed"/>
                <tiles:put name="onclick"           value="if(window.processReminderCommand) processReminderCommand(this, 'disable'); win.close('confirmDisableReminder${id}');"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>