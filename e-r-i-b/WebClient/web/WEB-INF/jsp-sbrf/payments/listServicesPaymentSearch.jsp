<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:importAttribute/>

<%--
Использующиеся внешние переменные
pageType - тип страницы, с которой вызывается поиск
--%>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsAndOperation.js"></script>

<c:set var="searchFieldDefaultText" value="введите название, категорию услуги, ИНН или расчетный счет"/>
<script type="text/javascript">

    // Функция устанавливающая параметры блокируюшего дива
    function setTintedRegionsDiv(show)
    {
        var regionsDiv = document.getElementById("searchRegionsDivWin");
        if(show == null) return ;
        TintedNet.setTinted(regionsDiv, show);
    }

    function afterConfirm()
    {
        var searchInput = document.getElementById("searchServices");
        searchInput.refresh = true;
        findCommandButton('button.search').click();
    }

    function clearRegion()
    {
        var clearInput = document.getElementById("regionClear");
        clearInput.value = 1;
        var searchInput = document.getElementById("searchServices");
        searchInput.refresh = true;
        findCommandButton('button.search').click();
    }

    <%-- Валидатор кнопки Искать --%>
    function searchBtClick()
    {
    <c:if test="${not isSearch}"> <%-- только если небыло поиска --%>
        var searchInput = document.getElementById("searchServices");
        <%-- Данные манипуляции необходимы, так как при выборе региона происходит клик по кнопке для сохранения поискового результата --%>
        if(searchInput.refresh != undefined && searchInput.refresh) <%-- если всеже необходимо обновить страницу --%>
            return true;

        if (trim(customPlaceholder.getCurrentVal(searchInput)) == '' )
        {
            searchInput.blur();
            return false;
        }
    </c:if>

        return true;
    }
    <%-- Функция, очищающая результат поиска --%>
    function clearSearchResults()
    {
        $('#searchServices').val('').blur();
        findCommandButton('button.search').click();
    }

    <%-- Функция, пейджинга страницы поиска --%>
    function changeSearchPage(page)
    {
        $('#searchPage').val(page);
        findCommandButton('button.search').click();
    }
    <%-- Функция пейджинга страницы --%>
    function changePage(page)
    {
        $('#currentPage').val(page);
        $('#searchServices').val('');
        new CommandButton('button.search').click();
    }

    function closeRegionsDivWin()
    {
        win.close('confirmSaveRegion');
        setTintedRegionsDiv(false);
    }

    doOnLoad(function()
    {
        $('#confirmSaveRegionWin .closeImg').bind('click', closeRegionsDivWin);
    });
</script>


