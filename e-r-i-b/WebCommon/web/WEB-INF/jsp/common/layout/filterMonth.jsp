<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
 омпонент дл€ отображени€ стандартного фильтра по временным промежуткам: за определенный мес€ц, за период (в мес€цах)
title - заголовок к фильтру
buttonKey - действие дл€ кнопки фильтрации
name - им€ фильтра
buttonBundle - бандл, где ищетс€ описание дл€ кнопки фильтрации
periodFormat - формат ввода периода
--%>

<tiles:importAttribute/>

<c:if test="${empty title}">
    <c:set var="title" value="операции"/>
</c:if>

<script type="text/javascript">
    doOnLoad(filterDateChange, '${name}');
    <c:if test="${not empty name}">
        doOnLoad(
            function()
            {
                $(function()
                {
                    <c:set var="contextName" value="${phiz:loginContextName()}"/>
                    var dP;
                    if ($('.${name}date-pick').datePicker)
                        <c:choose>
                            <c:when test="${contextName == 'PhizIA'}">
                                dP = $('.${name}date-pick').datePicker({displayClose: true, chooseImg: skinUrl + '/images/calendar.gif', dateFormat:'${periodFormat}'.toLowerCase()});
                            </c:when>
                            <c:otherwise>
                                dP = $('.${name}date-pick').datePicker({displayClose: true, chooseImg: globalUrl + '/commonSkin/images/datePickerCalendar.gif', dateFormat:'${periodFormat}'.toLowerCase()});
                            </c:otherwise>
                        </c:choose>
                    dP.dpApplyMask();
                });
        });
    </c:if>
</script>


<html:hidden styleId="filter(type${name})" property="filter(type${name})" onchange="filterDateChange('${name}')"/>

<c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${name})"/></c:set>
<table class="date-filter">
    <tr>
        <td style="height: 24px; vertical-align: middle; padding-top: 3px; margin-top: -3px; padding-bottom: 3px; margin-bottom: -3px;">
            <nobr>
                ѕоказать ${title}:
            </nobr>
        </td>

        <c:set var="className" value="${curType == 'month' ? 'date-filter-active' : 'date-filter-no-active'}"/>
        <c:set var="style" value="${curType != 'month' ? 'display: none;' : 'display: inline;'} "/>
        <td class="${className}" id="type${name}month" onclick="oneClickFilterMonthDateChange('${name}', 'month'); return false;">
            <nobr>
                за мес€ц
                <span style="${style}" id="type${name}monthDetail" class="monthYear">
                    &nbsp;<input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="${periodFormat}"/>'
                    size="8" name="filter(on${name})" id="filter(on${name})" class="${name}date-pick"/>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="viewType" value="icon"/>
                        <tiles:put name="commandKey" value="${buttonKey}"/>
                        <tiles:put name="commandHelpKey" value="${buttonKey}.help"/>
                        <tiles:put name="image"          value="period-find.gif"/>
                        <tiles:put name="bundle"         value="${buttonBundle}"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="validationFunction" value="${validationFunction}"/>
                    </tiles:insert>
                </span>
            </nobr>
        </td>

        <c:set var="className" value="${curType == 'monthPeriod' ? 'date-filter-active' : 'date-filter-no-active'}"/>
        <c:set var="style" value="${curType != 'monthPeriod' ? 'display: none;' : 'display: inline;'} "/>
        <td class="${className}" id="type${name}monthPeriod" onclick="oneClickFilterMonthDateChange('${name}', 'monthPeriod'); return false;">
            <nobr>
                <div>
                   за период
                </div>
                <span style="${style}" id="type${name}monthPeriodDetail" class="monthYear">
                    &nbsp;
                    <div>
                        c
                    </div>
                    <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="${periodFormat}"/>'
                           size="8" name="filter(from${name})" id="filter(from${name})" class="${name}date-pick"/>

                    <div>
                        по
                    </div>
                    <%-- добавлен класс endOfPeriod, необходим дл€ IE8--%>
                    <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="${periodFormat}"/>'
                           size="10" name="filter(to${name})" id="filter(to${name})" class="${name}date-pick endOfPeriod"/>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="viewType" value="icon"/>
                        <tiles:put name="commandKey" value="button.filter"/>
                        <tiles:put name="commandHelpKey" value="button.filter.help"/>
                        <tiles:put name="image"          value="period-find.gif"/>
                        <tiles:put name="bundle"         value="${buttonBundle}"/>
                        <tiles:put name="isDefault"        value="true"/>
                        <tiles:put name="validationFunction" value="${validationFunction}"/>
                    </tiles:insert>
                </span>
            </nobr>
        </td>
    </tr>
</table>


<script type="text/javascript">
    addClearMasks(null,
        function (event)
        {
            var regexp = /[dmy]/gi;
            clearInputTemplate('filter(from${name})', '${periodFormat}'.replace(regexp, '_'));
        });
</script>

