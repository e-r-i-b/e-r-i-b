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
        var fillAccountLink = "${phiz:calculateActionURL(pageContext,'/private/payments/payment')}";

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

        function loanConditions(event)
        {
            var url = "${phiz:calculateActionURL(pageContext,'/private/credit/offert/conditions.do')}";
            var params = "?appNum=${form.id}";
            openWindow(event, url + params, "loanConditionsWin", "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1024, height=700");
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
    <c:set var="loan" value="${loanLink.loan}"/>
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
                    <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle size21"/>
                </c:when>
                <c:otherwise>
                    <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight size21"/>
                </c:otherwise>
            </c:choose>

            <tiles:put name="productNumbersClass" value="fullProductInfo"/>
            <tiles:put name="productNumbers">
                <c:if test="${not empty loan.loanType}">
                    <c:out value="${loan.loanType}"/>&nbsp;
                </c:if>
                Дата окончания ${phiz:formatDateWithStringMonth(loan.termEnd)}

            </tiles:put>

            <c:set var="fullLoanLinkName"><bean:write name="loanLink" property="name"/></c:set>
            <tiles:put name="title"><c:out value="${phiz:buildFullLoanTitle(fullLoanLinkName)}"/></tiles:put>

            <tiles:put name="rightComment"><c:out value="true"/></tiles:put>
            <tiles:put name="comment"><c:out value="${loan.creditingRate}%"/></tiles:put>
            <tiles:put name="additionalCommentClass" value="smallTextDesc"/>

            <tiles:put name="productImgAdditionalData">
                <span class="smallroductInfo">
                    <c:choose>
                        <c:when test="${loan.isAnnuity}">
                            АННУИТЕНТНЫЙ
                        </c:when>
                        <c:otherwise>
                            ДИФФЕРЕН-<br/>ЦИРОВАННЫЙ
                        </c:otherwise>
                    </c:choose>
                </span>
            </tiles:put>

            <c:if test="${not detailsPage  and loan.state != 'closed'}">
                <tiles:put name="src" value="${loanDetailUrl}"/>
            </c:if>
            <tiles:put name="leftData">
                <c:if test="${loan.state == 'closed'}">
                    <span class="detailStatus">${loan.state.description}</span>
                    <br/><br/>
                </c:if>
                <table class="productDetail productDetailFix">
                    <tbody>
                        <tr>
                            <td>
                                <div>Первоначальная сумма</div>
                            </td>
                            <td class="alignRight">
                                <div>${phiz:formatAmountWithoutSpaces(loan.loanAmount)}</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Осталось погасить
                                <div class="inlineBlock">
                                    <tiles:insert definition="hintMouseOverTemplate" flush="false">
                                        <tiles:put name="id" value="remainsToRepay"/>
                                        <tiles:put name="color" value="whiteHint"/>
                                        <tiles:put name="data">
                                            Сумма основного долга, проценты и просрочки
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </td>
                            <td class="alignRight">${phiz:formatAmountWithoutSpaces(loan.fullRepaymentAmount)}</td>
                        </tr>
                    </tbody>
                </table>

                <div class="messDetailDate">
                    <c:choose>
                        <%-- не просрочен, средств достаточно--%>
                        <c:when test="${isEnough and !isOverdue}">
                            <div class="messDetail messDetail-b">
                                <h5 class="messDetailTitle">Ближайший платёж ${paymentDate}, ${phiz:productFormatAmountWithoutSpaces(loan.recPayment)}</h5>
                                <p class="messDetailTxt">На привязанных к кредиту счетах достаточно средств для оплаты.</p>
                            </div>
                        </c:when>
                        <%-- не просрочен, средств недостаточно--%>
                        <c:when test="${!isEnough and !isOverdue}">
                            <div class="messDetail messDetail-o">
                                <h5 class="messDetailTitle">Ближайший платёж ${paymentDate}, ${phiz:productFormatAmountWithoutSpaces(loan.recPayment)}<br />Не хватает ${phiz:productFormatAmountWithoutSpaces(needMoney)} для оплаты.</h5>

                                <div class="inlineBlock">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.loan.fill.account"/>
                                        <tiles:put name="commandHelpKey" value="button.loan.fill.account.help"/>
                                        <tiles:put name="bundle"         value="loansBundle"/>
                                        <tiles:put name="onclick">window.location = '${phiz:calculateActionURL(pageContext,'/private/payments/payment')}' + '?form=InternalPayment&toResource=${chooseToResource}';</tiles:put>
                                        <tiles:put name="viewType" value="buttonWhite"/>
                                    </tiles:insert>
                                </div>
                            </div>
                        </c:when>
                        <%-- просрочен, средств недостаточно--%>
                        <c:when test="${!isEnough and isOverdue}">
                            <c:set var="penalty" value="${phiz:getMoneyOperation(loan.overdueMainDebts, loan.interestPayments, '+')}"/>
                            <div class="messDetail messDetail-r">
                                <h5 class="messDetailTitle">Просрочен платёж от ${paymentDate}, ${phiz:productFormatAmountWithoutSpaces(loan.recPayment)}<br /> включая неустойку ${phiz:productFormatAmountWithoutSpaces(penalty)}</h5>
                                <div class="inlineBlock">
                                    <tiles:insert definition="clientButton" flush="false">
                                        <tiles:put name="commandTextKey" value="button.loan.fill.account"/>
                                        <tiles:put name="commandHelpKey" value="button.loan.fill.account.help"/>
                                        <tiles:put name="bundle"         value="loansBundle"/>
                                        <tiles:put name="onclick">window.location = '${phiz:calculateActionURL(pageContext,'/private/payments/payment')}' + '?form=InternalPayment&toResource=${chooseToResource}';</tiles:put>
                                        <tiles:put name="viewType" value="buttonWhite"/>
                                    </tiles:insert>
                                </div>
                            </div>
                        </c:when>
                        <%-- просрочен, средств достаточно--%>
                        <c:otherwise>
                            <c:set var="penalty" value="${phiz:getMoneyOperation(loan.overdueMainDebts, loan.interestPayments, '+')}"/>
                            <div class="messDetail messDetail-o">
                                <h5 class="messDetailTitle">Ожидание погашения просроченного платежа от ${paymentDate},  ${phiz:productFormatAmountWithoutSpaces(loan.recPayment)}<br /> включая неустойку ${phiz:productFormatAmountWithoutSpaces(penalty)}.
                                Сумма спишется автоматически в ближайшее время. Пожалуйста, не совершайте других операций с привязанными счетами</h5>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

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
