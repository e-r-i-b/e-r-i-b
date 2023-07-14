<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/loans/list" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <tiles:insert definition="accountInfo">
        <tiles:put name="mainmenu" value="Loans"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="loanChoiceOperation" value="${phiz:impliesOperation('ChoiceLoanOperation', 'ShortLoanClaim') || phiz:impliesOperation('ChoiceLoanOperation', 'ExtendedLoanClaim')}"/>
            <c:set var="loanChoice" value="${loanChoiceOperation && phiz:takeCreditLinkAvailable(true)}"/>
            <c:set var="creditHistory" value="${phiz:impliesOperation('CreditReportOperation', 'CreditReportService')
                    && phiz:getCreditHistoryLinkAvailable()}"/>
            <c:set var="extendedLoanClaimAvailable" value="${phiz:isExtendedLoanClaimAvailable()}"/>
            <c:set var="needShowLoanClaims" value="${phiz:impliesOperation('ExtendedLoanClaimListOperation','ExtendedLoanClaim')}"/>

            <c:set var="loanOffer" value="${phiz:getLoanOffer(false)}"/>
            <c:if test="${not empty loanOffer}">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="infMesGreen"/>
                    <tiles:put name="data">
                        <div class="relative noticeBtn">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.confirmEnter"/>
                                <tiles:put name="commandHelpKey" value="button.confirmEnter"/>
                                <tiles:put name="bundle" value="offertBundle"/>
                                <tiles:put name="viewType" value="greenBorder css3"/>
                                <tiles:put name="action" value="/private/credit/offert/accept.do?appNum=${loanOffer['applicationNumber']}&claimId=${loanOffer['claimId']}"/>
                            </tiles:insert>
                        </div>
                        <div class="notice noticeTick">
                            <div class="noticeTitle">Вам одобрен кредит &laquo;<c:out value="${loanOffer['loanProductName']}"/>&raquo;</div>
                            Для получения кредита подтвердите ваше согласие с индивидуальными условиями кредитования
                        </div>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${(loanChoice && extendedLoanClaimAvailable) || creditHistory || needShowLoanClaims}">
                <div class="mainWorkspace productListLink">
            </c:if>

                <c:if test="${not empty form.extendedLoanClaims or not empty form.officeLoanClaims}">
                    <div name="claimsStatuses">
                        <script type="text/javascript">
                            function edit(event, paymentId, state, formName)
                            {
                                preventDefault(event);
                                <c:set var="u" value="/private/payments/default-action.do"/>
                                var url = "${phiz:calculateActionURL(pageContext,u)}";
                                window.location = url + "?id=" + paymentId + "&history=true" + "&objectFormName=" + formName + "&stateCode=" + state;
                            }
                            function openOfficeLoan(event, paymentId)
                            {
                                preventDefault(event);
                                <c:set var="u" value="/private/officeLoanClaim/show.do"/>
                                var url = "${phiz:calculateActionURL(pageContext,u)}";
                                window.location = url + "?applicationNumber=" + paymentId;
                            }
                        </script>
                            <c:if test="${form.offerSeccess}">
                                <div class="cardClaims">
                                    <tiles:insert definition="roundBorderLight" flush="false">
                                        <tiles:put name="color" value="infMesGreen"/>
                                        <tiles:put name="data">
                                            <div class="noticeCreditDone ">
                                                <div class="noticeTitle"><bean:message bundle="loanclaimBundle" key="label.offer.wait"/></div>
                                                <bean:message bundle="loanclaimBundle" key="label.offer.employee.communicate"/>.
                                            </div>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </c:if>
                            <div class="gp-claimsList cardClaims">
                                <div class="gp-title"><bean:message key="list.title" bundle="sbnkdBundle"/></div>
                                <div class="gp-claims">
                                    <c:forEach var="loan" items="${form.extendedLoanClaims}">
                                        <tiles:insert definition="guestClaim" flush="false">
                                            <tiles:put name="type" value="credit"/>
                                            <tiles:put name="name">
                                                <span onclick="edit(event, '${loan.id}', '${loan.state.code}','${loan.formName}');">${loan.productName}</span>
                                            </tiles:put>
                                            <tiles:put name="info">
                                                <c:set var="loanRate" value="${loan.loanRate}"/>
                                                <c:out value="${phiz:formatAmountWithoutCents(loan.loanAmount)}"/>,&nbsp;<c:out value="${loan.loanPeriod}"/>&nbsp;мес.<c:if test="${loanRate != null}">,</c:if>
                                                <c:if test="${loanRate != null}">
                                                    <c:out value="${phiz:formatLoanRate(loanRate)}"/>
                                                </c:if>
                                            </tiles:put>
                                            <tiles:put name="state">
                                                <c:set var="claimStatus" value="${loan.state}"/>
                                                <c:set var="inWaitTM" value="${loan.inWaitTM}"/>
                                                <c:choose>
                                                    <c:when test="${claimStatus == 'DISPATCHED'}">
                                                        Обрабатывается банком
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'APPROVED'}">
                                                        Кредит одобрен
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'PREADOPTED'}">
                                                        Кредит предварительно одобрен
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'APPROVED_MUST_CONFIRM'}">
                                                        Одобрено, требуется подтверждение для выдачи
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'OFFER_DISPATCHED'}">
                                                        Отправка акцепта оферты в Банк
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'ACCEPTED_WAIT_DELIVERY'}">
                                                        Акцептовано, ожидание выдачи кредита
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'VISIT_REQUIRED'}">
                                                        Необходим визит в отделение
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'REFUSED'}">
                                                        Заявка отклонена банком
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'ISSUED'}">
                                                        Кредит выдан
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'PREADOPTED'}">
                                                        Принято предварительное решение
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'WAIT_TM'}">
                                                        Ожидайте звонка сотрудника банка
                                                    </c:when>
                                                    <c:when test="${claimStatus == 'VISIT_OFFICE'}">
                                                        Требуется визит в отделение банка
                                                    </c:when>
                                                    <c:when test="${inWaitTM == 'true'}">
                                                        Ожидайте звонка сотрудника банка
                                                    </c:when>
                                                    <c:otherwise>
                                                        Черновик
                                                    </c:otherwise>
                                                </c:choose>
                                            </tiles:put>
                                            <tiles:put name="date">
                                                <bean:message key="list.label.date.from" bundle="sbnkdBundle"/> ${phiz:formatDayWithStringMonthWithoutNought(loan.dateCreated)}
                                            </tiles:put>
                                            <tiles:put name="type" value="credit"/>
                                        </tiles:insert>
                                    </c:forEach>
                                    <c:forEach var="officeLoan" items="${form.officeLoanClaims}">
                                        <tiles:insert definition="guestClaim" flush="false">
                                            <tiles:put name="type" value="credit"/>
                                            <tiles:put name="name">
                                                <span onclick="openOfficeLoan(event, '${officeLoan.applicationNumber}');">${officeLoan.productName}</span>
                                            </tiles:put>
                                            <tiles:put name="info">
                                                <c:set var="loanRate" value="${officeLoan.loanRate}"/>
                                                <c:out value="${phiz:formatAmountWithoutCents(officeLoan.loanAmount)}"/>,&nbsp;<c:out value="${officeLoan.loanPeriod}"/>&nbsp;мес.<c:if test="${loanRate != null}">,</c:if>
                                                <c:if test="${loanRate != null}">
                                                    <c:out value="${phiz:formatPercentRate(loanRate)}"/>
                                                </c:if>
                                            </tiles:put>
                                            <tiles:put name="state">
                                                <c:set var="claimStatus" value="${officeLoan.state}"/>
                                                <c:choose>
                                                    <c:when test="${claimStatus == -2}">
                                                        Системный сбой при создании заявки
                                                    </c:when>
                                                    <c:when test="${claimStatus == -1}">
                                                        Ошибки заполнения
                                                    </c:when>
                                                    <c:when test="${claimStatus == 0}">
                                                        Отказ
                                                    </c:when>
                                                    <c:when test="${claimStatus == 1}">
                                                        Заявка создана успешно
                                                    </c:when>
                                                    <c:when test="${claimStatus == 2}">
                                                        Кредит одобрен
                                                    </c:when>
                                                    <c:when test="${claimStatus == 3}">
                                                        Заявка на обработке
                                                    </c:when>
                                                    <c:when test="${claimStatus == 4}">
                                                        Кредит выдан
                                                    </c:when>
                                                    <c:when test="${claimStatus == 5}">
                                                        Принято предварительное решение
                                                    </c:when>
                                                    <c:when test="${claimStatus == 6}">
                                                        Ожидание выдачи кредита
                                                    </c:when>
                                                    <c:when test="${claimStatus == 7}">
                                                        Необходим визит в отделение
                                                    </c:when>
                                                    <c:otherwise>
                                                        Черновик
                                                    </c:otherwise>
                                                </c:choose>
                                            </tiles:put>
                                            <tiles:put name="date">
                                                <bean:message key="list.label.date.from" bundle="sbnkdBundle"/> ${phiz:formatDayWithStringMonthWithoutNought(officeLoan.agreementDate)}
                                            </tiles:put>
                                            <tiles:put name="type" value="credit"/>
                                        </tiles:insert>
                                    </c:forEach>
                                </div>
                                <c:if test="${form.haveNotShowClaims}">
                                    <div class="gp-showHideBtn align-center">
                                        <div id="buttonHideShow" class="gp-showHideButton gp-show">
                                            <span class="gp-name floatLeft" id="textOfButtonHideShow">Показать все заявки</span>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                    </div>
                </c:if>


            <c:if test="${loanChoice}">
                <c:if test="${phiz:isExtendedLoanClaimAvailable()}">
                    <c:set var="presentationURL" value="${phiz:calculateActionURL(pageContext, \"/private/asynch/offers/presentation.do\")}"/>
                    <c:set var="ajaxUrl" value="ajaxQuery('cardOffer=false', '${presentationURL}', function(data){},'','','','')"/>

                    <tiles:insert definition="paymentsPaymentCardsDiv" flush="false">
                        <tiles:put name="serviceId" value="ExtendedLoanClaim"/>
                        <tiles:put name="image" value="credit_64.png"/>
                        <tiles:put name="action" value="/private/payments/payment"/>
                        <tiles:put name="description" value="Возможность быстро и выгодно получить деньги на любые цели"/>
                        <tiles:put name="listPayTitle" value="Взять кредит в Сбербанке"/>
                        <tiles:put name="onclick" value="${ajaxUrl}"/>
                    </tiles:insert>
                </c:if>
            </c:if>
            <c:if test="${creditHistory}">
                <tiles:insert page="creditHistoryBlock.jsp" flush="false"/>
            </c:if>
            <c:if test="${(loanChoice && extendedLoanClaimAvailable) || creditHistory || needShowLoanClaims}">
                </div>
                <div class="clear"></div>
            </c:if>

            <c:choose>
                <c:when test="${not empty form.activeLoans || not empty form.blockedLoans}">
                    <jsp:include page="annLoanMessageWindow.jsp" flush="false"/>
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Кредиты"/>
                        <tiles:put name="data">
                            <c:if test="${not empty form.activeLoans}">
                                <c:set var="elementsCount" value="${fn:length(form.activeLoans)-1}"/>
                                <logic:iterate id="listElement" name="ShowAccountsForm" property="activeLoans" indexId="i">
                                    <c:set var="loanLink" value="${listElement}" scope="request"/>
                                    <c:choose>
                                        <c:when test="${phiz:isJmsForLoanAvailable()}">
                                            <div class = "loanTemplateWithJMS">
                                            <jsp:include page="/WEB-INF/jsp-sbrf/loans/loanTemplateWithJMS.jsp" flush="false"/>
                                        </c:when>
                                        <c:otherwise>
                                            <jsp:include page="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" flush="false"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${elementsCount != i}">
                                        <div class="productDivider"></div>
                                    </c:if>
                                </logic:iterate>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>

                    <c:if test="${not empty form.blockedLoans}">
                        <tiles:insert definition="hidableRoundBorder" flush="false">
                            <a id="closedLoans"><tiles:put name="title" value="Закрытые кредиты"/></a>
                            <tiles:put name="color" value="whiteTop"/>
                            <tiles:put name="data">
                                <c:set var="elementsCount" value="${fn:length(form.blockedLoans)-1}"/>
                                <logic:iterate id="listElement" name="ShowAccountsForm" property="blockedLoans" indexId="i">
                                    <c:set var="loanLink" value="${listElement}" scope="request"/>
                                    <%@ include file="/WEB-INF/jsp-sbrf/loans/loanTemplate.jsp" %>
                                    <c:if test="${elementsCount != i}">
                                        <div class="productDivider"></div>
                                    </c:if>
                                </logic:iterate>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${not form.allLoanDown}">
                        <div id="infotext">
                            <c:set var="creationType">${phiz:getPersonInfo().creationType}</c:set>
                            <c:choose>
                                <c:when test="${creationType == 'UDBO' || creationType == 'SBOL'}">
                                    <tiles:insert page="loansEmpty.jsp?emptyLoanOffer=${form.emptyLoanOffer}" flush="false"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:insert page="/WEB-INF/jsp-sbrf/needUDBO.jsp" flush="false">
                                        <tiles:put name="product" value="кредитам"/>
                                    </tiles:insert>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
            &nbsp;
            <c:if test="${form.haveNotShowClaims}">
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/claims.js"></script>
                <script type="text/javascript">
                    $(document).ready(claims.init('${phiz:calculateActionURL(pageContext,"/private/getAllExtendedLoanClaims")}', ${phiz:size(form.extendedLoanClaims)}));
                </script>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
