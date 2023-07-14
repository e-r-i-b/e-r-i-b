<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/xml;windows-1251" language="java" %>

<c:set var="errors" scope="request" value=""/>
<tiles:importAttribute/>

<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>

<c:if test="${empty bundle || bundle==''}">
    <c:set var="bundle" value="commonBundle"/>
</c:if>

<c:set var="errors" value=""/>
<%-- Формирование Ошибок  --%>
<c:set var="errors">
    <phiz:messages id="errorData" bundle="${bundle}"  field="field" message="error">
       <error>
           <text>
               <![CDATA[
                   ${phiz:processBBCodeAndEscapeHtml(errorData,false)}
               ]]>
            </text>
           <c:if test="${field != null}">
               <c:set var="fieldName"><bean:write name="field" filter="false"/></c:set>
               <%-- Если среди известных названий полей есть данное поле, обернутое field(*), то отдаем
               именно такое название с field(*), иначе отдаем просто название поля --%>
               <c:if test="${knownFieldNames != null and knownFieldNames != ''}">
                   <c:set var="wrappedFieldName">field(${fieldName})</c:set>
                   <c:if test="${phiz:contains(knownFieldNames, wrappedFieldName)}">
                       <c:set var="fieldName" value="${wrappedFieldName}"/>
                   </c:if>
               </c:if>
               <elementId>${fieldName}</elementId>
           </c:if>
        </error>
    </phiz:messages>
</c:set>
<c:set var="errors">${fn:trim(errors)}</c:set>
<c:if test="${not empty errors && errors != ''}">
    <c:set var="errors" value="${errors}" scope="request"/>
</c:if>

<c:set var="messages" value=""/>
<%-- Формирование сообщений  --%>
<c:set var="messages">
    <phiz:messages id="messageData" bundle="${bundle}"  field="field" message="message">
        <warning>
            <text>
                <![CDATA[
                    ${phiz:processBBCodeAndEscapeHtml(messageData,false)}
                ]]>
            </text>
           <c:if test="${field != null}">
               <c:set var="fieldName"><bean:write name="field" filter="false"/></c:set>
               <%-- Если среди "правильных" названий полей есть данное поле, обернутое field(*), то отдаем
               именно такое название с field(*), иначе отдаем просто название поля --%>
               <c:if test="${knownFieldNames != null and knownFieldNames != ''}">
                   <c:set var="wrappedFieldName">field(${fieldName})</c:set>
                   <c:if test="${phiz:contains(knownFieldNames, wrappedFieldName)}">
                       <c:set var="fieldName" value="${wrappedFieldName}"/>
                   </c:if>
               </c:if>
               <elementId>${fieldName}</elementId>
           </c:if>
        </warning>
    </phiz:messages>
    <jsp:useBean id="inactiveExternalSystemMessages" scope="request" class="java.util.ArrayList" />
    <phiz:messages id="inactiveES" bundle="${bundle}"  field="field" message="inactiveExternalSystem">
        <c:set var="prepareMessage" value="${phiz:processBBCodeAndEscapeHtml(inactiveES, false)}" scope="request"/>
        <c:if test="${!phiz:contains(inactiveExternalSystemMessages, prepareMessage)}">
            <% inactiveExternalSystemMessages.add(request.getAttribute("prepareMessage")); %>
            <warning>
               <text>
                   <![CDATA[
                       ${prepareMessage}
                   ]]>
                </text>
            </warning>
        </c:if>
    </phiz:messages>
</c:set>
<c:set var="messages">${fn:trim(messages)}</c:set>
<c:if test="${not empty messages && messages != ''}">
    <c:set var="messages" value="${messages}" scope="request"/>
</c:if>
