<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<html:form action="/private/template/select-category" onsubmit="return setEmptyAction();">

    <c:set var="fromResource" value="${param['fromResource']}"/>
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="availableIMAPayment" value="${phiz:impliesOperation('CreateFormPaymentOperation','IMAPayment')}"/>
    <c:set var="fromFinanceCalendar" value="${frm.fromFinanceCalendar}"/>
    <c:set var="extractId" value="${frm.extractId}"/>

    <tiles:insert definition="templateProviders">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Мои шаблоны"/>
                <tiles:put name="action" value="/private/favourite/list/PaymentsAndTemplates.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Создание шаблона"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <c:set var="goBackURL" value="${phiz:calculateActionURL(pageContext,'/private/template/select-category')}"/>
            <script type="text/javascript">

                function goback()
                {
                    loadNewAction('', '');
                    window.location = '${goBackURL}?fromFinanceCalendar=${fromFinanceCalendar}&extractId=${extractId}';
                }
            </script>

            <c:url var="serviceAction" value="/private/template/select-service-provider.do">
                <c:param name="fromResource" value="${fromResource}"/>
                <c:if test="${fromFinanceCalendar}">
                    <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                    <c:param name="extractId" value="${extractId}"/>
                </c:if>
            </c:url>
            <c:url var="providerAction" value="/private/template/services-payments/edit.do">
                <c:param name="fromResource" value="${fromResource}"/>
                <c:if test="${fromFinanceCalendar}">
                    <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                    <c:param name="extractId" value="${extractId}"/>
                </c:if>
            </c:url>
            <c:url var="categoryAction" value="/private/template/select-category.do">
                <c:param name="fromResource" value="${fromResource}"/>
                <c:if test="${fromFinanceCalendar}">
                    <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                    <c:param name="extractId" value="${extractId}"/>
                </c:if>
            </c:url>
            <c:url var="paymentFormAction" value="/private/payments/template.do">
                <c:param name="fromResource" value="${fromResource}"/>
                <c:if test="${fromFinanceCalendar}">
                    <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                    <c:param name="extractId" value="${extractId}"/>
                </c:if>
            </c:url>
            <c:url var="jurPayAction" value="/private/template/jurPayment/edit.do">
                <c:param name="fromResource" value="${fromResource}"/>
                <c:if test="${fromFinanceCalendar}">
                    <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                    <c:param name="extractId" value="${extractId}"/>
                </c:if>
            </c:url>

            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Создание шаблона"/>
                <tiles:put name="data">
                    <%-- ЗАГОЛОВОК --%>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePath}/icon_template.png"/>
                        <tiles:put name="description">
                            Выберите платеж, для которого нужно создать шаблон. Для быстрого поиска платежа введите его название и нажмите на кнопку «Найти».
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>

                    <%-- ГРАДУСНИК --%>
                    <div id="paymentStripe">
                       <tiles:insert definition="stripe" flush="false">
                           <tiles:put name="name" value="выбор операции"/>
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

                    <%-- Переводы --%>
                    <jsp:include page="/WEB-INF/jsp-sbrf/payments/template/translationTemplates.jsp"/>
                    <%-- ПОИСК --%>
                    <c:set var="isTemplate" value="true" scope="request"/>
                    <c:set var="pageType" value="template" scope="request"/>
                    <c:set var="isSmsTemplateCreation" value="false" scope="request"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listServicesPaymentSearch.jsp" %>

                    <%-- КАТЕГОРИИ И ГРУППЫ УСЛУГ --%>
                    <c:if test="${not isSearch}">
                        <div class="paymentsContainer">
<%--                            <div class="servicePayments">
                                 <c:forEach var="service" items="${frm.services}">
                                     <c:choose>
                                         <c:when test="${not empty service.imageId}">
                                             <c:set var="imageData" value="${phiz:getImageById(service.imageId)}"/>
                                             <c:set var="imageId" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                         </c:when>
                                         <c:otherwise>
                                             <c:set var="imageId" value="${imagePath}${service.defaultImage}" />
                                         </c:otherwise>
                                     </c:choose>
                                     <tiles:insert definition="categoryTemplate" flush="false">
                                        <tiles:put name="title" value="${service.name}"/>
                                        <tiles:put name="imagePath">${imageId}</tiles:put>
                                        <tiles:put name="url"><c:url value="/private/template/select-service-provider.do">
                                            <c:param name="serviceId" value="${service.id}"/>
                                        </c:url></tiles:put>
                                        <tiles:put name="serviceId" value="RurPayJurSB"/>
                                    </tiles:insert>
                                 </c:forEach>
                                 <div class="clear"></div>
                            </div>--%>
                            <script type="text/javascript">
                                var rightCol = 0;
                                var leftCol = 0;
                            </script>
                            <c:choose>
                                <c:when test="${not empty frm.categories}">
                                    <div class="paymentCategories">
                                        <div class="categoryPayments doubleColumn" id="categories">
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
                                                     <tiles:insert definition="serviceCategoryTemplate" flush="false">
                                                         <tiles:put name="title" value="<span>${categoryName}</span>"/>
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
                                                        <tiles:put name="url">
                                                            <c:url value="/private/template/select-service-provider.do">
                                                                <c:param name="serviceId" value="${curCategory}"/>
                                                                <c:param name="fromResource" value="${fromResource}"/>
                                                                <c:if test="${fromFinanceCalendar}">
                                                                    <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                                                                    <c:param name="extractId" value="${extractId}"/>
                                                                </c:if>
                                                            </c:url>
                                                        </tiles:put>
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
                                                        ${links}<li><phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB"><span>
                                                                    <phiz:param name="serviceId" value="${serviceId}"/>
                                                                    <phiz:param name="parentIds" value="${curCategory}"/>
                                                                   ${serviceName}
                                                                </span></phiz:link> </li>
                                                    </c:set>
                                                </c:if>
                                             </c:forEach>
                                             <tiles:insert definition="serviceCategoryTemplate" flush="false">
                                                 <tiles:put name="title" value="<span>${categoryName}</span>"/>
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
                                                <tiles:put name="url">
                                                    <c:url value="/private/template/select-service-provider.do">
                                                        <c:param name="serviceId" value="${curCategory}"/>
                                                        <c:param name="fromResource" value="${fromResource}"/>
                                                        <c:if test="${fromFinanceCalendar}">
                                                            <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                                                            <c:param name="extractId" value="${extractId}"/>
                                                        </c:if>
                                                    </c:url>
                                                </tiles:put>
                                                <tiles:put name="serviceId" value="RurPayJurSB"/>
                                            </tiles:insert>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="emptyText">
                                        <tiles:insert definition="roundBorderLight" flush="false">
                                            <tiles:put name="color" value="greenBold"/>
                                            <tiles:put name="data">
                                                Не найдено ни одной категории услуг. Пожалуйста, воспользуйтесь поиском поставщиков для создания шаблона.
                                            </tiles:put>
                                        </tiles:insert>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <script type="text/javascript">
                            $('.doubleColumn').css('height', leftCol>rightCol ? leftCol : rightCol);
                        </script>
                    </c:if>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>