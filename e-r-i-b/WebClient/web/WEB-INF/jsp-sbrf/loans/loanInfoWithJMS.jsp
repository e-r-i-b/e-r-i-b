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


<c:set var="delimiter" value=", "/>
<c:set var="isOverdue" value="${not empty loan.nextPaymentDate and loan.state == 'overdue'}"/>
<c:set var="paymentDate" value="${phiz:formatDayWithStringMonth(loan.closestPaymentDate)}"/>
<c:forEach items="${form.loanAccountInfo}" var="acct" varStatus="statusCount">
    <c:choose>
        <c:when test="${statusCount.first}">
            <c:set var="accountAmount" value="${acct.amount}"/>
        </c:when>
        <c:otherwise>
            <c:set var="accountAmount"  value="${phiz:getMoneyOperation(acct.amount, accountAmount, '+')}"/>
        </c:otherwise>
    </c:choose>
</c:forEach>

<c:set var="chooseToResource" value="${form.loanAccountInfo[0].nameToPay}"/>

<c:set var="isEnough" value="${phiz:compareMoney(accountAmount, loan.recPayment) >= 0}"/>
<c:set var="needMoney" value="${phiz:getMoneyOperation(loan.recPayment, accountAmount, '-')}"/>

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

                            <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplateWithJMS.jsp"%>
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

                                <div class="clear">&nbsp;</div>
                                <div class="b-debts scroll">
                                    <div class="graphDesc">График платежей может изменяться от досрочных погашений и неустоек за просроченную задолженность.</div>

                                    <div class="cred-hist-repost-wrap">
                                        <div class="debts_view">
                                            <div class="debts_inner" >
                                                <div class="debts_reducer">
                                                    <table class="cred-hist-report debts_header">
                                                        <thead class="cred-hist-report__header">
                                                            <tr class="cred-hist-report__h-line">
                                                                <td class="cred-hist-report_h-month">ДАТА ОПЛАТЫ</td>
                                                                <td class="cred-hist-report_h-state main-owe">ОСНОВНОЙ<br />ДОЛГ</td>
                                                                <td class="cred-hist-report_h-sum cred-hist-report_percent">ПРОЦЕНТЫ</td>
                                                                <td class="cred-hist-report_h-pr">СУММА<br/>К ОПЛАТЕ</td>
                                                                <td class="cred-hist-report_h-month-p cred-hist-report_lastCol">ОСТАТОК<br/>ОСНОВНОГО ДОЛГА</td>
                                                            </tr>
                                                        </thead>
                                                    </table>
                                                    <div class="debts_scroll">

                                                        <table class="cred-hist-report debts_body">
                                                            <c:forEach items="${scheduleAbstract.yearPayments}" var="year">
                                                                <tr class="debts_year loan_row">
                                                                    <td class="cred-hist-report__divider debts_cell" colspan="5">
                                                                        <div class="cred-hist-report__divider-line">
                                                                            <span class="cred-hist-report__divider-year">${year.year}</span>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <c:forEach items="${year.months}" var="month">
                                                                    <tiles:insert page="loanRow.jsp" flush="false">
                                                                        <tiles:put name="month" beanName="month"/>
                                                                    </tiles:insert>
                                                                </c:forEach>
                                                            </c:forEach>
                                                        </table>
                                                    </div>
                                                </div>
                                                <div class="debts_bar">
                                                    <div class="debts_thumbs"></div>
                                                    <i class="debts_run"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </div>

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

<%--Показать или скрыть детальную информацию о договоре--%>
<script type="text/javascript">
    doOnLoad(function(){
        if ($("table.cred-hist-report.debts_body").height() > $(".debts_scroll").height())
            $(".b-debts").jDebtsScroller();
        else
            $(".debts_bar").hide();
    });

</script>
