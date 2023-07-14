<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<div class="g-main">
    <tiles:useAttribute name="steps"/>
    <c:if test="${not empty steps}">
        <ul class="b-step">
            ${steps}
        </ul><!-- // b-step -->
    </c:if>

    <%-- информационные сообщения и сообщения об ошибке --%>
    <tiles:useAttribute name="id"/>
    <tiles:insert page="messages.jsp" flush="false">
        <tiles:put name="formId" value="${id}"/>
    </tiles:insert>

    <div class="b-actions">
        <div id="step-content">
            <tiles:insert attribute="data"/>
        </div>
    </div><!-- // b-actions -->
</div><!-- // g-main -->