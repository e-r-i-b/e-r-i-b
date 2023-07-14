<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html:form action="/private/ima/printExtendedAbstract">
<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imAccountClient" value="${form.client}" scope="page"/>
<c:set var="imAccountLink" value="${form.imAccountLink}" scope="page"/>
<c:set var="imAccount" value="${imAccountLink.imAccount}" scope="page"/>
<c:set var="imAccountAbstract" value="${form.imAccountAbstract}" scope="page"/>

<tiles:insert definition="printDoc">
<tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>

<tiles:put name="data" type="string">

<style type="text/css">
    .tablePayments {
        font-family: The Times Roman;
        text-align: left;
        font-size: 9px;
        border: 1px solid #000000;
        width: 100%;
        vertical-align: middle;
    }

    .tablePayments td {
        font-family: The Times Roman;
        text-align: center;
        border-right: 1px solid #000000;
        border-bottom: 1px solid #000000;
        vertical-align: middle;
    }

    .label {
        text-align: center;
        font-family:The Times Roman;
        font-size:12px;
    }
</style>
<br/>
<div style="margin: 0cm 0.5cm 0cm 1cm; font-size:12px; font-family:The Times Roman;">
    <table style="font-family:The Times Roman">
        <tr>
            <td>
                ${form.office.name}
            </td>
        </tr>
        <tr>
            <td class="labelAbstractPrint">
                <bean:write name="form" property="office.code.fields(office)"/>&nbsp;№&nbsp;<bean:write
                    name="form" property="office.code.fields(branch)"/>
            </td>
        </tr>
    </table>
    <br/>

    <div align="center" style="text-align:center; font-family:The Times Roman">
        <c:if test="${param.mode != 'info' && !empty imAccountAbstract}">
            <b>
                Выписка из лицевого счёта
                <br/><br/>
                <span class="label">
                за период с&nbsp;${phiz:dateToString(imAccountAbstract.fromDate.time)}
                &nbsp;по&nbsp;${phiz:dateToString(imAccountAbstract.toDate.time)}
                </span>
            </b>
            <br/>
        </c:if>
        <c:if test="${param.mode == 'info'}">
            <b>ДОПОЛНИТЕЛЬНАЯ ИНФОРМАЦИЯ ПО СЧЁТУ</b>
            <br/>
        </c:if>
    </div>
    <br/>

    <c:if test="${!empty imAccountAbstract}">
        <p style="${param.mode == 'info' ? 'text-align:left;' : 'text-align:center; font-weight:bold;'}">
            <span class="label">Дата&nbsp;${phiz:dateToString(phiz:currentDate().time)}</span>
        </p>
        <br/>
    </c:if>

    <c:if test="${!empty imAccountAbstract && param.mode != 'info'}">
        <p>
            Ф.И.О. клиента:
            <span class="label">${imAccountClient.patrName}&nbsp;${imAccountClient.firstName}&nbsp;${imAccountClient.surName}</span>
        </p>
        <br/>
    
        <table style="font-family:The Times Roman; font-size:12px;">
            <tr>
                <td>
                    Счёт №:&nbsp;
                </td>
                <td class="label">
                        ${imAccountLink.number}
                </td>
                <td>&nbsp;</td>
                <td class="label">
                    Вид металла:
                    <c:if test="${not empty imAccount.currency}">
                        &nbsp;${imAccount.currency.name}
                    </c:if>
                </td>
            </tr>
            <tr>
                <td/>
                <td/>
                <td/>
                <td/>
            </tr>
        </table>
        <br/>

        <p>
            Дата предыдущей операции по счёту:
            <span class="label">${phiz:dateToString(imAccountAbstract.previousOperationDate.time)}</span>
        </p>
        <br/>

        <p>
            Входящий остаток:
            <span class="label">${phiz:formatAmount( imAccountAbstract.openingBalanceInRub )}</span>
            Учётная цена Банка России:
            <span class="label"><bean:write name="imAccountAbstract" property="rate" format="0.00"/></span>руб./грамм
            &nbsp;(${phiz:formatAmount(form.incomingBalance)})
        </p>
    </c:if>
</div>
<br/>

