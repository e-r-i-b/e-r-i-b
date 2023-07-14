<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 08.10.2009
  Time: 13:08:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="user" value="${form.user}"/>

<script type="text/javascript">
    function setWindowPosition(event)
    {
        event = event || window.event;
        <%--для IE--%>
        if (event.pageX == null && event.clientX != null )
        {
            var html = document.documentElement;
            var body = document.body;
            event.pageY = event.clientY + (html && html.scrollTop || body && body.scrollTop || 0) - (html.clientTop || 0);
        }
        document.getElementById('emailwin').style.position = "absolute";
        document.getElementById('emailwin').style.top = event.pageY+'px';
        document.getElementById('emailwin').style.left = 0;
    }
</script>
<c:if test="${phiz:impliesOperation('CreatePrivateScanClaimOperation', null)}">
    <div id="emailwin">
        <tiles:insert definition="window" flush="false">
            <tiles:put name="id" value="sendDocumentWindow"/>
            <tiles:put name="data">
                <tiles:insert page="sendDocumentToEmailWindow.jsp" flush="false"/>
            </tiles:put>
            <tiles:put name="closeCallback" value="onCloseWindow"/>
        </tiles:insert>
    </div>
</c:if>

<div style="position:absolute; top: 0; left: 0;">
    <div>
        ${office.name}
    </div>
    <c:if test="${not empty office.code}">
        <div>
            № <bean:write name="office" property="code.fields(branch)"/>/<bean:write name="office"
                                                                                     property="code.fields(office)"/>
        </div>
    </c:if>
