<%--
  Created by IntelliJ IDEA.
  User: akrenev
  Date: 27.09.2011
  Time: 10:56:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--Ссылки добавленные пользователем--%>
<c:choose>
    <c:when test="${not empty isWidget}">
        <c:choose>
            <c:when test="${not empty showFavouriteLinks}">
                <c:set var="links" value="${showFavouriteLinks}"/>
            </c:when>
            <c:otherwise>
                <c:set var="emptyLinks" value="${true}"/>
                        </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:set var="links" value="${phiz:getUserLinks()}"/>
        <c:set var="emptyLinks" value="${empty links}"/>
    </c:otherwise>
</c:choose>


<div class="favouriteLinkTitleItem">
    <phiz:linksList num="12" styleClass="underlined"  listSourceName="favouriteLinkList">
        <%--История операций--%>
        <c:if test="${phiz:impliesService('PaymentList')}">
            <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/payments/common.do?&status=all")}</c:set>
            <c:set var="title">
                <div class="greenTitle">
                    <span><bean:message key="label.payments.history" bundle="favouriteBundle"/></span>
                </div>
            </c:set>
            <phiz:linksListItem title="${title}" href="${url}" onClick="return redirectResolved();"/>
        </c:if>

        <c:if test="${phiz:impliesService('MobileWallet')}">
            <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/mobilewallet/edit.do")}</c:set>
            <c:set var="title">
                <div class="greenTitle"><span><bean:message key="label.mobilewallet.edit" bundle="mobileWallet"/></span></div>
            </c:set>
            <phiz:linksListItem title="${title}" href="${url}" onClick="return redirectResolved();"/>
        </c:if>

        <%--Мобильные приложения--%>
        <c:if test="${phiz:impliesService('ShowConnectedMobileDevicesService') and not phiz:impliesServiceRigid('NewClientProfile')}">
            <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/mobileApplications/view.do")}</c:set>
            <c:set var="title">
                <div class="greenTitle"><span><bean:message key="label.mobileApplication" bundle="favouriteBundle"/></span></div>
            </c:set>
            <phiz:linksListItem title="${title}" href="${url}" onClick="return redirectResolved();"/>
        </c:if>


       <%--Социальные приложения--%>
        <c:if test="${phiz:impliesService('ShowConnectedSocialAppService') and not phiz:impliesServiceRigid('NewClientProfile')}">
            <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/socialApplications/view.do")}</c:set>
            <c:set var="title">
                <div class="greenTitle"><span><bean:message key="label.socialApplication" bundle="favouriteBundle"/></span></div>
            </c:set>
            <phiz:linksListItem title="${title}" href="${url}" onClick="return redirectResolved();"/>
        </c:if>

        <%--Регистрация предпринимателя на сайте ФНС--%>
        <c:if test="${phiz:impliesService('BusinessmanRegistrationService')}">
            <c:set var="title">
                <div class="greenTitle"><span><bean:message key="label.businessmanRegistration" bundle="favouriteBundle"/></span></div>
            </c:set>
            <phiz:linksListItem title="${title}" href="#" onClick="openBusinessmanRegistrationWindow(event);"/>
        </c:if>

        <%-- Мои интернет-заказы --%>
        <c:if test="${phiz:impliesOperation('InternetOrderListOperation','InternetOrderPayments')}">
            <c:set var="url">${phiz:calculateActionURL(pageContext,"private/payments/internetShops/orderList.do")}</c:set>
            <c:set var="title">
                <div class="greenTitle"><span><bean:message key="label.internetOrders" bundle="favouriteBundle"/></span></div>
            </c:set>
            <phiz:linksListItem title="${title}" href="${url}" onClick="return redirectResolved();"/>
        </c:if>

        <c:if test="${phiz:impliesService('LoyaltyService')}">
            <c:set var="title">
                <div class="greenTitle">
                    <span><bean:message key="label.loyalty" bundle="loyaltyBundle"/></span>
                </div>
            </c:set>
            <c:set var="loyaltyAsyncUrl" >${phiz:calculateActionURL(pageContext,"/private/async/loyalty.do")}</c:set>
            <phiz:linksListItem title="${title}" href="#" onClick="loyaltyWindow('${loyaltyAsyncUrl}', event);"/>
        </c:if>

        <c:if test="${phiz:impliesService('LoyaltyProgramRegistrationClaim')}">
            <c:set var="url">${phiz:calculateActionURL(pageContext,"/private/loyalty/detail")}</c:set>
            <c:set var="title">
                <div class="greenTitle">
                    <span><bean:message key="label.loyalty" bundle="loyaltyBundle"/></span>
                </div>
            </c:set>
            <phiz:linksListItem title="${title}" href="${url}" onClick="return redirectResolved();"/>
        </c:if>

    </phiz:linksList>
</div>
<c:if test="${emptyLinks}">
    <div class="note">
        ${linksNote}
    </div>
</c:if>