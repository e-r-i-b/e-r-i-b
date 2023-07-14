<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insert definition="main">
    <tiles:put name="pageTitle" type="string">Ошибка</tiles:put>
    <tiles:put name="data" type="page" value ="/WEB-INF/jsp-sbrf/errorPageData.jsp"/>
</tiles:insert>
