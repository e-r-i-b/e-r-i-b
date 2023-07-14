<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath"       value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>
<c:set var="faqTariff"       value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm10')}"/>
<c:set var="faqCards"        value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm9')}"/>
<c:set var="faqQuickService" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'n18')}"/>

<%-- Подключение --%>
<c:set var="registration"            value="${form.registration}"/>
<c:set var="confirmableRegistration" value="${form.confirmableRegistration}"/>

<c:set var="mainCard" value="${registration.mainCard}" scope="request"/>
<c:set var="mainPhone" value="${registration.mainPhoneNumber}"/>

<c:set var="phoneCode" value="${phiz:getMobileBankPhoneCode(mainPhone)}"/>
<c:set var="cardCode" value="${phiz:getMobileBankCardCode(mainCard.cardNumber)}"/>
<c:set var="confirmableRegistrationPhoneCode" value="${phiz:getMobileBankPhoneCode(confirmableRegistration.mainPhoneNumber) }"/>
<c:if test="${not empty confirmableRegistration.mainCard.cardNumber}">
    <c:set var="confirmableRegistrationCardNumber" value="${confirmableRegistration.mainCard.cardNumber}"/>
</c:if>
<c:set var="windowCode" value="${mainCard.cardlink.id}|${phoneCode}"/>

<c:choose>
    <c:when test="${registration.tariff.code=='F'}">
        <c:set var="registrationTariff" value="Полный пакет"/>
    </c:when>
    <c:when test="${registration.tariff.code=='S'}">
        <c:set var="registrationTariff" value="Экономный пакет"/>
    </c:when>
    <c:otherwise>
        <c:set var="registrationTariff" value="Не указан"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${registration.status.code=='A'}">
        <c:set var="registrationStatus" value="Активна"/>
        <c:set var="registrationColor" value="italicInfo"/>
    </c:when>
    <c:when test="${registration.status.code=='I'}">
        <c:set var="registrationStatus" value="Заблокирована"/>
        <c:set var="registrationColor" value="text-red"/>
    </c:when>
    <c:when test="${registration.status.code=='PB'}">
        <c:set var="registrationStatus" value="Заблокирована"/>
        <c:set var="registrationColor" value="text-red"/>
    </c:when>
    <c:when test="${registration.status.code=='CB'}">
        <c:set var="registrationStatus" value="Заблокирована"/>
        <c:set var="registrationColor" value="text-red"/>
    </c:when>
    <c:otherwise>
        <c:set var="registrationStatus" value="Не известно"/>
        <c:set var="registrationColor" value="italicInfo"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${mainCard.cardStatus.code=='A'}">
        <c:set var="mainCardColor" value="green"/>
    </c:when>
    <c:when test="${mainCard.cardStatus.code=='I'}">
        <c:set var="mainCardColor" value="red"/>
    </c:when>
    <c:otherwise>
        <c:set var="mainCardColor" value="green"/>
    </c:otherwise>
</c:choose>

<c:set var="cardLink" value="${mainCard.cardlink}" scope="request"/>
<c:set var="card" value="${cardLink.card}"/>
<c:set var="description" value="${cardLink.description}"/>
<c:choose>
    <c:when test="${not empty card.cardLevel and card.cardLevel == 'MQ'}">
        <c:set var="imgCode" value="mq" />
    </c:when>
    <c:otherwise>
        <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
    </c:otherwise>
</c:choose>


<c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
<c:set var="mobileBankCardStatus" value=""/>
<c:if test="${mainCard.cardStatus.code == 'I'}">
    <c:set var="mobileBankCardStatus" value="Неактивная"/>
</c:if>

<%-- ссылка на страницу со списком шаблонов платежей по основной карте подключения --%>
<c:set var="paymentsPageLink" value="/private/mobilebank/payments/list.do?phoneCode=${phoneCode}&cardCode=${cardCode}" />

<%-- ссылка на страницу с информацией по основной карте подключения --%>
<c:if test="${not empty mainCard.cardlink and not empty mainCard.cardlink.id}">
    <c:url var="mainCardPageLink" value="/private/cards/info.do">
        <c:param name="id"
                 value="${mainCard.cardlink.id}"/>
    </c:url>
