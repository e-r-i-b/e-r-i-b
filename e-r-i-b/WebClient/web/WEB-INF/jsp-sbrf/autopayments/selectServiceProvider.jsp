<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<html:form action="/private/autopayment/select-service-provider" onsubmit="return setEmptyAction();">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fromResource" value="${param['fromResource']}"/>
    <c:set var="trimSearchServices" value="${fn:trim(frm.searchServices)}"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(trimSearchServices) != 0}"/>
    <c:set var="currentPage" value="${frm.currentPage}"/>

    <c:url var="serviceAction" value="/private/autopayment/select-service-provider.do">
        <c:param name="fromResource" value="${fromResource}"/>
    </c:url>
    <c:url var="providerAction" value="/private/payments/servicesPayments/edit.do">
        <c:param name="createLongOffer" value="true"/>
        <c:param name="fromResource" value="${fromResource}"/>
    </c:url>

    <tiles:insert definition="autopayProviders">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мои автоплатежи"/>
                <tiles:put name="action" value="/private/favourite/list/AutoPayments.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Подключение автоплатежа"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <div>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title"><span class="size24">Автоплатеж</span></tiles:put>
                <tiles:put name="controlLeft">
                    <div id="reigonSearchName" class="regionSelect" onclick="win.open('searchRegionsDiv'); return false">
                        <input type="hidden" name="field(regionId)" value="${frm.fields.regionId}"/>
                        <input type="hidden" name="field(regionName)" value="${frm.fields.regionName}"/>
                        <span id="reigonSearchNameSpan" title="${frm.fields.regionName}" class="reigonSearch">
                            <c:choose>
                                <%--  Текущий регион --%>
                                <c:when test="${empty frm.fields.regionName}">
                                    Все регионы
                                </c:when>
                                <c:otherwise>
                                    ${frm.fields.regionName}
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </tiles:put>
                <tiles:put name="data">

                    <c:set var="goBackURL" value="${phiz:calculateActionURL(pageContext,'/private/autopayment/select-category-provider')}"/>
                    <script type="text/javascript">

                        function goback()
                        {
                            loadNewAction('', '');
                            window.location = '${goBackURL}';
                        }
                    </script>

                    <%-- ЗАГОЛОВОК --%>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/iconPmntList_LongOffer.png"/>
                        <tiles:put name="description">
                            Обратите внимание! Поиск выполняется среди организаций, в адрес которых можно оформить автоплатеж.
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>

                    <%-- ГРАДУСНИК --%>
                    <div id="paymentStripe">
                       <tiles:insert definition="stripe" flush="false">
                           <tiles:put name="name" value="выбор услуги"/>
                           <tiles:put name="current" value="true"/>
                       </tiles:insert>
                       <tiles:insert definition="stripe" flush="false">
                           <tiles:put name="name" value="заполнение реквизитов"/>
                       </tiles:insert>
                       <tiles:insert definition="stripe" flush="false">
                           <tiles:put name="name" value="подтверждение"/>
                       </tiles:insert>
                       <tiles:insert definition="stripe" flush="false">
                           <tiles:put name="name" value="статус операции"/>
                       </tiles:insert>
                       <div class="clear"></div>
                    </div>

                    <%-- ПОИСК --%>
                    <c:set var="pageType" value="autopayment" scope="request"/>
                    <c:set var="showSearchRegion" value="false"/>
                    <c:set var="isSmsTemplateCreation" value="false" scope="request"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listServicesPaymentSearch.jsp" %>

                    <c:if test="${not isSearch}">
                        <input type="hidden" name="currentPage" id="currentPage">
                        <c:set var="providers" value="${frm.providers}"/>
                        <c:set var="servicesList" value="${frm.services}"/>

                        <%-- назад к выбору услуг --%>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="onclick" value="goback();"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                        <div class="clear"></div>

                        <div class="paymentsContainer">
                            <div class="servicePaymentGroup">
                                <div class="supplierGroup">
                                    <c:if test="${empty servicesList and empty providers}">
                                        <div class="emptyResults">Не найдено ни одной организации, соответствующей параметрам поиска. Пожалуйста, задайте другие параметры.</div>
                                    </c:if>

                                    <c:set var="group" value="0"/>
                                    <c:set var="links" value=""/>
                                    <c:set var="servicesCount" value="0"/>

                                    <c:if test="${not empty servicesList}">
                                        <c:set var="servicesCount" value="${phiz:size(servicesList)}"/>
                                        <c:forEach var="el" items="${servicesList}" varStatus="stat">
                                            <c:set var="groupId" value="${el[0]}"/>
                                            <c:set var="groupName" value="${el[1]}"/>
                                            <c:set var="imageId" value="${el[2]}"/>
                                            <c:set var="defaultImage" value="${el[3]}"/>
                                            <c:if test="${stat.count <= itemsPerPage}">
                                                <tiles:insert definition="serviceTemplate" flush="false">
                                                    <c:if test="${stat.count % 2 == 0}">
                                                        <tiles:put name="templateClass" value="secondColumn"/>
                                                    </c:if>
                                                    <tiles:put name="titleClass" value="categoryTitle"/>
                                                    <tiles:put name="title"><span>${groupName}</span></tiles:put>
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
                                                    <tiles:put name="defaultImagePath" value="${imagePathGlobal}${defaultImage}"/>
                                                    <tiles:put name="url"><c:url value="/private/autopayment/select-service-provider.do">
                                                        <phiz:param name="serviceId" value="${groupId}"/>
                                                        <phiz:param name="fromResource" value="${fromResource}"/>
                                                     </c:url></tiles:put>
                                                    <tiles:put name="serviceId" value="RurPayJurSB"/>
                                                </tiles:insert>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>

                                    <c:set var="group" value="0"/>
                                    <c:set var="links" value=""/>

                                    <c:if test="${not empty providers}">
                                        <c:forEach var="el" items="${providers}" varStatus="stat">
                                            <c:if test="${stat.count le itemsPerPage}">
                                                <c:set var="imageId" value="${el[2]}"/>
                                                <tiles:insert definition="serviceTemplate" flush="false">
                                                    <c:if test="${stat.count % 2 == 0}">
                                                        <tiles:put name="templateClass" value="secondColumn"/>
                                                    </c:if>
                                                    <tiles:put name="titleClass" value="providerName"/>
                                                    <tiles:put name="title">${el[1]}</tiles:put>
                                                    <c:choose>
                                                        <c:when test="${not empty imageId}">
                                                            <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                                            <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                           <c:set var="image" value="${imagePathGlobal}/IQWave-other.jpg" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <tiles:put name="imagePath" value="${image}"/>
                                                    <tiles:put name="defaultImagePath" value="${imagePathGlobal}/IQWave-other.jpg"/>
                                                    <tiles:put name="url">
                                                        <c:url value="/private/payments/servicesPayments/edit.do">
                                                            <c:param name="createLongOffer" value="true"/>
                                                            <c:param name="recipient" value="${el[0]}"/>
                                                        </c:url>
                                                    </tiles:put>
                                                    <tiles:put name="serviceId" value="RurPayJurSB"/>
                                                </tiles:insert>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>

                            <%-- ПАГИНАЦИЯ --%>
                            <c:if test="${currentPage > 0 || fn:length(providers) > itemsPerPage || servicesCount > itemsPerPage}">
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
                                        <c:when test="${fn:length(providers) gt itemsPerPage || servicesCount gt itemsPerPage}">
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

                        <%-- назад к выбору услуг --%>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="onclick" value="goback();"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                        </c:if>
                </tiles:put>
            </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
