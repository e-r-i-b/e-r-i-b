<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/card/products/list" onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="cardsBundle"/>

    <tiles:insert definition="cardProductList">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="title.card.product.list" bundle="${bundle}"/>
        </tiles:put>
        <tiles:put name="submenu" type="string" value="CardProducts"/>
        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false" operation="EditCardProductOperation">
                <tiles:put name="commandTextKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="action" value="/card/products/edit.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.card.product.name"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="productName"/>
                <tiles:put name="size" value="50"/>
                <tiles:put name="maxlength" value="255"/>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.card.product.filter.online"/>
                <tiles:put name="bundle" value="${bundle}"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="data">
                    <html:select property="filter(online)" styleClass="select">
                        <html:option value="">
                            <bean:message key="option.card.product.filter.all" bundle="${bundle}"/>
                        </html:option>
                        <html:option value="true">
                            <bean:message key="option.card.product.filter.online" bundle="${bundle}"/>
                        </html:option>
                        <html:option value="false">
                            <bean:message key="option.card.product.filter.not.online" bundle="${bundle}"/>
                        </html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                var cardProductList =
                {
                    onlines : new Array(),

                    editProduct : function ()
                    {
                        checkIfOneItem("selectedIds");
                        if(!checkSelection("selectedIds", "Выберите продукт"))
                            return;
                        <c:set var='url'>${phiz:calculateActionURL(pageContext, '/card/products/edit.do')}</c:set>
                        window.location = "${url}" + "?id=" + getRadioValue(document.getElementsByName("selectedIds"));
                    },

                    validateDelete : function ()
                    {
                        checkIfOneItem("selectedIds");
                        if(!checkSelection("selectedIds", "Выберите продукт"))
                            return;
                        var ids = document.getElementsByName("selectedIds");
                        for (var i = 0; i < ids.length; i++)
                            if (ids[i].checked)
                                if (this.onlines[ids[i].value])
                                {
                                    alert("Для удаления карточного продукта снимите его с публикации и повторите операцию.");
                                    return false;
                                }
                        return true;
                    }
                };
            </script>
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="сardProductsList"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="EditCardProductOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="onclick" value="cardProductList.editProduct()"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="RemoveCardProductOperation">
                        <tiles:put name="commandKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            cardProductList.validateDelete()
                        </tiles:put>
                        <tiles:put name="confirmText" value="Удалить выбранный продукт?"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditCardProductOperation">
                        <tiles:put name="commandKey" value="button.publish"/>
                        <tiles:put name="commandHelpKey" value="button.publish"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите продукт');
                            }
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditCardProductOperation">
                        <tiles:put name="commandKey" value="button.unpublish"/>
                        <tiles:put name="commandHelpKey" value="button.unpublish"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="validationFunction">
                            function()
                            {
                                checkIfOneItem("selectedIds");
                                return checkSelection('selectedIds', 'Выберите продукт');
                            }
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="grid">
                    <div class="gridDiv listCardProduct">
                        <c:forEach var="product" items="${form.data}">
                            <div>
                                <script type="text/javascript">
                                    if (${product.online})
                                        cardProductList.onlines[${product.id}] = ${product.online};
                                </script>
                                <input class="productId" type="radio" name="selectedIds" value="${product.id}"/>
                                <span class="bold"><c:out value="${product.name}"/></span>
                                -
                                <c:choose>
                                    <c:when test="${product.online}">
                                        <bean:message key="card.product.client.enabled" bundle="${bundle}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <bean:message key="card.product.client.not.enabled" bundle="${bundle}"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                                <c:if test="${!empty product.kindOfProducts}">
                                    <span class="bold">
                                        <bean:message key="label.card.product.currency" bundle="${bundle}"/>
                                    </span>
                                    -
                                    <c:set var="flag" value="false"/>
                                    <c:forEach var="kindOfProduct" items="${product.kindOfProducts}">
                                        <c:if test="${flag}">
                                            ,&nbsp;
                                        </c:if>
                                        ${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)}
                                        <c:set var="flag" value="true"/>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <div>
                                <span class="bold">
                                    <bean:message key="label.card.product.type" bundle="${bundle}"/>
                                </span>
                                -
                                <bean:message key="card.product.type.title" bundle="${bundle}"/>
                            </div>
                            <div>
                                <c:if test="${!empty product.kindOfProducts}">
                                    <table class="depositProductInfo">
                                        <tr>
                                            <th width="150px" align="center">
                                                <bean:message key="table.header.card.product.id" bundle="${bundle}"/>
                                            </th>
                                            <th style="text-align:left;">
                                                <bean:message key="table.header.card.product.sub.id" bundle="${bundle}"/>
                                            </th>
                                        </tr>
                                        <c:set var="tempId" value="0"/>
                                        <c:set var="table" value=""/>
                                        <c:forEach var="kindOfProduct" items="${product.kindOfProducts}">
                                            <c:if test="${tempId != kindOfProduct.productId && tempId != 0}">
                                                <tr>
                                                    <td align="center">
                                                        <c:out value="${tempId}"/>
                                                    </td>
                                                    <td>
                                                        <table class="productKindInfo">
                                                            ${table}
                                                        </table>
                                                    </td>
                                                </tr>
                                                <c:set var="table" value=""/>
                                            </c:if>
                                            <c:set var="table">
                                                ${table}
                                                <tr>
                                                    <td>
                                                        <c:out value="${kindOfProduct.productSubId}"/>
                                                        -
                                                        <c:out value="${kindOfProduct.name}"/>
                                                        <c:out value="(${phiz:getISOCodeExceptRubles(kindOfProduct.currency.code)})"/>
                                                    </td>
                                                </tr>
                                            </c:set>
                                            <c:set var="tempId" value="${kindOfProduct.productId}"/>
                                        </c:forEach>
                                        <tr>
                                            <td align="center">
                                                <c:out value="${tempId}"/>
                                            </td>
                                            <td>
                                                <table class="productKindInfo">
                                                    ${table}
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </c:if>
                            </div>
                            <br/>
                        </c:forEach>
                    </div>
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty form.data}"/>
                <tiles:put name="emptyMessage">
                    <bean:message key="label.card.product.empty.message" bundle="${bundle}"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>