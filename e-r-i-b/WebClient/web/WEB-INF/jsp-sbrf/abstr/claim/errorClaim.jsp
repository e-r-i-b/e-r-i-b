<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<c:set var="form" value="${SendPrivateOperationScanClaimForm}"/>
<c:set var="messages">
    <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
        <bean:write name="error" filter="false"/>
    </phiz:messages>
</c:set>
{"messages":"${phiz:escapeForJS(messages, true)}"}
