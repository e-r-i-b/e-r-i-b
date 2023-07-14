<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/contacts/sync">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:set var="sberContactMap" value="${form.sberContactMap}"/>
            <c:if test="${not empty sberContactMap}">
                <c:set var="sberContacts">
                    <c:forEach var="entry" items="${sberContactMap}">
                        <contact>
                            <name><c:out value="${entry.key}"/></name>
                            <c:forEach var="phone" items="${entry.value}">
                                <phone>${phone}</phone>
                            </c:forEach>
                        </contact>
                    </c:forEach>
                </c:set>
            </c:if>

            <c:if test="${not empty sberContacts}">
                <contacts>
                    ${sberContacts}
                </contacts>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

