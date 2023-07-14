<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(form.service)}"/>
<c:set var ="isLongOffer" value="${form.document.longOffer || form.createLongOffer}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="isITunes" value="${phiz:isITunesDocument(form.document)}"/>

<%-- ******************************* �������� � ��������� **************** --%>
<c:set var="imageId" value="${serviceProvider.imageId}"/>

<%-- ************************** �������� ������ ************************** --%>

<c:choose>
    <c:when test="${isLongOffer}">
        <c:set var="imagePath" value="${imagePath}/iconPmntList_LongOffer.png"/>
    </c:when>
    <c:when test="${not empty imageId}">
        <c:set var="imageData" value="${phiz:getImageById(imageId)}"/>
        <c:set var="imagePath" value="${phiz:getAddressImage(imageData, pageContext)}"/>
    </c:when>
    <c:otherwise>
        <c:set var="imagePath" value="${imagePath}/IQWave-other.jpg"/>
     </c:otherwise>
</c:choose>
<tiles:insert definition="formHeader" flush="false">
    <tiles:put name="image" value="${imagePath}"/>
    <tiles:put name="description">
        <c:choose>
            <c:when test="${isLongOffer}">
                ��� ���������� ����������� ��������� ���� ����� � ������� �� ������ ������������.<br/>
                <span class="text-gray">����, ������������ ��� ����������, ��������</span>
                <span class="text-red">*</span>
                <span class="text-gray">.</span>
            </c:when>
            <c:when test="${not empty paymentService.description}">
                ${paymentService.description}
            </c:when>
            <c:otherwise>
                <c:out value="${isITunes ? serviceProvider.description : ''}"/>
                ��������� ���� ����� � ������� �� ������ &laquo;����������&raquo;.
                <br/><br/>
                <span class="text-gray">����, ������������ ��� ����������, ��������</span>
                <span class="text-red">*</span>
                <span class="text-gray">.</span>
            </c:otherwise>
        </c:choose>
    </tiles:put>
</tiles:insert>
<c:set var="external" value="${form.externalPayment}"/>
<c:if test="${not isLongOffer && not external}">
    <tiles:insert definition="addToFavouriteButton" flush="false">
        <tiles:put name="formName">������ ����� <c:out value='${serviceProvider.name}'/></tiles:put>
        <tiles:put name="typeFormat">SERVICES_PAYMENT_LINK</tiles:put>
        <tiles:put name="dopParam">recipient=${form.recipient}&template=${form.template}</tiles:put>
    </tiles:insert>
</c:if>

<div class="clear"></div>
