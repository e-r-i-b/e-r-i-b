<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="graphParameters" value="${productParameters.diagramParameters}"/>
<c:set var="graphId" value="productsGraphAxis"/>
<c:set var="xAxis" value="${graphParameters.axisX}"/>
<c:set var="yAxis" value="${graphParameters.axisY}"/>
<c:set var="showZero" value="${graphParameters.useZero}"/>

<script type="text/javascript" src="${globalUrl}/scripts/productsGraphUtils.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        <c:if test="${showZero}">
            productsGraphUtils.drawXaxisValue('${graphId}', 0, 0, '0');
        </c:if>
        <c:forEach items="${xAxis.steps}" var="step">
            productsGraphUtils.drawXaxisValue('${graphId}', ${step.from}, ${step.to}, '<c:out value="${step.name}"/>');
        </c:forEach>
    });
</script>

<div class="productsAsGraph">
    <div class="choosePfpProductHeader">
        <div class="choosePfpProductTitle">
            <bean:message key="label.chooseProduct.${productType}.chooseTitle" bundle="pfpBundle"/>
        </div>

        <c:if test="${productParameters.useOnTable}">
            <div class="floatRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.showProductsAsTable"/>
                    <tiles:put name="commandHelpKey" value="button.showProductsAsTable.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="linkWithImg"/>
                    <tiles:put name="imageUrl" value="${globalUrl}/commonSkin/images/pfp/listProducts.gif"/>
                    <tiles:put name="onclick" value="showProductsAsList();"/>
                </tiles:insert>
            </div>
        </c:if>
        <div class="clear"></div>
    </div>

    <c:set var="graphData">
        <logic:iterate id="product" name="form" property="${dataName}" indexId="i">
            <c:choose>
                <c:when test="${not empty product.imageId}">
                    <c:set var="imageData" value="${phiz:getImageById(product.imageId)}"/>
                    <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="image" value="${globalPath}/pfp/icon_${product.productType}.png"/>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${product.universal}">
                    <c:set var="universalProductId" value="${product.id}"/>
                    <c:set var="universalProductImg" value="${image}"/>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="productOnGraphTemplate" flush="false">
                        <tiles:put name="graphId" value="${graphId}"/>
                        <tiles:put name="productId" value="${product.productType}${product.id}"/>
                        <tiles:put name="title"><c:out value="${product.name}"/></tiles:put>
                        <tiles:put name="image" value="${image}"/>
                        <tiles:put name="url">
                            <c:url value="/addProduct.do">
                                <c:param name="dictionaryProductType" value="${product.productType}"/>
                                <c:param name="productId" value="${product.id}"/>
                                <c:param name="id" value="${form.id}"/>
                                <c:param name="portfolioId" value="${form.portfolioId}"/>
                                <c:param name="portfolioType" value="${form.portfolioType}"/>
                            </c:url>
                        </tiles:put>
                        <tiles:put name="x" value="${product.axisX}"/>
                        <tiles:put name="y" value="${product.axisY}"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </logic:iterate>
    </c:set>

    <div class="graphAxisYTitle">
        <c:if test="${not empty yAxis}">
            <c:out value="${yAxis.name}"/>
        </c:if>
    </div>

    <div id="${graphId}" class="productsGraphAxis">
        <div class="graphArrowUp"></div>
        <div class="graphArrowRight"></div>
        ${graphData}
    </div>
    
    <div class="productsGraphAxisValues"></div>
    <div class="graphAxisXTitle">
        <c:if test="${not empty xAxis}">
            <c:out value="${xAxis.name}"/>
        </c:if>
    </div>
    <div class="clear"></div>
</div>