<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<tiles:useAttribute name="items"            scope="page" ignore="true"/>
<tiles:useAttribute name="selectedBeanName" scope="page" ignore="true"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id"    value="limitsList"/>
    <tiles:put name="grid">

        <sl:collection id="item" model="list" property="${items}" bundle="${bundle}" assignedPaginationSizes="20;50;100" assignedPaginationSize="20">
            <sl:collectionParam id="selectType"     value="checkbox"/>
            <sl:collectionParam id="selectProperty" value="id"/>
            <sl:collectionParam id="selectName"     value="${selectedBeanName}"/>

            <sl:collectionItem title="grid.performance.date">
                <sl:collectionItemParam id="value">
                    <a href="${baseUrl}?id=${item.id}">
                        <c:if test="${not empty item.startDate}">
                            <fmt:formatDate value="${item.startDate.time}" pattern="dd.MM.yyyy"/> -
                        </c:if>
                        <c:if test="${not empty item.endDate}">
                            <fmt:formatDate value="${item.endDate.time}" pattern="dd.MM.yyyy"/>
                        </c:if>
                    </a>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="grid.amount.overall">
                <c:if test="${not empty item}">
                    <nobr>
                        <bean:write name="item" property="amount.decimal" format="###,##0.00"/>
                        <bean:message bundle="${bundle}" key="grid.currency"/>
                    </nobr>
                </c:if>
            </sl:collectionItem>

            <sl:collectionItem title="grid.status">
                <c:if test="${not empty item}">
                    <bean:message bundle="${bundle}" key="filter.status.${item.status}"/>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
        <tiles:put name="isEmpty" value="${empty items}"/>
    </tiles:put>
    <tiles:put name="emptyMessage" value="Не найдено ни одного лимита, соответствующего заданному фильтру"/>
</tiles:insert>
<br/>