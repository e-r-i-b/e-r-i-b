<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<tiles:useAttribute name="formItems"/>
<c:forEach var="formItem" items="${formItems}">
    <jsp:include page="${formItem}"/>
</c:forEach>
