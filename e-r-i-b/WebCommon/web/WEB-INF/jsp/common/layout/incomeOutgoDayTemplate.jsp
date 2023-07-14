<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
Компонент для отображения доходов и расходов за день
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
incomeValue - доход за день
outcomeValue - расход за день
day - день месяца
month - название месяца
showDay - отобразить номер дня месяца
className - класс
--%>

<c:set var="formatOutgo" value="${phiz:getStringInNumberFormat(outcomeValue)}"/>
<c:set var="formatIncome" value="${phiz:getStringInNumberFormat(incomeValue)}"/>
<c:set var="title" value="${day} ${month}"/>
<script type="text/javascript">
    $(document).ready(function(){
        financesUtils.setIncomeOutgoDayVals('${id}', ${incomeValue}, ${outcomeValue}, ${maxValue});

        financesUtils.bindEnter('outgoDayLine${id}', '${title}', '-${formatOutgo}');
        financesUtils.bindEnter('incomeDayLine${id}', '${title}', '+${formatIncome}');

        financesUtils.bindLeave('outgoDayLine${id}');
        financesUtils.bindLeave('incomeDayLine${id}');
    });
</script>

<c:set var="addClass" value=""/>
<c:if test="${not empty className}">
    <c:set var="addClass" value="${className}IncomeOutgoDayLabel"/>
</c:if>
<div class="relative">
    <div class="incomeOutgoDayLabel ${addClass}">
        <c:if test="${showDay}">
            <c:if test="${day < 10}"><c:set var="day" value="0${day}"/></c:if>
            ${day}
        </c:if>
    </div>
    <div id="incomeOutgoDay${id}" class="incomeOutgoDay ${className}">
        <div class="leftPart">
            <div class="leftGraphPart">
                <div id="outgoDayLine${id}" class="outgoDayLine relative">
                    <div class="outgoDayLineLeft"></div>
                    <div class="lineCenter"></div>
                </div>

                <div class="clear"></div>
            </div>
        </div>

        <div class="partSeparator"></div>

        <div class="rightPart">
            <div class="rightGraphPart">
                <div id="incomeDayLine${id}" class="incomeDayLine float relative">
                    <div class="incomeDayLineRight"></div>
                    <div class="lineCenter"></div>
                </div>

                <div class="clear"></div>
             </div>
        </div>

        <div class="clear"></div>
    </div>
</div>
