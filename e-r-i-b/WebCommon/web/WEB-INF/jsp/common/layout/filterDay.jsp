<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
Компонент для отображения стандартного фильтра по временным промежуткам: за сегодня, за вчера, за дату
title - заголовок к фильтру
buttonKey - действие для кнопки фильтрации
name - имя фильтра
buttonBundle - бандл, где ищется описание для кнопки фильтрации
periodFormat - формат ввода периода
isSimpleTrigger - не обязательный параметр, указывающий, что данный переключатель является просто переключателем. По умолчанию false
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
    <table class="date-filter" onkeypress="onEnterKey(event);">
        <tr>
            <td class="filterDataTitle">
                <nobr>
                    ${title}:
                </nobr>
            </td>

            <c:set var="className" value="date-filter-active"/>

            <td class="${className}" id="type${name}period" onclick="oneClickFilterDateChange ('${name}', 'period', ${isSimpleTrigger}); return false;">
                <nobr>
                    на дату
                    <span id="type${name}periodDetail">
                        &nbsp;<input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="${periodFormat}"/>'
                        size="10" name="filter(from${name})" id="filter(from${name})" class="${name}date-pick" />
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="viewType" value="icon"/>
                            <tiles:put name="commandKey" value="${buttonKey}"/>
                            <tiles:put name="commandHelpKey" value="${buttonKey}.help"/>
                            <tiles:put name="image"          value="period-find.gif"/>
                            <tiles:put name="bundle"         value="${buttonBundle}"/>
                            <tiles:put name="isDefault"        value="true"/>
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
            if(ensureElement('filter(type${name})').value != 'period')
            {
                ensureElement('filter(from${name})').value = '';
            }
        });
</script>

