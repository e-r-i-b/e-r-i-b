<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<script type="text/javascript">
    var rootURL = document.webRoot;
    function goToCommonJournal()
    {
        checkIfOneItem("selectedIds");
       if (!checkOneSelection("selectedIds", "Выберите одну запись"))
            return;

        var ids = document.getElementsByName("selectedIds");
        for (var i = 0; i < ids.length; i++)
        {
            if (ids.item(i).checked)
            {
                var sessionId = getElementValue("session"+ids.item(i).value);
                var date      = getElementValue("openDate"+ids.item(i).value);
            }
        }
        window.location = getToCommonJournalUrl(sessionId, date);
    }

    function openDetails(operationUID)
	{
		window.open("${phiz:calculateActionURL(pageContext, '/log/confirmationInfo.do')}?filter(operationUID)=" + operationUID,'confirmationInfo', "resizable=1,menubar=1,toolbar=1,scrollbars=1");
	}

    function showAdditionalInfo(id)
    {
        var elem = document.getElementById('additionalInfo'+id);
        var img = document.getElementById('img'+id);
        if (elem.style.display == "none")
        {
            elem.style.display = "";
            img.src = "${imagePath}/minus.gif";
        }
        else
        {
            elem.style.display = "none";
            img.src = "${imagePath}/plus.gif";
        }
    }

    <c:if test="${form.fromStart}">
       //показываем фильтр при старте
       switchFilter(this);
    </c:if>
</script>

<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="InputJournalList"/>
    <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="label.comlog.page.name"/>
                <tiles:put name="commandHelpKey" value="label.comlog.page.name"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="onclick" value="goToCommonJournal();"/>
            </tiles:insert>
        </tiles:put>
    <tiles:put name="grid">
        <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle" selectBean="logEntry" styleClass="standartTable">
            <sl:collectionParam id="selectType"/>
            <sl:collectionParam id="selectProperty" value="id"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>

            <c:set var="logEntry" value="${listElement[2]}"/>
            <c:set var="confirmType" value="${listElement[0]}"/>
            <c:set var="hasTry" value="${listElement[1]}"/>

            <sl:collectionItem title="label.datetime">
                <html:hidden property="openDate${logEntry.id}" value="${phiz:dateToString(logEntry.date.time)}"/>
                <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </sl:collectionItem>

            <sl:collectionItem title="Тип">
                <c:if test="${not empty logEntry}">
                    ${phiz:getPlatformText(logEntry.type.description)}
                </c:if>
            </sl:collectionItem>

            <sl:collectionItem title="label.id">
                <c:if test="${not empty logEntry}">
                    <bean:write name="logEntry" property="loginId"/>
                </c:if>
            </sl:collectionItem>

            <sl:collectionItem title="label.fullName">
                <c:if test="${(not empty logEntry) && ((not empty logEntry.surName) || (not empty logEntry.firstName) ||(not empty logEntry.patrName))}">
                      <bean:write name="logEntry" property="surName"/>&nbsp;
                      <bean:write name="logEntry" property="firstName"/>&nbsp;
                      <bean:write name="logEntry" property="patrName"/>
                </c:if>
            </sl:collectionItem>

            <sl:collectionItem title="label.birthday">
                <fmt:formatDate value="${logEntry.birthday.time}" pattern="dd.MM.yyyy"/>
            </sl:collectionItem>

            <sl:collectionItem title="Приложение / Платформа">
                <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                    <tiles:put name="application" value="${logEntry.application}"/>
                </tiles:insert>

                <c:choose>
                    <c:when test="${logEntry.application == 'atm'}">
                        / АТМ <c:if test="${not empty logEntry.deviceInfo}"> <a onclick="showAdditionalInfo('${logEntry.id}');" class="imgA"> <img id='img${logEntry.id}' class="openClose" src="${imagePath}/plus.gif" alt="" border="0"> </a>
                        <br/><div id='additionalInfo${logEntry.id}' style="display:none; border: 1px solid;">${logEntry.deviceInfo}</div> </c:if>
                    </c:when>
                    <c:when test="${logEntry.application == 'socialApi'}">
                        <c:choose>
                            <c:when test="${empty(logEntry.deviceInfo)}">
                               / -
                            </c:when>
                            <c:otherwise>
                                / ${phiz:getPlatformText(logEntry.deviceInfo)}
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${fn:contains(logEntry.application, 'mobile')}">
                        <c:choose>
                            <c:when test="${empty(logEntry.deviceInfo)}">
                                / -
                            </c:when>
                            <c:otherwise>
                                / ${phiz:getPlatformText(logEntry.deviceInfo)}
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        / Web-браузер <c:if test="${not empty logEntry.deviceInfo}"> <a onclick="showAdditionalInfo('${logEntry.id}');" class="imgA"> <img id='img${logEntry.id}' class="openClose" src="${imagePath}/plus.gif" alt="" border="0"> </a>
                        <br/><div id='additionalInfo${logEntry.id}' style="display:none; border: 1px solid;">${logEntry.deviceInfo}</div> </c:if>
                    </c:otherwise>
                </c:choose>
            </sl:collectionItem>

            <sl:collectionItem title="label.session.id">
                <html:hidden property="session${logEntry.id}" value="${logEntry.sessionId}"/>
                <c:out value="${logEntry.sessionId}"/>
            </sl:collectionItem>

           <sl:collectionItem title="label.ip.address">
                <c:out value="${logEntry.ipAddress}"/>
            </sl:collectionItem>

            <sl:collectionItem title="label.confirm.password">
               <c:choose>
                   <c:when test="${confirmType == 'CARD'}">Подтверждение чековым паролем</c:when>
                   <c:when test="${confirmType == 'SMS'}">Подтверждение SMS</c:when>
                   <c:when test="${confirmType == 'CAP'}">Подтверждение CAP-картой</c:when>
                   <c:when test="${confirmType == 'PUSH'}">Push-уведомление</c:when>
                   <c:when test="${hasTry}">Не подтвержден</c:when>
                   <c:otherwise>Не требуется подтверждения</c:otherwise>
               </c:choose>
                <c:if test="${hasTry}">&nbsp;<input type="button" class="buttWhite smButt" onclick="openDetails('${logEntry.operationUID}');" value="..."/></c:if>
            </sl:collectionItem>
            <sl:collectionItem title="label.card.number">
                <c:out value="${phiz:getCutCardNumber(logEntry.cardNumber)}"/>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
</tiles:insert>
