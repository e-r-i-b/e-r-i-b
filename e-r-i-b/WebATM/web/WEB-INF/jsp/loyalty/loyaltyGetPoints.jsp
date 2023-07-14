<%@page contentType="text/xml;charset=windows-1251" language="java" %>

<%@taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"           prefix="c" %>
<%@taglib uri="http://rssl.com/tags"                        prefix="phiz" %>

<html:form action="/private/thanks">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm">
        <tiles:put name="data">
            <c:set var="thanks" value="${form.thanks}"/>
            <c:if test="${not empty thanks}">
                <thanks><c:out value="${thanks}"/></thanks>
            </c:if>

            <c:set var="loyaltyProgramState" value="${form.loyaltyProgramState}"/>
            <c:if test="${not empty loyaltyProgramState}">
                <loyaltyProgramState>${loyaltyProgramState}</loyaltyProgramState>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>