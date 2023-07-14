<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="onDateName" value="on${name}"/>
<c:set var="onDate" value="${form.filters[onDateName]}"/>

<script type="text/javascript">
    function changeMonth(type)
    {
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
        dateInput.val("01" + '/' + (month < 10 ? "0" + month: month) + '/' + date.getFullYear());

        findCommandButton('${buttonKey}').click('', false);
    }
</script>

<div class="dateFilter">
    <div class="dateFilterBg">
        <input type="hidden" name="filter(on${name})" value='<fmt:formatDate value="${onDate}" pattern="${periodFormat}"/>'/>

        <c:set var="currentFilterDate" value="${phiz:firstDayOfMonth(phiz:currentDate()).time}"/>
        <c:set var="prevMonth" value="${phiz:addToDate(onDate, 0, -1, 0)}"/>
        <c:set var="prevMonthOnclick" value=""/>
        <c:set var="showPrev" value="${prevMonth >= phiz:addToDate(currentFilterDate, -1, 0, 0)}"/> <%-- ƒанные можно просматривать на год назад --%>
        <c:set var="nextMonth" value="${phiz:addToDate(onDate, 0, 1, 0)}"/>
        <c:set var="nextMonthOnclick" value=""/>
        <c:set var="showNext" value="${nextMonth <= phiz:addToDate(currentFilterDate, 1, 0, 0)}"/> <%-- ƒанные можно просматривать на год вперед --%>

        <c:if test="${showPrev}">
            <c:set var="prevMonthOnclick" value="if(!redirectResolved()) return false; changeMonth('prev');"/>
        </c:if>
        <c:if test="${showNext}">
            <c:set var="nextMonthOnclick" value="if(!redirectResolved()) return false; changeMonth('next');"/>
        </c:if>

        <div class="monthSelector">
            <div class="monthSelectorLeftPart">
                <c:choose>
                    <c:when test="${showPrev}">
                        <div class="monthArrow float">
                            <img src="${globalImagePath}/greenArrowLeft2.gif" border="0" onclick="${prevMonthOnclick}">
                        </div>
                        <div class="monthSelectorPrevMonth">
                            <span class="capitalize" onclick="${prevMonthOnclick}">
                                <fmt:formatDate value="${prevMonth}" pattern="MMMM"/>
                            </span>
                        </div>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </div>
            <div class="monthSelectorCenter">
                <span class="bold capitalize"><fmt:formatDate value="${onDate}" pattern="MMMM"/></span>
                <span><fmt:formatDate value="${onDate}" pattern="yyyy"/></span>
            </div>
            <div class="monthSelectorRightPart">
                <c:choose>
                    <c:when test="${showNext}">
                        <div class="monthArrow floatRight">
                            <img src="${globalImagePath}/greenArrowRight2.gif" border="0" onclick="${nextMonthOnclick}">
                        </div>
                        <div class="monthSelectorNextMonth floatRight">
                            <span class="capitalize" onclick="${nextMonthOnclick}">
                                <fmt:formatDate value="${nextMonth}" pattern="MMMM"/>
                            </span>
                        </div>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
