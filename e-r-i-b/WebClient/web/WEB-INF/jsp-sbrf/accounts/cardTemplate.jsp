<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 04.05.2010
  Time: 10:19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%--
Внешние переменные, используемые в данном файле
bottomDataInfo - данные низа. Если не пустое, то отображается содержимое данной переменной, а опереции не отображаются.
cardInfoLink  - если true, тогда картинка и другие элементы будут кликабильные и будут вести на детальную страницу
page - наименование страницы
showInMainCheckBox - признак необходимости вывода чекбокса "показать на главной"
cardLink - cardLink текущей карты
resourceAbstract - текущей карты
showArrow - показывать стрелочки у дополнительных карт
showInThisWidgetCheckBox - признак необходимости вывода чекбокса "показывать в этом виджете"
--%>

<tiles:importAttribute/>

<!-- Информация по карте -->
<c:if test="${cardLink != null}">
<c:set var="card" value="${cardLink.card}"/>
<c:set var="isArrested" value="${card.cardAccountState != null && card.cardAccountState == 'ARRESTED'}"/>
<c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
<c:set var="isVirtual" value="${card.virtual}"/>
<c:set var="isMain" value="${cardLink.main}"/>
<c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
<c:set var="description" value="${cardLink.description}"/>
<c:set var="addLink" value="${cardInfoLink && not empty cardLink.id}"/>
<c:set var="cardNumber">${phiz:getCutCardNumber(cardLink.number)}</c:set>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>

<c:choose>
    <c:when test="${not empty card.cardLevel and card.cardLevel == 'MQ'}">
        <c:set var="imgCode" value="mq" />
    </c:when>
    <c:otherwise>
        <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
    </c:otherwise>
</c:choose>

<tiles:insert definition="productTemplate" flush="false">
<tiles:put name="productViewBacklight" value="false"/>
<c:if test="${showArrow and !isMain}">
    <tiles:put name="arrow" value="true"/>
</c:if>
<c:choose>
    <c:when test="${card.cardState != 'active'}">
        <tiles:put name="img" value="${imagePath}/cards_type/icon_cards_${imgCode}64Blocked.jpg"/>
    </c:when>
    <c:otherwise>
        <tiles:put name="img" value="${imagePath}/cards_type/icon_cards_${imgCode}64.gif"/>
    </c:otherwise>
</c:choose>

<c:if test="${isMain}">
    <tiles:put name="productImgAdditionalData">
        <div class="cardType">
            ${card.cardType.displayDescription}
        </div>
    </tiles:put>
</c:if>

<tiles:put name="alt" value="${description}"/>
<tiles:put name="title"><c:out value="${phiz:getCardUserName(cardLink)}"/></tiles:put>
<tiles:put name="productNumbers">
    <c:choose>
        <c:when test="${addLink}">
            <div class="accountNumber decoration-none">${cardNumber}, действует по ${phiz:formatExpirationCardDate(cardLink)}</div>
        </c:when>
        <c:otherwise>
            <div class="accountNumber">${cardNumber}, действует по ${phiz:formatExpirationCardDate(cardLink)}</div>
        </c:otherwise>
    </c:choose>
</tiles:put>
<c:choose>
    <c:when test="${page=='cardsDetail'}">
        <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
        <tiles:put name="operationsBlockPosition" value="rightPosition"/>
        <tiles:put name="additionalProductClass" value="mainProductDetailInfo"/>
    </c:when>
    <c:otherwise>
        <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
        <tiles:put name="additionalProductClass" value="mainProductInfo"/>
    </c:otherwise>
</c:choose>

<tiles:put name="comment">
    <c:choose>
        <c:when test="${isMain  && !isVirtual}">
            <c:if test="${countAdditionalCards > 0}">
                <nobr>&nbsp;<bean:message key="card.label.isMain" bundle="cardInfoBundle"/></nobr>
            </c:if>
        </c:when>
        <c:when test="${!isMain && !isVirtual}"><nobr>&nbsp;<bean:message key="card.label.isAdditional" bundle="cardInfoBundle"/></nobr></c:when>
        <c:otherwise><nobr>&nbsp;<bean:message key="card.label.isVirtual" bundle="cardInfoBundle"/></nobr></c:otherwise>
    </c:choose>
</tiles:put>

<c:if test="${addLink}">
    <tiles:put name="src" value="${cardInfoUrl}${cardLink.id}"/>
