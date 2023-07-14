<?xml version="1.0" encoding="windows-1251" ?>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/types/status.jsp"%>
<response>
    <status>
        <code>${STATUS_ACCESS_DENIED}</code>
        <errors>
            <error>
                <c:choose>
                    <c:when test="${not empty errorMessageKey}">
                        <text><bean:message key="${errorMessageKey}" bundle="commonBundle"/></text>
                    </c:when>
                    <c:otherwise>
                        <text><bean:message key="error.accessDeny" bundle="commonBundle"/></text>
                    </c:otherwise>
                </c:choose>
            </error>
        </errors>
    </status>
</response>