<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<html:form action="/ermb/migration/vip">
    <tiles:insert page="list.jsp" flush="false">
        <tiles:put name="vip" value="true"/>
    </tiles:insert>
</html:form>