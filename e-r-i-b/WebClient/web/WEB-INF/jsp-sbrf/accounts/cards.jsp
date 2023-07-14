<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/cards/list" onsubmit="return setEmptyAction(event)">
    <tiles:importAttribute/>
    <tiles:insert definition="accountInfo">
        <tiles:put name="mainmenu" value="Cards"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="isLoanProduct" value="${phiz:impliesOperation('LoanOfferListOperation', 'LoanCardProduct') && phiz:isPublishedCreditCardProductsExists()}"/>
            <c:set var="loanOfferClaimType" value="${phiz:getLoanOfferClaimType()}"/>
            <c:set var="isVirtualCard" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'VirtualCardClaim') && phiz:activeCardProductExists('VIRTUAL')}"/>
            <c:set var="availableSBNKD" value="${phiz:availableSBNKD()}"/>
            <%--есть ли право "Заявка на предодобренную кредитную карту (новый механизм)"--%>
            <c:set var="haveRightForLoanCardClaim" value="${phiz:impliesService('LoanCardClaim')}"/>
            <c:set var="isLoanCardClaimAvailable" value="${phiz:isLoanCardClaimAvailable(false)}"/>
            <c:set var="haveLoanCardClaim" value="${!empty form.loanCardClaimList}"/>

            <c:if test="${isLoanProduct || availableSBNKD || isVirtualCard || phiz:impliesServiceRigid('ReIssueCardClaim')}">
                <div class="mainWorkspace productListLink">
                    <c:if test="${(availableSBNKD and not (empty form.claimSBNKDData)) or haveLoanCardClaim}">
                        <div class="gp-claimsList cardClaims">
                            <div class="gp-title"><bean:message key="list.title" bundle="sbnkdBundle"/></div>
                            <div class="gp-claims">
                                <%--Заявки на дебетовые карты--%>
                                <c:if test="${not empty form.claimSBNKDData}">
                                    <c:forEach var="cardInfo" items="${form.claimSBNKDData}" varStatus="lineInfo">
                                        <c:set var="claimId" value="${cardInfo[0]}"/>
                                        <c:set var="claimDate" value="${cardInfo[1]}"/>
                                        <c:set var="claimStatus" value="${cardInfo[2]}"/>
                                        <c:set var="firstCardName" value="${cardInfo[3]}"/>
                                        <c:set var="firstCardCurrency" value="${cardInfo[4]}"/>
                                        <c:set var="cardsCount" value="${cardInfo[5]}"/>
                                        <c:choose>
                                            <c:when test="${cardsCount < 7 and cardsCount > 1}">
                                                <c:set var="claimLabelEnd">
                                                    <bean:message key="list.label.orderName.end.${cardsCount-1}" bundle="sbnkdBundle"/>
                                                </c:set>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="claimLabelEnd">
                                                    <bean:message key="list.label.orderName.end.other" arg0="${cardsCount-1}" bundle="sbnkdBundle"/>
                                                </c:set>
                                            </c:otherwise>
                                        </c:choose>

                                        <tiles:insert definition="guestClaim" flush="false">
                                            <tiles:put name="name">
                                                <div class="gp-name gp-ref">
                                                    <c:choose>
                                                        <c:when test="${claimStatus == 'INIT' || claimStatus == 'INIT_NO_VIP'}">
                                                            <c:set var="sbnkdClaimAction" value="/private/sberbankForEveryDay.do?id=${claimId}&fromHistory=true"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="sbnkdClaimAction" value="/private/sberbankForEveryDay/viewClaim.do?id=${claimId}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <html:link action="${sbnkdClaimAction}" styleClass="gp-nameLink">
                                                        <span class="word-wrap">
                                                            <bean:message key="list.label.orderName.card" bundle="sbnkdBundle"/> ${firstCardName}<c:if test="${cardsCount > 1}"> ${claimLabelEnd}</c:if>
                                                        </span>
                                                    </html:link>
                                                </div>
                                            </tiles:put>
                                            <c:if test="${cardsCount == '1' and not empty firstCardCurrency}">
                                                <tiles:put name="info">
                                                    <bean:message key="field.currency.${firstCardCurrency}" bundle="sbnkdBundle"/>
                                                </tiles:put>
                                            </c:if>
                                            <tiles:put name="state">
                                                <c:set var="cardStateClass" value="cardClaimListState"/>
                                                <c:if test="${claimStatus == 'ERROR'}"><c:set var="cardStateClass" value="cardClaimListState cardClaimErrorState"/></c:if>
                                                <div class="${cardStateClass}">
                                                    <c:choose>
                                                        <c:when test="${cardsCount == '1'}">
                                                            <bean:message key="label.claimState.${claimStatus}" bundle="sbnkdBundle"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <bean:message key="list.label.orderNumber" bundle="sbnkdBundle"/> ${claimId}<br/>
                                                            <c:if test="${cardsCount > 1}">
                                                                ${cardsCount} <bean:message key="list.label.orderCards" bundle="sbnkdBundle"/>
                                                            </c:if>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </tiles:put>
                                            <tiles:put name="date">
                                                <bean:message key="list.label.date.from" bundle="sbnkdBundle"/> ${phiz:formatDayWithStringMonthWithoutNought(claimDate)}
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${haveLoanCardClaim}">
                                    <%--Заявки на кредитные карты--%>
                                    <c:forEach var="cardClaim" items="${form.loanCardClaimList}" varStatus="cardLineInfo">
                                        <c:set var="cardClaimId" value="${cardClaim.id}"/>
                                        <c:set var="cardName" value="${cardClaim.creditCard}"/>
                                        <tiles:insert definition="guestClaim" flush="false">
                                            <tiles:put name="name">
                                                <div class="gp-name gp-ref">
                                                    <c:set var="sbnkdClaimAction" value="private/payments/view.do?id=${cardClaimId}"/>
                                                    <html:link action="${sbnkdClaimAction}" styleClass="gp-nameLink">
                                                        <span class="word-wrap">
                                                            <bean:message key="list.label.orderName.creditCard" bundle="sbnkdBundle"/> ${cardName}
                                                        </span>
                                                    </html:link>
                                                </div>
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${isLoanCardClaimAvailable}">
                        <c:choose>
                            <c:when test="${empty loanOfferClaimType or empty loanOfferClaimType.id}">
                                <%--Заявка на оформление кредитной карты на общих условиях--%>
                                <tiles:insert definition="paymentsPaymentCardsDiv" service="LoanCardClaim" operation="LoanOfferListOperation" flush="false">
                                    <tiles:put name="serviceId" value="LoanCardClaim"/>
                                    <tiles:put name="globalImage" value="guest/icon_creditCardAdd.jpg"/>
                                    <tiles:put name="action" value="/private/payments/payment"/>
                                    <tiles:put name="listPayTitle" value="Заказать кредитную карту"/>
                                    <tiles:put name="description" value="и пользоваться возобновляемым кредитом без процентов"/>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                    <c:set var="presentationURL" value="${phiz:calculateActionURL(pageContext, \"/private/asynch/offers/presentation.do\")}"/>
                                    <c:set var="ajaxUrl" value="ajaxQuery('id=${loanOfferClaimType.id}&cardOffer=true', '${presentationURL}', function(data){},'','','','')"/>
                                    <tiles:insert definition="paymentsPaymentCardsDiv" service="LoanCardClaim" flush="false">
                                        <tiles:put name="serviceId" value="LoanCardClaim"/>
                                        <tiles:put name="globalImage" value="guest/icon_creditCardAdd.jpg"/>
                                        <tiles:put name="action" value="/private/payments/payment"/>
                                        <tiles:put name="listPayTitle" value="Заказать кредитную карту"/>
                                        <tiles:put name="listPayTitle" value="Заказать кредитную карту"/>
                                        <tiles:put name="description" value="и пользоваться возобновляемым кредитом без процентов"/>
                                        <tiles:putList name="params">
                                            <tiles:putList name="names">
                                                <tiles:add>offerId</tiles:add>
                                                <tiles:add>offerType</tiles:add>
                                                <tiles:add>preapproved</tiles:add>
                                            </tiles:putList>
                                            <tiles:putList name="values">
                                                <tiles:add>${loanOfferClaimType.id}</tiles:add>
                                                <tiles:add>${loanOfferClaimType.type}</tiles:add>
                                                <tiles:add>${true}</tiles:add>
                                            </tiles:putList>
                                        </tiles:putList>
                                        <tiles:put name="onclick" value="${ajaxUrl}"/>
                                    </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${isVirtualCard}">
                        <tiles:insert definition="paymentsPaymentCardsDiv" service="VirtualCardClaim"
                                      operation="CreateFormPaymentOperation"
                                      flush="false">
                            <tiles:put name="serviceId" value="VirtualCardClaim"/>
                            <tiles:put name="action" value="/private/payments/payment"/>
                            <tiles:put name="globalImage" value="payment/iconPmntList_virtualCard.png"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${availableSBNKD}">
                        <tiles:insert definition="paymentsPaymentCardsDiv" flush="false">
                            <tiles:put name="serviceId" value="CreateSberbankForEveryDayClaimService"/>
                            <tiles:put name="globalImage" value="guest/icon_creditCardAdd.jpg"/>
                             <tiles:put name="action" value="/private/sberbankForEveryDay"/>
                            <tiles:put name="notForm" value="true"/>
                            <tiles:put name="listPayTitle"><bean:message key="link.title" bundle="sbnkdBundle"/></tiles:put>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${phiz:impliesServiceRigid('ReIssueCardClaim')}">
                        <tiles:insert definition="paymentsPaymentCardsDiv" service="ReIssueCardClaim"
                                      flush="false">
                            <tiles:put name="serviceId" value="ReIssueCardClaim"/>
                            <tiles:put name="image" value="card_64.png"/>
                            <tiles:put name="action" value="/private/payments/payment"/>
                        </tiles:insert>
                    </c:if>
                </div>
            </c:if>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Карты"/>
                <c:if test="${not empty form.activeCards}">
                    <tiles:put name="data">
                        <c:set var="cardInfoLink" value="true" scope="request"/>
                        <c:set var="elementsCount" value="${fn:length(form.activeCards)-1}"/>
                        <logic:iterate id="listElement" name="ShowAccountsForm" property="activeCards" indexId="i">
                            <c:set var="cardLink" value="${listElement}" scope="request"/>
                            <c:if test="${cardLink.main}">
                                <c:set var="mainNumber" value="${cardLink.number}"/>
                                <c:set var="countAdditionalCards" value="${cardLink.countAdditionalCards}"/>
                                <c:set var="countAddCards" value="-1"/>
                            </c:if>
                            <c:set var="showArrow" value="${not empty mainNumber and cardLink.mainCardNumber eq mainNumber}" />
                            <c:set var="resourceAbstract" value="${cardAbstract[listElement]}" scope="request"/>
                            <%@ include file="cardTemplate.jsp" %>
                            <c:set var="countAddCards" value="${countAddCards+1}"/>
                            <c:if test="${elementsCount != i && (empty countAdditionalCards || countAdditionalCards <= countAddCards)}">
                                <div class="productDivider"></div>
                            </c:if>
                        </logic:iterate>
                    </tiles:put>
                </c:if>
            </tiles:insert>

            <c:if test="${not empty form.blockedCards}">
                <tiles:insert definition="hidableRoundBorder" flush="false">
                    <a id="closedCards"><tiles:put name="title" value="Заблокированные карты"/></a>
                    <tiles:put name="data">
                        <c:set var="cardInfoLink" value="true" scope="request"/>
                        <c:set var="elementsCount" value="${fn:length(form.blockedCards)-1}"/>
                        <c:set var="countAdditionalCards" value=""/>
                        <logic:iterate id="listElement" name="ShowAccountsForm" property="blockedCards" indexId="i">
                            <c:set var="cardLink" value="${listElement}" scope="request"/>
                            <c:if test="${cardLink.main}">
                                <c:set var="mainNumber" value="${cardLink.number}"/>
                                <c:set var="countAdditionalCards" value="${cardLink.countAdditionalCards}"/>
                                <c:set var="countAddCards" value="-1"/>
                            </c:if>
                            <c:set var="showArrow" value="${not empty mainNumber and cardLink.mainCardNumber eq mainNumber}" />
                            <c:set var="resourceAbstract" value="${cardAbstract[listElement]}" scope="request"/>
                            <%@ include file="cardTemplate.jsp" %>
                            <c:set var="countAddCards" value="${countAddCards+1}"/>
                            <c:if test="${elementsCount != i && (empty countAdditionalCards || countAdditionalCards <= countAddCards)}">
                                <div class="productDivider"></div>
                            </c:if>
                        </logic:iterate>
                    </tiles:put>
                </tiles:insert>
            </c:if>

            <c:if test="${phiz:isScriptsRSAActive()}">
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/hashtable.js"></script>
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa.js"></script>
                <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/rsa-support.js"></script>

                <%-- подготовка данных по deviceTokenFSO для ВС ФМ --%>
                <%@ include file="/WEB-INF/jsp/common/monitoring/fraud/pmfso-support.jsp"%>

                <script type="text/javascript">
                    <%-- формирование основных данных для ФМ --%>
                    new RSAObject().toHiddenParameters();
                </script>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>