<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:importAttribute/>
<c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isSearch" value="${frm.searchServices != null and fn:length(fn:trim(frm.searchServices)) != 0}"/>
<c:set var="isSearchNew" value="${frm.fields.isSearch}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript">var showProvidersURL = "${phiz:calculateActionURL(pageContext,'/private/async/providers')}";</script>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/PaymentsAndOperation.js"></script>
<script type="text/javascript">
    <%-- Валидатор кнопки Искать --%>
    function searchBtClick()
    {
        removeMessage(MIN_SEARCH_STRING_MESSAGE);
        var searchInput = document.getElementById("searchServices");
        <c:if test="${not isSearch}"> <%-- только если небыло поиска --%>
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
</script>

<div class="paymentFilter paymentGoods">
    <c:set var="findProviderParams" value=""/>
    <div class="filter">
        <c:if test="${pageType == 'index'}">
            <div class="categoriesTitle"><bean:message key="label.payments.services" bundle="commonBundle"/></div>
        </c:if>
        <c:if test="${pageType == 'autopayment'}">
            <div class="categoriesTitle">Поиск</div>
        </c:if>
        <c:if test="${pageType == 'basketServices'}">
            <div class="categoriesTitle">Подключение услуги с выставлением счета</div>
            <div class="clear"></div>
            <div class="searchServicesDescription">Воспользуйтесь поиском или выберите из списка подходящую услугу</div>
            <div class="clear"></div>
            <span class="size24 floatLeft sp-regionWord">Регион: </span>
            <c:set var="findProviderParams" value="accountingEntityId=${frm.accountingEntityId}"/>
        </c:if>
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

            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="searchRegionsDiv"/>
                <tiles:put name="loadAjaxUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/list')}?isOpening=true"/>
                <tiles:put name="styleClass" value="regionsDiv"/>
            </tiles:insert>

            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="confirmSaveRegion"/>
                <tiles:put name="styleClass" value="confirmWidow"/>
                <tiles:put name="data">
                    <div class="confirmWindowTitle">
                        <h2>
                            Сохранение региона оплаты
                        </h2>
                    </div>

                    <div id="message_confirmSaveRegion" class="confirmWindowMessage">
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
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.saveRegion"/>
                            <tiles:put name="commandHelpKey" value="button.saveRegion"/>
                            <tiles:put name="onclick" value="saveRegion();"/>
                            <tiles:put name="bundle" value="paymentsBundle"/>
                        </tiles:insert>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
        <div class="clear"></div>
        <div class="paymentSearch">
            <div class="searchForm" onkeydown="onEnterKey(event);">
                <div class="rightRound">
                    <div class="cetnerSearch">
                        <html:text property="searchServices" value="${frm.searchServices}" styleClass="customPlaceholder search"
                                   maxlength="256" styleId="searchServices" titleKey="search.service.providers.help"  bundle="commonBundle"/>

                        <div id="search-button">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.search"/>
                                <tiles:put name="commandHelpKey" value="button.search"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                                <tiles:put name="onclick" value="search();"/>
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
                            </c:if>,
                            matchSubset:0,
                            greyStyle:false,
                            resultsClass: "serviceProviderSerchResult",
                            matchContains:1,
                            selectFirst:false,
                            formatItem:liFormat,
                            operaIgnore:true,
                            onItemSelect: function(li){
                                search();
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
<c:set var="regions" value="${frm.regions}"/>
<c:set var="provCount" value="${frm.provCount}"/>
<c:set var="resultCount" value="${frm.resultCount}"/>
<c:set var="searchPage" value="${frm.searchPage}"/>
<c:set var="orderByRegion" value="${frm.searchType == 'byRegion'}"/>
<input type="hidden" name="searchPage" id="searchPage">
<input type="hidden" name="paginationType" id="paginationType"/>
<input type="hidden" id="needAdd" value="false"/>
<input type="hidden" name="provCount" value="${provCount}">
<input type="hidden" name="provCountInPage" id="provCountInPage"/>
<input type="hidden" name="pageList" id="pageList" value="${frm.pageList}"/>
<input type="hidden" name="searchType" value="${frm.searchType}" />
<c:if test="${not empty result or isSearchNew}">
    <div class="providerFilterTabs">
        <table class="date-filter">
            <tr class="noBottomPadding">
                <td class="date-filter-desc">упорядочить:</td>
                <td class="date-filter-${orderByRegion ? '' : 'no-'}active" id="activePayments" onclick="changeOrderType('byRegion');">
                   <span>по региону</span>
                </td>
                <td class="date-filter-${frm.searchType == 'byName' ? '' : 'no-'}active" id="inactivePayments" onclick="changeOrderType('byName');">
                    <span>по алфавиту</span>
                </td>
                <td class="date-filter-${frm.searchType == 'byService' ? '' : 'no-'}active" id="allPayments" onclick="changeOrderType('byService');">
                   <span>по услуге</span>
                </td>
            </tr>
        </table>
    </div>
    <div id="providerFilterResults">
        <div id="homeRegion">
            <c:choose>
                <c:when test="${not empty result}">
                    <c:set var="currLetter" value=""/>
                    <c:set var="stringCount" value="0"/>
                    <c:set var="providerCountInBlock" value="0"/>
                    <c:set var="providerCount" value="0"/>
                    <c:set var="fromResource" value="${frm.fromResource}"/>
                    <c:set var="serviceActionUrl" value="${serviceURL}"/>

                    <c:set var="serviceProviderAction" value="${providerURL}"/>
                    <c:set var="paymentAction" value="/private/payments/payment.do"/>
                    <c:choose>
                        <c:when test="${orderByRegion}">
                            <%@ include file="/WEB-INF/jsp-sbrf/payments/search/orderByRegion.jsp" %>
                        </c:when>
                        <c:when test="${frm.searchType == 'byName'}">
                            <%@ include file="/WEB-INF/jsp-sbrf/payments/search/orderByName.jsp" %>
                        </c:when>
                        <c:when test="${frm.searchType == 'byService'}">
                            <%@ include file="/WEB-INF/jsp-sbrf/payments/search/orderByService.jsp" %>
                        </c:when>
                    </c:choose>
                    <div class="clear"></div>
                    <c:if test="${provCount > 0 || fn:length(result) > providerCount}">
                        <div class="buttonsArea">
                            <c:choose>
                                <c:when test="${provCount gt 0}">
                                    <c:set var="str" value="${phiz:replaceQuotes(frm.searchServices)}"/>
                                    <a class="active-arrow" onclick="changeSearchPage(${providerCount}, 'last', ${searchPage}, '${str}');"><div class="activePaginLeftArrow"></div></a>
                                </c:when>
                                <c:otherwise>
                                    <div class="inactivePaginLeftArrow"></div>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${fn:length(result) gt providerCount}">
                                    <c:set var="str" value="${phiz:replaceQuotes(frm.searchServices)}"/>
                                    <a class="active-arrow" onclick="changeSearchPage(${providerCount}, 'next', ${searchPage}, '${str}');"><div class="activePaginRightArrow"></div></a>
                                </c:when>
                                <c:otherwise>
                                    <div class="inactivePaginRightArrow"></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <div class="emptyText emptyResults">
                        <span class="text-dark-gray normal relative">
                            <c:choose>
                                <c:when test="${orderByRegion}">
                                    Не найдено ни одного получателя в регионе оплаты. Пожалуйста, задайте другие параметры
                                        <c:if test="${pageType == 'index'}"> или щелкните по ссылке <phiz:link styleClass="orangeText" action="/private/payments/jurPayment/edit" serviceId="JurPayment"><span>&laquo;Оплата по реквизитам или квитанции&raquo;</span></phiz:link></c:if>.
                                </c:when>
                                <c:otherwise>
                                    Не найдено ни одного получателя, соответствующего условиям поиска. Пожалуйста, задайте другие параметры <c:if test="${pageType == 'index'}"> или щелкните по ссылке <phiz:link styleClass="orangeText" action="/private/payments/jurPayment/edit" serviceId="JurPayment"><span>&laquo;Оплата по реквизитам или квитанции&raquo;</span></phiz:link></c:if>.
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${orderByRegion}">
            <c:if test="${providerCount % 2 != 0}"><c:set var="providerCount" value="${providerCount+1}"/></c:if>
            <c:if test="${empty result}"><c:set var="providerCount" value="2"/></c:if>
            <div class="otherRegionsAreaShow" id="regionListOpen">
                <c:if test="${findProviderParams != ''}">
                    <c:set var="findProviderParams" value="findProviderParams=${findProviderParams}' + '&' + 'fromResource=${phiz:escapeForJS(frm.fromResource, false)}"/>
                </c:if>
                <c:if test="${findProviderParams == ''}">
                    <c:set var="findProviderParams" value="fromResource=${phiz:escapeForJS(frm.fromResource, false)}"/>
                </c:if>
                <div class="greenTitle" onclick="findProvider(0, 'next',0, ${providerCount == 8 ? resultCount : resultCount - providerCount}, 0, 'true', 'searchServices', '${pageType}', '${findProviderParams}');"> Показать результаты без учета региона</div>
            </div>
            <div id="otherRegion"></div>
        </c:if>
    </div>
    <div class="clear"></div>
</c:if>
<c:if test="${isSearchNew && orderByRegion && empty result}">
    <script type="text/javascript">
        doOnLoad(function() {
            findProvider(0, 'next',0, 6, 0, 'true', 'searchServices', '${pageType}', '${findProviderParams}');
        });
    </script>
</c:if>
