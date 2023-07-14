<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="usersLog"/>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
            <c:set var="logEntry" value="${listElement}"/>
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.id">
                <c:if test="${not empty logEntry}">
                    <bean:write name="logEntry" property="loginId"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.fullName">
                    <c:choose>
                            <c:when test="${(not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName)}">
                                  <bean:write name="logEntry" property="surName"/>&nbsp;
                                  <bean:write name="logEntry" property="firstName"/>&nbsp;
                                  <bean:write name="logEntry" property="patrName"/>
                            </c:when>
                            <c:otherwise> ${logEntry.userId}</c:otherwise>
                    </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.operation.name" name="logEntry" property="description"/>
            <sl:collectionItem title="label.operation.parameter">
                <c:if test="${not empty logEntry.parameters}">
                    <input type="button" class="buttWhite smButt" onclick="openOperationDetails(${logEntry.id});" value="..."/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.application">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${logEntry.application}"/>
                </tiles:insert>
            </sl:collectionItem>
            <sl:collectionItem title="label.department.name">
                <c:if test="${not empty logEntry}">
                    <bean:write name="logEntry" property="departmentName"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.ip.address"name="logEntry" property="ipAddress"/>
            <sl:collectionItem title="label.session.id" name="logEntry" property="sessionId"/>
            <sl:collectionItem title="label.message.type">
                <c:choose>
                    <c:when test="${logEntry.type=='B'}">
                        <bean:message key="user.log.type.security" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${logEntry.type=='S'}">
                        <bean:message key="user.log.type.success" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${logEntry.type=='E'}">
                        <bean:message key="user.log.type.system.error" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${logEntry.type=='U'}">
                        <bean:message key="user.log.type.user.error" bundle="logBundle"/>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="user.log.type.other" bundle="logBundle"/>
                    </c:otherwise>
                 </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.record.num" name="logEntry" property="id"/>
            <sl:collectionItem title="label.node.id" name="logEntry" property="nodeId"/>
        </sl:collection>
    </tiles:put>

    <script type="text/javascript">
       var widthClient = getClientWidth();
       if (navigator.appName=='Microsoft Internet Explorer')
           document.getElementById("workspaceDiv").style.width = widthClient - leftMenuSize - 120;

       <c:if test="${form.fromStart}">
           //показываем фильтр при старте
           switchFilter(this);
       </c:if>
       function openOperationDetails(id)
       {
           openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/operationParameters')}"+'?type=simple&id='+id,'Параметры');
       }

    </script>
    <tiles:put name="isEmpty"      value="${empty form.list}"/>
    <tiles:put name="emptyMessage" value="Не найдено ни одной записи, соответствующей заданному фильтру!"/>
</tiles:insert>