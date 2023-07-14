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

<c:set var="curDate" value="${phiz:currentDate()}"/>
<c:set var="user" value="${form.user}"/>

<tiles:insert definition="tableTemplate" flush="false">
  <tiles:put name="text" value="Выписка по счетам"/>
  <tiles:put name="cleanText" value="true"/>
  <tiles:put name="data" type="string">
        <tr class="noBorder">
            <td>
                <table class="font10" width="100%">
                    <tr>
                        <td><img src="${globalImagePath}/miniLogoSbrf.jpg"/>
                        </td>
                        <td align="left" width="30%">
                            <nobr>Сбербанк&nbsp;России</nobr>
                        </td>
                        <td align="right" width="70%">
                            <nobr>Ф. № 204-c</nobr>
                        </td>
                    </tr>
                </table>
            <tr>
                <td>
                    ${accountLink.office.name}
                </td>
            </tr>
            <tr>
                <td>
                    № ${accountLink.office.code.fields[branch]}/${accountLink.office.code.fields[office]}
                </td>
            </tr>
        <tr class="noBorder">
            <td align="center" style="font-size:11pt;">
                Выписка из лицевого счета по вкладу
            </td>
        </tr>
        <tr class="noBorder">
            <td>
                <br/><br/>
                <br/><br/>
            </td>
        </tr>
        <tr class="noBorder">
            <td class="needBorder">
                <table cellpadding="0" cellspacing="0" width="100%" style>
                <tr>
                    <td class="labelAll">
                        Номер лицевого счета: №
                    </td>
                    <td>
                        ${phiz:getFormattedAccountNumber(accountLink.number)}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Цель депозита:
                    </td>
                    <td>
                        Получение дохода в виде процентов, начисляемых на сумму размещенных денежных средств.
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Номер договора:
                    </td>
                    <td>
                        ${account.agreementNumber}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Дата заключения договора:
                    </td>
                    <td>
                        <bean:write name="account" property="openDate.time" format="dd.MM.yyyy"/>
                    </td>
                </tr>
                <tr>
                    <xsl:variable name="curCode" select="currencyCode"/>
                    <td class="labelAll">
                        Вклад:
                    </td>
                    <td>
                        ${accountLink.description} в ${accountLink.currency.code}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                         Срок погашения депозита (срок вклада):
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${empty account.closeDate}">
                                    до востребования
                            </c:when>
                            <c:otherwise>
                                    ${phiz:formatDateWithStringMonth(account.closeDate)} г.
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Размер процентной ставки:
                    </td>
                    <td>
                        ${account.interestRate}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Вкладчик:
                    </td>
                    <td>
                        ${accountLink.accountClient.fullname}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Выписка составлена за период
                    </td>
                    <td>
                        с <bean:write name="resourceAbstract" property="fromDate.time" format="dd.MM.yyyy"/> по <bean:write name="resourceAbstract" property="toDate.time" format="dd.MM.yyyy"/>
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Дата предыдущей операции по счету:
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty resourceAbstract.previousOperationDate}">
                                ${phiz:formatDateWithStringMonth(resourceAbstract.previousOperationDate)} г.
                            </c:when>
                            <c:otherwise>
                                &mdash;
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Входящий остаток на <bean:write name="curDate" property="time" format="dd.MM.yyyy"/>:
                    </td>
                    <td>
                        <bean:write name="resourceAbstract" property="openingBalance.decimal" format="0.00"/>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        <tr class="noBorder"><td><br/><br/><br/>&nbsp;</td></tr>
        <tr class="noBorder">
            <td class="needBorder">
                <table cellpadding="0" cellspacing="0">
                <tr class="tblInfHeader">
                    <td width="11%" rowspan="2">Дата совершения операции</td>
                    <td width="11%" rowspan="2">Номер документа</td>
                    <td width="10%" rowspan="2">Вид ( шифр) операции</td>
                    <td width="15%" rowspan="2">Наименование операции</td>
                    <td width="15%" rowspan="2">Номер корреспондирующего счета</td>
                    <td width="25%" colspan="2">Сумма операции</td>
                    <td rowspan="2">Остаток по счету</td>
                </tr>
                <tr class="tblInfHeader">
                    <td>По Дт<br/> счета</td>
                    <td>По Кт<br/> счета</td>
                </tr>
                    <c:set var="debitSummary" value= "0"/>
                    <c:set var="creditSummary" value= "0"/>
                    <c:forEach items="${resourceAbstract.transactions}" var="transaction">
                        <tr>
                            <td class="docTdBorderSecond textPadding" align="center">
                                <c:if test="${!empty transaction.date}">
                                    <bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.documentNumber}">
                                    ${transaction.documentNumber}
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.operationCode}">
                                    ${transaction.operationCode}
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.description}">
                                    ${transaction.description}
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.counteragentAccount}">
                                    ${transaction.counteragentAccount}
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.debitSum}">
                                    <bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
                                    <c:set var="debitSummary" value= "${debitSummary+transaction.debitSum.decimal}"/>
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.creditSum}">
                                    <bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
                                    <c:set var="creditSummary" value= "${creditSummary+transaction.creditSum.decimal}"/>
                                </c:if>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:if test="${!empty transaction.balance}">
                                    <bean:write name="transaction" property="balance.decimal" format="0.00"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                <xsl:apply-templates select="row" mode="f204"/>
                <tr>
                    <td colspan="2" align="center">Итого обороты</td>
                    <td align="center">&nbsp;</td>
                    <td align="center">&nbsp;</td>
                    <td align="center">&nbsp;</td>
                    <td align="right" class="textPadding"><bean:write name="debitSummary" format="0.00"/> </td>
                    <td align="right" class="textPadding"><bean:write name="creditSummary" format="0.00"/> </td>
                    <td align="center">&nbsp;</td>
                </tr>
                </table>
            </td>
        </tr>
        <tr class="noBorder"><td><br/><br/><br/>&nbsp;</td></tr>
        <tr class="noBorder">
            <td class="needBorder">
                <table cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td class="labelAll">
                        Исходящий остаток:
                    </td>
                    <td>
                        <bean:write name="resourceAbstract" property="closingBalance.decimal" format="0.00"/>
                        <c:if test="${accountLink.currency.code != 'RUB'}">
                            <c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
                            <c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>
                            <c:set var="RurBalance" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB', accountLink.currency.code, 'CB', department, tarifPlanCodeType), 2) * resourceAbstract.closingBalance.decimal}"/>
                            &nbsp;/&nbsp;<bean:write name="RurBalance" format="0.00"/>&nbsp;RUB
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Выписка составлена по запросу:
                    </td>
                    <td>
                        ${user.fullName}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Дата составления выписки:
                    </td>
                    <td>
                        <c:set var="date" value="${phiz:currentDate()}"/>
                        <bean:write name="date" property="time" format="dd.MM.yyyy"/>
                    </td>
                </tr>
                <c:if test="${!empty codeFields.office}">
                    <tr>
                        <td class="labelAll">
                            Выписка выдана стуркутрным подразделением
                        </td>
                        <td>
                            <%--<fmt:parseNumber var="branch" value="${codeFields.branch}"/>--%>
                            <%--<fmt:parseNumber var="office" value="${codeFields.office}"/>--%>
                            № ${codeFields.branch}/${codeFields.office}
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="labelAll">
                        <bean:message bundle="commonBundle" key="text.SBOL.executor"/>
                    </td>
                    <td>
                        <bean:message bundle="commonBundle" key="text.SBOL.formedIn"/>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
    </tiles:put>
</tiles:insert>