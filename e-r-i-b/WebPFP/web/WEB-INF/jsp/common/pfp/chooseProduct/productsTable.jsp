<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>
<c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="tableColumns" value="${productParameters.tableParameters.columns}"/>

<script type="text/javascript" src="${globalUrl}/scripts/productsGraphUtils.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        $('.pfpProductRow')
            .live('mouseover', function(){$(this).addClass("over");})
            .live('mouseout',  function(){$(this).removeClass("over");});
    });
</script>

<div class="productsAsList" <c:if test="${productParameters.useOnDiagram}">style="display: none;"</c:if>>
    <div class="choosePfpProductHeader">
        <div class="choosePfpProductTitle">
            <bean:message key="label.chooseProduct.${productType}.chooseTitle" bundle="pfpBundle"/>
        </div>

        <c:if test="${productParameters.useOnDiagram}">
            <div class="floatRight">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.showProductsAsGraph"/>
                    <tiles:put name="commandHelpKey" value="button.showProductsAsGraph.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="linkWithImg"/>
                    <tiles:put name="imageUrl" value="${globalUrl}/commonSkin/images/pfp/graphProducts.gif"/>
                    <tiles:put name="onclick" value="showProductsAsGraph();"/>
                </tiles:insert>
            </div>
         </c:if>
        <div class="clear"></div>
    </div>

    <div class="pfpProductsTable">
        <table cellpadding="0" cellspacing="0">
            <tr>
                <c:forEach items="${tableColumns}" var="column">
                    <th>${column.value}</th>
                </c:forEach>
            </tr>

            <logic:iterate id="product" name="form" property="${dataName}">
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
                        <c:set var="productColumns" value="${product.tableParameters.columns}"/>
                        <c:if test="${not empty productColumns}">
                            <c:set var="url">
                                <c:url value="/addProduct.do">
                                    <c:param name="dictionaryProductType" value="${product.productType}"/>
                                    <c:param name="productId" value="${product.id}"/>
                                    <c:param name="id" value="${form.id}"/>
                                    <c:param name="portfolioId" value="${form.portfolioId}"/>
                                    <c:param name="portfolioType" value="${form.portfolioType}"/>
                                </c:url>
                            </c:set>
                            <c:set var="url" value="${fn:trim(url)}"/>
                            <tr class="pfpProductRow" onclick="window.location = '${url}';">

                                <c:forEach items="${tableColumns}" var="column" varStatus="i">
                                    <td>
                                        <c:if test="${i.index == 0 && product.tableParameters.useIcon}">
                                            <img src="${image}" alt="">
                                        </c:if>

                                        ${productColumns[column]}
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </logic:iterate>
        </table>
    </div>
</div>