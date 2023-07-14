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

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="messageLog"/>
    <tiles:put name="buttons">
         <tiles:insert definition="commandButton" flush="false">
            <tiles:put name="commandKey" value="button.download"/>
            <tiles:put name="commandHelpKey" value="button.download"/>
            <tiles:put name="bundle" value="logBundle"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" styleClass="standartTable">
            <c:set var="logEntry" value="${listElement[1]}"/>
            <c:set var="translate" value="${listElement[0]}"/>
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>
            <c:if test="${isGuest}">
                <sl:collectionItem title="label.log.guest.phone">
                    <c:out value="${phiz:getAdminPhoneNumber(logEntry.phoneNumber)}"/>
                </sl:collectionItem>
            </c:if>
            <sl:collectionItem title="label.system">
                <c:choose>
                    <c:when test="${logEntry.system != null}">
                        <bean:message key="message.log.system.${logEntry.system}" bundle="logBundle"/>
                    </c:when>
                    <c:otherwise><bean:message key="system.log.application.other" bundle="logBundle"/></c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.fullName">
                <c:choose>
                    <c:when test="${(not empty logEntry) && ((not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName))}">
                          <bean:write name="logEntry" property="surName"/>&nbsp;
                          <bean:write name="logEntry" property="firstName"/>&nbsp;
                          <bean:write name="logEntry" property="patrName"/>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.document">
                <c:out value="${not empty logEntry.personNumbers? logEntry.personNumbers: ''}"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.birthday">
                <c:choose>
                    <c:when test="${not empty logEntry.birthDay}">
                        <fmt:formatDate value="${logEntry.birthDay.time}" pattern="dd.MM.yyyy"/>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
            <sl:collectionItem title="label.application">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${logEntry.application}"/>
                </tiles:insert>
            </sl:collectionItem>
            <sl:collectionItem name="logEntry">
                <sl:collectionItemParam id="title">
                    <bean:message key="label.request.tag" bundle="logBundle"/>
                    <input type="button" id="requestButton" title="Для того чтобы изменить язык отображения, нажмите на эту кнопку" onclick="changeElemntValue(this, 'Ru', 'En');copyValueFromElementToElementById(this, 'request');hideOrShowByClass('request_tag_en', 'request_tag_ru');" value="Ru" class="changeLanguageButton"/>
                    <html:hidden styleId="request" property="field(request)"/>
                </sl:collectionItemParam>
                <div class="request_tag_en cursorArrow" style="display:block;" title="${empty translate ? logEntry.messageType  : translate}">
                    <c:out value="${logEntry.messageType}"/>&nbsp;
                </div>
                <div class="request_tag_ru cursorArrow" style="display:none;" title="${logEntry.messageType}">
                    <c:out value="${empty translate ? logEntry.messageType : translate}"/>&nbsp;
                </div>
            </sl:collectionItem>
            <sl:collectionItem title="label.messages">
                <input type="button" class="buttWhite smButt" onclick="openMessageDetails(${logEntry.id});" value="..."/>
            </sl:collectionItem>
            <sl:collectionItem title="label.department.code">
                <c:if test="${not empty logEntry.departmentCode}">
                    <bean:write name="logEntry" property="departmentCode"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.record.num" name="logEntry" property="id"/>
            <sl:collectionItem title="label.promoterId">
                <c:out value="${!isGuest? logEntry.promoterId: ''}"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.mGUID">
                <c:out value="${!isGuest? logEntry.mGUID: ''}"/>
            </sl:collectionItem>
            <sl:collectionItem title="label.ip.address" name="logEntry" property="ipAddress"/>
            <sl:collectionItem title="label.request.result">
                <c:choose>
                    <c:when test="${logEntry.errorCode == '0'}">Успешно</c:when>
                    <c:otherwise>Неуспешно</c:otherwise>
                </c:choose>
            </sl:collectionItem>
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
