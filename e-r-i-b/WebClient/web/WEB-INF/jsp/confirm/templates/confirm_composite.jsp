<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<logic:iterate id="requestId" name="confirmRequest" property="requests">
	<c:set var="confirmTemplate" value="confirm_${requestId.strategyType}"/>
	<tiles:insert definition="${confirmTemplate}" flush="false">
		<tiles:put name="confirmRequest" beanName="requestId"/>
	</tiles:insert>
</logic:iterate>
<c:if test="${not empty message}">
	${message}
</c:if>