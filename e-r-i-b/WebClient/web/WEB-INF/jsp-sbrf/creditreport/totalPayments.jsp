<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
    report - отчет
--%>
<tiles:importAttribute/>
<div class="credit-history-in-total">
    <div class="credit-history-in-total-line">
        <c:forEach items="${report.totalPayment}" var="payment">
            <c:if test="${payment != ''}">
                <div class="credit-history-in-total-col-names">
                    <div class="credit-history-in-total-title">Итого выплат по кредитам:</div>
                </div>
                <div class="credit-history-in-total-col-values">
                    <div class="credit-history-in-total-value">${payment}</div>
                </div>
                <br/>
            </c:if>
        </c:forEach>
    </div>
    <div class="credit-history-in-total-line delay-by-month">
        <c:forEach items="${report.totalArrears}" var="arrears">
            <c:if test="${arrears.arrears.value != 0}">
                <div class="credit-history-in-total-col-names">
                    <div class="credit-history-in-total-note-title">Просрочка ${arrears.dateArrears} <a><span>${arrears.bankName}</span></a>:</div>
                </div>
                <div class="credit-history-in-total-col-values">
                    <div class="credit-history-in-total-not-value">${arrears.arrears}</div>
                </div>
                <br/>
            </c:if>
        </c:forEach>
    </div>
</div>
