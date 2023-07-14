<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Оформление заявки на кредит (Условия по кредитам)</title>
        <script type="text/javascript">
            function changeDateSelector(el)
            {
                document.getElementById("changeDate").value = el;
            }
        </script>
    </head>

    <body>
    <h1>Оформление заявки на кредит (Условия по кредитам)</h1>

    <html:form action="/atm/loanProductOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/loan/loanProduct.do"/>
            <tiles:put name="formName" value="LoanProduct"/>
            <tiles:put name="operation" value="next"/>

            <tiles:put name="data">
                <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <c:if test="${not empty form.response and not empty form.response.loanProductStage}">
                        <c:set var="loanProductStage" value="${form.response.loanProductStage}"/>
                        <input type="hidden" name="changeDate" id="changeDate" value="">
                        <table>
                            <c:forEach var="option" items="${loanProductStage.option}">
                                <th>Вид</th>
                                <th>Наименование</th>
                                <th>От</th>
                                <th>До</th>
                                <th>Первоначальный взнос(%)</th>
                                <th>Обеспечение</th>
                                <tr>
                                    <td><c:out value="${option.loanKindName}"/></td>
                                    <td><c:out value="${option.name}"/></td>
                                    <td>
                                        <c:if test="${not empty option.duration.min}">
                                            <c:out value="${option.duration.min}"/> мес.
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty option.duration.max}">
                                            <c:out value="${option.duration.max}"/> мес.
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty option.initialInstalment.min and not empty option.initialInstalment.max}">
                                             <c:out value="${option.initialInstalment.min}"/>-<c:out value="${option.initialInstalment.max}"/>
                                        </c:if>
                                    </td>
                                    <td><c:out value="${option.needSurety}"/></td>

                                </tr>
                                <th></th> <%--интпут--%>
                                <th>Сумма кредита</th>
                                <th>Процентная ставка (%)</th>
                                <tr>

                                    <c:forEach var="condition" items="${option.conditions.condition}">
                                    <tr>
                                        <td><input type="radio" onclick="changeDateSelector(${condition.changeDate});" value="${condition.loanId}" name="loanId"></td>
                                        <td>
                                            <c:if test="${not empty condition.amount.min}">
                                                от <c:out value="${condition.amount.min.amount}"/> <c:out value="${phiz:normalizeCurrencyCode(condition.amount.min.currency.code)}"/>
                                                <c:choose>
                                                    <c:when test="${not empty condition.amount.max.maxAmount}">
                                                        до <c:out value="${condition.amount.max.maxAmount.amount}"/> <c:out value="${phiz:normalizeCurrencyCode(condition.amount.max.maxAmount.currency.code)}"/>
                                                        <c:if test="${condition.amount.max.include == false}">*<c:set
                                                                var="include" value="1"/></c:if>
                                                    </c:when>
                                                    <c:when test="${not empty condition.amount.max.maxPercent}">
                                                        до ${condition.amount.max.maxPercent} % от стоимости
                                                        <c:if test="${condition.amount.max.include == false}">*<c:set
                                                                var="include" value="1"/></c:if>
                                                    </c:when>
                                                </c:choose>
                                            </c:if>
                                        </td>
                                        <td>
                                             <c:choose>
                                                <c:when test="${not empty condition.interestRate.min and not empty condition.interestRate.max.maxAmount}">
                                                    <c:out value="${condition.interestRate.min}-${condition.interestRate.max.maxAmount}"/>
                                                    <c:if test="${condition.interestRate.max.include == false}">*<c:set
                                                            var="include" value="1"/></c:if>
                                                </c:when>
                                                <c:when test="${not empty condition.interestRate.min and empty condition.interestRate.max.maxAmount}">
                                                    от <c:out value="${condition.interestRate.min}"/>
                                                </c:when>
                                                <c:when test="${empty condition.interestRate.min and not empty condition.interestRate.max.maxAmount}">
                                                    до <c:out value="${condition.interestRate.max.maxAmount}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    -
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    </c:forEach>

                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </tiles:put>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
