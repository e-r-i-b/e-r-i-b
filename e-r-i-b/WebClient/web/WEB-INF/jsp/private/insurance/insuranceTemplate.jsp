<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<%-- Информация о программе лояльности --%>
<c:set var="imagePath" value="${globalUrl}/commonSkin/images/products"/>
<div class="insuranceTemplate <c:if test="${detailsPage}">detailTemplate</c:if>">
    <tiles:insert definition="productTemplate" flush="false">
        <tiles:put name="productViewBacklight" value="false"/>
        <c:choose>
            <c:when test="${insuranceLink.businessProcess == 'NPF'}">
                 <tiles:put name="img" value="${imagePath}/icon_npf.jpg"/>
                <c:set var="infoUrl" value="${phiz:calculateActionURL(pageContext,'/private/npf/view')}"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="img" value="${imagePath}/icon_insurance.jpg"/>
                <c:set var="infoUrl" value="${phiz:calculateActionURL(pageContext,'/private/insurance/view')}"/>
            </c:otherwise>
        </c:choose>
        <c:if test="${insuranceInfoLink == true}">
            <tiles:put name="src" value="${infoUrl}?id=${insuranceLink.id}"/>
        </c:if>

        <tiles:put name="comment">
            <nobr>&nbsp;<c:out value="${insuranceApp.status}"/></nobr>
        </tiles:put>

        <tiles:put name="productNumbers">
            <c:out value="${insuranceApp.SNILS}"/>
        </tiles:put>

        <tiles:put name="title"><c:out value="${insuranceLink.name}"/></tiles:put>
        <c:choose>
            <c:when test="${detailsPage}">
                <tiles:put name="titleClass" value="mainProductTitle mainProductDetailTitle"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="titleClass" value="mainProductTitle mainProductTitleLight"/>
            </c:otherwise>
        </c:choose>

        <tiles:put name="leftData">
            <span class="companyName"> <c:out value="${insuranceApp.company}"/></span>
            Срок окончания страховки: ${phiz:сalendarToString(insuranceApp.endDate)}
        </tiles:put>
    </tiles:insert>
</div>

