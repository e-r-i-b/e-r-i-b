<%--
  Created by IntelliJ IDEA.
  User: mihaylov
  Date: 26.05.2010
  Time: 19:38:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
Компонент для отображения стандартного фильтра по временным промежуткам: неделям, месяцам и периодам
title - заголовок к фильтру (что мы отфильтровываем, пр. Операции)
isNeedTitle - требуется ли отображать заголовок к фильтру (По умолчанию true)
week - отображать ли радоибаттон/вкладку для недели
month - отображать ли радоибаттон/вкладку для месяца, если указано extra - то отображается форма со стрелками для выбора месяца.
windowId - идентификатор окна в котором открыт компонент, если не задан, значит фильтр встроенный в основную страницу
buttonKey - действие для кнопки фильтрации
onclick - javascript функция, срабатывающаЯ при нажатии на кнопку фильтрации
action - экшен, срабатывающий при нажатии на кнопку фильтрации
name - имя фильтра
buttonBundle - бандл, где ищется описание для кнопки фильтрации
buttons - набор дополнительных кнопок
needErrorValidate - признак необходимости отображения ошибок валидации рядом с полем
periodFormat - формат ввода периода
id - идентификатор кнопок фильтра, для управления расположением этих кнопок
isSimpleTrigger - не обязательный параметр, указывающий, что данный переключатель является просто переключателем. По умолчанию false
enableFilterDataPeriod - не обязательный прараметр, разрешает использовать класс filter-validateData. По умолчанию true
useInPFR - не обязательный прараметр, указывает на использованеи в ПФР. По умолчанию false
--%>

<tiles:importAttribute/>
<c:set var="filterData">

    <%--Неделя--%>
    <c:if test="${week}">
        <div class="alignLabel">
            <html:radio property="filter(type${name})" value="week" styleId="type${name}Week" onchange="filterDateChange('${name}')"  onclick="this.blur();"/>
            <label for="type${name}Week">за неделю</label>
        </div>
    </c:if>

    <%--Месяц--%>
    <c:if test="${month}">
        <div class="alignLabel">
            <html:radio property="filter(type${name})" value="month" styleId="type${name}Month" onchange="filterDateChange('${name}')"  onclick="this.blur();"/>
            <label for="type${name}Month">за месяц</label>
        </div>
    </c:if>
    <div class="clear"></div>
    <div class="btnFilterPeriod">
        <c:if test="${week or month}">
            <html:radio property="filter(type${name})" value="period" styleId="type${name}" onchange="filterDateChange('${name}', this)" onclick="this.blur();"/>
        </c:if>
        <label for="type${name}">за период</label>
            c
            <input disabled="true" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="${periodFormat}"/>'
                   size="10" name="filter(from${name})" id="filter(from${name})" class="${name}date-pick" />
            по
            <input disabled="true" value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="${periodFormat}"/>'
                   size="10" name="filter(to${name})" id="filter(to${name})" class="${name}date-pick"/>

    </div>
    <script type="text/javascript">
        function isPeriod${name}()
        {
            return $('[name="filter(type${name})"]:checked').val() == 'period';
        }

    </script>
</c:set>

