<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<tiles:importAttribute/>
<%--
    loanOffers - коллекция предодобр. заявок на кредит
    loanCardOffers - коллекция предодобр. заявок на кред. карты
    loanOffersNoInformed - коллекция идентификаторов пред.заявок на кредит, с неотправленным откликом "Проинформирован"
    loanCardOffersNoInformed - коллекция идентификаторов пред.кредитных карт, с неотправленным откликом "Проинформирован"
    loanOffersNoPresentation - коллекция идентификаторов пред.заявок на кредит, с неотправленным откликом "Презентация"
    loanCardOffersNoPresentation - коллекция идентификаторов пред.кредитных карт, с неотправленным откликом "Презентация"
    colNum - количество столбцов 2 или 3
--%>

<%--если кредитные предложение переданны--%>
<c:if test="${not empty loanOffers or not empty loanCardOffers}">
    <tiles:insert definition="barleyBreak" flush="false">
        <tiles:put name="totalColums" value="2"/>
        <tiles:put name="styleClass" value="loanOfferBlock"/>
        <tiles:putList name="data">

            <c:set var="useNewAlgorithm" value="${phiz:isUseNewLoanClaimAlgorithm()}"/>
            <c:choose>
                <c:when test="${useNewAlgorithm}">
                    <c:set var="allLoanOfferUrl" value="/private/payments/payment.do?form=ExtendedLoanClaim"/>
                </c:when>
                <c:otherwise>
                    <c:set var="allLoanOfferUrl" value="/private/payments/loan_offer.do"/>
                </c:otherwise>
            </c:choose>
            <c:set var="presentationURL" value="${phiz:calculateActionURL(pageContext, \"/private/asynch/offers/presentation.do\")}"/>
            <c:if test="${not empty loanOffers and phiz:isExtendedLoanClaimAvailable() and phiz:isPreapprovedLoanClaimAvailable()}">
                <c:forEach var="loanOffer" items="${loanOffers}">
                    <tiles:add>
                        ${loanOffer.productName}
                        <tiles:insert definition="roundBorder" flush="false">
                            <tiles:put name="color" value="lightGreen"/>
                            <tiles:put name="data">
                                <b>${phiz:formatAmount(loanOffer.maxLimit)}</b>&nbsp;
                                <br/>под ${loanOffer.percentRate}% на срок до ${loanOffer.duration} мес.
                            </tiles:put>
                        </tiles:insert>
                        <div class="buttonsArea">
                            <c:set var="actionLoanOfferUrl" value="ajaxQuery('id=${loanOffer.offerId}&cardOffer=false', '${presentationURL}', redirectToLoanOffer('${phiz:calculateActionURL(pageContext, allLoanOfferUrl)}'),'','','','')"/>
                            <tiles:insert definition="clientButton" flush="fase">
                                <tiles:put name="commandTextKey" value="button.get"/>
                                <tiles:put name="commandHelpKey" value="button.get"/>
                                <tiles:put name="bundle" value="commonBundle"/>
                                <c:choose>
                                    <c:when test="${phiz:contains(loanOffersNoPresentation, loanOffer.offerId)}">
                                        <tiles:put name="onclick" value="${actionLoanOfferUrl}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:put name="action" value="${allLoanOfferUrl}"/>
                                    </c:otherwise>
                                </c:choose>

                            </tiles:insert>
                        </div>
                    </tiles:add>
                </c:forEach>
            </c:if>


            <c:set var="isPreapprovedLoanOffer" value="${not empty phiz:getLoanOfferClaimType() and phiz:getLoanOfferClaimType().type != 'LoanCardProduct'}"/>
            <%--Проверка наличия права "Заявка на предодобренную кредитную карту (новый механизм)"--%>
            <c:set var="haveRightForPreapprovedLoanCardClaim" value="${phiz:impliesService('PreapprovedLoanCardClaim')}"/>
            <c:set var="haveRightForLoanCardClaim" value="${phiz:impliesService('PreapprovedLoanCardClaim')}"/>
            <%--Проверка наличия права "Заявка на предодобренную кредитную карту (старый механизм)"--%>
            <c:set var="haveRightForLoanCardOffer" value="${phiz:impliesService('LoanCardOffer')}"/>
            <c:set var="isLoanCardClaimAvailable" value="${phiz:isLoanCardClaimAvailable(true)}"/>
            <c:set var="useCRM" value="${phiz:impliesService('GetPreapprovedOffersService')}"/>
            <c:set var="availableChangeLimit" value="${phiz:impliesService('ChangeCreditLimitClaim')}"/>
            <c:if test="${not empty loanCardOffers and
                          ( (!useNewAlgorithm and haveRightForLoanCardOffer) ||
                            (useNewAlgorithm and isPreapprovedLoanOffer and haveRightForLoanCardClaim) )}">
                <c:forEach var="loanCardOffer" items="${loanCardOffers}">
                    <c:set var="type" value="${loanCardOffer.offerType.value}" scope="request"/>
                    <c:if test="${isLoanCardClaimAvailable || type != 1}">
                    <tiles:add>
                        <c:if test="${type eq '2' and !useCRM or type eq '3' }">
                            <c:set var="creditCard" value="${phiz:getClientCreditCard()}"/>
                            ${creditCard.name}
                        </c:if>
                        <div class="pruductImg">
                            <c:choose>
                                <c:when test="${type=='2' && useCRM && availableChangeLimit}">
                                    <img src="${globalUrl}/commonSkin/images/products/creditOffer64.png"/>
                                </c:when>
                                <c:when test="${type=='2' && !useCRM }">
                                    <img src="${skinUrl}/images/cards_type/icon_cards_m64.gif"/>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="bankOfferText">
                                <c:choose>
                                    <c:when test="${type=='1'}">
                                        Мы одобрили Вам кредитную карту с <br/>
                                        лимитом
                                        ${phiz:formatAmountWithoutCents(loanCardOffer.maxLimit)} на <br/> индивидуальных условиях!
                                        <br/>
                                    </c:when>
                                    <c:when test="${availableChangeLimit && type=='2' && useCRM}">
                                        <span class="offertText">Предлагаем Вам новый лимит по кредитной карте!</span>
                                    </c:when>
                                    <c:when test="${type=='2' && !useCRM}">
                                        Предлагаем Вам увеличить лимит по Вашей кредитной карте ${creditCard.name}
                                        <br/> до
                                        <b>${phiz:formatAmountWithoutCents(loanCardOffer.maxLimit)}</b>
                                    </c:when>
                                    <c:when test="${type=='3'}">
                                        Предлагаем Вам изменить тип Вашей кредитной карты ${creditCard.name}, а так же увеличить кредитный лимит
                                        <br/> до
                                        <b>${phiz:formatAmountWithoutCents(loanCardOffer.maxLimit)}</b>
                                    </c:when>
                                </c:choose>
                        </div>
                        <div class="clear"></div>
                        <c:if test="${type eq '1'}">
                            <c:choose>
                                <c:when test="${useNewAlgorithm}">
                                    <c:set var="action" value="/private/payments/payment.do?form=LoanCardClaim&offerId=${loanCardOffer.offerId}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="action" value="/private/payments/loan_card_offer.do"/>
                                </c:otherwise>
                            </c:choose>
                            <div class="buttonsArea">
                               <c:set var="actionLoanCardOfferUrl" value="ajaxQuery('id=${loanCardOffer.offerId}&cardOffer=true', '${presentationURL}', redirectToLoanOffer('${phiz:calculateActionURL(pageContext, action)}'),'','','','')"/>
                                <tiles:insert definition="clientButton" flush="fase">
                                    <tiles:put name="commandTextKey" value="button.get"/>
                                    <tiles:put name="commandHelpKey" value="button.get"/>
                                    <tiles:put name="bundle" value="commonBundle"/>
                                    <c:choose>
                                        <c:when test="${phiz:contains(loanCardOffersNoPresentation, loanCardOffer.offerId)}">
                                            <tiles:put name="onclick" value="${actionLoanCardOfferUrl}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:put name="action" value="${action}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </tiles:insert>
                            </div>
                        </c:if>
                        <c:if test="${type eq '2'  }">
                            <div class="buttonsArea">
                                <c:choose>
                                    <c:when test="${useCRM && availableChangeLimit}">
                                        <tiles:insert definition="clientButton" flush="fase">
                                            <tiles:put name="commandTextKey" value="button.view"/>
                                            <tiles:put name="commandHelpKey" value="button.view"/>
                                            <tiles:put name="bundle" value="loanOfferBundle"/>
                                            <tiles:put name="action" value="/private/payments/payment.do?form=ChangeCreditLimitClaim"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:when test="${!useCRM}">
                                        <tiles:insert definition="clientButton" flush="fase">
                                            <tiles:put name="commandTextKey" value="button.get"/>
                                            <tiles:put name="commandHelpKey" value="button.get"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="action" value="/private/payments/loan_card_offer.do"/>
                                        </tiles:insert>
                                    </c:when>
                                </c:choose>
                            </div>
                        </c:if>
                        <c:if test="${type eq '3'}">
                            <div class="buttonsArea">
                                <tiles:insert definition="clientButton" flush="fase">
                                    <tiles:put name="commandTextKey" value="button.get"/>
                                    <tiles:put name="commandHelpKey" value="button.get"/>
                                    <tiles:put name="bundle" value="commonBundle"/>
                                    <tiles:put name="action" value="/private/payments/loan_card_offer.do?changeType=true"/>
                                </tiles:insert>
                            </div>
                        </c:if>
                    </tiles:add>
                    </c:if>
                </c:forEach>
            </c:if>
            <c:set var="informedURL" value="${phiz:calculateActionURL(pageContext, \"/private/asynch/offers/informed.do\")}"/>
            <script type= "text/javascript">
                doOnLoad(function()
                {
                    <c:forEach var="loanOffersNoInformed" items="${loanOffersNoInformed}">
                        ajaxQuery('id=${loanOffersNoInformed}&cardOffer=false', '${informedURL}', '','','','','');
                    </c:forEach>
                    <c:forEach var="loanCardOffersNoInformed" items="${loanCardOffersNoInformed}">
                        ajaxQuery('id=${loanCardOffersNoInformed}&cardOffer=true', '${informedURL}', '','','','','');
                    </c:forEach>
                })

                function redirectToLoanOffer(url)
                {
                    loadNewAction('','');
                    window.location=url;
                }
            </script>
        </tiles:putList>
    </tiles:insert>
</c:if>


