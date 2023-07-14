<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<tiles:insert definition="login">
<tiles:put name="pageTitle" type="string" value="Регистрация."/>
<tiles:put type="page" name="data" value ="/WEB-INF/jsp-sbrf/LoginPasswordiPasData.jsp"/>
</tiles:insert>
