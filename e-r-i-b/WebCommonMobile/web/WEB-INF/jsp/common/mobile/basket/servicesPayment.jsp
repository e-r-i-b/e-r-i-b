<%@ page contentType="text/xml;charset=windows-1251" language="java" %>

<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html"  %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean"  %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/basket/subscriptions/create">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            ${form.html}
        </tiles:put>
    </tiles:insert>
</html:form>