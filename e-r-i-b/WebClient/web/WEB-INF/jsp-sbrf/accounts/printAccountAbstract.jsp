<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:importAttribute/>

<html:form action="/private/accounts/printAcc">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="accountAbstract" value="${form.accountAbstract}"/>
    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">
            <c:if test="${not empty accountAbstract}">
                <script type="text/javascript">
                function convertSum(decimal)
                    {
                        var len = decimal.length;
                        document.write(decimal.substring(0,len-3) + "&nbsp;руб." + decimal.substring(len-2,len) + "коп.");
                    }
                </script>

                  <tabel>
                    <tr  class="labelAbstractPrint">
                            <td>${form.accountLink.office.name}</td>
                    </tr>
                    <c:if test="${not empty form.accountLink.office.code}">
                        <tr class="labelAbstractPrint">
                               <td>№<bean:write name="form" property="accountLink.office.code.fields(branch)"/>/<bean:write name="form" property="accountLink.office.code.fields(office)"/></td>
                        </tr>
                    </c:if>
                    <tr>
                         <td><div style="font-size:11pt;text-align:center;padding-top:20px; margin-bottom: 10px;">Выписка по вкладу ${form.accountLink.description} в «Сбербанк России ОАО»</div></td>
                    </tr>
                  </tabel>
                  <table cellpadding="0" cellspacing="0" class="accountOperationsAbstract">
                    <tr class="tblInfHeaderAbstrPrint">
                        <td rowspan="1" style="width:10%;">Наименование операции</td>
                        <td rowspan="1" style="width:10%;">Дата совершения операции</td>
                        <td rowspan="1" style="width:10%;">Сумма операции</td>
                        <td rowspan="1" style="width:15%;">Наименование получателя/плательщика</td>
                    </tr>
                    <c:if test="${accountAbstract != null && accountAbstract.transactions != null}">
                        <c:forEach items="${accountAbstract.transactions}" var="transaction">
                            <tr>
                                <td style="text-align:left;">
                                    <c:choose>
                                        <c:when test="${!empty transaction.description}">
                                            <c:out value="${transaction.description}"/>
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
                                </td>
                                <td style="text-align:left;">
                                    <c:if test="${!empty transaction.date}">
                                        <bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
                                    </c:if>&nbsp;
                                </td>
                                <td>
                                    <c:set var="debit"
                                           value="${(transaction.debitSum!=null and transaction.debitSum.asCents!=0) ? transaction.debitSum.decimal : null}"/>
                                    <c:set var="credit"
                                           value="${(transaction.creditSum!=null and transaction.creditSum.asCents!=0) ? transaction.creditSum.decimal : null}"/>
                                    <c:choose>
                                        <c:when test="${!empty debit and !empty credit}">
                                            <bean:write name="debit" format="0.00"/>
                                            &nbsp;
                                            <bean:write name="credit" format="0.00"/>
                                        </c:when>
                                        <c:when test="${!empty debit}">
                                            <bean:write name="debit" format="0.00"/>
                                        </c:when>
                                        <c:when test="${!empty credit}">
                                            <bean:write name="credit" format="0.00"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="0" pattern="0.00"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="text-align:left;">
                                    <c:if test="${!empty transaction.recipient}">
                                        <c:out value="${transaction.recipient}"/>
                                    </c:if>&nbsp;
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </table>
                <div class="abstractContainer NotPrintable">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.print.transactions"/>
                        <tiles:put name="commandHelpKey" value="button.print.transactions.help"/>
                        <tiles:put name="bundle" value="accountInfoBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                        <tiles:put name="onclick">window.print();</tiles:put>
                    </tiles:insert>
                </div>
                <table class="total" style="margin-top: 10px;">
                    <tr>
                        <td class="labelAbstractPrint">
                            Исходящий остаток:&nbsp;
                            <c:set var="clBalance" value="${accountAbstract.closingBalance.decimal}"/>
                            <c:if test="${not empty clBalance}">
                                <c:choose>
                                    <%--Для рублёвых вкладов--%>
                                    <c:when test="${form.accountLink.currency.number == 643}">
                                        <script type="text/javascript">
                                            convertSum('${clBalance}');
                                        </script>
                                    </c:when>
                                   <%--Для валютных--%>
                                    <c:otherwise>
                                        ${phiz:formatAmount(accountAbstract.closingBalance)}
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>

                    <tr>
                        <td class="labelAbstractPrint">
                            ФИО клиента:&nbsp;
                            <c:set var="accountClient" value="${form.accountLink.accountClient}"/>
                            <c:if test="${not empty accountClient}">
                                <c:out value="${phiz:getFormattedPersonName(accountClient.firstName, accountClient.surName, accountClient.patrName)}"/>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="labelAbstractPrint">
                            Дата распечатки документа:&nbsp;
                            <c:set var="date" value="${phiz:currentDate()}"/>
                            <bean:write name="date" property="time" format="dd.MM.yyyy"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="labelAbstractPrint">
                            <bean:message bundle="commonBundle" key="text.SBOL.executor"/>
                            <bean:message bundle="commonBundle" key="text.SBOL.formedIn"/>
                        </td>
                    </tr>
                </table>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>