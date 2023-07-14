<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/credit/report" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="profile" value="${form.profile}"/>
    <c:set var="waitingNew" value="${form.waitingNew}"/>
    <c:set var="bkiError" value="${form.bkiError}"/>
    <tiles:insert definition="creditHistory">
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                    <c:if test="${form.fields.relocateToDownload != null && form.fields.relocateToDownload == 'true'}">
                        <script type="text/javascript">
                            $(window).load(function(){
                                <c:set var="downloadFileURL" value="${phiz:calculateActionURL(pageContext,'/download')}?fileType=CreditHistoryResult&contentType=pdf&clientFileName=${form.fields.clientFileName}"/>
                                clientBeforeUnload.showTrigger=false;
                                if(window.navigator.userAgent.indexOf("iPhone") != -1 || window.navigator.userAgent.indexOf("iPad") != -1)
                                    window.open('${downloadFileURL}');
                                else
                                    goTo('${downloadFileURL}');
                                clientBeforeUnload.showTrigger=false;
                            });
                        </script>
                    </c:if>
                    <div class="credit-history-box">
                        <div class="credit-history">
                            <h1 class="Title">
                                <bean:message bundle="creditHistoryBundle" key="label.credit.history.report.title"/>
                            </h1>
                            <p class="headerTitleText credit-history__desc">
                                <bean:message bundle="creditHistoryBundle" key="label.credit.history.report.description"/>
                            </p>
                            <c:set var="report" value="${form.report}"/>
                            <c:if test="${!empty report.rating}">
                                <tiles:insert page="rating.jsp" flush="false">
                                    <tiles:put name="updated" beanName="report" beanProperty="updated"/>
                                    <tiles:put name="mustUpdated" beanName="report" beanProperty="mustUpdated"/>
                                    <tiles:put name="rating" beanName="report" beanProperty="rating"/>
                                    <tiles:put name="providerId" beanName="form" beanProperty="providerId"/>
                                    <tiles:put name="waitingNew" value="${waitingNew}"/>
                                    <tiles:put name="bkiError" value="${bkiError}"/>
                                </tiles:insert>
                            </c:if>
                            <c:choose>
                                <c:when test="${phiz:size(report.credits) == 0 && phiz:size(report.cards) == 0}">
                                    У Вас нет открытых кредитов и кредитных карт
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert page="activeCredits.jsp" flush="false">
                                        <tiles:put name="credits" beanName="report" beanProperty="credits"/>
                                        <tiles:put name="cards" beanName="report" beanProperty="cards"/>
                                        <tiles:put name="isActiveCreditViewBlock" value="${form.activeCreditViewBlock}"/>
                                        <tiles:put name="creditsCount" beanName="report" beanProperty="creditsCount"/>
                                    </tiles:insert>
                                    <div class="clear"></div>
                                    <tiles:insert page="totalPayments.jsp" flush="false">
                                        <tiles:put name="report" beanName="report"/>
                                    </tiles:insert>
                                </c:otherwise>
                            </c:choose>
                            <div class="clear"></div>
                            <c:if test="${report.closedCreditsCount != 0 || report.closedCardsCount != 0}">
                                <tiles:insert page="closedCredits.jsp" flush="false">
                                    <tiles:put name="report" beanName="report"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${report.creditHistoryRequests != null}">
                                <tiles:insert page="creditHistoryInteresed.jsp" flush="false">
                                    <tiles:put name="historyRequests" beanName="report" beanProperty="creditHistoryRequests"/>
                                    <tiles:put name="historyRequestsCount" beanName="report" beanProperty="creditHistoryRequestsCount"/>
                                    <tiles:put name="historyRequestsHalfYear" beanName="report" beanProperty="creditHistoryRequestsHalfYear"/>
                                    <tiles:put name="historyRequestsHalfYearCount" beanName="report" beanProperty="creditHistoryRequestsHalfYearCount"/>
                                </tiles:insert>
                            </c:if>
                            <div class="cred-hist-print-page-wrap">
                                <a href="#" class="credit-history-print-page" onclick="window.print();">
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
                        <c:if test="${profile.connected && profile.report != null}">
                            <c:if test="${phiz:getPdfButtonShow()}">
                                <div class="align-center">
                                    <tiles:insert definition="bkiReportSelect" flush="false">
                                        <tiles:put name="creditProfile" beanName="form" beanProperty="profile"/>
                                    </tiles:insert>
                                </div>
                            </c:if>
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