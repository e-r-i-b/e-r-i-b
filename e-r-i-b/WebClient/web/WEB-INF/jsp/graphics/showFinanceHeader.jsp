<%--
  Created by IntelliJ IDEA.
  User: kichinova
  Date: 04.12.2012
  Time: 09:35:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<c:set var="operationsServiceAvailable" value="${phiz:impliesService('FinanceOperationsService')}"/>
<c:set var="categoriesServiceAvailable" value="${phiz:impliesService('CategoriesCostsService')}"/>
<c:set var="targetsServiceAvailable" value="${phiz:impliesService('TargetsService')}"/>
<c:set var="financeCalendarServiceAvailable" value="${phiz:impliesService('FinanceCalendarService')}"/>
<c:set var="viewFinanceAvailable" value="${phiz:impliesService('ViewFinance')}"/>
<c:set var="clientPfpEditServiceAvailable" value="${phiz:hasAccessToPFP()}"/>
<c:set var="personalFinanceUsed" value="${phiz:isPersonalFinanceEnabled()}"/>
<c:set var="addFinanceOperationServiceAvailable" value="${phiz:impliesService('AddFinanceOperationsService')}"/>

<span class="myFinanceHeaderTitle"> Мои финансы</span>
<tiles:insert definition="formHeader" flush="false">
    <tiles:put name="image" value="${globalUrl}/commonSkin/images/icon_pie.jpg" />
    <tiles:put name="description">
       <c:choose>
          <c:when test="${clientPfpEditServiceAvailable && (((operationsServiceAvailable || categoriesServiceAvailable) && (personalFinanceUsed || addFinanceOperationServiceAvailable)) || targetsServiceAvailable)}">
             На этой странице Вы можете посмотреть структуру Ваших денежных средств на вкладах, картах и других продуктах и выполнить анализ расходов по разным категориям. Также с помощью финансового планирования Вы сможете подобрать наиболее подходящие для Вас продукты.
          </c:when>
          <c:when test="${!clientPfpEditServiceAvailable && (((operationsServiceAvailable || categoriesServiceAvailable) && (personalFinanceUsed || addFinanceOperationServiceAvailable)) || targetsServiceAvailable)}">
             На этой странице Вы можете посмотреть структуру Ваших денежных средств на вкладах, картах и других продуктах, а также выполнить анализ расходов по разным категориям.
          </c:when>
          <c:otherwise>
             Финансовое планирование способствует эффективному распределению Ваших денежных средств. В результате анализа Ваших финансовых целей мы поможем подобрать наиболее выгодные для Вас продукты.
          </c:otherwise>
       </c:choose>
    </tiles:put>
</tiles:insert>
<div class="myFinanceBlock">
    <tiles:insert definition="paymentTabs" flush="false">
        <c:set var="itemsCount" value="0"/>
        <tiles:put name="tabItems">
            <c:if test="${categoriesServiceAvailable && ( personalFinanceUsed || addFinanceOperationServiceAvailable )}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="${selectedTab == 'operationCategories'}"/>
                        <c:choose>
                            <c:when test="${phiz:impliesService('UseWebAPIService')}">
                                <tiles:put name="url" value="${phiz:getWebAPIUrl('operation.categories')}"/>
                            </c:when>
                            <c:otherwise>
                                <tiles:put name="action" value="/private/finances/operationCategories.do"/>
                            </c:otherwise>
                        </c:choose>
                    <tiles:put name="title" value="Расходы"/>
                    <c:set var="itemsCount" value="${itemsCount + 1}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${operationsServiceAvailable && ( personalFinanceUsed || addFinanceOperationServiceAvailable )}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="${selectedTab == 'operations'}"/>
                    <c:choose>
                        <c:when test="${phiz:impliesService('UseWebAPIService')}">
                            <tiles:put name="url" value="${phiz:getWebAPIUrl('operations')}"/>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="action" value="/private/finances/operations.do"/>
                        </c:otherwise>
                    </c:choose>
                    <tiles:put name="title" value="Операции"/>
                    <c:set var="itemsCount" value="${itemsCount + 1}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${viewFinanceAvailable}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="${selectedTab == 'myFinance'}"/>
                    <c:choose>
                        <c:when test="${phiz:impliesService('UseWebAPIService')}">
                            <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                        </c:when>
                        <c:otherwise>
                            <tiles:put name="action" value="/private/graphics/finance.do"/>
                        </c:otherwise>
                    </c:choose>
                    <tiles:put name="title" value="Доступные средства"/>
                    <c:set var="itemsCount" value="${itemsCount + 1}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${targetsServiceAvailable}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="${selectedTab == 'myTargets'}"/>
                    <tiles:put name="action" value="/private/finances/targets/targetsList.do"/>
                    <tiles:put name="title" value="Мои цели"/>
                    <c:set var="itemsCount" value="${itemsCount + 1}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${financeCalendarServiceAvailable}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="active" value="${selectedTab == 'financeCalendar'}"/>
                    <tiles:put name="action" value="/private/finances/financeCalendar.do"/>
                    <tiles:put name="title" value="Календарь"/>
                    <c:set var="itemsCount" value="${itemsCount + 1}"/>
                </tiles:insert>
            </c:if>
            <c:if test="${clientPfpEditServiceAvailable}">
                <tiles:insert definition="paymentTabItem" flush="false">
                    <tiles:put name="largeText" value="true"/>
                    <tiles:put name="active" value="${selectedTab == 'pfp'}"/>
                    <tiles:put name="action" value="/private/pfp/edit.do"/>
                    <tiles:put name="title" value="Финансовое планирование"/>
                    <c:set var="itemsCount" value="${itemsCount + 1}"/>
                </tiles:insert>
            </c:if>
        </tiles:put>
        <tiles:put name="count" value="${itemsCount}"/>
    </tiles:insert>
</div>