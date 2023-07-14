<?xml version="1.0" encoding="windows-1251" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ include file="/WEB-INF/jsp/common/socialApi/types/status.jsp"%>
<response>
    <status>
        <code>${STATUS_LOGIC_ERROR}</code>
        <errors>
            <error>
                <text><bean:message bundle="notFoundExceptionMessagesBundle" key="${exceptionMessage}"/></text>
            </error>
        </errors>
    </status>
</response>
