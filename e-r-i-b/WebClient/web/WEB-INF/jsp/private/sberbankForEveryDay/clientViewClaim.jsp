<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/sberbankForEveryDay/viewClaim" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="claim" value="${form.claim}"/>
    <c:set var="mobileBankTariff" value=""/>
    <c:set var="isGuest" value="${phiz:isGuest()}"/>
    <c:choose>
        <c:when test="${isGuest}">
            <tiles:insert definition="guestPage">
                <tiles:put name="mainMenuType" value="guestMainMenu"/>
                <tiles:put name="mainmenu" value="Index"/>
                <tiles:put name="data" type="string">
                    <%@ include file="clientViewClaimData.jsp" %>
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="main">
                <tiles:put name="breadcrumbs">
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="main" value="true"/>
                        <tiles:put name="action" value="/private/accounts.do"/>
                    </tiles:insert>
                    <tiles:insert definition="breadcrumbsLink" flush="false">
                        <tiles:put name="name" value="Карты"/>
                        <tiles:put name="action" value="/private/cards/list.do"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data" type="string">
                    <%@ include file="clientViewClaimData.jsp" %>
                </tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>

</html:form>