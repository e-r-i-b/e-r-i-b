<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form"  value="${ListRegionsForm}"/>
<c:set var="id"    value="${form.id}"/>
<c:set var="region" value="${phiz:findRegionById(id)}"/>
<c:set var="issue"  value="${phiz:getRegionChildren(region)}"/>

<c:forEach items="${issue}" var="region">
    <tr class="tree">
        <td>
            &nbsp;
            <c:set var="children" value="${phiz:getRegionChildren(region)}"/>
            <input type="checkbox" name="selectedIds" value="${region.id}"  style="border:none;"/>
            <input type="hidden" name="name${region.id}" value="${region.name}"/>
            <c:choose>
                <c:when test="${not empty children}">
                    <a id="parent${region.id}" href="javascript:showChild('${region.id}')">
                        ${region.name} (${region.synchKey})
                    </a>
                </c:when>
                <c:otherwise>
                    ${region.name} (${region.synchKey})
                </c:otherwise>
            </c:choose>
            <table  id="${region.id}" style="display:none; margin-left: 2em;height:auto;">
                <tbody>
                </tbody>
            </table>
        </td>
    </tr>
</c:forEach>
