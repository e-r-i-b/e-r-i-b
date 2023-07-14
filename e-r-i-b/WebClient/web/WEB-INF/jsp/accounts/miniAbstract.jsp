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
  resourceAbstract выписка по счету
--%>

<div class="mini-abstract">
    <sl:collection id="transaction" model="no-pagination"
                   name="resourceAbstract" property="transactions"
                   collectionSize="3">
        <c:choose>
            <c:when test="${!empty transaction.description}">
                <sl:collectionItem styleClass="mini-abstract-name"
                                   property="description"/>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${!empty transaction.creditSum}">
                        <sl:collectionItem styleClass="mini-abstract-name" value="«ачисление"/>

                    </c:when>
                    <c:when test="${!empty transaction.debitSum}">
                        <sl:collectionItem styleClass="mini-abstract-name" value="—писание"/>
                    </c:when>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <sl:collectionItem styleClass="mini-abstract-date">
            ${phiz:formatDateDependsOnSysDate(transaction.date, false, false)}
        </sl:collectionItem>
        <sl:collectionItem styleClass="mini-abstract-amount">
            <c:choose>
                <c:when test="${!empty transaction.creditSum and transaction.creditSum.decimal != '0.00'}"> <%-- из v6 приходит в случае пустой суммы зачислени€ приходит не null, а 0.00  --%>
                    <span class="plus-amount">
                        ${phiz:formatAmount(transaction.creditSum)}
                    </span>
                </c:when>
                <c:when test="${!empty transaction.debitSum and transaction.debitSum.decimal != '0.00'}">  <%-- из v6 приходит в случае пустой суммы списани€ приходит не null, а 0.00  --%>
                    &minus;${phiz:formatAmount(transaction.debitSum)}
                </c:when>
                <c:otherwise>
                    <fmt:formatNumber value="0" pattern="0"/>
                </c:otherwise>
            </c:choose>
        </sl:collectionItem>
        <sl:collectionItem styleClass="align-right">
            ${transaction.recipient}
        </sl:collectionItem>
    </sl:collection>
</div>