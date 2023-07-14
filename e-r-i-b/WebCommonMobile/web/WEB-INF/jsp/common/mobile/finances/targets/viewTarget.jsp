<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c"%>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz"%>

<html:form action="/private/finances/targets/viewTarget">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <id><c:out value="${form.id}"/></id>
            <autopayable><c:out value="${phiz:isMoneyBoxSupported(form.document)}"/></autopayable>
        </tiles:put>
    </tiles:insert>
</html:form>