<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>

    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>


            <tiles:insert definition="mainWorkspace" flush="false">
                <c:set var="titleText">
                    <c:if test="${pageKind == 'index'}">
                        ќплата:  ${paymentService.name}
                    </c:if>
                    <c:if test="${pageKind == 'basket'}">
                        –егион:
                    </c:if>
                </c:set>
                <tiles:put name="title"><span class="size24">${titleText}</span></tiles:put>
                <tiles:put name="controlLeft">
                    <div id="reigonSearchName" class="regionSelect" onclick="win.open('searchRegionsDiv'); return false">
                        <input type="hidden" name="field(regionId)" value="${frm.fields.regionId}"/>
                        <input type="hidden" name="field(regionName)" value="${frm.fields.regionName}"/>
                        <span id="reigonSearchNameSpan" title="${frm.fields.regionName}" class="reigonSearch">
                            <c:choose>
                                <%--  “екущий регион --%>
                                <c:when test="${empty frm.fields.regionName}">
                                    ¬се регионы
                                </c:when>
                                <c:otherwise>
                                    ${frm.fields.regionName}
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function goback()
                        {
                            loadNewAction('', '');
                            window.location = '${phiz:calculateActionURL(pageContext,backURL)}';
                        }
                    </script>
                    <c:set var="imageId" value="${paymentService.imageId}"/>
                    <c:choose>
                        <c:when test="${not empty imageId}">
                            <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                            <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                        </c:when>
                        <c:otherwise>
                           <c:set var="image" value="${imagePathGlobal}${paymentService.defaultImage}" />
                        </c:otherwise>
                    </c:choose>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${image}"/>
                        <tiles:put name="description">
                            <h3>
                                <c:if test="${pageKind == 'index'}">
                                    ¬ыберите организацию или услугу, которую ¬ы хотите оплатить. „тобы ограничить количество получателей на странице, выберите регион оплаты или воспользуйтесь поиском.
                                </c:if>
                                <c:if test="${pageKind == 'basket'}">
                                    ¬оспользуйтесь поиском или выберите из списка подход€щую услугу.
                                </c:if>
                            </h3>
                        </tiles:put>
                        <tiles:put name="width" value="64px"/>
                        <tiles:put name="height" value="64px"/>
                    </tiles:insert>
                    <div class="clear"></div>

                    <c:if test="${pageKind == 'index'}">
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
                    </c:if>
                    <c:if test="${pageKind == 'basket'}">
                        <div id="sp-paymentStripe">
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="выбор услуги"/>
                                <tiles:put name="current" value="true"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="заполнение формы"/>
                            </tiles:insert>
                            <tiles:insert definition="stripe" flush="false">
                                <tiles:put name="name" value="подтверждение"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </div>
                    </c:if>
                    <%--форма дл€ поиска--%>
                    <c:url var="serviceAction" value="${serviceURL}">
                        <c:param name="fromResource" value="${fromResource}"/>
                        <c:if test="${pageKind == 'basket'}">
                            <c:param name="accountingEntityId" value="${frm.accountingEntityId}"/>
                        </c:if>
                    </c:url>
                    <c:url var="providerAction" value="${providerURL}">
                        <c:param name="fromResource" value="${fromResource}"/>
                        <c:if test="${pageKind == 'basket'}">
                            <c:param name="serviceId" value="${frm.serviceId}"/>
                            <c:param name="accountingEntityId" value="${frm.accountingEntityId}"/>
                        </c:if>
                    </c:url>
                    <c:url var="categoryAction" value="${categoryURL}">
                        <c:param name="fromResource" value="${fromResource}"/>
                        <c:if test="${pageKind == 'basket'}">
                            <c:param name="accountingEntityId" value="${frm.accountingEntityId}"/>
                        </c:if>
                    </c:url>
                    <c:set var="showSearchRegion" value="false"/>
                    <c:set var="isSmsTemplateCreation" value="false" scope="request"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listServicesPaymentSearch.jsp" %>

                   <c:if test="${not isSearch}">
                        <input type="hidden" name="currentPage" id="currentPage">
                        <c:set var="providers" value="${frm.providers}"/>
                        <c:set var="servicesList" value="${frm.services}"/>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="${paymentListURL}"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                        <div class="clear"></div>
                        <div class="paymentsContainer">
                            <div class="servicePaymentGroup">
                                <div class="supplierGroup">
                                    <c:if test="${empty servicesList and empty providers}">
                                        <div class="emptyText emptyResults">
                                            ƒл€ ¬ашего региона оплаты <c:if test="${frm.serviceId != null && frm.serviceId != ''}">в категории У${paymentService.name}Ф</c:if> не найдены поставщики услуг. ¬ы можете:
                                            <ul>
                                               <li>  расширить область поиска, выбрав <span class="underline"><html:link href="#"  onclick="win.open('searchRegionsDiv'); return false;">другой регион оплаты</html:link></span>; </li>
                                               <li>  воспользоватьс€ строкой поиска; </li>
                                               <li>  совершить <span class="underline"><phiz:link  action="/private/payments/jurPayment/edit" serviceId="JurPayment">перевод по реквизитам</phiz:link></span>;</li>
                                               <li>  или <span class="underline"><html:link href="#"  onclick="goback();">вернутьс€ на страницу выбора услуг</html:link></span>. </li>
                                            </ul>
                                        </div>
                                    </c:if>
                                    <c:set var="group" value="0"/>
                                    <c:set var="links" value=""/>

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
                                                    <tiles:put name="title">${groupName}</tiles:put>
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
                                                    <tiles:put name="url"><c:url value="${serviceURL}">
                                                        <phiz:param name="serviceId" value="${groupId}"/>
                                                        <phiz:param name="fromResource" value="${fromResource}"/>
                                                        <c:if test="${pageKind == 'basket'}">
                                                            <c:param name="accountingEntityId" value="${frm.accountingEntityId}"/>
                                                        </c:if>
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
                                                    <tiles:put name="title">${el[1]}</tiles:put>
                                                    <tiles:put name="titleClass" value="providerName"/>
                                                    <c:if test="${el[7]}">
                                                        <tiles:put name="availableAutopayment">
                                                            <div class="availableAutopayment">доступен автоплатеж</div>
                                                        </tiles:put>
                                                    </c:if>
                                                    <c:choose>
                                                        <c:when test="${not empty imageId}">
                                                            <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
                                                            <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                           <c:set var="image" value="${imagePathGlobal}/IQWave-other.jpg" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <tiles:put name="imagePath"  value="${image}"/>
                                                    <tiles:put name="defaultImagePath" value="${imagePathGlobal}/IQWave-other.jpg"/>
                                                    <tiles:put name="url"><c:url value="${providerURL}">
                                                        <phiz:param name="categoryId" value="${frm.categoryId}"/>
                                                        <phiz:param name="serviceId" value="${frm.serviceId}"/>
                                                        <phiz:param name="recipient" value="${el[0]}"/>
                                                        <phiz:param name="fromResource" value="${fromResource}"/>
                                                        <c:if test="${pageKind == 'basket'}">
                                                            <c:param name="accountingEntityId" value="${frm.accountingEntityId}"/>
                                                        </c:if>
                                                    </c:url></tiles:put>
                                                    <tiles:put name="serviceId"  value="RurPayJurSB"/>
                                                    <tiles:put name="hint"       value="${el[3]}"/>
                                                </tiles:insert>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
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
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="${paymentListURL}"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                   </c:if>
                </tiles:put>
            </tiles:insert>