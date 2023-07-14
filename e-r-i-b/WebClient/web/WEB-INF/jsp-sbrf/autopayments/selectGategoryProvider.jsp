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
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<html:form action="/private/autopayment/select-category-provider" onsubmit="return setEmptyAction();">

    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
    <c:set var="fromResource" value="${param['fromResource']}"/>
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>

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
            <c:set var="goBackURL" value="${phiz:calculateActionURL(pageContext,'/private/autopayment/select-category-provider')}"/>
            <script type="text/javascript">

                function goback()
                {
                    loadNewAction('', '');
                    window.location = '${goBackURL}';
                }
            </script>

            <c:url var="serviceAction" value="/private/autopayment/select-service-provider.do">
                <c:param name="fromResource" value="${fromResource}"/>
            </c:url>
            <c:url var="providerAction" value="/private/payments/servicesPayments/edit.do">
                <c:param name="createLongOffer" value="true"/>
                <c:param name="fromResource" value="${fromResource}"/>
            </c:url>
            <c:url var="categoryAction" value="/private/autopayment/select-category-provider.do">
                <c:param name="fromResource" value="${fromResource}"/>
            </c:url>

            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Подключение автоплатежа"/>
                <tiles:put name="data">
                    <%-- ЗАГОЛОВОК --%>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${imagePathGlobal}/iconPmntList_LongOffer.jpg"/>
                        <tiles:put name="description">
                            <div class="titleItems">
                                <h2>Автоплатеж</h2>
                                <span>— это автоматическая оплата услуг за сотовую связь, ЖКХ, городской телефон и Интернет,
                                    а также кредитов других банков, штрафов ГИБДД и налогов. Услуга предоставляется бесплатно.</span>
                            </div>
                            <br />
                            <a class="separatedLink orangeText" target="_blank" href="http://sberbank.ru/ru/person/paymentsandremittances/payments/mobile/autopayment/"><span>Подробнее об услуге »</span></a>
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
                    <%-- ПЕРЕВОДЫ --%>
                    <c:if test="${phiz:impliesServiceRigid('CreateP2PAutoTransferClaim')}">
                        <jsp:include page="/WEB-INF/jsp-sbrf/autopayments/autoPaymentTransfers.jsp"/>
                    </c:if>
                    <div class="clear">&nbsp;</div>

                    <%-- ПОПУЛЯРНЫЕ АВТОПЛАТЕЖИ --%>
                    <div class="titleItems">
                        <h2>Популярные автоплатежи</h2>
                    </div>
                        <div class="clear">&nbsp;</div>
                        <c:if test="${phiz:impliesService('CreateAutoPaymentPayment')}">
                            <div class="clear"></div>
                            <c:set var="popularProviders" value="${phiz:getAutoPayPromoBlock(frm.fields.regionId)}"/>
                            <div class="autoPaymentListBorder">
                                <div class="createAutoPaymentForProviderButtonArea">
                                    <c:forEach items="${popularProviders}" var="provider">
                                        <c:choose>
                                            <c:when test="${not empty provider.imageId}">
                                                <c:set var="imageData" value="${phiz:getImageById(provider.imageId)}"/>
                                                <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="image" value="${imagePathGlobal}/IQWave-other.jpg" />
                                            </c:otherwise>
                                        </c:choose>
                                        <tiles:insert definition="serviceTemplate" flush="false">
                                            <tiles:put name="title">${provider.name}</tiles:put>
                                            <tiles:put name="imagePath" value="${image}"/>
                                            <tiles:put name="defaultImagePath" value="${imagePathGlobal}/IQWave-other.jpg"/>
                                            <tiles:put name="url">
                                                <c:url value="/private/payments/servicesPayments/edit.do">
                                                    <c:param name="createLongOffer" value="true"/>
                                                    <c:param name="recipient" value="${provider.id}"/>
                                                </c:url>
                                            </tiles:put>
                                        </tiles:insert>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:if>
                    <%-- ПОИСК --%>
                    <c:set var="pageType" value="autopayment" scope="request"/>
                    <c:set var="isSmsTemplateCreation" value="false" scope="request"/>
                    <c:set var="serviceURL" value="/private/autopayment/select-service-provider.do" scope="request"/>
                    <c:set var="providerURL" value="/private/payments/servicesPayments/edit.do?createLongOffer=true" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp-sbrf/payments/listServicesProviderSearch.jsp"/>

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
                                             <c:set var="imageId" value="${imagePathGlobal}${service.defaultImage}" />
                                         </c:otherwise>
                                     </c:choose>
                                     <tiles:insert definition="categoryTemplate" flush="false">
                                        <tiles:put name="title" value="${service.name}"/>
                                        <tiles:put name="imagePath">${imageId}</tiles:put>
                                        <tiles:put name="url"><c:url value="/private/autopayment/select-service-provider.do">
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
                                            <%-- сбрасывае счетчик --%>
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
                                                        <tiles:put name="url">
                                                            <c:url value="/private/autopayment/select-service-provider.do">
                                                                <c:param name="serviceId" value="${curCategory}"/>
                                                                <c:param name="fromResource" value="${fromResource}"/>
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
                                                        ${links}<li><phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                                    <span><phiz:param name="serviceId" value="${serviceId}"/>
                                                                    <phiz:param name="parentIds" value="${curCategory}"/>
                                                                   ${serviceName}<span>
                                                                </phiz:link> </li>
                                                    </c:set>
                                                </c:if>
                                             </c:forEach>
                                             <tiles:insert definition="serviceCategoryTemplate" flush="false">
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
                                                <tiles:put name="url">
                                                    <c:url value="/private/autopayment/select-service-provider.do">
                                                        <c:param name="serviceId" value="${curCategory}"/>
                                                        <c:param name="fromResource" value="${fromResource}"/>
                                                    </c:url>
                                                </tiles:put>
                                                <tiles:put name="serviceId" value="RurPayJurSB"/>
                                            </tiles:insert>
                                            <%-- АВТОПЛАТЕЖ ПО РЕКВИЗИТАМ --%>
                                            <c:if test="${frm.isAutoPaySearch && phiz:impliesService('ClientFreeDetailAutoSubManagement')}">
                                                <div class="payment" id="popularPaymentsBlock1">
                                                    <div class="popularPaymentsBlock">
                                                        <div class="receiptDotted"></div>
                                                        <div class="paymentOnReceipt" onclick="goTo('${phiz:calculateActionURL(pageContext, '/autopayment/freeDetatilAutoSub')}');">
                                                            <a class="popularPaymentsLink">
                                                                <span>Не нашли подходящий<br /> раздел, но знаете<br /> реквизиты?</span>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <script type="text/javascript">
                                                        $('#popularPaymentsBlock1').css('left', 368);
                                                        $('#popularPaymentsBlock1').css('top', rightCol + 14);
                                                        rightCol = rightCol +  $('#popularPaymentsBlock1').height();
                                                    </script>
                                                </div>
                                            </c:if>
                                            <div class="clear"></div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="emptyResults">
                                        <c:choose>
                                            <c:when test="${phiz:impliesService('ClientFreeDetailAutoSubManagement')}">
                                                Не найдено ни одной категории услуг. Пожалуйста, воспользуйтесь поиском поставщиков для создания автоплатежа или перейдите по ссылке <span class="underline"><phiz:link  action="/autopayment/freeDetatilAutoSub" serviceId="ClientFreeDetailAutoSubManagement">&laquo;Автоплатеж свободным по реквизитам&raquo;</phiz:link></span>.
                                            </c:when>
                                            <c:otherwise>
                                                Не найдено ни одной категории услуг. Пожалуйста, воспользуйтесь поиском поставщиков для создания автоплатежа.
                                            </c:otherwise>
                                        </c:choose>
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


