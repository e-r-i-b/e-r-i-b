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
  resourceAbstract карточные выписка
--%>
<div class="mini-abstract">
    <sl:collection id="transaction" model="simple-pagination" name="resourceAbstract"
                   property="transactions">
        <sl:collectionItem styleClass="mini-abstract-name">
            <c:if test="${not empty transaction.description}">
                ${transaction.description}
            </c:if>
            <c:if test="${not empty transaction.shopInfo}">
                ${transaction.shopInfo}
            </c:if>
        </sl:collectionItem>
        <sl:collectionItem styleClass="mini-abstract-date">
            <nobr>${phiz:formatDateDependsOnSysDate(transaction.date, true, false)}&nbsp;</nobr>
        </sl:collectionItem>
        <sl:collectionItem styleClass="mini-abstract-amount">
            <%--Если creditSum 0, а accountCreditSum не ноль, то выводим accountCreditSum.
                                                                                                                                   Аналогично для debitSum и  accountDebitSum.--%>
            <nobr>
                <c:choose>
                    <c:when test="${(empty transaction.creditSum || transaction.creditSum.decimal == 0) && !empty transaction.accountCreditSum && transaction.accountCreditSum.decimal!=0}">
                        <span class="plus-amount">
                            ${phiz:formatAmount(transaction.accountCreditSum)}
                        </span>
                    </c:when>
                    <c:when test="${!empty transaction.creditSum && transaction.creditSum.decimal != 0}">
                        <span class="plus-amount">
                            ${phiz:formatAmount(transaction.creditSum)}
                        </span>
                    </c:when>
                    <c:when test="${(empty transaction.debitSum || transaction.debitSum.decimal == 0) && !empty transaction.accountDebitSum && transaction.accountDebitSum.decimal!=0}">
                        &minus;${phiz:formatAmount(transaction.accountDebitSum)}
                    </c:when>
                    <c:when test="${!empty transaction.debitSum && transaction.debitSum.decimal != 0}">
                        &minus;${phiz:formatAmount(transaction.debitSum)}
                    </c:when>
                </c:choose>
            </nobr>
        </sl:collectionItem>
        <sl:collectionItem>&nbsp;</sl:collectionItem>
    </sl:collection>
</div>