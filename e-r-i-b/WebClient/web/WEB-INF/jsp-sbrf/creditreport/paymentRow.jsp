<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>
<c:set var="indicator" value=""/>
<c:choose>
    <c:when test="${month.state.code eq 'A'}">
        <c:set var="indicator" value="1-30"/>
    </c:when>
    <c:when test="${month.state.code eq '1'}">
        <c:set var="indicator" value="31-60"/>
    </c:when>
    <c:when test="${month.state.code eq '2'}">
        <c:set var="indicator" value="61-90"/>
    </c:when>
    <c:when test="${month.state.code eq '3'}">
        <c:set var="indicator" value="91-120"/>
    </c:when>
    <c:when test="${month.state.code eq '4'}">
        <c:set var="indicator" value="121-150"/>
    </c:when>
    <c:when test="${month.state.code eq '5'}">
        <c:set var="indicator" value="151-180"/>
    </c:when>
    <c:when test="${month.state.code eq '6'}">
        <c:set var="indicator" value="181"/>
    </c:when>
    <c:when test="${month.state.code eq '8'}">
        <c:set var="indicator" value="neupl"/>
    </c:when>
    <c:when test="${month.state.code eq 'L'}">
        <c:set var="indicator" value="sud"/>
    </c:when>
    <c:when test="${month.state.code eq 'W'}">
        <c:set var="indicator" value="spis"/>
    </c:when>
</c:choose>

<tr class="cred-hist-report__b-line cred-hist-pay-val-${indicator} debts_row">
    <td class="cred-hist-report_b-month month debts_cell"><c:out value="${month.month}"/></td>
    <td class="cred-hist-report_b-state debts_cell"><c:out value="${month.state.name}"/></td>
    <td class="cred-hist-report_b-sum debts_cell"><c:out value="${month.balance}"/></td>
    <td class="cred-hist-report_b-pr debts_cell"><c:out value="${month.arrears}"/></td>
    <td class="cred-hist-report_b-month-p debts_cell"><c:out value="${month.payment}"/></td>
</tr>
