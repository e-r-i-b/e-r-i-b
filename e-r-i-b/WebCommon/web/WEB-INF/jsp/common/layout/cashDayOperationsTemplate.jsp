<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--
Компонент для отображения доходов и расходов за месяц
id - идентификатор блока, обязателен для заполнения, если таких компонентов на странице несколько
cashIncome - доход за день наличными
cashOutcome - расход за день наличными
cashlessIncome - доход за день по безналичному расчету
cashlessOutcome - расход за день по безналичному расчету
day - день месяца
month - название месяца
showDay - отобразить номер дня месяца
className - класс
--%>

<c:set var="formatOutgoCash" value="${phiz:getStringInNumberFormat(cashOutcome)}"/>
<c:set var="formatIncomeCash" value="${phiz:getStringInNumberFormat(cashIncome)}"/>
<c:set var="formatOutgoCashless" value="${phiz:getStringInNumberFormat(cashlessOutcome)}"/>
<c:set var="formatIncomeCashless" value="${phiz:getStringInNumberFormat(cashlessIncome)}"/>
<c:set var="dateString" value="${day} ${month}"/>
<script type="text/javascript">
    $(document).ready(function(){
        financesUtils.setCashDayVals('${id}', ${cashIncome}, ${cashOutcome}, ${cashlessIncome}, ${cashlessOutcome}, ${maxValue});

        financesUtils.bindEnter('outgoCashDayLine${id}', '${dateString}', '-${formatOutgoCash}');
        financesUtils.bindEnter('outgoCashlessDayLine${id}', '${dateString}', '-${formatOutgoCashless}');
        financesUtils.bindEnter('incomeCashDayLine${id}', '${dateString}', '+${formatIncomeCash}');
        financesUtils.bindEnter('incomeCashlessDayLine${id}', '${dateString}', '+${formatIncomeCashless}');

        financesUtils.bindLeave('outgoCashDayLine${id}');
        financesUtils.bindLeave('outgoCashlessDayLine${id}');
        financesUtils.bindLeave('incomeCashDayLine${id}');
        financesUtils.bindLeave('incomeCashlessDayLine${id}');
    });
</script>

<c:set var="addClass" value=""/>
<c:if test="${not empty className}">
    <c:set var="addClass" value="${className}CashDayLabel"/>    
</c:if>
<div class="relative">
    <div class="cashDayLabel ${addClass}">
        <c:if test="${showDay}">
            <c:if test="${day < 10}"><c:set var="day" value="0${day}"/></c:if>
            ${day}
        </c:if>
    </div>
    <div id="cashDay${id}" class="cashDay ${className}">
        <div class="leftPart">
            <div class="leftGraphPart">
                <div class="outgoCashDayLine relative">
                    <div id="outgoCashDayLine${id}">
                        <div class="outgoCashDayLineLeft"></div>
                        <div class="lineCenter"></div>
                    </div>
                </div>
                <div class="clear"></div>

                <div class="outgoCashlessDayLine relative">
                    <div id="outgoCashlessDayLine${id}">
                        <div class="outgoCashlessDayLineLeft"></div>
                        <div class="lineCenter"></div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>

        <div class="partSeparator"></div>

        <div class="rightPart">
            <div class="rightGraphPart">
                <div class="incomeCashDayLine float relative">
                    <div id="incomeCashDayLine${id}">
                        <div class="incomeCashDayLineRight"></div>
                        <div class="lineCenter"></div>
                    </div>
                </div>
                <div class="clear"></div>

                <div class="incomeCashlessDayLine float relative">
                    <div id="incomeCashlessDayLine${id}">
                        <div class="incomeCashlessDayLineRight"></div>
                        <div class="lineCenter"></div>
                    </div>
                </div>
                <div class="clear"></div>
             </div>
        </div>

        <div class="clear"></div>
    </div>
</div>
