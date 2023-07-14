<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>

<%--"selectedTab" - назначать активную вкладку--%>
<div class="mobileAppsHeader">
<tiles:insert definition="paymentTabs" flush="false">
    <tiles:put name="tabItems">
        <tiles:insert definition="paymentTabItem" flush="false">
            <tiles:put name="position" value="first-last"/>
            <tiles:put name="active" value="${selectedTab == 'mobileApp'}"/>
            <tiles:put name="title" value="ѕодключение"/>
        </tiles:insert>
    </tiles:put>
    <%--пока одна страница, потом помен€ть, если стр. прибав€тс€--%>
    <tiles:put name="count" value="1"/>
</tiles:insert>
</div>