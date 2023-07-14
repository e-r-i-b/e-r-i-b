<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 24.06.2009
  Time: 16:51:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>
    <body>
    <c:if test="${phiz:isPhizIC()}">
        <tiles:insert definition="googleTagManager"/>
    </c:if>
    <script type="text/javascript">
        doOnLoad(function() {showMessage()});
    </script>

    <c:catch var="errorJSP">
        <html:form show="true">
		${data}
		<tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
	        <tiles:useAttribute name="messagesBundle"/>
	        <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
        </tiles:insert>
        </html:form>
    </c:catch>
    <c:if test="${not empty errorJSP}">
        ${phiz:writeLogMessage(errorJSP)}
        <script type="text/javascript">
            window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
        </script>
    </c:if>
	</body>
</html:html>