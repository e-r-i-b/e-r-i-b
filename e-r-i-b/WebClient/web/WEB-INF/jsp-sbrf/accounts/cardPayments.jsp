<%--
  Created by IntelliJ IDEA.
  User: kligina
  Date: 11.02.2011
  Time: 17:13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/cards/payments"  onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="cardLink" value="${form.cardLink}" scope="request"/>
<c:set var="page" value="cardsDetail" scope="request" />
<c:set var="card"    value="${cardLink.card}"/>
<c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
<c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>

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
                            ${cardLink.patternForFavouriteLink}
                        </c:otherwise>
                    </c:choose>
                </c:set>

                <div class="abstractContainer3">
                    <c:set var="baseInfo">
                        <bean:message key="favourite.link.card" bundle="paymentsBundle"/>
                    </c:set>
                    <div class="favouriteLinksButton">
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
                        <tiles:put name="count" value="4"/>
                        <c:if test="${!cardNotCreditNotMain}">
                            <tiles:put name="count" value="3"/>
                        </c:if>
                        <tiles:put name="tabItems">
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="first"/>
                                <tiles:put name="active" value="false"/>
                                <tiles:put name="title" value="Последние операции"/>
                                <tiles:put name="action" value="/private/cards/info?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="active" value="false"/>
                                <tiles:put name="title" value="Информация по карте"/>
                                <tiles:put name="action" value="/private/cards/detail.do?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="active" value="true"/>
                                <c:if test="${!cardNotCreditNotMain}">
                                    <tiles:put name="position" value="last"/>
                                </c:if>
                                <tiles:put name="title" value="Шаблоны и платежи"/>
                                <tiles:put name="action" value="/private/cards/payments.do?id=${cardLink.id}"/>
                            </tiles:insert>
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
                </div>

                <div class="clear">&nbsp;</div>

                <%@ include file="showTemplatesAndPayments.jsp"%>
            </tiles:put>
        </tiles:insert>
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
                            &nbsp;
                            <c:forEach items="${form.otherCards}" var="others">
                                <div class="another-container">
                                    <c:choose>
                                        <c:when test="${not empty others.card.cardLevel and others.card.cardLevel == 'MQ'}">
                                            <c:set var="imgSrc" value="mq" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="imgSrc" value="${phiz:getCardImageCode(others.description)}" />
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
