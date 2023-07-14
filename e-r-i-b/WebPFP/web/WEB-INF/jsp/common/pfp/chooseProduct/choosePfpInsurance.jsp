<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/editPortfolio/chooseProduct/insurance" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="personPortfolioType" value="${pfptags:getPersonPortfolioTypeById(form.portfolioId)}"/>
    <c:set var="insuranceUniversalProductId" value=""/>
    <c:set var="insuranceUniversalProductImg" value=""/>
    <c:set var="pensionUniversalProductId" value=""/>
    <c:set var="pensionUniversalProductImg" value=""/>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message bundle="pfpBundle" key="index.breadcrumbsLink"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Финансовое планирование</tiles:put>
                <tiles:put name="action" value="/private/pfp/editPortfolioList.do?id=${form.id}"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">«${personPortfolioType}»</tiles:put>
                <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${form.id}&portfolioId=${form.portfolioId}"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.productTitle.${form.dictionaryProductType}" bundle="pfpBundle"/></tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data">
            <div class="pfpBlocks">
                <tiles:insert definition="formHeader" flush="false">
                    <tiles:put name="image" value="${globalPath}/pfp/icon_${form.dictionaryProductType}.png"/>
                    <tiles:put name="width" value="64px"/>
                    <tiles:put name="height" value="64px"/>
                    <tiles:put name="description">
                        <bean:message key="label.chooseProduct.${form.dictionaryProductType}" bundle="pfpBundle"/>
                        <br/>
                        <span class="notation">
                            <bean:message key="label.chooseProduct.${form.dictionaryProductType}.notation" bundle="pfpBundle" failIfNone="false"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.financePlan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>

                <c:url var="editPortfolioUrl" value="/editPortfolio.do">
                    <c:param name="id" value="${form.id}"/>
                    <c:param name="portfolioId" value="${form.portfolioId}"/>
                </c:url>

                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back.toProfile"/>
                    <tiles:put name="commandHelpKey" value="button.back.toProfile.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="onclick" value="goBackToPortfolio();"/>
                </tiles:insert>
                <div class="clear"></div>

                <c:choose>
                    <c:when test="${phiz:size(form.insuranceProductList) > 0 || phiz:size(form.pensionProductList) > 0}">
                        <c:if test="${phiz:size(form.insuranceProductList) > 0}">
                            <script type="text/javascript">
                                var companies = new Array(); <%-- Список страховых компаний --%>
                                var types = new Array; <%-- Список типов страховых продуктов --%>

                                function init()
                                {
                                    <c:forEach var="product" items="${form.insuranceProductList}">
                                        <c:if test="${not product.universal}">
                                            <c:set var="companyImg" value=""/>
                                            <c:if test="${not empty product.insuranceCompany.imageId}">
                                                <c:set var="imageData" value="${phiz:getImageById(product.insuranceCompany.imageId)}"/>
                                                <c:set var="companyImg">${phiz:getAddressImage(imageData, pageContext)}</c:set>
                                            </c:if>
                                            var company = new Object();
                                            company.id = ${product.insuranceCompany.id};
                                            company.name = '${product.insuranceCompany.name}';
                                            company.img = '${companyImg}';
                                            pushIfNone(companies, company);

                                            var type = new Object();
                                            type.id = ${product.type.id};
                                            type.name = '${product.type.name}';
                                            type.parentId = '${product.type.parent.id}';
                                            type.parentName = '${product.type.parent.name}';
                                            pushType(type);
                                        </c:if>
                                    </c:forEach>
                                }

                                function pushIfNone(arr, obj)
                                {
                                    if (!findObj(arr, obj.id))
                                        arr.push(obj);
                                }

                                function findObj(arr, objId)
                                {
                                    for(var i=0; i< arr.length; i++)
                                        if (arr[i].id == objId)
                                            return arr[i];
                                    return false;
                                }

                                function pushType(obj)
                                {
                                    <%-- сам является родительским типом --%>
                                    if (obj.parentId == "")
                                    {
                                        var parent = findObj(types, obj.id);
                                        if (!parent) <%-- еще нет в списке => надо добавить --%>
                                        {
                                            parent = new Object();
                                            parent.id = obj.id;
                                            parent.name = obj.name;
                                            parent.children = new Array();
                                            types.push(parent);
                                        }
                                    }
                                    <%-- является дочерним типом --%>
                                    else
                                    {
                                        var parent = findObj(types, obj.parentId);
                                        if (parent) <%-- есть списке родительский => надо добавить к нему дочерний --%>
                                        {
                                            if(!findObj(parent.children, obj.id))
                                                parent.children.push(obj);
                                        }
                                        else <%-- еще нет в списке родительского => надо добавить и родительский и дочерний --%>
                                        {
                                            parent = new Object();
                                            parent.id = obj.parentId;
                                            parent.name = obj.parentName;
                                            parent.children = new Array();
                                            parent.children.push(obj);
                                            types.push(parent);
                                        }
                                    }
                                }

                                <%--
                                 * Рисует заголовок таблицы
                                 * @param blockId - идентификатор блока, в котором находится таблица
                                 * @param title - заголовок первого столбца
                                 --%>
                                function drawTableHeader(blockId, title)
                                {
                                    var table = $('#' + blockId + ' table');
                                    var trParent = $('<tr />').appendTo(table); <%-- заголовки родительских типов --%>
                                    var trChildren = $('<tr />').appendTo(table); <%-- заголовки дочерних типов --%>

                                    <%-- Первый заголовок всегда title --%>
                                    $('<th />').appendTo(trParent).attr('rowSpan', '2').text(title);

                                    for (var i=0; i < types.length; i++)
                                    {
                                        var th = $('<th />').appendTo(trParent).text(types[i].name);
                                        if (i < types.length - 1) th.addClass('rightGrayBolder');

                                        var children = types[i].children;
                                        if (children.length == 0)
                                        {
                                            th.attr('rowSpan', '2');
                                        }
                                        else
                                        {
                                            th.attr('colSpan', children.length);
                                            for (var j=0; j < children.length; j++)
                                            {
                                                var thChild = $('<th />').appendTo(trChildren).text(children[j].name);
                                                if (i < types.length - 1 && j == children.length - 1)
                                                    thChild.addClass('rightGrayBolder');
                                            }
                                        }
                                    }
                                }

                                <%--
                                 * Отрисовывает строки таблицы
                                 * @param blockId
                                 --%>
                                function drawTableRows(blockId)
                                {
                                    var table = $('#' + blockId + ' table');
                                    for(var c = 0; c < companies.length; c++)
                                    {
                                        var tr = $('<tr />').appendTo(table);
                                        var companyTd = $('<td />').appendTo(tr).addClass('pfpInsuranceCompany');
                                        if (companies[c].img != '')
                                        {
                                            var img = $('<img />').attr('src', companies[c].img); <%-- если есть, то добавляем иконку --%>
                                            companyTd.append(img).append('<br/>');
                                        }
                                        var name = $('<span />').text(companies[c].name); <%-- название --%>
                                        companyTd.append(name);

                                        for(var t=0; t < types.length; t++)
                                        {
                                            var children = types[t].children;
                                            if (children.length == 0)
                                            {
                                                var td = $('<td />', {id: 'company'+companies[c].id+'_type'+types[t].id, className: 'pfpProductTd'}).appendTo(tr);
                                                if (t < types.length - 1)
                                                    td.addClass('rightGrayBolder');
                                            }
                                            else
                                            {
                                                for(var ch=0; ch < children.length; ch++)
                                                {
                                                    var td = $('<td />', {id: 'company'+companies[c].id+'_type'+children[ch].id, className: 'pfpProductTd'}).appendTo(tr);
                                                    if (t < types.length - 1 && ch == children.length - 1)
                                                        td.addClass('rightGrayBolder');
                                                }
                                            }
                                        }
                                    }
                                }

                                function drawTableProducts(blockId)
                                {
                                    var products = $('#' + blockId + ' .productInTable');
                                    for(var i = 0; i < products.length; i++)
                                    {
                                        var cellId = $(products[i]).attr('cellId');
                                        $('#'+cellId).append(products[i]);
                                        $(products[i]).show();
                                    }
                                }

                                $(document).ready(function(){
                                    init();
                                    drawTableHeader("insuranceTable", "Страховая компания");
                                    drawTableRows("insuranceTable");
                                    drawTableProducts("insuranceTable");
                                });
                            </script>

                            <div class="choosePfpProductHeader">
                                <div class="choosePfpProductTitle">
                                    <bean:message key="label.chooseProduct.INSURANCE.chooseTitle" bundle="pfpBundle"/>
                                </div>
                                <div class="clear"></div>
                            </div>

                            <div id="insuranceTable" class="">
                                <table cellpadding="0" cellspacing="0"></table>

                                <logic:iterate id="product" name="form" property="insuranceProductList" indexId="i">
                                    <c:choose>
                                        <c:when test="${product.universal}">
                                            <c:set var="insuranceUniversalProductId" value="${product.id}"/>
                                            <c:set var="insuranceUniversalProductImg" value="${image}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="title"><c:out value="${product.name}"/></c:set>

                                            <c:set var="url">
                                                <c:url value="/addProduct.do">
                                                    <c:param name="dictionaryProductType" value="INSURANCE"/>
                                                    <c:param name="productId" value="${product.id}"/>
                                                    <c:param name="id" value="${form.id}"/>
                                                    <c:param name="portfolioId" value="${form.portfolioId}"/>
                                                    <c:param name="portfolioType" value="${form.portfolioType}"/>
                                                </c:url>
                                            </c:set>
                                            <c:set var="url" value="${fn:trim(url)}"/>
                                            <div cellId="company${product.insuranceCompany.id}_type${product.type.id}" class="productInTable" onclick="window.location = '${url}';" style="display:none;">
                                                <c:choose>
                                                    <c:when test="${not empty product.imageId}">
                                                        <c:set var="imageData" value="${phiz:getImageById(product.imageId)}"/>
                                                        <c:set var="image">${phiz:getAddressImage(imageData, pageContext)}</c:set>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="image">${globalPath}/pfp/icon_${form.dictionaryProductType}.png</c:set>
                                                    </c:otherwise>
                                                </c:choose>
                                                <tiles:insert definition="roundBorder" flush="false">
                                                    <tiles:put name="color" value="lightGrayWithoutTop"/>
                                                    <tiles:put name="data">
                                                        <div class="productInTableImg">
                                                            <img src="${image}" border="0" alt="${title}"/>
                                                        </div>
                                                        <div class="productInTableTitle">${title}</div>
                                                    </tiles:put>
                                                </tiles:insert>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </logic:iterate>
                            </div>
                            <c:if test="${not empty insuranceUniversalProductId}">
                                <c:set var="productParameters" value="${pfptags:getProductTypeParameters('INSURANCE')}"/>
                                <c:set var="chooseLater" value="${productParameters.link}"/>
                                <c:set var="chooseLaterUrl">
                                    <c:url value="/addProduct.do">
                                        <c:param name="dictionaryProductType" value="INSURANCE"/>
                                        <c:param name="productId" value="${insuranceUniversalProductId}"/>
                                        <c:param name="id" value="${form.id}"/>
                                        <c:param name="portfolioId" value="${form.portfolioId}"/>
                                        <c:param name="portfolioType" value="${form.portfolioType}"/>
                                    </c:url>
                                </c:set>

                                <div class="chooseProductLater">
                                    <div class="chooseProductLaterImg">
                                        <img src="${insuranceUniversalProductImg}" alt="">
                                    </div>
                                    <div class="chooseProductLaterData">
                                        <div class="chooseProductLaterTitle">
                                            <c:set var="chooseLaterUrl" value="${fn:trim(chooseLaterUrl)}"/>
                                            <span onclick="window.location = '${chooseLaterUrl}';"><c:out value="${chooseLater.name}"/></span>
                                        </div>
                                        <div class="chooseProductLaterDescr"><c:out value="${chooseLater.hint}"/></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </c:if>
                        </c:if>

                        <c:if test="${phiz:size(form.pensionProductList) > 0}">
                            <div class="choosePfpProductHeader">
                                <div class="choosePfpProductTitle">
                                    <bean:message key="label.chooseProduct.PENSION.chooseTitle" bundle="pfpBundle"/>
                                </div>

                                <div class="clear"></div>
                            </div>
                            <div id="pensionProductTable">
                                <table>
                                    <tr>
                                        <th>Пенсионный фонд</th>
                                        <th>Продукт</th>
                                    </tr>
                                    <c:set var="prevFundId" value=""/>
                                    <c:forEach var="pensionProduct" items="${form.pensionProductList}">
                                        <c:choose>
                                            <c:when test="${pensionProduct.universal}">
                                                <c:set var="pensionUniversalProductId" value="${pensionProduct.id}"/>
                                                <c:if test="${not empty pensionProduct.imageId}">
                                                    <c:set var="imageData" value="${phiz:getImageById(pensionProduct.imageId)}"/>
                                                    <c:set var="pensionUniversalProductImg" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                </c:if>
                                                <c:if test="${empty pensionUniversalProductImg}">
                                                    <c:set var="pensionUniversalProductImg" value="${globalPath}/pfp/icon_PENSION.png"/>
                                                </c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="url">
                                                    <c:url value="/addProduct.do">
                                                        <c:param name="dictionaryProductType" value="PENSION"/>
                                                        <c:param name="productId" value="${pensionProduct.id}"/>
                                                        <c:param name="id" value="${form.id}"/>
                                                        <c:param name="portfolioId" value="${form.portfolioId}"/>
                                                        <c:param name="portfolioType" value="${form.portfolioType}"/>
                                                    </c:url>
                                                </c:set>
                                                <c:if test="${pensionProduct.pensionFund.id != prevFundId}">
                                                    <tr>
                                                        <td>
                                                            <c:set var="imageData" value="${phiz:getImageById(pensionProduct.pensionFund.imageId)}"/>
                                                            <img src="${phiz:getAddressImage(imageData, pageContext)}" border="0"/>
                                                            <br/>
                                                            <span>${pensionProduct.pensionFund.name}</span>
                                                        </td>
                                                        <td>
                                                            <c:set var="url" value="${fn:trim(url)}"/>
                                                            <div class="productInTable" onclick="window.location = '${url}';">
                                                                <tiles:insert definition="roundBorder" flush="false">
                                                                    <tiles:put name="color" value="lightGrayWithoutTop"/>
                                                                    <tiles:put name="style" value="width:120px"/>
                                                                    <tiles:put name="data">
                                                                        <div class="productInTableImg">
                                                                            <c:if test="${not empty pensionProduct.imageId}">
                                                                                <c:set var="imageData" value="${phiz:getImageById(pensionProduct.imageId)}"/>
                                                                                <c:set var="productIcon" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                                                            </c:if>
                                                                            <c:if test="${empty productIcon}">
                                                                                <c:set var="productIcon" value="${globalPath}/pfp/icon_PENSION.png"/>
                                                                            </c:if>
                                                                            <img src="${productIcon}" border="0" alt="${pensionProduct.name}"/>
                                                                        </div>
                                                                        <div class="productInTableTitle">${pensionProduct.name}</div>
                                                                    </tiles:put>
                                                                </tiles:insert>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </table>
                            </div>
                            <c:if test="${not empty pensionUniversalProductId}">
                                <c:set var="productParameters" value="${pfptags:getProductTypeParameters('PENSION')}"/>
                                <c:set var="chooseLater" value="${productParameters.link}"/>
                                <c:set var="chooseLaterUrl">
                                    <c:url value="/addProduct.do">
                                        <c:param name="dictionaryProductType" value="PENSION"/>
                                        <c:param name="productId" value="${pensionUniversalProductId}"/>
                                        <c:param name="id" value="${form.id}"/>
                                        <c:param name="portfolioId" value="${form.portfolioId}"/>
                                        <c:param name="portfolioType" value="${form.portfolioType}"/>
                                    </c:url>
                                </c:set>

                                <div class="chooseProductLater">
                                    <div class="chooseProductLaterImg">
                                        <img src="${pensionUniversalProductImg}" alt="">
                                    </div>
                                    <div class="chooseProductLaterData">
                                        <div class="chooseProductLaterTitle">
                                            <c:set var="chooseLaterUrl" value="${fn:trim(chooseLaterUrl)}"/>
                                            <span onclick="window.location = '${chooseLaterUrl}';"><c:out value="${chooseLater.name}"/></span>
                                        </div>
                                        <div class="chooseProductLaterDescr"><c:out value="${chooseLater.hint}"/></div>
                                    </div>
                                    <div class="clear"></div>
                                </div>
                            </c:if>
                        </c:if>
                    </c:when>
                    <c:otherwise><br>
                        <div class="emptyText">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="greenBold"/>
                                <tiles:put name="data">
                                    Не найдено ни одного продукта. Пожалуйста, выберите другой банковский продукт.
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:otherwise>
                </c:choose>

                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back.toProfile"/>
                    <tiles:put name="commandHelpKey" value="button.back.toProfile.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="onclick" value="goBackToPortfolio();"/>
                </tiles:insert>
            </div>

            <script type="text/javascript">
                function goBackToPortfolio()
                {
                    loadNewAction('', '');
                    window.location = "${editPortfolioUrl}";
                }
            </script>

        </tiles:put>
    </tiles:insert>

</html:form>