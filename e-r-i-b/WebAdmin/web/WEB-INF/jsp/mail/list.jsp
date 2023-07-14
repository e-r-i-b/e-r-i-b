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
<html:form action="/mail/list">
<tiles:insert definition="mailMain">
<tiles:put name="submenu" type="string" value="MailList"/>
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
			<tiles:put name="label" value="label.mailType"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
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
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.firstName"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="firstName"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
			<tiles:put name="label" value="label.number"/>
			<tiles:put name="bundle" value="mailBundle"/>
			<tiles:put name="mandatory" value="false"/>
			<tiles:put name="name" value="num"/>
	</tiles:insert>
	<tiles:insert definition="filterDataSpan" flush="false">
		<tiles:put name="label" value="label.fromDate"/>
		<tiles:put name="bundle" value="mailBundle"/>
		<tiles:put name="mandatory" value="false"/>
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
	<tiles:insert definition="tableTemplate" flush="false">
        <script type="text/javascript">
            var addUrl = "${phiz:calculateActionURL(pageContext,'/mail/edit')}";

            function doEdit()
            {
                checkIfOneItem("selectedIds");
                if (!checkOneSelection("selectedIds", "Выберите одно письмо!"))
                    return;
                var mailId = getRadioValue(document.getElementsByName("selectedIds"));
                window.location = addUrl + "?mailId=" + mailId;
            }
        </script>
		<tiles:put name="id" value="incomingMailList"/>
		<tiles:put name="image" value="iconMid_Letters.gif"/>
		<tiles:put name="text" value="Входящие письма"/>
        <tiles:put name="buttons">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.view"/>
                <tiles:put name="commandHelpKey" value="button.view.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="onclick" value="view();"/>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.reply"/>
                <tiles:put name="commandHelpKey" value="button.reply.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkOneSelection("selectedIds", "Выберите одно письмо");
                    }
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="grid">
        <sl:collection id="listElement" model="list" property="data" selectBean="mail_">
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>
            <sl:collectionParam id="selectType" value="checkbox"/>
            <sl:collectionParam id="selectName" value="selectedIds"/>
            <sl:collectionParam id="selectProperty" value="id"/>

            <c:set var="sender" value="${listElement[0]}"/>
			<c:set var="mail_" value="${listElement[1]}"/>
			<c:set var="type" value="${mail_.type}"/>

            <sl:collectionItem title="Номер">
                <sl:collectionItemParam
                        id="action"
                        value="/mail/view.do?id=${mail_.id}"
                        condition="${phiz:impliesService('MailManagment')}"
                        />
                <c:if test="${not empty mail_}">
                    <c:choose>
                        <c:when test="${!mail_.read}">
                            <img src="${imagePath}/mail.gif" alt="Новое письмо" border="0">
                        </c:when>
                        <c:otherwise>
                            <img src="${imagePath}/iconSm_read.gif" alt="Прочтенное письмо" border="0">
                        </c:otherwise>
                    </c:choose>
                    &nbsp;<bean:write name="mail_" property="num"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="Отправитель">
                <c:if test="${not empty sender}">
					&nbsp;<bean:write name="sender" property="surName"/>
					&nbsp;<bean:write name="sender" property="firstName"/>
					&nbsp;<bean:write name="sender" property="patrName"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="Тема" name="mail_" property="subject"/>
            <sl:collectionItem title="Дата">
                <c:if test="${not empty mail_.date}">
                    &nbsp;<bean:write name="mail_" property="date.time" format="dd.MM.yyyy HH:mm"/>
                </c:if>
            </sl:collectionItem>
            <sl:collectionItem title="Тип письма">
                <sl:collectionItemParam id="value" value="Вопрос"   condition="${type=='Q'}"/>
                <sl:collectionItemParam id="value" value="Письмо"   condition="${type=='M'}"/>
                <sl:collectionItemParam id="value" value="Жалоба"   condition="${type=='C'}"/>
                <sl:collectionItemParam id="value" value="Проблема" condition="${type=='P'}"/>
            </sl:collectionItem>
        </sl:collection>
		</tiles:put>
		<tiles:put name="isEmpty" value="${empty ListMailForm.data}"/>
		<tiles:put name="emptyMessage" value="Не найдено ни одного письма, <br/>соответствующего заданному фильтру!"/>
    </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>