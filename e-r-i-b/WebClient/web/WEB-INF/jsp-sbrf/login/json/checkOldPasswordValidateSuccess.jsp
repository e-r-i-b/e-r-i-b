<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="form" value="${CheckOldPasswordForm}"/>
<tiles:insert definition="jsonResponseSuccess">
    <tiles:put name="redirect" value=""/>
</tiles:insert>