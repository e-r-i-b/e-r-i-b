<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="showBottomData" value="false" scope="request"/>
<c:set var="showInMainCheckBox" value="true" scope="request"/>
<c:set var="detailsPage" value="true" scope="request"/>

<c:set var="nameOrNumber">
    <c:choose>
        <c:when test="${empty accountLink.name}">
            ${phiz:getFormattedAccountNumber(account.number)}
        </c:when>
        <c:otherwise>
            «${accountLink.name}»
        </c:otherwise>
    </c:choose>
</c:set>

<c:set var="pattern">
    <c:choose>
        <c:when test="${empty accountLink.name}">
            ${accountLink.patternForFavouriteLink}
        </c:when>
        <c:otherwise>
            «${accountLink.patternForFavouriteLink}»
        </c:otherwise>
    </c:choose>
</c:set>

<div class="abstractContainer3">
    <c:set var="baseInfo" value="${type} "/>
    <div class="favouriteLinksButton">
        <tiles:insert definition="addToFavouriteButton" flush="false">
            <tiles:put name="formName"><c:out value='${baseInfo}${nameOrNumber}'/></tiles:put>
            <tiles:put name="patternName"><c:out value='${baseInfo}${pattern}'/></tiles:put>
            <tiles:put name="typeFormat">ACCOUNT_LINK</tiles:put>
            <tiles:put name="productId">${form.id}</tiles:put>
        </tiles:insert>
    </div>
</div>
<div class="clear"></div>
<%@ include file="accountTemplate.jsp" %>
<br/>

<div class="tabContainer">
    <tiles:insert definition="paymentTabs" flush="false">
        <c:set var="count" value="4"/>
        <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
            <c:set var="count" value="${count+1}"/>
        </c:if>
        <tiles:put name="count" value="${count}"/>
        <tiles:put name="tabItems">
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="first"/>
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Последние операции"/>
                <tiles:put name="action" value="/private/accounts/operations?id=${accountLink.id}"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Информация по вкладу"/>
                <tiles:put name="action" value="/private/accounts/info.do?id=${accountLink.id}"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="active" value="true"/>
                <tiles:put name="title" value="Шаблоны и платежи"/>
                <tiles:put name="action" value="/private/account/payments.do?id=${accountLink.id}"/>
            </tiles:insert>
            <tiles:insert definition="paymentTabItem" flush="false">
                <tiles:put name="position" value="last"/>
                <tiles:put name="active" value="false"/>
                <tiles:put name="title" value="Графическая выписка"/>
                <tiles:put name="action" value="/private/accounts/graphic-abstract.do?id=${accountLink.id}"/>
            </tiles:insert>
            <c:if test="${phiz:impliesService('MoneyBoxManagement')}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="position" value="last"/>
                    <tiles:put name="active" value="false"/>
                    <tiles:put name="title">
                        <div style="float:left">
                            <div style="display:inline-block ;vertical-align:middle;"><c:out value="Копилка"/></div>
                            <div class="il-newIconMoneyBoxSmall" style="display:inline-block ;vertical-align:middle;"></div>
                        </div>
                    </tiles:put>
                    <tiles:put name="action" value="/private/accounts/moneyBoxList.do?id=${accountLink.id}"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
    </tiles:insert>
</div>

<div class="abstractFilter">

    <c:set var="nameOrNumber">
        <c:choose>
            <c:when test="${empty accountLink.name}">
                ${phiz:getFormattedAccountNumber(account.number)}
            </c:when>
            <c:otherwise>
                «${accountLink.name}»
            </c:otherwise>
        </c:choose>
    </c:set>

    <c:set var="pattern">
        <c:choose>
            <c:when test="${empty accountLink.name}">
                ${accountLink.patternForFavouriteLink}
            </c:when>
            <c:otherwise>
                «${accountLink.patternForFavouriteLink}»
            </c:otherwise>
        </c:choose>
    </c:set>

    <div class="abstractContainer2">
        <c:set var="baseInfo" value="${type} "/>
        <div class="favouriteLinksButton">
            <tiles:insert definition="addToFavouriteButton" flush="false">
                <tiles:put name="formName"><c:out value='${baseInfo}${nameOrNumber}'/></tiles:put>
                <tiles:put name="patternName"><c:out value='${baseInfo}${pattern}'/></tiles:put>
            </tiles:insert>
        </div>
    </div>
</div>
<div class="clear"></div>
<%@ include file="showTemplatesAndPayments.jsp" %>


