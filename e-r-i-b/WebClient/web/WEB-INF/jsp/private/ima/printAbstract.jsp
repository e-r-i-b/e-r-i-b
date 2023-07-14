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

<html:form action="/private/ima/printAbstract">

    <tiles:importAttribute/>
    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">
            <style type="text/css">
                .tablePayments {
                    font-family: The Times Roman;
                    text-align: left;
                    font-size: 12px;
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
            </style>
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="imAccountLink" value="${form.imAccountLink}" scope="page"/>
            <c:set var="imAccount" value="${imAccountLink.imAccount}" scope="page"/>
            <c:set var="imAccountAbstract" value="${form.imAccountAbstract}" scope="page"/>

            <c:set var="model"    value="no-pagination"/>
            <c:set var="hideable" value="false"/>
            <c:set var="recCount" value="${phiz:size(imAccountAbstract.transactions)}"/>

            <p align="center" style="font-size:11pt; text-align:center; padding:20px 0 10px 0;">
                <span>Последние операции по счету № ${phiz:getFormattedAccountNumber(imAccountLink.number)}</span>
            </p>
            <c:if test="${not empty imAccountAbstract && not empty imAccountAbstract.transactions}">

                <table class="tablePayments" cellspacing="0px">
                    <tr>
                        <td><bean:message key="label.operaationDate" bundle="imaBundle"/></td>
                        <td><bean:message key="label.metal" bundle="imaBundle"/></td>
                        <td><bean:message key="label.grammesAmount" bundle="imaBundle"/></td>
                        <td><bean:message key="label.operationSumm" bundle="imaBundle"/></td>
                    </tr>
                    <c:forEach var="transaction" items="${imAccountAbstract.transactions}">
                        <tr>
                            <c:if test="${not empty transaction.correspondentAccount}">
                                <td>
                                        ${transaction.correspondentAccount}
                                </td>
                            </c:if>
                            <%--Дата операции--%>
                            <c:if test="${not empty transaction.date}">
                                <td>
                                        ${phiz:formatDateWithStringMonth(transaction.date)}&nbsp;
                                </td>
                            </c:if>
                            <%--Металл--%>
                            <c:if test="${not empty imAccount.currency}">
                                <td>
                                        ${imAccount.currency.name}&nbsp;(${phiz:normalizeCurrencyCode(imAccount.currency.code)})
                                </td>
                            </c:if>
                            <%--Объем операции--%>
                            <td>
                                <c:choose>
                                    <c:when test="${!empty transaction.creditSumInPhizicalForm && transaction.creditSumInPhizicalForm.decimal > 0.00}">
                                        -${phiz:formatAmount(transaction.creditSumInPhizicalForm)}
                                    </c:when>
                                    <c:when test="${!empty transaction.debitSumInPhizicalForm && transaction.debitSumInPhizicalForm.decimal > 0.00}">
                                        +${phiz:formatAmount(transaction.debitSumInPhizicalForm)}
                                    </c:when>
                                    <%--TODO Отображать пока не появится полная выписка--%>
                                    <c:when test="${!empty transaction.creditSum && transaction.creditSum.decimal > 0.00}">
                                        -${phiz:formatAmount(transaction.creditSum)}
                                    </c:when>
                                    <c:when test="${!empty transaction.debitSum && transaction.debitSum.decimal > 0.00}">
                                        +${phiz:formatAmount(transaction.debitSum)}
                                    </c:when>
                                </c:choose>
                            </td>
                            <%--Сумма операции--%>
                            <c:set var="operationRurSumm" value="${transaction.operationRurSumm}"/>
                            <c:if test="${not empty operationRurSumm}">
                                <td>
                                    <c:choose>
                                    <c:when test="${operationRurSumm.decimal != '0.00'}">
                                        ${phiz:formatAmount(operationRurSumm)}
                                        <c:if test="${transaction.operationType == 'CS_METAL'}">&nbsp;(слиток)</c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <div align="center" >
                                            &mdash;
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
                <div>
                    «*» - знак «+» означает зачисление металла на ОМС, знак «-» - списание металла с ОМС.
                </div>
                <div class="abstractContainer NotPrintable">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.abstract.print"/>
                            <tiles:put name="commandHelpKey" value="button.abstract.print"/>
                            <tiles:put name="bundle" value="imaBundle"/>
                            <tiles:put name="viewType" value="simpleLink"/>
                            <tiles:put name="onclick">window.print();</tiles:put>
                        </tiles:insert>
                </div>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
