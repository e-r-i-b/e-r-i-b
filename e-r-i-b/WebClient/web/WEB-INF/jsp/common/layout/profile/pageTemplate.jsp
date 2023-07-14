<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<div id="userProfile">
    <div id="profileLeftMenu">
        <%@ include file="/WEB-INF/jsp/private/newProfile/leftMenu.jsp"%>
    </div>
    <div id="profileWorkspace">
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="title" value="${title}"/>
            <tiles:put name="data">${data}</tiles:put>
        </tiles:insert>
    </div>
</div>