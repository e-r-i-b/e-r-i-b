<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 22.08.2008
  Time: 9:49:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="countTd" value="${countTd+1}" scope="request"/>
<tiles:importAttribute/>
<td class="filter" valign="middle">
    <span class="${isFastSearch == 'true' ? 'fastSearch' : ''}">
    <c:if test="${not empty label}">
		<bean:message key="${label}" bundle="${bundle}"/>
		<c:if test="${mandatory==true}">
			<span class="filterAsterisk" style="cursor:default;">*</span>
		</c:if>
    </c:if>
    </span>
</td>
<td>
	<span class="${isFastSearch == 'true' ? 'fastSearch' : ''}" style="font-weight:normal;cursor:default;">

        <c:set var="fieldClass" value=""/>
        <c:set var="fieldClass2" value=""/>
        <c:if test="${needClearing != 'true'}">
            <c:set var="fieldClass" value="${fieldClass} notCleaningField"/>
            <c:set var="fieldClass2" value="${fieldClass} notCleaningField"/>
        </c:if>
        <c:if test="${not empty isDefault}">
            <c:set var="fieldClass" value="${fieldClass} customPlaceholder"/>
        </c:if>
        <c:if test="${not empty default2}">
            <c:set var="fieldClass2" value="${fieldClass2} customPlaceholder"/>
        </c:if>

        <c:if test="${not empty label}">
            <c:out value="${prev}"/>
            <html:text property="filter(${name})" size="${size}" maxlength="${maxlength}" readonly="${not editable}" styleClass="${fieldClass}" title="${isDefault}"/>
            &nbsp;
            <c:out value="${prev2}"/>
            <html:text property="filter(${name2})" size="${size2}" maxlength="${maxlength2}" readonly="${not editable2}" styleClass="${fieldClass2}" title="${default2}"/>
        </c:if>

        <%-- Маски ввода--%>
        <c:if test="${not empty template}">
            <script type="text/javascript">
                $(document).ready(function(){
                    $("input[name=filter(${name})]").createMask(${template});
                });
            </script>
        </c:if>
        <c:if test="${template2!=''}">
            <script type="text/javascript">
                 $(document).ready(function(){
                     $("input[name=filter(${name2})]").createMask(${template2});
                 });
             </script>
        </c:if>
	</span>
</td>
<c:if test="${empty colCount || colCount == ''}"><c:set var="colCount" value="3"/></c:if>
<c:if test="${countTd>=colCount}">
	</tr>
	<tr >
	<c:set var="countTd" value="0" scope="request"/>
</c:if>