<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 26.02.2007
  Time: 16:48:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<% pageContext.getRequest().setAttribute("mode", "Mail");%>
<html:form action="/private/mail/list">
<tiles:insert definition="mailMain">
<tiles:put name="submenu" type="string" value="MailList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="mailBundle"/>.&nbsp;<bean:message key="receivedList.title" bundle="mailBundle"/>
</tiles:put>
<tiles:put name="menu" type="string">
	<script type="text/javascript">
		function next(param)
		{
            checkIfOneItem("selectedIds");
			if (!checkOneSelection("selectedIds", "Выберите одно письмо"))
				return;

			if (param == 'view')
			{
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/mail/view')}"/>
				window.location = "${url}"
								+ "?id=" + getRadioValue(document.getElementsByName("selectedIds"));
			}
			if (param == 'reply')
			{
                <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/mail/edit')}"/>
				window.location = "${url}"
								+ "?mailId=" + getRadioValue(document.getElementsByName("selectedIds"));
			}
		}
	</script>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.new.mail"/>
		<tiles:put name="commandHelpKey" value="button.add.help"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="action"  value="/private/mail/edit.do"/>
	</tiles:insert>
</tiles:put>
<script type="text/javascript">
	function checkSelected()
	{
        checkIfOneItem("selectedIds");
		 if (checkSelection('selectedIds', 'Выберите письмо'))
			if(checkOneSelection('selectedIds', 'Выберите одно письмо'))
				return true;
		return false;
	}
</script>
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.subject"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="name" value="subject"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.important"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="data">
			<html:select property="filter(important)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="1">Да</html:option>
				<html:option value="0">Нет</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.received"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="data">
			<html:select property="filter(received)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="1">Да</html:option>
				<html:option value="0">Нет</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
<tiles:insert definition="tableTemplate" flush="false">
	<tiles:put name="id" value="mailTab"/>
	<tiles:put name="image" value="iconMid_Letters.gif"/>
	<tiles:put name="text" value="Список полученных писем"/>
	<tiles:put name="buttons">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.view"/>
			<tiles:put name="commandHelpKey" value="button.view.help"/>
			<tiles:put name="bundle"         value="mailBundle"/>
			<tiles:put name="onclick"        value="next('view');"/>
			<tiles:put name="image"          value="iconSm_view.gif"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.reply"/>
			<tiles:put name="commandHelpKey" value="button.reply.help"/>
			<tiles:put name="bundle"         value="mailBundle"/>
			<tiles:put name="image"          value="iconSm_reply.gif"/>
			<tiles:put name="onclick" value="next('reply');"></tiles:put>
		</tiles:insert>
	</tiles:put>
    <tiles:put name="grid">
        <tiles:importAttribute/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <c:set var="imagePath" value="${skinUrl}/images"/>

        <sl:collection id="listElement" model="list" property="data">
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <sl:collectionItem title="Тема" width="70%">
                <sl:collectionItemParam
                        id="action"
                        value="/private/mail/view.do?id=${listElement.id}"
                        condition="${phiz:impliesService('ClientMailManagement')}"/>
                <c:choose>
                    <c:when test="${!listElement.read && listElement.important}">
                        <img src="${imagePath}/iconSm_important.gif" alt="Новое письмо! Обязательно для прочтения!" border="0">
                    </c:when>
                    <c:when test="${listElement.read && listElement.important}">
                        <img src="${imagePath}/iconSm_important_read.gif" alt="Обязательно для прочтения" border="0">
                    </c:when>
                    <c:when test="${!listElement.read && !listElement.important}">
                        <img src="${imagePath}/mail.gif" alt="Новое письмо" border="0">
                    </c:when>
                    <c:when test="${listElement.read && !listElement.important}">
                        <img src="${imagePath}/iconSm_read.gif" alt="Новое письмо" border="0">
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
                <c:if test="${not empty listElement}">
                    <bean:write name="listElement" property="subject"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="Дата">
                <c:if test="${not empty listElement}">
                    <bean:write name="listElement" property="date.time" format="dd.MM.yyyy HH:mm"/>
                </c:if>
            </sl:collectionItem>
        </sl:collection>
    </tiles:put>
    <tiles:put name="isEmpty" value="${isEmpty}"/>
    <tiles:put name="emptyMessage" value="Не найдено ни одного письма,<br/>соответствующего заданному фильтру!"/>
</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>