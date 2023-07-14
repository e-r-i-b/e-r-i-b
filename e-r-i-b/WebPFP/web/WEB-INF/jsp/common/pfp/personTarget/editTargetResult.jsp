<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

&nbsp;
<html:form action="/async/editPfpTarget">
    <tiles:insert definition="webModulePage">
        <tiles:put name="data">
            <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
            <c:set var="personTargetList" value="${form.personTargetList}"/>

            <c:choose>
                <%--если вернулась цель клиента, то формируем строку таблицы с данной целью--%>
                <c:when test="${not empty personTargetList}">
                    <table id="targetListResult">
                        <c:set var="showThermometer" value="${form.showThermometer}"/>
                        <c:forEach items="${personTargetList}" var="target">
                            <%@ include file="/WEB-INF/jsp/common/pfp/personTarget/targetLine.jsp"%>
                        </c:forEach>
                    </table>
                </c:when>
                <%--иначе пробеум вытащить сообщения об ошибках--%>
                <c:otherwise>
                    <phiz:messages  id="error" bundle="pfpBundle" field="field" message="error">
                        <c:set var="errors">${errors}${error}</c:set>
                    </phiz:messages>
                    <c:out value="${errors}"/>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>