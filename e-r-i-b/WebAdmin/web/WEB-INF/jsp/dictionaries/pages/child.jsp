<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<c:set var="form" value="${ListPagesForm}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="parentId"><bean:write name="form" property="filter(parentId)"/></c:set>

<c:if test="${not empty form.data}">
<%-- костыль для IE, когда делаем ajaxQuery IE хочет в начале ответа видеть &nbsp; --%>
<c:if test="${not param.nonbsp}">&nbsp;</c:if>

<c:forEach items="${form.data}" var="child">
    <tr class="tree">
        <td>
            &nbsp;
            <c:choose>
                <c:when test="${empty child.url}">
                    <input id="id${child.id}" type="hidden" name="selectedIds" value="${child.id}"/>
                </c:when>
                <c:otherwise>
                    <input id="id${child.id}" type="checkbox" name="selectedIds" value="${child.id}" style="border:none;" onclick="selectPage(${child.id});"/>
                </c:otherwise>
            </c:choose>
            <input type="hidden" name="name${child.id}" value="${child.name}"/>

            <c:set var="pageId" value="${child.id}"/>
            <c:set var="name"><bean:write name="form" property="filter(name)"/></c:set>
            <c:choose>
                <c:when test="${empty child.url}">
                    <a href='#${pageId}' onclick="showChild('${pageId}');" class="imgA">
                        <img id="img${pageId}" class="openClose" src="${imagePath}/plus.gif" alt="" border="0">
                    </a>

                    <a href='#${pageId}' onclick="showChild('${pageId}')"><span class="page">${child.name}</span></a>

                    <a class="selectChild">
                        <span id="selectChild${pageId}" class="page" onclick="selectPage(${pageId});">выбрать все</span>
                        <span id="unselectChild${pageId}" class="page" style="display: none;" onclick="selectPage(${pageId});">очистить</span>
                    </a>
                </c:when>
                <c:otherwise>
                    ${child.name}
                </c:otherwise>
            </c:choose>
            <table  id="${pageId}" style="display:none;" class="pageTree">
                <tbody>
                </tbody>
            </table>
        </td>
    </tr>
</c:forEach>
 </c:if>
