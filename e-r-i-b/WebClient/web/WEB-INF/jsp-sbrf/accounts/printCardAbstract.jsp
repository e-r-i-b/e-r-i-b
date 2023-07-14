<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<html:form action="/private/cards/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="cardAbstract" value="${form.cardAbstract}" scope="request"/>

    <tiles:insert definition="printDoc">
        <tiles:put name="pageTitle" value="../../sbrfHeader.jsp"/>
        <tiles:put name="data" type="string">
            <div style="font-size:11pt;text-align:center;padding-top:20px;margin : 5px;">
                <c:choose>
                    <c:when test="${not empty cardAbstract && not empty cardAbstract.transactions}">
                        Последние операции по карте № ${phiz:getCutCardNumber(form.cardLink.number)}
                        <sl:collection id="transaction" model="no-pagination" name="cardAbstract"
                                       property="transactions">
                            <sl:collectionItem>
                                <c:if test="${not empty transaction.description}">
                                    ${transaction.description}
                                </c:if>
                                <c:if test="${not empty transaction.shopInfo}">
                                    ${transaction.shopInfo}
                                </c:if>
                            </sl:collectionItem>
                            <sl:collectionItem>
                                ${phiz:formatDateWithStringMonth(transaction.date)}&nbsp;
                                <bean:write name='transaction' property="date.time" format="HH:mm"
                                            ignore="true"/>
                            </sl:collectionItem>
                            <sl:collectionItem>
                                <%--Если creditSum 0, а accountCreditSum не ноль, то выводим accountCreditSum.
                                                                                                                            Аналогично для debitSum и  accountDebitSum.--%>
                                <c:choose>
                                    <c:when test="${(empty transaction.creditSum || transaction.creditSum.decimal == 0) && !empty transaction.accountCreditSum && transaction.accountCreditSum.decimal!=0}">
                                        +${phiz:formatAmount(transaction.accountCreditSum)}
                                    </c:when>
                                    <c:when test="${!empty transaction.creditSum && transaction.creditSum.decimal != 0}">
                                        +${phiz:formatAmount(transaction.creditSum)}
                                    </c:when>
                                    <c:when test="${(empty transaction.debitSum || transaction.debitSum.decimal == 0) && !empty transaction.accountDebitSum && transaction.accountDebitSum.decimal!=0}">
                                        -${phiz:formatAmount(transaction.accountDebitSum)}
                                    </c:when>
                                    <c:when test="${!empty transaction.debitSum && transaction.debitSum.decimal != 0}">
                                        -${phiz:formatAmount(transaction.debitSum)}
                                    </c:when>
                                </c:choose>
                            </sl:collectionItem>
                        </sl:collection>
                    </c:when>
                    <c:otherwise>
                        По данной карте не найдено ни одной операции.
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="abstractContainer NotPrintable">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.print.abstract"/>
                    <tiles:put name="commandHelpKey" value="button.print.abstract.help"/>
                    <tiles:put name="bundle" value="cardInfoBundle"/>
                    <tiles:put name="viewType" value="simpleLink"/>
                    <tiles:put name="onclick">window.print();</tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>