<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>



<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<html:form action="/private/async/providers" onsubmit="return setEmptyAction(event)">
&nbsp;
    <script type="text/javascript">
        var providerZIndex1 = 10;
        function setZIndexNew(id)
        {
            var parent = document.getElementById('provider' + id);
            var otherRegions = document.getElementById('otherRegions' + id);
            $(parent).css('z-index', providerZIndex1);
            if (otherRegions != undefined)
                $('#otherRegions'+id).css('z-index', providerZIndex1);
            providerZIndex1--;
        }
    </script>
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="pageType" value="${frm.pageType}"/>
    <c:set var="findProviderParams" value=""/>
    <c:choose>
        <c:when test="${pageType == 'autopayment'}">
            <c:set var="serviceProviderAction" value="/private/payments/servicesPayments/edit.do?createLongOffer=true"/>
            <c:url var="serviceAction" value="/private/autopayment/select-service-provider.do"/>
        </c:when>
        <c:when test="${pageType == 'basketServices'}">
            <c:set var="serviceProviderAction" value="/private/userprofile/basket/subscription/create.do?accountingEntityId=${accountingEntityIdParam}"/>
            <c:url var="serviceAction" value="/private/userprofile/serviceContent.do"/>
            <c:set var="findProviderParams" value="${frm.accountingEntityId}"/>
        </c:when>
        <c:otherwise>
            <c:set var="serviceProviderAction" value="/private/payments/servicesPayments/edit.do"/>
            <c:url var="serviceAction" value="/private/payments/servicesPayments.do"/>
        </c:otherwise>
    </c:choose>
    <div class="providerFilterResults">
    <div class="otherRegionsAreaHide">
       <div class="greenTitle hide" onclick="hideSearchResult();">—крыть результаты без учета региона</div>
    </div>

    <c:set var="result" value="${frm.searchResults}"/>
    <c:set var="regions" value="${frm.regions}"/>
    <c:if test="${empty result}">
        <div class="emptyText emptyResults">
            <span class="normal">
                Ќе найдено ни одного получател€, соответствующего услови€м поиска. ѕожалуйста, задайте другие параметры или щелкните по ссылке <phiz:link styleClass="orangeText" action="/private/payments/jurPayment/edit" serviceId="JurPayment"><span>&laquo;ќплата по реквизитам или квитанции&raquo;</span></phiz:link>.
            </span>
        </div>
    </c:if>
    <input type="hidden" id="pageListByRegion" value="${frm.pageListByRegion}"/>
    <c:set var="provCount" value="${frm.provCount}"/>
    <c:set var="currRegion" value=""/>
    <c:set var="stringCount" value="0"/>
    <c:set var="providerCountInBlock" value="0"/>
    <c:set var="providerCount" value="0"/>
    <c:set var="maxStringCount" value="${frm.resultCount/2}"/>
    <c:if test="${frm.searchPage == 0}">
        <c:set var="maxStringCount" value="${frm.firstPageProviderCount/2}"/>
    </c:if>

    <c:forEach var="el" items="${result}" varStatus="stat">
        <c:set var="serviceId"       value="${el[0]}"/>
        <c:set var="serviceName"     value="${el[1]}"/>
        <c:set var="provider" value="${el[2]}"/>
        <c:set var="providerName" value="${el[3]}"/>
        <c:set var="imageId" value="${el[4]}"/>
        <c:set var="INN" value="${el[5]}"/>
        <c:set var="region" value="${el[6]}"/>
        <c:set var="account" value="${el[7]}"/>
        <c:set var="showBreadcrumbs" value="${el[8]}"/>
        <c:set var="billingServiceName" value="${el[9]}"/>
        <c:if test="${not (stringCount + providerCountInBlock/2 == maxStringCount || (stringCount + providerCountInBlock/2 == (maxStringCount-0.5) && region != currRegion))}">
            <c:if test="${providerCountInBlock % 2 == 0}">
                <div class="clear"></div>
            </c:if>
            <c:if test="${region != currRegion}">
                <c:if test="${providerCountInBlock % 2 == 1}">
                    <c:set var="providerCountInBlock" value="${providerCountInBlock+1}"/>
                </c:if>
                <c:set var="stringCount" value="${stringCount + providerCountInBlock/2}"/>
                <c:set var="currRegion" value="${region}"/>
                <div class="clear"></div>
                <div class="titleRegion">
                    <span class="bold">${currRegion}</span>
                </div>
                <c:set var="providerCountInBlock" value="0"/>
            </c:if>
            <c:set var="providerCount" value="${providerCount + 1}"/>
            <c:set var="providerCountInBlock" value="${providerCountInBlock + 1}"/>

            <%--про картинки--%>
            <c:choose>
                <c:when test="${not empty imageId}">
                    <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                    <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="image" value="${imagePath}/defaultProviderIcon.jpg" />
                </c:otherwise>
            </c:choose>
            <%-- /про картинки--%>
            <c:set var="otherRegions" value="${regions[provider]}"/>
            <c:set var="fromResource" value="${frm.fromResource}"/>
            <c:url var="serviceProviderActionURL" value="${serviceProviderAction}">
                <c:param name="recipient" value="${provider}"/>
                <c:param name="fromResource" value="${fromResource}"/>
                <c:param name="needSelectProvider" value="true"/>
            </c:url>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="id" value="${provider}"/>
                <tiles:put name="idForDiv" value="${providerCount}${provider}"/>
                <tiles:put name="providerName" value="${providerName}"/>
                <tiles:put name="INN" value="${INN}"/>
                <tiles:put name="account" value="${account}"/>
                <tiles:put name="serviceName" value="${billingServiceName}"/>
                <tiles:put name="image" value="${image}"/>
                <tiles:put name="region" value="${otherRegions[0]}"/>
                <tiles:put name="otherRegions" value="${otherRegions[1]}"/>
                <tiles:put name="zIndFunc" value="setZIndexNew"/>
                <tiles:put name="service">
                   <c:if test="${showBreadcrumbs}">
                         <c:if test="${not empty serviceName}">
                            <span class="serviceGroup">
                            <phiz:link url="${serviceAction}" serviceId="RurPayJurSB">
                                <phiz:param name="serviceId" value="${serviceId}"/>
                                ${serviceName}
                            </phiz:link>
                            </span>
                        </c:if>
                    </c:if>
                </tiles:put>
                <tiles:put name="action" value="${serviceProviderActionURL}"/>
            </tiles:insert>
        </c:if>
    </c:forEach>
    </div>
    <div class="clear"></div>
    <c:if test="${provCount > 0 || fn:length(result) > providerCount}">
        <div class="buttonsArea">
            <html:hidden property="searchServices" styleId="currentSearchingServicesMaskContainer"/>
            <c:choose>
                <c:when test="${provCount gt 0}">
                    <a class="active-arrow" onclick="findProvider(${providerCount}, 'last', ${provCount}, ${frm.firstPageProviderCount}, ${frm.searchPage}, 'false', 'currentSearchingServicesMaskContainer', '${pageType}', '${findProviderParams}');"><div class="activePaginLeftArrow"></div></a>
                </c:when>
                <c:otherwise>
                    <div class="inactivePaginLeftArrow"></div>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${fn:length(result) gt providerCount}">
                    <a class="active-arrow" onclick="findProvider(${providerCount}, 'next', ${provCount}, ${frm.firstPageProviderCount}, ${frm.searchPage}, 'false', 'currentSearchingServicesMaskContainer', '${pageType}', '${findProviderParams}');"><div class="activePaginRightArrow"></div></a>
                </c:when>
                <c:otherwise>
                    <div class="inactivePaginRightArrow"></div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</html:form>
