<%--
  Created by IntelliJ IDEA.
  User: Omeliyanchuk
  Date: 13.10.2006
  Time: 16:03:54
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
	<%@ include file="/WEB-INF/jsp/common/layout/print-head.jsp"%>

    <body onLoad="showMessage();" style="background-color:#FFFFFF" class="PrintDoc">
    <c:if test="${phiz:isPhizIC()}">
        <tiles:insert definition="googleTagManager"/>
    </c:if>
    <c:catch var="errorJSP">
    <table cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <tiles:insert attribute="data"/>
                <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp">
                    <tiles:useAttribute name="messagesBundle"/>
                    <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                </tiles:insert>
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