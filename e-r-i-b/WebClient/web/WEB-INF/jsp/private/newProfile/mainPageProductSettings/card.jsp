<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--Информация по картам--%>
<div id="sortableCards">
        <div class="sortableHeader">
            <bean:message bundle="userprofileBundle" key="title.cards"/>
        </div>
        <div id="sortableCardsShow"  class="connectedSortable">
            <c:set var="cardsCount" value="${phiz:size(form.cards)}"/>
            <c:set var="countShowCards" value="0"/>
            <logic:iterate id="cardLink" name="form" property="cards">
                <c:if test="${cardLink.showInMain}">
                    <c:set var="countShowCards" value="${countShowCards+1}"/>
                    <c:set var="description"    value="${cardLink.description}"/>
                    <c:set var="card"           value="${cardLink.card}"/>

                    <c:choose>
                        <c:when test="${not empty card.cardLevel and card.cardLevel == 'MQ'}">
                            <c:set var="imgCode" value="mq" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
                        </c:otherwise>
                    </c:choose>

                    <div class="sortableProductLinks">
                        <html:hidden property="sortedCardIds" value="${cardLink.id}"/>
                        <div class="sortIcon opacitySort"></div>
                        <div class="tinyProductImg opacitySort">
                            <c:choose>
                                <c:when test="${cardLink.card.cardState != 'active'}">
                                    <img src="${imagePath}/cards_type/icon_cards_${imgCode}32Blocked.jpg"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${imagePath}/cards_type/icon_cards_${imgCode}32.gif"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="left opacitySort">
                            <div class="titleBlock">
                                <html:multibox name="form" property="selectedCardIds" value="${cardLink.id}"  styleId="userProfileSecurity${cardLink.id}"  styleClass="hideCheckbox"/>
                                <c:set var="linkName"><c:out value="${cardLink.name}"/></c:set>
                                <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                                <div class="lightness"></div>
                            </div>
                            <c:set var="formattedNumber" value="${phiz:getCutCardNumber(cardLink.number)}"/>
                            <span class="productNumber">${phiz:changeWhiteSpaces(formattedNumber)}</span>
                        </div>
                        <div class="right opacitySort">
                            <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(cardLink.card.availableLimit)}</span>
                        </div>
                    </div>
                </c:if>
            </logic:iterate>
            <div class="sortableMenuLinksShowDesc" <c:if test="${countShowCards > 0}">style="display:none"</c:if>>
                <span>Перетащите сюда, чтобы показать</span>
            </div>
        </div>
        <div class="sortableMenuLinksHide">
            <div class="sortableMenuLinksHideTitle"  <c:if test="${countShowCards == cardsCount}">style="display:none"</c:if>>СКРЫТЫ НА ГЛАВНОЙ</div>
            <div id="sortableCardsHide"  class="connectedSortable">
                <logic:iterate id="cardLink" name="form" property="cards">
                    <c:if test="${!cardLink.showInMain}">
                        <c:set var="card"        value="${cardLink.card}"/>
                        <c:set var="description" value="${cardLink.description}"/>

                        <c:choose>
                            <c:when test="${not empty card.cardLevel and card.cardLevel == 'MQ'}">
                                <c:set var="imgCode" value="mq" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="imgCode" value="${phiz:getCardImageCode(description)}" />
                            </c:otherwise>
                        </c:choose>

                        <div class="sortableProductLinks">
                            <html:hidden property="sortedCardIds" value="${cardLink.id}"/>
                            <div class="sortIcon opacitySort"></div>
                            <div class="tinyProductImg opacitySort">
                                <c:choose>
                                    <c:when test="${cardLink.card.cardState != 'active'}">
                                        <img src="${imagePath}/cards_type/icon_cards_${imgCode}32Blocked.jpg"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${imagePath}/cards_type/icon_cards_${imgCode}32.gif"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="left opacitySort">
                                <div class="titleBlock">
                                    <html:multibox name="form" property="selectedCardIds"  value="${cardLink.id}" styleId="userProfileSecurity${cardLink.id}"  styleClass="hideCheckbox"/>
                                    <c:set var="linkName"><c:out value="${cardLink.name}"/></c:set>
                                    <span class="linkTitle">${phiz:changeWhiteSpaces(linkName)}</span>
                                    <div class="lightness"></div>
                                </div>
                                <c:set var="formattedNumber" value="${phiz:getCutCardNumber(cardLink.number)}"/>
                                <span class="productNumber">${phiz:changeWhiteSpaces(formattedNumber)}</span>
                            </div>
                            <div class="right opacitySort">
                                <span class="sortableProductCurrency">${phiz:getHtmlFormatAmount(cardLink.card.availableLimit)}</span>
                            </div>
                        </div>
                    </c:if>
                </logic:iterate>
            </div>
            <div class="sortableMenuLinksHideDesc" <c:if test="${countShowCards != cardsCount}">style="display:none;"</c:if>>
                Перетащите сюда, чтобы скрыть
            </div>
        </div>
</div>