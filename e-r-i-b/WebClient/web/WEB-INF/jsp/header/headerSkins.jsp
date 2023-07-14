<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form action="/private/async/header/skins">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="skinSelector" flush="false">
            <tiles:put name="skinsList" beanName="form" beanProperty="skins"/>
            <tiles:put name="actionUrl" value="/private/async/header/skins"/>
        </tiles:insert>
</html:form>