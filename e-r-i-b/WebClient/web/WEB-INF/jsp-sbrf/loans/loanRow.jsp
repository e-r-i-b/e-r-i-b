<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tr class="cred-hist-report__b-line loan_row debts_row">
    <td class="month debts_cell t-report_b-month cred-hist-report_b-date "><c:out value="${phiz:formatDayWithStringMonth(month.scheduleItem.date)}"/></td>
    <td class="cred-hist-report_gray cred-hist-report_right-summ   "><c:out value="${phiz:formatBigDecimal(month.scheduleItem.principalAmount.decimal)}"/></td>
    <td class="cred-hist-report_b-sum cred-hist-report_gray        "><c:out value="${phiz:formatBigDecimal(month.scheduleItem.interestsAmount.decimal)}"/></td>
    <td class="cred-hist-report_b-pr cred-hist-report_to-pay       "><c:out value="${phiz:formatBigDecimal(month.scheduleItem.totalPaymentAmount.decimal)}"/></td>
    <td class="cred-hist-report_b-month-p cred-hist-report_gray    "><c:out value="${phiz:formatBigDecimal(month.scheduleItem.remainDebt.decimal)}"/></td>
</tr>


