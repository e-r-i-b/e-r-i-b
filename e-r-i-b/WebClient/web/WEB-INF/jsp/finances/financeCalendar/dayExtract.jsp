<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form action="/private/async/finances/financeCalendar/dayExtract">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="extractDate" value="${form.extractDate}"/>
    <c:set var="dateType" value="${form.dateType}"/>
    <c:set var="currentDate" value="${phiz:currentDate()}"/>
    <c:set var="extractDateShort"><fmt:formatDate value="${extractDate.time}" pattern="ddMMyyyy"/></c:set>
    <c:set var="currentDateShort"><fmt:formatDate value="${currentDate.time}" pattern="ddMMyyyy"/></c:set>
    <c:set var="isToday" value="${dateType == 'TODAY'}"/>
    <c:set var="paymentsCount" value="${phiz:size(form.dayExtractByAutoSubscriptions) +
                                        phiz:size(form.dayExtractByInvoiceSubscriptions) +
                                        phiz:size(form.dayExtractByReminders)}"/>
    <c:set var="extractId">financeCalendarExtract${extractDateShort}</c:set>

    <c:set var="totalOutcome" value="0"/>
    <c:set var="totalIncome" value="0"/>
    <c:set var="totalPaymentAmounts" value="0"/>

    <div id="${extractId}" class="financeCalendarExtract">
        <div class="financeCalendarExtractTriangle"></div>

        <div class="dataBox">
            <div class="extractClose" onclick="financeCalendar.closeExtract('${extractId}');"></div>
            <div class="extractBox">
                <div class="extractTitle">
                    <bean:message bundle="financesBundle" key="lable.finance.calendar.day.extract.title" arg0="${phiz:formatDateWithStringMonth(extractDate)}"/>
                </div>

                <c:choose>
                    <c:when test="${paymentsCount > 0 || phiz:size(form.dayExtractByOperations) > 0}">
                        <table>
                            <tr>
                                <th class="imageTd"/>
                                <th class="operationNameTd">
                                    <c:choose>
                                        <c:when test="${paymentsCount > 0 && !isToday}">
                                            <bean:message bundle="financesBundle" key="lable.finance.calendar.day.extract.operation.future.description"/>
                                        </c:when>
                                        <c:otherwise>
                                            <bean:message bundle="financesBundle" key="lable.finance.calendar.day.extract.operation.description"/>
                                        </c:otherwise>
                                    </c:choose>
                                </th>
                                <th class="categoryColorTd"></th>
                                <th class="categoryNameTd">
                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.day.extract.operation.category"/>
                                </th>
                                <th class="extractSumTd">
                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.day.extract.operation.amount"/>
                                </th>
                            </tr>
                        </table>
                        <div class="extractBlock">
                            <table class="extractTable">
                                <c:forEach var="operation" items="${form.dayExtractByOperations}">
                                    <tr>
                                        <td class="imageTd"/>
                                        <td class="operationNameTd">
                                            <div class="extractOperationName">
                                                <c:out value="${operation.description}"/>
                                            </div>
                                        </td>
                                        <td class="categoryColorTd">
                                            <div class="extractCategoryColor" style="background: #${operation.categoryColor};"></div>
                                        </td>
                                        <td class="categoryNameTd">
                                            <c:out value="${operation.categoryName}"/>
                                        </td>
                                        <c:choose>
                                            <c:when test="${operation.amount > 0}">
                                                <td class="extractSumTd positiveAmount">
                                                    <span class="extractSum">
                                                        +${phiz:formatDecimalToAmount(operation.amount, 2)}
                                                        <c:set var="totalIncome" value="${totalIncome + operation.amount}"/>
                                                    </span>
                                                    <span class="grayText">
                                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                                    </span>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="extractSumTd">
                                                    <span class="extractSum">
                                                        ${phiz:formatDecimalToAmount(operation.amount, 2)}
                                                        <c:set var="totalOutcome" value="${totalOutcome + operation.amount}"/>
                                                    </span>
                                                    <span class="grayText">
                                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                                    </span>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </c:forEach>

                                <c:if test="${paymentsCount > 0}">
                                    <c:if test="${isToday}">
                                        <tr class="subBlockTitle">
                                            <td colspan="5">
                                                ${phiz:numeralDeclension(paymentsCount, 'Запланирован', '', 'о', 'о')}
                                                ${paymentsCount}
                                                ${phiz:numeralDeclension(paymentsCount, 'платеж', '', 'а', 'ей')}
                                            </td>
                                        </tr>
                                    </c:if>

                                    <c:forEach var="reminder" items="${form.dayExtractByReminders}">
                                        <c:set var="totalPaymentAmounts" value="${totalPaymentAmounts + reminder.amount}"/>

                                        <tr>
                                            <td class="imageTd">
                                                <span class="imageTdReminder"/>
                                            </td>
                                            <td class="operationNameTd">
                                                <span class="reminderDescription pointer" onclick="editTemplate('${reminder.id}','${reminder.formTypeName}','${reminder.state}')">
                                                    <c:out value="${reminder.name}"/>
                                                </span>
                                                <span class="reminderField">
                                                    <br/>
                                                    <c:if test="${not empty reminder.providerName}">
                                                        <c:out value="${reminder.providerName}"/>&nbsp;
                                                    </c:if>
                                                    <c:out value="${reminder.keyName}"/>:<c:out value="${reminder.keyValue}"/>
                                                </span>
                                            </td>
                                            <td class="categoryColorTd">&nbsp;</td>
                                            <td class="categoryNameTd">
                                                <c:set var="reminderInfo" value="${reminder.info}"/>

                                                <bean:message bundle="financesBundle" key="lable.finance.calendar.reminder.category"/><br/>

                                                <c:if test="${not empty reminderInfo}">
                                                    <c:set var="reminderType" value="${reminderInfo.type}"/>
                                                    <c:set var="nextReminderDate" value="${reminder.nextReminderDate}"/>

                                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.reminder.type.${reminderType}.prefix"/>,
                                                    <c:choose>
                                                        <c:when test="${reminderType == 'ONCE'}">
                                                            <c:out value="${phiz:formatDateWithMonthString(reminderInfo.onceDate)}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${reminderInfo.dayOfMonth}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.dayOfMonth"/>

                                                            <c:if test="${reminderType == 'ONCE_IN_QUARTER'}">
                                                                <c:out value="${reminderInfo.monthOfQuarter}"/><bean:message bundle="financesBundle" key="lable.finance.calendar.autoSubscription.periodicity.monthOfQuarter"/>
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <br/>
                                                    <c:if test="${not empty nextReminderDate}">
                                                        <bean:message bundle="financesBundle" key="lable.finance.calendar.reminder.next.description"/>
                                                        <c:out value="${phiz:getDayOfDateWithoutNought(nextReminderDate)}"/>&nbsp;<c:out value="${phiz:monthToString(nextReminderDate)}"/>
                                                    </c:if>
                                                </c:if>
                                            </td>
                                            <td class="extractSumTd">
                                                <c:set var="reminderAmount" value="${reminder.amount}"/>
                                                <c:if test="${not empty reminderAmount}">
                                                    <span class="subscriptionSum">
                                                        -${phiz:formatDecimalToAmount(reminderAmount, 2)}
                                                    </span>
                                                    <span class="grayText">
                                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                                    </span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:forEach var="invoiceSubscription" items="${form.dayExtractByInvoiceSubscriptions}">
                                        <c:set var="totalPaymentAmounts" value="${totalPaymentAmounts + invoiceSubscription.amount}"/>

                                        <tr>
                                            <td class="imageTd"/>
                                            <td class="operationNameTd">
                                                <div class="extractOperationName">
                                                    <span class="subscriptionDescription pointer" onclick="financeCalendar.getInvoiceDetailInfo('${extractId}', '${invoiceSubscription.id}', '${invoiceSubscription.type}');">
                                                        <c:out value="${invoiceSubscription.serviceName}"/>
                                                    </span>
                                                    <br/>
                                                    <c:if test="${not empty invoiceSubscription.accountingEntityName}">
                                                        <span class="subscriptionReceiver">
                                                            <c:out value="${invoiceSubscription.accountingEntityName}"/>
                                                        </span>
                                                        <br/>
                                                    </c:if>
                                                    <span class="subscriptionReceiver">
                                                        <c:out value="${invoiceSubscription.receiverName}"/>
                                                    </span>
                                                </div>
                                            </td>
                                            <td class="categoryColorTd">&nbsp;</td>
                                            <td class="categoryNameTd">
                                                <bean:message bundle="financesBundle" key="lable.finance.calendar.invoice.category"/>
                                            </td>
                                            <td class="extractSumTd">
                                                <span class="subscriptionSum">
                                                    -${phiz:formatDecimalToAmount(invoiceSubscription.amount, 2)}
                                                </span>
                                                <span class="grayText">
                                                    <bean:message bundle="financesBundle" key="lable.rub"/>
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    <c:if test="${dateType == 'FUTURE' && phiz:size(form.dayExtractByAutoSubscriptions) > 0}">
                                        <tr class="subBlockTitle">
                                            <td colspan="4">
                                                <bean:message bundle="financesBundle" key="lable.finance.calendar.autopayments.title"/>
                                            </td>
                                        </tr>
                                    </c:if>

                                    <c:forEach var="autoSubscription" items="${form.dayExtractByAutoSubscriptions}">
                                        <c:set var="totalPaymentAmounts" value="${totalPaymentAmounts + autoSubscription.amount}"/>

                                        <tr>
                                            <td class="imageTd"/>
                                            <td class="operationNameTd">
                                                <div class="extractOperationName">
                                                    <span class="subscriptionDescription">
                                                        <c:set var="autoSubsctiptionUrl" value="${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/info?id=')}"/>
                                                        <a href="${autoSubsctiptionUrl}${autoSubscription.number}">
                                                            <c:out value="${autoSubscription.description}"/>
                                                        </a>
                                                    </span>
                                                    <br/>
                                                    <span class="subscriptionReceiver">
                                                        <c:out value="${autoSubscription.receiverName}"/>
                                                    </span>
                                                </div>
                                            </td>
                                            <td class="categoryColorTd">
                                                &nbsp;
                                            </td>
                                            <td class="categoryNameTd">
                                                <%@ include file="autoSubscriptionPeriodicity.jsp"%>
                                            </td>
                                            <td class="extractSumTd">
                                                <c:if test="${autoSubscription.sumType != 'BY_BILLING'}">
                                                    <span class="subscriptionSum">
                                                        -${phiz:formatDecimalToAmount(autoSubscription.amount, 2)}
                                                    </span>
                                                    <span class="grayText">
                                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                                    </span>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="label.finance.calendar.day.extract.operation.empty" bundle="financesBundle"/>
                    </c:otherwise>
                </c:choose>

                <c:set var="remindUrl" value="${phiz:calculateActionURL(pageContext,'/private/finances/financeCalendar/listTemplates.do')}"/>
                <c:set var="templateUrl" value="${phiz:calculateActionURL(pageContext,'/private/templates/default-action.do')}"/>

                <script type="text/javascript">
                    function editTemplate(id, formTypeName, stateCode)
                    {
                        window.location = '${templateUrl}?id=' + id + '&fromFinanceCalendar=true&extractId=' + getExtractIdToTemplate(getExtractId()) + '&objectFormName='+ formTypeName + '&stateCode=' + stateCode;
                    }

                    function remind()
                    {
                        var extractId = getExtractId();

                        var extract = $('#financeCalendarExtract' + extractId);
                        extract.hide();

                        var win = window.open('${remindUrl}?extractId=' + getExtractIdToTemplate(extractId), 'Templates', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
                        win.resizeTo(width=850,height=600);
                        win.moveTo(screen.width / 2 - 410, screen.height / 2 - 225);
                    }

                    function getExtractIdToTemplate(extractId)
                    {
                        var extractDay = extractId.substr(0,2);
                        var extractMonth = extractId.substr(2,2);
                        var extractYear = extractId.substr(4,4);
                        return extractDay + '.' + extractMonth + '.' + extractYear;
                    }

                    function getExtractId()
                    {
                        var clickedBox = $('.clicked')[0].id;
                        return clickedBox.substr('calendarBox_'.length,8);
                    }
                </script>

                <div class="totalAmounts">
                    <table>
                        <c:if test="${dateType != 'FUTURE'}">
                            <tr>
                                <td class="totalTitle">
                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.totalOutcome"/>:
                                </td>
                                <td class="totalAmount">
                                    ${phiz:formatDecimalToAmount(totalOutcome, 2)}
                                </td>
                                <td class="currency">
                                    <span class="grayText bold">
                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td class="totalTitle">
                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.totalIncome"/>:
                                </td>
                                <td class="totalAmount">
                                    <span class="positiveAmount">+${phiz:formatDecimalToAmount(totalIncome, 2)}</span>
                                </td>
                                <td class="currency">
                                    <span class="grayText bold">
                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                    </span>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${isToday}">
                            <tr>
                                <td class="totalTitle currentBalanceTitle">
                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.currentBalance"/>:
                                </td>
                                <td id="currentBalanceAmountTd" class="totalAmount currentBalanceAmount"></td>
                                <td class="currentBalanceAmount currency">
                                    <span class="grayText bold">
                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                    </span>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <div class="clear"></div>
                <div class="remindersBottom">
                    <table>
                        <tr>
                            <td class="remindersButton">
                                <c:if test="${phiz:impliesService('ReminderManagment')}">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.remind"/>
                                        <tiles:put name="commandHelpKey" value="button.remind.help"/>
                                        <tiles:put name="bundle" value="financesBundle"/>
                                        <tiles:put name="viewType" value="buttonWhite"/>
                                        <tiles:put name="onclick" value="remind();"/>
                                        <tiles:put name="image" value="btnBell.png"/>
                                        <tiles:put name="imagePosition" value="left"/>
                                    </tiles:insert>
                                </c:if>
                            </td>
                            <td>
                                <table class="totalPaymentAmounts">
                                    <tr>
                                        <c:choose>
                                            <c:when test="${isToday}">
                                                <td class="totalTitle">
                                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.totalPaymentAmount.today"/>:
                                                </td>
                                                <td class="totalAmount subscriptionTotalAmountToday">
                                                    <c:choose>
                                                        <c:when test="${totalPaymentAmounts > 0}">
                                                            -${phiz:formatDecimalToAmount(totalPaymentAmounts, 2)}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -0
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="currency subscriptionTotalAmountCurrencyToday">
                                                    <span class="grayText bold">
                                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                                    </span>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td class="totalTitle subscriptionTotalAmountTitleFuture">
                                                    <bean:message bundle="financesBundle" key="lable.finance.calendar.totalPaymentAmount"/>:
                                                </td>
                                                <td class="totalAmount subscriptionTotalAmountFuture">
                                                    <c:choose>
                                                        <c:when test="${totalPaymentAmounts > 0}">
                                                            -${phiz:formatDecimalToAmount(totalPaymentAmounts, 2)}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -0
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="currency subscriptionTotalAmountCurrencyFuture">
                                                    <span class="grayText bold">
                                                        <bean:message bundle="financesBundle" key="lable.rub"/>
                                                    </span>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="clear"></div>
            </div>
        </div>
    </div>
</html:form>