<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<tiles:insert attribute="pageTitle"/>
<c:catch var="errorJSP">
<table class="MaxWidth" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="print">
                <tiles:insert attribute="data"/>
            </div>
            <div class="printMessages">
                <tiles:insert page="messages.jsp">
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
