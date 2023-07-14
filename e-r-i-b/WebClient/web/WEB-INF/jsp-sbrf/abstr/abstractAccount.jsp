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
                            <nobr>Ф. № 319</nobr>
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
                Выписка из лицевого счета
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
                        Номер договора:
                    </td>
                    <td>
                        ${account.agreementNumber}
                    </td>
                </tr>
                <tr>
                    <td class="labelAll">
                        Дата договора:
                    </td>
                    <td>
                        ${phiz:formatDateWithStringMonth(account.openDate)} г.
                    </td>
                </tr>
                <tr>
                    <xsl:variable name="curCode" select="currencyCode"/>
                    <td class="labelAll">
                        Счет:
                    </td>
                    <td>
                        ${accountLink.description} в ${accountLink.currency.code}
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
                        Владелец:
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
                        с ${phiz:formatDateWithStringMonth(resourceAbstract.fromDate)} г.по ${phiz:formatDateWithStringMonth(resourceAbstract.toDate)} г.
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
                        Входящий остаток:
                    </td>
                    <td>
                        <bean:parameter id="opBalance" name="opBalance" value="${resourceAbstract.openingBalance.decimal}"/>
                        <%
                            int opLength = opBalance.length();
                            String absOpBalance = opBalance.substring(0, opLength-3);
                            String drobOpBalance = opBalance.substring(opLength-2, opLength);
                        %>
                        <%=absOpBalance%>&nbsp;руб.&nbsp;<%=drobOpBalance%>&nbsp;коп.

                        на&nbsp;${phiz:formatDateWithStringMonth(phiz:currentDate())} г.
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
                                <c:choose>
                                    <c:when test="${!empty transaction.debitSum}">
                                        <bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
                                        <c:set var="debitSummary" value= "${debitSummary+transaction.debitSum.decimal}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber value="0" pattern="0.00"/>
                                    </c:otherwise>
                                </c:choose>&nbsp;
                            </td>
                            <td class="docTdBorder textPadding" align="center">
                                <c:choose>
                                    <c:when test="${!empty transaction.creditSum}">
                                        <bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
                                        <c:set var="creditSummary" value= "${creditSummary+transaction.creditSum.decimal}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatNumber value="0" pattern="0.00"/>
                                    </c:otherwise>
                                </c:choose>&nbsp;
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
                        <bean:parameter id="clBalance" name="clBalance" value="${resourceAbstract.closingBalance.decimal}"/>
                        <%
                            int clLength = clBalance.length();
                            String absClBalance = clBalance.substring(0, clLength-3);
                            String drobClBalance = clBalance.substring(clLength-2, clLength);
                        %>
                        <%=absClBalance%>&nbsp;руб.&nbsp;<%=drobClBalance%>&nbsp;коп.
                    </td>
                </tr>
                </table>
            </td>
        </tr>
    </tiles:put>
</tiles:insert>