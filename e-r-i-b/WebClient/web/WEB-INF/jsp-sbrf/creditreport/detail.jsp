<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/credit/report" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="detail" value="${form.creditDetail}"/>
    <c:set var="creditContract" value="${detail.creditContract}"/>
    <c:set var="creditClosedDate" value="${creditContract.closedDate}"/>
    <c:set var="waitingNew" value="${form.waitingNew}"/>
    <c:set var="bkiError" value="${form.bkiError}"/>
    <c:set var="report" value="${form.report}"/>

    <tiles:insert definition="creditHistory">
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <div class="credit-history-box">
                        <div class="credit-history">
                            <c:set var="credit" value="${detail.credit}"/>
                            <c:set var="typeName" value="Карта"/>
                            <c:set var="isCredit" value="${phiz:isInstance(credit, 'com.rssl.phizic.business.bki.Credit')}"/>
                            <c:if test="${isCredit}">
                                <c:set var="typeName" value="Кредит"/>
                            </c:if>
                            <h1 class="Title">${typeName} ${credit.bankName}</h1>

                            <p class="headerTitleText cred-det__desc">
                                По состоянию на ${phiz:formatDateWithMonthString(detail.lastReport)}
                            </p>

                            <c:if test="${not empty creditClosedDate}">
                                <div class="workspace-box roundBorder orange">
                                    <div class="info-message">
                                        <div class="messageContainer alignCenter">
                                            Кредит закрыт<c:if test="${not empty creditContract.reasonForClosure}">. ${creditContract.reasonForClosure}</c:if>
                                             ${phiz:formatDateWithStringMonth(creditClosedDate)}
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <div class="credit-history-items">
                                <c:choose>
                                    <c:when test="${isCredit}">
                                        <tiles:insert page="credit.jsp" flush="false">
                                            <tiles:put name="credit" beanName="credit"/>
                                            <tiles:put name="isDetail" value="true"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert page="card.jsp" flush="false">
                                            <tiles:put name="card" beanName="credit"/>
                                            <tiles:put name="isDetail" value="true"/>
                                        </tiles:insert>
                                    </c:otherwise>
                                </c:choose>

                                <tiles:insert page="contract.jsp" flush="false">
                                    <tiles:put name="creditContract" beanName="detail" beanProperty="creditContract"/>
                                    <tiles:put name="isCredit" value="${isCredit}"/>
                                </tiles:insert>
                            </div>

                            <c:set var="repaymentHistory" value="${detail.repaymentHistory}"/>

                            <div class="b-debts scroll">
                                <div class="credit-history-title cred-hist__title-question">
                                    <div class="float">История погашения и просрочки</div>
                                    <div class="float">
                                        <tiles:insert definition="hintTemplate" flush="false">
                                            <tiles:put name="color" value="whiteHint"/>
                                            <tiles:put name="data">
                                                Информация формируется по мере поступления данных в Кредитное бюро и может не содержать последних платежей.
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </div>
                                <div class="clear"></div>

                                <c:set var="repaymentHistory" value="${detail.repaymentHistory}"/>
                                <div class="cred-hist-payments-by-inst">
                                    <c:set var="stateSize" value="${phiz:size(repaymentHistory.states)}"/>
                                    <c:set var="criticalStateSize" value="${phiz:size(repaymentHistory.criticalStates)}"/>
                                    <c:choose>
                                        <c:when test="${stateSize + criticalStateSize == 0}">
                                            <div class="cred-hist-pay-good">Все выплаты по кредиту произведены вовремя</div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${stateSize < 4}">
                                                    <c:forEach items="${repaymentHistory.states}" var="state">
                                                        <div class="cred-hist-pay-col">
                                                            <tiles:insert page="paymentState.jsp" flush="false">
                                                                <tiles:put name="code" value="${state.state.code}"/>
                                                                <tiles:put name="name" value="${state.state.name}"/>
                                                                <tiles:put name="count" value="${state.count}"/>
                                                            </tiles:insert>
                                                        </div>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="cred-hist-pay-col">
                                                        <tiles:insert page="paymentState.jsp" flush="false">
                                                            <tiles:put name="code" value="${repaymentHistory.states[0].state.code}"/>
                                                            <tiles:put name="name" value="${repaymentHistory.states[0].state.name}"/>
                                                            <tiles:put name="count" value="${repaymentHistory.states[0].count}"/>
                                                        </tiles:insert>
                                                        <tiles:insert page="paymentState.jsp" flush="false">
                                                            <tiles:put name="code" value="${repaymentHistory.states[1].state.code}"/>
                                                            <tiles:put name="name" value="${repaymentHistory.states[1].state.name}"/>
                                                            <tiles:put name="count" value="${repaymentHistory.states[1].count}"/>
                                                        </tiles:insert>
                                                        <c:if test="${stateSize == 7}">
                                                            <tiles:insert page="paymentState.jsp" flush="false">
                                                                <tiles:put name="code" value="${repaymentHistory.states[6].state.code}"/>
                                                                <tiles:put name="name" value="${repaymentHistory.states[6].state.name}"/>
                                                                <tiles:put name="count" value="${repaymentHistory.states[6].count}"/>
                                                            </tiles:insert>
                                                        </c:if>
                                                    </div>
                                                    <div class="cred-hist-pay-col">
                                                        <tiles:insert page="paymentState.jsp" flush="false">
                                                            <tiles:put name="code" value="${repaymentHistory.states[2].state.code}"/>
                                                            <tiles:put name="name" value="${repaymentHistory.states[2].state.name}"/>
                                                            <tiles:put name="count" value="${repaymentHistory.states[2].count}"/>
                                                        </tiles:insert>
                                                        <tiles:insert page="paymentState.jsp" flush="false">
                                                            <tiles:put name="code" value="${repaymentHistory.states[3].state.code}"/>
                                                            <tiles:put name="name" value="${repaymentHistory.states[3].state.name}"/>
                                                            <tiles:put name="count" value="${repaymentHistory.states[3].count}"/>
                                                        </tiles:insert>
                                                    </div>
                                                    <c:if test="${stateSize >= 5}">
                                                        <div class="cred-hist-pay-col">
                                                            <tiles:insert page="paymentState.jsp" flush="false">
                                                                <tiles:put name="code" value="${repaymentHistory.states[4].state.code}"/>
                                                                <tiles:put name="name" value="${repaymentHistory.states[4].state.name}"/>
                                                                <tiles:put name="count" value="${repaymentHistory.states[4].count}"/>
                                                            </tiles:insert>
                                                            <c:if test="${stateSize >= 6}">
                                                                <tiles:insert page="paymentState.jsp" flush="false">
                                                                    <tiles:put name="code" value="${repaymentHistory.states[5].state.code}"/>
                                                                    <tiles:put name="name" value="${repaymentHistory.states[5].state.name}"/>
                                                                    <tiles:put name="count" value="${repaymentHistory.states[5].count}"/>
                                                                </tiles:insert>
                                                            </c:if>
                                                        </div>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${stateSize > 1}">
                                                <c:set var="classStyle" value="cred-hist-pay-col-sum-3"/>
                                                <c:choose>
                                                    <c:when test="${stateSize < 4}">
                                                        <c:set var="classStyle" value="cred-hist-pay-col-sum-1"/>
                                                    </c:when>
                                                    <c:when test="${stateSize > 3 && stateSize < 7}">
                                                        <c:set var="classStyle" value="cred-hist-pay-col-sum-2"/>
                                                    </c:when>
                                                </c:choose>
                                                <div class="${classStyle}">
                                                    Всего <span class="bold">${repaymentHistory.stateCount}</span> ${phiz:numeralDeclension(repaymentHistory.stateCount, "просроч", "ка", "ки", "ек")}
                                                </div>
                                            </c:if>

                                            <c:if test="${criticalStateSize > 0}">
                                                <c:if test="${stateSize > 0}">
                                                    <hr class="cred-hist-pay-line"/>
                                                </c:if>
                                                <c:forEach items="${repaymentHistory.criticalStates}" var="state">
                                                    <div class="cred-hist-pay-col">
                                                        <tiles:insert page="paymentState.jsp" flush="false">
                                                            <tiles:put name="code" value="${state.state.code}"/>
                                                            <tiles:put name="name" value="${state.state.name}"/>
                                                            <tiles:put name="count" value="${state.count}"/>
                                                        </tiles:insert>
                                                    </div>
                                                </c:forEach>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="clear"></div>
                                <div class="b-get-credit-history-bg cred-hist__report-bg"></div>

                                <div class="cred-hist-repost-wrap">
                                    <div class="debts_view">
                                        <div class="debts_inner">
                                            <div class="debts_reducer">
                                                <table class="cred-hist-report debts_header">
                                                    <thead class="cred-hist-report__header">
                                                        <tr class="cred-hist-report__h-line">
                                                            <td class="cred-hist-report_h-month">МЕСЯЦ</td>
                                                            <td class="cred-hist-report_h-state">СОСТОЯНИЕ ВЫПЛАТ И ПРОСРОЧКИ</td>
                                                            <td class="cred-hist-report_h-sum">ОБЩАЯ СУММА ЗАДОЛЖЕННОСТИ</td>
                                                            <td class="cred-hist-report_h-pr">ПРОСРОЧЕННАЯ ЗАДОЛЖЕННОСТЬ</td>
                                                            <td class="cred-hist-report_h-month-p">ЕЖЕМЕСЯЧНЫЙ ПЛАТЁЖ</td>
                                                        </tr>
                                                    </thead>
                                                </table>
                                                <div class="debts_scroll">

                                                    <table class="cred-hist-report debts_body">
                                                        <c:forEach items="${repaymentHistory.years}" var="year">
                                                            <tr class="debts_year">
                                                                <td class="cred-hist-report__divider debts_cell" colspan="5">
                                                                    <div class="cred-hist-report__divider-line">
                                                                        <span class="cred-hist-report__divider-year">${year.year}</span>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <c:forEach items="${year.monthHistory}" var="month">
                                                                <tiles:insert page="paymentRow.jsp" flush="false">
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
                            <div class="cred-hist-report-footer">
                                <c:set var="url" value="private/credit/report.do?form=CreditReportService"/>
                                <a href="${phiz:calculateActionURL(pageContext, url)}" class="cred-hist-report-back"><span class="cred-hist-report-back-in">Вернуться к отчёту</span></a>
                                <a href="#" class="credit-history-print-page right" onclick="window.print();">
                                    <img src="${globalUrl}/commonSkin/images/print-check.gif" alt=""><span class="credit-history-print-page-in">Печать страницы</span>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="credit-history-right">
                        <c:if test="${not bkiError and (report.mustUpdated or waitingNew)}">
                            <tiles:insert page="creditHistoryRequest.jsp" flush="false">
                                <tiles:put name="providerId" beanName="form" beanProperty="providerId"/>
                                <tiles:put name="cost" beanName="form" beanProperty="cost"/>
                                <tiles:put name="waitingNew" value="${waitingNew}"/>
                            </tiles:insert>
                        </c:if>
                        <div class="b-r-sidebar-okb">
                            <img src="${globalUrl}/commonSkin/images/okb-logo.gif" alt="" class="float"/>
                            <p>Отчёт предоставлен <a href="${phiz:getBkiOkbUrl()}" target="_blank">ЗАО «ОКБ»</a></p>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

<%--Показать или скрыть детальную информацию о договоре--%>
<script type="text/javascript">
    doOnLoad(function(){
        hideOrShowCreditDetail(true);
        if ($("table.cred-hist-report.debts_body").height() > $(".debts_scroll").height())
            $(".b-debts").jDebtsScroller();
        else
            $(".debts_bar").hide();
    });
    function hideOrShowCreditDetail(hide)
    {
        hideOrShow(document.getElementById('creditDetail'), hide);
        hideOrShow(document.getElementById('showDetail'), !hide);
        hideOrShow(document.getElementById('hideDetail'), hide);
    }
</script>
