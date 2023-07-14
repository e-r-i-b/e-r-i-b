<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>


<html:form action="/private/pfr/pfrHistoryFullPrint">

    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <tiles:importAttribute/>
    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="accountAbstract" value="${form.accountAbstract}"/>
            <c:set var="account" value="${form.account}"/>
            <c:set var="card" value="${form.card}"/>
            <c:set var="client" value="${form.client}"/>
            <c:set var="clientFIO" value="${phiz:getFormattedPersonName(client.firstName, client.surName, client.patrName)}"/>

            <c:choose>
                <c:when test="${empty accountAbstract or accountAbstract == null or accountAbstract.transactions == null or fn:length(accountAbstract.transactions) == 0}">
                    <table width="100%">
                        <tr>
                            <td align="center" class="messageTab"><br>
                                Зачислений от Пенсионного Фонда России за указанный период не обнаружено
                            </td>
                        </tr>
                    </table>
                </c:when>
                <c:otherwise>

                    <table style="width:90%; margin: 0px 50px 0px 50px;">
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 50px;">
                                <c:choose>
                                    <c:when test="${not empty account}">
                                        ${account.office.name} <c:if test="${not empty office.code}"> № <bean:write name="office" property="code.fields(branch)"/>/<bean:write name="office"
                                                                                                                                                                               property="code.fields(office)"/></c:if>
                                    </c:when>
                                    <c:otherwise>
                                        ${card.office.name} <c:if test="${not empty office.code}"> № <bean:write name="office" property="code.fields(branch)"/>/<bean:write name="office"
                                                                                                                                                                            property="code.fields(office)"/></c:if>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 50px;text-transform:uppercase;font-weight:bold;">
                                Справка
                            </td>
                        </tr>

                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 30px;font-weight:bold;">
                                о видах и размерах пенсий и других социальных выплат,<br/>
                                зачисленных на счет в ОАО «Сбербанк России»<br/>
                                за период с <c:out value="${phiz:сalendarToString(accountAbstract.fromDate)}"/> по <c:out value="${phiz:сalendarToString(accountAbstract.toDate)}"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 30px;">
                                <c:out value="${phiz:сalendarToString(accountAbstract.closedDate)}"/>
                            </td>
                        </tr>

                        <tr>
                            <td style="font-size:12pt;text-align:left;padding-top: 40px;">
                                <c:choose>
                                    <c:when test="${not empty account}">
                                        Счет № <c:out value="${phiz:getFormattedAccountNumber(account.number)}"/>
                                    </c:when>
                                    <c:otherwise>
                                        Карта № <c:out value="${phiz:getCutCardNumber(card.number)}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>

                        <tr>
                            <td style="font-size:12pt;text-align:left;padding-top: 20px;">
                                ФИО владельца счета: <c:out value="${clientFIO}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="borderedTable" style="padding-top: 40px;">
                                <table cellpadding="0" cellspacing="0" style="width:100%; font-size:12pt;">
                                    <tr class="tblInfHeaderAbstrPrint">
                                        <td class="docTdLeftTopBorder" style="width:25%;text-align:center;"">Сумма пенсии и других социальных выплат по видам выплат
                                            (руб.)
                                        </td>
                                        <td class="docTdTopBorder" style="width:25%;text-align:center;"">Дата выплаты</td>
                                        <td class="docTdTopBorder" style="width:25%;text-align:center;"">Вид выплаты</td>
                                        <td class="docTdTopBorder" style="width:25%;text-align:center;"">Наименование организации-плательщика</td>
                                    </tr>

                                    <c:set var="summary" value="0"/>
                                    <c:forEach items="${accountAbstract.transactions}" var="transaction">
                                        <tr>
                                            <td class="docTdBorderSecond textPadding" style="text-align:center;">
                                                <c:choose>
                                                    <c:when test="${!empty transaction.debitSum}">
                                                        <c:set var="operationSumm" value="${transaction.debitSum}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="operationSumm" value="${transaction.creditSum}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:if test="${!empty operationSumm}">
                                                    <c:out value="${phiz:formatDecimalToAmountWithCustomSeparator(operationSumm.decimal, 2, '.')}"/>
                                                    <c:set var="summary" value="${summary+operationSumm.decimal}"/>
                                                </c:if>&nbsp;
                                            </td>
                                            <td class="docTdBorder textPadding" style="text-align:center;">
                                                <c:if test="${!empty transaction.date}">
                                                    <bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
                                                </c:if>&nbsp;
                                            </td>
                                            <td class="docTdBorder textPadding" style="text-align:center;">
                                                <c:if test="${!empty transaction.operationCode}">
                                                    ${transaction.operationCode}
                                                </c:if>&nbsp;
                                            </td>
                                            <td class="docTdBorder textPadding" style="text-align:center;">
                                                ПФР&nbsp;
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 40px;">
                                Итоговая сумма пенсии и других социальных выплат, зачисленная на счет за период:
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 20px;">
                                <c:out value="${phiz:formatDecimalToAmountWithCustomSeparator(summary, 2, '.')}"/>&nbsp;(<c:out value="${phiz:sumInWords(summary, 'RUB')})"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 50px;font-weight:bold;">
                                По вопросам начисления сумм пенсий и других социальных выплат обращайтесь в территориальный орган ПФР по месту осуществления пенсионного обеспечения.
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:center;padding-top: 50px;font-weight:bold;">
                                Спасибо!
                            </td>
                        </tr>

                        <tr>
                            <td style="font-size:12pt;text-align:left;padding-top: 50px;">
                                Справка составлена по запросу: <span class="bold"><c:out value="${clientFIO}"/></span>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:left;padding-top: 10px;">
                                Дата составления справки: <span class="bold"><c:out value="${phiz:сalendarToString(accountAbstract.closedDate)}"/></span>
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size:12pt;text-align:left;padding-top: 10px;">
                                <bean:message bundle="commonBundle" key="text.SBOL.executor"/><span class="bold"><bean:message bundle="commonBundle" key="text.SBOL.formedIn"/></span>
                            </td>
                        </tr>
                    </table>
                    <div class="abstractContainer NotPrintable">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.print.ordering"/>
                            <tiles:put name="commandHelpKey" value="button.print.transactions.help"/>
                            <tiles:put name="bundle" value="accountInfoBundle"/>
                            <tiles:put name="viewType" value="simpleLink"/>
                            <tiles:put name="onclick">window.print();</tiles:put>
                        </tiles:insert>
                    </div>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>