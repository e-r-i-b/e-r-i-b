<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/private/pfr/print">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="printDoc" flush="false">
        <tiles:put name="data" type="string">
            ${form.html}
        </tiles:put>
    </tiles:insert>
</html:form>