<div class="paymentFilter">
    <div id="filter">
        <div class="filter">
            <div class="categoriesTitle">Поиск</div>
            <c:if test="${empty showSearchRegion or showSearchRegion != 'false'}">
                <div class="selectRegion">
                    <div id="reigonSearchName" class="regionSelect" onclick="win.open('searchRegionsDiv'); return false">
                        <input type="hidden" name="field(regionId)" value="${frm.fields.regionId}"/>
                        <input type="hidden" name="field(regionName)" value="${frm.fields.regionName}"/>
                        <span id="reigonSearchNameSpan" title="${frm.fields.regionName}">
                            <c:choose>
                                <%--  Текущий регион --%>
                                <c:when test="${empty frm.fields.regionName}">
                                    Все регионы
                                </c:when>
                                <c:otherwise>
                                    ${frm.fields.regionName}
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </div>
            </c:if>
            <div class="clear"></div>
            <div class="paymentSearch">
                <div class="searchForm" onkeydown="onEnterKey(event);">
                    <div class="rightRound">
                        <div class="cetnerSearch">
                            <html:text property="searchServices" value="${frm.searchServices}" styleClass="customPlaceholder search"
                                       styleId="searchServices" title="${searchFieldDefaultText}" maxlength="256"/>

                            <c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/list')}"/>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="searchRegionsDiv"/>
                                <tiles:put name="loadAjaxUrl" value="${regionUrl}?isOpening=true"/>
                                <tiles:put name="styleClass" value="regionsDiv"/>
                            </tiles:insert>

                            <c:set var="winId" value="confirmSaveRegion"/>
                            <tiles:insert definition="window" flush="false">
                                <tiles:put name="id" value="${winId}"/>
                                <tiles:put name="styleClass" value="confirmWidow"/>
                                <tiles:put name="data">
                                    <div class="confirmWindowTitle">
                                        <h2>
                                            Сохранение региона оплаты
                                        </h2>
                                    </div>

                                    <div id="message_${winId}" class="confirmWindowMessage">
                                         Если Вы хотите сохранить этот регион, чтобы постоянно оплачивать услуги, предоставляемые в данном регионе, нажмите на кнопку «Сохранить».
                                    </div>

                                    <div class="buttonsArea">
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                            <tiles:put name="commandHelpKey" value="button.cancel"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="onclick" value="afterConfirm();win.close('confirmSaveRegion');"/>
                                        </tiles:insert>
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.saveRegion"/>
                                            <tiles:put name="commandTextKey" value="button.saveRegion"/>
                                            <tiles:put name="commandHelpKey" value="button.saveRegion"/>
                                            <tiles:put name="bundle" value="paymentsBundle"/>
                                        </tiles:insert>
                                        <div class="clear"></div>
                                    </div>
                                </tiles:put>
                            </tiles:insert>

                            <div id="search-button">
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey" value="button.search"/>
                                    <tiles:put name="commandHelpKey" value="button.search"/>
                                    <tiles:put name="bundle" value="paymentsBundle"/>
                                    <tiles:put name="validationFunction" value="searchBtClick()"/>
                                    <tiles:put name="image" value=""/>
                                    <tiles:put name="isDefault" value="true"/>
                                </tiles:insert>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${pageType == 'autopayment'}">
                <div class="payments-legend">Обратите внимание! Поиск выполняется среди организаций, в адрес которых можно оформить автоплатеж.</div>
            </c:if>

        </div>
        <div class="clear"></div>
    </div>

    <c:if test="${isSearch}">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.goto.select.service"/>
            <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
            <tiles:put name="bundle" value="paymentsBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="onclick" value="goback();"/>
            <tiles:put name="image"       value="backIcon.png"/>
            <tiles:put name="imageHover"     value="backIconHover.png"/>
            <tiles:put name="imagePosition"  value="left"/>
        </tiles:insert>
        <div class="clear"></div>
        <div class="word-wrap">
            Результаты поиска для: <span id="searchWord" class="searchWord"><c:out value="${frm.searchServices}"/></span>
            <img class="clickable" src="${imagePath}/close.gif" onclick="clearSearchResults();" alt="закрыть"/>
        </div>
        <%-- функция обрезки слов поиска --%>
        <script type="text/javascript">
            if (window.opera) document.getElementById("searchWord").innerHTML = wordBreak(document.getElementById("searchWord").innerHTML, 40);
        </script>
    </c:if>

    <%--Для "живого" поиска поставщиков--%>
    <c:if test="${phiz:impliesOperation('AsynchSearchServiceProvidersOperation','AsyncSearchAccess')}">
        <script type="text/javascript">
            $(document).ready(function()
            {
                $("#searchServices").autocomplete(
                        "${phiz:calculateActionURL(pageContext, "/private/async/search/serviceProviders")}",
                        {
                            inputClass: "",
                            lineSeparator:'@', <%--разделитель строк--%>
                            cellSeparator:'|', <%--разделитель ячеек в строках--%>
                            minChars:3,
                            delay:400,
                            cacheLength: 1,
                            maxItemsToShow:10,
                            extraParams: "regionId=${frm.fields.regionId}"
                                    <c:if test="${frm.categoryId != null && frm.categoryId != ''}">
                                    + "&categoryId=${frm.categoryId}"
                                    </c:if>
                                    <c:if test="${not empty frm.serviceId}">
                                    + "&serviceId=${frm.serviceId}"
                                    </c:if>
                                    <c:if test="${not empty pageType}">
                                    + "&pageType=<c:out value="${pageType}"/>"
                                    </c:if>,

                            matchSubset:0,
                            greyStyle:false,
                            resultsClass: "serviceProviderSerchResult",
                            matchContains:1,
                            selectFirst:false,
                            formatItem:liFormat,
                            operaIgnore:true,
                            onItemSelect: function(li){
                                findCommandButton('button.search').click();
                            }
                        });
                $("#searchServices").attr('autocomplete', 'off');
            });
            <%--сделаем более удобными для восприятия результаты поиска--%>
            function liFormat(row, i, num)
            {
                var result = row[0]; <%--поставщик услуг--%>
                if (!isEmpty(row[1]))
                    result += " <span class=\"bold\">ИНН:</span> " + row[1]; <%--его ИНН--%>
                if (!isEmpty(row[2]))
                    result += " <span class=\"bold\">Расч.счет:</span> " + row[2]; <%--и его счет--%>
                return result;
            }
        </script>
    </c:if>
</div>

<c:set var="result" value="${frm.searchResults}"/>
<c:set var="searchPage" value="${frm.searchPage}"/>
<c:set var="itemsPerPage" value="${frm.itemsPerPage}"/>

