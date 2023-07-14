<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:importAttribute ignore="true"/>

<%--
Узел иерархии фильтра карт
element - объект типа com.rssl.phizic.business.tree.TreeElement
--%>
<value>${element.id}</value>
<label><c:out value="${element.name}"/></label>
<c:if test="${not element.leaf and not empty element.list}">
    <sl:collection id="child" name="element" property="list" model="xml-list" title="nodeList">
        <sl:collectionItem title="node">
            <tiles:insert page="treeNode.jsp" flush="false">
                <tiles:put name="element" beanName="child"/>
            </tiles:insert>
        </sl:collectionItem>
    </sl:collection>
</c:if>