<%--
  User: usachev
  Date: 15.05.15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${AsyncLoadClaimForm}"/>
<c:if test="${!form.hasErrors}">
    <span id="markerAccept"></span>
    <c:forEach var="loan" items="${form.data}">
        <tiles:insert definition="guestClaim" flush="false">
            <tiles:put name="type" value="credit"/>
            <tiles:put name="name">
                <span onclick="edit(event, '${loan.id}', '${loan.state.code}','${loan.formName}');">${loan.productName}</span>
            </tiles:put>
            <tiles:put name="info">
                <c:out value="${phiz:formatAmountWithoutCents(loan.loanAmount)}"/>&nbsp;<c:out value="${loan.loanPeriod}"/>&nbsp;мес.&nbsp;
                <c:set var="loanRate" value="${loan.loanRate}"/>
                <c:if test="${loanRate != null}">
                    <c:out value="${phiz:formatPercentRate(loanRate.minLoanRate)}"/>
                </c:if>
            </tiles:put>
            <tiles:put name="state">
                <c:set var="claimStatus" value="${loan.state}"/>
                <c:choose>
                    <c:when test="${claimStatus == 'DISPATCHED'}">
                        Обрабатывается банком
                    </c:when>
                    <c:when test="${claimStatus == 'APPROVED'}">
                        Кредит одобрен
                    </c:when>
                    <c:when test="${claimStatus == 'REFUSED'}">
                        Заявка отклонена банком
                    </c:when>
                    <c:when test="${claimStatus == 'ISSUED'}">
                        Кредит выдан
                    </c:when>
                    <c:when test="${claimStatus == 'PREADOPTED'}">
                        Принято предварительное решение
                    </c:when>
                    <c:when test="${claimStatus == 'WAIT_TM'}">
                        Ожидайте звонка сотрудника банка
                    </c:when>
                    <c:when test="${claimStatus == 'VISIT_OFFICE'}">
                        Требуется визит в отделение банка
                    </c:when>
                    <c:otherwise>
                        Черновик
                    </c:otherwise>
                </c:choose>
            </tiles:put>
            <tiles:put name="date">
                <bean:message key="list.label.date.from" bundle="sbnkdBundle"/> ${phiz:formatDayWithStringMonthWithoutNought(loan.dateCreated)}
            </tiles:put>
            <tiles:put name="type" value="credit"/>
        </tiles:insert>
    </c:forEach>
</c:if>