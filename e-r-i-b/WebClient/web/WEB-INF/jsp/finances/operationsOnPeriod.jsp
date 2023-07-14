<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>

<c:forEach items="${form.monthList}" var="item" varStatus="i">
    <div <c:if test="${i.count > 1}">class="dottedBorderTop"</c:if>>
        <tiles:insert definition="incomeOutgoTemplate" flush="false">
            <tiles:put name="id" value="month_${i.count}"/>
            <tiles:put name="incomeVal" value="${item.income}"/>
            <tiles:put name="outcomeVal" value="${item.outcome}"/>
            <tiles:put name="title"><fmt:formatDate value="${item.date.time}" pattern="MMMM yyyy"/></tiles:put>
            <tiles:put name="maxValue" value="${maxVal}"/>
            <tiles:put name="functionParams">
                '<fmt:formatDate value="${item.date.time}" pattern="dd/MM/yyyy"/>',
                ${form.filters['showCreditCards']=='on'},
                '<fmt:formatDate value="${form.openPageDate.time}" pattern="dd/MM/yyyy HH:mm:ss"/>'
            </tiles:put>
        </tiles:insert>
    </div>
</c:forEach>

<tiles:insert definition="scaleTemplate" flush="false">
    <tiles:put name="id" value="bottom"/>
    <tiles:put name="maxVal" value="${maxVal}"/>
</tiles:insert>

