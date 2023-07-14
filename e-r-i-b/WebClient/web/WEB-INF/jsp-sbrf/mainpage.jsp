<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>

<html:form action="/private/accounts" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="accountInfo">
        <tiles:put name="haveWidgetMainContainer" value="true"/>
        <tiles:put name="mainmenu" value="Info"/>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="showSlidebar" value="true"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="pane" flush="false">
                <tiles:put name="widgetContainerCodename" value="main"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
    
</html:form>