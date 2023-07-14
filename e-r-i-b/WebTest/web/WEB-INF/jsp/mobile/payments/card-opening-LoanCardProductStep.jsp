<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Оформление заявки на кредитную карту(Условия по кредитным картам )</title>
         <script type="text/javascript">
             function changeDateSelector(el)
             {
                document.getElementById("changeDate").value = el;
             }
         </script>
    </head>

    <body>
    <h1>Условия по кредитным картам </h1>

    <html:form action="/mobileApi/cardOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="mobile" flush="false">
            <tiles:put name="address" value="/private/payments/loan/cardProduct.do"/>
            <tiles:put name="formName" value="LoanCardProduct"/>
            <tiles:put name="operation" value="next"/>

            <tiles:put name="data">
                <table>
                    <tr>
                        <td></td>
                        <th>Наименование</th>
                        <th>Доступный кредитный лимит</th>
                        <th>Процентная ставка</th>
                        <th>Годовое обслуживание первый/послед.годы</th>
                        <th>Дополнительные условия</th>
                    </tr>
                    <input type="hidden" name="changeDate" id="changeDate" value="">
                    <c:forEach items="${form.response.loanCardProductStage.option}" var="option">
                        <tr>
                            <td>
                                <input type="radio" name="loanId" value="${option.loanId}" onclick="changeDateSelector(${option.changeDate});"/>
                            </td>
                            <td><div class="loanTitle kind"><c:out value="${option.name}"/></div></td>
                            <td>
                                от <c:out value="${option.creditLimit.min.amount}"/> <c:out value="${phiz:normalizeCurrencyCode(option.creditLimit.min.currency.code)}"/>
                                до <c:out value="${option.creditLimit.max.amount}"/> <c:out value="${phiz:normalizeCurrencyCode(option.creditLimit.max.currency.code)}"/>
                                <c:if test="${option.creditLimit.includeMax}">*</c:if>
                            </td>
                            <td><c:out value="${option.offerInterestRate}"/></td>
                            <td><c:out value="${option.firstYearPayment.amount}"/><c:out value="${phiz:normalizeCurrencyCode(option.firstYearPayment.currency.code)}"/> /
                                <c:out value="${option.nextYearPayment.amount}"/><c:out value="${phiz:normalizeCurrencyCode(option.nextYearPayment.currency.code)}"/></td>
                            <td>
                                <c:if test="${not empty option.terms}">
                                    <c:out value="${option.terms}"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
