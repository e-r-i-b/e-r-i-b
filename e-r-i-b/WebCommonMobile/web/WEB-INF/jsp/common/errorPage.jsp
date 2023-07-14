<?xml version="1.0" encoding="windows-1251" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<response>
    <status>
        <code>${STATUS_CRITICAL_ERROR}</code>
        <errors>
            <error>
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                                ${phiz:processBBCode(errorMessage)}
                            </c:when>
                    <c:when test="${not empty errorMessageKey}">
                        <text><bean:message key="${errorMessageKey}" bundle="commonBundle"/></text>
                    </c:when>
                    <c:otherwise>
                        <text><bean:message key="error.errorHeader" bundle="commonBundle"/></text>
                    </c:otherwise>
                </c:choose>
            </error>
        </errors>
    </status>
</response>