<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form"  value="${ListRegionsForm}"/>
<c:set var="id"    value="${form.id}"/>
<c:set var="region" value="${phiz:findRegionById(id)}"/>
<c:set var="issue"  value="${phiz:getRegionChildren(region)}"/>

var child;
var children = new Array();

<logic:iterate id="item" name="issue">
    child      = new Object();
    child.id   = '${item.id}';
    child.name = '${item.name}';
    child.code = '${item.code}';
    <c:set var="grandchildren" value="${phiz:getRegionChildren(item)}"/>
    <c:choose>
        <c:when test="${not empty grandchildren}">
            child.hasChild = '1';
        </c:when>
        <c:otherwise>
            child.hasChild = '0';
        </c:otherwise>
    </c:choose>
    children[children.length] = child;
</logic:iterate>
parent.addRow(${id}, children);