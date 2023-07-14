<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/dictionary/regions">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="data">
            <c:if test="${not empty form.region.id}">
                <region>
                    <c:set var="region" value="${form.region}" scope="request"/>
                    <jsp:include page="../types/regionType.jsp"/>
                </region>
            </c:if>
            <sl:collection id="region" property="regions" model="xml-list" title="regionsList">
                <sl:collectionItem title="region">
                    <id>${region.id}</id>
                    <description><c:out value="${region.name}"/></description>
                    <c:set var="code" value="${region.synchKey}"/>
                    <c:if test="${not empty code}">
                        <c:set var="tmpCode" value="${phiz:replace(code, '[0-9]{5}')}"/>
                        <c:if test="${fn:length(tmpCode) eq 0}">
                            <code><c:out value="${code}"/></code>
                        </c:if>
                    </c:if>
                    <guid><c:out value="${region.multiBlockRecordId}"/></guid>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
