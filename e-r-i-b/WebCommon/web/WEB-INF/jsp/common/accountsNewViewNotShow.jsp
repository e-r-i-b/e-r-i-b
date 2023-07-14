<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>

<tiles:importAttribute/>

<td class="align-center checkboxColumnEmpty">
    <div class="products-text-style">
        <c:set var="spanName" value="${selectdIds}${listItem.id}"/>
        <c:if test="${not empty confirmRequest}">
            <html:multibox name="form" property="${selectdIds}" styleId="${spanName}" onclick="showDescSys('${spanName}');" value="${listItem.id}" style="display:none"/>
        </c:if>
        <c:set var="label">
            <c:choose>
                <c:when test="${typeView eq 'inSystem'}">${listItem.showInSystem}</c:when>
                <c:when test="${typeView eq 'inMobile'}">${listItem.showInMobile}</c:when>
                <c:when test="${typeView eq 'inSocial'}">${listItem.showInSocial}</c:when>
                <c:when test="${typeView eq 'inES'}">${listItem.showInATM}</c:when>
                <c:when test="${typeView eq 'inSMS'}">${listItem.showInSms}</c:when>
            </c:choose>
        </c:set>
        <html:multibox name="form" property="${selectdIds}" styleId="${spanName}"  onclick="${functionShowDesc}('${spanName}');" value="${listItem.id}" disabled="${not empty confirmRequest}" style="display:none"/>
        <span class="${spanName}"><label for="${spanName}"></label></span>
    </div>
</td>