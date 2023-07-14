<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="activeElement"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${name})"/></c:set>
<c:set var="onDateName" value="on${name}"/>
<c:set var="onDate" value="${form.filters[onDateName]}"/>
<c:if test="${activeElement == 'period'}">
    <c:set var="onDate" value="${phiz:currentDate().time}"/>
</c:if>

<script type="text/javascript">
    function changeDateFilterType(type)
    {
        $('input[name="filter(type${name})"]').val(type);

        if(type == 'period')
        {
            $('.dateFilter .periodFilterDisabler').hide();
        }
        if(type=='month')
        {
            $('.dateFilter .periodFilterDisabler').show();
            if('${activeElement}' != 'month')
            {
                findCommandButton('button.filter').click('', false);
            }
        }
    }

    function changeMonth(type)
    {
        $('input[name="filter(type${name})"]').val("month");
        var dateInput = $('input[name="filter(on${name})"]');
        var date = Str2Date(dateInput.val());
        date.setDate(1);

        if (type == 'next')
        {
            date.setMonth(date.getMonth() + 1);
        }
        else
        {
            date.setMonth(date.getMonth() - 1);
        }

        var day = date.getDate();
        var month = date.getMonth() + 1;
        dateInput.val((day < 10 ? "0" + day : day) + '/'
                    + (month < 10 ? "0" + month: month)
                    + '/' + date.getFullYear());

        findCommandButton('button.filter').click('', false);
    }
</script>

<div class="dateFilter">
    <div class="dateFilterBg">
        <html:hidden property="filter(type${name})" value="${activeElement}" onchange="filterDateChange('${name}')"/>
        <input type="hidden" name="filter(on${name})" value='<fmt:formatDate value="${onDate}" pattern="${periodFormat}"/>'/>

        <c:set var="currentFilterDate" value="${phiz:firstDayOfMonth(phiz:currentDate()).time}"/>
        <c:set var="prevMonthOnclick" value=""/>
        <c:set var="nextMonthOnclick" value=""/>
        <c:if test="${phiz:addToDate(onDate, 0, -1, 0) >= phiz:addToDate(currentFilterDate, -1, -1, 0)}">
            <c:set var="prevMonthOnclick" value="if(!redirectResolved()) return false; changeMonth('prev');"/>
        </c:if>
        <c:if test="${phiz:addToDate(onDate, 0, 1, 0) < phiz:addToDate(currentFilterDate, 0, 1, 0)}">
            <c:set var="nextMonthOnclick" value="if(!redirectResolved()) return false; changeMonth('next');"/>
        </c:if>
        <div class="monthSelect">
            <div class="monthSelectLeftPart" onclick="${prevMonthOnclick}">
                <div class="monthSelectLeft"></div>
                <div class="monthSelectArrowLeft">
                    <img src="${globalImagePath}/arrow-to-left.gif" border="0">
                </div>
                <div class="monthSelectDivider">
                    <img src="${globalImagePath}/grayDivideLine.png" border="0">
                </div>
            </div>
            <div class="monthSelectCenter" onclick="if(!redirectResolved()) return false; changeDateFilterType('month');">
                <fmt:formatDate value="${onDate}" pattern="MMMM yyyy"/>
            </div>
            <div class="monthSelectLeftPart" onclick="${nextMonthOnclick}">
                <div class="monthSelectDivider">
                    <img src="${globalImagePath}/grayDivideLine.png" border="0">
                </div>
                <div class="monthSelectArrowRight">
                    <img src="${globalImagePath}/arrow-to-right.gif" border="0">
                </div>
                <div class="monthSelectRight"></div>
            </div>
            <div class="clear"></div>
        </div>

        <div class="periodFilterContent filterContent" onclick="if(!redirectResolved()) return false; changeDateFilterType('period');">
            <div class="periodFilterDisabler opacity50" <c:if test="${activeElement == 'period'}">style="display: none;"</c:if>></div>
            <span style="display: inline;">
                &nbsp;
                <div>
                    За период
                </div>
                <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="${periodFormat}"/>'
                       size="10" name="filter(from${name})" class="${name}date-pick"/>

                <div>
                    по
                </div>
                <%-- добавлен класс endOfPeriod, необходим для IE8--%>
                <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="${periodFormat}"/>'
                       size="10" name="filter(to${name})" class="${name}date-pick endOfPeriod"/>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="viewType"       value="icon"/>
                    <tiles:put name="commandKey"     value="button.filter"/>
                    <tiles:put name="commandHelpKey" value="button.filter.help"/>
                    <tiles:put name="image"          value="period-find.gif"/>
                    <tiles:put name="bundle"         value="${buttonBundle}"/>
                    <tiles:put name="isDefault"      value="true"/>
                    <tiles:put name="validationFunction" value="${validationFunction}"/>
                </tiles:insert>
            </span>
            <div class="clear"></div>
        </div>
    </div>
</div>

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
