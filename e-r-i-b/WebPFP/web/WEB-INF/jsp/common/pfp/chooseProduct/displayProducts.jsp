<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="productParameters" value="${pfptags:getProductTypeParameters(productType)}"/>
<c:set var="universalProductId" value=""/>
<c:set var="universalProductImg" value=""/>

<script type="text/javascript">
    function showProductsAsGraph()
    {
        $('.productsAsGraph').show();
        $('.productsAsList').hide();
    }

    function showProductsAsList()
    {
        $('.productsAsGraph').hide();
        $('.productsAsList').show();
    }
</script>

<c:if test="${productParameters.useOnDiagram}">
    <%@ include file="/WEB-INF/jsp/common/pfp/chooseProduct/productsDiagram.jsp"%>
</c:if>

<c:if test="${productParameters.useOnTable}">
    <%@ include file="/WEB-INF/jsp/common/pfp/chooseProduct/productsTable.jsp"%>
</c:if>

<c:if test="${not empty universalProductId}">
    <c:set var="chooseLater" value="${productParameters.link}"/>
    <c:set var="chooseLaterUrl">
        <c:url value="/addProduct.do">
            <c:param name="dictionaryProductType" value="${productType}"/>
            <c:param name="productId" value="${universalProductId}"/>
            <c:param name="id" value="${form.id}"/>
            <c:param name="portfolioId" value="${form.portfolioId}"/>
            <c:param name="portfolioType" value="${form.portfolioType}"/>
        </c:url>
    </c:set>
    <c:set var="chooseLaterUrl" value="${fn:trim(chooseLaterUrl)}"/>
    <div class="chooseProductLater">
        <div class="chooseProductLaterImg">
            <img src="${universalProductImg}" alt="">
        </div>
        <div class="chooseProductLaterData">
            <div class="chooseProductLaterTitle">
                <span onclick="window.location = '${chooseLaterUrl}';"><c:out value="${chooseLater.name}"/></span>
            </div>
            <div class="chooseProductLaterDescr"><c:out value="${chooseLater.hint}"/></div>
        </div>
        <div class="clear"></div>
    </div>
</c:if>