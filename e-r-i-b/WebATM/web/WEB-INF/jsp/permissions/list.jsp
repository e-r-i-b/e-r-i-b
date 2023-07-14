<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/private/permissions">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
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
             <checkedUDBO>
                ${form.checkedUDBO}
            </checkedUDBO>
        </tiles:put>
    </tiles:insert>
</html:form>