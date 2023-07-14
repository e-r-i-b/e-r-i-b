<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<fmt:setLocale value="ru-RU"/>

<c:set var="currentDate" value="${phiz:currentDate()}"/>
<c:set var="currentYear"><fmt:formatDate value="${currentDate.time}" pattern="yyyy"/></c:set>
<c:set var="currentMonth"><fmt:formatDate value="${currentDate.time}" pattern="MM"/></c:set>
<fmt:parseNumber value="${(currentMonth-1)/3 + 1}" var="currentQuarter" integerOnly="true"/>
<c:set var="minQuarter" value="${currentQuarter + 1}"/>
<c:set var="minYear" value="${currentYear}"/>

<c:if test="${minQuarter > 4}">
    <c:set var="minQuarter" value="1"/>
    <c:set var="minYear" value="${currentYear+1}"/>
</c:if>

<legend><bean:message bundle="pfpBundle" key="label.income.description"/></legend>
<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message bundle="pfpBundle" key="label.income.date"/></tiles:put>
    <tiles:put name="data">
            <tiles:insert definition="scrollTemplate2D" flush="false">
                <tiles:put name="id" value="incomeDate"/>
                <tiles:put name="xMinValue" value="${minYear}"/>
                <tiles:put name="xMaxValue" value="${currentYear + pfptags:getMaxPlanningYear()}"/>
                <tiles:put name="xUnit" value="год"/>
                <tiles:put name="yUnit" value="квартал"/>
                <tiles:put name="xFieldName" value="field(incomeYear)"/>
                <tiles:put name="yFieldName" value="field(incomeQuarter)"/>
                <tiles:put name="yScrollType" value="quarter"/>
                <tiles:put name="xCurrValue" value="${currentYear + pfptags:getDefaultPeriodValue()}"/>
                <tiles:put name="yCurrValue" value="${currentQuarter}"/>
                <tiles:put name="yMinPossibleVal" value="${minQuarter}"/>
                <tiles:put name="yMaxPossibleVal" value="${currentQuarter}"/>
                <tiles:put name="inputHint"><span class="italic"><bean:message key="label.dateScroll.hint" bundle="pfpBundle"/></span></tiles:put>
                <tiles:put name="callback" value="calculateIncomeAmount();"/>
            </tiles:insert>
    </tiles:put>
</tiles:insert>

<div class="pfpBattery">
    <tiles:insert definition="batteryTemplate" flush="false">
        <tiles:put name="id" value="battery"/>
        <tiles:put name="hintVal1" value="Ваш потенциальный доход"/>
        <tiles:put name="hintVal2" value="Вложенные средства"/>
        <tiles:put name="hintVal3" value="Итого"/>
    </tiles:insert>
</div>

<script type="text/javascript">

    var quarterInYear = 4;

    //функция возвращающая период за который необходимо посчитать доходность продукта
    //возвращается количество кварталов
    function getIncomePeriod()
    {
        var incomeDateYear = parseFloatVal($('input[name="field(incomeYear)"]').val());
        var incomeDateQuarter = parseFloatVal($('input[name="field(incomeQuarter)"]').val());
        var incomePeriod = incomeDateYear * quarterInYear + incomeDateQuarter - ${currentYear} * quarterInYear - ${currentQuarter};
        if(incomePeriod < 0)
            incomePeriod = 0;
        return incomePeriod;
    }

</script>
