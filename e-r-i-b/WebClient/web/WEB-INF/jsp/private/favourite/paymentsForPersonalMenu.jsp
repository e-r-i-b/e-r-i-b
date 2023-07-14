<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<html:form action="/private/async/favourite/listAutoPaymentsForPersonalMenu" onsubmit="return setEmptyAction(event)">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="activePayments" value="${form.activePaymentsForPersonalMenu}"/>
<c:set var="longOfferInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/longOffers/info?id=')}"/>
<c:set var="autoPaymentInfoUrl" value="${phiz:calculateActionURL(pageContext, '/private/autopayments/info?id=')}"/>
<c:set var="autoSubsctiptionUrl" value="${phiz:calculateActionURL(pageContext, '/private/autosubscriptions/info?id=')}"/>
<c:set var="autoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/favourite/list/AutoPayments.do")}</c:set>
<c:set var="addAutoPaymentsURL">${phiz:calculateActionURL(pageContext,"/private/autopayment/select-category-provider.do")}</c:set>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="helpRegularPayments" value="${phiz:calculateActionURL(pageContext,'help.do?id=/private/payments#m4')}"/>
<c:set var="imageItemList" value="<img src='${imagePath}/newItem.png' class='newItemList'/>"/>
<c:set var="imageList" value="<img src='${imagePath}/newGroup.png' class='newGroup'/>"/>
<c:choose>
    <c:when test="${phiz:isNeedToShowP2PNewMark()}">
        <c:set var="autoSubscriptionsListImage" value="${imageList}"></c:set>
        <c:set var="createAutoSubscriptionsImage" value="${imageItemList}"/>
    </c:when>
    <c:otherwise>
        <c:set var="createAutoSubscriptionsImage" value=""/>
        <c:set var="autoSubscriptionsListImage" value=""/>
    </c:otherwise>
</c:choose>
<phiz:linksList num="0" styleClass="underlined" listSourceName="autoPaymentsLinkList" image="${autoSubscriptionsListImage}" title="Мои автоплатежи" titleStyleClass="linksListTitle">
    <c:choose>
        <c:when test="${empty activePayments}">
        </c:when>
        <c:otherwise>
            <c:forEach items="${activePayments}" var="link">
                <c:set var="isLongOffer" value="${phiz:isInstance(link, 'com.rssl.phizic.business.resources.external.LongOfferLink')}"/>
                <c:set var="isAutoSubscription" value="${phiz:isInstance(link, 'com.rssl.phizic.business.resources.external.AutoSubscriptionLink')}"/>
                <c:set var="isAutoPayment" value="${phiz:isInstance(link, 'com.rssl.phizic.business.resources.external.AutoPaymentLink')}"/>
                <c:set var="payment" value="${link.value}"/>

                <%-- Строим ссылку для перехода при нажатии на просмотр автоплатежа. --%>
                <c:choose>
                    <c:when test="${not isAutoSubscription}">
                        <c:set var="curId"><bean:write name='link' property="id" ignore="true"/></c:set>
                        <c:set var="curUrl">
                            ${isLongOffer ? longOfferInfoUrl : autoPaymentInfoUrl}${curId}
                        </c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="curId"><bean:write name='payment' property="number" ignore="true"/></c:set>
                        <c:set var="curUrl">
                            ${autoSubsctiptionUrl}${curId}
                        </c:set>
                    </c:otherwise>
                </c:choose>

                <c:set var="title">
                    <bean:write name="link" property="name" ignore="true"/>
                </c:set>
                <c:set var="titleTag" value="<span>${title}</span>"/>
                <phiz:linksListItem title="${titleTag}" href="${curUrl}" onClick="return personalMenuItemClick(event);"/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <c:set var="titleTag" value="<span>Подключить автоплатеж</span>"/>
    <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" image="${createAutoSubscriptionsImage}" href="${addAutoPaymentsURL}" onClick="return redirectResolved();"/>
    <c:set var="titleTag" value="<span>Управление автоплатежами</span>"/>
    <phiz:linksListItem title="${titleTag}" styleClass="templateAndPaymentControl" href="${autoPaymentsURL}" onClick="return redirectResolved();"/>
</phiz:linksList>
</html:form>