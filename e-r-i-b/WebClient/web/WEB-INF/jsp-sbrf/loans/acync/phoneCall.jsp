<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="form" value="${AsyncConfirmPhoneCallForLoanClaimForm}"/>
{"errorMessage": "${phiz:escapeForJS(form.errorMessage, false)}",
 "informMessage": "${phiz:escapeForJS(form.informMessage, false)}",
 "isSmsPasswordExist": "${form.isSmsPasswordExist}",
 "token": "${sessionScope['org.apache.struts.action.TOKEN']}",
 "needNewPassword": "${form.isNeedNewPassword}",
 "successInfoTitle": "${form.successInfoTitle}",
 "successInfoText": "${form.successInfoText}"}