<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<%--
  resourceAbstract ima выписка
--%>

<div class="mini-abstract">
    <sl:collection id="transaction" model="no-pagination"
                   name="resourceAbstract"
                   property="transactions"
                   collectionSize="3" >
        <c:if test="${not empty transaction.correspondentAccount}">
            <sl:collectionItem styleClass="align-left">
                ${transaction.correspondentAccount}
            </sl:collectionItem>
        </c:if>
        <sl:collectionItem styleClass="mini-abstract-date">
            <c:if test="${not empty transaction.date}">
                <span class="text-gray"> ${phiz:formatDateDependsOnSysDate(transaction.date, false, false)}&nbsp;</span>
            </c:if>
        </sl:collectionItem>

        <%--TODO Убрать, когда появится полная выписка--%>
        <c:set var="credit" value="${transaction.creditSum}"/>
        <c:set var="debit" value="${transaction.debitSum}"/>

        <sl:collectionItem styleClass="mini-abstract-name">
            <c:choose>
                <c:when test="${not empty credit}">
                    ${credit.currency.name}&nbsp;(${phiz:normalizeCurrencyCode(credit.currency.id)})
                </c:when>
                <c:when test="${not empty debit}">
                    ${debit.currency.name}&nbsp;(${phiz:normalizeCurrencyCode(debit.currency.id)})
                </c:when>
            </c:choose>
        </sl:collectionItem>

        <sl:collectionItem styleClass="align-right mini-abstract-amount">

            <c:choose>
                <c:when test="${!empty transaction.creditSumInPhizicalForm && transaction.creditSumInPhizicalForm.decimal > 0.00}">
                    &minus;${phiz:formatAmount(transaction.creditSumInPhizicalForm)}
                </c:when>
                <c:when test="${!empty transaction.debitSumInPhizicalForm && transaction.debitSumInPhizicalForm.decimal > 0.00}">
                    <span class="plus-amount">
                        ${phiz:formatAmount(transaction.debitSumInPhizicalForm)}
                    </span>
                </c:when>
                <%--TODO Отображать пока не появится полная выписка--%>
                <c:when test="${!empty credit && credit.decimal > 0.00}">
                    &minus;${phiz:formatAmount(credit)}
                </c:when>
                <c:when test="${!empty debit && debit.decimal > 0.00}">
                    <span class="plus-amount">
                        ${phiz:formatAmount(debit)}
                    </span>
                </c:when>
            </c:choose>
        </sl:collectionItem>

        <sl:collectionItem styleClass="align-right mini-abstract-amount">
            <c:choose>
                <c:when test="${!empty transaction.operationRurSumm && transaction.operationRurSumm.decimal != '0.00'}">
                    ${phiz:formatAmount(transaction.operationRurSumm)}
                </c:when>
                <c:otherwise>
                    <div align="center" >
                        &mdash;
                    </div>
                </c:otherwise>
            </c:choose>
        </sl:collectionItem>

    </sl:collection>
</div>