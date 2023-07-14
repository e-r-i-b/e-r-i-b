<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<c:set var="errors" value=""/>
<c:set var="messages" value=""/>

<%-- Формирование Ошибок  --%>
<script type="text/javascript">
    var errors = {};
    <csa:messages  id="error" bundle="${bundle}" field="field" message="error">
        <c:choose>
            <c:when test="${field != null}">
                errors['<bean:write name="field" filter="false"/>'] = {text :"${error}"};
            </c:when>
            <c:otherwise>
                <c:set var="errors">
                    ${errors}
                    <div class="itemDiv"><bean:write name="error" filter="false"/></div>
                </c:set>
            </c:otherwise>
        </c:choose>
        <tiles:useAttribute name="formId"/>
        utils.initErrors('${formId}', errors);
    </csa:messages>
</script>

<%-- Формирование сообщений  --%>
<csa:messages  id="message" bundle="${bundle}" field="field" message="message">
    <c:set var="messages">
        ${messages}
        <div class="itemDiv"><bean:write name="message" filter="false"/></div>
    </c:set>
</csa:messages>

<c:if test="${not empty messages or not empty errors}">
    <div class="b-message ugc">
        ${messages}
        ${errors}
    </div>
</c:if>

