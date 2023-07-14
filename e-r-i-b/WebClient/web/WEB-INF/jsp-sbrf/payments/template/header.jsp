<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="serviceProvider" value="${phiz:getServiceProvider(form.recipient)}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<%-- ******************************* Картинка в заголовке **************** --%>
<c:set var="imageId" value="${serviceProvider.imageId}"/>

<%-- ************************** Описание услуги ************************** --%>

<c:choose>
    <c:when test="${not empty imageId}">
        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
        <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
    </c:when>
    <c:otherwise>
        <c:set var="imagePath" value="${imagePath}/icon_template.png"/>
     </c:otherwise>
</c:choose>
<tiles:insert definition="formHeader" flush="false">
    <tiles:put name="image" value="${imagePath}"/>
    <tiles:put name="description">
        Для создания шаблона заполните необходимые поля и нажмите на кнопку &laquo;Продолжить&raquo;.
        <br/>
        <span class="text-gray">Поля, обязательные для заполнения, отмечены</span>
        <span class="text-red">*</span>
        <span class="text-gray">.</span>
    </tiles:put>
</tiles:insert>
<tiles:insert definition="addToFavouriteButton" flush="false">
    <tiles:put name="formName">Шаблон платежа <c:out value='${serviceProvider.name}'/></tiles:put>
    <tiles:put name="typeFormat">CREATE_TEMPLATE_LINK</tiles:put>
    <tiles:put name="dopParam">recipient=${form.recipient}</tiles:put>
</tiles:insert>

<div class="clear"></div>
