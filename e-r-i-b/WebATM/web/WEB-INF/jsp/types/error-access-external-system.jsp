<?xml version="1.0" encoding="windows-1251" ?>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ include file="/WEB-INF/jsp/types/status.jsp"%>
<response>
    <status>
        <code>${STATUS_CRITICAL_ERROR}</code>
        <errors>
            <error>
                <text>
                    <phiz:messages id="inactiveES" bundle="commonBundle" field="field" message="inactiveExternalSystem">${inactiveES}</phiz:messages>
                </text>
            </error>
        </errors>
    </status>
</response>