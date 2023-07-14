<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/persons/limits" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="personEdit">
        <c:set var="subMenu">
            <c:choose>
                <c:when test="${form.type == 'INTERNET_CLIENT'}">UserLimitsMain</c:when>
                <c:when test="${form.type == 'MOBILE_API'}">UserLimitsMobile</c:when>
                <c:when test="${form.type == 'SOCIAL_API'}">UserLimitsSocial</c:when>
                <c:when test="${form.type == 'SELF_SERVICE_DEVICE'}">UserLimitsSelfServiceDevice</c:when>
                <c:when test="${form.type == 'ERMB_SMS'}">UserLimitsErmb</c:when>
                <c:when test="${form.type == 'ALL'}">UserLimitsOverallAmountPerDay</c:when>
            </c:choose>
        </c:set>
        <tiles:put name="submenu" type="string" value="${subMenu}"/>
        <tiles:put name="data" type="string">
            <c:choose>
                <c:when test="${form.type == 'ALL'}">
                    <div class="pageTitle">
                        <bean:message key="overallPerDay.title" bundle="limitsBundle"/>
                    </div>

                    <tiles:insert page="/WEB-INF/jsp/persons/limit/daily-limit.jsp" flush="false">
                        <tiles:put name="items"      value="overallAmountPerDayLimits"/>
                        <tiles:put name="limitsInfo" beanName="form" beanProperty="limitsInfo"/>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <div class="pageTitle">
                        <bean:message key="page.title.securityType" bundle="limitsBundle"/>
                        &nbsp;-&nbsp;
                        <bean:message key="page.title.securityType.${form.activePerson.securityType}" bundle="limitsBundle"/>
                    </div>
                    <h2><bean:message key="userlimits.page.title" bundle="limitsBundle"/></h2>

                    <%--заградительный суточный лимит--%>
                    <h3><bean:message bundle="limitsBundle" key="obstruction.title"/></h3>
                    <tiles:insert page="/WEB-INF/jsp/persons/limit/daily-limit.jsp" flush="false">
                        <tiles:put name="items" value="obstructionLimits"/>
                        <tiles:put name="limitsInfo" beanName="form" beanProperty="limitsInfo"/>
                    </tiles:insert>

                    <%--имси лимит--%>
                    <h3><bean:message bundle="limitsBundle" key="imsi.title"/></h3>
                    <tiles:insert page="/WEB-INF/jsp/persons/limit/daily-limit.jsp" flush="false">
                        <tiles:put name="items" value="imsiLimits"/>
                        <tiles:put name="limitsInfo" beanName="form" beanProperty="limitsInfo"/>
                    </tiles:insert>

                    <%--лимиты по группам риска--%>
                    <c:forEach var="groupRisk" items="${form.groupRiskLimitsMap}">
                        <h3><c:out value="${groupRisk.value.first}"/></h3>
                        <c:choose>
                            <c:when test="${not empty groupRisk.value.second}">
                                <tiles:insert definition="tableTemplate" flush="false">
                                    <tiles:put name="id"    value="limitsList"/>
                                    <tiles:put name="grid">
                                        <sl:collection id="userLimit" model="list" name="groupRisk" property="value.second" bundle="limitsBundle">
                                            <c:if test="${userLimit != null}">
                                                <c:set var="accumAmount" value="${phiz:getClientTotalAmountByLimits(form.limitsInfo, userLimit)}"/>
                                                <c:set var="accumCount" value="${phiz:getClientOperCountByLimits(form.limitsInfo, userLimit)}"/>
                                            </c:if>

                                            <sl:collectionItem title="label.limit.name">
                                                <sl:collectionItemParam id="value">
                                                    <bean:message key="label.limit.type.${userLimit.restrictionType}" bundle="limitsBundle"/>
                                                </sl:collectionItemParam>
                                            </sl:collectionItem>
                                            <sl:collectionItem title="label.limit.sizecount">
                                                <sl:collectionItemParam id="value">
                                                    <c:choose>
                                                        <c:when test="${userLimit.restrictionType == 'AMOUNT_IN_DAY' || userLimit.restrictionType == 'MIN_AMOUNT'}">
                                                            <c:if test="${userLimit.amount != null}">
                                                                <fmt:formatNumber value="${userLimit.amount.decimal}" pattern="###,##0.00"/>
                                                                <bean:message bundle="limitsBundle" key="grid.currency"/>
                                                            </c:if>
                                                        </c:when>
                                                        <c:when test="${userLimit.restrictionType == 'OPERATION_COUNT_IN_DAY' || userLimit.restrictionType == 'OPERATION_COUNT_IN_HOUR'}">
                                                            <c:out value="${userLimit.operationCount}"/>
                                                            <c:if test="${userLimit.operationCount != null}">
                                                                <c:out value=" операций"/>
                                                            </c:if>
                                                        </c:when>
                                                    </c:choose>
                                                </sl:collectionItemParam>
                                            </sl:collectionItem>
                                            <sl:collectionItem title="label.limit.used">
                                                <c:choose>
                                                    <c:when test="${userLimit.restrictionType == 'AMOUNT_IN_DAY' || userLimit.restrictionType == 'MIN_AMOUNT'}">
                                                        <c:if test="${accumAmount != null}">
                                                            <fmt:formatNumber value="${accumAmount.decimal}" pattern="###,##0.00"/>
                                                            <bean:message bundle="limitsBundle" key="grid.currency"/>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${userLimit.restrictionType == 'OPERATION_COUNT_IN_DAY' || userLimit.restrictionType == 'OPERATION_COUNT_IN_HOUR'}">
                                                        <c:if test="${accumCount != null}">
                                                            <c:out value="${accumCount}"/>
                                                            <c:out value=" операций"/>
                                                        </c:if>
                                                    </c:when>
                                                </c:choose>
                                            </sl:collectionItem>
                                            <sl:collectionItem title="label.limit.balance">
                                                <c:choose>
                                                    <c:when test="${userLimit.restrictionType == 'AMOUNT_IN_DAY' || userLimit.restrictionType == 'MIN_AMOUNT'}">
                                                        <c:if test="${userLimit.amount != null && accumAmount != null}">
                                                            <fmt:formatNumber value="${userLimit.amount.decimal - accumAmount.decimal}" pattern="###,##0.00"/>
                                                            <bean:message bundle="limitsBundle" key="grid.currency"/>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${userLimit.restrictionType == 'OPERATION_COUNT_IN_DAY' || userLimit.restrictionType == 'OPERATION_COUNT_IN_HOUR'}">
                                                        <c:if test="${userLimit.operationCount != null && accumCount != null}">
                                                            <c:out value="${userLimit.operationCount - accumCount}"/>
                                                            <c:out value=" операций"/>
                                                        </c:if>
                                                    </c:when>
                                                </c:choose>
                                            </sl:collectionItem>
                                        </sl:collection>
                                    </tiles:put>
                                </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="roundBorderLight" flush="false">
                                    <tiles:put name="color" value="redBlock"/>
                                    <tiles:put name="data">
                                        <c:out value="Для данной группы не заданы параметры"/>
                                    </tiles:put>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>