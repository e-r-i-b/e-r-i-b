<%--
  Created by IntelliJ IDEA.
  User: kligina
  Date: 11.02.2011
  Time: 10:05:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/cards/detail" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="cardLink" value="${form.cardLink}" scope="request"/>
<c:set var="page" value="cardsDetail" scope="request" />
<c:set var="card"    value="${cardLink.card}"/>
<c:set var="cardAccount" value="${cardLink.cardAccount}"/>
<%--номер счета карты из БД--%>
<c:set var="cardPrimaryAccount" value="${card.primaryAccountNumber}"/>
<c:if test="${empty cardPrimaryAccount}">
    <c:choose>
        <c:when test="${!empty cardLink.cardPrimaryAccount}">
            <c:set var="cardPrimaryAccount" value="${cardLink.cardPrimaryAccount}"/>
        </c:when>
        <c:when test="${!empty cardAccount}">
            <c:set var="cardPrimaryAccount" value="${cardAccount.number}"/>
        </c:when>
        <c:otherwise>
            <c:set var="cardPrimaryAccount" value=""/>
        </c:otherwise>
    </c:choose>
</c:if>
<%--владелец карты--%>
<c:set var="cardClient" value="${cardLink.cardClient}"/>
<c:set var="mainCard" value="${card}"/>
<c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
<c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
<c:set var="additionalCardFaqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'h1')}"/>
<c:set var="isERMBConnectedPerson" value="${phiz:isERMBConnectedPerson()}"/>
<c:set var="showPFRHistoryFull" value="${phiz:impliesService('PFRHistoryFullService')}"/>
<c:set var="showPFRHistoryFull" value="${showPFRHistoryFull and phiz:isMaestroSocialNotClosedNotBlockedCardWithPrimaryAccountFilter(cardLink.id)}"/>

