<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html" %>
<%@ taglib uri="http://rssl.com/tags"                        prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>

<html:form action="/private/finances/targets/viewDepositTerms">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <text><![CDATA[${form.html}]]></text>
        </tiles:put>
    </tiles:insert>
</html:form>