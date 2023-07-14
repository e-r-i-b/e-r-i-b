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

<html:form action="/private/payments/category"  onsubmit="return setEmptyAction(event)">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fromResource" value="${param['fromResource']}"/>
    <c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
    <c:set var="categoryId" value="${frm.categoryId}"/>
    <c:set var="groupServices" value="${frm.groupServices}"/>
    <c:set var="servicesList" value="${frm.services}"/>
    <c:set var="currentPage" value="${frm.currentPage}"/>
    <tiles:insert definition="paymentMain">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="action" value="/private/payments.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <c:set var="payMsg" value=""/>
                <c:if test="${categoryId != 'DEPOSITS_AND_LOANS' && categoryId != 'PFR'}">
                    <c:set var="payMsg" value="ќплата: "/>
                </c:if>                
                <tiles:put name="name">${payMsg}<bean:message key="category.operations.${categoryId}" bundle="paymentServicesBundle"/></tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function goback()
                {
                    loadNewAction('', '');
                    window.location = '${phiz:calculateActionURL(pageContext,'/private/payments')}';
                }
            </script>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title">ќплата:  <bean:message key="category.operations.${categoryId}" bundle="paymentServicesBundle"/></tiles:put>
                <tiles:put name="data">
                    <c:set var="image">${imagePathGlobal}/<bean:message key="category.operations.${categoryId}.image" bundle="paymentServicesBundle"/></c:set>
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="image" value="${image}"/>
                        <tiles:put name="description">
                            <c:choose>
                                <%--ѕереводы и обмен валют--%>
                                <c:when test="${not empty categoryId}">
                                    <bean:message key="category.operations.decs.${categoryId}" bundle="paymentServicesBundle"/>
                                </c:when>
                                <c:otherwise>
                                    ¬ыберите организацию или услугу, которую ¬ы хотите оплатить. „тобы ограничить количество получателей на странице, выберите регион оплаты или воспользуйтесь поиском.
                                </c:otherwise>
                            </c:choose>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
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
                    <%--форма дл€ поиска--%>
                    <c:url var="serviceAction" value="/private/payments/servicesPayments.do">
                        <c:param name="fromResource" value="${fromResource}"/>
                    </c:url>
                    <c:url var="providerAction" value="/private/payments/servicesPayments/edit.do">
                        <c:param name="fromResource" value="${fromResource}"/>
                    </c:url>
                    <c:url var="categoryAction" value="/private/payments/category.do">
                        <c:param name="fromResource" value="${fromResource}"/>
                    </c:url>
                    <c:set var="isSmsTemplateCreation" value="false" scope="request"/>
                    <%@ include file="/WEB-INF/jsp-sbrf/payments/listServicesPaymentSearch.jsp" %>
                    <c:if test="${not isSearch}">
                        <input type="hidden" name="currentPage" id="currentPage">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
                            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="/private/payments"/>
                            <tiles:put name="image"       value="backIcon.png"/>
                            <tiles:put name="imageHover"     value="backIconHover.png"/>
                            <tiles:put name="imagePosition"  value="left"/>
                        </tiles:insert>
                        <div class="clear"></div>
                        <div class="paymentsContainer">
                            <div class="categoryPayments">
                                <input type="hidden" name="id" id="id" value="${categoryId}" />
                                <c:set var="groupServicesCount" value="0" />
                                <c:if test="${currentPage == 0}">
                                    <c:choose>
                                        <c:when test="${categoryId == 'TRANSFER'}">
                                            <%@ include file="/WEB-INF/jsp-sbrf/payments/categories/transfersAndExchange.jsp" %>
                                            <c:set var="groupServicesCount" value="4" />
                                        </c:when>
                                        <c:when test="${categoryId == 'DEPOSITS_AND_LOANS'}">
                                            <%@ include file="/WEB-INF/jsp-sbrf/payments/categories/accountAndCreditPayments.jsp" %>
                                            <c:set var="groupServicesCount" value="3" />
                                        </c:when>
                                        <c:when test="${categoryId == 'TAX_PAYMENT'}">
                                            <%@ include file="/WEB-INF/jsp-sbrf/payments/categories/taxPayments.jsp" %>
                                            <c:set var="groupServicesCount" value="1" />
                                        </c:when>
                                        <c:when test="${categoryId == 'PFR' && (phiz:impliesOperation('ListPFRClaimOperation', 'PFRService'))}">
                                            <%@ include file="/WEB-INF/jsp-sbrf/payments/categories/PFR.jsp" %>
                                            <c:set var="groupServicesCount" value="1" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${empty groupServices and empty servicesList}">
                                                <div class="emptyText">
                                                    <tiles:insert definition="roundBorderLight" flush="false">
                                                        <tiles:put name="color" value="greenBold"/>
                                                        <tiles:put name="data">
                                                            Ќе найдено ни одной организации, соответствующей параметрам поиска. ѕожалуйста, задайте другие параметры.
                                                        </tiles:put>
                                                    </tiles:insert>
                                                </div>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>

                                <c:set var="group" value="0"/>
                                <c:set var="links" value=""/>
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
                                                            <phiz:param name="categoryId" value="${categoryId}"/>
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
                                                <tiles:put  name="url"><c:url value="/private/payments/servicesPayments.do">
                                                    <phiz:param name="categoryId" value="${categoryId}"/>
                                                    <phiz:param name="serviceId" value="${group}"/>
                                                    <phiz:param name="fromResource" value="${fromResource}"/>
                                                </c:url></tiles:put>
                                                <tiles:put name="serviceId" value="RurPayJurSB"/>
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
                                                ${links}<phiz:link url="${serviceAction}" serviceId="RurPayJurSB">
                                                    <phiz:param name="categoryId" value="${categoryId}"/>
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
                                                        <phiz:param name="categoryId" value="${categoryId}"/>
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
                                            <tiles:put name="url"><c:url value="/private/payments/servicesPayments.do">
                                                <phiz:param name="categoryId" value="${categoryId}"/>
                                                <phiz:param name="serviceId" value="${groupId}"/>
                                                <phiz:param name="fromResource" value="${fromResource}"/>
                                            </c:url></tiles:put>
                                            <tiles:put name="serviceId" value="RurPayJurSB"/>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>

                                <c:set var="group" value="0"/>
                                <c:set var="links" value=""/>
                                <c:set var="servicesCount" value="0" />
                                <c:if test="${not empty servicesList}">
                                    <c:forEach var="el" items="${servicesList}">
                                        <c:set var="groupId" value="${el[0]}"/>

                                        <c:set var="providerId" value="${el[5]}"/>
                                        <c:set var="providerName" value="${el[6]}"/>

                                        <c:if test="${group == 0}">
                                            <c:set var="servicesCount" value="${servicesCount+1}" />
                                            <c:set var="group" value="${el[0]}"/>
                                            <c:set var="groupName" value="${el[1]}"/>
                                            <c:set var="groupDesc" value="${el[2]}"/>
                                            <c:set var="imageId" value="${el[3]}"/>
                                            <c:set var="defaultImage" value="${el[4]}"/>
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
                                                            <phiz:param name="categoryId" value="${categoryId}"/>
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
                                                <tiles:put name="url"><c:url value="/private/payments/servicesPayments.do">
                                                    <phiz:param name="categoryId" value="${categoryId}"/>
                                                    <phiz:param name="serviceId" value="${group}"/>
                                                    <phiz:param name="fromResource" value="${fromResource}"/>
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
                                                ${links}<phiz:link url="${providerAction}" serviceId="RurPayJurSB">
                                                    <phiz:param name="recipient" value="${providerId}"/>
                                                    ${providerName}
                                                </phiz:link>
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
                                            <tiles:put name="url"><c:url value="/private/payments/servicesPayments.do">
                                                <phiz:param name="categoryId" value="${categoryId}"/>
                                                <phiz:param name="serviceId" value="${groupId}"/>
                                                <phiz:param name="fromResource" value="${fromResource}"/>
                                            </c:url></tiles:put>
                                            <tiles:put name="serviceId" value="RurPayJurSB"/>
                                        </tiles:insert>
                                    </c:if>
                                </c:if>

                            </div>

                            <c:if test="${currentPage > 0 || groupServicesCount + servicesCount > itemsPerPage}">
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
                            <tiles:put name="bundle" value="paymentsBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="/private/payments"/>
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