<tiles:insert definition="accountInfo">
    <tiles:put name="mainmenu" value="Cards"/>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Карты"/>
            <tiles:put name="action" value="/private/cards/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:write name="cardLink" property="name"/> ${phiz:getCutCardNumber(cardLink.number)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
     </tiles:put>
     <tiles:put name="data" type="string">
        <html:hidden property="id"/>
        <script type="text/javascript">
            function openPrintCardInfo(event, abstract)
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/cards/print.do')}?id=${form.id}&printAbstract=" + abstract;
                openWindow(event, url, "PrintInformation", "resizable=1,menubar=1,toolbar=0,scrollbars=1");
            }
            function openPfrHistoryFull(){
                win.open('pfrHistoryFull');
                $('.form-row.error:first').removeClass('error').addClass('active-help');
                removeAllErrors('errMessagesBlock');
                $('#failTextMessage').hide();
                $('#startTextMessage').show();
            }
            function openPrintBankDetails(event)
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/cards/bankDetails.do')}";
                var params = "?id=" + ${form.id};
                openWindow(event, url + params, "PrintBankDetails", "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1024, height=700");
            }
        </script>
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="data">
                <c:set var="showInMainCheckBox" value="true" scope="request"/>
                <c:set var="nameOrNumber">
                    <c:choose>
                        <c:when test="${not empty cardLink.name}">
                            «${cardLink.name}»
                        </c:when>
                        <c:otherwise>
                            ${phiz:getFormattedAccountNumber(cardNumber)}
                        </c:otherwise>
                    </c:choose>
                </c:set>
                <c:set var="pattern">
                    <c:choose>
                        <c:when test="${not empty cardLink.name}">
                            «${cardLink.patternForFavouriteLink}»
                        </c:when>
                        <c:otherwise>
                            cardLink.patternForFavouriteLink
                        </c:otherwise>
                    </c:choose>
                </c:set>

                <div class="abstractContainer3">
                    <c:set var="baseInfo">
                        <bean:message key="favourite.link.card" bundle="paymentsBundle"/>
                    </c:set>

                    <div class="favouriteLinksButton marginFavouriteLinksButton">
                        <tiles:insert definition="addToFavouriteButton" flush="false">
                            <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                            <tiles:put name="patternName"><c:out value='${baseInfo} ${pattern}'/></tiles:put>
                            <tiles:put name="typeFormat">CARD_LINK</tiles:put>
                            <tiles:put name="productId">${form.id}</tiles:put>
                        </tiles:insert>
                    </div>
                </div>

                <%@ include file="cardTemplate.jsp"%>
                <div class="tabContainer">
                    <tiles:insert definition="paymentTabs" flush="false">
                        <c:set var="cardNotCreditNotMain" value="${phiz:isCardNotCreditNotMain(card)}"/>
                        <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('card')}"/>
                        <tiles:put name="count" value="4"/>
                        <c:if test="${!cardNotCreditNotMain}">
                            <tiles:put name="count" value="3"/>
                        </c:if>
                        <c:choose>
                            <c:when test="${!cardNotCreditNotMain && !showTemplates}">
                                <tiles:put name="count" value="2"/>
                            </c:when>
                            <c:when test="${cardNotCreditNotMain && showTemplates}">
                                <tiles:put name="count" value="4"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="count" value="3"/>
                            </c:otherwise>
                        </c:choose>
                        <tiles:put name="tabItems">
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="first"/>
                                <tiles:put name="active" value="false"/>
                                <tiles:put name="title" value="Последние операции"/>
                                <tiles:put name="action" value="/private/cards/info?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="active" value="true"/>
                                <tiles:put name="title" value="Информация по карте"/>
                                <c:if test="${!cardNotCreditNotMain && !showTemplates}">
                                    <tiles:put name="position" value="last"/>
                                </c:if>
                                <tiles:put name="action" value="/private/cards/detail.do?id=${cardLink.id}"/>
                            </tiles:insert>
                            <c:if test="${showTemplates}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="active" value="false"/>
                                    <c:if test="${!cardNotCreditNotMain}">
                                        <tiles:put name="position" value="last"/>
                                    </c:if>
                                    <tiles:put name="title" value="Шаблоны и платежи"/>
                                    <tiles:put name="action" value="/private/cards/payments.do?id=${cardLink.id}"/>
                                </tiles:insert>
                            </c:if>
                            <c:if test="${cardNotCreditNotMain}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="last"/>
                                    <tiles:put name="active" value="false"/>
                                    <tiles:put name="title" value="Графическая выписка"/>
                                    <tiles:put name="action" value="/private/cards/graphic-abstract.do?id=${cardLink.id}"/>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>

                    <div class="productTitleDetailInfo">
                        <div id="productNameText" name="productNameText" class="word-wrap">
                            <span class="productTitleDetailInfoText">
                                <c:out value="${form.fields.cardName}"/>
                                <a class="productTitleDetailInfoEditBullet" onclick="showEditProductName();"></a>
                            </span>
                        </div>
                        <div id="productNameEdit" name="productNameEdit" class="productTitleDetailInfoEditBlock">
                            <html:text property="field(cardName)" size="30" maxlength="56" styleId="fieldCardName" styleClass="productTitleDetailInfoEditTextBox"/>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.saveCardName"/>
                                <tiles:put name="commandHelpKey" value="button.saveCardName"/>
                                <tiles:put name="bundle" value="cardInfoBundle"/>
                            </tiles:insert>
                            <div class="errorDiv clear" style="display:none;"></div>
                        </div>
                    </div>

                    <script type="text/javascript">
                        function showEditProductName() {
                            $("#productNameText").hide();
                            $("#productNameEdit").show();
                            $("#fieldCardName")[0].selectionStart = $("#fieldCardName")[0].selectionEnd = $("#fieldCardName").val().length;
                        }
                    </script>

                    <%--Окно с фильтром для расширенной выписки ПФР--%>
                    <c:if test="${showPFRHistoryFull}">
                        <c:set var="abstractUrl" value="${phiz:calculateActionURL(pageContext,'/private/pfr/pfrHistoryFull.do?isWindow=true&fromResource=card:')}"/>
                        <tiles:insert definition="window" flush="false">
                            <tiles:put name="id" value="pfrHistoryFull"/>
                            <tiles:put name="loadAjaxUrl" value="${abstractUrl}${cardLink.id}"/>
                        </tiles:insert>
                    </c:if>
                    <div class="abstractContainer2">

                        <%--Реквизиты перевода на счет карты. Отображать если:
                        - определен номер счета карты,
                        - есть право "Получение реквизитов банка" --%>
                        <c:if test="${!empty cardPrimaryAccount}">
                            <div class="inlineBlock">
                                <tiles:insert definition="clientButton" flush="false" service="AccountBankDetailsService">
                                    <tiles:put name="commandTextKey" value="button.show.details"/>
                                    <tiles:put name="commandHelpKey" value="button.show.details.help"/>
                                    <tiles:put name="bundle" value="cardInfoBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image"    value="revizity.gif"/>
                                    <tiles:put name="imageHover"    value="revizityHover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick" value="openPrintBankDetails(event);"/>
                                </tiles:insert>
                            </div>
                        </c:if>
                        <div class="inlineBlock">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.show.tarif"/>
                                <tiles:put name="commandHelpKey" value="button.show.tarif.help"/>
                                <tiles:put name="bundle" value="cardInfoBundle"/>
                                <tiles:put name="onclick" value="openWindow(event, 'http://sberbank.ru/ru/person/bank_cards/tarif/');"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="image"    value="revizity.gif"/>
                                <tiles:put name="imageHover"    value="revizityHover.gif"/>
                                <tiles:put name="imagePosition" value="left"/>
                            </tiles:insert>
                        </div>
                        <div class="printButtonRight">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.print.detail.info"/>
                                <tiles:put name="commandHelpKey" value="button.print.detail.info.help"/>
                                <tiles:put name="bundle" value="cardInfoBundle"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick">openPrintCardInfo(event, false);</tiles:put>
                                <tiles:put name="image" value="print-check.gif"/>
                                <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                <tiles:put name="imagePosition" value="left"/>
                            </tiles:insert>
                        </div>
                        <div class="clear"></div>
                        <c:if test="${showPFRHistoryFull}">
                            <div class="secondLineBtn">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.PFRHistoryFull"/>
                                    <tiles:put name="commandHelpKey" value="button.PFRHistoryFull.help"/>
                                    <tiles:put name="bundle" value="pfrBundle"/>
                                    <tiles:put name="viewType" value="buttonGrey"/>
                                    <tiles:put name="image" value="pfr-reference.gif"/>
                                    <tiles:put name="imageHover" value="pfr-reference-hover.gif"/>
                                    <tiles:put name="imagePosition" value="left"/>
                                    <tiles:put name="onclick">openPfrHistoryFull();</tiles:put>
                                </tiles:insert>
                            </div>
                        </c:if>
                    </div>
                    <div class="clear"></div>
                    <fieldset>
                            <table class="additional-product-info addCardFields firstColumnFix">
                            <tr>
                                <td colspan="2">
                                    <span class="cardDetailTitle cardDetailTitleFirst">Общая информация</span>
                                </td>
                            </tr>
                            <c:if test="${not empty cardClient}">
                                <tr>
                                    <td class="align-right field fixColumn">Держатель карты:</td>
                                    <td>
                                        <span class="bold">
                                            <c:out value="${phiz:getFormattedPersonName(cardClient.firstName,cardClient.surName,cardClient.patrName)}"/>
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                            <%--Для виртуальных карт показываем не замаскированный номер карты--%>
                            <c:if test="${isVirtual}">
                                <tr>
                                    <td class="align-right field">Номер карты:</td>
                                    <td>
                                        <span class="bold">${phiz:getFormattedCardNumber(cardLink.number)}</span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${card.cardType != 'credit'}">
                                <tr>
                                    <td class="align-right field">Номер счета карты:</td>
                                    <td>
                                        <span class="bold">${phiz:getFormattedAccountNumber(cardPrimaryAccount)}</span>
                                    </td>
                                </tr>
                            </c:if>
                            <%--Для дополнительных карт--%>
                            <c:if test="${!form.cardLink.main}">
                                <tr>
                                    <td class="align-right field">Номер основной карты:</td>
                                    <td>
                                        <span class="bold">${phiz:getCutCardNumber(cardLink.mainCardNumber)}</span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${card.cardType == 'credit' and not empty card.nextReportDate}">
                                <tr>
                                    <td class="align-right field">Дата формирования<br/> отчета:</td>
                                    <td>
                                        <span class="bold">${phiz:formatDateWithStringMonth(card.nextReportDate)}</span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${isERMBConnectedPerson == true}">
                                <tr class="form-row">
                                    <td class="align-right valign-top"><span class="paymentTextLabel">Персональный<br/> SMS-идентификатор:</span></td>
                                    <td>
                                        <html:text property="field(clientSmsAlias)" size="20" maxlength="20" styleClass="bold"/>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.saveClientSmsAlias"/>
                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                            <tiles:put name="bundle" value="cardInfoBundle"/>
                                        </tiles:insert>
                                        <div class="errorDiv" style="display:none;"></div>
                                    </td>
                                </tr>
                                <tr class="form-row">
                                    <td class="align-right valign-top"><span class="paymentTextLabel">Автоматический<br/> SMS-идентификатор:</span></td>
                                    <td>
                                        <span class="bold">
                                            <c:out value="${form.fields.autoSmsAlias}"/>
                                        </span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:set var="cardDetail" value="${cardLink.card}"/>
                            <c:set var="notDebitAndHaveOverdraftInfo" value="${card.cardType != 'debit' && not empty card && (isMain || isVirtual)}"/>
                            <c:set var="showToGetCash" value="${not empty cardDetail.availableCashLimit}"/>
                            <c:set var="showForShopping" value="${not empty cardDetail.purchaseLimit}"/>
                            <c:set var="showDestination" value="${not empty cardLink.card && card.cardType != 'credit' && card.cardState!= 'blocked' && (showForShopping || showToGetCash)}"/>
                            <c:set var="showPaymentDate" value="${card.cardType == 'credit' && not empty card}"/>
                            <c:set var="showOwnSum" value="${not empty card.overdraftOwnSum and card.overdraftOwnSum.wholePart >= 0}"/>
                            <%--Отображение "Обязательный платеж" для овердрафтных завязывается на значение "Лимит овердрафта"--%>
                            <c:set var="showMandatoryPayment" value="${not empty card.overdraftMinimalPayment &&
                                (card.cardType == 'credit' ||
                                    (card.cardType == 'overdraft' && not empty card.overdraftLimit && card.overdraftLimit.decimal>0))}"/>
                            <c:set var="showTotalDebt" value="${not empty card.overdraftTotalDebtSum}"/>
                            <c:if test="${showOwnSum || showDestination || showTotalDebt || showMandatoryPayment || showPaymentDate}">
                            <tr>
                                <td colspan="2">
                                    <span class="cardDetailTitle">Состояние счета</span>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${notDebitAndHaveOverdraftInfo}">
                                <tr>
                                    <td class="align-right field">Собственные средства:</td>
                                     <td>
                                        <span class="bold">
                                            <c:choose>
                                                <c:when test="${showOwnSum}">
                                                    ${phiz:formatAmount(card.overdraftOwnSum)}
                                                </c:when>
                                                <c:otherwise>
                                                    0,00 руб.
                                                </c:otherwise>
                                            </c:choose>
                                         </span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${showDestination}">
                                <c:if test="${showToGetCash}">
                                    <tr>
                                        <td class="align-right field">
                                            <div class="available">Для снятия наличных:</div>
                                        </td>
                                        <td class="value bold">
                                            <div class="available bold"><nobr>${phiz:productFormatAmount(cardDetail.availableCashLimit)}</nobr></div>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${showForShopping}">
                                    <tr>
                                        <td class="align-right field">
                                            <div class="available">Для покупок:</div>
                                        </td>
                                        <td class="value bold">
                                            <div class="available bold"><nobr>${phiz:productFormatAmount(cardDetail.purchaseLimit)}</nobr></div>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:if>
                            <c:if test="${notDebitAndHaveOverdraftInfo}">
                                <tr>
                                    <td class="align-right field">Общая задолженность на дату<br/> формирования отчета:</td>
                                     <td>
                                        <span class="bold">
                                            <c:if test="${showTotalDebt}">
                                                ${phiz:formatAmount(card.overdraftTotalDebtSum)}
                                            </c:if>
                                            &nbsp;
                                         </span>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${notDebitAndHaveOverdraftInfo && showMandatoryPayment}">
                                <tr>
                                     <td class="align-right field">Обязательный платеж:</td>
                                     <td>
                                        <span class="bold">
                                            ${phiz:formatAmount(card.overdraftMinimalPayment)}
                                        </span>
                                     </td>
                                </tr>
                            </c:if>

                            <c:if test="${showPaymentDate}">
                                <tr>
                                     <td class="align-right field">Дата платежа: <br/>(окончание льготного периода)</td>
                                     <td>
                                        <span class="bold">
                                            <c:if test="${not empty card.overdraftMinimalPaymentDate}">
                                                ${phiz:formatDateWithStringMonth(card.overdraftMinimalPaymentDate)}
                                            </c:if>
                                            &nbsp;
                                         </span>
                                     </td>
                                </tr>
                            </c:if>
                        </table>
                    </fieldset>

                    <c:if test="${form.cardLink.main && not empty form.additionalCards}">
                        <div id="additional-cards-container">
                            <nobr>
                                <span class="title"><b>Список дополнительных карт&nbsp;</b></span>
                                <a href="#" onclick="openFAQ('${additionalCardFaqLink}')">
                                    что такое дополнительная карта и как ее оформить?
                                </a>
                            </nobr>
                            <div class="card-detail-hint">
                                <c:set var="cardInfoUrl"
                                       value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
                                <%--Доп. карты--%>
                                <c:forEach items="${form.additionalCards}" var="additional">
                                    <c:set var="cardLink" value="${additional}" scope="request"/>
                                    <c:set var="additionalCard" value="${cardLink.card}"/>

                                    <c:set var="additionaCardDetail" value="${cardLink.card}"/>
                                    <c:set var="amountinfo" value="${phiz:formatAmount(additionalCard.availableLimit)}"/>
                                    <%--Для дополнительных карт доступный расходный лимит "Avail" не приходит в GFL и CRDWI,
                                        вместо него отображаем доступный лимит для оплаты "AvailPmt"--%>
                                    <c:if test="${empty additionalCard.availableLimit && not empty additionaCardDetail && not empty additionaCardDetail.purchaseLimit}">
                                        <c:set var="amountinfo" value="${phiz:formatAmount(additionaCardDetail.purchaseLimit)}"/>
                                    </c:if>

                                    <c:set var="isLock" value="${additionalCard.cardState!=null && additionalCard.cardState!='active'}"/>
                                    <c:set var="description" value="${cardLink.description}"/>
                                    <c:choose>
                                        <c:when test="${not empty additionalCard.cardLevel and additionalCard.cardLevel == 'MQ'}">
                                            <c:set var="imgCode" value="mq" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
                                        </c:otherwise>
                                    </c:choose>

                                    <c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
                                    <tiles:insert definition="productTemplate" flush="false">
                                        <c:if test="${not empty cardLink.id}">
                                            <tiles:put name="src" value="${cardInfoUrl}${cardLink.id}"/>
                                        </c:if>
                                        <tiles:put name="img" value="${imagePath}/cards_type/icon_cards_${imgCode}64.gif"/>
                                        <tiles:put name="alt" value="${description}"/>
                                        <tiles:put name="productViewBacklight" value="false"/>

                                        <tiles:put name="title">${phiz:getCardUserName(cardLink)}</tiles:put>
                                        <tiles:put name="comment">дополнительная</tiles:put>
                                        <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
                                        <tiles:put name="productNumbers">
                                            <div class="accountNumber decoration-none">${phiz:getCutCardNumber(cardLink.number)}, действует по ${phiz:formatExpirationCardDate(cardLink)}</div>
                                        </tiles:put>
                                        <tiles:put name="leftData">
                                            <c:if test="${isLock}">
                                                <span class="detailStatus">${additionalCard.cardState.description}</span>
                                            </c:if>
                                        </tiles:put>
                                        <tiles:put name="centerData">
                                            <a href="${cardInfoUrl}${cardLink.id}"
                                               class="accountNumber decoration-none">
                                                <c:choose>
                                                    <%--Доп.карта--%>
                                                    <c:when test="${not empty card.availableLimit || (not empty additionaCardDetail && not empty additionaCardDetail.purchaseLimit)}">
                                                        <c:if test="${!isLock}">
                                                            ${amountinfo}
                                                        </c:if>
                                                        <c:if test="${isLock}">
                                                            <span class="redAmount">${amountinfo}</span>
                                                        </c:if>
                                                    </c:when>
                                                    <%--Основная карта--%>
                                                    <c:when test="${not empty mainCard.availableLimit}">
                                                            <c:if test="${!isLock}">
                                                                ${phiz:formatAmount(mainCard.availableLimit)}
                                                            </c:if>
                                                            <c:if test="${isLock}">
                                                                <span class="redAmount">${phiz:formatAmount(mainCard.availableLimit)}</span>
                                                            </c:if>
                                                    </c:when>
                                                </c:choose>
                                            </a>
                                        </tiles:put>
                                        <tiles:put name="rightData"/>
                                        <c:if test="${isLock}">
                                            <tiles:put name="status" value="error"/>
                                        </c:if>
                                    </tiles:insert>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>
            </tiles:put>
        </tiles:insert>

        <c:if test="${phiz:impliesService('MoneyBoxManagement') && not empty form.moneyBoxesTargets}">
            <div class="productTitleDetailInfo">
                <div class="productTitleDetailInfoText" style="display:inline-block ;vertical-align:middle;margin-right: 0px;">Подключенные копилки</div>
                <div class="il-newIconMoneyBoxLarge" style="display:inline-block ;vertical-align:middle;"/>
            </div>
            <tiles:insert definition="mainWorkspace" flush="false">
                <c:forEach items="${form.moneyBoxesTargets}" var="target">
                    <tiles:insert definition="moneyBoxListForTarget" flush="false">
                        <tiles:put name="title" value="${target.key!='EMPTY'? target.key.dictionaryTarget.description:'Пополнение вкладов'}"/>
                        <tiles:put name="type" value="${target.key!='EMPTY'? target.key.dictionaryTarget:'ACCOUNT'}"/>
                        <tiles:put name="id" value="${target.key!='EMPTY'? target.key.id:''}"/>
                        <tiles:put name="data">
                            <c:forEach var="link" items="${target.value}">
                                <c:set var="item" value="${link.value}"/>
                                <tiles:insert definition="moneyBoxCardTemplate" flush="false">
                                    <tiles:put name="id" value="${item.number}"/>
                                    <tiles:put name="goal" value="${target.key=='EMPTY'}"/>
                                    <c:choose>
                                        <c:when test="${item.sumType == 'FIXED_SUMMA'}">
                                            <tiles:put name="amount" value="${phiz:formatBigDecimal(item.amount.decimal)}"/>
                                            <tiles:put name="longOfferPayDay" beanName="item" beanProperty="longOfferPayDay"/>
                                            <c:if test="${item.amount!=null}">
                                                <tiles:put name="codCurrency" value="${item.amount.currency.code}"/>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${phiz:isInstance(item, 'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}">
                                                    <c:set var="maxSumWrite" value="${item.amount}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="maxSumWrite" value="${link.autoSubscriptionInfo.maxSumWritePerMonth}"/>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${not empty maxSumWrite}">
                                                <tiles:put name="maxAmount" value="${phiz:formatBigDecimal(maxSumWrite.decimal)}"/>
                                            </c:if>
                                            <c:if test="${maxSumWrite!=null}">
                                                <tiles:put name="codCurrency" value="${maxSumWrite.currency.code}"/>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                    <tiles:put name="percent" value="${item.percent}"/>
                                    <tiles:put name="period" value="${item.executionEventType}"/>
                                    <c:choose>
                                        <c:when test="${phiz:isInstance(item,'com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment')}">
                                            <tiles:put name="status" value="DRAFT"/>
                                            <tiles:put name="onclick" value="openViewClaimMoneyBoxWindows('${item.id}');"/>
                                        </c:when>
                                        <c:when test="${item.autoPayStatusType == 'Paused'}">
                                            <tiles:put name="status" value="PAUSED"/>
                                            <tiles:put name="onclick" value="openViewLinkMoneyBoxWindows('${item.number}');"/>
                                        </c:when>
                                        <c:otherwise>
                                            <tiles:put name="status" value="ACTIVE"/>
                                            <tiles:put name="onclick" value="openViewLinkMoneyBoxWindows('${item.number}');"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <tiles:put name="title" value="${item.friendlyName}"/>
                                    <tiles:put name="typeSum" value="${item.sumType}"/>
                                    <c:if test="${not empty item.nextPayDate}">
                                        <tiles:put name="dateNearPay" beanName="item" beanProperty="nextPayDate"/>
                                    </c:if>
                                </tiles:insert>
                            </c:forEach>
                        </tiles:put>
                    </tiles:insert>
                </c:forEach>
            </tiles:insert>
            <tiles:insert definition="moneyBoxWindowViewer" flush="false"/>
        </c:if>

        <c:if test="${not empty form.otherCards}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <c:set var="cardCount" value="${phiz:getClientProductLinksCount('CARD')}"/>
                    <tiles:put name="title">
                        Остальные карты
                        (<a href="${phiz:calculateActionURL(pageContext, "/private/cards/list.do")}" class="blueGrayLink">показать все ${cardCount}</a>)
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="another-items">
                            <c:forEach items="${form.otherCards}" var="others">
                                <div class="another-container">
                                    <c:choose>
                                        <c:when test="${not empty others.card.cardLevel and others.card.cardLevel == 'MQ'}">
                                            <c:set var="imgSrc" value="mq" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="imgSrc" value="${phiz:getCardImageCode(others.card.description)}" />
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="${cardInfoUrl}${others.id}">
                                        <img src="${skinUrl}/images/cards_type/icon_cards_${imgSrc}32.gif" alt="${others.description}"/>
                                    </a>
                                    <a href="${cardInfoUrl}${others.id}" class="another-name decoration-none">
                                        <bean:write name="others" property="name"/>

                                    </a>
                                    <span class="another-type uppercase">
                                        <c:set var="virtual" value="${others.card.virtual}"/>
                                        <c:choose>
                                            <c:when test="${others.main  && !virtual}">основная</c:when>
                                            <c:when test="${!others.main && !virtual}">дополнительная</c:when>
                                            <c:otherwise>виртуальная</c:otherwise>
                                        </c:choose>
                                    </span>
                                    <div class="another-number">
                                        <a href="${cardInfoUrl}${others.id}" class="another-number">${phiz:getCutCardNumber(others.number)}</a>
                                        <c:set var="state" value="${others.card.cardState}"/>
                                        <c:set var="className">
                                            <c:if test="${state eq 'blocked' or state eq 'delivery'}">
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
</tiles:insert>
</html:form>

