<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${AddPromoCodeForm}"/>
{"errorMessageNumber": "${form.errorMessage.number}",
"errorMessageTitle": "${phiz:processBBCodeAndEscapeHtml(form.errorMessage.title,false)}",
"errorMessageText": "${phiz:processBBCodeAndEscapeHtml(form.errorMessage.text,false)}",
"error12MessageText": "${phiz:processBBCodeAndEscapeHtml(form.error12Message.text,false)}"}