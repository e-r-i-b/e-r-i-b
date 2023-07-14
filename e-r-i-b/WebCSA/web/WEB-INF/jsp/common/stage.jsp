<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa"%>

<tiles:importAttribute/>

<c:set var="errors" value=""/>
<c:set var="messages" value=""/>

<%-- Формирование Ошибок  --%>
<script type="text/javascript">
    <csa:messages  id="error" bundle="${bundle}" field="field" message="error">
        <c:choose>
            <c:when test="${field != null}">
                payInput.fieldError('<bean:write name="field" filter="false"/>', "${error}", "stageForm");
            </c:when>
            <c:otherwise>
                <c:set var="errors">
                    ${errors}
                    <div class = "itemDiv"><bean:write name="error" filter="false"/></div>
                </c:set>
            </c:otherwise>
        </c:choose>
    </csa:messages>
</script>

<csa:messages  id="message" bundle="${bundle}" field="field" message="message">
    <c:set var="messages">
        ${messages}
        <div class = "itemDiv"><bean:write name="message" filter="false"/></div>
    </c:set>
</csa:messages>

<%-- Название --%>
<div class="title-stage">
    <h2><tiles:insert attribute="title"/></h2>
</div>

<%-- Описание --%>
<c:if test="${not empty description}">
    <div class="description-stage">
        <tiles:insert attribute="description"/>
    </div>
</c:if>

<%-- Сообщения --%>
<c:set var="messageLength" value="${fn:length(messages)}"/>
<tiles:insert definition="warningBlock" flush="false">
    <tiles:put name="regionSelector" value="warnings"/>
    <tiles:put name="isDisplayed" value="${messageLength gt 0 ? true : false}"/>
    <tiles:put name="data">
       <bean:write name="messages" filter="false"/>
    </tiles:put>
</tiles:insert>

<%-- Ошибки --%>
<c:set var="errorsLength" value="${fn:length(errors)}"/>
<tiles:insert definition="errorBlock" flush="false">
    <tiles:put name="regionSelector" value="errors"/>
    <tiles:put name="isDisplayed" value="${errorsLength gt 0 ? true : false}"/>
    <tiles:put name="data">
        <bean:write name="errors" filter="false"/>
    </tiles:put>
</tiles:insert>

<div class="data-stage">
    <tiles:insert attribute="data"/>
</div>