</c:if>
<%--LEFT--%>
<tiles:put name="leftData">
    <c:if test="${isMain && not empty card && (card.cardType == 'credit' || card.cardType == 'overdraft')}">
        <table class="productDetail">
            <%--Отображение "Лимит овердрафта" и "Обязательный платеж" для овердрафтных завязываем на значение "Лимит овердрафта"--%>
            <c:if test="${not empty card.overdraftLimit && (card.cardType == 'credit' || (card.cardType == 'overdraft' && card.overdraftLimit.decimal>0))}">
                <tr>
                    <td>
                        <div class="available">
                            <c:choose>
                                <c:when test="${card.cardType == 'credit'}">
                                    <bean:message key="card.overdraft.limit.credit" bundle="cardInfoBundle"/>
                                </c:when>
                                <c:otherwise>
                                    <bean:message key="card.overdraft.limit.overdraft" bundle="cardInfoBundle"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </td>
                    <td class="value align-right">
                        <div class="available bold">
                            <nobr>${phiz:formatAmountWithoutCents(card.overdraftLimit)}</nobr>
                        </div>
                    </td>
                </tr>
            </c:if>
            <c:if test="${not empty card.overdraftMinimalPayment && (card.cardType == 'credit' || (card.cardType == 'overdraft' && not empty card.overdraftLimit && card.overdraftLimit.decimal>0))}">
                <tr>
                    <td>
                        <div class="available">
                            <bean:message key="card.overdraft.minimalPayment" bundle="cardInfoBundle"/>
                        </div>
                    </td>
                    <td class="value align-right">
                        <div class="available bold cardData">
                            <nobr>${phiz:formatAmount(card.overdraftMinimalPayment)}</nobr>
                        </div>
                    </td>
                </tr>
            </c:if>
        </table>
        <c:if test="${not empty card.overdraftMinimalPaymentDate && card.cardType == 'credit'}">
            <div class="available productTextStyle">
                <bean:message key="card.overdraft.minimalPayment.date" bundle="cardInfoBundle"/> <nobr class="bold">${phiz:formatDateWithStringMonth(card.overdraftMinimalPaymentDate)}</nobr>
            </div>
            <div class="clear"></div>
        </c:if>

    </c:if>
    <c:if test="${isLock || isArrested}">
        <div class="clear"></div>
        <c:choose>
            <c:when test="${isArrested}">
                <span class="detailStatus float"><bean:message key="label.card.arrested" bundle="paymentsBundle"/></span>
                <div class="float">
                    <tiles:insert definition="hintTemplate" flush="false">
                        <tiles:put name="color" value="whiteHint"/>
                        <tiles:put name="data">
                            <bean:message key="card.message.isArrested" bundle="cardInfoBundle"/>
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:when>
            <c:otherwise>
                <span class="detailStatus float">${card.cardState.description}</span>
            </c:otherwise>
        </c:choose>
        <div class="clear"></div>
    </c:if>
    <c:set var="cardDetailInfo" value="${cardLink.card}"/>
    <c:if test="${not empty cardDetailInfo && !isMain && !isVirtual}">
        <table class="productDetail">
            <c:if test="${not empty cardDetailInfo and card.cardState!= 'blocked'}">
                <c:if test="${not empty cardDetailInfo.availableCashLimit}">
                    <tr>
                        <td>
                            <div class="available"><bean:message key="card.label.availableCashLimit" bundle="cardInfoBundle"/>:</div>
                        </td>
                        <td class="value align-right">
                            <div class="available"><nobr>${phiz:productFormatAmount(cardDetailInfo.availableCashLimit)}</nobr></div>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not empty cardDetailInfo.purchaseLimit}">
                    <tr>
                        <td>
                            <div class="available"><bean:message key="card.label.purchaseLimit" bundle="cardInfoBundle"/>:</div>
                        </td>
                        <td class="value align-right">
                            <div class="available"><nobr>${phiz:productFormatAmount(cardDetailInfo.purchaseLimit)}</nobr></div>
                        </td>
                    </tr>
                </c:if>
            </c:if>
        </table>
    </c:if>
    <c:if test="${page == 'cardsDetail'}">
        <c:set var="url" value="${phiz:calculateActionURL(pageContext,'private/cards/detail')}?id=${cardLink.id}"/>
        <br/>
        <a class="blueGrayLink" href="${url}"><bean:message key="card.label.detailInfo" bundle="cardInfoBundle"/></a>
    </c:if>
