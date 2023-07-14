<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

&nbsp;
<html:form action="/async/blockingMessage">
    <c:set var="form" value="${csa:currentForm(pageContext)}"/>
    <c:if test="${not empty form.blockingMessage}">
        <div id="blockingMessage">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="yellowTop"/>
                <tiles:put name="data" type="string">
                    <c:out value="${form.blockingMessage}"/>
                </tiles:put>
            </tiles:insert>
        </div>
    </c:if>
</html:form>