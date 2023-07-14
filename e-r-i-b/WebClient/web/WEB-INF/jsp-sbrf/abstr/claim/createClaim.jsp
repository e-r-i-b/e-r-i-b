<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${SendPrivateOperationScanClaimForm}"/>
<c:set var="eMail" value="${phiz:getMaskedEMail(form.fields.eMail)}"/>
{"eMail": "${eMail}"}