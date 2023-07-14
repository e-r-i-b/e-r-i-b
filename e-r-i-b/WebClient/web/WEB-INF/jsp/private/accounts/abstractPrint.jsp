<%--
  Created by IntelliJ IDEA.
  User: Kosyakova
  Date: 01.08.2006
  Time: 11:41:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/accounts/print">

<tiles:insert definition="printDoc">
	<tiles:put name="data" type="string">
		<c:set var="listSize" value="0"/>
		<c:forEach var="current" items="${PrintAccountAbstractForm.HTML}">
			<c:if test="${listSize!=0}">
				<br style="page-break-after:always;">
			</c:if>
			<c:out value="${current}" escapeXml="false"/>
			<c:set var="listSize" value="${listSize+1}"/>
		</c:forEach>
	</tiles:put>
</tiles:insert>

</html:form>