<c:if test="${!empty imAccountAbstract && param.mode != 'info'}">
    <div style="margin-left:1cm;">
        <table class="tablePayments">
            <tr>
                <c:if test="${param.mode == 'extended'}">
                    <td rowspan="2">№ операции</td>
                </c:if>

                <td rowspan="2">Дата операции</td>

                <c:if test="${param.mode == 'extended'}">
                    <td rowspan="2">Номер документа</td>
                    <td rowspan="2">Шифр операции</td>
                </c:if>

                <td rowspan="2">Номер корреспондирующего счёта</td>

                <c:if test="${param.mode == 'abstract'}">
                    <td rowspan="2">Тип счёта</td>
                </c:if>

                <td rowspan="2">Дебет</td>
                <td rowspan="2">Кредит</td>

                <c:if test="${param.mode == 'extended'}">
                    <td rowspan="2">Остаток</td>
                </c:if>

                <c:if test="${param.mode == 'extended'}">
                    <td colspan="3">В том числе в физической форме</td>
                </c:if>
            </tr>

            <tr>
                <c:if test="${param.mode == 'extended'}">
                    <td>Дебет</td>
                    <td>Кредит</td>
                    <td>Остаток</td>
                </c:if>
            </tr>

            <c:if test="${param.mode == 'extended'}">
                <tr>
                    <td></td>
                    <td colspan="6">Входящие остатки</td>
                    <td>${form.incomingBalance.decimal}</td>
                    <td/>
                    <td/>
                    <td>${imAccountAbstract.openingBalance.decimal}</td>
                </tr>
            </c:if>
            <c:if test="${!empty imAccountAbstract}">
                <logic:iterate id="transaction" name="imAccountAbstract" property="transactions" indexId="i">
                    <tr>
                        <c:if test="${param.mode == 'extended'}">
                            <td>${transaction.operationNumber}</td>
                        </c:if>

                        <td>${phiz:dateToString(transaction.date.time)}</td>

                        <c:if test="${param.mode == 'extended'}">
                            <td>${transaction.documentNumber}</td>
                            <td>${transaction.operationCode}</td>
                        </c:if>

                        <td>${transaction.correspondentAccount}</td>

                        <c:if test="${param.mode == 'abstract'}">
                            <td>
                                <c:if test="${not empty imAccount.currency}">
                                    ${imAccount.currency.name}&nbsp;(${phiz:normalizeCurrencyCode(imAccount.currency.code)})
                                </c:if>
                            </td>
                        </c:if>

                        <td>
                            <c:choose>
                                <c:when test="${!empty transaction.debitSum && transaction.debitSum.decimal > 0.00}">
                                    ${transaction.debitSum.decimal}
                                </c:when>
                            </c:choose>
                        </td>
                        
                        <td>
                            <c:choose>
                                <c:when test="${!empty transaction.creditSum && transaction.creditSum.decimal > 0.00}">
                                    ${transaction.creditSum.decimal}
                                </c:when>
                            </c:choose>
                        </td>
                        <c:if test="${param.mode == 'extended'}">
                            <td>${transaction.balance.decimal}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${!empty transaction.debitSumInPhizicalForm && transaction.debitSumInPhizicalForm.decimal > 0.00}">
                                        ${transaction.debitSumInPhizicalForm.decimal}
                                    </c:when>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${!empty transaction.creditSumInPhizicalForm && transaction.creditSumInPhizicalForm.decimal > 0.00}">
                                        ${transaction.creditSumInPhizicalForm.decimal}
                                    </c:when>
                                </c:choose>
                            </td>

                            <c:set var="outboxBalanceInPhys" value="${transaction.balanceInPhizicalForm.decimal}"/>
                            <td>${outboxBalanceInPhys}</td>
                        </c:if>
                    </tr>
                </logic:iterate>
            </c:if>
            <c:if test="${param.mode == 'extended'}">
                <tr>
                    <td colspan="5">Обороты за период, Исходящие остатки</td>
                    <td>${form.totalDebitAmount.decimal}</td>
                    <td>${form.totalCreditAmount.decimal}</td>
                    <td>${form.outboxBalance.decimal}</td>
                    <td>${form.totalDebitAmountInPhys.decimal}</td>
                    <td>${form.totalCreditAmountInPhys.decimal}</td>
                    <td>${outboxBalanceInPhys}</td>
                </tr>
            </c:if>
        </table>
        <br/>
        <div>
            <p style="font-family:The Times Roman; font-size:12px;">
                Исходящий остаток:&nbsp;${phiz:formatAmount(imAccountAbstract.closingBalanceInRub)}
                &nbsp;(${phiz:formatAmount(form.outboxBalance)})
            </p>
        </div>
    </div>
</c:if>

</tiles:put>
</tiles:insert>
</html:form>