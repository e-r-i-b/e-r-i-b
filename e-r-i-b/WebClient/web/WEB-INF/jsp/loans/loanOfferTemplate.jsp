<%--
  User: Moshenko
  Date: 27.05.2011
  Time: 11:15:42
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<tiles:importAttribute/>

<%--
Переменные: Отображение предодобренных продуктов, кредитов и кредитных карт
  loanOffer - предодобренный продукт. Допустимые типы, LoanOffer или LoanCardOffer
  isCard - признак того что передается LoanCardOffer. 
  type - тип предложение.
--%>

<c:set var="useNewAlgorithm" value="${phiz:isUseNewLoanClaimAlgorithm()}"/>
<c:set var="isNotPreapprovedLoanOffer" value="${empty phiz:getLoanOfferClaimType() or phiz:getLoanOfferClaimType().type == 'LoanCardProduct'}"/>
<c:if test="${loanOffer!=null and not (isCard and useNewAlgorithm and isNotPreapprovedLoanOffer)}">

    <c:choose>
        <c:when test="${isCard}">
            <c:set var="duration" value="&nbsp"/>
            <c:set var="offerMessage" value="Кредитный лимит."/>

            <c:if test="${type eq '2' or type eq '3'}">
                <c:set var="creditCardLink" value="${phiz:getClientCreditCard()}"/>
                <c:set var="description" value="${creditCardLink.description}"/>
                <c:set var="imgCode" value="${phiz:getCardImageCode(description)}"/>

                <c:set var="imgName" value="cards_type/icon_cards_${imgCode}64.gif"/>
                <c:set var="loanProductName" value="${creditCardLink.name}"/>

                <c:set var="addOfferInfo" value="${phiz:getCutCardNumber(creditCardLink.number)}"/>
            </c:if>
            <c:choose>
                <c:when test="${type=='1'}">
                    <c:set var="imgName" value="cards_type/icon_cards_m64.gif"/>
                    <c:set var="loanProductName" value="Ваша кредитная карта"/>
                </c:when>
                <c:when test="${type=='3'}">
                    <c:set var="loanProductName" value="${creditCardLink.name}"/>
                    <c:set var="duration" value="Вы можете изменить тип карты и ее кредитный лимит"/>
                </c:when>
            </c:choose>
        </c:when>
        <c:otherwise>
            <c:set var="imgName" value="credit_type/icon_creditDiffer.jpg"/>
            <c:set var="loanProductName" value="${loanOffer.productName}"/>
            <c:set var="duration" value="на срок до ${loanOffer.duration} мес."/>
            <c:set var="addOfferInfo" value="${loanOffer.percentRate}%"/>
            <c:set var="offerMessage" value="Сумма кредита."/>
        </c:otherwise>
    </c:choose>

    <c:set var="loanMaxLimit" value="${loanOffer.maxLimit}"/>

    <div class="loan-offer-template">
        <tiles:insert definition="productTemplate" flush="false">
            <tiles:put name="img" value="${imagePath}/${imgName}"/>
            <tiles:put name="title" value="${loanProductName}"/>
            <tiles:put name="additionalProductInfo" value="${addOfferInfo}"/>
            <tiles:put name="additionalData">
                <div class = "addInfo">${duration}</div>
            </tiles:put>
            <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
            <tiles:put name="centerData">
                <span class="overallAmount">${phiz:formatAmount(loanMaxLimit)}</span><br />
                <span class="amountStatus">${offerMessage}</span>
            </tiles:put>

            <div id="test" style="font-size: 10px">
                <tiles:put name="rightData">
                    <c:choose>
                        <c:when test="${(isCard && (type eq '1' || type eq '3' || type eq '2')) || !isCard}">
                            <c:if test="${isCard && type eq '1'}">
                                <c:choose>
                                    <c:when test="${useNewAlgorithm}">
                                        <c:set var="action" value="/private/payments/payment.do?form=LoanCardClaim&offerId=${loanOffer.offerId}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="action" value="/private/payments/loan_card_offer.do"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${isCard && type eq '3'}">
                                <c:set var="action" value="/private/payments/loan_card_offer.do?changeType=true"/>
                           </c:if>
                           <c:if test="${isCard && type eq '2'}">
                               <c:set var="action" value="/private/payments/loan_card_offer.do"/>
                            </c:if>
                            <c:if test="${!isCard}">
                                <c:set var="useNewAlgorithm" value="${phiz:isUseNewLoanClaimAlgorithm()}"/>
                                <c:choose>
                                    <c:when test="${useNewAlgorithm}">
                                        <c:set var="action" value="/private/payments/payment.do?form=ExtendedLoanClaim"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="action" value="/private/payments/loan_offer.do"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.issue"/>
                                    <tiles:put name="commandHelpKey" value="button.issue.help"/>
                                    <tiles:put name="bundle" value="commonBundle"/>
                                    <tiles:put name="action" value="${action}"/>
                                    <tiles:put name="viewType" value="buttonGrayNew"/>
                                    <tiles:put name="width" value="80%"/>
                                </tiles:insert>
                        </c:when>
                        <c:otherwise>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.issue"/>
                                <tiles:put name="commandHelpKey" value="button.issue.help"/>
                                <tiles:put name="bundle" value="commonBundle"/>
                                <tiles:put name="action" value="/private/loan/loanoffer/show.do?loan=${loanOffer.offerId}"/>
                                <tiles:put name="viewType" value="buttonGrayNew"/>
                                <tiles:put name="width" value="80%"/>
                            </tiles:insert>
                        </c:otherwise>
                    </c:choose>
                </tiles:put>
            </div>
        </tiles:insert>
    </div>
</c:if>

