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
                <sbercontacts>
                    <c:forEach var="entry" items="${sberContactMap}">
                        <contact>
                            <name><c:out value="${entry.key}"/></name>
                            <c:forEach var="phone" items="${entry.value}">
                                <phone>${phone}</phone>
                            </c:forEach>
                        </contact>
                    </c:forEach>
                </sbercontacts>
            </c:if>

            <c:set var="nosberContactMap" value="${form.noSberContactMap}"/>
            <c:if test="${not empty nosberContactMap}">
                <nosbercontacts>
                    <c:forEach var="entry" items="${nosberContactMap}">
                        <contact>
                            <name><c:out value="${entry.key}"/></name>
                            <c:forEach var="phone" items="${entry.value}">
                                <phone>${phone}</phone>
                            </c:forEach>
                        </contact>
                    </c:forEach>
                </nosbercontacts>
            </c:if>

            <c:set var="incognitoContactMap" value="${form.incognitoContactMap}"/>
            <c:if test="${not empty incognitoContactMap}">
                <incognitocontacts>
                    <c:forEach var="entry" items="${incognitoContactMap}">
                        <contact>
                            <name><c:out value="${entry.key}"/></name>
                            <c:forEach var="phone" items="${entry.value}">
                                <phone>${phone}</phone>
                            </c:forEach>
                        </contact>
                    </c:forEach>
                </incognitocontacts>
            </c:if>

            <c:set var="unlimitedContactMap" value="${form.unlimitedContactMap}"/>
            <c:if test="${not empty unlimitedContactMap}">
                <unlimitedcontacts>
                    <c:forEach var="entry" items="${unlimitedContactMap}">
                        <contact>
                            <name><c:out value="${entry.key}"/></name>
                            <c:forEach var="phone" items="${entry.value}">
                                <phone>${phone}</phone>
                            </c:forEach>
                        </contact>
                    </c:forEach>
                </unlimitedcontacts>
            </c:if>

            <c:set var="limits" value="${form.result}"/>
            <c:if test="${not empty limits}">
                <limits>
                    <firstSyncLimit><c:out value="${limits.firstSyncLimit}"/></firstSyncLimit>
                    <weekLimit><c:out value="${limits.weekLimit}"/></weekLimit>
                    <dayLimit><c:out value="${limits.dayLimit}"/></dayLimit>
                </limits>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