<c:set var="oneClickFilterData">
    <html:hidden styleId="filter(type${name})" property="filter(type${name})" onchange="filterDateChange('${name}')"/>

    <c:if test="${month == 'extra'}">
        <div class="newPeriodFilter">
            <div id="periodMonth${name}" class="inner firstButton activeButton" onclick="hideShowPeriodSelector('false');filter();">Месяц</div>
            <div id="otherPeriod${name}" class="inner lastButton" onclick="hideShowPeriodSelector('true');">Период</div>
        </div>
        <div id="monthSelector${name}" class="float">
            <div class="float" style="margin-left: 20px;">&nbsp;</div>
            <div class="newPeriodFilter" >
                <div class="inner firstButton arrow" onclick="setDateForMonthButton(-1, true); selectCurrentPeriod();">&#9668;</div>
                <div class="inner" id="periodName${name}" onclick="selectCurrentPeriod();">Текущий месяц</div>
                <div class="inner lastButton arrow" onclick="setDateForMonthButton(+1, true); selectCurrentPeriod();">&#9658;</div>
            </div>
        </div>
        <div class="float newPeriodFilterContainer" id="periodSelector${name}">
    </c:if>
    <script type="text/javascript">
        function isPeriod${name}()
        {
            return ensureElement('filter(type${name})').value == 'period';
        }
    </script>

    <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(type${name})"/></c:set>
    <table class="date-filter" onkeypress="onEnterKey(event);">
        <tr>
            <c:if test="${isNeedTitle}">
                <td class="filterDataTitle">
                    <nobr>
                        ${title}:
                    </nobr>
                </td>
            </c:if>
            <%--Неделя--%>
            <c:if test="${week}">
                <c:set var="className" value="date-filter-no-active"/>
                <c:if test="${curType == 'week'}">
                    <c:set var="className" value="date-filter-active"/>
                </c:if>
                <td class="${className}" id="type${name}week" onclick="oneClickFilterDateChange ('${name}', 'week', ${isSimpleTrigger}); return false;">
                    <span>
                        <nobr>
                            за неделю
                        </nobr>
                    </span>
                </td>
                <td class="separateTableTabs"></td>
            </c:if>

            <%--Месяц--%>
            <c:if test="${month == 'true'}">
                <c:set var="className" value="date-filter-no-active"/>
                <c:if test="${curType == 'month'}">
                    <c:set var="className" value="date-filter-active"/>
                </c:if>
                <td class="${className}" id="type${name}month" onclick="oneClickFilterDateChange ('${name}', 'month', ${isSimpleTrigger}); return false;">
                    <span>
                        <nobr>
                            за месяц
                        </nobr>
                    </span>
                </td>
                <td class="separateTableTabs"></td>
            </c:if>

            <c:if test="${!week && !(month == 'true')}">
                <c:set var="curType" value="period"/>
            </c:if>
            <c:set var="className" value="${curType == 'period' ? 'date-filter-active' : 'date-filter-no-active'}"/>
            <c:set var="style" value="${curType != 'period' ? 'display: none;' : 'display: inline;'} "/>
            <td class="${className}" id="type${name}period" onclick="fillDateTime${name}(); oneClickFilterDateChange ('${name}', 'period', ${isSimpleTrigger}); return false;">
                <nobr>
                    <c:choose>
                        <c:when test="${isNeedTitle}">
                            <div>
                                за период
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${month == 'extra'}">
                                <div>
                                    Период
                                </div>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <span style="${style}" id="type${name}periodDetail">
                        &nbsp;
                        <div>
                            c
                        </div>
                        <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="${periodFormat}"/>'
                               size="10" name="filter(from${name})" id="filter(from${name})" class="${name}date-pick" />
                        <div>
                            по
                        </div>
                      <%-- добавлен класс endOfPeriod, необходим для IE8--%>
                        <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="${periodFormat}"/>'
                               size="10" name="filter(to${name})" id="filter(to${name})" class="${name}date-pick endOfPeriod"/>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="viewType" value="icon"/>
                            <tiles:put name="commandKey" value="button.filter"/>
                            <tiles:put name="commandHelpKey" value="button.filter.help"/>
                            <tiles:put name="image"          value="period-find.gif"/>
                            <tiles:put name="bundle"         value="${buttonBundle}"/>
                            <tiles:put name="isDefault"        value="true"/>
                        </tiles:insert>
                    </span>
                </nobr>
            </td>
        </tr>
    </table>
    <c:if test="${month=='extra'}">
    </div>
   <script type="text/javascript">
       function hideShowPeriodSelector(show)
       {
           if (show.toString() == "true")
           {
               $('#periodSelector${name}').show();
               $('#monthSelector${name}').hide();
               $('#periodMonth${name}').removeClass('activeButton');
               $('#otherPeriod${name}').addClass('activeButton');
               document.getElementById("showPeriod").value = "true";
           }
           else
           {
               $('#monthSelector${name}').show();
               $('#periodSelector${name}').hide();
               $('#periodMonth${name}').addClass('activeButton');
               $('#otherPeriod${name}').removeClass('activeButton');
               document.getElementById("showPeriod").value = "false";
               setDateForMonthButton(0, true);
           }
       }
       function selectCurrentPeriod()
       {
           setDateForMonthButton(0, true);
           filter();
       }
       function  filter()
       {
           findCommandButton("button.filter").click();
       }
       function getFormatted(date)
       {
           return date < 10 ? ("0" + date) : date;
       }
       function getLastDayOfMonth(curDate)
       {
          var date = new Date(curDate.getFullYear(), curDate.getMonth()+1, 0);
          return getFormatted(date.getDate()) + "/" + getFormatted(curDate.getMonth() + 1) + "/" + curDate.getFullYear();
       }
       function getFirstDayOfMonth(curDate)
       {
           return getFormatted(1) + "/" + getFormatted(curDate.getMonth() + 1) + "/" + curDate.getFullYear();
       }
       function setDateForMonthButton(dMonth, needUpdateFilter)
       {
           var dt = $('[name="filter(to${name})"]').val().split("/");
           if (!dMonth)
               dMonth = 0;
           var dte = new Date(dt[2], dt[1] - 1 + dMonth, 1);
           var month = ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'];
           $('#periodName${name}').text(month[dte.getMonth()] + " " + dte.getFullYear());
           if (needUpdateFilter)
           {
                $('[name="filter(to${name})"]').val(getLastDayOfMonth(dte));
                $('[name="filter(from${name})"]').val(getFirstDayOfMonth(dte));
           }
       }
   </script>
