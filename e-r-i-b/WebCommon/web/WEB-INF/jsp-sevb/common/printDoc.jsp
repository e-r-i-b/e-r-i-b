<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 24.02.2009
  Time: 14:51:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>

    <body onLoad="showMessage();" Language="JavaScript" class="Print">
    <c:catch var="errorJSP">
    <table class="MaxSize" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<div class="MaxSize printPmnt">
		<tiles:insert attribute="data"/>
        <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
            <tiles:useAttribute name="messagesBundle"/>
            <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
        </tiles:insert>
		</div>
		</td>
	</tr>
	</table>
     </c:catch>
     <c:if test="${not empty errorJSP}">
        ${phiz:writeLogMessage(errorJSP)}
        <script type="text/javascript">
            window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
        </script>
     </c:if>
	</body>
</html:html>