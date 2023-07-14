<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/confirm/way4" method="post" onsubmit="this.onsubmit = function(){ return false; }; return setEmptyAction();">
    <tiles:insert definition="confirmPassword">
        <tiles:put name="pageTitle" value="Сбербанк Онлайн"/>
        <tiles:put type="page" name="data" value="/WEB-INF/jsp-sbrf/confirm/confirmiPasData.jsp"/>
    </tiles:insert>
</html:form>