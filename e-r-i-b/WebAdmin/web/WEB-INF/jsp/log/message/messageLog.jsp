<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="standalone" value="${empty param['action']}"/>
<c:choose>
	<c:when test="${standalone}">
		<c:set var="layout" value="personList"/>
	</c:when>
	<c:otherwise>
		<c:set var="layout" value="dictionary"/>
	</c:otherwise>
</c:choose>
<c:set var="form" value="${MessageLogForm}"/>

<html:form action="/log/messages">
<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="System"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="label.message.page.name" bundle="logBundle"/>
</tiles:put>

<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.request.tag"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="requestTag"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.request.word"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="requestWord"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.system"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(system)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="retail"><bean:message key="message.log.system.retail" bundle="logBundle"/></html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.responce.word"/>
			<tiles:put name="bundle" value="logBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="responceWord"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
        <tiles:put name="label" value="label.application"/>
        <tiles:put name="bundle" value="logBundle"/>
        <tiles:put name="mandatory" value="false"/>
        <tiles:put name="data">
            <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
        </tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.period"/>
		<tiles:put name="bundle" value="logBundle"/>
		<tiles:put name="mandatory" value="false"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
		<tiles:put name="time" value="true"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <script type="text/javascript">
        function openDetails(id)
        {
            openWindow(null, "${phiz:calculateActionURL(pageContext, '/log/detail/messageDetail')}?id="+id,'Сообщение');
        }
    </script>
    <tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="messageLog"/>
        <tiles:put name="buttons">
             <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.download"/>
                <tiles:put name="commandHelpKey" value="button.download"/>
                <tiles:put name="bundle" value="logBundle"/>
                 <tiles:put name="autoRefresh" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="wide-list" property="list" bundle="logBundle">
                <c:set var="logEntry" value="${listElement[0]}"/>
				<c:set var="userInfo" value="${listElement[1]}"/>
				<c:set var="emplInfo" value="${listElement[2]}"/>
                <sl:collectionItem title="label.record.num" name="logEntry" property="id"/>
                <sl:collectionItem title="label.datetime">
                    <fmt:formatDate value="${logEntry.date.time}" pattern="dd.MM.yyyy HH:mm:ss"/>
                </sl:collectionItem>
                <sl:collectionItem title="label.application">
                    <tiles:insert page="/WEB-INF/jsp/log/applicationTitle.jsp" flush="false">
                        <tiles:put name="application" value="${logEntry.application}"/>
                    </tiles:insert>
                </sl:collectionItem>
                <sl:collectionItem title="label.system">
                    <c:choose>
                        <c:when test="${logEntry.system=='retail'}">
                            <bean:message key="message.log.system.retail" bundle="logBundle"/>
                        </c:when>
                        <c:otherwise><bean:message key="system.log.application.other" bundle="logBundle"/></c:otherwise>
                    </c:choose>
                </sl:collectionItem>
                <sl:collectionItem title="label.request.tag" name="logEntry" property="messageType"/>
                <sl:collectionItem title="label.messages">
                    <input type="button" class="buttWhite smButt" onclick="javascript:openDetails(${logEntry.id});" value="..."/>
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

        <tiles:put name="emptyMessage" value="Не найдено ни одной записи, соответствующей заданному фильтру!"/>
      </tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>