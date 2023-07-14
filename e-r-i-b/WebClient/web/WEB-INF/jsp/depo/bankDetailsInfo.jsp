<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="form" value="${frm.form}"/>
<fieldset>
    <table class="additional-deposit-info">
        <c:forEach  items="${form.depositorAccounts}" var="account">
            <c:set var="rub" value="${account.currency.code == 'RUB'}"/>
            <c:choose>
                <c:when test="${rub}" >
                    <tr>
                        <td class="align-right field"><span class="bold depoAddress">Рублевый счет</span></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="align-right field">Номер рублевого счета:</td>
                        <td><span class="bold">${account.accountNumber}</span></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td class="align-right field"><span class="bold depoAddress">Валютный счет</span></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="align-right field">Валюта счета:</td>
                        <td><span class="bold">${account.currency.code}</span></td>
                    </tr>
                    <tr>
                        <td class="align-right field">Номер валютного счета:</td>
                        <td><span class="bold">${account.accountNumber}</span></td>
                    </tr>
                </c:otherwise>
            </c:choose>
            <tr>
                <td class="align-right field">Вид пластиковой карты:</td>
                <td><span class="bold">${account.cardType}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Номер пластиковой карты:</td>
                <td><span class="bold">${account.cardId}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Банк, где открыт 
                    <c:choose>
                        <c:when test="${rub}">рублевый</c:when>
                        <c:otherwise>валютный</c:otherwise>
                    </c:choose>
                    счет:</td>
                <td><span class="bold">${account.bankName}</span></td>
            </tr>
            <tr>
                <td class="align-right field">БИК <c:if test="${not rub}">, SWIFT</c:if>:</td>
                <td><span class="bold">${account.BIC}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Корреспондентский счет:</td>
                <td><span class="bold">${account.corAccount}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Банк, где открыт корреспондентский счет:</td>
                <td><span class="bold">${account.corBankName}</span></td>
            </tr>
            <tr>
                <td class="align-right field">Назначение счета:</td>
                <td><span class="bold">${account.destination}</span></td>
            </tr>
        </c:forEach>
        <tr>
            <td class="align-right field"><span class="bold depoAddress">Дополнительные реквизиты анкеты</span></td>
            <td>&nbsp;</td>
        </tr>
        <c:if test="${not empty form.recIncomeMethod}">
            <tr>
                <td class="align-right field">Способ получения доходов:</td>
                <td><span class="bold">${form.recIncomeMethod}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.recInstryctionMethod}">
            <tr>
                <td class="align-right field">Способ приема поручений от владельца счета депо:</td>
                <td><span class="bold">${form.recInstryctionMethod}</span></td>
            </tr>
        </c:if>
        <c:if test="${not empty form.recInfoMethod}">
            <tr>
                <td class="align-right field">Способ передачи информации владельцу счета депо:</td>
                <td><span class="bold">${form.recInfoMethod}</span></td>
            </tr>
        </c:if>
        <tr>
            <td class="align-right field">Дата заполнения анкеты:</td>
            <td><span class="bold">${phiz:formatDateWithStringMonth(form.startDate)}</span></td>
        </tr>
    </table>
</fieldset>