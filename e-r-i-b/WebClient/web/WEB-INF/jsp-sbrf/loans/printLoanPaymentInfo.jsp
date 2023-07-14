<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/loans/payment/info/print">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="user" value="${form.user}"/>
<c:set var="loanLink" value="${form.loanLink}" scope="request"/>
<c:set var="loan" value="${loanLink.loan}" scope="request"/>
<c:set var="loanOffice" value="${loan.office}" scope="request"/>
<c:set var="loanParentOffice" value="${phiz:getTerBank(loanOffice.code)}" scope="request"/>
<c:set var="scheduleItem" value="${form.scheduleItem}" scope="request"/>



<tiles:insert definition="printDoc">
<tiles:put name="data" type="string">

<br/>
<table width="100%">
    <tr>
        <td>
            Сбербанк России ОАО
        </td>
    </tr>
    <tr>
         <td>
             ${loanParentOffice.name}
         </td>
    </tr>
    <tr>
        <td>
            ${loanOffice.name}
        </td>
    </tr>

    <tr>
        <td style="font-size:11pt;text-align:center;padding-top:20mm;font: bold 11pt Arial;">
           <h1>платеж по кредиту от ${phiz:formatDateWithStringMonth(scheduleItem.date)}</h1>
        </td>
    </tr>
    <tr>
        <td>
             ФИО клиента:&nbsp;
            <c:if test="${not empty user}">
                <c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/>
            </c:if>
        </td>
    </tr>
    <tr>
        <td>
            Тип кредита: ${loan.description}
        </td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
        <td>
            Название: <bean:write name="loanLink" property="name"/>
        </td>
    </tr>
    <tr>
        <td>
            Сумма основного долга: ${phiz:formatAmount(scheduleItem.principalAmount)}
        </td>
    </tr>
    <tr>
        <td>
           Выплаты по процентам:  ${phiz:formatAmount(scheduleItem.interestsAmount)}
        </td>
    </tr>

     <c:forEach var="listElement" items="${scheduleItem.penaltyDateDebtItemMap}" varStatus="lineInfo">
         <tr>
            <td>
               ${listElement.key.description}:  ${phiz:formatAmount(listElement.value.amount)}
            </td>
        </tr>
    </c:forEach>
    
    <tr>
        <td>
            Итого:   ${phiz:formatAmount(scheduleItem.totalPaymentAmount)}
        </td>
    </tr>
    <c:if test="${not empty scheduleItem.overpayment}">
        <tr>
            <td>
               Переплата :  ${phiz:formatAmount(scheduleItem.overpayment)}
            </td>
        </tr>
    </c:if>
    <tr>
        <td>

        </td>
    </tr>
</table>
</tiles:put>
</tiles:insert>
</html:form>
