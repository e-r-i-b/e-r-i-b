<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>

<html:form action="/private/payments/status">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="state" value="${form.state}"/>

   <tiles:insert definition="atm" flush="false">
        <tiles:put name="data" type="string">
           <state><c:out value="${state}"/></state>
        </tiles:put>
    </tiles:insert>

</html:form>
