<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>


<html:form action="/private/accounts/print">

<tiles:insert definition="printDoc">
	<tiles:put name="data" type="string">

	<c:set var="form" value="${PrintAccountAbstractForm}"/>

	<c:choose>
		<c:when test="${!form.copying}">
			<%@ include file="/WEB-INF/jsp-sevb/abstr/abstract.jsp"%>
		</c:when>
		<c:otherwise>
			<%@ include file="/WEB-INF/jsp-sevb/abstr/information.jsp"%>
		</c:otherwise>
	</c:choose>

	</tiles:put>
</tiles:insert>

</html:form>