<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 24.08.2007
  Time: 17:17:07
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
<html:form action="/private/mail/sentList">
<tiles:insert definition="mailMain">
<tiles:put name="submenu" type="string" value="SentMailList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="mailBundle"/>.&nbsp;<bean:message key="sentList.title" bundle="mailBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/private/mail/viewSent')}";
		function view()
		{
            checkIfOneItem("selectedIds");
			if (!checkOneSelection("selectedIds", "Выберите одно письмо"))
				return;

			var id = getRadioValue(document.getElementsByName("selectedIds"))

			window.location = addUrl + "?id=" + id;
		}
	</script>
	<tiles:insert definition="clientButton" flush="false">
		<tiles:put name="commandTextKey" value="button.add"/>
		<tiles:put name="commandHelpKey" value="button.add.help"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="action" value="/private/mail/edit.do"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.subject"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="name" value="subject"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
		<tiles:put name="label" value="label.mailType"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="data">
			<html:select property="filter(type)" styleClass="select">
				<html:option value="">Все</html:option>
				<html:option value="M">Письмо</html:option>
				<html:option value="Q">Вопрос</html:option>
				<html:option value="C">Жалоба</html:option>
				<html:option value="P">Проблема</html:option>
			</html:select>
		</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.date"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="mailTab"/>
		<tiles:put name="image" value="iconMid_sentLetters.gif"/>
		<tiles:put name="text" value="Список отправленных писем"/>
		<tiles:put name="buttons">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.view"/>
				<tiles:put name="commandHelpKey" value="button.view.help"/>
				<tiles:put name="bundle"         value="mailBundle"/>
				<tiles:put name="onclick"        value="view();"/>
				<tiles:put name="image"          value="iconSm_view.gif"/>
			</tiles:insert>
		</tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="type" value="${listElement.type}"/>

                <sl:collectionItem title="Номер" name="listElement" property="num">
                    <sl:collectionItemParam
                            id="action"
                            value="/private/mail/viewSent.do?id=${listElement.id}"
                            condition="${phiz:impliesService('ClientMailManagement')}"
                            />
                </sl:collectionItem>
                <sl:collectionItem title="Тема" name="listElement" property="subject"/>
                <sl:collectionItem title="Дата">
                    <c:if test="${not empty listElement}">
                        <bean:write name="listElement" property="date.time" format="dd.MM.yyyy HH:mm"/>
                    </c:if>
                </sl:collectionItem>
                <sl:collectionItem title="Тип письма">
                    <sl:collectionItemParam id="value" value="Вопрос" condition="${type=='Q'}"/>
                    <sl:collectionItemParam id="value" value="Письмо" condition="${type=='M'}"/>
                    <sl:collectionItemParam id="value" value="Жалоба" condition="${type=='C'}"/>
                    <sl:collectionItemParam id="value" value="Проблема" condition="${type=='P'}"/>
                </sl:collectionItem>
            </sl:collection>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty ListSentMailForm.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного письма, <br/>соответствующего заданному фильтру"/>
	</tiles:insert>
</tiles:put>
</tiles:insert>
</html:form>