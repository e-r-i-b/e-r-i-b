<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>

<html:form action="/private/finances/operations/edit">
    <tiles:insert definition="iphone" flush="false"/>
</html:form>