</tiles:put>
<%--CENTER--%>
<tiles:put name="centerData">
    <c:set var="amountinfo" value="${phiz:formatAmount(card.availableLimit)}"/>
    <%--Для дополнительных карт доступный расходный лимит "Avail" не приходит в GFL и CRDWI,
        вместо него отображаем доступный лимит для оплаты "AvailPmt"--%>
    <c:if test="${empty card.availableLimit && !isMain && not empty cardDetailInfo && not empty cardDetailInfo.purchaseLimit}">
        <c:set var="amountinfo" value="${phiz:formatAmount(cardDetailInfo.purchaseLimit)}"/>
    </c:if>

    <c:set var="isNegative" value="${(not empty card.availableLimit && card.availableLimit.decimal<0) || (empty card.availableLimit && not empty cardDetail.purchaseLimit && cardDetail.purchaseLimit.decimal<0)}"/>
    <c:if test="${isNegative}">
        <c:set var="amountinfo">
            &minus;${fn:substring(amountinfo, 1, -1)}
        </c:set>
    </c:if>
    <c:if test="${not empty card.availableLimit || (not empty cardDetailInfo && not empty cardDetailInfo.purchaseLimit)}">
      <c:choose>
        <c:when test="${page=='cardsDetail'}">
                <c:choose>
                    <c:when test="${isLock || isNegative}">
                        <span class="detailAmount negativeAmount nowrap">${amountinfo}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="detailAmount nowrap">${amountinfo}</span>
                    </c:otherwise>
                </c:choose>
                <br />
        </c:when>
        <c:otherwise>
                <c:choose>
                    <c:when test="${isLock || isNegative}">
                        <span class="overallAmount nowrap negativeAmount">${amountinfo}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="overallAmount nowrap">${amountinfo}</span>
                    </c:otherwise>
                </c:choose>
                <br />
        </c:otherwise>
      </c:choose>
    </c:if>
</tiles:put>
<%--RIGHT--%>
<tiles:put name="rightData">
    <c:set var="isMoneyBoxSupported" value="${phiz:impliesService('CreateMoneyBoxPayment') && phiz:isCardForMoneyBoxMatched(cardLink)}"/>
    <c:if test="${isMoneyBoxSupported}">
        <tiles:insert definition="moneyBoxWindowCreator" flush="false"/>
    </c:if>
    <tiles:insert definition="listOfOperation" flush="false">
        <tiles:put name="productOperation" value="true"/>
        <c:if test="${page=='cardsDetail'}">
            <tiles:put name="nameOfOperation"><bean:message key="card.operations" bundle="cardInfoBundle"/></tiles:put>
        </c:if>
        <c:choose>
            <c:when test="${isLock}">
                <c:choose>
                    <%--Перевыпуск карты--%>
                    <c:when test="${phiz:impliesServiceRigid('ReIssueCardClaim')
                            && phiz:acceptObjectByFilter('com.rssl.phizic.business.resources.ReissuedCardFilter', cardLink)}">
                        <tiles:putList name="items">
                            <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=ReIssueCardClaim&cardLink=card:${cardLink.id}</c:set>
                            <tiles:add><a href="${url}"><bean:message key="card.operations.reissueCard" bundle="cardInfoBundle"/></a></tiles:add>
                        </tiles:putList>
                    </c:when>
                    <%--Оплатить--%>
                    <c:otherwise>
                        <tiles:put name="isLock" value="true"/>
                        <tiles:putList name="items">
                            <tiles:add><bean:message key="card.operations.pay" bundle="cardInfoBundle"/></tiles:add>
                        </tiles:putList>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <tiles:putList name="items">
                    <%--Оплатить--%>
                    <c:if test="${!isVirtual && !isArrested}">
                        <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'RurPayJurSB')}">
                            <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments.do')}?fromResource=card:${cardLink.id}</c:set>
                            <tiles:add><a href="${url}"><bean:message key="card.operations.pay" bundle="cardInfoBundle"/></a></tiles:add>
                        </c:if>
                    </c:if>

                    <%--Пополнить--%>
                    <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'InternalPayment')}">
                        <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=InternalPayment&toResource=card:${cardLink.id}</c:set>
                        <tiles:add><a href="${url}"><bean:message key="card.operations.topUp" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>

                    <%--Перевести между своими счетами и картами--%>
                    <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'InternalPayment') and card.cardType != 'credit' and (cardLink.main or card.additionalCardType == 'CLIENTTOCLIENT') && !isArrested}">
                        <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do?form=InternalPayment')}&fromResource=card:${cardLink.id}</c:set>
                        <tiles:add><a class="operationSeparate" href="${url}"><bean:message key="card.operations.internalTransfer" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>

                    <%--Перевести организации--%>
                    <c:if test="${phiz:impliesOperation('CreateFormPaymentOperation', 'JurPayment') and !isVirtual and card.cardType != 'credit' and cardLink.currency.code == 'RUB' and (cardLink.main or card.additionalCardType == 'CLIENTTOCLIENT') and !isArrested}">
                        <c:set var="url">${phiz:calculateActionURL(pageContext, 'private/payments/jurPayment/edit.do')}?fromResource=card:${cardLink.id}</c:set>
                        <tiles:add><a href="${url}"><bean:message key="card.operations.transferToOrganization" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>

                    <%--Перевести частному лицу--%>
                    <c:set var="rurPaymentAccess" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'RurPayment')}"/>
                    <c:set var="newRurPaymentAccess" value="${phiz:impliesOperation('CreateFormPaymentOperation', 'NewRurPayment')}"/>
                    <c:if test="${(rurPaymentAccess || newRurPaymentAccess) && !isVirtual && card.cardType != 'credit' && (cardLink.main or card.additionalCardType == 'CLIENTTOCLIENT') && !isArrested}">
                        <c:choose>
                            <c:when test="${newRurPaymentAccess}">
                                <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=NewRurPayment&fromResource=card:${cardLink.id}</c:set>
                            </c:when>
                            <c:otherwise>
                                <c:set var="url">${phiz:calculateActionURL(pageContext, '/private/payments.do')}</c:set>
                            </c:otherwise>
                        </c:choose>
                        <tiles:add><a href="${url}"><bean:message key="card.operations.transferToPrivatePerson" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>

                    <%--Перевыпуск карты--%>
                    <c:if test="${phiz:impliesServiceRigid('ReIssueCardClaim')
                        && phiz:acceptObjectByFilter('com.rssl.phizic.business.resources.ReissuedCardFilter', cardLink)}">
                        <c:set var="url"
                            value="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=ReIssueCardClaim&cardLink=card:${cardLink.id}"/>
                        <tiles:add><a href="${url}"><bean:message key="card.operations.reissueCard" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>

                    <%--Погасить кредит--%>
                    <c:if test="${!isVirtual && !isArrested}">
                        <c:set var="type">${card.cardType}</c:set>
                        <c:if test="${phiz:isOpenedDifLoan() && phiz:impliesOperation('CreateFormPaymentOperation', 'LoanPayment') && type!='credit'}">
                            <c:set var="url"
                                   value="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=LoanPayment&fromResource=card:${cardLink.id}"/>
                            <tiles:add><a href="${url}"><bean:message key="card.operations.loanPayment" bundle="cardInfoBundle"/></a></tiles:add>
                        </c:if>
                    </c:if>
                    <%--Подключить копилку--%>
                    <c:if test="${isMoneyBoxSupported}">
                        <tiles:add><a href="#" onclick="openCreateMoneyBoxFromCardWindow('${cardLink.code}');"><bean:message key="card.operations.createMoneyBox" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>
                    <%--Заблокировать--%>
                    <c:if test="${phiz:impliesService('BlockingCardClaim') && (phiz:impliesService('BlockingCardIncludeVirtualClaim') || !isVirtual)}">
                        <c:set var="url"
                               value="${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=BlockingCardClaim&cardLink=card:${cardLink.id}"/>
                        <tiles:add><a class="operationSeparate block" href="${url}"><bean:message key="card.operations.blockCard" bundle="cardInfoBundle"/></a></tiles:add>
                    </c:if>
                </tiles:putList>
            </c:otherwise>
        </c:choose>
    </tiles:insert>
