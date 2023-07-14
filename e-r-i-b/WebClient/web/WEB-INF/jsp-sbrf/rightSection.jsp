<%--
  Created by IntelliJ IDEA.
  User: Nechaeva
  Date: 08.005.2010
  Time: 15:47:52
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/widget-tags" prefix="widget" %>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="faqLink" value="${phiz:calculateActionURL(pageContext, '/faq.do')}"/>
<c:if test="${rightSection == 'true'}">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:choose>
        <c:when test="${widget:isAvailableWidget() && !widget:isOldBrowser()}">
            <div id="right-section">
                <tiles:insert definition="pane" flush="false">
                    <tiles:put name="widgetContainerCodename" value="sidemenu"/>
                </tiles:insert>
            </div>
        </c:when>
        <%--Блок с банером для Гостевого входа--%>
        <c:when test="${showGuestEntryBanner ==  'true'}">
            <tiles:insert page="/WEB-INF/jsp-sbrf/guestEntry/guestEntryRightColumn.jsp"/>
            <div id="right-section">
                <div>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/helpSection.jsp"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div id="right-section">
                <!-- Личное меню-->
                <tiles:insert page="/WEB-INF/jsp-sbrf/personalMenu.jsp"/>
                <div>
                    <tiles:insert page="/WEB-INF/jsp-sbrf/helpSection.jsp"/>
                </div>
                <!-- Начало Обмен валют -->
                <c:if test="${showRates == 'true' && phiz:impliesService('CurrenciesRateInfo')}">
                    <c:set var="currentDepartment4Rate" scope="request" value="${phiz:getDepartmentForCurrentClient()}"/>
                    <c:if test="${phiz:isRateAccess(currentDepartment4Rate)}">
                        <div class="rightSectionLastBlock">
                            <div class="currencyRate">
                                <%@ include file="/WEB-INF/jsp-sbrf/currencyRate.jsp" %>
                            </div>
                        </div>
                    </c:if>
                </c:if>
                <!-- Конец Обмен валют -->
            </div>
        </c:otherwise>
    </c:choose>
</c:if>