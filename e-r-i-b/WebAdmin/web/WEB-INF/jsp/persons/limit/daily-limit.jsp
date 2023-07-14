<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:useAttribute name="items" scope="page" ignore="true"/>
<tiles:useAttribute name="limitsInfo" scope="page" ignore="true"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id"    value="limitsList"/>
    <tiles:put name="grid">
        <sl:collection id="item" model="list" property="${items}" bundle="limitsBundle" >
            <c:if test="${item != null}">
                <c:set var="accumAmount" value="${phiz:getClientTotalAmountByLimits(limitsInfo, item)}"/>
                <c:set var="accumCount" value="${phiz:getClientOperCountByLimits(limitsInfo, item)}"/>
            </c:if>

            <sl:collectionItem title="label.limit.size">
                <sl:collectionItemParam id="value">
                    <c:if test="${item.amount != null}">
                        <fmt:formatNumber value="${item.amount.decimal}" pattern="###,##0.00"/>
                        <bean:message bundle="limitsBundle" key="grid.currency"/>
                    </c:if>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="label.limit.used">
                <sl:collectionItemParam id="value">
                    <c:if test="${item != null}">
                        <c:choose>
                            <c:when test="${item.restrictionType == 'OPERATION_COUNT_IN_DAY' || item.restrictionType == 'OPERATION_COUNT_IN_HOUR'}">
                                ${accumCount}
                            </c:when>
                            <c:otherwise>
                                <fmt:formatNumber value="${accumAmount.decimal}" pattern="###,##0.00"/>
                                <bean:message bundle="limitsBundle" key="grid.currency"/>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </sl:collectionItemParam>
            </sl:collectionItem>

            <sl:collectionItem title="label.limit.balance">
                <sl:collectionItemParam id="value">
                    <c:if test="${item != null}">
                        <c:choose>
                            <c:when test="${item.restrictionType == 'OPERATION_COUNT_IN_DAY' || item.restrictionType == 'OPERATION_COUNT_IN_HOUR'}">
                                ${item.operationCount - accumCount}
                            </c:when>
                            <c:otherwise>
                                <c:if test="${item.amount.decimal != null && accumAmount != null}">
                                    <fmt:formatNumber value="${item.amount.decimal - accumAmount.decimal}" pattern="###,##0.00"/>
                                    <bean:message bundle="limitsBundle" key="grid.currency"/>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </sl:collectionItemParam>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="emptyMessage" value="Для данного лимита не заданы параметры"/>
</tiles:insert>
