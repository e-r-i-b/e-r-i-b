<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<html:form styleId="editGarageFormId" action="/private/basket/accounting/editGarage" onsubmit="return setEmptyAction();" show="true">
    <c:set var="type" value="Garage"/>
    <%@include file="edit.jsp"%>
</html:form>