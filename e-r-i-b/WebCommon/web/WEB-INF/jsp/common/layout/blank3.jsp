
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>

    <body onLoad="showMessage();" Language="JavaScript">
    <tiles:insert definition="googleTagManager"/>
    <c:catch var="errorJSP">
    <table class="MaxSize" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<div class="MaxSize">
		<tiles:insert attribute="data"/>
		<tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
	        <tiles:useAttribute name="messagesBundle"/>
	        <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
        </tiles:insert>
		</div>
		</td>
	</tr>
    <tr>
		<td colspan="3" height="10px" width="100%" class="borderDownLine fontInterBank" align="right">
			<a href="http://www.softlab.ru/solutions/InterBank/">
				&copy;InterBank
			</a>
		</td>
	</tr>
	</table>
	</body>
    </c:catch>
    <c:if test="${not empty errorJSP}">
        ${phiz:writeLogMessage(errorJSP)}
        <script type="text/javascript">
            window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
        </script>
    </c:if>
</html:html>