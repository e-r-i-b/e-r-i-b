<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/person/search" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="pfpPassing">
        <c:set var="searchForm" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="clientNotFound" value="${searchForm.fields.clientNotFound}"/>
        <c:choose>
            <c:when test="${clientNotFound}">
                <tiles:put name="additionalInfoBlock" value=""/>
                <tiles:put name="submenu" type="string" value="clientNotFound"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="submenu" type="string" value="searchClient"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="data" type="string">
            <tiles:insert definition="searchClientForm" flush="false">
                <tiles:put name="accessService" value="EmployeePfpEditService"/>
            </tiles:insert>
            <c:if test="${clientNotFound}">
                <c:set var="potentialPersonInfoURL" value="${phiz:calculateActionURL(pageContext, '/pfp/person/potentialPersonInfo')}"/>
                <script type="text/javascript">
                    $(document).ready(function()
                    {
                        if (confirm('<bean:message key="label.person.search.page.empty.person.message" bundle="pfpPassingBundle"/>'))
                            goTo('${potentialPersonInfoURL}');
                    });
                </script>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>