</div>
<table style="width:100%;">
    <tr>
        <td style="font-size:14pt;text-align:center;padding-top: 10px;text-transform:uppercase;font-weight:bold;">
            Выписка из лицевого счета по вкладу
        </td>
    </tr>
    <tr>
        <td class="needBorder" style="padding-top:5mm;">
            <table cellpadding="0" cellspacing="0" style="width:100%;">
            <tr>
                <td class="labelAbstractPrint">
                    Номер лицевого счета: №&nbsp;
                    <span class="bold">${phiz:getFormattedAccountNumber(accountLink.number)}</span>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Цель депозита:&nbsp;
                    <span class="bold">Получение дохода в виде процентов, начисляемых на сумму размещенных денежных средств.</span>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Номер договора:&nbsp;
                    <span class="bold">${user.agreementNumber}</span>
                </td>
            </tr>
             <tr>
                <td class="labelAbstractPrint">
                    Дата заключения договора:&nbsp;
                    <c:if test="${not empty account.openDate}">
                        <span class="bold"><bean:write name="account" property="openDate.time" format="dd.MM.yyyy"/></span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <xsl:variable name="curCode" select="currencyCode"/>
                <td class="labelAbstractPrint">
                    Вклад:&nbsp;
                    <span class="bold">${accountLink.description} в <c:if test="${not empty accountLink.currency}">${accountLink.currency.code}</c:if></span>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Срок погашения депозита (срок вклада):&nbsp;
                    <span class="bold">
                    <c:set var="account" value="${accountLink.account}"/>
                    <c:if test="${not empty account.demand}">
                        <c:choose>
                            <c:when test="${account.demand}">
                                    до востребования
                            </c:when>
                            <c:otherwise>
                                <c:if test="${not empty account and not empty account.closeDate}">
                                    ${phiz:formatDateWithStringMonth(account.closeDate)} г.
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Размер процентной ставки:&nbsp;
                    <c:if test="${(!empty account.interestRate) && (account.interestRate >= 0)}">
                        <span class="bold"><fmt:formatNumber value="${account.interestRate}" pattern="##0.00"/>%</span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Вкладчик:&nbsp;
                    <c:set var="client" value="${form.client}"/>
                    <c:if test="${not empty client}">
                        <span class="bold" style="text-transform:uppercase;"><c:out value="${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}"/></span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Выписка составлена за период&nbsp;
                    <span class="bold"><c:if test="${not empty resourceAbstract.fromDate}">с
                        <bean:write name="resourceAbstract" property="fromDate.time" format="dd.MM.yyyy"/></c:if><c:if test="${not empty resourceAbstract.toDate}"> по
                        <bean:write name="resourceAbstract" property="toDate.time" format="dd.MM.yyyy"/></c:if></span>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Дата предыдущей операции по счету:&nbsp;
                    <span class="bold">
                    <c:choose>
                        <c:when test="${not empty resourceAbstract.previousOperationDate}">
                            <bean:write name="resourceAbstract" property="previousOperationDate.time" format="dd.MM.yyyy"/>
                        </c:when>
                        <c:otherwise>
                            &mdash;
                        </c:otherwise>
                    </c:choose>
                    </span>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Входящий остаток:&nbsp;
                    <span class="bold">
                     <c:set var="openingBalance" value="${resourceAbstract.openingBalance}"/>
                     <c:if test="${!empty openingBalance}">
                        ${phiz:formatAmount(openingBalance)}
                     </c:if>
                     <c:if test="${not empty resourceAbstract.fromDate}">
                         на&nbsp;${phiz:formatDateWithStringMonth(resourceAbstract.fromDate)} г.
                     </c:if>
                     </span>
                </td>
            </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="borderedTable" style="padding-top:3mm;">
            <c:if test="${phiz:impliesOperation('CreatePrivateScanClaimOperation', null)}">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="orange"/>
                    <tiles:put name="data">
                        <bean:message key="eds.notification.message" bundle="sendClaimBundle"/>
                    </tiles:put>
                </tiles:insert>
            </c:if>
            <table cellpadding="0" cellspacing="0" style="width:100%; font-size:9pt;">
            <tr class="tblInfHeaderAbstrPrint">
                <td rowspan="2" class="docTdLeftTopBorder" style="width:10%;">Дата совершения операции</td>
                <td rowspan="2" class="docTdTopBorder"  style="width:10%;">Номер документа</td>
                <td rowspan="2" class="docTdTopBorder"  style="width:10%;">Вид ( шифр) операции</td>
                <td rowspan="2" class="docTdTopBorder"  style="width:15%;">Наименование операции</td>
                <td rowspan="2" class="docTdTopBorder"  style="width:12%;">Номер корреспондирующего счета</td>
                <td rowspan="2" class="docTdTopBorder"  style="width:10%;">Наименование корреспондента</td>
                <td colspan="2" class="docTdTopBorder"  style="width:20%;border-bottom-width: 1px;">Сумма операции</td>
                <td rowspan="2" class="docTdBoldBorder"  style="width:13%;">Остаток по счету</td>
            </tr>
            <tr class="tblInfHeaderAbstrPrint">
                <td class="docTdBorder">По Дт<br/> счета</td>
                <td class="docTdBorder">По Кт<br/> счета</td>
            </tr>
                <c:set var="debitSummary" value= "0"/>
                <c:set var="creditSummary" value= "0"/>
                <c:forEach items="${resourceAbstract.transactions}" var="transaction">
                    <tr class="<c:if test="${not empty transaction.cardUseData}">sendingRecord</c:if>">
                        <td class="docTdBorderSecond textPadding" style="text-align:left;">
                            <c:if test="${!empty transaction.date}">
                                <bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
                            </c:if>&nbsp;
                        </td>
                        <td class="docTdBorder textPadding documentNumber" style="text-align:left;">
                            <c:if test="${!empty transaction.documentNumber}">
                                ${transaction.documentNumber}
                            </c:if>&nbsp;
                        </td>
                        <td class="docTdBorder textPadding" style="text-align:left;">
                            <c:if test="${!empty transaction.operationCode}">
                                ${transaction.operationCode}
                            </c:if>&nbsp;
                        </td>
                        <td class="docTdBorder textPadding" style="text-align:left;">
                            <c:choose>
                                <c:when test="${!empty transaction.description}">
                                    ${transaction.description}
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${!empty transaction.debitSum}">
                                            Списание
                                        </c:when>
                                        <c:otherwise>
                                            Зачисление
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>&nbsp;
                            <c:if test="${not empty transaction.cardUseData}">
                                <c:set var="cardUseData" value="${transaction.cardUseData}"/>
                                <input type="hidden" class="clientName" value="${cardUseData.clientName}"/>
                                <input type="hidden" class="cardNumber" value="${cardUseData.cardNumber}"/>
                                <c:set var="operationDate"><bean:write name='cardUseData' property="operationDate.time" format="dd.MM.yyyy"/></c:set>
                                <input type="hidden" class="operationDate" value="${operationDate}"/>
                                <input type="hidden" class="authCode" value="${cardUseData.authCode}"/>
                                <div class="clear"></div>
                                <div onclick="setWindowPosition(event);">
                                    <tiles:insert definition="clientButton" operation="CreatePrivateScanClaimOperation" flush="false">
                                        <tiles:put name="commandTextKey" value="button.send.to.email"/>
                                        <tiles:put name="commandHelpKey" value="button.send.to.email.help"/>
                                        <tiles:put name="viewType" value="linkWithImg"/>
                                        <tiles:put name="imageUrl" value="${globalUrl}/commonSkin/images/wordIcon.png"/>
                                        <tiles:put name="bundle"  value="sendClaimBundle"/>
                                        <tiles:put name="onclick" value="openWindow(this);"/>
                                    </tiles:insert>
                                </div>
                            </c:if>
                        </td>
                        <td class="docTdBorder textPadding" style="text-align:left;">
                            <c:if test="${!empty transaction.counteragentAccount}">
                                ${transaction.counteragentAccount}
                            </c:if>&nbsp;
                        </td>
                        <td class="docTdBorder textPadding" style="text-align:left;">
                            <c:if test="${!empty transaction.counteragent}">
                                ${transaction.counteragent}
                            </c:if>&nbsp;
                        </td>
                        <td class="docTdBorder textPadding debit" style="text-align:right;">
                            <span>
                            <c:if test="${!empty transaction.debitSum}">
                                <bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
                                <c:set var="debitSummary" value= "${debitSummary+transaction.debitSum.decimal}"/>
                            </c:if></span>&nbsp;
                        </td>
                        <td class="docTdBorder textPadding credit" style="text-align:right;">
                            <span>
                            <c:if test="${!empty transaction.creditSum}">
                                <bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
                                <c:set var="creditSummary" value= "${creditSummary+transaction.creditSum.decimal}"/>
                            </c:if></span>&nbsp;
                        </td>
                        <td class="docTdBorderRight textPadding" style="text-align:right;">
                            <c:if test="${!empty transaction.balance}">
                                <bean:write name="transaction" property="balance.decimal" format="0.00"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="6" style="text-align:right;font-weight:bold;" class="docTdLeftTopBorder">
                        Итого обороты:
                    </td>
                    <td style="text-align:right;font-weight:bold;" class="docTdTopBorder textPadding">
                        <bean:write name="debitSummary" format="0.00"/>
                    </td>
                    <td style="text-align:right;font-weight:bold;" class="docTdTopBorder textPadding">
                        <bean:write name="creditSummary" format="0.00"/>
                    </td>
                    <td class="docTdBoldBorder">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="6" style="text-align:right;font-weight:bold;border:none;">Исходящий
                        остаток:
                    </td>
                    <td style="text-align:right;border:none;font-weight:bold;">
                        <c:if test="${!empty resourceAbstract.closingBalance}">
                            <bean:write name="resourceAbstract" property="closingBalance.decimal" format="0.00"/>
                            <c:if test="${accountLink.currency.code != 'RUB'}">
                                <c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
                                <c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>
                                <c:set var="rate" value="${phiz:getRateByDepartment(accountLink.currency.code, 'RUB', 'CB', department, tarifPlanCodeType)}"/>
                                <c:set var="RurBalance"
                                       value="${phiz:getFormattedCurrencyRate(rate, 2) * resourceAbstract.closingBalance.decimal}"/>
                                &nbsp;/&nbsp;<bean:write name="RurBalance" format="0.00"/>&nbsp;RUB
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td class="needBorder" style="padding-top:7mm;">
            <table cellpadding="0" cellspacing="0" style="width:100%;">
            <tr>
                <td class="labelAbstractPrint">
                    Выписка составлена по запросу:&nbsp;
                    <c:if test="${not empty user}">
                        <span class="bold" style="text-transform:uppercase;"><c:out value="${phiz:getFormattedPersonName(user.firstName, user.surName, user.patrName)}"/></span>
                    </c:if>
                </td>
                <td class="abstractContainer NotPrintable">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print.transactions"/>
                        <tiles:put name="commandHelpKey" value="button.print.transactions.help"/>
                        <tiles:put name="bundle" value="accountInfoBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                        <tiles:put name="onclick">window.print();</tiles:put>
                    </tiles:insert>
                </td>
            </tr>
            <tr>
                <td class="labelAbstractPrint">
                    Дата составления выписки:&nbsp;
                    <c:set var="date" value="${phiz:currentDate()}"/>
                    <span class="bold">
                        <bean:write name="date" property="time" format="dd.MM.yyyy"/>
                    </span>
                </td>
            </tr>
            <c:if test="${!empty office.code.fields[office]}">
                <tr>
                    <td class="labelAbstractPrint">
                        Выписка выдана стуркутрным подразделением&nbsp;
                        <span class="bold">
                        <fmt:parseNumber var="branch" value="${office.code.fields[branch]}"/>
                        <fmt:parseNumber var="office" value="${office.code.fields[office]}"/>
                        № <bean:write name="branch" format="0000"/>/<bean:write name="office" format="00000"/>
                        </span>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td class="labelAbstractPrint">
                    <bean:message bundle="commonBundle" key="text.SBOL.executor"/>
                    <span class="bold"><bean:message bundle="commonBundle" key="text.SBOL.formedIn"/></span>
                </td>
            </tr>
            </table>
        </td>
    </tr>
</table>