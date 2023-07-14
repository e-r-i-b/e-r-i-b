<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/common/mobile/types/status.jsp"%>
<response>
    <status>
        <code>${STATUS_ACCESS_DENIED}</code>
        <errors>
            <error>
                <text><bean:message key="error.accessDeny" bundle="commonBundle"/></text>
            </error>
        </errors>
    </status>
</response>