<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/connectUdbo/connectUdbo">
    <tiles:insert definition="main">
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="mainmenu" value="moreSbol"/>
        <tiles:put name="enabledLink" value="false"/>
        <tiles:put name="data" type="string">
            <%@ include file="udboInfo.jsp"%>
        </tiles:put>
    </tiles:insert>
</html:form>