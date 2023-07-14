<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 22.08.2008
  Time: 15:38:10
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

<tiles:importAttribute/>
<c:if test="${time==true}">
	<c:if test="${countTd==2}">
		<td colspan="2">&nbsp;</td>
		</tr>
		<tr class="filter">
		<c:set var="countTd" value="0" scope="request"/>
	</c:if>
	<c:set var="countTd" value="${countTd+2}" scope="request"/>
</c:if>
<c:if test="${time!=true}">
	<c:set var="countTd" value="${countTd+1}" scope="request"/>
</c:if>
<td class="filter" valign="middle">
		<bean:message key="${label}" bundle="${bundle}"/>
		<c:if test="${mandatory==true}">
			<span class="filterAsterisk" style="cursor:default;">*</span>
		</c:if>
		&nbsp;
</td>
<c:if test="${time==true}">
	<td colspan="3"><nobr>
</c:if>
<c:if test="${time==false}">
	<td><nobr>
</c:if>
			с&nbsp;
		<span style="font-weight:normal;overflow:visible;cursor:default;">
            <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${name})" format="dd.MM.yyyy"/>'
                   name="filter(from${name})" class="${className}"
                   size="10"/>
			<c:if test="${time==true}">
                <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(fromTime)" format="HH:mm:ss"/>'
                       name="filter(fromTime)"
                       class="time-template"
                       onkeydown="onTabClick(event,'filter(to${name})')"
                       size="8"/>
			</c:if>
		</span>
	        &nbsp;по&nbsp;
		<span style="font-weight:normal;cursor:default;">
            <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${name})" format="dd.MM.yyyy"/>'
                   name="filter(to${name})" class="${className}"
                   size="10"/>
			<c:if test="${time==true}">
                <input value='<bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(toTime)" format="HH:mm:ss"/>'
                       name="filter(toTime)"
                       class="time-template"
                       size="8"/>
			</c:if>
		</span>
		</nobr>
	</td>

<c:if test="${empty colCount || colCount == ''}"><c:set var="colCount" value="3"/></c:if>
<c:if test="${countTd>=colCount}">
	</tr>
	<tr>
	<c:set var="countTd" value="0" scope="request"/>
</c:if>
<script type="text/javascript">
    addClearMasks(null,
            function (event)
            {
                clearInputTemplate('filter(from${name})', '__.__.____');
                clearInputTemplate('filter(to${name})', '__.__.____');
            }
    );
</script>

