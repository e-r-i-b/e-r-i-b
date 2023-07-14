<%--
  User: usachev
  Date: 14.12.14
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<html:form action="/private/fund/response/list">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="list" value="${form.data}"/>
    <c:set var="avatars" value="${form.avatarsMap}"/>

    <tiles:insert definition="iphone">
        <tiles:put name="defaultStatus" value="0"/>
        <tiles:put name="data">
            <c:if test="${not empty list}">
                <responses>
                    <c:forEach items="${list}" var="response">
                        <response>
                            <id>${response.externalId}</id>
                            <fio>${response.initiatorFIO}</fio>
                            <phones>
                                <c:forEach items="${response.listInitiatorPhones}" var="phone">
                                    <phone>${phone}</phone>
                                </c:forEach>
                            </phones>
                            <requestState>${response.viewRequestState.description}</requestState>
                            <c:if test="${not empty response.requiredSum}">
                                <requiredSum>${response.requiredSum}</requiredSum>
                            </c:if>
                            <c:if test="${not empty response.reccomendSum}">
                                <reccomendSum>${response.reccomendSum}</reccomendSum>
                            </c:if>
                            <createDate>${phiz:ñalendarToString(response.createdDate)}</createDate>
                            <c:if test="${not empty response.closedDate}">
                                <closeDate>${phiz:ñalendarToString(response.closedDate)}</closeDate>
                            </c:if>
                            <message>${response.requestMessage}</message>
                            <c:if test="${not empty avatars[response.externalId]}">
                                <avatarPath>${avatars[response.externalId]}</avatarPath>
                            </c:if>
                            <state>${response.state.description}</state>
                        </response>
                    </c:forEach>
                </responses>
            </c:if>
        </tiles:put>
    </tiles:insert>
</html:form>