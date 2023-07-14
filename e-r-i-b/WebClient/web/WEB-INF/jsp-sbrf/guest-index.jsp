<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html:form action="/guest/index" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:set var="claimListSize" value="${phiz:size(form.claimsList)}"/>
    <c:set var="claimListLimitSize" value="${phiz:getGuestClaimsLimit()}"/>
    <c:set var="needShowAllClaimsButton" value="${claimListSize > claimListLimitSize}"/>
    <tiles:insert definition="guestPage">
        <tiles:put name="mainMenuType" value="guestMainMenu"/>
        <tiles:put name="mainmenu" value="Index"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="data" type="string">
            <div class="gp-wrapper">
                <div class="float">
                    <c:if test="${phiz:isNeedToShowGuestBanner()}">
                        <div class="gp-message">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="orangeLight"/>
                                <tiles:put name="data">
                                    <div class="gp-banner">
                                        <div class="gp-title">
                                            Добро пожаловать в Сбербанк Онлайн!
                                        </div>
                                        <div class="gp-text">
                                            <p>Отслеживайте статусы своих заказов и заказывайте другие продукты Сбербанка. Если у вас есть банковская карта Сбербанка, получите доступ ко
                                                <span class="gp-underlined">всем возможностям Сбербанк Онлайн</span></p>
                                            <p>Заходите в Сбербанк Онлайн по адресу https://<b>online.sberbank.ru</b> используя свой логин и пароль.</p>
                                        </div>
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:if>
                    <c:if test="${claimListSize != 0}">
                        <script type="text/javascript">
                            function edit(event, paymentId, state, formName)
                            {
                                preventDefault(event);
                                var url = "${phiz:calculateActionURL(pageContext,'/guest/payments/default-action')}";
                                window.location = url + "?id=" + paymentId + "&history=true" + "&objectFormName=" + formName + "&stateCode=" + state;
                            }
                        </script>
                        <div class="gp-claimsList">
                            <div class="gp-title"><bean:message key="list.title" bundle="sbnkdBundle"/></div>
                            <div class="gp-claims">
                                <c:forEach var="claim" items="${form.claimsList}" varStatus="i">
                                    <c:set var="claimId" value="${claim.documentNumber}"/>
                                    <c:set var="docId" value="${claim.documentId}"/>
                                    <c:set var="claimDate" value="${claim.documentDate}"/>
                                    <c:set var="claimStatus" value="${claim.documentStatus}"/>
                                    <c:set var="productName" value="${claim.productName}"/>
                                    <c:set var="productCurrency" value="${claim.producCurrency}"/>
                                    <c:set var="prodCount" value="${claim.producCount}"/>

                                    <c:set var="prodSumm" value="${claim.prodSumm}"/>
                                    <c:set var="prodRate" value="${claim.prodRate}"/>
                                    <c:set var="prodDuration" value="${claim.prodDuration}"/>
                                    <c:set var="isCard" value="${claim.card}"/>
                                    <c:set var="isCredit" value="${claim.credit}"/>
                                    <c:choose>
                                        <c:when test="${prodCount < 7 and prodCount > 1}">
                                            <c:set var="claimLabelEnd">
                                                <bean:message key="list.label.orderName.end.${prodCount-1}" bundle="sbnkdBundle"/>
                                            </c:set>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="claimLabelEnd">
                                                <bean:message key="list.label.orderName.end.other" arg0="${prodCount-1}" bundle="sbnkdBundle"/>
                                            </c:set>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${isCard}">
                                            <c:choose>
                                                <c:when test="${isCredit}">
                                                    <c:set var="claimAction" value="/guest/payments/view.do?id=${docId}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${claimStatus == 'INIT' || claimStatus == 'INIT_NO_VIP'}">
                                                            <c:set var="claimAction" value="/guest/sberbankForEveryDay.do?id=${claimId}&fromHistory=true"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="claimAction" value="/private/sberbankForEveryDay/viewClaim.do?id=${claimId}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                            <tiles:insert definition="guestClaim" flush="false">
                                                <tiles:put name="type" value="credit_card"/>
                                                <tiles:put name="isGuest" value="true"/>
                                                <tiles:put name="name">
                                                    <html:link action="${claimAction}" styleClass="gp-nameLink">
                                                        <span class="word-wrap">
                                                            <c:choose>
                                                                <c:when test="${isCredit}">
                                                                    <bean:message key="list.label.orderName.creditCard" bundle="sbnkdBundle"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <bean:message key="list.label.orderName.card" bundle="sbnkdBundle"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                             ${productName}<c:if test="${prodCount > 1}"> ${claimLabelEnd}</c:if>
                                                        </span>
                                                    </html:link>
                                                </tiles:put>
                                                <c:if test="${prodCount == '1' and not empty productCurrency}">
                                                    <tiles:put name="info">
                                                        <bean:message key="field.currency.${productCurrency}" bundle="sbnkdBundle"/>
                                                    </tiles:put>
                                                </c:if>
                                                <tiles:put name="date">
                                                    <bean:message key="list.label.date.from" bundle="sbnkdBundle"/> ${phiz:formatDayWithStringMonthWithoutNought(claimDate)}
                                                </tiles:put>
                                            </tiles:insert>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:insert definition="guestClaim" flush="false">
                                                <tiles:put name="isGuest" value="true"/>
                                                <tiles:put name="type" value="credit"/>
                                                <tiles:put name="name">
                                                    <span onclick="edit(event, '${docId}', '${claimStatus}','${claim.formName}');">${productName}</span>
                                                </tiles:put>
                                                <tiles:put name="info">
                                                    <c:out value="${phiz:formatAmountWithoutCents(prodSumm)}"/>,&nbsp;<c:out value="${prodDuration}"/>&nbsp;мес.,&nbsp;<c:out value="${phiz:formatPercentRate(prodRate)}"/>
                                                </tiles:put>
                                                <tiles:put name="state">
                                                    <c:choose>
                                                        <c:when test="${claimStatus == 'DISPATCHED'}">
                                                            Исполняется банком
                                                        </c:when>
                                                        <c:when test="${claimStatus == 'APPROVED'}">
                                                            Одобрен
                                                        </c:when>
                                                        <c:when test="${claimStatus == 'REFUSED'}">
                                                            Отклонено банком
                                                        </c:when>
                                                        <c:when test="${claimStatus == 'ISSUED'}">
                                                            Выдан
                                                        </c:when>
                                                        <c:when test="${claimStatus == 'PREADOPTED'}">
                                                            Принято предварительное решение
                                                        </c:when>
                                                        <c:when test="${claimStatus == 'WAIT_TM'}">
                                                            Ожидание звонка сотрудника банка
                                                        </c:when>
                                                        <c:when test="${claimStatus == 'VISIT_OFFICE'}">
                                                            Требуется визит в отделение банка
                                                        </c:when>
                                                        <c:otherwise>
                                                            Не отправлено
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tiles:put>
                                                <tiles:put name="date">
                                                    <bean:message key="list.label.date.from" bundle="sbnkdBundle"/> ${phiz:formatDayWithStringMonthWithoutNought(claimDate)}
                                                </tiles:put>
                                            </tiles:insert>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>

                            <c:if test="${needShowAllClaimsButton}">
                                <div class="gp-showHideBtn align-center">
                                    <div id="buttonHideShow" class="gp-showHideButton gp-show">
                                        <div class="gp-name inlineBlock" id="textOfButtonHideShow">
                                            <bean:message key="button.showAllClaims" bundle="sbnkdBundle"/>
                                        </div>
                                        <span class="gp-dot inlineBlock"></span>
                                        <span class="gp-info inlineBlock">${claimListSize - claimListLimitSize}</span>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </c:if>

                    <div class="gp-optionsList">
                        <div class="optionsListTitle">
                            Воспользуйтесь другими продуктами банка
                        </div>
                        <div class="gp-option">
                            <a class="gp-left gp-takeCredit" href="${phiz:calculateActionURL(pageContext, '/guest/payments/payment.do?form=ExtendedLoanClaim')}"></a>
                            <div class="gp-right floatLeft">
                                <div class="gp-title">
                                    <span class="gp-ref">
                                        <a class="gp-a-ref" href="${phiz:calculateActionURL(pageContext, '/guest/payments/payment.do?form=ExtendedLoanClaim')}">Взять кредит в Сбербанке</a>
                                    </span>
                                </div>
                                <div class="gp-text">
                                    Потребительский кредит без обеспечения - оптимальный выбор, если<br/>
                                    вам нужен кредит до 1 500 000 рублей максимально быстро
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>

                        <div class="gp-separator"></div>

                        <c:set var="hasRightForLoanCardClaim" value="${phiz:impliesService('LoanCardClaim')}"/>
                        <c:set var="loanOfferClaimType"       value="${phiz:getLoanOfferClaimType()}"/>
                        <c:set var="isLoanCardClaimAvailable" value="${phiz:isLoanCardClaimAvailable(true)}"/>

                        <c:if test="${isLoanCardClaimAvailable and hasRightForLoanCardClaim}">
                            <div class="gp-option">
                            <a class="gp-left gp-claimCard"  href="${phiz:calculateActionURL(pageContext, '/guest/payments/payment.do?form=LoanCardClaim')}"></a>
                                <div class="gp-right floatLeft">
                                    <div class="gp-title">
                                        <span class="gp-ref">
                                        <c:choose>
                                            <c:when test="${empty loanOfferClaimType or empty loanOfferClaimType.id}">
                                                <a class="gp-a-ref" href="${phiz:calculateActionURL(pageContext, '/guest/payments/payment.do?form=LoanCardClaim')}">Заказать кредитную карту</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="gp-a-ref" href="${phiz:calculateActionURL(pageContext, '/guest/payments/payment.do?form=LoanCardClaim')}&offerId=${loanOfferClaimType.id}&offerType=${loanOfferClaimType.type}">Заказать кредитную карту</a>
                                            </c:otherwise>
                                        </c:choose>
                                        </span>
                                    </div>
                                    <div class="gp-text">
                                        Кредитная карта - это надёжный источник средств для покупок<br/>
                                        в любое время и в любом месте, а также доступ к различным<br/>
                                        привилегиям, скидкам и дополнительным услугам
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>
                            <div class="gp-separator"></div>
                        </c:if>

                        <div class="gp-option">
                            <div class="gp-option">
                                <a class="gp-left gp-claimCard" href="${phiz:calculateActionURL(pageContext, '/guest/sberbankForEveryDay')}"></a>
                                <div class="gp-right floatLeft">
                                    <div class="gp-title">
                                        <span class="gp-ref"><a  class="gp-a-ref" href="${phiz:calculateActionURL(pageContext, '/guest/sberbankForEveryDay')}">Заказать дебетовую карту</a></span>
                                    </div>
                                    <div class="gp-text">
                                        Дебетовая карта - идеальное решение для совершения покупок,<br/>
                                        переводов и получения наличных в России и за рубежом
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>

                    </div>
                </div>
                <%--<div class="gp-right floatRight">--%>
                    <%--<a href="${form.registrationURL}" target="_blank"><div class="gp-rightBanner"></div></a>--%>
                    <%--<div class="gp-help">--%>
                        <%--<tiles:insert page="/WEB-INF/jsp-sbrf/helpSection.jsp" flush="false"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="clear"></div>--%>
            </div>

            <c:if test="${needShowAllClaimsButton}">
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/claims.js"></script>
                <script type="text/javascript">
                    $(document).ready(function() {
                        claims.initWithFullData(${claimListLimitSize});
                    });
                </script>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>