<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute />

<c:if test="${isInitHeadTab == 'true'}">
    <c:set var="tabName" value="${name}" scope="request"/>
</c:if>

<c:if test="${isInitHeadTab == 'true' || isWriteContent == 'true'}">
    <c:set var="tabId" value="${id}" scope="request"/>
</c:if>

<c:if test="${isWriteContent == 'true'}">
    <div id="${id}">
        <div class="slider_div">
            ${content}
        </div>
    </div>
</c:if>
