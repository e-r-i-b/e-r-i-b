<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute ignore="true"/>
<%--
    Компонент для отображения фильтра

    data                - данные фильтра
    hiddenData          - данные расширенного фильтра
    buttonKey           - действие для кнопки фильтрации
    buttonTextKey       - текст для кнопки фильтрации
    buttonBundle        - бандл, где ищется описание для кнопки фильтрации
    validationFunction  - функция валидации
    hideFilterButton    - признак необходимости прятать коммандную кнопку фильтра. по умолчанию false
    showClearButton     - признак необходимости показывать кнопку "Очистить"
    showButtonOpenMore     - признак необходимости показывать кнопку "Показать/Свернуть"
    clearButtonBundle   -  бандл, где ищется описание для кнопки "очистить"
    clearButtonPosition  - расположение кнопки "очистить"
    clearButtonStyle     - стить кнопки "очистить"
    rightBtn            - позиционирование кнопки справа
--%>
<script type="text/javascript">
    function needCleaning(elem)
    {
        return ((elem.tagName == "INPUT" && elem.type != "checkbox") || elem.tagName == "SELECT")&&
               elem.name.indexOf('filter(') >=0 && elem.className.indexOf("notCleaningField") == -1;
    }

    function clearFilterParams(data)
    {
        var frm = document.forms.item(0);
        for (var i = frm.elements.length-1; i >= 0; i--)
        {
            var elem = frm.elements.item(i);
            if (needCleaning(elem))
                elem.value = "";
        }

        var params =  data.split(";");
        for (var j=params.length-1; j >=0; j--)
        {
            var filterElement= params[j].split("=");
            if (filterElement.length != 2) {
                continue;
            }
            var element = getElement("filter("+trim(filterElement[0])+")");
            var value = trim(filterElement[1]);
            if (element != null)
            {
                if (element.type != 'checkbox')
                {
                    element.value = value;
                } else
                {
                    element.checked = (value == 'true' || value == 'on');
                }
            }
        }

        if ($('.Datedate-pick').datePicker)
        {
            var dP = $('.Datedate-pick').datePicker({displayClose: true, chooseImg: skinUrl + '/skins/commonSkin/images/datePickerCalendar.gif', dateFormat: 'dd/mm/yyyy'});
            dP.dpApplyMask();
        }
        if (window.additionalClearFilter)
            additionalClearFilter();
        return false;
    }
</script>
<c:set var="filterBt">
    <span class="filterAreaButtons">
        <c:if test="${showClearButton && clearButtonPosition =='center'}">
            <tiles:insert definition="clientButton" flush="fase">
                <tiles:put name="commandTextKey" value="button.clear"/>
                <tiles:put name="commandHelpKey" value="button.clear.help"/>
                <tiles:put name="bundle" value="${clearButtonBundle}"/>
                <tiles:put name="onclick" value="clearFilter(event);"/>
                <tiles:put name="viewType" value="${clearButtonStyle}"/>
            </tiles:insert>
        </c:if>
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="${buttonKey}"/>
            <tiles:put name="commandTextKey" value="${buttonTextKey}"/>
            <tiles:put name="commandHelpKey" value="${buttonKey}.help"/>
            <tiles:put name="bundle" value="${buttonBundle}"/>
            <c:if test="${!empty validationFunction}">
                <tiles:put name="validationFunction" value="${validationFunction}"/>
            </c:if>
            <tiles:put name="isDefault" value="true"/>
        </tiles:insert>

    </span>
    <c:if test="${showClearButton && clearButtonPosition =='right'}">
        <div class="filterClearButtonRight">
        <tiles:insert definition="clientButton" flush="fase">
            <tiles:put name="commandTextKey" value="button.clear"/>
            <tiles:put name="commandHelpKey" value="button.clear.help"/>
            <tiles:put name="bundle" value="${clearButtonBundle}"/>
            <tiles:put name="onclick" value="clearFilter(event);"/>
            <tiles:put name="viewType" value="${clearButtonStyle}"/>
        </tiles:insert>
        </div>
    </c:if>
</c:set>

<c:if test="${empty filterComponentCounter}">
    <c:set var="filterComponentCounter" value="0" scope="request"/>
</c:if>

<c:set var="filterComponentCounter" value="${filterComponentCounter+1}"  scope="request"/>

<div class="filter-wrapper">
    <div class="filter" onkeypress="onEnterKey(event);">
        <c:choose>
            <c:when test="${rightBtn}">
                <div class="fieldBox">
                    ${data}
                </div>
            </c:when>
            <c:otherwise>
                ${data}
            </c:otherwise>
        </c:choose>
        <%-- Кнопка фильтра --%>
        <c:if test="${hideFilterButton}">
            <c:set var="style" value="display: none;"/>
        </c:if>
        <c:choose>
            <c:when test="${rightBtn}">
             <div class="btnBox">
                 <span class="filterButton" style="${style}">${filterBt}</span>
             </div>
            </c:when>
            <c:otherwise>
                <span class="filterButton" style="${style}">${filterBt}</span>
            </c:otherwise>
        </c:choose>

        <%-- Расширеный фильтр --%>
        <c:if test="${not empty hiddenData}">
            <%-- значения фильтра, запоминаем и вытаскиваем из формы --%>
            <c:set var="fForm" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="filterCounter" value="filterCounter${filterComponentCounter}"/>
            <c:set var="counterState">${fForm.filterState[filterCounter] == null?"false":fForm.filterState[filterCounter]}</c:set>
            <input type="hidden" name="filterState(filterCounter${filterComponentCounter})" value='<c:out value="${counterState}"/>'/>
            <c:if test="${showButtonOpenMore}">
                <span class="more-filter-link">
                    <a href="#" class="showHideMoreFilterLink"
                       rel="свернуть <img src='${globalUrl}/commonSkin/images/close-filter.gif' class='more-filter-image'/>"
                       onclick="openMoreFilter(this, 'filterState(filterCounter${filterComponentCounter})'); return false;" id="filterCounterTag${filterComponentCounter}">
                        показать
                        <img src="${globalUrl}/commonSkin/images/show-filter.gif" class="more-filter-image"/>
                    </a>
                </span>
            </c:if>
            <div class="clear"></div>
            <div class="filterMore" style="display: none">
                ${hiddenData}
                <span class="filterAreaButtons">
                    <span class="filterMoreButton">${filterBt}</span>
                </span>
            </div>
        </c:if>
   </div>
</div>
<c:if test="${showButtonOpenMore}">
    <img src="${globalUrl}/commonSkin/images/filter-tooth.gif" class="filter-tooth"/>
</c:if>
<c:if test="${not empty counterState && counterState}">
    <script type="text/javascript">
        document.getElementById("filterCounterTag${filterComponentCounter}").onclick();
    </script>
</c:if>
