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
    function openDetails(id, type)
    {
        openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/messageDetail')}?id="+id +"&messageType="+type,'Сообщение');
    }
</script>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="isGuest" value="${form.filters['isGuestLog']}"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="messageLog"/>
    <c:if test="${standalone}">
        <tiles:put name="buttons">
             <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.download"/>
                <tiles:put name="commandHelpKey" value="button.download"/>
                <tiles:put name="bundle" value="logBundle"/>
            </tiles:insert>
        </tiles:put>
    </c:if>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${listElement.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>
            <c:if test="${isGuest}">
                <sl:collectionItem title="label.log.guest.phone">
                    <c:out value="${phiz:getAdminPhoneNumber(listElement.phone)}"/>
                </sl:collectionItem>
            </c:if>
            <sl:collectionItem title="label.system">
                <c:choose>
                    <c:when test="${listElement.system != null}">
                        <bean:message key="message.log.system.${listElement.system}" bundle="logBundle"/>
                    </c:when>
                    <c:otherwise><bean:message key="system.log.application.other" bundle="logBundle"/></c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem name="logEntry">
                <sl:collectionItemParam id="title">
                    <bean:message key="label.request.tag" bundle="logBundle"/>
                    <input type="button" id="requestButton" title="Для того чтобы изменить язык отображения, нажмите на эту кнопку" onclick="changeElemntValue(this, 'Ru', 'En');copyValueFromElementToElementById(this, 'request');hideOrShowByClass('request_tag_en', 'request_tag_ru');" value="Ru" class="changeLanguageButton"/>
                    <html:hidden styleId="request" property="field(request)"/>
                </sl:collectionItemParam>
                <div class="request_tag_en cursorArrow" style="display:block;" title="${empty listElement.messageTranslate ? listElement.messageType  : listElement.messageTranslate}">
                    <c:out value="${listElement.messageType}"/>&nbsp;
                </div>
                <div class="request_tag_ru cursorArrow" style="display:none;" title="${listElement.messageType}">
                    <c:out value="${empty listElement.messageTranslate ? listElement.messageType : listElement.messageTranslate}"/>&nbsp;
                </div>
            </sl:collectionItem>
            <sl:collectionItem title="label.messages">
                <input type="button" class="buttWhite smButt detailButton" onclick="openDetails(${listElement.id}, '${listElement.type}');" value="..." disabled="disabled"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.application">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${listElement.application}"/>
                </tiles:insert>
            </sl:collectionItem>
            <sl:collectionItem title="label.department.name">
                <c:if test="${not empty listElement.departmentName}">
                    <c:out value="${listElement.departmentName}"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.record.num" ><c:out value="${listElement.messageTranslate}"/></sl:collectionItem>
            <sl:collectionItem title="label.session.id">
                <c:if test="${not empty listElement.sessionId}">
                    <c:out value="${listElement.sessionId}"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.node.id">
                <c:if test="${not empty listElement.nodeId}">
                    <c:out value="${listElement.nodeId}"/>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${empty form.list}"/>
</tiles:insert>
<script type="text/javascript">
    doOnLoad(function()
    {
        autoClickLanguageButton("requestButton", "request", "En");
        autoClickLanguageButton("responseButton", "response", "En");

        $('.detailButton').removeAttr('disabled');
    });
</script>
