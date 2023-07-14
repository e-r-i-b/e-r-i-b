<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript">
    function onExceptionPlus(event,id,ref)
    {
        cancelEventCrossBrowser(event);
        var message = document.getElementById('message'+id);
        var isOff = (message.style.display == "none");
        ref.innerHTML = (isOff ? "<image src='" + document.imgPath + "iconSm_triangleUp.gif' border='0'/>" :
                                 "<image src='" + document.imgPath + "iconSm_triangleDown.gif' border='0'/>");
        message.style.display = (isOff ? "block":"none");
    }
</script>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="certList"/>
    <tiles:put name="buttons">
        <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.download"/>
            <tiles:put name="commandHelpKey" value="button.download"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="autoRefresh" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
            <c:set var="logEntry" value="${listElement}"/>
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.login">
                <c:if test="${not empty logEntry}">
                    <c:choose>
                        <c:when test="${empty logEntry.login or logEntry.login == -1}">
                            &nbsp;
                        </c:when>
                        <c:when test="${not empty logEntry.login}">
                            <bean:write name="logEntry" property="login"/>
                        </c:when>
                    </c:choose>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.fullName">
                <c:choose>
                        <c:when test="${(not empty logEntry) && ((not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName))}">
                              <bean:write name="logEntry" property="surName"/>&nbsp;
                              <bean:write name="logEntry" property="firstName"/>&nbsp;
                              <bean:write name="logEntry" property="patrName"/>
                        </c:when>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.message.type">
                <c:set var="messageLevel" value="${logEntry.level}"/>
                <c:choose>
                    <c:when test="${messageLevel=='I'}">
                        <bean:message key="system.log.layer.I" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${messageLevel=='D'}">
                        <bean:message key="system.log.layer.D" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${messageLevel=='E'}">
                        <bean:message key="system.log.layer.E" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${messageLevel=='W'}">
                        <bean:message key="system.log.layer.W" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${messageLevel=='F'}">
                        <bean:message key="system.log.layer.F" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${messageLevel=='T'}">
                        <bean:message key="system.log.layer.T" bundle="logBundle"/>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.ip.address">
                <c:choose>
                    <c:when test="${empty logEntry.ipAddress or  logEntry.ipAddress == 'none'}">
                        &nbsp;
                    </c:when>
                    <c:otherwise>
                         <bean:write name="logEntry" property="ipAddress"/>&nbsp;
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.message">
                <c:if test="${logEntry.exception}">
                    <a id="plus${logEntry.id}" href="" onclick="onExceptionPlus(event,${logEntry.id},this);">
                        <image src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                    </a>
                </c:if>
                <c:choose>
                    <c:when test="${(!empty logEntry.message)&&(!empty logEntry.id)}">
                        <div id="message${logEntry.id}"<c:if test="${logEntry.exception}">style="display:none"</c:if> >
                            <pre><c:out value="${logEntry.message}"/></pre>
                        </div>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.application">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${logEntry.application}"/>
                </tiles:insert>
            </sl:collectionItem>
            <sl:collectionItem title="label.source">
                <c:if test="${not empty logEntry.source}">
                    <bean:message key="user.log.source.${logEntry.source}" bundle="logBundle"/>
                </c:if>
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
            <sl:collectionItem title="label.record.num" name="logEntry" property="id"/>
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
    <tiles:put name="emptyMessage" value="Не найдено ни одной записи, соответствующей заданному фильтру!"/>
  </tiles:insert>
