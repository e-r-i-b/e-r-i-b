<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/loans/earlyloanrepayment" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="loanLink" value="${form.loanLink}" scope="request"/>
    <c:set var="loan" value="${loanLink.loan}" scope="request"/>
    <c:set var="loanOffice" value="${loan.office}" scope="request"/>
    <c:set var="scheduleAbstract" value="${form.scheduleAbstract}" scope="request"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="detailsPage" value="true"/>
    <c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>
    <c:set var="image" value="${globalUrl}/commonSkin/images"/>
    <c:set var="earlyLoanRepaymentUrl" value="${phiz:calculateActionURL(pageContext, 'private/payments/payment.do?form=EarlyLoanRepaymentClaim&loanLinkId=')}${loanLink.id}"/>

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
        <tiles:put name="data" type="string">
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="cancelEarlyLoanRepaymentClaim"/>
                <tiles:put name="styleClass" value="compact"/>
                <tiles:put name="data" type="string">
                    <div class="align-center size20"><bean:message key="message.cancel.early.loan.repayment.claim" bundle="paymentsBundle"/></div>
                    <div class="buttonsArea">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.cancelLoanClaim"/>
                            <tiles:put name="commandHelpKey" value="button.cancelLoanClaim"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.notCancelLoanClaim"/>
                            <tiles:put name="commandHelpKey" value="button.notCancelLoanClaim"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="onclick" value="win.close('cancelEarlyLoanRepaymentClaim')"/>
                            <tiles:put name="viewType" value="simpleLink"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>

            <input type="hidden" id="terminationRequestId" name="terminationRequestId"/>

            <div id="cards">
                <div id="card-detail">
                    <c:if test="${loanLink != null}">
                        <jsp:include page="annLoanMessageWindow.jsp" flush="false"/>
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
                                    <c:if test="${not scheduleAbstract.isAvailable}">
                                        <c:set var="position" value="-last"/>
                                    </c:if>
                                    <tiles:insert definition="paymentTabs" flush="false">
                                        <tiles:put name="count" value="3"/>
                                        <tiles:put name="tabItems">
                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                <tiles:put name="position" value="first${position}"/>
                                                <tiles:put name="active" value="false"/>
                                                <tiles:put name="title" value="Детальная информация"/>
                                                <tiles:put name="action" value="/private/loans/detail?id=${loanLink.id}"/>
                                            </tiles:insert>
                                            <c:if test="${scheduleAbstract.isAvailable}">
                                                <tiles:insert definition="paymentTabItem" flush="false">
                                                    <tiles:put name="position" value="last"/>
                                                    <tiles:put name="active" value="false"/>
                                                    <tiles:put name="title" value="График платежей"/>
                                                    <tiles:put name="action" value="/private/loans/info.do?id=${loanLink.id}"/>
                                                </tiles:insert>
                                            </c:if>
                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                <tiles:put name="position" value="irrelevant"/>
                                                <tiles:put name="active" value="true"/>
                                                <tiles:put name="title" value="Досрочное погашение"/>
                                                <tiles:put name="action" value="/private/loans/earlyloanrepayment.do?id=${loanLink.id}"/>
                                            </tiles:insert>
                                        </tiles:put>
                                    </tiles:insert>

                                    <c:if test="${form.earlyLoanRepaymentAllowed}">
                                        <div class="prePmtn">
                                            <div class="prePmtnType">
                                                <h5 class="prePmtnTtl">Частичное досрочное погашение</h5>
                                                <p class="prePmtnText">После исполнения заявления на погашение график платежей пересчитывается и плановый ежемесячный платёж может уменьшаться (зависит от суммы погашения).</p>
                                                <a href="${earlyLoanRepaymentUrl}&partial=true">
                                                    <div class="simpleWhiteBtn">
                                                        Частично погасить кредит
                                                    </div>
                                                </a>
                                            </div>
                                            <div class="prePmtnType prePmtnCol2">
                                                <h5 class="prePmtnTtl">Полное досрочное погашение</h5>
                                                <p class="prePmtnText">После исполнения заявления на погашение обслуживаемый кредит погашается полностью.</p>
                                                <a href="${earlyLoanRepaymentUrl}">
                                                    <div class="simpleWhiteBtn">
                                                        Полностью погасить кредит
                                                    </div>
                                                </a>
                                            </div>
                                            <p class="prePmtnText prePmtnDesc">Если на дату платежа на счёте недостаточно средств, досрочное погашение не выполняется.</p>
                                        </div>
                                    </c:if>

                                    <div class="prePmtnHistory">
                                        <h5 class="prePmtnHistTtl">История досрочных погашений</h5>
                                        <!--<div class="prePmtnHistoryTbl">-->
                                        <table class="prePmtnHistoryTbl" cellspacing="0" cellpadding="0">
                                            <tr class="tblInfHeader">
                                                <th class="titleTable">
                                                    <div class="operationCell">
                                                        <span class="word-wrap">ОПЕРАЦИЯ</span>
                                                    </div>
                                                </th>
                                                <th class="titleTable">
                                                    <span> СЧЕТ СПИСАНИЯ </span>
                                                </th>
                                                <th class="titleTable">
                                                    <div class="alignRight"> СУММА </div>
                                                </th>
                                                <th class="titleTable">
                                                    <div class="alignRight">ДАТА ОПЛАТЫ</div>
                                                </th>
                                            </tr>
                                            <c:forEach var="item" items="${form.earlyRepayments}">
                                                <tr>
                                                    <td>
                                                        <table class="paymentDescription">
                                                            <tbody>
                                                            <tr>
                                                                <td>
                                                                    <div class="payment-description-body draft">
                                                                        <span class="word-wrap">
                                                                            <span>Досрочное погашение кредита</span>
                                                                        </span>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${item.status=='Принято'}">
                                                                            <img class="iconState" alt="${item.status}" src="${imagePath}/state_waiting2.png"/>
                                                                            <span class="state-waiting">${item.status}</span>
                                                                        </c:when>
                                                                        <c:when test="${item.status=='Исполнено'}">
                                                                            <img class="iconState" alt="${item.status}" src="${imagePath}/state_executed2.png"/>
                                                                            <span class="state-executed">${item.status}</span>
                                                                        </c:when>
                                                                        <c:when test="${item.status=='Отменено'}">
                                                                            <img class="iconState" alt="${item.status}" src="${imagePath}/state_cancel.png"/>
                                                                            <span class="state-cancel">${item.status}</span>
                                                                        </c:when>
                                                                        <c:when test="${item.status=='Отклонено'}">
                                                                            <img class="iconState" alt="${item.status}" src="${imagePath}/state_cancel2.png"/>
                                                                            <span class="state-cancel">${item.status}</span>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </td>
                                                    <td class="draft">
                                                        <c:choose>
                                                            <c:when test="${fn:length(item.account)<20}">
                                                                <c:set var="cardlink" value="${phiz:getCardLink(item.account)}"/>
                                                                <div class="linkName">
                                                                    ${phiz:getCardUserName(cardlink)}
                                                                </div>
                                                                <div class="grayText">${phiz:getCutCardNumber(item.account)}</div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="grayText">${phiz:getFormattedAccountNumber(item.account)}</div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="alignRight draft">
                                                        <div class="textNobr amount"><bean:write name="item" property="amount" format="### ##0.00"/> руб.</div>
                                                    </td>
                                                    <td class="alignRight draft">
                                                        <c:set var="itemDateAsString" value="${phiz:dateToString(item.date)}"/>
                                                        <c:set var="itemDateAsCalendar" value="${phiz:parseCalendar(itemDateAsString)}"/>
                                                        <c:out value="${phiz:formatDayWithStringMonth(itemDateAsCalendar)}"/>
                                                        <c:if test="${item.status=='Принято' && phiz:impliesService('CancelEarlyLoanRepaymentClaim') && form.earlyRepCancelAllowed}">
                                                            <div class="clear"></div>
                                                            <div class="simpleWhiteBtn" onclick="$('#terminationRequestId').val('${item.terminationRequestId}');  javascript:win.open('cancelEarlyLoanRepaymentClaim');">Отозвать погашение</div>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                        <!--</div>-->
                                    </div>

                                    <div class="clear"></div>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                    <div class="clear"></div>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
