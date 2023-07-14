<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 24.08.2007
  Time: 15:49:06
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
<html:form action="/mail/sentList">
<tiles:insert definition="mailMain">
<tiles:put name="submenu" type="string" value="SentMailList"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="mailBundle"/>
</tiles:put>

<tiles:put name="menu" type="string">
	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/mail/view')}";
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
		<tiles:put name="image" value=""/>
		<tiles:put name="action" value="/mail/edit.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
	</tiles:insert>
</tiles:put>
<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.surName"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="surName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.subject"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="subject"/>
	</tiles:insert>
	<tiles:insert definition="filterEntryField" flush="false">
			<tiles:put name="label" value="label.recipientType"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="data">
				<html:select property="filter(recipientType)" styleClass="select">
					<html:option value="">Все</html:option>
					<html:option value="P">Клиент</html:option>
					<html:option value="G">Группа</html:option>
				</html:select>
			</tiles:put>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.groupName"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="groupName"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.fromDate"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="name" value="Date"/>
		<tiles:put name="template" value="DATE_TEMPLATE"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.patrName"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="patrName"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
	<c:set var="form" value="${ListSentMailForm}"/>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="sentMailList"/>
		<tiles:put name="text" value="Отправленные письма"/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.view"/>
                <tiles:put name="commandHelpKey" value="button.view.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="onclick" value="view();"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
            <sl:collection id="listElement" model="list" property="data" selectBean="mail_">
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <c:set var="recipientType" value="${listElement[0]}"/>
                <c:set var="firstName" value="${listElement[1]}"/>
                <c:set var="patrName" value="${listElement[2]}"/>
                <c:set var="surName" value="${listElement[3]}"/>
                <c:set var="mail_" value="${listElement[4]}"/>

                <sl:collectionItem title="Номер" name="mail_" property="num">
                    <sl:collectionItemParam
                            id="action"
                            value="/mail/view.do?id=${mail_.id}"
                            condition="${phiz:impliesService('MailManagment')}"
                            />
                </sl:collectionItem>
                <sl:collectionItem title="Получатель">
                    <sl:collectionItemParam id="value" condition="${(recipientType == 'P')}">
                        &nbsp;<c:out value="${surName}"/>&nbsp;<c:out value="${firstName}"/>&nbsp;<c:out value="${patrName}"/>
                    </sl:collectionItemParam>
					<sl:collectionItemParam id="value" condition="${(recipientType == 'G')}">
                        &nbsp;Группа&nbsp;"<c:out value="${firstName}"/>"
					</sl:collectionItemParam>
                </sl:collectionItem>
                <sl:collectionItem title="Тема" name="mail_" property="subject"/>
                <sl:collectionItem title="Дата">
                    &nbsp;
                    <c:if test="${not empty mail_.date}">
					    <bean:write name="mail_" property="date.time" format="dd.MM.yyyy HH:mm"/>
                    </c:if>
                </sl:collectionItem>
            </sl:collection>
        </tiles:put>
		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного письма, <br/>соответствующего заданному фильтру!"/>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>