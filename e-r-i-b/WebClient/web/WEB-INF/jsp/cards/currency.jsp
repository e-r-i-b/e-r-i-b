<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/cards/currency/code">
    &nbsp;
    <html:hidden styleId="currency" property="currency"/>
    <script type="text/javascript">
        var currencyErrorMessageAr = [];
        <phiz:messages  id="error" bundle="commonBundle" field="field" message="error">
            <c:set var="errorPost"><bean:write name="error" filter="false" /></c:set>
            currencyErrorMessageAr.push("${phiz:escapeForJS(errorPost, true)}");
        </phiz:messages>
    </script>
</html:form>
