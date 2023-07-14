<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>


{
"errors":[
<phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
    <c:set var="errorPost">${phiz:processBBCodeAndEscapeHtml(error,false)}</c:set>
    "${phiz:escapeForJS(errorPost, true)}",
</phiz:messages>
""
],
"messages": [
<phiz:messages  id="error" bundle="commonBundle" field="field" message="message">
    <c:set var="messagePost">${phiz:processBBCodeAndEscapeHtml(error,false)}</c:set>
    "${phiz:escapeForJS(messagePost, true)}",
</phiz:messages>
""
]
}
