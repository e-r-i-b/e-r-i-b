<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:set var="month"><bean:write name="form" property="filter(fromDate)" format="MM"/></c:set>
<c:set var="monthString" value="${phiz:dateMonthToString(month)}"/>
<div class="incomeOutgoMonthName">        
    <bean:write name="form" property="filter(fromDate)" format="MMMM yyyy"/>
</div>

<c:set var="showCreditCard" value="${form.filters['showCreditCards']=='on'}"/>

<c:set var="numOfDays" value="${phiz:size(form.monthList)}"/>
<c:forEach items="${form.monthList}" var="item" varStatus="i">
    <c:set var="day" value="${i.count}"/>

    <c:set var="className" value=""/>
    <c:set var="showDay" value="false"/>
    <c:if test="${i.count == 1}">
        <c:set var="className" value="first"/>
        <c:set var="showDay" value="true"/>
    </c:if>
    <c:if test="${i.count == numOfDays}">
        <c:set var="className" value="last"/>
        <c:set var="showDay" value="true"/>
    </c:if>

    <%-- Воскресения выделяем серой линией (воскресенье - 1й день недели) --%>
    <c:if test="${phiz:getDayOfWeek(item.date) == 1 && day > 4 && day <= numOfDays - 4}">
        <c:set var="className" value="sunday"/>
        <c:set var="showDay" value="true"/>
    </c:if>

    <div>
        <tiles:insert definition="incomeOutgoDayTemplate" flush="false">
            <tiles:put name="id" value="day_${day}"/>
            <tiles:put name="incomeValue" value="${item.income}"/>
            <tiles:put name="outcomeValue" value="${item.outcome}"/>
            <tiles:put name="day" value="${day}"/>
            <tiles:put name="month" value="${monthString}"/>
            <tiles:put name="maxValue" value="${maxVal}"/>
            <tiles:put name="className" value="${className}"/>
            <tiles:put name="showDay" value="${showDay}"/>
        </tiles:insert>
    </div>
</c:forEach>

<tiles:insert definition="scaleTemplate" flush="false">
    <tiles:put name="id" value="bottom"/>
    <tiles:put name="maxVal" value="${maxVal}"/>
</tiles:insert>

<c:set var="id" value="Days"/>
<c:set var="date"><bean:write name="form" property="filter(fromDate)" format="dd/MM/yyyy"/></c:set>
<c:set var="openPageDate"><fmt:formatDate value="${form.openPageDate.time}" pattern="dd/MM/yyyy HH:mm:ss"/></c:set>

<div id="Operations${id}" class="operationsListBlock" style="display:none;">
    <input type="hidden" id="incomeOperations${id}" value=""/>
    <input type="hidden" id="cashOperations${id}" value=""/>

    <div class="filter triggerFilter">
        <div class="greenSelector active" id="income_${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'income', '', '${date}', ${showCreditCard}, '${openPageDate}', getElementValue('filter(showOtherAccounts)')); return false;">
           <span>Все операции</span>
        </div>
        <div class="greenSelector transparent" id="income_1${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'income', '1', '${date}', ${showCreditCard}, '${openPageDate}', getElementValue('filter(showOtherAccounts)')); return false;">
           <span>Поступления</span>
        </div>
        <div class="greenSelector transparent" id="income_0${id}" onclick="financesUtils.changeSearchTypeValue('${id}', 'income', '0', '${date}', ${showCreditCard}, '${openPageDate}', getElementValue('filter(showOtherAccounts)')); return false;">
           <span>Списания</span>
        </div>
    </div>

    <div id="listOperations${id}" class="operationsList">
    </div>
</div>

<script type="text/javascript">
    doOnLoad(function()
    {
        financesUtils.showOperations('Operations${id}', '${date}', ${showCreditCard}, '${openPageDate}', getElementValue('filter(showOtherAccounts)'));
    });
</script>
