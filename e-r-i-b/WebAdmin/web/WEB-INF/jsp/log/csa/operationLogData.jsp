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
<script type="text/javascript">
    function onParamPlus(event,id,ref)
    {
        cancelEventCrossBrowser(event);
        var message = document.getElementById(id);
        var isOff = (message.style.display == "none");
        ref.innerHTML = (isOff ? "<image src='" + document.imgPath + "iconSm_triangleUp.gif' border='0'/>" :
                                 "<image src='" + document.imgPath + "iconSm_triangleDown.gif' border='0'/>");
        message.style.display = (isOff ? "block":"none");
    }
</script>
<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="usersLog"/>
    <tiles:put name="text" value="Журнал действий пользователей"/>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle">
            <c:set var="logEntry" value="${listElement}"/>
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.login">
                <c:if test="${not empty logEntry}">
                    <bean:write name="logEntry" property="login"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.fullName">
                    <c:choose>
                            <c:when test="${(not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName)}">
                                  <bean:write name="logEntry" property="surName"/>&nbsp;
                                  <bean:write name="logEntry" property="firstName"/>&nbsp;
                                  <bean:write name="logEntry" property="patrName"/>
                            </c:when>
                            <c:otherwise> ${logEntry.login}</c:otherwise>
                    </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.operation.name">
                <c:if test="${logEntry.key!= null}">
                    <c:out value="${logEntry.operationKeyDescription}"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.operation.parameter">
                <c:if test="${not empty logEntry.parameters}">
                    <a id="plus${logEntry.operationKey}" href="" onclick="onParamPlus(event,'param${logEntry.operationKey}', this);">
                        <image src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                    </a>
                    <div id="param${logEntry.operationKey}"style="display:none" >
                        <pre><c:out value="${logEntry.parameters}"/></pre>
                    </div>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.application">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${logEntry.application}"/>
                </tiles:insert>
            </sl:collectionItem>
            <sl:collectionItem title="label.department.code">
                <c:choose>
                    <c:when test="${empty logEntry.departmentCode or  logEntry.departmentCode == 'none'}">
                        &nbsp;
                    </c:when>
                    <c:otherwise>
                         <bean:write name="logEntry" property="departmentCode"/>&nbsp;
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.ip.address"name="logEntry" property="ipAddress"/>
            <sl:collectionItem title="label.operation.state">
                <c:out value="${logEntry.operationStateDescription}"/>
            </sl:collectionItem>
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
    </script>
    <tiles:put name="isEmpty"      value="${empty form.list}"/>
    <tiles:put name="emptyMessage" value="Не найдено ни одной записи,<br/>соответствующей заданному фильтру!"/>
</tiles:insert>