</c:if>
<%-- ссылка на страницу выбора поставщиков для добавляемого шаблона --%>
<c:url var="createTemplateFromPaymentPageLink"
       value="/private/mobilebank/payments/create-template-from-payment.do">
    <c:param name="phoneCode" value="${phoneCode}"/>
    <c:param name="cardCode" value="${cardCode}"/>
</c:url>
<c:set var="onClick" value="loadNewAction('',''); window.location='${createTemplateFromPaymentPageLink}';"/>
<c:if test="${card.cardState eq 'blocked' or card.cardState eq 'delivery'}">
    <c:set var="onClick" value="addMessage('Вы не можете создать SMS-шаблон, потому что по Вашей карте запрещены платежные операции.');"/>
</c:if>
<tiles:insert page="header.jsp" flush="false">
    <tiles:put name="title"> ${phiz:getCutPhoneNumber(mainPhone)} </tiles:put>
    <tiles:put name="cardName" value="${phiz:getCardUserName(cardLink)}"/>
    <tiles:put name="cardNumber">
        ${phiz:getCutCardNumber(cardLink.number)}<br />
        Номер для отправки SMS-запросов: <b>900</b>
    </tiles:put>
    <tiles:put name="amount" value="${phiz:formatAmount(card.availableLimit)}" />
    <tiles:put name="rightData">
        <div class="clientButton ">
            <div class="buttonGrayNew" onclick="${onClick}">
                <a>
                    <div class="left-corner"> </div>
                    <div class="text">
                        <span>Создать SMS-шаблон</span>
                    </div>
                    <div class="right-corner"> </div>
                </a>
            </div>
            <div class="clear"></div>
        </div>
    </tiles:put>
    <c:if test="${not empty cardLink.id}">
        <tiles:put name="src" value="${cardInfoUrl}${cardLink.id}"/>
    </c:if>
    <tiles:put name="mainCardPageLink" value="${mainCardPageLink}" />
    <tiles:put name="registrationTariff" value="${registrationTariff}"/>
    <tiles:put name="registrationStatus" value="${registrationStatus}"/>
    <tiles:put name="registrationColor" value="${registrationColor}"/>
</tiles:insert>

<div class="tabContainer">
    <c:set var="paymentsPageLink" value="/private/mobilebank/payments/list.do?phoneCode=${phoneCode}&cardCode=${cardCode}"/>
    <tiles:insert definition="paymentTabs" flush="false">
        <tiles:put name="count" value="2"/>
        <tiles:put name="tabItems">
            <tiles:insert definition="paymentTabItem" flush="false" service="FavouriteManagment">
                <tiles:put name="position" value="first"/>
                <tiles:put name="active" value="true"/>
                <tiles:put name="title" value="Детали подключения"/>
                <tiles:put name="action" value="/private/mobilebank/main.do"/>
            </tiles:insert>
           <tiles:insert definition="paymentTabItem" flush="false" service="FavouriteManagment">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="SMS-запросы и шаблоны"/>
                <tiles:put name="action" value="${paymentsPageLink}"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>


