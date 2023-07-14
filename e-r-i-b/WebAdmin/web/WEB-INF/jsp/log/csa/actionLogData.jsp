<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="actionLog"/>
    <tiles:put name="text" value="Журнал входов ЦСА"/>
    <tiles:put name="buttons">

        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.toSystemLog"/>
            <tiles:put name="commandHelpKey" value="button.toSystemLog"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="onclick" value="goToOtherLog('${phiz:calculateActionURL(pageContext, '/log/csa/system')}');"/>
        </tiles:insert>
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.toMessageLog"/>
            <tiles:put name="commandHelpKey" value="button.toMessageLog"/>
            <tiles:put name="bundle" value="logBundle"/>
            <tiles:put name="onclick" value="goToOtherLog('${phiz:calculateActionURL(pageContext,'/log/csa/messages')}');"/>
        </tiles:insert>

    </tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="logUID"/>
            <c:set var="logEntry" value="${listElement}"/>
            <sl:collectionItem title="label.datetime">
                <fmt:formatDate value="${logEntry.startDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>

            <sl:collectionItem title="label.fullName">
                <c:choose>
                    <c:when test="${(not empty logEntry) && ((not empty logEntry.clientSurName) || (not empty logEntry.clientFirstName) ||(not empty logEntry.clientPatrName))}">
                        <bean:write name="logEntry" property="clientSurName"/>&nbsp;
                        <bean:write name="logEntry" property="clientFirstName"/>&nbsp;
                        <bean:write name="logEntry" property="clientPatrName"/>
                    </c:when>
                </c:choose>
            </sl:collectionItem>

            <sl:collectionItem title="label.identification.type">
                <c:out value="${logEntry.identificationType.description}"/> :
                <c:choose>
                    <c:when test="${logEntry.identificationType == 'cardNumber'}">
                        <c:out value="${phiz:getCutCardNumber(logEntry.identificationParam)}"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${logEntry.identificationParam}"/>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>

            <sl:collectionItem title="label.csa.operation.type">
                <c:set var="operationKey" value="csa.operation.type.${logEntry.operationType}"/>
                <bean:message key="${operationKey}" bundle="logBundle"/>
            </sl:collectionItem>

            <sl:collectionItem title="label.employeeFIO">
                <c:out value="${logEntry.employeeFio}"/>
            </sl:collectionItem>

            <sl:collectionItem title="label.employeeLogin">
                <c:out value="${logEntry.employeeLogin}"/>
            </sl:collectionItem>

            <sl:collectionItem title="label.ip.address">
                <c:if test="${not empty logEntry.ipAddress}">
                    <bean:write name="logEntry" property="ipAddress"/>&nbsp;
                </c:if>
            </sl:collectionItem>

            <sl:collectionItem title="label.confirmation.type">
                <c:choose>
                    <c:when test="${empty logEntry.confirmationType}">
                        <bean:message key="label.confirmation.type.none" bundle="logBundle"/>
                    </c:when>
                    <c:when test="${logEntry.confirmationType == 'card'}">
                        <bean:message key="label.confirmation.type.card" bundle="logBundle"/> :
                        <c:out value="${phiz:getCutCardNumber(logEntry.cardNumber)}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="confirmationKey" value="label.confirmation.type.${logEntry.confirmationType}"/>
                        <bean:message key="${confirmationKey}" bundle="logBundle"/>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>

            <sl:collectionItem title="label.error.text">
                <c:if test="${!empty logEntry.errorMessage}">
                    <div id="triangle_${logEntry.logUID}" href="" onclick="openDetails(event, '${logEntry.logUID}', this);" class="simpleLink handCursor">
                        <image src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                    </div>
                </c:if>
                <c:choose>
                    <c:when test="${(!empty logEntry.errorMessage)}">
                        <div id="excep_${logEntry.logUID}" style="display:none">
                            <pre><c:out value="${logEntry.errorMessage}"/></pre>
                        </div>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>

    <c:set var="parameters" value="?filter(fio)=${form.activePerson.fullName}"/>
    <c:set var="parameters" value="${parameters}&filter(number)=${form.passport}"/>
    <c:set var="birthDate">
       <fmt:formatDate value="${form.activePerson.birthDay.time}" pattern="dd.MM.yyyy"/>
    </c:set>
    <c:set var="parameters" value="${parameters}&filter(birthday)=${birthDate}"/>

    <script type="text/javascript">
        var widthClient = getClientWidth();
        if (navigator.appName == 'Microsoft Internet Explorer')
            document.getElementById("workspaceDiv").style.width = widthClient - leftMenuSize - 120;

        <c:if test="${form.fromStart}">
        //показываем фильтр при старте
        switchFilter(this);
        </c:if>

        function openDetails(event, logUID, ref)
        {
            cancelEventCrossBrowser(event);
            var message = document.getElementById('excep_'+logUID);
            var isOff = (message.style.display == "none");
            ref.innerHTML = (isOff ? "<image src='" + document.imgPath + "iconSm_triangleUp.gif' border='0'/>" :
                                     "<image src='" + document.imgPath + "iconSm_triangleDown.gif' border='0'/>");
            message.style.display = (isOff ? "block":"none");
        }

        function checkSelectedIds(checkBoxName, msg)
        {
            if (getSelectedQnt(checkBoxName) > 1)
            {
                clearLoadMessage();
                return groupError(msg);
            }
            return true;
        }

        function goToOtherLog(actionUrl)
        {
            if (checkSelectedIds("selectedIds", "Выберите не более одной записи."))
            {
                var ids = document.getElementsByName("selectedIds");
                var logUrl = actionUrl + '${parameters}';
                for (var i = 0; i < ids.length; i++)  if (ids[i].checked)
                {
                    changeFormAction(logUrl + '&filter(logUID)=' + ids[i].value);
                    findCommandButton('button.filter').click('', true);
                    return;
                }
                var dateFormat = 'dd.mm.yyyy';
                var timeFormat = 'hh:min:ss';
                var date = new Date();
                var toDate = date.asString(dateFormat);
                var fromTime = date.asString(timeFormat);
                var toTime = date.asString(timeFormat);
                date.setMonth(date.getMonth() - 1);
                var fromDate = date.asString(dateFormat);
                var timeParams = "&filter(fromDate)=" + fromDate + "&filter(fromTime)=" + fromTime + "&filter(toDate)=" + toDate + "&filter(toTime)=" + toTime;
                logUrl = logUrl + timeParams;
                changeFormAction(logUrl);
                findCommandButton('button.filter').click('', true);
            }
        }

    </script>
    <tiles:put name="isEmpty" value="${empty form.list}"/>
    <tiles:put name="emptyMessage" value="Не найдено ни одной записи,<br/>соответствующей заданному фильтру!"/>
</tiles:insert>
