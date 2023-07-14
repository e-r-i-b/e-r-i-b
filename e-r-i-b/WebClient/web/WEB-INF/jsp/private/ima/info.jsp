<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/ima/info" onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="image" value="${globalUrl}/commonSkin/images"/>

<tiles:insert definition="imaInfo">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imAccountLink" value="${form.imAccountLink}"/>
<c:set var="imAccountAbstract" value="${form.imAccountAbstract}"/>
<c:set var="abstractEmpty"     value="${empty imAccountAbstract || empty imAccountAbstract.transactions}"/>
<c:set var="imAccount" value="${imAccountLink.imAccount}"/>
<c:set var="showInMainCheckBox" value="true"/>
<c:set var="isAbstractPage" value="true"/>
<c:set var="detailsPage" value="true"/>

<tiles:put name="mainmenu" value="IMAInfo"/>
<tiles:put name="menu" type="string"/>

<tiles:put name="breadcrumbs">
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="main" value="true"/>
        <tiles:put name="action" value="/private/accounts.do"/>
    </tiles:insert>
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="name" value="Металлические счета"/>
        <tiles:put name="action" value="/private/ima/list.do"/>
    </tiles:insert>
    <tiles:insert definition="breadcrumbsLink" flush="false">
        <tiles:put name="name"><bean:write name="imAccountLink" property="name"/> ${phiz:getFormattedAccountNumber(imAccountLink.number)}</tiles:put>
        <tiles:put name="last" value="true"/>
    </tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<%@include file="scripts.jsp" %>
<html:hidden property="id"/>
<div id="cards">
    <div id="card-detail">
        <c:if test="${not empty imAccountLink}">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">

                    <c:set var="model"    value="simple-pagination"/>
                    <c:set var="hideable" value="false"/>
                    <c:set var="recCount" value="10"/>

                    <c:set var="nameOrNumber">
                        <c:choose>
                            <c:when test="${not empty imAccountLink.name}">
                                «${imAccountLink.name}»
                            </c:when>
                            <c:otherwise>
                                ${imAccountLink.number}
                            </c:otherwise>
                        </c:choose>
                    </c:set>

                    <c:set var="pattern">
                        <c:choose>
                            <c:when test="${not empty imAccountLink.name}">
                                «${imAccountLink.patternForFavouriteLink}»
                            </c:when>
                            <c:otherwise>
                                ${imAccountLink.patternForFavouriteLink}
                            </c:otherwise>
                        </c:choose>
                    </c:set>

                    <div class="abstractContainer3">
                        <c:set var="baseInfo">
                            <bean:message key="favourite.link.ima" bundle="paymentsBundle"/>
                        </c:set>
                        <div class="favouriteLinksButton">
                            <tiles:insert definition="addToFavouriteButton" flush="false">
                                <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                                <tiles:put name="patternName"><c:out value='${baseInfo} ${pattern}'/></tiles:put>
                                <tiles:put name="typeFormat">IMA_LINK</tiles:put>
                                <tiles:put name="productId">${form.id}</tiles:put>
                            </tiles:insert>
                        </div>
                    </div>
                    <%@include file="imaTemplate.jsp" %>

                    <%-- TODO Полная выписка не приходит, поэтому фильтр отключён.
                    <tiles:insert definition="filterDataPeriod" flush="false">
                        <tiles:put name="id" value="detailFilterButton"/>
                        <tiles:put name="buttonKey" value="button.filter"/>
                        <tiles:put name="buttonBundle" value="imaBundle"/>
                        <tiles:put name="name" value="Abstract"/>
                        <tiles:put name="needErrorValidate" value="true"/>
                    </tiles:insert>
                    --%>
                    <div class="tabContainer">
                        <tiles:insert definition="paymentTabs" flush="false">
                            <tiles:put name="count" value="2"/>
                            <tiles:put name="tabItems">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="first"/>
                                    <tiles:put name="active" value="true"/>
                                    <tiles:put name="title" value="Последние операции"/>
                                    <tiles:put name="action" value="/private/ima/info.do?id=${imAccountLink.id}"/>
                                </tiles:insert>
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="position" value="last"/>
                                    <tiles:put name="active" value="false"/>
                                    <tiles:put name="title" value="Детальная информация"/>
                                    <tiles:put name="action" value="/private/ima/detail.do?id=${imAccountLink.id}"/>
                                </tiles:insert>
                            </tiles:put>
                        </tiles:insert>

                        <div class="abstractFilter">

                            <div class="abstractContainer2">
                                <c:if test="${not abstractEmpty}">
                                    <div class="printButtonRight">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.print"/>
                                            <tiles:put name="commandHelpKey" value="button.print"/>
                                            <tiles:put name="bundle" value="imaBundle"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="image"    value="print-check.gif"/>
                                            <tiles:put name="imageHover"     value="print-check-hover.gif"/>
                                            <tiles:put name="imagePosition" value="left"/>
                                            <tiles:put name="onclick">printIMAPayments(event, 'abstract', 'printAbstract')</tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="clear">&nbsp;</div>
                        <c:set var="style" value="detailInfoTable" scope="request"/>
                        <%@include file="printAbstractTable.jsp" %>
                    </div>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </c:if>

        <c:if test="${not empty form.additionalIMAccountLink}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <c:set var="imAccountCount" value="${phiz:getClientProductLinksCount('IM_ACCOUNT')}"/>
                    <tiles:put name="title">
                        Остальные металлические счета
                        (<a href="${phiz:calculateActionURL(pageContext, '/private/ima/list')}" class="blueGrayLink">показать все ${imAccountCount}</a>)
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="another-items">
                            <div class="clear"></div>
                            <c:set var="imaInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/ima/info.do?id=')}"/>
                            <c:forEach items="${form.additionalIMAccountLink}" var="additional">
                                <c:set var="additionalIMAccount" value="${additional.imAccount}"/>
                                <div class="another-container">
                                    <a href="${imaInfoUrl}${additional.id}">
                                        <img src="${image}/ima_type/imaccount32.jpg" alt="${additional.name}"/>
                                    </a>
                                    <a class="another-name" href="${imaInfoUrl}${additional.id}">
                                        <c:choose>
                                            <c:when test="${not empty additional.name}">
                                                <bean:write name="additional" property="name"/>
                                            </c:when>
                                            <c:when test="${not empty additionalIMAccount.currency}">
                                                <c:out value="${additionalIMAccount.currency.name} (${phiz:normalizeCurrencyCode(additionalIMAccount.currency.code)})"/>
                                            </c:when>
                                        </c:choose>
                                    </a>
                                    <div class="another-number">
                                        <a class="another-number decoration-none" href="${imaInfoUrl}${additional.id}">
                                            ${phiz:getFormattedAccountNumber(additional.number)}
                                        </a>
                                        <c:set var="state" value="${not empty additionalIMAccount.state ? additionalIMAccount.state : ''}"/>

                                        <c:set var="className">
                                            <c:if test="${state eq 'closed'}">
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
    </div>
</div>
</tiles:put>
</tiles:insert>
</html:form>
