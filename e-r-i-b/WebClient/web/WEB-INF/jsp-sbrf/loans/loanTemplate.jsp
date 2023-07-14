<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>

<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<c:if test="${detailsPage}">
    <script type="text/javascript">
        var url = "${phiz:calculateActionURL(pageContext,'/private/loans/payment/info')}";
        var printInfoUrl = "${phiz:calculateActionURL(pageContext,'/private/loans/info/print')}";
        var printPaymentsUrl = "${phiz:calculateActionURL(pageContext,'/private/loans/payments/print')}";

        function viewPayment(row)
        {
            var nodes = row.cells[0].childNodes;

            var paymentNumber = nodes[0].nodeValue;
            var startNumber = getElementValue('field(startNumber)');
            var count = getElementValue('field(count)');
            window.location = url + "?id=" + ${loanLink.id} + "&paymentNumber=" + paymentNumber +
                              "&startNumber=" + startNumber + "&count=" + count;
        }

        function printLoanInfo(event)
        {
            openWindow(event, printInfoUrl + "?id=" +${loanLink.id}, "PrintLoanInfo", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
        }

        function printLoanPayments(event)
        {
            openWindow(event, printPaymentsUrl + "?id=" + ${loanLink.id} +
                    "&filter(fromPeriod)=" + '<bean:write name="form" property="filters(fromPeriod)" format="dd/MM/yyyy"/>' +
                              "&filter(toPeriod)=" + '<bean:write name="form" property="filters(toPeriod)" format="dd/MM/yyyy"/>',
                    "PrintLoanInfo", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
        }
    </script>
</c:if>
<%--
 bottomDataInfo - данные низа. Если не пустое, то отображается содержимое данной переменной а опереции не отображаются.
 showInMainCheckBox - признак, указывающий на необходимость отображения checkbox'а отвечающего за отображение
                        данного кредита на главной странице. Значение по умолчанию false
--%>

<!-- Информация по кредиту -->
<c:if test="${loanLink != null}">
    <c:set var="loan" value="${loanLink.loanShortCut}"/>
    <c:set var="loanInfoUrl"
           value="${phiz:calculateActionURL(pageContext,'/private/loans/info.do?id=')}${loanLink.id}"/>
    <c:set var="loanDetailUrl"
           value="${phiz:calculateActionURL(pageContext,'/private/loans/detail.do?id=')}${loanLink.id}"/>
    <c:set var="loanPaymentUrl"
           value="${phiz:calculateActionURL(pageContext,'private/payments/payment.do?form=LoanPayment')}&loanAccountNumber=${loan.accountNumber}"/>
    <c:set var="earlyLoanRepaymentUrl"
           value="${phiz:calculateActionURL(pageContext, 'private/payments/payment.do?form=EarlyLoanRepaymentClaim&loanLinkId=')}${loanLink.id}"/>
    <c:set var="paymentUrl" value="${loan.state == 'closed' ? null : loanPaymentUrl}"/>
    <c:set var="graphicUrl" value="${loan.state == 'closed' ? null : loanInfoUrl}"/>
    <c:choose>
        <c:when test="${scheduleAbstract.remainAmount != null}">
            <c:set var="remainAmount" value="${scheduleAbstract.remainAmount}"/>
        </c:when>
        <c:otherwise>
            <c:set var="remainAmount" value="${loan.balanceAmount}"/>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${notify == true and isOverdue == true and not phiz:contains(notNotifiedLoanLinkIds, loanLink.id)}">
            <c:set var="blinkLoan" value="blinkLoan"/>
        </c:when>
        <c:otherwise>
            <c:set var="blinkLoan" value=""/>
        </c:otherwise>
    </c:choose>
    <div class="loanProductCard ${blinkLoan}" id="${loanLink.id}">
        <tiles:insert definition="productTemplate" flush="false">
            <tiles:put name="productViewBacklight" value="false"/>
            <c:if test="${detailsPage}">
                <tiles:put name="operationsBlockPosition" value="rightPosition"/>
            </c:if>
            <%--Устанавливаем путь к картинке в зависимости от типа погашения--%>
            <c:choose>
                <c:when test="${loan.isAnnuity}">
                    <c:if test="${loan.state != 'closed'}">
                        <tiles:put name="img" value="${imagePath}/credit_type/icon_creditAnuitet.jpg"/>
                    </c:if>
                    <c:if test="${loan.state == 'closed'}">
                        <tiles:put name="img" value="${imagePath}/credit_type/icon_creditAnuitetBlocked.jpg"/>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${loan.state != 'closed'}">
                        <tiles:put name="img" value="${imagePath}/credit_type/icon_creditDiffer.jpg"/>
                    </c:if>
                    <c:if test="${loan.state == 'closed'}">
                        <tiles:put name="img" value="${imagePath}/credit_type/icon_creditDifferBlocked.jpg"/>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <tiles:put name="alt" value="Кредит"/>

            <c:choose>
                <c:when test="${detailsPage}">
                    <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
                </c:otherwise>
            </c:choose>

            <tiles:put name="productNumbers">
                <c:choose>
                    <c:when test="${detailsPage}">
                        <div class="loanClientType loanDetailType">вы&nbsp;${loanLink.personRole.description}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="loanClientType">вы&nbsp;${loanLink.personRole.description}</div>
                    </c:otherwise>
                </c:choose>
            </tiles:put>

            <tiles:put name="title"><bean:write name="loanLink" property="name"/></tiles:put>

            <c:if test="${not detailsPage  and loan.state != 'closed'}">
                <tiles:put name="src" value="${loanDetailUrl}"/>
            </c:if>
            <tiles:put name="leftData">
                <c:if test="${loan.state == 'closed'}">
                    <span class="detailStatus">${loan.state.description}</span>
                    <br/><br/>
                </c:if>
                <table class="productDetail">
                    <c:if test="${loan.rate != null}">
                        <td>
                            <div>Ставка по кредиту:</div>
                        </td>
                        <td class="value">
                            <span class="bold">${loan.rate}</span>%
                        </td>
                     </c:if>
                    <c:if test="${not empty loan.nextPaymentDate and loan.state != 'overdue'}">
                        <tr>
                            <c:choose>
                                <c:when test="${blinkLoan == 'blinkLoan'}">
                                     <td>
                                         <div>Осталось:</div>
                                     </td>
                                     <td class="value">
                                        <div>${daysLeft} дней</div>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <div>Внести до:</div>
                                    </td>
                                    <td class="value">
                                        <div>${phiz:formatDateWithStringMonth(loan.nextPaymentDate)}</div>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:if>
                    <c:if test="${detailsPage}">
                        <tr>
                            <td>
                                <c:if test="${remainAmount != null}">
                                    <c:choose>
                                        <c:when test="${loan.isAnnuity}">
                                            Осталось оплатить:
                                        </c:when>
                                        <c:otherwise>
                                            Осталось оплатить <br/>
                                            (основной долг + %):
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${remainAmount != null}">
                                    ${phiz:productFormatAmount(remainAmount)}
                                </c:if>
                            </td>
                        </tr>
                        <c:if test="${scheduleAbstract.doneAmount != null}">
                            <tr>
                                <td>
                                    Оплачено:
                                </td>
                                <td>
                                    ${phiz:productFormatAmount(scheduleAbstract.doneAmount)}
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                </table>
            </tiles:put>
            <tiles:put name="centerData">
                <c:if test="${not empty loan.nextPaymentAmount and loan.state != 'overdue'}">
                    <c:set var="amountinfo" value="${phiz:formatAmount(loan.nextPaymentAmount)}"/>
                    <c:if test="${loan.nextPaymentAmount.decimal<0}">
                        <c:set var="amountinfo">
                            &minus;${fn:substring(amountinfo, 1, -1)}
                        </c:set>
                    </c:if>
                    <c:choose>
                        <c:when test="${loan.nextPaymentAmount.decimal<0}">
                            <c:if test="${detailsPage}">
                                <span class="detailAmount negativeAmount">${amountinfo}</span>
                            </c:if>
                            <c:if test="${not detailsPage}">
                                <span class="overallAmount negativeAmount">${amountinfo}</span>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${detailsPage}">
                                <span class="detailAmount">${amountinfo}</span>
                            </c:if>
                            <c:if test="${not detailsPage}">
                                <span class="overallAmount">${amountinfo}</span>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <c:choose>
                        <c:when test="${blinkLoan == 'blinkLoan'}"><span class="amountStatus">Необходимо внести</span></c:when>
                        <c:otherwise><span class="amountStatus">Рекомендованный платеж</span></c:otherwise>
                    </c:choose>
                </c:if>
            </tiles:put>
            <tiles:put name="rightData">
                <tiles:insert definition="listOfOperation" flush="false">
                    <tiles:put name="productOperation" value="true"/>
                    <c:if test="${detailsPage}">
                        <tiles:put name="nameOfOperation">Операции по кредиту</tiles:put>
                    </c:if>

                    <c:choose>
                        <c:when test="${loan.state == 'closed'}">
                            <tiles:put name="isLock" value="true"/>
                            <tiles:putList name="items">
                                <c:if test="${!loan.isAnnuity and phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment')}">
                                    <tiles:add><span>Внести платеж</span></tiles:add>
                                </c:if>
                                <c:if test="${not detailsPage}">
                                    <tiles:add>График</tiles:add>
                                </c:if>
                            </tiles:putList>
                        </c:when>
                        <c:otherwise>
                            <tiles:putList name="items">
                                <c:if test="${!loan.isAnnuity and not empty loan.balanceAmount and phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment')}">
                                    <tiles:add><a href="${paymentUrl}">Внести платеж</a></tiles:add>
                                </c:if>
                                <c:if test="${not detailsPage}">
                                    <tiles:add><a href="${graphicUrl}">График</a></tiles:add>
                                </c:if>
                                <c:if test="${loan.isAnnuity}">
                                    <tiles:add><a class="operationSeparate" href="#" onclick="win.open('annLoanMessage'); return false;">Как оплатить?</a></tiles:add>
                                </c:if>
                                <c:if test="${detailsPage && phiz:impliesService('EarlyLoanRepaymentClaim') && form.earlyLoanRepaymentAllowed}">
                                    <tiles:add><a href="${earlyLoanRepaymentUrl}&partial=true">Частично погасить кредит</a></tiles:add>
                                    <tiles:add><a href="${earlyLoanRepaymentUrl}">Полностью погасить кредит</a></tiles:add>
                                </c:if>
                            </tiles:putList>
                        </c:otherwise>
                    </c:choose>
                </tiles:insert>
            </tiles:put>
            <c:if test="${showInMainCheckBox}">
                <tiles:put name="id" value="${loanLink.id}"/>
                <tiles:put name="showInMainCheckBox" value="true"/>
                <tiles:put name="inMain" value="${loanLink.showInMain}"/>
                <tiles:put name="productType" value="loan"/>
            </c:if>
            <c:if test="${showInThisWidgetCheckBox}">
                <tiles:put name="id" value="${loanLink.id}"/>
                <tiles:put name="showInThisWidgetCheckBox" value="true"/>
                <tiles:put name="productType" value="loan"/>
            </c:if>
            <c:if test="${notificationButton}">
                <tiles:put name="id" value="${loanLink.id}"/>
                <tiles:put name="notificationButton" value="true"/>
            </c:if>
            <c:choose>
                <c:when test="${loan.state == 'open' or loan.state == 'undefined'}">
                    <tiles:put name="status" value="active"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="status" value="error"/>
                    <c:if test="${loan.state != 'closed'}">
                        <tiles:put name="imgStatus" value="${loan.state.description}"/>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </tiles:insert>
    </div>
</c:if>