<c:if test="${not empty result or isSearch}">
    <input type="hidden" name="searchPage" id="searchPage">
    <div class="paymentFilterResults <c:if test="${not empty result && empty pageType}">dashedBorder</c:if> ">
     <c:choose>
        <c:when test="${empty result}"> <%-- Если список пустой выводим сообщение --%>
            <div class="emptyText emptyResults">
                <c:set var="services" value=""/>
                <c:if test="${not empty frm.serviceId && not empty payServices}">
                   <c:forEach var="paymentServ" items="${payServices}">
                        <c:set var="services" value="${services} - \"${paymentServ.name}\""/>
                    </c:forEach>
                </c:if>
                <c:choose>
                    <c:when test="${frm.isAutoPaySearch && phiz:impliesService('ClientFreeDetailAutoSubManagement')}">
                        <span class="text-dark-gray normal relative">
                            <c:choose>
                                <c:when test="${frm.fields.regionId==-1 || empty frm.fields.regionId}">
                                    Не найдено ни одного получателя, соответствующего условиям поиска. Пожалуйста, задайте другие параметры или щелкните по ссылке <phiz:link styleClass="orangeText" action="/autopayment/freeDetatilAutoSub" serviceId="ClientFreeDetailAutoSubManagement"><span>&laquo;Автоплатеж по реквизитам&raquo;</span></phiz:link>.
                                </c:when>
                                <c:otherwise>
                                    Не найдено ни одного получателя в регионе оплаты. Пожалуйста, задайте другие параметры или щелкните по ссылке <phiz:link styleClass="orangeText" action="/autopayment/freeDetatilAutoSub" serviceId="ClientFreeDetailAutoSubManagement"><span>&laquo;Автоплатеж по реквизитам&raquo;</span></phiz:link>.
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="text-dark-gray normal relative">
                            Для Вашего региона оплаты <c:if test="${frm.serviceId != null && frm.serviceId != ''}">в категории “${paymentService.name}”</c:if> не найдены поставщики услуг. Вы можете:
                            <ul>
                                <li>  расширить область поиска, выбрав <span class="underline"><html:link href="#" styleClass="orangeText" onclick="win.open('searchRegionsDiv'); return false;"><span>другой регион оплаты</span></html:link></span>; </li>
                                <li>  воспользоваться строкой поиска; </li>
                                <c:if test="${pageType != 'template' && pageType != 'mobilebank'}">
                                    <li>  совершить <span class="underline"><phiz:link styleClass="orangeText" action="/private/payments/jurPayment/edit" serviceId="JurPayment"><span>перевод по реквизитам</span></phiz:link></span>;</li>
                                </c:if>
                                <li>  или <span class="underline"><html:link href="#" styleClass="orangeText" onclick="goback();"><span>вернуться на страницу выбора услуг</span></html:link></span>. </li>
                            </ul>
                        </span>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:when>
        <c:otherwise> <%-- Выводим список --%>
            <ol start="${itemsPerPage*searchPage+1}">
                <c:forEach var="el" items="${result}" varStatus="stat">
                    <c:if test="${stat.count le itemsPerPage}">
                        <c:set var="categoryId" value="${el[0]}"/>
                        <c:set var="categoryName" value="${el[1]}"/>
                        <c:set var="groupId" value="${el[2]}"/>
                        <c:set var="groupName" value="${el[3]}"/>
                        <c:set var="serviceId" value="${el[4]}"/>
                        <c:set var="serviceName" value="${el[5]}"/>
                        <c:set var="providerId" value="${el[6]}"/>
                        <c:set var="providerName" value="${el[7]}"/>
                        <c:set var="providerInn" value="${el[8]}"/>
                        <c:set var="providerAccount" value="${el[9]}"/>
                        <c:set var="resultType" value="${el[10]}"/>
                        <c:set var="showBreadcrumbs" value="${el[11]}"/>
                        <c:choose>
                            <c:when test="${resultType == 'service'}">
                                <li>
                                    <span class="serviceProvider">
                                        <phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                            <phiz:param name="serviceId" value="${serviceId}"/>
                                            <c:if test="${groupId != null || categoryId != null}">
                                                <phiz:param name="parentIds" value="${categoryId},${groupId}"/>
                                            </c:if>
                                            <span class="word-wrap">${serviceName}</span>
                                        </phiz:link>
                                   </span>
                                   <c:if test="${showBreadcrumbs || isSmsTemplateCreation}">
                                        <div class="payment-parametrs">
                                            <c:if test="${categoryId != null}">
                                                <span class="serviceGroup">
                                                    <phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                        <phiz:param name="serviceId" value="${categoryId}"/>
                                                        <span>${categoryName}</span>
                                                    </phiz:link>
                                                </span> &rarr;
                                            </c:if>
                                            <c:if test="${groupId != null}">
                                                <span class="serviceGroup">
                                                    <phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                        <phiz:param name="serviceId" value="${groupId}"/>
                                                        <phiz:param name="parentIds" value="${categoryId}"/>
                                                        <span>${groupName}</span>
                                                    </phiz:link>
                                               </span>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </li>
                            </c:when>
                            <c:when test="${resultType == 'provider'}">
                                 <li>
                                   <span class="serviceProvider">
                                        <phiz:link styleClass="orangeText" url="${providerAction}" serviceId="RurPayJurSB">
                                            <phiz:param name="recipient" value="${providerId}"/>
                                            <span class="word-wrap">${providerName}</span>
                                        </phiz:link>
                                   </span>
                                   <div class="payment-parametrs">
                                       <c:if test="${not empty providerInn}"><span class="bold">ИНН:</span> ${providerInn}</c:if>
                                       <c:if test="${not empty providerAccount}"><span class="bold">Расч. счет:</span> ${providerAccount}</c:if>
                                   </div>
                                    <c:if test="${showBreadcrumbs  || isSmsTemplateCreation}">
                                        <div class="payment-parametrs">
                                            <c:if test="${categoryId != null}">
                                                <span class="serviceGroup">
                                                    <phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                        <phiz:param name="serviceId" value="${categoryId}"/>
                                                        <span>${categoryName}</span>
                                                    </phiz:link>
                                                </span> &rarr;
                                            </c:if>
                                            <c:if test="${groupId != null}">
                                                <span class="serviceGroup">
                                                    <phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                        <phiz:param name="serviceId" value="${groupId}"/>
                                                        <phiz:param name="parentIds" value="${categoryId}"/>
                                                        <span>${groupName}</span>
                                                    </phiz:link>
                                               </span> &rarr;
                                            </c:if>
                                            <c:if test="${serviceId != null}">

                                                <span class="serviceGroup">
                                                    <phiz:link styleClass="orangeText" url="${serviceAction}" serviceId="RurPayJurSB">
                                                        <phiz:param name="serviceId" value="${serviceId}"/>
                                                        <phiz:param name="parentIds" value="${categoryId},${groupId}"/>
                                                        <span>${serviceName}</span>
                                                    </phiz:link>
                                               </span>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:url var="paymentActionURL" value="${phiz:getLinkPaymentByFormName(categoryId)}">
                                    <c:param name="form" value="${categoryId}"/>
                                </c:url>
                                <c:if test="${isTemplate}">
                                    <c:choose>
                                        <c:when test="${categoryName == 'JurPayment'}">
                                            <c:url var="paymentActionURL" value="/private/template/jurPayment/edit.do">
                                                <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                                                <c:param name="extractId" value="${extractId}"/>
                                            </c:url>
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="paymentActionURL" value="/private/payments/template.do">
                                                <c:param name="form" value="${categoryName}"/>
                                                <c:param name="fromFinanceCalendar" value="${fromFinanceCalendar}"/>
                                                <c:param name="extractId" value="${extractId}"/>
                                            </c:url>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                                <li>
                                    <span class="serviceProvider">
                                        <phiz:link styleClass="orangeText" url="${paymentActionURL}">
                                            <span class="word-wrap">${providerName}</span>
                                        </phiz:link>
                                    </span>
                                </li>
                            </c:otherwise>
                        </c:choose>

                    </c:if>
                </c:forEach>
            </ol>
            <c:if test="${searchPage > 0 || fn:length(result) > itemsPerPage}">
                <div class="pagination">
                    <c:choose>
                        <c:when test="${searchPage gt 0}">
                            <a class="active-arrow" href="#" onclick="changeSearchPage(${searchPage-1})"><div class="activePaginLeftArrow"></div></a>
                        </c:when>
                        <c:otherwise>
                            <div class="inactivePaginLeftArrow"></div>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${fn:length(result) gt itemsPerPage}">
                            <a class="active-arrow" href="#" onclick="changeSearchPage(${searchPage+1})"><div class="activePaginRightArrow"></div></a>
                        </c:when>
                        <c:otherwise>
                            <div class="inactivePaginRightArrow"></div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </c:otherwise>
     </c:choose>
    </div>
    <c:if test="${empty pageType && not empty result}">
        <%@ include file="/WEB-INF/jsp-sbrf/payments/jurPaymentBlock.jsp"%>
    </c:if>
    <div class="clear"></div>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.goto.select.service"/>
        <tiles:put name="commandHelpKey" value="button.goto.select.service"/>
        <tiles:put name="bundle" value="paymentsBundle"/>
        <tiles:put name="viewType" value="buttonGrey"/>
        <tiles:put name="onclick" value="goback();"/>
        <tiles:put name="image"       value="backIcon.png"/>
        <tiles:put name="imageHover"     value="backIconHover.png"/>
        <tiles:put name="imagePosition"  value="left"/>
    </tiles:insert>

</c:if>

