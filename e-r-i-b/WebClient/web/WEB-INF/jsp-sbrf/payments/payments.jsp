<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tiles:importAttribute/>
<c:set var="person" value="${phiz:getPersonInfo()}"/>
<c:set var="fromResource" value="${param['fromResource']}"/>
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isSearch" value="${frm.fields.isSearch}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<c:url var="serviceAction" value="${serviceURL}">
    <c:param name="fromResource" value="${fromResource}"/>
    <c:if test="${not empty form.accountingEntityId}">
        <c:param name="accountingEntityId" value="${form.accountingEntityId}"/>
    </c:if>
</c:url>
<c:if test="${!isSearch}">
<script type="text/javascript">
     var rightCol = 0;
     var leftCol = 0;
</script>
<div class="paymentsContainer">
    <div class="paymentCategories">
        <c:choose>
            <c:when test="${not empty frm.categories}">
                <div class="categoryPayments" id="categories">
                    <div class="doubleColumn" >
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
                                        <c:url value="${serviceURL}">
                                            <c:param name="serviceId" value="${curCategory}"/>
                                            <c:param name="fromResource" value="${fromResource}"/>
                                            <c:if test="${not empty form.accountingEntityId}">
                                                <c:param name="accountingEntityId" value="${form.accountingEntityId}"/>
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
                                                </span></phiz:link></li>
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
                                <c:url value="${serviceURL}">
                                    <c:param name="serviceId" value="${curCategory}"/>
                                    <c:param name="fromResource" value="${fromResource}"/>
                                    <c:if test="${not empty form.accountingEntityId}">
                                        <c:param name="accountingEntityId" value="${form.accountingEntityId}"/>
                                    </c:if>
                                </c:url>
                            </tiles:put>
                            <tiles:put name="serviceId" value="RurPayJurSB"/>
                        </tiles:insert>
                        <div class="payment" id="popularPaymentsBlock1">
                            <jsp:include page="/WEB-INF/jsp-sbrf/payments/jurPaymentBlock.jsp"/>
                            <c:set var="categoryTemplateCounter" value="0" scope="request"/>
                            <script type="text/javascript">
                                    $('#popularPaymentsBlock1').css('left', 368);
                                    $('#popularPaymentsBlock1').css('top', rightCol + 14);
                                    rightCol = rightCol +  $('#popularPaymentsBlock1').height();
                            </script>
                        </div>

                        <div class="clear"></div>
                    </div>
                </div>
                <script type="text/javascript">
                    $('.doubleColumn').css('height', leftCol>rightCol ? leftCol : rightCol);
                </script>
            </c:when>
            <c:otherwise>
                <div class="emptyText">
                    <tiles:insert definition="roundBorderLight" flush="false">
                        <tiles:put name="color" value="greenBold"/>
                        <tiles:put name="data">
                            Не найдено ни одной категории услуг. Пожалуйста, воспользуйтесь поиском поставщиков или перейдите по ссылке <span class="underline"><phiz:link  action="/private/payments/jurPayment/edit" serviceId="JurPayment">&laquo;Оплата по реквизитам или квитанции&raquo;</phiz:link></span>.
                        </tiles:put>
                    </tiles:insert>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</c:if>
