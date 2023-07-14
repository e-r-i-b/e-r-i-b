<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/guest/sberbankForEveryDay" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="isLogOnGuestProfile" value="${phiz:isLogOnGuestProfile()}"/>
    <c:choose>
        <c:when test="${isLogOnGuestProfile}">
            <c:set var="definition" value="guestPage"/>
        </c:when>
        <c:otherwise>
            <c:set var="definition" value="guestExpressClaim"/>
        </c:otherwise>
    </c:choose>
    <tiles:insert definition="${definition}">
        <tiles:put name="pageTitle" ><bean:message key="label.title" bundle="sbnkdBundle"/></tiles:put>
        <tiles:put name="phone" >${form.issueCardDoc.phone}</tiles:put>

        <tiles:put name="mainMenuType" value="guestMainMenu"/>
        <tiles:put name="mainmenu" value="Index"/>

        <tiles:put name="data" type="string">
            <%@ include file="confirmClaim.jsp" %>
        </tiles:put>
    </tiles:insert>

</html:form>




