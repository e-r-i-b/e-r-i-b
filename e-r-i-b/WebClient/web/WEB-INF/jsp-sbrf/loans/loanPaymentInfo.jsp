<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html:form action="/private/loans/payment/info" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="loanLink" value="${form.loanLink}" scope="request"/>
<c:set var="loan" value="${loanLink.loan}" scope="request"/>
<c:set var="scheduleItem" value="${form.scheduleItem}" scope="request"/>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="Loans"/>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Кредиты"/>
            <tiles:put name="action" value="/private/loans/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:write name="loanLink" property="name"/> ${phiz:formatAmount(loanLink.loan.loanAmount)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
     </tiles:put>
    <c:if test="${not empty form.loanLink}">

         <tiles:put name="data" type="string">
            <div id="loans">
                <div id="loan-detail-payment">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title">
                            Платеж по кредиту от ${phiz:formatDateWithStringMonth(scheduleItem.date)}
                        </tiles:put>
                        <tiles:put name="data">                            
                        <tiles:insert definition="productTemplate" flush="false">
                            <c:choose>
                                <c:when test="${loan.isAnnuity}">
                                    <tiles:put name="img" value="${imagePath}/credit_type/icon_creditAnuitet.jpg"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="img" value="${imagePath}/credit_type/icon_creditDiffer.jpg"/>
                                </c:otherwise>
                            </c:choose>

                            <tiles:put name="alt" value="Платеж по кредиту"/>
                            <tiles:put name="title">
                                <bean:write name="loanLink" property="name"/>
                            </tiles:put>
                            <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>

                            <tiles:put name="productNumbers">
                                <div class="loanClientType loanDetailType">Роль:&nbsp;${loanLink.personRole.description}</div>
                            </tiles:put>

                            <tiles:put name="leftData">
                                <c:if test="${loan.state == 'closed'}">
                                    <span class="detailStatus">${loan.state.description}</span>
                                    <br/><br/>
                                </c:if>

                                <c:if test="${loan.rate != null}">
                                    Ставка по кредиту:&nbsp;<span class="bold">${loan.rate}</span>%
                                    <br/>
                                </c:if>

                                <c:if test="${scheduleItem.paymentState == 'current' || scheduleItem.paymentState == 'future'}">
                                    Внести до&nbsp;<span class="bold"><fmt:formatDate value="${loan.nextPaymentDate.time}" pattern="dd.MM.yyyy"/></span>
                                    <br/>
                                </c:if>

                                Сумма кредита:&nbsp;<span class="bold">${phiz:formatAmount(loan.loanAmount)}</span>
                            </tiles:put>

                            <tiles:put name="centerData">
                                <span class="detailAmount">${phiz:formatAmount(scheduleItem.totalPaymentAmount)}</span>
                                <br/>
                                <span class="amountStatus">Рекомендованный платеж</span>
                            </tiles:put>

                            <c:choose>
                                <c:when test="${scheduleItem.paymentState == 'paid'}">
                                    <tiles:put name="status" value="inActive"/>
                                </c:when>
                                <c:when test="${scheduleItem.paymentState == 'fail'}">
                                    <tiles:put name="status" value="error"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="status" value="active"/>
                                </c:otherwise>
                            </c:choose>
                        </tiles:insert>

                        <%--Вывод постатейной разбивки по платежу--%>
                        <c:set var="debt" value="${null}"/>
                        <c:forEach var="listElement" items="${scheduleItem.penaltyDateDebtItemMap}" varStatus="lineInfo">
                            <c:set var="debt" value="${phiz:getMoneyOperation(debt, listElement.value.amount, '+')}"/>
                        </c:forEach>

                        <table class="tblInf">
                            <tr class="ListLine0">
                                <td class="align-left">
                                    Сумма основного долга
                                </td>
                                <td class="align-right bold">
                                    ${phiz:formatAmount(scheduleItem.principalAmount)}
                                </td>
                            </tr>
                            <tr class="ListLine1">
                                <td class="align-left">
                                    Проценты
                                </td>
                                <td class="align-right bold">
                                    ${phiz:formatAmount(scheduleItem.interestsAmount)}
                                </td>
                            </tr>
                            <c:if test="${debt != null}">
                                <tr class="ListLine1">
                                    <td class="align-left">
                                        Пени и штрафы
                                    </td>
                                    <td class="align-right bold">
                                        ${phiz:formatAmount(debt)}
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${debt != null}">
                                <c:forEach var="listElement" items="${scheduleItem.penaltyDateDebtItemMap}" varStatus="lineInfo">
                                    <c:if test="${listElement.value.amount != null && phiz:formatAmount(listElement.value.amount) != '0,00 р.'}">
                                    <tr class="ListLine1">
                                        <td>
                                            <div style="margin-left:50px">
                                                ${listElement.key.description}
                                            </div>
                                        </td>
                                        <td class="align-right">
                                            ${phiz:formatAmount(listElement.value.amount)}
                                        </td>
                                    </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <tr class="ListLine1 last">
                                <td class="align-left bold">
                                    Итого
                                </td>
                                <td class="align-right bold">
                                    ${phiz:formatAmount(scheduleItem.totalPaymentAmount)}
                                </td>
                            </tr>
                            <c:if test="${not empty scheduleItem.overpayment}">
                                <tr class="ListLine1 last">
                                    <td class="align-left bold">
                                        Переплата
                                    </td>
                                    <td class="align-right bold">
                                        ${phiz:formatAmount(scheduleItem.overpayment)}
                                    </td>
                                </tr>
                            </c:if>
                        </table>

                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                               <tiles:put name="commandTextKey" value="button.payment.print"/>
                               <tiles:put name="commandHelpKey" value="button.payment.print.help"/>
                               <tiles:put name="bundle"         value="loansBundle"/>
                               <tiles:put name="typeBtn"            value="dictBtn"/>
                               <tiles:put name="onclick">printLoanInfo(event)</tiles:put>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>

                        <div class="backToService">
                           <tiles:insert definition="clientButton" flush="false">
                               <tiles:put name="commandTextKey" value="button.payment.back"/>
                               <tiles:put name="commandHelpKey" value="button.payment.back.help"/>
                               <tiles:put name="bundle"         value="loansBundle"/>
                               <tiles:put name="onclick"        value="backToLoanInfo();"/>
                               <tiles:put name="viewType"       value="blueGrayLink"/>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </div>
        </tiles:put>
    </c:if>
</tiles:insert>
<script type="text/javascript">
var url = "${phiz:calculateActionURL(pageContext,'/private/loans/info')}";
var printInfoUrl = "${phiz:calculateActionURL(pageContext,'/private/loans/payment/info/print')}";
    function backToLoanInfo()
    {
        window.location = url + "?id="+${loanLink.id};
    }
    function printLoanInfo(event)
	{
            var querystring = document.location.href;
            querystring = querystring.split("?");
            openWindow(event, printInfoUrl + "?" + querystring[1] , "PrintLoanInfo", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
	}
</script>
</html:form>
