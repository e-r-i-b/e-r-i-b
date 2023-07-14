<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 
<html:html>
    <%@ include file="/WEB-INF/jsp/common/layout/print-head.jsp"%>
    <body style="height:99%;">
        <tiles:insert definition="googleTagManager"/>
        <tiles:insert attribute="pageTitle"/>
        <c:catch var="errorJSP">
            <div class="print">
                <tiles:insert attribute="data"/>
            </div>
            <div class="printMessages">
                <tiles:insert page="messages.jsp">
                    <tiles:useAttribute name="messagesBundle"/>
                    <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                </tiles:insert>
            </div>
        </c:catch>
        <c:if test="${not empty errorJSP}">
            ${phiz:writeLogMessage(errorJSP)}
            <script type="text/javascript">
                window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
            </script>
        </c:if>
	</body>
    <div id="loading" class="waitMessageDiv">
        <div id="loadingImg"><img src="${skinUrl}/images/ajax-loader64.gif"/></div>
        <span><bean:message bundle="commonBundle" key="label.wait.message"/></span>
    </div>
</html:html>