<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--todo CHG059738 удалить--%>
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/mobilebank/payments/view-category-provider"  onsubmit="return setEmptyAction(event)">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
    <c:set var="categoryId" value="${frm.categoryId}"/>
    <c:set var="groupServices" value="${frm.groupServices}"/>
    <c:set var="services" value="${frm.services}"/>
    <c:set var="currentPage" value="${frm.currentPage}"/>
    <c:url var="serviceAction" value="/private/mobilebank/payments/select-service-provider.do">
        <c:param name="phoneCode" value="${frm.phoneCode}"/>
        <c:param name="cardCode" value="${frm.cardCode}"/>
    </c:url>
    <c:url var="providerAction" value="/private/mobilebank/payments/create-template.do">
        <c:param name="phoneCode" value="${frm.phoneCode}"/>
        <c:param name="cardCode" value="${frm.cardCode}"/>
    </c:url>
    <c:url var="categoryAction" value="/private/mobilebank/payments/view-category-provider.do">
        <c:param name="phoneCode" value="${frm.phoneCode}"/>
        <c:param name="cardCode" value="${frm.cardCode}"/>
    </c:url>
    <tiles:insert definition="paymentMain">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мобильный банк"/>
                <tiles:put name="action" value="/private/mobilebank/main.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Создание SMS-шаблона"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title">Оплата:  <bean:message key="category.operations.${categoryId}" bundle="paymentServicesBundle"/></tiles:put>
                <tiles:put name="data">
                    <%@ include file="templateHeader.jsp" %>
                    <%--форма для поиска--%>
                    <c:set var="pageType" value="mobilebank" scope="request"/>
                    <c:set var="isSmsTemplateCreation" value="true" scope="request"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listServicesPaymentSearch.jsp" %>
                    <c:if test="${not isSearch}">
                    <input type="hidden" name="currentPage" id="currentPage">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                        <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick" value="goback();"/>
                        <tiles:put name="image"       value="backIcon.png"/>
                        <tiles:put name="imageHover"     value="backIconHover.png"/>
                        <tiles:put name="imagePosition"  value="left"/>
                    </tiles:insert>
                    <div class="clear"></div>
                        <div class="paymentsContainer">
                            <div class="categoryPayments">
                                <input type="hidden" name="id" id="id" value="${categoryId}" />
                                <c:if test="${empty groupServices and empty services}">
                                    <div class="emptyResults">Не найдено ни одной организации, соответствующей параметрам поиска. Пожалуйста, задайте другие параметры.</div>
                                </c:if>
                                 <c:set var="group" value="0"/>
                                <c:set var="links" value=""/>
                                <c:set var="groupServicesCount" value="0" />
                                <c:if test="${not empty groupServices}">
                                    <c:forEach var="el" items="${groupServices}">
                                        <c:set var="groupId" value="${el[0]}"/>

                                        <c:set var="serviceId" value="${el[5]}"/>
                                        <c:set var="serviceName" value="${el[6]}"/>

                                        <c:if test="${group == 0}">
                                            <c:set var="group" value="${el[0]}"/>
                                            <c:set var="groupName" value="${el[1]}"/>
                                            <c:set var="groupDesc" value="${el[2]}"/>
                                            <c:set var="imageId" value="${el[3]}"/>
                                            <c:set var="defaultImage" value="${el[4]}"/>
                                            <c:set var="groupServicesCount" value="${groupServicesCount+1}" />
                                        </c:if>

                                        <c:if test="${group != 0 && group != groupId}">
                                            <tiles:insert definition="categoryTemplate" flush="false">
                                                <tiles:put name="title">${groupName}</tiles:put>
                                                <tiles:put name="hint">
                                                    ${groupDesc}
                                                </tiles:put>
                                                <tiles:put name="hintLink">
                                                    <div class ="hintLinks">
                                                        ${links}
                                                    </div>
                                                    <div class ="hintLinks">
                                                         <phiz:link url="${serviceAction}" serviceId="RurPayJurSB" styleClass="more">
                                                             <phiz:param name="serviceId" value="${group}"/>
                                                             еще&hellip;
                                                         </phiz:link>
                                                    </div>
                                                </tiles:put>
                                                <c:choose>
                                                    <c:when test="${not empty imageId}">
                                                        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                                        <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                       <c:set var="image" value="${imagePathGlobal}${defaultImage}" />
                                                    </c:otherwise>
                                                </c:choose>
                                                <tiles:put name="imagePath" value="${image}"/>
                                                <tiles:put name="url"><c:url value="/private/mobilebank/payments/select-service-provider.do">
                                                    <c:param name="phoneCode" value="${frm.phoneCode}"/>
                                                    <c:param name="cardCode" value="${frm.cardCode}"/>
                                                    <c:param name="serviceId" value="${group}"/>
                                                </c:url></tiles:put>
                                            </tiles:insert>
                                            <c:set var="group" value="${el[0]}"/>
                                            <c:set var="groupName" value="${el[1]}"/>
                                            <c:set var="groupDesc" value="${el[2]}"/>
                                            <c:set var="imageId" value="${el[3]}"/>
                                            <c:set var="defaultImage" value="${el[4]}"/>
                                            <c:set var="links" value=""/>
                                            <c:set var="groupServicesCount" value="${groupServicesCount+1}" />
                                        </c:if>

                                        <c:if test="${group == groupId}">
                                            <c:set var="links">
                                                <phiz:link url="${serviceAction}" serviceId="RurPayJurSB" styleClass="more">
                                                    <phiz:param name="serviceId" value="${serviceId}"/>
                                                    ${serviceName}
                                                </phiz:link>
                                            </c:set>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${groupServicesCount <= itemsPerPage}">
                                        <tiles:insert definition="categoryTemplate" flush="false">
                                            <tiles:put name="title">${groupName}</tiles:put>
                                            <tiles:put name="hint">
                                                ${groupDesc}
                                            </tiles:put>
                                            <tiles:put name="hintLink">
                                                <div class ="hintLinks">
                                                    ${links}
                                                </div>
                                                <div class ="hintLinks">
                                                    <phiz:link url="${serviceAction}" serviceId="RurPayJurSB" styleClass="more">
                                                        <phiz:param name="serviceId" value="${groupId}"/>
                                                        еще&hellip;
                                                    </phiz:link>
                                                </div>
                                            </tiles:put>
                                            <c:choose>
                                                <c:when test="${not empty imageId}">
                                                    <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                                    <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                </c:when>
                                                <c:otherwise>
                                                   <c:set var="image" value="${imagePathGlobal}${defaultImage}" />
                                                </c:otherwise>
                                            </c:choose>
                                            <tiles:put name="imagePath" value="${image}"/>
                                            <tiles:put name="url"><c:url value="/private/mobilebank/payments/select-service-provider.do">
                                                <c:param name="phoneCode" value="${frm.phoneCode}"/>
                                                <c:param name="cardCode" value="${frm.cardCode}"/>
                                                <c:param name="serviceId" value="${groupId}"/>
                                            </c:url></tiles:put>
                                           <tiles:put name="serviceId" value="RurPayJurSB"/>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>

                                <c:set var="group" value="0"/>
                                <c:set var="links" value=""/>
                                <c:set var="servicesCount" value="0" />
                                <c:if test="${not empty services}">
                                    <c:forEach var="el" items="${services}">
                                        <c:set var="groupId" value="${el[0]}"/>

                                        <c:set var="providerId" value="${el[5]}"/>
                                        <c:set var="providerName" value="${el[6]}"/>

                                        <c:if test="${group == 0}">
                                            <c:set var="group" value="${el[0]}"/>
                                            <c:set var="groupName" value="${el[1]}"/>
                                            <c:set var="groupDesc" value="${el[2]}"/>
                                            <c:set var="imageId" value="${el[3]}"/>
                                            <c:set var="defaultImage" value="${el[4]}"/>
                                            <c:set var="servicesCount" value="${servicesCount+1}" />
                                        </c:if>

                                        <c:if test="${group != 0 && group != groupId}">
                                            <tiles:insert definition="categoryTemplate" flush="false">
                                                <tiles:put name="title">${groupName}</tiles:put>
                                                <tiles:put name="hint">
                                                    ${groupDesc}
                                                </tiles:put>
                                                <tiles:put name="hintLink">
                                                    <div class ="hintLinks">
                                                        ${links}
                                                    </div>
                                                    <div class ="hintLinks">
                                                        <phiz:link url="${serviceAction}" serviceId="RurPayJurSB" styleClass="more">
                                                            <phiz:param name="serviceId" value="${group}"/>
                                                            еще&hellip;
                                                        </phiz:link>
                                                    </div>
                                                </tiles:put>
                                                <c:choose>
                                                    <c:when test="${not empty imageId}">
                                                        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                                        <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                       <c:set var="image" value="${imagePathGlobal}${defaultImage}" />
                                                    </c:otherwise>
                                                </c:choose>
                                                <tiles:put name="imagePath" value="${image}"/>
                                                <tiles:put name="url"><c:url value="/private/mobilebank/payments/select-service-provider.do">
                                                    <c:param name="phoneCode" value="${frm.phoneCode}"/>
                                                    <c:param name="cardCode" value="${frm.cardCode}"/>
                                                    <c:param name="serviceId" value="${group}"/>
                                                </c:url></tiles:put>
                                                <tiles:put name="serviceId" value="RurPayJurSB"/>
                                            </tiles:insert>
                                            <c:set var="group" value="${el[0]}"/>
                                            <c:set var="groupName" value="${el[1]}"/>
                                            <c:set var="groupDesc" value="${el[2]}"/>
                                            <c:set var="imageId" value="${el[3]}"/>
                                            <c:set var="defaultImage" value="${el[4]}"/>
                                            <c:set var="links" value=""/>
                                            <c:set var="servicesCount" value="${servicesCount+1}" />
                                        </c:if>

                                        <c:if test="${group == groupId}">
                                            <c:set var="links">
                                                ${links}<phiz:link url="${providerAction}" serviceId="RurPayJurSB"><phiz:param name="recipient" value="${providerId}"/>${providerName}</phiz:link>
                                            </c:set>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${servicesCount+groupServicesCount <= itemsPerPage}">
                                       <tiles:insert definition="categoryTemplate" flush="false">
                                            <tiles:put name="title">${groupName}</tiles:put>
                                            <tiles:put name="hint">
                                                ${groupDesc}
                                            </tiles:put>
                                            <tiles:put name="hintLink">
                                                <div class ="hintLinks">
                                                    ${links}
                                                </div>
                                                <div class ="hintLinks">
                                                    <phiz:link url="${serviceAction}" serviceId="RurPayJurSB" styleClass="more">
                                                        <phiz:param name="serviceId" value="${groupId}"/>
                                                        еще&hellip;
                                                    </phiz:link>
                                                </div>
                                            </tiles:put>
                                            <c:choose>
                                                <c:when test="${not empty imageId}">
                                                    <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                                    <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                </c:when>
                                                <c:otherwise>
                                                   <c:set var="image" value="${imagePathGlobal}${defaultImage}" />
                                                </c:otherwise>
                                            </c:choose>
                                            <tiles:put name="imagePath" value="${image}"/>
                                           <tiles:put name="url"><c:url value="/private/mobilebank/payments/select-service-provider.do">
                                               <c:param name="phoneCode" value="${frm.phoneCode}"/>
                                               <c:param name="cardCode" value="${frm.cardCode}"/>
                                               <c:param name="serviceId" value="${groupId}"/>
                                           </c:url></tiles:put>
                                            <tiles:put name="serviceId" value="RurPayJurSB"/>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>

                            </div>
                               <c:if test="${currentPage > 0 || groupServicesCount+servicesCount > itemsPerPage}">
                                <div class="pagination">
                                    <c:choose>
                                        <c:when test="${currentPage gt 0}">
                                            <a class="active-arrow" href="#" onclick="changePage(${currentPage-1})"><div class="activePaginLeftArrow"></div></a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="inactivePaginLeftArrow"></div>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${groupServicesCount + servicesCount gt itemsPerPage}">
                                            <a class="active-arrow" href="#" onclick="changePage(${currentPage+1})"><div class="activePaginRightArrow"></div></a>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="inactivePaginRightArrow"></div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>
                        <div class="clear"></div>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="mobilebankBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="onclick" value="goback();"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                        </c:if>
                    </tiles:put>
                </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>