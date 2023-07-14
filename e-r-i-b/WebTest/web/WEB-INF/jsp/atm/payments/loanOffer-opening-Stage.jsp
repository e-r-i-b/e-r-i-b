<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Оформление заявки на предодобренный кредит (Условия)</title>
        <script type="text/javascript">
            function setDuration(duration)
            {
                var durationElement = document.getElementById("duration");
                durationElement.value = duration;
            }
        </script>
    </head>

    <body>
    <h1>Оформление заявки на  предодобренный кредит (Условия)</h1>

    <html:form action="/atm/loanOfferOpeningClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address" value="/private/payments/loan/loanOffer.do"/>

            <tiles:put name="data">
                <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
                    <c:if test="${not empty form.response and not empty form.response.loanOfferStage}">
                        <c:set var="loanOfferStage" value="${form.response.loanOfferStage}"/>
                        <input type="hidden" id="duration" name="duration"/>
                        <table>
                            <th></th>
                            <th>Наименование кредита</th>
                            <th>Сумма кредита</th>
                            <th>Процентрная ставка</th>
                            <th>Срок кредита</th>
                            <c:forEach var="option" items="${loanOfferStage.option}">
                                <tr>
                                    <td><input type="radio" name="loanId" value="${option.loanId}" onclick="setDuration(${option.duration})"/></td>
                                    <td><c:out value="${option.name}"/></td>
                                    <td>до <c:out value="${option.offerSum.amount}"/> <c:out value="${phiz:normalizeCurrencyCode(option.offerSum.currency.code)}"/></td>
                                    <td><c:out value="${option.rate}"/>%</td>
                                    <td>до <c:out value="${option.duration}"/> мес.</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </tiles:put>

            <tiles:put name="operation" value="next"/>
            <tiles:put name="formName" value="LoanOffer"/>
        </tiles:insert>
    </html:form>

    </body>
</html:html>
