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

            <sl:collectionItem title="grid.creation.date">
                <sl:collectionItemParam id="value" condition="${not empty(item.creationDate)}">
                    <c:choose>
                        <c:when test="${item.status == 'ACTIVE' || item.status == 'ENTERED' || item.status == 'DRAFT'}">
                            <a href="${baseUrl}&id=${item.id}">
                                <fmt:formatDate value="${item.creationDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${item.creationDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </c:otherwise>
                    </c:choose>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="grid.start.date">
                <sl:collectionItemParam id="value" condition="${not empty(item.startDate)}">
                    <c:if test="${not empty item}">
                        <fmt:formatDate value="${item.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                    </c:if>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="grid.amount.daily">
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