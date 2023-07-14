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
<td class="filter" valign="middle">&nbsp;</td>
<td>&nbsp;</td>
<c:if test="${empty colCount || colCount == ''}"><c:set var="colCount" value="3"/></c:if>
<c:if test="${countTd>=colCount}">
	</tr>
	<tr >
	<c:set var="countTd" value="0" scope="request"/>
</c:if>