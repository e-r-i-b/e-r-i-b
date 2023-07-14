<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/permissions">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.permissions}">
                <permissions>
                    <c:forEach items="${form.permissions}" var="entry">
                        <permission>
                            <key>${entry.key}</key>
                            <allowed>${entry.value}</allowed>
                        </permission>
                    </c:forEach>
                </permissions>
            </c:if>
            <c:if test="${not empty form.dictionaries}">
                <dictionaries>
                    <c:forEach items="${form.dictionaries}" var="dictionary">
                        <dictionary>
                            <code><c:out value="${dictionary.key}"/></code>
                            <state><c:out value="${dictionary.value}"/></state>
                        </dictionary>
                    </c:forEach>
                </dictionaries>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>