<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:set var="bundleName" value="commonBundle"/>

<c:set var="form" value="${ShowFinanceStructureForm}"/>
<c:set var="actionSuffix" value="categories"/>
<c:set var="currentSelectedTab" value="operationCategories"/>

<c:choose>
    <c:when test="${form.activePagesCategory eq 'operations'}">
        <c:set var="actionSuffix" value="operations"/>
        <c:set var="currentSelectedTab" value="operations"/>
    </c:when>
    <c:when test="${form.activePagesCategory eq 'financeCalendar'}">
        <c:set var="actionSuffix" value="financeCalendar"/>
        <c:set var="currentSelectedTab" value="financeCalendar"/>
    </c:when>
</c:choose>

<html:form action="/private/finances/show/${actionSuffix}" onsubmit="return setEmptyAction(event)">
    <tiles:insert definition="accountInfo">

        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderWithoutTop" flush="false">
                <tiles:put name="top">
                    <c:set var="selectedTab" value="${currentSelectedTab}"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="financeContainer" flush="false">
                        <tiles:put name="showTitle" value="false"/>
                        <tiles:put name="showFavourite" value="false"/>
                        <tiles:put name="data">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="greenBold"/>
                                <tiles:put name="data">
                                    <bean:message key="message.waiting.claims" bundle="financesBundle"/>
                                </tiles:put>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>
