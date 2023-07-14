
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="information">
    <tiles:put name="data" type="page" value="/WEB-INF/jsp/indexData.jsp"/>
</tiles:insert>

