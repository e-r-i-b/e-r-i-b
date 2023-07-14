<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:form styleId="editFlatFormId" action="/private/basket/accounting/editFlat" onsubmit="return setEmptyAction();" show="true">
    <c:set var="type" value="Flat"/>
    <%@include file="edit.jsp"%>
</html:form>