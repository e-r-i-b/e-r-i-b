<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--todo CHG059738 удалить--%>
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<html:form action="/private/mobilebank/payments/select-category-provider"
           onsubmit="return setEmptyAction();">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
    <tiles:insert definition="mobilebank">
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

         <%-- ссылка на страницу выбора поставщиков для добавляемого шаблона --%>
        <c:url var="createTemplateFromPaymentPageLink"
               value="/private/mobilebank/payments/create-template-from-payment.do">
            <c:param name="phoneCode" value="${frm.phoneCode}"/>
            <c:param name="cardCode" value="${frm.cardCode}"/>
        </c:url>
             <script type="text/javascript">
                function gotoTemplates()
                {
                    loadNewAction('','');
                    window.location='${createTemplateFromPaymentPageLink}';
                }
            </script>
            <div id="mobilebank">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Создание SMS-шаблона"/>
                <tiles:put name="data">
                    <%@ include file="templateHeader.jsp" %>
                    <c:set var="pageType" value="mobilebank" scope="request"/>
                    <c:set var="isSmsTemplateCreation" value="true" scope="request"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listServicesPaymentSearch.jsp" %>
                    <c:if test="${not isSearch}">
                        <div class="paymentsContainer">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.goto.select.template"/>
                                <tiles:put name="commandHelpKey" value="button.goto.select.template"/>
                                <tiles:put name="bundle" value="mobilebankBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                                <tiles:put name="onclick" value="gotoTemplates()"/>
                            </tiles:insert>
                            <div class="clear"></div>
                            <%--<div class="servicePayments">--%>
                                 <%--<c:forEach var="service" items="${frm.services}">--%>
                                     <%--<c:choose>--%>
                                         <%--<c:when test="${not empty service.imageId}">--%>
                                             <%--<c:set var="imageData" value="${phiz:getImageById(service.imageId)}"/>--%>
                                             <%--<c:set var="imageId" value="${phiz:getAddressImage(imageData, pageContext)}"/>--%>
                                         <%--</c:when>--%>
                                         <%--<c:otherwise>--%>
                                             <%--<c:set var="imageId" value="${imagePathGlobal}${service.defaultImage}" />--%>
                                         <%--</c:otherwise>--%>
                                     <%--</c:choose>--%>
                                     <%--<tiles:insert definition="categoryTemplate" flush="false">--%>
                                        <%--<tiles:put name="title" value="${service.name}"/>--%>
                                        <%--<tiles:put name="imagePath">${imageId}</tiles:put>--%>
                                        <%--<tiles:put name="url"><c:url value="/private/mobilebank/payments/select-service-provider.do">--%>
                                            <%--<c:param name="serviceId" value="${service.id}"/>--%>
                                            <%--<c:param name="phoneCode" value="${frm.phoneCode}"/>--%>
                                            <%--<c:param name="cardCode" value="${frm.cardCode}"/>--%>
                                        <%--</c:url></tiles:put>--%>
                                        <%--<tiles:put name="serviceId" value="RurPayJurSB"/>--%>
                                    <%--</tiles:insert>--%>
                                 <%--</c:forEach>--%>
                                 <%--<div class="clear"></div>--%>
                             <%--</div>--%>

                            <c:choose>
                                <c:when test="${not empty frm.categories}">
                                    <div class="paymentCategories">
                                        <div class="categoryPayments supplierGroups" id="categories">
                                             <c:set var="curCategory" value="0"/>
                                             <c:forEach var="el" items="${frm.categories}">
                                                 <c:set var="serviceId" value="${el[4]}"/>
                                                 <c:set var="serviceName" value="${el[5]}"/>

                                                 <c:if test="${curCategory == 0}">
                                                     <c:set var="curCategory" value="${el[0]}"/>
                                                     <c:set var="categoryName" value="${el[1]}"/>
                                                     <c:set var="imageId" value="${el[2]}"/>
                                                     <c:set var="defaultImage" value="${el[3]}"/>
                                                     <c:set var="links" value=""/>
                                                 </c:if>

                                                 <c:if test="${curCategory != 0 && curCategory != el[0]}">
                                                     <tiles:insert definition="serviceTemplate" flush="false">
                                                         <tiles:put name="titleClass" value="categoryTitle"/>
                                                         <tiles:put name="title" value="${categoryName}"/>
                                                         <tiles:put name="links" value="${links}"/>
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
                                                        <tiles:put name="url" value="${serviceAction}&serviceId=${curCategory}"/>
                                                        <tiles:put name="serviceId" value="RurPayJurSB"/>
                                                    </tiles:insert>
                                                     <c:set var="curCategory" value="${el[0]}"/>
                                                     <c:set var="categoryName" value="${el[1]}"/>
                                                     <c:set var="imageId" value="${el[2]}"/>
                                                     <c:set var="defaultImage" value="${el[3]}"/>
                                                     <c:set var="links" value=""/>
                                                 </c:if>
                                                 <c:if test="${curCategory == el[0]}">
                                                    <c:set var="links">
                                                        ${links}<li><phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                                    <span><phiz:param name="serviceId" value="${serviceId}"/>
                                                                    <phiz:param name="parentIds" value="${curCategory}"/>
                                                                   ${serviceName}</span>
                                                                </phiz:link> </li>
                                                    </c:set>
                                                </c:if>
                                             </c:forEach>
                                             <tiles:insert definition="serviceTemplate" flush="false">
                                                 <tiles:put name="title" value="${categoryName}"/>
                                                 <tiles:put name="titleClass" value="categoryTitle"/>
                                                 <tiles:put name="links" value="${links}"/>
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
                                                <tiles:put name="url" value="${serviceAction}&serviceId=${curCategory}"/>
                                                <tiles:put name="serviceId" value="RurPayJurSB"/>
                                            </tiles:insert>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="emptyText emptyResults">
                                        Не найдено ни одной категории услуг. Пожалуйста, воспользуйтесь поиском поставщиков или выберите шаблон, на основе которого можно создать SMS-шаблон.
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <div class="clear"></div>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.goto.select.template"/>
                                <tiles:put name="commandHelpKey" value="button.goto.select.template"/>
                                <tiles:put name="bundle" value="mobilebankBundle"/>
                                <tiles:put name="viewType" value="blueGrayLink"/>
                                <tiles:put name="onclick" value="gotoTemplates()"/>
                            </tiles:insert>

                        </div>
                         </c:if>
                </tiles:put>
            </tiles:insert>
            </div>
        </tiles:put>

    </tiles:insert>

</html:form>


