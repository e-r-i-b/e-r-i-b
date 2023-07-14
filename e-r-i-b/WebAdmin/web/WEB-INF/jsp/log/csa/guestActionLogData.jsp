<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
    <c:if test="${form.fromStart}">
    //показываем фильтр при старте
    switchFilter(this);
    </c:if>
</script>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isGuest" value="${form.filters['isGuestLog']}"/>
<c:set var="list" value="${form.list}"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="messageLog"/>
    <tiles:put name="grid">
        <sl:collection id="logEntry" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
            <sl:collectionItem title="label.record.num" name="logEntry" property="logUID"/>
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${logEntry.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>
            <c:if test="${isGuest}">
                <sl:collectionItem title="label.log.guest.phone">
                    <c:out value="${phiz:getAdminPhoneNumber(logEntry.phoneNumber)}"/>
                </sl:collectionItem>
            </c:if>
            <sl:collectionItem title="label.fullName">
                <c:choose>
                    <c:when test="${(not empty logEntry) && ((not empty logEntry.clientSurName) || (not empty logEntry.clientFirstName) ||(not empty logEntry.clientPatrName))}">
                        <bean:write name="logEntry" property="clientSurName"/>&nbsp;
                        <bean:write name="logEntry" property="clientFirstName"/>&nbsp;
                        <bean:write name="logEntry" property="clientPatrName"/>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.operation.name">
                <c:out value="${logEntry.operationType.requestType}"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.identification.type">
                <c:out value="${logEntry.identificationType.description}"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.operation.state">
                <c:choose>
                    <c:when test="${logEntry.errorMessage == null}">
                        Неуспешно
                    </c:when>
                    <c:otherwise>
                        Успешно
                    </c:otherwise>
                </c:choose>
                <c:if test="${logEntry.errorMessage == null}"></c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.birthday">
                <c:choose>
                    <c:when test="${not empty logEntry.birthDate}">
                        <fmt:formatDate value="${logEntry.birthDate.time}" pattern="dd.MM.yyyy"/>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.department.code">
                <c:if test="${not empty logEntry.tb}">
                    <bean:write name="logEntry" property="tb"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.ip.address" name="logEntry" property="ipAddress"/>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${empty form.list}"/>
</tiles:insert>
<script type="text/javascript">
    function openMessageDetails(id)
    {
        openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/messageDetail')}?id="+id+ "&messageType=${isGuest?'GUEST':'OTHER'}" +"&field(app)=CSABack",'Сообщение');
    }

    doOnLoad(function()
    {
        autoClickLanguageButton("requestButton", "request", "En");
        autoClickLanguageButton("responseButton", "response", "En");
    });
</script>
