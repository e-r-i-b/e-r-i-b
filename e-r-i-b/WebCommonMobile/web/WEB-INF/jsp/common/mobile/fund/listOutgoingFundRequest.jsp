<%--
  User: usachev
  Date: 05.12.14
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/fund/request/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone">
        <tiles:put name="defaultStatus" value="0"/>
        <tiles:put name="data">
            <c:if test="${not empty form.data}">
                <requests>
                    <c:forEach items="${form.data}" var="record">
                        <request>
                            <id>${record.id}</id>
                            <state>${record.state.description}</state>
                            <c:if test="${not empty record.requiredSum}">
                                <requiredSum>${record.requiredSum}</requiredSum>
                            </c:if>
                            <c:if test="${not empty record.accumulatedSum}">
                                <accumulatedSum>${record.accumulatedSum}</accumulatedSum>
                            </c:if>
                            <createDate>${phiz:ñalendarToString(record.createdDate)}</createDate>
                            <c:if test="${not empty record.closedDate}">
                                <closeDate>${phiz:ñalendarToString(record.closedDate)}</closeDate>
                            </c:if>
                        </request>
                    </c:forEach>
                </requests>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>

