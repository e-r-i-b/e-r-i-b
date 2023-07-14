<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="cardLinks" value="${form.cards}"/>
<c:set var="cardLinksCount" value="${fn:length(cardLinks)}"/>
<c:set var="showCardLinkIds" value="${form.showCardLinkIds}"/>
<c:set var="showCardLinksCount" value="${fn:length(showCardLinkIds)}"/>

<tiles:insert definition="widget" flush="false">
    <tiles:put name="digitClassname" value="widget.ProductBlockWidget"/>
    <tiles:put name="cssClassname" value="wide-not-sizeable-widget"/>

    <c:set var="cardListUrl" value="${phiz:calculateActionURL(pageContext, '/private/cards/list')}"/>
    <tiles:put name="title">
        У Вас
        <a href="${cardListUrl}"><b>${cardLinksCount}&nbsp;${phiz:numeralDeclension(cardLinksCount, "карт", "а", "ы", "")}</b></a><c:if
        test="${cardLinksCount > showCardLinksCount}">, ${phiz:numeralDeclension(showCardLinksCount, 'показан', 'а', 'о', 'о')} ${showCardLinksCount}</c:if>
    </tiles:put>
    
    <tiles:put name="linksControl">
        <a href="#" onclick="hideAllOperations(this); return false;" onmouseover="changeTitle(this);">
            показать/свернуть операции
        </a>
        &nbsp;|&nbsp;
        <a href="${cardListUrl}" title="К списку">
            все карты
        </a>
        &nbsp;|&nbsp;
        <a href="${phiz:calculateActionURL(pageContext, '/private/favourite/list')}" title="Настроить">
            настроить
        </a>
    </tiles:put>
    
    <tiles:put name="borderColor" value="greenTop"/>
    <tiles:put name="sizeable" value="false"/>
    <tiles:put name="editable" value="true"/>

    <tiles:put name="viewPanel">
        <c:if test="${not empty cardLinks}">
            <c:catch var="error">
                <c:set var="page" value="main" scope="request"/>
                <c:set var="cardInfoLink" value="true" scope="request"/>
                <c:set var="currentHiddenCount" value="${0}"/>
                <logic:iterate id="listElement" name="form" property="cards" indexId="i">
                    <c:set var="cardLink" value="${listElement}" scope="request"/>
                    <c:set var="showInThisWidgetCheckBox" value="false" scope="request"/>
                    <c:choose>
                        <c:when test="${showCardLinkIds ne null and phiz:contains(showCardLinkIds, cardLink.id)}">
                            <c:set var="resourceAbstract" value="${form.cardAbstract[listElement]}" scope="request"/>
                            <c:if test="${cardLink.main}">
                                <c:set var="mainNumber" value="${cardLink.number}"/>
                            </c:if>
                            <c:set var="showArrow" value="${not empty mainNumber and cardLink.mainCardNumber eq mainNumber}" scope="request"/>
                            <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/cardTemplate.jsp" flush="false"/>
                            <c:if test="${showCardLinksCount - 1 > i - currentHiddenCount}">
                                <div class="productDivider"></div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="currentHiddenCount" value="${currentHiddenCount + 1}"/>
                        </c:otherwise>
                    </c:choose>
                </logic:iterate>
            </c:catch>
            <c:if test="${not empty error}">
                ${phiz:setException(error, pageContext)}
            </c:if>
        </c:if>
    </tiles:put>


    <tiles:put name="editPanel">
        <%-- Та часть виджета, которая будет скроллиться в режиме редактирования--%>
        <c:if test="${not empty cardLinks}">
            <c:catch var="error">
                <c:set var="page" value="main" scope="request"/>
                <c:set var="cardInfoLink" value="true" scope="request"/>
                <logic:iterate id="listElement" name="form" property="cards" indexId="i">
                    <c:set var="cardLink" value="${listElement}" scope="request"/>
                    <c:set var="showInThisWidgetCheckBox" value="true" scope="request"/>
                    <c:set var="resourceAbstract" value="${form.cardAbstract[listElement]}" scope="request"/>
                    <c:if test="${cardLink.main}">
                        <c:set var="mainNumber" value="${cardLink.number}"/>
                    </c:if>
                    <c:set var="showArrow" value="${not empty mainNumber and cardLink.mainCardNumber eq mainNumber}" scope="request"/>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/accounts/cardTemplate.jsp" flush="false"/>
                    <c:if test="${cardLinksCount - 1 > i}">
                        <div class="productDivider"></div>
                    </c:if>
                </logic:iterate>
            </c:catch>
            <c:if test="${not empty error}">
                ${phiz:setException(error, pageContext)}
            </c:if>
        </c:if>

        <tiles:insert page="/WEB-INF/jsp/widget/widgetDeleteWindow.jsp" flush="false">
            <tiles:put name="productType" value="card"/>
            <tiles:put name="widgetId" value="${form.codename}"/>
        </tiles:insert>
        
    </tiles:put>
</tiles:insert>
