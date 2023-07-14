<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/firstConnectUDBO" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="login">
        <tiles:put name="pageTitle" type="string" value="Сбербанк Онлайн"/>
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="needHelp" value="true"/>
        <tiles:put name="data" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/private/connectUdbo/udboInfo.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>