<div class="accessCardsBlock">
    <div class="buttonsArea" style="text-align:left;">
      <c:if test="${not empty confirmableRegistration}">
          <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(confirmableRegistration)}"/>
      </c:if>

      <span>
        <c:set var="linkEnabled" value="true"/>
          
        <c:choose>
            <c:when test="${registration.quickServiceStatusCode eq 'QUICK_SERVICE_STATUS_ON'}">
                <c:set var="commandTextKey"   value="button.quickService.off"/>
                <c:set var="quickServiceText" value="подключена"/>
            </c:when>
            <c:when test="${registration.quickServiceStatusCode eq 'QUICK_SERVICE_STATUS_OFF'}">
                <c:set var="commandTextKey"   value="button.quickService.on"/>
                <c:set var="quickServiceText" value="отключена"/>
            </c:when>
            <c:otherwise>
                <c:set var="commandTextKey"   value="button.quickService.unknown"/>
                <c:set var="linkEnabled"      value="false"/>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${linkEnabled}">
                
                <script type="text/javascript">
                    function changeStatus(phoneCode, cardCode)
                    {
                        setElement('phoneCode', phoneCode);
                        setElement('cardCode',  cardCode);
                        return callOperation(null, 'button.change');
                    };
                </script>
                <br>
                У Вас ${quickServiceText} бесплатная опция Мобильного банка «Быстрый платеж», которая
                позволяет  мгновенно и без комиссии оплачивать любой мобильный телефон и переводить деньги на
                любую карту Сбербанка.<br>
                Вы можете
                <span class="blueGrayLinkDotted" onclick="changeStatus('${phoneCode}','${cardCode}');"><bean:message key="${commandTextKey}" bundle="commonBundle"/></span>
                опцию «Быстрый платеж».
                <span class="blueGrayLinkDotted" onclick="openFAQ('${faqQuickService}');">Подробнее</span>
                <c:if test="${not empty confirmRequest and confirmableRegistrationCardNumber == mainCard.cardNumber}">
                    <script type="text/javascript">
                        $(document).ready(function()
                        {
                            win.open('openChangeQuickServiceWindow${confirmableRegistrationCardNumber}');
                        });
                    </script>
                </c:if>
            </c:when>
            <c:otherwise>
                Статус услуги оплаты сотового телефона и перевода на карту по номеру телефона не определен.
                <span class="blueGrayLinkDotted" onclick="openFAQ('${faqQuickService}');">Подробнее</span>
                <br/>
                <span class="link"><bean:message key="${commandTextKey}" bundle="commonBundle"/></span>
            </c:otherwise>
        </c:choose>
      </span>

  </div>
  <br/>
    <div class="mobilebankPaymentCards">
        <h4>Платежная карта </h4>
        <c:if test="${cardLink != null}">
            <div class="mobilebankCadrs">
                <tiles:insert definition="productTemplate" flush="false">
                    <tiles:put name="img" value="${image}/cards_type/icon_cards_${imgCode}64.gif"/>
                    <c:if test="${not empty cardLink.id}">
                        <tiles:put name="src" value="${cardInfoUrl}${cardLink.id}"/>
                    </c:if>
                    <tiles:put name="alt" value="${description}"/>
                    <tiles:put name="title">
                        <c:out value="${phiz:getCardUserName(cardLink)}"/>
                    </tiles:put>
                    <tiles:put name="titleClass" value="mainProductTitle"/>
                    <tiles:put name="comment">
                        <c:choose>
                            <c:when test="${cardLink.main}">основная карта</c:when>
                            <c:otherwise>дополнительная карта</c:otherwise>
                        </c:choose>
                    </tiles:put>
                    <tiles:put name="additionalProductInfo">
                        <span class="additionalProductInfo accountNumber">
                            <html:link href="${mainCardPageLink}">
                                ${phiz:getCutCardNumber(cardLink.number)}
                            </html:link>
                        </span>
                        <c:if test="${mainCard.cardStatus.code == 'I'}">
                            <br/>
                            <span class="detailStatus">${mobileBankCardStatus}</span>
                        </c:if>
                    </tiles:put>
                    <tiles:put name="leftData">
                        <div class="productType">
                            <span>С этой карты идет списание средств</span>
                        </div>
                    </tiles:put>
                    <tiles:put name="rightData">
                        <div class="simpleTable">
                            <c:if test="${not empty mainCard.favoriteSmsRequests}">
                                <c:set target="${form}"
                                       property="smsCommands"
                                       value="${mainCard.favoriteSmsRequests}"/>
                                <tiles:insert page="sms-command-list.compact.jsp" flush="false"/>
                                <div class="align-left">
                                   <html:link href="/PhizIC${paymentsPageLink}" styleClass="blueGrayLink">
                                       показать еще &raquo;
                                   </html:link>
                                </div>
                            </c:if>
                        </div>
                    </tiles:put>
                    <c:if test="${mainCard.cardStatus.code == 'I'}">
                        <tiles:put name="status" value="error"/>
                    </c:if>
                </tiles:insert>
            </div>
            <div class="productDivider"></div>
        </c:if>
    </div>
    <c:if test="${not empty registration.infoCards}">
        <h4> Карты для получения информации </h4>
        <html:link href="" onclick="openFAQ('${faqCards}'); return false;" styleClass="text-green">
            в чём отличие от основной карты?
        </html:link>

        <c:set var="elementsCount" value="${fn:length(registration.infoCards)-1}"/>

        <logic:iterate id="curCard" collection="${registration.infoCards}" indexId="i">
            <c:set var="cardLink" value="${curCard.cardlink}" scope="request"/>
            <c:set var="description" value="${cardLink.description}"/>
            <c:set var="mobileBankCardStatus" value=""/>
            <c:choose>
                <c:when test="${not empty cardLink.card.cardLevel and cardLink.card.cardLevel == 'MQ'}">
                    <c:set var="imgCode" value="mq" />
                </c:when>
                <c:otherwise>
                    <c:set var="imgCode" value="${phiz:getCardImageCode(description)}"/>
                </c:otherwise>
            </c:choose>

            <c:if test="${curCard.cardStatus.code == 'I'}">
                <c:set var="mobileBankCardStatus" value="Неактивная"/>
            </c:if>
            <c:url var="infoCardPageLink" value="/private/cards/info.do">
                <c:param name="id" value="${cardLink.id}"/>
            </c:url>
            <c:if test="${cardLink != null}">
                <div class="mobilebankCadrs">
                    <tiles:insert definition="productTemplate" flush="false">
                        <tiles:put name="img" value="${image}/cards_type/icon_cards_${imgCode}64.gif"/>
                        <tiles:put name="alt" value="${description}"/>
                        <tiles:put name="arrow" value="true"/>
                        <c:if test="${not empty cardLink.id}">
                            <tiles:put name="src" value="${cardInfoUrl}${cardLink.id}"/>
                        </c:if>
                        <tiles:put name="title">
                            <c:out value="${phiz:getCardUserName(cardLink)}"/>
                        </tiles:put>
                        <tiles:put name="titleClass" value="mainProductTitle"/>
                        <tiles:put name="comment">
                            <c:choose>
                                <c:when test="${cardLink.main}">основная карта</c:when>
                                <c:otherwise>дополнительная карта</c:otherwise>
                            </c:choose>
                        </tiles:put>
                        <tiles:put name="additionalProductInfo">
                            <span class="additionalProductInfo accountNumber">
                                <html:link href="${infoCardPageLink}">
                                    ${phiz:getCutCardNumber(cardLink.number)}
                                </html:link>
                            </span>
                            <c:if test="${mainCard.cardStatus.code == 'I'}">
                                <br/>
                                <span class="detailStatus">${mobileBankCardStatus}</span>
                            </c:if>
                        </tiles:put>
                        <tiles:put name="rightData">
                            <div class="simpleTable">
                                <c:if test="${not empty curCard.favoriteSmsRequests}">
                                    <c:set target="${form}"
                                           property="smsCommands"
                                           value="${curCard.favoriteSmsRequests}"/>
                                    <tiles:insert page="sms-command-list.compact.jsp" flush="false"/>
                                </c:if>
                            </div>
                        </tiles:put>
                        <c:if test="${curCard.cardStatus.code=='I'}">
                            <tiles:put name="status" value="error"/>
                        </c:if>
                    </tiles:insert>
                </div>
                <c:if test="${elementsCount != i}">
                    <div class="productDivider"></div>
                </c:if>
            </c:if>
        </logic:iterate>
    </c:if>
</div>
</div>

<tiles:insert definition="window">
    <tiles:put name="id" value="openChangeQuickServiceWindow${mainCard.cardNumber}"/>
    <tiles:put name="data">
        <tiles:insert page="changeQuickService.jsp" flush="false"/>
    </tiles:put>
</tiles:insert>
