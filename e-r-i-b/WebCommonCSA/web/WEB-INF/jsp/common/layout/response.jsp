<?xml version="1.0" encoding="windows-1251" ?>
<%@ page contentType="text/xml;windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:importAttribute ignore="true"/>
<%--
    defaultStatus - статус по умолчанию (если небыло никаких ошибок). По умолчанию 0 (STATUS_OK).
    status - принудительный статус. Устанавливается в не зависимости от наличия ошибок
    errorDescription - описание ошибки для принудительного статуса
	data - данные
    messagesBundle - bundle сообщений. По умолчанию common
--%>
<%@ include file="/WEB-INF/jsp/common/layout/status.jsp"%>
<response>

    <tiles:insert page="messages.jsp">
        <tiles:useAttribute name="messagesBundle"/>
        <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
    </tiles:insert>

   <c:choose>
        <%--Если из самой jsp не передали определённый статус--%>
        <c:when test="${empty status || status == ''}">
            <c:set var="statusCode">${not empty errors && errors != ''?STATUS_LOGIC_ERROR:defaultStatus}</c:set>
        </c:when>
        <c:otherwise>
            <c:set var="statusCode">${status}</c:set>
            <c:if test="${not empty errorDescription && errorDescription != ''}">
                <c:set var="errors">
                    ${errors}
                    <error>
                        <text>
                            <![CDATA[
                                ${errorDescription}
                            ]]>
                        </text>
                    </error>
                </c:set>
            </c:if>
        </c:otherwise>
    </c:choose>

    <status>
        <code>${statusCode}</code>
        <c:if test="${not empty errors && errors != '' && status != STATUS_PRODUCT_ERROR}">
            <errors>
                ${errors}
            </errors>
        </c:if>

        <c:if test="${not empty messages && messages != '' && status != STATUS_PRODUCT_ERROR}">
            <warnings>
                ${messages}
            </warnings>
        </c:if>
    </status>
    ${data}

</response>