</c:if>
</c:set>

<script type="text/javascript">
    doOnLoad(filterDateChange, '${name}');
    <c:if test="${not empty name}">
    doOnLoad(
        function()
        {
            <c:if test="${!week && !(month == 'true')}">
                oneClickFilterDateChange ('${name}', 'period', ${isSimpleTrigger});
            </c:if>
            $(function()
            {
                <c:set var="contextName" value="${phiz:loginContextName()}"/>
                var dpDateFormat = '${periodFormat}'.toLowerCase();
                var dP;
                var elements = $('.${name}date-pick:not(.dp-applied)');
                if (elements.datePicker)
                {
                    <c:choose>
                        <c:when test="${contextName == 'PhizIA'}">
                            dP = elements.datePicker({displayClose: true, chooseImg: skinUrl + '/images/calendar.gif', dateFormat:dpDateFormat});
                        </c:when>
                        <c:otherwise>
                            dP = elements.datePicker({displayClose: true, chooseImg: globalUrl + '/commonSkin/images/datePickerCalendar.gif', dateFormat:dpDateFormat});
                        </c:otherwise>
                    </c:choose>
                    dP.dpApplyMask();
                }
        });
        });
    </c:if>

    <c:if test="${month == 'extra'}">
        doOnLoad(function()
        {
            hideShowPeriodSelector(document.getElementById("showPeriod").value);
            setDateForMonthButton(0, false);
        });
    </c:if>

</script>

<c:choose>
    <c:when test="${needErrorValidate}">
        <div id="filter">

            <c:if test="${empty windowId and enableFilterDataPeriod}"><div class="filter-validateData"></c:if>

            <tiles:insert definition="formRow" flush="false">
                <tiles:put name="title" value="${title}"/>
                <tiles:put name="useInPFR" value="${useInPFR}"/>
                <tiles:put name="isNecessary" value="true"/>
                <tiles:put name="data">
                    ${filterData}
                </tiles:put>
            </tiles:insert>

            <c:if test="${empty windowId and enableFilterDataPeriod}"></div></c:if>

            <div class="clear"></div>

            <c:if test="${not empty windowId}">
               <div class="clear"></div>
            </c:if>
            <div id="${id}" class="filterButtons">
                ${buttons}
                <c:if test="${not empty windowId}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.revoke"/>
                        <tiles:put name="commandHelpKey" value="button.revoke.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                        <tiles:put name="onclick">win.close('${windowId}');</tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="${buttonKey}"/>
                        <tiles:put name="commandHelpKey" value="${buttonKey}.help"/>
                        <tiles:put name="bundle" value="${buttonBundle}"/>
                        <tiles:put name="onclick">${onclick};</tiles:put>
                        <tiles:put name="action" value="${action}"/>
                    </tiles:insert>
                </c:if>
                <div class="clear"></div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        ${oneClickFilterData}
    </c:otherwise>
</c:choose>

<c:set var="currentDate" value="${phiz:currentDate()}"/>
<c:set var="previousWeek" value="${phiz:previousWeek(currentDate)}"/>
<c:set var="previousMonth" value="${phiz:previousMonth(currentDate)}"/>

<c:set var="currentDateStr" value="${phiz:formatDateToStringWithSlash(currentDate)}"/>
<c:set var="previousWeekStr" value="${phiz:formatDateToStringWithSlash(previousWeek)}"/>
<c:set var="previousMonthStr" value="${phiz:formatDateToStringWithSlash(previousMonth)}"/>

<script type="text/javascript">
    function fillDateTime${name}()
    {
        if (!isPeriod${name}())
        {
            ensureElement('filter(to${name})').value = "${currentDateStr}";
            <c:choose>
                <c:when test="${curType=='week'}">
                    ensureElement('filter(from${name})').value = '${previousWeekStr}';
                </c:when>
                <c:otherwise>
                    ensureElement('filter(from${name})').value = '${previousMonthStr}';
                </c:otherwise>
            </c:choose>
        }
    }
    addClearMasks(null,
        function (event)
        {
            var regexp = /[dmy]/gi;
            clearInputTemplate('filter(from${name})', '${periodFormat}'.replace(regexp, '_'));
            clearInputTemplate('filter(to${name})', '${periodFormat}'.replace(regexp, '_'));
            if (!isPeriod${name}())
            {
                ensureElement('filter(from${name})').value = '';
                ensureElement('filter(to${name})').value = '';
            }
        });
</script>

