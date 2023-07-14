<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insert definition="main">
    <tiles:put name="pageTitle" type="string">Ошибка</tiles:put>
    <tiles:put name="needShowMessages" value="false"/>
    <tiles:put name="data" type="string">

        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <c:set var="inactiveESMessages" value="${not empty inactiveESMessages ? inactiveESMessages : ''}"/>
        <c:out value="${inactiveESMessages}"/>
        <phiz:messages id="inactiveES" bundle="commonBundle" field="field" message="inactiveExternalSystem">
            <c:set var="inactiveESMessages">${inactiveESMessages}<div class = "itemDiv">${phiz:processBBCode(inactiveES)} </div></c:set>
        </phiz:messages>

        <c:set var="inactiveESMessagesLength" value="${fn:length(inactiveESMessages)}"/>
        <tiles:insert definition="inactiveESMessagesBlock" flush="false">
            <tiles:put name="isDisplayed" value="${inactiveESMessagesLength gt 0 ? true : false}"/>
            <tiles:put name="data">
                <bean:write name='inactiveESMessages' filter='false'/>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
