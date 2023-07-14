<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <!-- ¬кладки -->
    <tiles:insert definition="mainMenu">
        <tiles:importAttribute name="mainmenu" ignore="true"/>
        <tiles:put name="mainmenu" value="${mainmenu}"/>
    </tiles:insert>