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

<html:form action="/private/loans/info" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="loanLink" value="${form.loanLink}" scope="request"/>
<c:set var="loanLinkAmount" value="${loanLink.loan.loanAmount}" scope="request"/>
<c:set var="loan" value="${loanLink.loan}" scope="request"/>
<c:set var="loanOffice" value="${loan.office}" scope="request"/>
<c:set var="scheduleAbstract" value="${form.scheduleAbstract}" scope="request"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="detailsPage" value="true"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

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
            <tiles:put name="name"><bean:write name="loanLink" property="name"/> ${phiz:formatAmount(loanLinkAmount)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <c:if test="${not empty form.loanLink}">
         <tiles:put name="data" type="string">
            <jsp:include page="annLoanMessageWindow.jsp" flush="false"/>
            <html:hidden property="field(startNumber)"/>
            <html:hidden property="field(count)"/>

                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="data">
                            <c:set var="showInMainCheckBox" value="${phiz:impliesService('ReceiveLoansOnLogin')}"/>
                            <c:set var="bottomDataInfo" value="false"/>

                            <c:set var="loanLinkName">
                                «${loanLink.name}»
                            </c:set>

                            <div class="abstractContainer3">
                                <c:set var="baseInfo">
                                    <bean:message key="favourite.link.loan" bundle="paymentsBundle"/>
                                </c:set>
                                <div class="favouriteLinksButton">
                                    <tiles:insert definition="addToFavouriteButton" flush="false">
                                        <tiles:put name="formName"><c:out value='${baseInfo} ${loanLinkName}'/></tiles:put>
                                        <tiles:put name="patternName"><c:out value='${baseInfo} «${loanLink.patternForFavouriteLink}»'/></tiles:put>
                                        <tiles:put name="typeFormat">LOAN_LINK</tiles:put>
                                        <tiles:put name="productId">${form.id}</tiles:put>
                                    </tiles:insert>
                                </div>
                            </div>

                            <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp"%>
                            <div class="tabContainer">
                                <tiles:insert definition="paymentTabs" flush="false">
                                    <tiles:put name="count" value="3"/>
                                    <tiles:put name="tabItems">
                                        <tiles:insert definition="paymentTabItem" flush="false">
                                            <tiles:put name="position" value="first"/>
                                            <tiles:put name="active" value="false"/>
                                            <tiles:put name="title" value="Детальная информация"/>
                                            <tiles:put name="action" value="/private/loans/detail?id=${loanLink.id}"/>
                                        </tiles:insert>
                                        <tiles:insert definition="paymentTabItem" flush="false">
                                            <tiles:put name="position" value="last"/>
                                            <tiles:put name="active" value="true"/>
                                            <tiles:put name="title" value="График платежей"/>
                                            <tiles:put name="action" value="/private/loans/info.do?id=${loanLink.id}"/>
                                        </tiles:insert>
                                        <c:if test="${phiz:impliesService('EarlyLoanRepaymentClaim') && form.earlyLoanRepaymentPossible}">
                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                <tiles:put name="position" value="irrelevant"/>
                                                <tiles:put name="active" value="false"/>
                                                <tiles:put name="title" value="Досрочное погашение"/>
                                                <tiles:put name="action" value="/private/loans/earlyloanrepayment.do?id=${loanLink.id}"/>
                                            </tiles:insert>
                                        </c:if>
                                    </tiles:put>
                                </tiles:insert>
                                <div class="abstractFilter">

                                    <div class="clear"></div>

                                    <div class="abstractContainer2">
                                        <c:if test="${scheduleAbstract.isAvailable}">
                                            <%-- фильтр --%>
                                            <div class="inlineBlock">
                                                <tiles:insert definition="filterDataPeriod" flush="false">
                                                    <tiles:put name="week" value="false"/>
                                                    <tiles:put name="month" value="false"/>
                                                    <tiles:put name="name" value="Period"/>
                                                    <tiles:put name="buttonKey" value="button.filter"/>
                                                    <tiles:put name="buttonBundle" value="loansBundle"/>
                                                    <tiles:put name="needErrorValidate" value="false"/>
                                                    <tiles:put name="periodFormat" value="MM/yyyy"/>
                                                    <tiles:put name="isNeedTitle" value="false"/>
                                                </tiles:insert>
                                            </div>
                                            <%-- /фильтр --%>
                                            <%--кнопка печати графика платежей--%>
                                            <div class="printLoansButton printButtonRight">
                                                <tiles:insert definition="clientButton" flush="false">
                                                    <tiles:put name="commandTextKey" value="button.payments.list.print2"/>
                                                    <tiles:put name="commandHelpKey" value="button.payments.list.print2.help"/>
                                                    <tiles:put name="bundle"         value="loansBundle"/>
                                                    <tiles:put name="viewType"       value="buttonGrey"/>
                                                    <tiles:put name="image" value="print-check.gif"/>
                                                    <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                                    <tiles:put name="imagePosition" value="left"/>
                                                    <tiles:put name="onclick">printLoanPayments(event)</tiles:put>
                                                </tiles:insert>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="clear">&nbsp;</div>

                                <%--График погашения--%>
                                <c:if test="${scheduleAbstract.isAvailable}">
                                    <tiles:insert definition="simpleTableTemplate" flush="false">
                                        <tiles:put name="id" value="detailInfoTable"/>
                                        <tiles:put name="grid">
                                            <c:set var="showOverpayment" value="false"/>
                                            <c:forEach items="${scheduleAbstract.schedules}" var="schedule">
                                                <c:if test="${not empty schedule.overpayment}">
                                                    <c:set var="showOverpayment" value="true"/>
                                                </c:if>
                                            </c:forEach>

                                            <sl:collection id="schedule" property="scheduleAbstract.schedules" model="simple-pagination" onRowClick="viewPayment(this);" assignedPaginationSize="12" assignedPaginationSizes="12;36;60">
                                                <c:set var="paymentStyle" value="${schedule.paymentNumber == 0 ? 'bold' : ''}"/>
                                                <c:set var="paymentStyle" value="${paymentStyle} ${loan.state == 'overdue' && schedule.paymentState == 'current' ? 'red' : ''}"/>
                                                <c:set var="paymentStyle" value="${paymentStyle} ${schedule.paymentState == 'current' ? 'bold' : ''}"/>

                                                <sl:collectionItem name="paymentNumber" styleClass="align-center" title="Номер платежа" hidden="true">
                                                    ${(schedule.paymentNumber)}
                                                </sl:collectionItem>
                                                <sl:collectionItem styleClass="align-center leftPaddingCell ${paymentStyle}" styleTitleClass="align-center leftRoundCell" title="Дата оплаты">
                                                    ${phiz:formatDateDependsOnSysDate(schedule.date, false, false)}
                                                </sl:collectionItem>

                                                <sl:collectionItem styleClass="align-right ${paymentStyle}" styleTitleClass="align-right" title="Основной долг">
                                                    ${phiz:formatAmount(schedule.principalAmount)}
                                                </sl:collectionItem>

                                                <sl:collectionItem styleClass="align-center" title="+"/>

                                                <sl:collectionItem styleClass="align-right ${paymentStyle}" styleTitleClass="align-right" title="Проценты">
                                                    ${phiz:formatAmount(schedule.interestsAmount)}
                                                </sl:collectionItem>

                                                <sl:collectionItem styleClass="align-center" title="+"/>

                                                <sl:collectionItem styleClass="align-right ${paymentStyle}" styleTitleClass="align-right" title="Пени, штрафы">
                                                    <%-- считаем сумму задолжностей по платежу --%>
                                                    <c:if test="${schedule.penaltyDateDebtItemMap != null}">
                                                        <c:set var="debt" value="${null}"/>
                                                        <c:forEach var="listElement" items="${schedule.penaltyDateDebtItemMap}" varStatus="lineInfo">
                                                            <c:set var="debt" value="${phiz:getMoneyOperation(debt, listElement.value.amount, '+')}"/>
                                                        </c:forEach>
                                                        <c:choose>
                                                            <c:when test="${debt == null}">
                                                                0,00 ${phiz:getCurrencyName(loanLinkAmount.currency)}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${phiz:formatAmount(debt)}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </sl:collectionItem>

                                                <sl:collectionItem styleClass="align-center" title="="/>
                                                <c:choose>
                                                    <c:when test="${!showOverpayment}">
                                                        <sl:collectionItem styleClass="align-right ${paymentStyle}" styleTitleClass="align-right" title="Сумма оплаты">
                                                            ${phiz:formatAmount(schedule.totalPaymentAmount)}
                                                        </sl:collectionItem>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <sl:collectionItem styleClass="align-right ${paymentStyle}" styleTitleClass="align-right" title="Сумма оплаты">
                                                            ${phiz:formatAmount(schedule.totalPaymentAmount)}
                                                        </sl:collectionItem>
                                                    </c:otherwise>
                                                </c:choose>

                                                <c:if test="${showOverpayment}">
                                                    <sl:collectionItem styleClass="align-right ${paymentStyle}" styleTitleClass="align-right" title="Переплата">
                                                        <c:if test="${not empty schedule.overpayment}">
                                                            ${phiz:formatAmount(schedule.overpayment)}
                                                        </c:if>
                                                    </sl:collectionItem>
                                                </c:if>
                                            </sl:collection>
                                        </tiles:put>

                                        <tiles:put name="isEmpty" value="${empty form.scheduleAbstract.schedules}"/>
                                        <tiles:put name="emptyMessage"><bean:message key="message.empty" bundle="loansBundle"/></tiles:put>
                                    </tiles:insert>

                                </c:if>
                            </div>
                            <div class="clear"></div>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${not empty form.anotherLoans}">
                        <div id="another-cards">
                            <tiles:insert definition="mainWorkspace" flush="false">
                                <c:set var="loanCount" value="${phiz:getClientProductLinksCount('LOAN')}"/>
                                <tiles:put name="title">
                                    Остальные кредиты
                                    (<a href="${phiz:calculateActionURL(pageContext, '/private/loans/list')}" class="blueGrayLink">показать все ${loanCount}</a>)
                                </tiles:put>
                                <tiles:put name="data">
                                    <div class="another-items">
                                        &nbsp;
                                        <c:set var="loanInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/loans/info.do?id=')}"/>
                                        <c:forEach items="${form.anotherLoans}" var="others">
                                            <div class="another-container">
                                                <c:choose>
                                                    <c:when test="${others.loan.isAnnuity}">
                                                        <c:set var="loanImagePath" value="${image}/credit_type/icon_creditAnuitet32.jpg"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="loanImagePath" value="${image}/credit_type/icon_creditDiffer32.jpg"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <a href="${loanInfoUrl}${others.id}">
                                                    <img src="${loanImagePath}" alt="${others.loan.description}"/>
                                                </a>
                                                <a class="another-number" href="${loanInfoUrl}${others.id}"><bean:write name="others" property="name"/></a>
                                                <div class="another-name">
                                                    <a class="another-name decoration-none" href="${loanInfoUrl}${others.id}">${phiz:formatAmount(others.loan.loanAmount)}</a>
                                                    <c:set var="state" value="${others.loan.state}"/>
                                                    <c:set var="className">
                                                        <c:if test="${state eq 'overdue' or state eq 'closed'}">
                                                            red
                                                        </c:if>
                                                    </c:set>

                                                    <span class="${className}">
                                                        <span class="prodStatus status" style="font-weight:normal;">
                                                            <c:if test="${not empty className}">
                                                                <nobr>(${state.description})</nobr>
                                                            </c:if>
                                                        </span>
                                                    </span>
                                                </div>
                                            </div>
                                        </c:forEach>
                                        &nbsp;
                                        <div class="clear"></div>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:if>
         </tiles:put>
    </c:if>
</tiles:insert>
</html:form>
