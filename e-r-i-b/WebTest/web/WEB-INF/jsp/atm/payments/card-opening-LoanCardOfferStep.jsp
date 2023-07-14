<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Оформление заявки на кредитную карту</title>
    </head>

    <body>
    <h1>Оформление заявки на кредитную карту</h1>

    <html:form action="/atm/cardOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/loan/cardOffer.do"/>

            <tiles:put name="data">
                <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <c:if test="${true}">
                        <table>
                            <tr>
                                <td></td>
                                <th>Наименование</th>
                                <th>Кредитный лимит</th>
                                <th>Процентная ставка</th>
                                <th>Годовое обслуживание(первый/послед.годы)</th>
                                <th>Дополнительные условия</th>

                            <c:if test="${not empty form.response.loanCardOfferStage.option[0].offerId}">
                                <input type="hidden" name="offerId" value="${form.response.loanCardOfferStage.option[0].offerId}"/>
                            </c:if>
                            </tr>
                            <c:forEach items="${form.response.loanCardOfferStage.option}" var="option">
                                <tr>
                                    <td>
                                        <input type="radio" name="loanId" value="${option.loanId}"/>
                                    </td>
                                    <td>
                                        <div class="loanTitle kind"><c:out value="${option.name}"/></div>
                                    </td>
                                    <td>
                                        <c:out value="${option.maxLimit.amount}"/> <c:out value="${phiz:normalizeMetalCode(option.maxLimit.currency.code)}"/>
                                    </td>
                                    <td><c:out value="${option.offerInterestRate}"/></td>
                                    <td><c:out value="${option.firstYearPayment.amount}"/><c:out value="${phiz:normalizeMetalCode(option.firstYearPayment.currency.code)}"/> /
                                        <c:out value="${option.nextYearPayment.amount}"/><c:out value="${phiz:normalizeMetalCode(option.nextYearPayment.currency.code)}"/></td>
                                    <td>
                                        <c:if test="${not empty option.terms}">
                                            <c:out value="${option.terms}"/>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </tiles:put>

            <tiles:put name="operation" value="next"/>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
