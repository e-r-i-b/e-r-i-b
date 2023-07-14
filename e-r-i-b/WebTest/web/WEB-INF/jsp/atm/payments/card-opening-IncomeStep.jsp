<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Оформление заявки на кредитную карту(Выбор дохода)</title>

    </head>

    <body>
    <h1>Оформление заявки на кредитную карту(Выбор дохода)</h1>

    <html:form action="/atm/cardOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/incomeLevel.do"/>

            <tiles:put name="data">
                <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <c:if test="${not empty form.response and not empty form.response.incomeStage}">
                        <c:set var="incomeStage" value="${form.response.incomeStage}"/>
                        <table>
                            <caption>Средняя сумма доходов клиента</caption>

                            <c:forEach var="option" items="${incomeStage.option}">
                                <tr>
                                    <td>
                                        <input type="radio" name="selectedIds"  value="${option.id}" >  ID:     <c:out value="${option.id}"/> </input>
                                    </td>
                                    <td>
                                        от <c:out value="${option.minIncome.amount}"/>   <c:out value="${phiz:normalizeMetalCode(option.minIncome.currency.code)}"/>
                                    </td>
                                    <td>
                                        до <c:out value="${option.maxIncome.amount}"/>   <c:out value="${phiz:normalizeMetalCode(option.maxIncome.currency.code)}"/>
                                    </td>
                                    <td>
                                          <c:if test="${option.maxIncomeInclude}">*</c:if>
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