</tiles:put>
<%--BOTTOM--%>
<tiles:put name="bottomData">
    <c:if test="${page!='cardsDetail'}">
        <tiles:insert definition="simpleTableTemplate" flush="false">
            <tiles:put name="hideable" value="true"/>
            <tiles:put name="id" value="${cardLink.id}"/>
            <tiles:put name="productType" value="card"/>
            <tiles:put name="show" value="${phiz:isShowOperations(cardLink)}"/>
            <c:choose>
                <c:when test="${not empty resourceAbstract && not empty resourceAbstract.transactions && empty bottomDataInfo}">
                    <tiles:put name="grid">
                        <tiles:insert page="/WEB-INF/jsp/cards/miniAbstract.jsp" flush="false">
                            <tiles:put name="resourceAbstract" beanName="resourceAbstract"/>
                        </tiles:insert>
                    </tiles:put>
                </c:when>
                <c:otherwise>
                    <tiles:put name="ajaxDataURL">${phiz:calculateActionURL(pageContext, '/private/async/extract.do')}?type=card&id=${cardLink.id}</tiles:put>
                </c:otherwise>
            </c:choose>


        </tiles:insert>
    </c:if>
    ${bottomDataInfo}
</tiles:put>
<c:if test="${showInMainCheckBox}">
    <tiles:put name="id" value="${cardLink.id}"/>
    <tiles:put name="showInMainCheckBox" value="${not isLock}"/>
    <tiles:put name="inMain" value="${cardLink.showInMain}"/>
    <tiles:put name="productType" value="card"/>
</c:if>
<c:if test="${showInThisWidgetCheckBox}">
    <tiles:put name="id" value="${cardLink.id}"/>
    <tiles:put name="showInThisWidgetCheckBox" value="true"/>
    <tiles:put name="productType" value="card"/>
</c:if>
<c:if test="${isLock}">
    <tiles:put name="status" value="error"/>
</c:if>
</tiles:insert>
</c:if>
