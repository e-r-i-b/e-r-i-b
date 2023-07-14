<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:importAttribute/>

<c:url var="serviceAction" value="${serviceURL}">
    <c:param name="fromResource" value="${fromResource}"/>
</c:url>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<c:set var="currCatName" value=""/>
<c:set var="currGroupName" value=""/>
<c:set var="currService" value=""/>
<div class="providerFilterResults">
<c:forEach var="el" items="${result}" varStatus="stat">
    <c:set var="category"        value="${el[0]}"/>
    <c:set var="category_name"   value="${el[1]}"/>
    <c:set var="groupId"         value="${el[2]}"/>
    <c:set var="groupName"       value="${el[3]}"/>
    <c:set var="serviceId"       value="${el[4]}"/>
    <c:set var="serviceName"     value="${el[5]}"/>
    <c:set var="provider"        value="${el[6]}"/>
    <c:set var="providerName"    value="${el[7]}"/>
    <c:set var="imageId"         value="${el[8]}"/>
    <c:set var="INN"             value="${el[9]}"/>
    <c:set var="account"         value="${el[11]}"/>
    <c:set var="resultType"      value="${el[10]}"/>
    <c:set var="imageName" value="${el[12]}"/>
    <c:set var="showBreadcrumbs" value="${el[13]}"/>
    <c:set var="billingServiceName" value="${el[14]}"/>
    <c:if test="${resultType == 'payment'}">
        <c:set var="category"        value="${el[11]}"/>
    </c:if>
    <c:if test="${not (stringCount + providerCountInBlock/2 == 4 || (stringCount + providerCountInBlock/2 == 3.5 && (groupName != currServiceParent || serviceName != currService)))}">
        <c:if test="${providerCountInBlock % 2 == 0}">
            <div class="clear"></div>
        </c:if>
        <c:if test="${category_name != currCatName
        || groupName != currGroupName
        || serviceName != currService}">
            <c:if test="${providerCountInBlock % 2 == 1}">
                <c:set var="providerCountInBlock" value="${providerCountInBlock+1}"/>
            </c:if>
            <c:set var="stringCount" value="${stringCount + providerCountInBlock/2}"/>
            <c:set var="currCatName" value="${category_name}"/>
            <c:set var="currGroupName" value="${groupName}"/>
            <c:set var="currService" value="${serviceName}"/>
            <div class="clear"></div>
            <div class="titleRegion">
                <span class="bold">
                    <c:choose>
                        <c:when test="${not empty currService}">
                            <c:out value="${currService}"/>
                        </c:when>
                        <c:when test="${not empty currGroupName}">
                            <c:out value="${currGroupName}"/>
                        </c:when>
                        <c:when test="${not empty currCatName}">
                            <c:out value="${currCatName}"/>
                        </c:when>
                        <c:otherwise>
                            ѕрочие операции
                        </c:otherwise>
                    </c:choose>
                </span>
            </div>
            <c:set var="providerCountInBlock" value="0"/>
        </c:if>
        <c:set var="providerCount" value="${providerCount + 1}"/>
        <c:set var="providerCountInBlock" value="${providerCountInBlock + 1}"/>
        <c:choose>
            <c:when test="${resultType == 'provider'}">
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
                <c:url var="serviceProviderActionURL" value="${serviceProviderAction}">
                    <c:param name="recipient" value="${provider}"/>
                    <c:param name="fromResource" value="${fromResource}"/>
                    <c:param name="needSelectProvider" value="true"/>
                </c:url>
                <tiles:insert definition="providerTemplate" flush="false">
					<tiles:put name="id" value="${provider}"/>
                    <tiles:put name="providerName" value="${providerName}"/>
                    <tiles:put name="idForDiv" value="${providerCount}${provider}"/>
                    <tiles:put name="INN" value="${INN}"/>
                    <tiles:put name="account" value="${account}"/>
                    <tiles:put name="serviceName" value="${billingServiceName}"/>
                    <tiles:put name="image" value="${image}"/>
                    <tiles:put name="region" value="${empty otherRegions[0] ? '¬се регионы' : otherRegions[0]}"/>
                    <tiles:put name="otherRegions" value="${otherRegions[1]}"/>
                    <tiles:put name="service">
                        <c:if test="${showBreadcrumbs}">
                            <c:if test="${not empty serviceName}">
                                <span class="serviceGroup">
                                <phiz:link url="${serviceAction}" serviceId="RurPayJurSB">
                                    <phiz:param name="serviceId" value="${serviceId}"/>
                                    <c:if test="${category != null || groupId != null}">
                                        <phiz:param name="parentIds" value="${category},${groupId}"/>
                                    </c:if>
                                    ${serviceName}
                                </phiz:link>
                                </span>
                            </c:if>
                        </c:if>
                    </tiles:put>
                    <tiles:put name="action" value="${serviceProviderActionURL}"/>
                </tiles:insert>
            </c:when>
             <c:when test="${resultType == 'service'}">
                <%--про картинки--%>
                <c:choose>
                    <c:when test="${not empty imageId}">
                        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                        <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="image" value="${imagePathGlobal}${imageName}" />
                    </c:otherwise>
                </c:choose>

                <%-- /про картинки--%>
                <tiles:insert definition="providerTemplate" flush="false">
                    <tiles:put name="id" value="${serviceId}"/>
                    <tiles:put name="idForDiv" value="all${providerCount}${serviceId}"/>
                    <tiles:put name="providerName" value="${serviceName}"/>
                    <tiles:put name="image" value="${image}"/>
                    <tiles:put name="service">
                        <c:if test="${showBreadcrumbs}">
                            <c:if test="${not empty category_name}">
                                <span class="serviceGroup">
                                     <phiz:link url="${serviceAction}" serviceId="RurPayJurSB">
                                        <phiz:param name="serviceId" value="${category}"/>
                                        ${category_name}
                                    </phiz:link> &rarr;
                                </span>
                            </c:if>
                            <c:if test="${not empty groupName}">
                                <span class="serviceGroup">
                                    <phiz:link url="${serviceAction}" serviceId="RurPayJurSB">
                                        <phiz:param name="serviceId" value="${groupId}"/>
                                        <phiz:param name="parentIds" value="${category}"/>
                                        ${groupName}
                                    </phiz:link>
                                </span>
                            </c:if>
                        </c:if>
                    </tiles:put>
                    <c:url var="paymentActionURL" value="${serviceActionUrl}">
                        <c:param name="serviceId" value="${serviceId}"/>
                        <c:if test="${category != null || groupId != null}">
                            <c:param name="parentIds" value="${category},${groupId}"/>
                        </c:if>
                    </c:url>
                    <tiles:put name="action" value="${paymentActionURL}"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <c:set var="category" value="${el[9]}"/>
                 <c:choose>
                    <c:when test="${category == 'VirtualCardClaim' || category == 'LoanCardProduct'}"><c:set var="paymentImage" value="${imagePath}/card_64.png"/></c:when>
                    <c:when test="${category == 'LoanProduct' || category == 'LoanCardOffer' || category == 'LoanOffer'}"><c:set var="paymentImage" value="${imagePath}/credit_64.png"/></c:when>
                    <c:otherwise><c:set var="paymentImage" value="${imagePath}/iconPmntList_${category}.png"/></c:otherwise>
                </c:choose>
                <c:url var="paymentActionURL" value="${phiz:getLinkPaymentByFormName(category)}">
                    <c:param name="form" value="${category}"/>
                </c:url>
                <c:if test="${pageType == 'index'}">
                    <tiles:insert definition="providerTemplate" flush="false">
                        <tiles:put name="id" value="${provider}"/>
                        <tiles:put name="providerName" value="${providerName}"/>
                        <tiles:put name="image" value="${paymentImage}"/>
                        <tiles:put name="action" value="${paymentActionURL}"/>
                    </tiles:insert>
                </c:if>
            </c:otherwise>
        </c:choose>
   </c:if>
</c:forEach>
</div>

