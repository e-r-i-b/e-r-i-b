<%@page contentType="text/html;charset=windows-1251" language="java"%>

<%@taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>


<html:form action="/private/deposit/collateralAgreement">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    ${form.collateralAgreement}
</html:form>