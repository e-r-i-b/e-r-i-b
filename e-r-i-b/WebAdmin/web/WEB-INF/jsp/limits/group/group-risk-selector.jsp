<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:select property="field(${selectProperty})" styleClass="select">
    <c:choose>
        <c:when test="${not empty form.groupRisks}">
            <html:option value="">Не выбрана группа риска</html:option>
            <c:forEach var="groupRisk" items="${form.groupRisks}">
                <html:option value="${groupRisk.id}">
                    <c:out value="${groupRisk.name}"/>
                </html:option>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <html:option value="">Не созданы группы риска</html:option>
        </c:otherwise>
    </c:choose>
</html:select>